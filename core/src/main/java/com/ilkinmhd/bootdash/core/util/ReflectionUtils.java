package com.ilkinmhd.bootdash.core.util;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Utilities for working with reflection and scanning for classes. */
public final class ReflectionUtils {
  private static final Logger logger = LoggerFactory.getLogger(ReflectionUtils.class);

  private ReflectionUtils() {}

  /**
   * Find classes in the specified package that have a specific annotation.
   *
   * @param basePackage the base package to scan
   * @param annotation the annotation to look for
   * @return a set of classes with the specified annotation
   */
  public static Set<Class<?>> findClassesWithAnnotation(
      String basePackage, Class<? extends Annotation> annotation) {
    Set<Class<?>> result = new HashSet<>();
    try {
      Set<Class<?>> classes = findClasses(basePackage);
      for (Class<?> clazz : classes) {
        if (clazz.isAnnotationPresent(annotation)) {
          result.add(clazz);
        }
      }
    } catch (Exception e) {
      logger.error("Error scanning for classes with annotation: {}", annotation.getName(), e);
    }
    return result;
  }

  /**
   * Find all classes in the specified package.
   *
   * @param basePackage the base package to scan
   * @return a set of all classes in the package
   */
  public static Set<Class<?>> findClasses(String basePackage) {
    Set<Class<?>> result = new HashSet<>();
    try {
      String path = basePackage.replace('.', '/');
      ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
      Enumeration<URL> resources = classLoader.getResources(path);

      while (resources.hasMoreElements()) {
        URL resource = resources.nextElement();
        String protocol = resource.getProtocol();

        if ("file".equals(protocol)) {
          result.addAll(findClassesInDirectory(new File(resource.getFile()), basePackage));
        } else if ("jar".equals(protocol)) {
          logger.warn("JAR scanning not implemented yet");
        }
      }
    } catch (IOException e) {
      logger.error("Error scanning for classes in package: {}", basePackage, e);
    }
    return result;
  }

  /**
   * Find classes in a directory.
   *
   * @param directory the directory to scan
   * @param packageName the package name
   * @return a set of classes in the directory
   */
  private static Set<Class<?>> findClassesInDirectory(File directory, String packageName) {
    Set<Class<?>> result = new HashSet<>();

    if (!directory.exists()) {
      return result;
    }

    File[] files = directory.listFiles();
    if (files == null) {
      return result;
    }

    for (File file : files) {
      if (file.isDirectory()) {
        result.addAll(findClassesInDirectory(file, packageName + "." + file.getName()));
      } else if (file.getName().endsWith(".class")) {
        try {
          String className =
              packageName + "." + file.getName().substring(0, file.getName().length() - 6);
          Class<?> clazz = Class.forName(className);
          result.add(clazz);
        } catch (ClassNotFoundException e) {
          logger.warn("Could not load class: {}", file.getName(), e);
        }
      }
    }

    return result;
  }

  /**
   * Get the ID value of an entity.
   *
   * @param entity the entity object
   * @return the ID value, or null if not found
   */
  public static Object getEntityId(Object entity) {
    try {
      for (java.lang.reflect.Field field : entity.getClass().getDeclaredFields()) {
        if (field.isAnnotationPresent(jakarta.persistence.Id.class)) {
          field.setAccessible(true);
          return field.get(entity);
        }
      }
    } catch (Exception e) {
      logger.error("Error getting entity ID", e);
    }
    return null;
  }

  /**
   * Set a field value on an object.
   *
   * @param object the object
   * @param fieldName the field name
   * @param value the value to set
   */
  public static void setFieldValue(Object object, String fieldName, Object value) {
    try {
      java.lang.reflect.Field field = object.getClass().getDeclaredField(fieldName);
      field.setAccessible(true);
      field.set(object, value);
    } catch (Exception e) {
      logger.error("Error setting field value", e);
    }
  }

  /**
   * Get a field value from an object.
   *
   * @param object the object
   * @param fieldName the field name
   * @return the field value
   */
  public static Object getFieldValue(Object object, String fieldName) {
    try {
      java.lang.reflect.Field field = object.getClass().getDeclaredField(fieldName);
      field.setAccessible(true);
      return field.get(object);
    } catch (Exception e) {
      logger.error("Error getting field value", e);
      return null;
    }
  }
}
