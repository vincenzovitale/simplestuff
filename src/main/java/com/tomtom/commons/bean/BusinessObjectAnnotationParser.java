/*
 * @(#)BusinessObjectAnnotationReader.java     6 Sep 2007
 *
 * Copyright (c) 2006 TomTom International B.V. All rights reserved.
 * TomTom PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.tomtom.commons.bean;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;
import org.springframework.util.ReflectionUtils.FieldFilter;

import com.tomtom.commons.annotation.BusinessField;

/**
 * Utility class which retrieves a &quot;business key&quot; from an object by inspecting
 * <code>BusinessField</code> annotations.
 * 
 * @author anph
 * @see com.tomtom.commons.annotation.BusinessObject
 * @see com.tomtom.commons.annotation.BusinessField
 * @since 6 Sep 2007
 *
 */
public class BusinessObjectAnnotationParser {

    /**
     * Retrieves a &quot;field-value&quot; map of all properties of the given object annotated as 
     * <code>BusinessField</code>s.
     * <p>
     * These should constitute a &quot;business key&quot; for this object
     * 
     * @param object    the object whose properties should be examined
     * @return          a map of all appropriately annotated fields
     */
    public static Map<String, Object> retrieveBusinessFields(Object object) {
        Map<String, Object> businessProperties = new HashMap<String, Object>();
        
        for (Field field : collectAnnotatedFields(object.getClass())) {
            String propertyName = field.getName();
            
            try {
                businessProperties.put(propertyName, 
                                       PropertyUtils.getProperty(object, propertyName));
            } catch (Exception exception) {

                // ignore any of the exceptions that can be thrown and simply skip the property
            }
            
        }
        
        return businessProperties;
    }
    
    private static Collection<Field> collectAnnotatedFields(Class clazz) {
        final List<Field> businessFields = new ArrayList<Field>();
        
        ReflectionUtils.doWithFields(clazz, new FieldCallback() {

                // simply add each found field to the list
                public void doWith(Field field) throws IllegalArgumentException, 
                        IllegalAccessException {
                    businessFields.add(field);
                } 
            
            }, new FieldFilter() {

                // match fields with the "@BusinessField" annotation
                public boolean matches(Field field) {
                    return (field.getAnnotation(BusinessField.class) != null);
                }
                
            });
        
        return businessFields;        
    }
    
}
