package com.ilkinmhd.bootdash.core.model;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/** Contains metadata about a method that has been marked with @AdminAction. */
public class ActionMetadata {
  private String name;
  private String description;
  private boolean individual;
  private String icon;
  private String buttonClass;
  private boolean requireConfirmation;
  private String confirmationMessage;
  private Method method;
  private Map<String, Object> additionalProperties = new HashMap<>();

  public ActionMetadata() {}

  public ActionMetadata(Method method) {
    this.method = method;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public boolean isIndividual() {
    return individual;
  }

  public void setIndividual(boolean individual) {
    this.individual = individual;
  }

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public String getButtonClass() {
    return buttonClass;
  }

  public void setButtonClass(String buttonClass) {
    this.buttonClass = buttonClass;
  }

  public boolean isRequireConfirmation() {
    return requireConfirmation;
  }

  public void setRequireConfirmation(boolean requireConfirmation) {
    this.requireConfirmation = requireConfirmation;
  }

  public String getConfirmationMessage() {
    return confirmationMessage;
  }

  public void setConfirmationMessage(String confirmationMessage) {
    this.confirmationMessage = confirmationMessage;
  }

  public Method getMethod() {
    return method;
  }

  public void setMethod(Method method) {
    this.method = method;
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
