/*
 * @(#)BusinessField.java     Apr 26, 2007
 *
 *TODO Add license.
 *
 */
package com.google.code.simplestuff.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that specifies if a field have a business meaning.
 * 
 * @author Vincenzo Vitale (vincenzo.vitale)
 * @since Jul 08, 2008
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface BusinessField {

    /**
     * Returns if this field has to be considered in the equals() method.
     * 
     * @return <code>true</code> if the field has to be considered when
     *         evaluating the equals().
     */
    boolean includeInEquals() default true;

    /**
     * Returns if this field has to be considered in the hashCode() method.
     * 
     * @return <code>true</code> if the field has to be considered when
     *         evaluating the hashCode().
     */
    boolean includeInHashCode() default true;

    /**
     * Returns if this field has to be considered in the toString() method.
     * 
     * @return <code>true</code> if the field has to be considered when
     *         evaluating the toString().
     */
    boolean includeInToString() default true;

}
