/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.simplestuff.bean;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.SerializationUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;

import com.google.code.simplestuff.annotation.BusinessField;
import com.google.code.simplestuff.annotation.BusinessObject;
import com.google.code.simplestuff.bean.AbstractBusinessObject;
import com.google.code.simplestuff.bean.SimpleBean;

/**
 * TestCase class that tests the {@link SimpleBean} class. This test implements
 * {@link Serializable} just for be able to use the {@link SerializationUtils}
 * class with the nested classes defined.
 * 
 * 
 * @author Vincenzo Vitale (vincenzo.vitale)
 * @since Jul 08, 2008 TODO: Write the tests for all the different strangest
 *        (i.e. passing not directly business object beans). TODO Use Junit 4
 * 
 */
@SuppressWarnings("serial")
@RunWith(JUnit4ClassRunner.class)
public class SimpleBeanTest implements Serializable {

    static ParentClass testObjectOne;

    static ParentClass testObjectTwo;

    // @BeforeClass
    // public static void beforeClass(){
    // LogFactory.getLog(SimpleBean.class).
    // }
    
    @Before
    public void setUp() throws Exception {

        testObjectOne = new ParentClass();
        testObjectOne.setBooleanField(true);
        testObjectOne.setBooleanPrimitiveField(true);
        testObjectOne.setStringField("Test String");
        testObjectOne
                .setStringArrayField(new String[] { "One", "Two", "Three" });
        testObjectOne.setDateField(new Date());
        testObjectOne.setChilds(new HashSet<ChildClass>());
        testObjectOne.getChilds().add(
                new ChildClass("Child One String field", testObjectOne));
        testObjectOne.getChilds().add(
                new ChildClass("Child Two String field", testObjectOne));

        testObjectTwo = (ParentClass) SerializationUtils.clone(testObjectOne);
    }

    /**
     * Test method for
     * {@link com.tomtom.commons.bean.SimpleBean#equals(java.lang.Object, java.lang.Object)}
     * .
     */
    @Test
    public void testEquals() {
        assertTrue(testObjectOne.equals(testObjectTwo));

    }

    /**
     * Test method for
     * {@link com.tomtom.commons.bean.SimpleBean#equals(java.lang.Object, java.lang.Object)}
     * .
     */
    @Test
    public void testEqualsBetweenAppleAndTable() {
        assertTrue((new Apple()).equals(new Table()));
    }

    /**
     * Test method for
     * {@link com.tomtom.commons.bean.SimpleBean#equals(java.lang.Object, java.lang.Object)}
     * .
     */
    @Test
    public void testNotEqualsBetweenAppleAndChair() {
        assertFalse((new Apple()).equals(new Chair()));
    }

    /**
     * Test method for
     * {@link com.tomtom.commons.bean.SimpleBean#equals(java.lang.Object, java.lang.Object)}
     * .
     */
    @Test
    public void testNotEqualsBetweenTableAndChair() {
        assertFalse((new Table()).equals(new Chair()));
    }

    /**
     * Test method for
     * {@link com.tomtom.commons.bean.SimpleBean#hashCode(java.lang.Object)}.
     */
    @Test
    public void testHashCode() {
        assertEquals(testObjectOne.hashCode(), testObjectTwo.hashCode());
    }

    /**
     * Test method for
     * {@link com.tomtom.commons.bean.SimpleBean#toString(java.lang.Object)}.
     */
    @Test
    public void testToString() {
        assertEquals(replacePattern(testObjectOne.toString(),
                "@\\p{Alnum}+\\[", "["), replacePattern(testObjectTwo
                .toString(), "@\\p{Alnum}+\\[", "["));
    }

    /**
     * Test that the StackOverflow error is fixed when a proper getter is not
     * defined.
     */
    @Test
    public void testStackOverflowProblemFixed() {
        NoSuchMethodExceptionBean firstBean = new NoSuchMethodExceptionBean();

        firstBean.toString();

    }

    /**
     * Utility method for replace a pattern in a String.
     * 
     * @param source the source string.
     * @param pattern the pattern to match.
     * @param replacement The replacement.
     * @return the result string.
     */
    private String replacePattern(String source, String pattern,
            String replacement) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(source);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, replacement);
        }
        m.appendTail(sb);

        return sb.toString();
    }

    /**
     * Test method for
     * {@link com.tomtom.commons.bean.SimpleBean#TestBean(Class)}.
     */
    @Test
    public void testGetTestBean() {
        ParentClass testBean = SimpleBean.getTestBean(ParentClass.class, null);
        assertEquals(true, testBean.isBooleanPrimitiveField());
        assertEquals(true, testBean.getBooleanField().booleanValue());
        assertEquals(10, testBean.getIntPrimitiveField());
        assertEquals(10, testBean.getIntField().intValue());
        assertEquals('t', testBean.getCharPrimitiveField());
        assertEquals('t', testBean.getCharField().charValue());
        assertEquals(10, testBean.getLongPrimitiveField());
        assertEquals(10.0, testBean.getLongField().doubleValue());
        assertEquals(10.0F, testBean.getFloatPrimitiveField());
        assertEquals(10.0F, testBean.getFloatField().floatValue());
        assertEquals(10, testBean.getBytePrimitiveField());
        assertEquals(10, testBean.getByteField().byteValue());

        assertEquals("testStringField", testBean.getStringField());

    }

    /**
     * Parent class for testing the {@link SimpleBean}.
     * 
     * @author Vincenzo Vitale (vita)
     * @since Apr 26, 2007
     * 
     */
    public static class ParentClass extends AbstractBusinessObject implements
            Serializable {

        @BusinessField
        Boolean booleanField;

        @BusinessField
        boolean booleanPrimitiveField;

        @BusinessField
        int intPrimitiveField;

        @BusinessField
        Integer intField;

        @BusinessField
        char charPrimitiveField;

        @BusinessField
        Character charField;

        @BusinessField
        long longPrimitiveField;

        @BusinessField
        Long longField;

        @BusinessField
        float floatPrimitiveField;

        @BusinessField
        Float floatField;

        @BusinessField
        byte bytePrimitiveField;

        @BusinessField
        Byte byteField;

        @BusinessField
        String stringField;

        @BusinessField
        String[] stringArrayField;

        @BusinessField
        Date dateField;

        @BusinessField
        Set<ChildClass> childs;

        public Boolean getBooleanField() {
            return booleanField;
        }

        public void setBooleanField(Boolean booleanField) {
            this.booleanField = booleanField;
        }

        public boolean isBooleanPrimitiveField() {
            return booleanPrimitiveField;
        }

        public void setBooleanPrimitiveField(boolean booleanPrimitiveField) {
            this.booleanPrimitiveField = booleanPrimitiveField;
        }

        public Set<ChildClass> getChilds() {
            return childs;
        }

        public void setChilds(Set<ChildClass> childs) {
            this.childs = childs;
        }

        public String[] getStringArrayField() {
            return stringArrayField;
        }

        public void setStringArrayField(String[] stringArrayField) {
            this.stringArrayField = stringArrayField;
        }

        public String getStringField() {
            return stringField;
        }

        public void setStringField(String stringField) {
            this.stringField = stringField;
        }

        public Date getDateField() {
            return dateField;
        }

        public void setDateField(Date dateField) {
            this.dateField = dateField;
        }

        public Long getLongField() {
            return longField;
        }

        public void setLongField(Long longField) {
            this.longField = longField;
        }

        public long getLongPrimitiveField() {
            return longPrimitiveField;
        }

        public void setLongPrimitiveField(long longPrimitiveField) {
            this.longPrimitiveField = longPrimitiveField;
        }

        public int getIntPrimitiveField() {
            return intPrimitiveField;
        }

        public void setIntPrimitiveField(int intPrimitiveField) {
            this.intPrimitiveField = intPrimitiveField;
        }

        public Integer getIntField() {
            return intField;
        }

        public void setIntField(Integer intField) {
            this.intField = intField;
        }

        public char getCharPrimitiveField() {
            return charPrimitiveField;
        }

        public void setCharPrimitiveField(char charPrimitiveField) {
            this.charPrimitiveField = charPrimitiveField;
        }

        public Character getCharField() {
            return charField;
        }

        public void setCharField(Character charField) {
            this.charField = charField;
        }

        public float getFloatPrimitiveField() {
            return floatPrimitiveField;
        }

        public void setFloatPrimitiveField(float floatPrimitiveField) {
            this.floatPrimitiveField = floatPrimitiveField;
        }

        public Float getFloatField() {
            return floatField;
        }

        public void setFloatField(Float floatField) {
            this.floatField = floatField;
        }

        public byte getBytePrimitiveField() {
            return bytePrimitiveField;
        }

        public void setBytePrimitiveField(byte bytePrimitiveField) {
            this.bytePrimitiveField = bytePrimitiveField;
        }

        public Byte getByteField() {
            return byteField;
        }

        public void setByteField(Byte byteField) {
            this.byteField = byteField;
        }

    }

    /**
     * Child class for testing the {@link SimpleBean}.
     * 
     * @author Vincenzo Vitale (vita)
     * @since Apr 26, 2007
     * 
     */
    public static class ChildClass extends AbstractBusinessObject implements
            Serializable {

        @BusinessField
        String stringField;

        ParentClass parent;

        public ParentClass getParent() {
            return parent;
        }

        public void setParent(ParentClass parent) {
            this.parent = parent;
        }

        /**
         * @param stringField
         * @param parent
         */
        public ChildClass(String stringField, ParentClass parent) {
            super();
            this.stringField = stringField;
            this.parent = parent;
        }

        public String getStringField() {
            return stringField;
        }

        public void setStringField(String stringField) {
            this.stringField = stringField;
        }

    }

    /**
     * Apple class for testing the {@link SimpleBean} and the
     * includeClassAsBusinessField behaviour.
     * 
     * @author Vincenzo Vitale (vita)
     * @since Apr 26, 2007
     * 
     */
    @BusinessObject(includeClassAsBusinessField = false)
    public static class Apple extends AbstractBusinessObject implements
            Serializable {
        @BusinessField
        String color = "red";

        /**
         * @return the color
         */
        public String getColor() {
            return color;
        }

        /**
         * @param color the color to set
         */
        public void setColor(String color) {
            this.color = color;
        }

    }

    /**
     * Apple class for testing the {@link SimpleBean} and the
     * includeClassAsBusinessField behavior.
     * 
     * @author Vincenzo Vitale (vita)
     * @since Apr 26, 2007
     * 
     */
    @BusinessObject(includeClassAsBusinessField = false)
    public static class Table extends AbstractBusinessObject implements
            Serializable {
        @BusinessField
        private String color = "red";

        /**
         * @return the color
         */
        public String getColor() {
            return color;
        }

        /**
         * @param color the color to set
         */
        public void setColor(String color) {
            this.color = color;
        }

    }

    /**
     * Apple class for testing the {@link SimpleBean} and the
     * includeClassAsBusinessField behavior.
     * 
     * @author Vincenzo Vitale (vita)
     * @since Apr 26, 2007
     * 
     */
    @BusinessObject(includeClassAsBusinessField = true)
    public static class Chair extends AbstractBusinessObject implements
            Serializable {
        @BusinessField
        private String color = "red";

        /**
         * @return the color
         */
        public String getColor() {
            return color;
        }

        /**
         * @param color the color to set
         */
        public void setColor(String color) {
            this.color = color;
        }
    }

    /**
     * This bena should generate a {@link NoSuchMethodException} in ternally in
     * Simplestuff. There was a bug in the toString method when this was
     * happening generating a {@link StackOverflowError}. This test tests it was
     * fixed.
     * 
     * @author Vincenzo Vitale (vita)
     * @since Apr 26, 2007
     * 
     */
    public static class NoSuchMethodExceptionBean extends
            AbstractBusinessObject implements Serializable {
        @BusinessField
        private String color = "red";

        /**
         * @return the color
         */
        public String getColorWrongGetter() {
            return color;
        }

        /**
         * @param color the color to set
         */
        public void setColor(String color) {
            this.color = color;
        }
    }

}
