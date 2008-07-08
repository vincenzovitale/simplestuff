/*
 * @(#)BusinessObjectDescriptor.java     Jul 7, 2008
 *
 * TODO Add license terms.
 *
 */
package com.google.code.simplestuff.bean;

import java.lang.reflect.Field;
import java.util.Collection;

/**
 * Container maintaining information on a BusinessObject
 * 
 * @author Vincenzo Vitale (vita)
 * 
 */
public class BusinessObjectDescriptor {

    /**
     * <code>true</code> if the class has to be considered in the equals
     * comparison.
     */
    private Boolean classToBeConsideredInComparison;

    /**
     * The first annotated class in the object comparison.
     */
    private Class<? extends Object> nearestBusinessObjectClass;

    /**
     * The collection of BusinessField annotated fields.
     */
    private Collection<Field> annotatedFields;

    /**
     * @return the classToBeConsideredInComparison
     */
    public Boolean isClassToBeConsideredInComparison() {
        return classToBeConsideredInComparison;
    }

    /**
     * @param classToBeConsideredInComparison the
     *        classToBeConsideredInComparison to set
     */
    public void setClassToBeConsideredInComparison(
            Boolean classToBeConsideredInComparison) {
        this.classToBeConsideredInComparison = classToBeConsideredInComparison;
    }

    /**
     * @return the nearestBusinessObjectClass
     */
    public Class<? extends Object> getNearestBusinessObjectClass() {
        return nearestBusinessObjectClass;
    }

    /**
     * @param nearestBusinessObjectClass the nearestBusinessObjectClass to set
     */
    public void setNearestBusinessObjectClass(
            Class<? extends Object> nearestBusinessObjectClass) {
        this.nearestBusinessObjectClass = nearestBusinessObjectClass;
    }

    /**
     * @return the annotatedFields
     */
    public Collection<Field> getAnnotatedFields() {
        return annotatedFields;
    }

    /**
     * @param annotatedFields the annotatedFields to set
     */
    public void setAnnotatedFields(Collection<Field> annotatedFields) {
        this.annotatedFields = annotatedFields;
    }
}
