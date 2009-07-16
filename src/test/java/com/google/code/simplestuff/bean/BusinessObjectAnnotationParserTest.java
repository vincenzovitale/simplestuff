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
package com.google.code.simplestuff.bean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;

import com.google.code.simplestuff.annotation.BusinessField;

/**
 * A unit test for the <code>BusinessObjectAnnotationParser</code>.
 * 
 * @author anph
 * @see BusinessObjectAnnotationParser
 * @since 14 Sep 2007
 * 
 */
@RunWith(JUnit4ClassRunner.class)
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
