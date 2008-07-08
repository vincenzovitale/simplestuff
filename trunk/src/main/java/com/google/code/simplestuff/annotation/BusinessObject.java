/*
 * @(#)BusinessObject.java     Apr 26, 2007
 *
 * TODO Add license.
 *
 */
package com.google.code.simplestuff.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that specifies if a bean is a business object.
 * 
 * @author Vincenzo Vitale (vincenzo.vitale)
 * @since Jul 08, 2008
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.TYPE })
public @interface BusinessObject {

    /**
     * Specify if the Class of a Business Object must be considered when doing
     * comparisons, thus being considered as a Business Field.
     * 
     * @return <code>true</code> if the object class is a business field.
     */
    boolean includeClassAsBusinessField() default true;

}
