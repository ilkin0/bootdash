package com.ilkinmhd.bootdash.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** Mark a class as an entity to be managed by admin panel. */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AdminEntity {

  /**
   * The display name of the entity in singular form. If not specified, the class name will be used.
   */
  String name() default "";

  /**
   * The display name of the entity in singular form. If not specified, an "s" will be appended to
   * the singular name.
   */
  String pluralName() default "";

  /** A description of the entity to be displayed in the admin panel. */
  String description() default "";

  /**
   * Whether this entity is read-only in the admin panel. If true, the entity cannot be created,
   * updated, or deleted
   */
  boolean readOnly() default false;

  /**
   * The icon to use for this entity in the admin panel. This should correspond to an icon name in
   * the UI library.
   */
  String icon() default "";

  /** The group this entity belongs to in the admin panel sidebar. */
  String group() default "";

  /** The order of this entity within its group in the admin panel. Lower values appear first. */
  int order() default 100;
}
