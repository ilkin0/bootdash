package com.ilkinmhd.bootdash.core.service.impl;

import com.ilkinmhd.bootdash.core.annotation.AdminEntity;
import com.ilkinmhd.bootdash.core.model.EntityMetadata;
import com.ilkinmhd.bootdash.core.service.EntityMetadataService;
import com.ilkinmhd.bootdash.core.util.ReflectionUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Implementation of EntityMetadataService that scans classes for annotations. */
public class EntityMetadataServiceImpl implements EntityMetadataService {
  private static final Logger logger = LoggerFactory.getLogger(EntityMetadataServiceImpl.class);
  private final Map<String, EntityMetadata> entityMetadataMap = new HashMap<>();

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
    return entityMetadataMap.values().stream()
        .map(EntityMetadata::getGroup)
        .distinct()
        .collect(Collectors.toList());
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
        // TODO process entity class
      }
    }
    logger.info("Entity scanning complete. Found {} entities", entityMetadataMap.size());
  }
}
