package com.ilkinmhd.bootdash.core.service.impl;

import com.ilkinmhd.bootdash.core.annotation.AdminAction;
import com.ilkinmhd.bootdash.core.annotation.AdminEntity;
import com.ilkinmhd.bootdash.core.annotation.AdminField;
import com.ilkinmhd.bootdash.core.model.ActionMetadata;
import com.ilkinmhd.bootdash.core.model.EntityMetadata;
import com.ilkinmhd.bootdash.core.model.FieldMetadata;
import com.ilkinmhd.bootdash.core.service.EntityMetadataService;
import com.ilkinmhd.bootdash.core.util.ReflectionUtils;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Implementation of EntityMetadataService that scans classes for annotations. */
public class EntityMetadataServiceImpl implements EntityMetadataService {
  private static final Logger logger = LoggerFactory.getLogger(EntityMetadataServiceImpl.class);
  private final Map<String, EntityMetadata> entityMetadataMap = new ConcurrentHashMap<>();

  @Override
  public List<EntityMetadata> getAllEntities() {
    return List.of(entityMetadataMap.values().toArray(new EntityMetadata[0]));
  }

  @Override
  public Optional<EntityMetadata> getEntityMetadata(String entityName) {
    return Optional.ofNullable(entityMetadataMap.get(entityName));
  }

  @Override
  public List<String> getEntityGroups() {
    return entityMetadataMap.values().stream().map(EntityMetadata::getGroup).distinct().toList();
  }

  @Override
  public void scanForEntities(String... basePackages) {
    if (basePackages == null || basePackages.length == 0) {
      logger.warn("basePackages is null or empty");
      return;
    }

    for (String basePackage : basePackages) {
      logger.info("Scanning for entity packages: {}", basePackage);
      Set<Class<?>> classes =
          ReflectionUtils.findClassesWithAnnotation(basePackage, AdminEntity.class);

      for (Class<?> clazz : classes) {
        processEntityClass(clazz);
      }
    }
    logger.info("Entity scanning complete. Found {} entities", entityMetadataMap.size());
  }

  private void processEntityClass(Class<?> entityClass) {
    AdminEntity annotation = entityClass.getAnnotation(AdminEntity.class);
    if (annotation != null) {
      return;
    }
    logger.debug("Processing entity class: {}", entityClass.getName());

    EntityMetadata metadata = new EntityMetadata(entityClass);

    String name = annotation.name().isEmpty() ? entityClass.getSimpleName() : annotation.name();
    metadata.setName(name);
    String pluralName = annotation.pluralName().isEmpty() ? name + "s" : annotation.pluralName();
    metadata.setPluralName(pluralName);

    metadata.setDescription(annotation.description());
    metadata.setReadOnly(annotation.readOnly());
    metadata.setIcon(annotation.icon());
    metadata.setGroup(annotation.group());
    metadata.setOrder(annotation.order());

    processFields(metadata, entityClass);
    processMethods(metadata, entityClass);

    entityMetadataMap.put(name.toLowerCase(), metadata);
    logger.debug("Added entity metadata for: {}", name);
  }

  private void processFields(EntityMetadata metadata, Class<?> entityClass) {
    for (Field field : entityClass.getDeclaredFields()) {
      if (Modifier.isStatic(field.getModifiers()) || Modifier.isTransient(field.getModifiers())) {
        continue;
      }

      FieldMetadata fieldMetadata = new FieldMetadata(field);

      if (field.isAnnotationPresent(Id.class)) {
        fieldMetadata.setId(true);
        fieldMetadata.setEditable(false);
      }

      processRelationships(fieldMetadata, field);

      if (field.isAnnotationPresent(AdminField.class)) {
        AdminField annotation = field.getAnnotation(AdminField.class);

        if (annotation != null) {
          String fieldName = annotation.name().isEmpty() ? field.getName() : annotation.name();
          fieldMetadata.setName(fieldName);

          fieldMetadata.setDescription(annotation.description());
          fieldMetadata.setEditable(annotation.editable());
          fieldMetadata.setVisible(annotation.visible());
          fieldMetadata.setOrder(annotation.order());
          fieldMetadata.setWidget(annotation.widget());
          fieldMetadata.setInList(annotation.inList());
          fieldMetadata.setRequired(annotation.required());
          fieldMetadata.setPattern(annotation.pattern());
          fieldMetadata.setValidationMessage(annotation.validationMessage());
        }
      } else {
        fieldMetadata.setName(field.getName());
        fieldMetadata.setEditable(true);
        fieldMetadata.setVisible(true);
        fieldMetadata.setInList(true);

        fieldMetadata.setWidget(determineDefaultWidget(field.getType()));
      }

      metadata.addField(fieldMetadata);
    }
    metadata.getFields().sort(Comparator.comparingInt(FieldMetadata::getOrder));
  }

  private void processRelationships(FieldMetadata fieldMetadata, Field field) {
    if (field.isAnnotationPresent(OneToOne.class)) {
      fieldMetadata.setRelationship(true);
      fieldMetadata.setRelationshipType("one-to-one");
      OneToOne annotation = field.getAnnotation(OneToOne.class);

      // Extract target entity name if possible
      Class<?> targetEntity = annotation.targetEntity();
      updateRelationshipEntityName(fieldMetadata, field, targetEntity);
    } else if (field.isAnnotationPresent(OneToMany.class)) {
      fieldMetadata.setRelationship(true);
      fieldMetadata.setRelationshipType("one-to-many");
      OneToMany annotation = field.getAnnotation(OneToMany.class);
      Class<?> targetEntity = annotation.targetEntity();
      updateRelationshipEntityName(fieldMetadata, field, targetEntity);
    } else if (field.isAnnotationPresent(ManyToOne.class)) {
      fieldMetadata.setRelationship(true);
      fieldMetadata.setRelationshipType("many-to-one");
      ManyToOne annotation = field.getAnnotation(ManyToOne.class);
      Class<?> targetEntity = annotation.targetEntity();
      if (targetEntity != void.class) {
        fieldMetadata.setRelationshipEntityName(targetEntity.getSimpleName());
      } else {
        fieldMetadata.setRelationshipEntityName(field.getType().getSimpleName());
      }
      updateRelationshipEntityName(fieldMetadata, targetEntity, field.getType().getSimpleName());

    } else if (field.isAnnotationPresent(ManyToMany.class)) {
      fieldMetadata.setRelationship(true);
      fieldMetadata.setRelationshipType("many-to-many");
      ManyToMany annotation = field.getAnnotation(ManyToMany.class);
      Class<?> targetEntity = annotation.targetEntity();
      updateRelationshipEntityName(fieldMetadata, field, targetEntity);
    }
  }

  private void updateRelationshipEntityName(
      FieldMetadata fieldMetadata, Field field, Class<?> targetEntity) {
    if (targetEntity != void.class) {
      fieldMetadata.setRelationshipEntityName(targetEntity.getSimpleName());
    } else {
      fieldMetadata.setRelationshipEntityName(getGenericType(field));
    }
  }

  private void updateRelationshipEntityName(
      FieldMetadata fieldMetadata, Class<?> targetEntity, String simpleName) {
    if (targetEntity != void.class) {
      fieldMetadata.setRelationshipEntityName(targetEntity.getSimpleName());
    } else {
      fieldMetadata.setRelationshipEntityName(simpleName);
    }
  }

  private String getGenericType(Field field) {
    String typeName = field.getGenericType().getTypeName();
    int startIndex = typeName.indexOf('<');
    int endIndex = typeName.lastIndexOf('>');

    if (startIndex > 0 && endIndex > startIndex) {
      String genericPart = typeName.substring(startIndex + 1, endIndex);
      String[] parts = genericPart.split("\\.");
      return parts[parts.length - 1];
    }

    return field.getType().getSimpleName();
  }

  private String determineDefaultWidget(Class<?> type) {
    if (type == String.class) {
      return "text";
    } else if (type == Integer.class
        || type == int.class
        || type == Long.class
        || type == long.class
        || type == Short.class
        || type == short.class) {
      return "number";
    } else if (type == Double.class
        || type == double.class
        || type == Float.class
        || type == float.class) {
      return "number";
    } else if (type == Boolean.class || type == boolean.class) {
      return "boolean";
    } else if (type == java.util.Date.class || type == java.time.LocalDate.class) {
      return "date";
    } else if (type == java.time.LocalDateTime.class || type == java.time.ZonedDateTime.class) {
      return "datetime";
    } else {
      return "text";
    }
  }

  private void processMethods(EntityMetadata metadata, Class<?> entityClass) {
    for (Method method : entityClass.getDeclaredMethods()) {
      if (!Modifier.isPublic(method.getModifiers())) {
        continue;
      }

      if (method.isAnnotationPresent(AdminAction.class)) {
        AdminAction annotation = method.getAnnotation(AdminAction.class);
        if (annotation != null) {
          ActionMetadata actionMetadata = getActionMetadata(method, annotation);
          metadata.addAction(actionMetadata);
        }
      }
    }

    metadata.getActions().sort(Comparator.comparing(ActionMetadata::getName));
  }

  private static ActionMetadata getActionMetadata(Method method, AdminAction annotation) {
    ActionMetadata actionMetadata = new ActionMetadata(method);
    actionMetadata.setName(annotation.name());
    actionMetadata.setDescription(annotation.description());
    actionMetadata.setIndividual(annotation.individual());
    actionMetadata.setIcon(annotation.icon());
    actionMetadata.setButtonClass(annotation.buttonClass());
    actionMetadata.setRequireConfirmation(annotation.requireConfirmation());
    actionMetadata.setConfirmationMessage(annotation.confirmationMessage());
    return actionMetadata;
  }
}
