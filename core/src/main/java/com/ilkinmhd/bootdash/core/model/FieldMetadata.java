package com.ilkinmhd.bootdash.core.model;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/** Contains metadata about a field that has been marked with @AdminField. */
public class FieldMetadata {
  private String name;
  private String fieldName;
  private String description;
  private boolean editable;
  private boolean visible;
  private int order;
  private String widget;
  private boolean inList;
  private boolean required;
  private String pattern;
  private String validationMessage;
  private Class<?> type;
  private Field field;
  private boolean isId;
  private boolean isRelationship;

  // "one-to-one", "one-to-many", "many-to-one", "many-to-many"
  private String relationshipType;
  private String relationshipEntityName;
  private Map<String, Object> additionalProperties = new HashMap<>();

  public FieldMetadata() {}

  public FieldMetadata(Field field) {
    this.field = field;
    this.fieldName = field.getName();
    this.type = field.getType();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getFieldName() {
    return fieldName;
  }

  public void setFieldName(String fieldName) {
    this.fieldName = fieldName;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public boolean isEditable() {
    return editable;
  }

  public void setEditable(boolean editable) {
    this.editable = editable;
  }

  public boolean isVisible() {
    return visible;
  }

  public void setVisible(boolean visible) {
    this.visible = visible;
  }

  public int getOrder() {
    return order;
  }

  public void setOrder(int order) {
    this.order = order;
  }

  public String getWidget() {
    return widget;
  }

  public void setWidget(String widget) {
    this.widget = widget;
  }

  public boolean isInList() {
    return inList;
  }

  public void setInList(boolean inList) {
    this.inList = inList;
  }

  public boolean isRequired() {
    return required;
  }

  public void setRequired(boolean required) {
    this.required = required;
  }

  public String getPattern() {
    return pattern;
  }

  public void setPattern(String pattern) {
    this.pattern = pattern;
  }

  public String getValidationMessage() {
    return validationMessage;
  }

  public void setValidationMessage(String validationMessage) {
    this.validationMessage = validationMessage;
  }

  public Class<?> getType() {
    return type;
  }

  public void setType(Class<?> type) {
    this.type = type;
  }

  public Field getField() {
    return field;
  }

  public void setField(Field field) {
    this.field = field;
    this.fieldName = field.getName();
    this.type = field.getType();
  }

  public boolean isId() {
    return isId;
  }

  public void setId(boolean id) {
    isId = id;
  }

  public boolean isRelationship() {
    return isRelationship;
  }

  public void setRelationship(boolean relationship) {
    isRelationship = relationship;
  }

  public String getRelationshipType() {
    return relationshipType;
  }

  public void setRelationshipType(String relationshipType) {
    this.relationshipType = relationshipType;
  }

  public String getRelationshipEntityName() {
    return relationshipEntityName;
  }

  public void setRelationshipEntityName(String relationshipEntityName) {
    this.relationshipEntityName = relationshipEntityName;
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
