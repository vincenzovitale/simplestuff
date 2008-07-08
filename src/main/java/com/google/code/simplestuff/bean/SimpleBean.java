/*
 * @(#)SimpleBean.java     Apr 26, 2007
 *
 * TODO Add license
 *
 */
package com.google.code.simplestuff.bean;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;
import org.springframework.util.ReflectionUtils.FieldFilter;

import com.google.code.simplestuff.annotation.BusinessField;
import com.google.code.simplestuff.annotation.BusinessObject;

/**
 * 
 * Utility class for Java bean enhancement.
 * 
 * @author Vincenzo Vitale (vincenzo.vitale)
 * @since Jul 08, 2008
 * 
 * @deprecated To be fixed after the code review... If you are seeing this
 *             please use another version.
 */
// TODO: Class is too big, should be broken up into functional parts. For
// example,
// getting BusinessObjectDescriptor and actually processing it (during equals,
// hashCode etc.) doesn't need to be in the same class.
public class SimpleBean {

    /** Logger for this class */
    private static Log log = LogFactory.getLog(SimpleBean.class);

    /**
     * Compare two bean basing the comparison only on the {@link BusinessField}
     * annotated fields.
     * 
     * @param firstBean First bean to compare.
     * @param secondBean Second bean to compare.
     * @return The equals result.
     * @throws IllegalArgumentException If one of the beans compared is not an
     *         instance of a {@link BusinessObject} annotated class.
     * @deprecated To be fixed after the code review... If you are seeing this
     *             please use another version.
     */
    public static boolean equals(Object firstBean, Object secondBean) {

        // null + !null = false
        // null + null = true
        if ((firstBean == null) || (secondBean == null)) {
            if ((firstBean == null) && (secondBean == null)) {
                return true;
            } else {
                return false;
            }
        }

        final BusinessObjectDescriptor firstBusinessObjectInfo =
                getBusinessObjectInfo(firstBean.getClass());
        final BusinessObjectDescriptor secondBusinessObjectInfo =
                getBusinessObjectInfo(secondBean.getClass());

        if ((firstBusinessObjectInfo == null)
                || (secondBusinessObjectInfo == null)) {
            throw new IllegalArgumentException(
                    "One or both of the beans to compare are not annotated in their hierarchy as Business Object!!!");
        }

        // If one of the two bean has the class with Business relevance then
        // we need to compare the lowest hierarchical annotated classes of
        // the two beans.
        if ((firstBusinessObjectInfo.isClassToBeConsideredInComparison() || secondBusinessObjectInfo
                .isClassToBeConsideredInComparison())
                && (!firstBusinessObjectInfo.getNearestBusinessObjectClass()
                        .equals(
                                secondBusinessObjectInfo
                                        .getNearestBusinessObjectClass()))) {
            // If the comparison fails than we can already return false.
            return false;
        }

        // Then we continue with the annotated fields, first checking
        // if the two objects contain the same annotated fields.
        if (!compareAnnotatedFields(firstBusinessObjectInfo
                .getAnnotatedFields(), secondBusinessObjectInfo
                .getAnnotatedFields())) {
            // If the comparison fails than we can already return false.
            return false;
        }

        // Then we continue with the values of the annotated fields.
        Collection<Field> firstBeanAnnotatedFields =
                firstBusinessObjectInfo.getAnnotatedFields();

        for (Field field : firstBeanAnnotatedFields) {

            final BusinessField fieldAnnotation =
                    field.getAnnotation(BusinessField.class);
            // Since the cycle is on the annotated Field we are sure that
            // fieldAnnotation will always be not null.
            if (fieldAnnotation.includeInEquals()) {

                Object firstBeanFieldValue = null;
                Object secondBeanFieldValue = null;

                try {
                    firstBeanFieldValue =
                            PropertyUtils.getProperty(firstBean, field
                                    .getName());
                    // Also in this case, since before of the cycle we
                    // compare the annotated fields, we can be sure that the
                    // field exists.
                    secondBeanFieldValue =
                            PropertyUtils.getProperty(secondBean, field
                                    .getName());

                    // If there were problems (like when we compare
                    // different Business Object with different Business
                    // Fields), then we return false.
                } catch (IllegalAccessException e) {
                    if (log.isDebugEnabled()) {
                        log
                                .debug(
                                        "IllegalAccessException exception when comparing "
                                                + firstBean + " with  "
                                                + secondBean, e);
                    }
                    return false;
                } catch (InvocationTargetException e) {
                    if (log.isDebugEnabled()) {
                        log
                                .debug(
                                        "InvocationTargetException exception when comparing "
                                                + firstBean + " with  "
                                                + secondBean, e);
                    }
                    return false;
                } catch (NoSuchMethodException e) {
                    if (log.isDebugEnabled()) {
                        log
                                .debug(
                                        "NoSuchMethodException exception when comparing "
                                                + firstBean + " with  "
                                                + secondBean, e);
                    }
                    return false;
                }

                // Some date implementations give not exact
                // comparison...
                if ((ClassUtils.isAssignable(field.getType(), Date.class))
                        || (ClassUtils.isAssignable(field.getType(),
                                Calendar.class))) {

                    if (firstBeanFieldValue != null) {
                        firstBeanFieldValue =
                                DateUtils.round(firstBeanFieldValue,
                                        Calendar.MILLISECOND);
                    }

                    if (secondBeanFieldValue != null) {
                        secondBeanFieldValue =
                                DateUtils.round(secondBeanFieldValue,
                                        Calendar.MILLISECOND);
                    }

                }

                // We use always EqualsBuilder since we can get also
                // primitive arrays and they need ot be internally
                // compared.
                EqualsBuilder equalsBuilder = new EqualsBuilder();
                equalsBuilder.append(firstBeanFieldValue, secondBeanFieldValue);
                if (!equalsBuilder.isEquals()) {
                    return false;
                } else {

                    // If we are here the bean are both not null and
                    // equals or both null (then equals)... the cycle
                    // can
                    // continue...
                    continue;
                }

            }
        }

        // If we finally arrive here, then all the comparison were
        // successful and the two beans are equals.
        return true;

    }

    /**
     * Returns true if the two field collections are equals, basing the
     * comparison only on the name and not on the class.
     * 
     * @param annotatedFields
     * @param annotatedFields2
     * @return
     */
    private static boolean compareAnnotatedFields(
            Collection<Field> annotatedFields,
            Collection<Field> annotatedFields2) {
        // TODO To complete.

        return true;
    }

    /**
     * 
     * Returns the hashCode basing considering only the {@link BusinessField}
     * annotated fields.
     * 
     * @param bean The bean.
     * @return The hashCode result.
     * @throws IllegalArgumentException If the bean is not a Business Object.
     * @deprecated To be fixed after the code review... If you are seeing this
     *             please use another version.
     */
    public static int hashCode(Object bean) {

        if (bean == null) {
            throw new IllegalArgumentException("The bean passed is null!!!");
        }

        BusinessObjectDescriptor businessObjectInfo =
                getBusinessObjectInfo(bean.getClass());

        if (businessObjectInfo == null) {
            throw new IllegalArgumentException(
                    "The bean passed is not annotated in the hierarchy as Business Object!!!");
        }

        Collection<Field> annotatedField = getAnnotatedFields(bean.getClass());

        HashCodeBuilder builder = new HashCodeBuilder();
        for (Field field : annotatedField) {
            field.setAccessible(true);
            final BusinessField fieldAnnotation =
                    field.getAnnotation(BusinessField.class);
            if ((fieldAnnotation != null)
                    && (fieldAnnotation.includeInHashCode())) {
                try {
                    // Vincenzo Vitale(vita) May 23, 2007 2:39:26 PM: some
                    // date implementations give not equals values...
                    if ((ClassUtils.isAssignable(field.getType(), Date.class))
                            || (ClassUtils.isAssignable(field.getType(),
                                    Calendar.class))) {
                        Object fieldValue =
                                PropertyUtils
                                        .getProperty(bean, field.getName());
                        if (fieldValue != null) {
                            builder.append(DateUtils.round(fieldValue,
                                    Calendar.SECOND));
                        }

                    } else {
                        builder.append(PropertyUtils.getProperty(bean, field
                                .getName()));
                    }
                } catch (IllegalAccessException e) {
                    if (log.isDebugEnabled()) {
                        log.info(
                                "IllegalAccessException exception when calculating the hashcode of "
                                        + bean, e);
                    }
                } catch (InvocationTargetException e) {
                    if (log.isDebugEnabled()) {
                        log.debug(
                                "InvocationTargetException exception when calculating the hashcode of "
                                        + bean, e);
                    }
                } catch (NoSuchMethodException e) {
                    if (log.isDebugEnabled()) {
                        log.debug(
                                "NoSuchMethodException exception when calculating the hashcode of "
                                        + bean, e);
                    }
                }
            }
        }

        return builder.toHashCode();

    }

    /**
     * 
     * Returns the description of a bean considering only the
     * {@link BusinessField} annotated fields.
     * 
     * @param bean The bean to describe.
     * @return The description of the bean.
     * @throws IllegalArgumentException If the bean is not a Business Object.
     */
    public static String toString(Object bean) {

        if (bean == null) {
            throw new IllegalArgumentException("The bean passed is null!!!");
        }

        BusinessObjectDescriptor businessObjectInfo =
                getBusinessObjectInfo(bean.getClass());

        if (businessObjectInfo == null) {
            throw new IllegalArgumentException(
                    "The bean passed is not annotated in the hierarchy as Business Object!!!");
        }

        Collection<Field> annotatedField = getAnnotatedFields(bean.getClass());
        ToStringBuilder builder =
                new ToStringBuilder(bean, ToStringStyle.MULTI_LINE_STYLE);
        for (Field field : annotatedField) {
            final BusinessField fieldAnnotation =
                    field.getAnnotation(BusinessField.class);
            if ((fieldAnnotation != null)
                    && (fieldAnnotation.includeInToString())) {
                try {
                    // Vincenzo Vitale(vita) May 23, 2007 2:39:26 PM: some
                    // date implementations give not equals values...
                    if ((ClassUtils.isAssignable(field.getType(), Date.class))
                            || (ClassUtils.isAssignable(field.getType(),
                                    Calendar.class))) {
                        Object fieldValue =
                                PropertyUtils
                                        .getProperty(bean, field.getName());
                        if (fieldValue != null) {
                            builder.append(DateUtils.round(fieldValue,
                                    Calendar.SECOND));
                        }

                    } else {
                        builder.append(PropertyUtils.getProperty(bean, field
                                .getName()));
                    }
                } catch (IllegalAccessException e) {
                    if (log.isDebugEnabled()) {
                        log
                                .debug(
                                        "IllegalAccessException exception when calculating the string representation of "
                                                + bean, e);
                    }
                } catch (InvocationTargetException e) {
                    if (log.isDebugEnabled()) {
                        log
                                .debug(
                                        "InvocationTargetException exception when calculating the string representation of "
                                                + bean, e);
                    }
                } catch (NoSuchMethodException e) {
                    if (log.isDebugEnabled()) {
                        log
                                .debug(
                                        "NoSuchMethodException exception when calculating the string representation of "
                                                + bean, e);
                    }
                }
            }
        }

        return builder.toString();

    }

    /**
     * 
     * Returns a test object with all the {@link BusinessField} annotated fields
     * set to a test value. TODO At the moment only the String field are
     * considered and the collection are not considered.
     * 
     * @param bean The class of the bean to fill.
     * @param suffix The suffix to append in the string field.
     * @return The bean with test values.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getTestBean(Class<T> beanClass, String suffix) {
        if (beanClass == null) {
            throw new IllegalArgumentException(
                    "The bean class passed is null!!!");
        }

        T testBean = null;
        try {
            testBean = beanClass.newInstance();
        } catch (InstantiationException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IllegalAccessException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        BusinessObjectDescriptor businessObjectInfo =
                getBusinessObjectInfo(beanClass);

        if (businessObjectInfo != null) {

            Collection<Field> annotatedField = getAnnotatedFields(beanClass);
            for (Field field : annotatedField) {
                final BusinessField fieldAnnotation =
                        field.getAnnotation(BusinessField.class);
                if (fieldAnnotation != null) {
                    try {
                        if (field.getType().equals(String.class)) {
                            String stringValue =
                                    "test"
                                            + StringUtils.capitalize(field
                                                    .getName())
                                            + (suffix == null ? "" : suffix);
                            PropertyUtils.setProperty(testBean,
                                    field.getName(), stringValue);

                        } else if ((field.getType().equals(boolean.class))
                                || (field.getType().equals(Boolean.class))) {
                            PropertyUtils.setProperty(testBean,
                                    field.getName(), true);
                        } else if ((field.getType().equals(int.class))
                                || (field.getType().equals(Integer.class))) {
                            PropertyUtils.setProperty(testBean,
                                    field.getName(), 10);
                        } else if ((field.getType().equals(char.class))
                                || (field.getType().equals(Character.class))) {
                            PropertyUtils.setProperty(testBean,
                                    field.getName(), 't');
                        } else if ((field.getType().equals(long.class))
                                || (field.getType().equals(Long.class))) {
                            PropertyUtils.setProperty(testBean,
                                    field.getName(), 10L);
                        } else if ((field.getType().equals(float.class))
                                || (field.getType().equals(Float.class))) {
                            PropertyUtils.setProperty(testBean,
                                    field.getName(), 10F);
                        } else if ((field.getType().equals(byte.class))
                                || (field.getType().equals(Byte.class))) {
                            PropertyUtils.setProperty(testBean,
                                    field.getName(), (byte) 10);
                        } else if (field.getType().equals(Date.class)) {
                            PropertyUtils.setProperty(testBean,
                                    field.getName(), new Date());
                        } else if (field.getType().equals(Collection.class)) {
                            // TODO: create a test object of the collection
                            // class specified (if one is specified and
                            // recursively call this method.
                        }

                    } catch (IllegalAccessException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }

        return testBean;
    }

    /**
     * Retrieve a {@link BusinessObjectDescriptor} object for a
     * {@link BusinessObject} class bean.
     * 
     * @param objectClass The class of the bean to check.
     * @return A {@link BusinessObjectDescriptor} for the bean class passed or
     *         null if the bean class is not a {@link BusinessObject} according
     *         to its hierarchy.
     */
    private static BusinessObjectDescriptor getBusinessObjectInfo(
            Class<? extends Object> objectClass) {
        return getBusinessObjectInfo(objectClass, objectClass);
    }

    /**
     * Retrieve a {@link BusinessObjectDescriptor} object for a
     * {@link BusinessObject} class bean.
     * 
     * @param objectClass The class of the bean to check.
     * @param annotationObjectClass The class to use for retrieving the
     *        {@link BusinessField} annotated field.
     * @return A {@link BusinessObjectDescriptor} for the bean class passed or
     *         null if the bean class is not a {@link BusinessObject} according
     *         to its hierarchy.
     */
    private static BusinessObjectDescriptor getBusinessObjectInfo(
            Class<? extends Object> objectClass,
            Class<? extends Object> annotationObjectClass) {
        if (objectClass == null) {
            return null;
        } else {
            if (objectClass.isAnnotationPresent(BusinessObject.class)) {
                BusinessObjectDescriptor businessObjectInfo =
                        new BusinessObjectDescriptor();
                businessObjectInfo
                        .setAnnotatedFields(getAnnotatedFields(annotationObjectClass));
                businessObjectInfo
                        .setClassToBeConsideredInComparison(objectClass
                                .getAnnotation(BusinessObject.class)
                                .includeClassAsBusinessField());
                businessObjectInfo.setNearestBusinessObjectClass(objectClass);
                return businessObjectInfo;
            } else {
                return getBusinessObjectInfo(objectClass.getSuperclass(),
                        annotationObjectClass);
            }

        }
    }

    /**
     * Returns an array of all fields used by this object from it's class and
     * all super classes.
     * 
     * @author Andrew Phillips (anph)
     * @param objectClass the class
     * @param fields the current field list
     * @return an array of fields
     * 
     */
    private static Collection<Field> getAnnotatedFields(Class objectClass) {
        final List<Field> businessFields = new ArrayList<Field>();

        ReflectionUtils.doWithFields(objectClass, new FieldCallback() {

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
