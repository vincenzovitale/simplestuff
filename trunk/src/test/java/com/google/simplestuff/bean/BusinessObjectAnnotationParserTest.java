/*
 * @(#)BusinessObjectAnnotationReaderTest.java     14 Sep 2007
 *
 * Add license terms.
 *
 */
package com.google.simplestuff.bean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;

import com.google.code.simplestuff.annotation.BusinessField;
import com.google.code.simplestuff.bean.BusinessObjectAnnotationParser;

/**
 * A unit test for the <code>BusinessObjectAnnotationParser</code>.
 * 
 * @author anph
 * @see BusinessObjectAnnotationParser
 * @since 14 Sep 2007
 * 
 */
public class BusinessObjectAnnotationParserTest {

    @com.google.code.simplestuff.annotation.BusinessObject
    public class BusinessObject {

        @BusinessField
        private String property1;

        @BusinessField
        private int property2;

        /**
         * Getter for property1.
         * 
         * @return the property1.
         */
        public String getProperty1() {
            return property1;
        }

        /**
         * @param property1 the property1 to set
         */
        public void setProperty1(String property1) {
            this.property1 = property1;
        }

        /**
         * Getter for property2.
         * 
         * @return the property2.
         */
        public int getProperty2() {
            return property2;
        }

        /**
         * @param property2 the property2 to set
         */
        public void setProperty2(int property2) {
            this.property2 = property2;
        }

    }

    @Test
    public void retrieveBusinessFields() {
        Map<String, Object> businessKeyFields =
                BusinessObjectAnnotationParser
                        .retrieveBusinessFields(new BusinessObject());

        assertEquals(2, businessKeyFields.size());
        assertTrue(businessKeyFields.containsKey("property1"));
        assertTrue(businessKeyFields.containsKey("property2"));
    }

}
