package com.ilkinmhd.bootdash.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** Configures how a field is displayed and managed in the admin panel. */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AdminField {

  /** The display name of the field. If not specified, the field name will be used. */
  String name() default "";

  /** A description of the field to be displayed in the admin panel. */
  String description() default "";

  /** Whether this field is editable in forms. */
  boolean editable() default true;

  /** Whether this field is visible in the admin panel. */
  boolean visible() default true;

  /** The order of this field in forms and list views. Lower values appear first. */
  int order() default 100;

  /**
   * The widget type to use for this field. Options include: "text", "number", "date", "datetime",
   * "boolean", "select", "multiselect", "textarea", "password", "email", "url", etc. If not
   * specified, a suitable widget will be chosen based on the field type.
   */
  String widget() default "";

  /** Whether this field should be displayed in list views. */
  boolean inList() default true;

  /** Whether this field is required in forms. */
  boolean required() default false;

  /** A validation pattern (regular expression) for this field. */
  String pattern() default "";

  /** Custom validation message to display if validation fails. */
  String validationMessage() default "";
}
