package com.ilkinmhd.bootdash.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** Marks a method as a custom action that can be performed on entities from the admin panel. */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AdminAction {

  /** The display name of the action. */
  String name();

  /** A description of what the action does. */
  String description() default "";

  /**
   * Whether this action applies to individual entities or to the entire list. If true, the action
   * is shown for each entity in the list. If false, the action is shown as a bulk action for
   * selected entities.
   */
  boolean individual() default true;

  /** The icon to display for this action. */
  String icon() default "bolt";

  /**
   * The CSS class to apply to the action button. Options might include "primary", "secondary",
   * "danger", etc.
   */
  String buttonClass() default "secondary";

  /** Whether to prompt for confirmation before executing the action. */
  boolean requireConfirmation() default false;

  /** The confirmation message to display if requireConfirmation is true. */
  String confirmationMessage() default "Are you sure you want to perform this action?";
}
