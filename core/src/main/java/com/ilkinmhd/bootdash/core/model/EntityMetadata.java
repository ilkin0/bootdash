package com.ilkinmhd.bootdash.core.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Contains metadata about an entity class that has been marked with @AdminEntity. */
public class EntityMetadata {
  private String name;
  private String pluralName;
  private String className;
  private Class<?> entityClass;
  private String description;
  private boolean readOnly;
  private String icon;
  private String group;
  private int order;
  private List<FieldMetadata> fields = new ArrayList<>();
  private List<ActionMetadata> actions = new ArrayList<>();
  private Map<String, Object> additionalProperties = new HashMap<>();

  public EntityMetadata() {}

  public EntityMetadata(Class<?> entityClass) {
    this.entityClass = entityClass;
    this.className = entityClass.getName();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPluralName() {
    return pluralName;
  }

  public void setPluralName(String pluralName) {
    this.pluralName = pluralName;
  }

  public String getClassName() {
    return className;
  }

  public void setClassName(String className) {
    this.className = className;
  }

  public Class<?> getEntityClass() {
    return entityClass;
  }

  public void setEntityClass(Class<?> entityClass) {
    this.entityClass = entityClass;
    this.className = entityClass.getName();
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public boolean isReadOnly() {
    return readOnly;
  }

  public void setReadOnly(boolean readOnly) {
    this.readOnly = readOnly;
  }

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public String getGroup() {
    return group;
  }

  public void setGroup(String group) {
    this.group = group;
  }

  public int getOrder() {
    return order;
  }

  public void setOrder(int order) {
    this.order = order;
  }

  public List<FieldMetadata> getFields() {
    return fields;
  }

  public void setFields(List<FieldMetadata> fields) {
    this.fields = fields;
  }

  public void addField(FieldMetadata field) {
    this.fields.add(field);
  }

  public List<ActionMetadata> getActions() {
    return actions;
  }

  public void setActions(List<ActionMetadata> actions) {
    this.actions = actions;
  }

  public void addAction(ActionMetadata action) {
    this.actions.add(action);
  }

  public Map<String, Object> getAdditionalProperties() {
    return additionalProperties;
  }

  public void setAdditionalProperties(Map<String, Object> additionalProperties) {
    this.additionalProperties = additionalProperties;
  }

  public void addAdditionalProperty(String key, Object value) {
    this.additionalProperties.put(key, value);
  }
}
