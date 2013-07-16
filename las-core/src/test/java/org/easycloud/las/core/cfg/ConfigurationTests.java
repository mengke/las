/*
 * Copyright 2013 Ke Meng (mengke@icloud.com)
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.easycloud.las.core.cfg;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.testng.Assert.assertEquals;

/**
 * Created by IntelliJ IDEA.
 * User: Meng, Ke
 * Date: 13-2-28
 */
public class ConfigurationTests {

    private static final String CONF_NAME = "resourcesTests.properties";

    private Configuration conf;

    @BeforeClass
    public void setUp() {
        conf = new Configuration() {
            // nothing
        };
    }

    @AfterTest
    public void afterTest() {
        conf.setResourceName(null);
    }

    @Test
    public void testLoadByName() {
        conf.setResourceName(CONF_NAME);
        String value1 = conf.get("test1");
        assertEquals(value1, "value1");
    }

    @Test
    public void testGet() {
        conf.setResourceName(CONF_NAME);
        String value1 = conf.get("test1");
        assertEquals(value1, "value1");
        String value2 = conf.get("test2");
        assertEquals(value2, "value2 ");
    }

    @Test
    public void testGetTrimmed() {
        conf.setResourceName(CONF_NAME);
        String value1 = conf.getTrimmed("test1");
        assertEquals(value1, "value1");
        String value2 = conf.getTrimmed("test2");
        assertEquals(value2, "value2");
    }

    @Test
    public void testGetDefaults() {
        conf.setResourceName(CONF_NAME);
        String value1 = conf.get("test1", "value2");
        assertEquals(value1, "value1");
        String valueDefaults = conf.get("testDefaults", "defaultValue");
        assertEquals(valueDefaults, "defaultValue");
    }

    @Test
    public void testGetInt() {
        conf.setResourceName(CONF_NAME);
        int intValue = conf.getInt("testInt", 0);
        assertEquals(intValue, 47);
        int hexValue = conf.getInt("testHex", 0);
        assertEquals(hexValue, 175);
        int defaultValue = conf.getInt("testIntDefaults", 13);
        assertEquals(defaultValue, 13);
    }

    @Test
    public void testGetStringCollection() {
        conf.setResourceName(CONF_NAME);
        Collection<String> actualValue = conf.getStringCollection("testStrings");
        List<String> expectedValue = new ArrayList<String>();
        expectedValue.add("string1 ");
        expectedValue.add("string2 ");
        expectedValue.add("string3 ");
        assertEquals(actualValue, expectedValue);
    }

    @Test
    public void testGetStrings() {
        conf.setResourceName(CONF_NAME);
        String[] actualValue = conf.getStrings("testStrings");
        String[] expectedValue = new String[]{"string1 ", "string2 ", "string3 "};
        assertEquals(actualValue, expectedValue);
    }

    @Test
    public void testGetStringsDefaults() {
        conf.setResourceName(CONF_NAME);
        String[] expectedValue = new String[]{"string2", "string4", "string3"};
        String[] actualValue = conf.getStrings("testStringsDefaults", expectedValue);
        assertEquals(actualValue, expectedValue);
    }

    @Test
    public void testGetTrimmedStringCollection() {
        conf.setResourceName(CONF_NAME);
        Collection<String> actualValue = conf.getTrimmedStringCollection("testStrings");
        List<String> expectedValue = new ArrayList<String>();
        expectedValue.add("string1");
        expectedValue.add("string2");
        expectedValue.add("string3");
        assertEquals(actualValue, expectedValue);
    }

    @Test
    public void testGetTrimmedStrings() {
        conf.setResourceName(CONF_NAME);
        String[] actualValue = conf.getTrimmedStrings("testStrings");
        String[] expectedValue = new String[]{"string1", "string2", "string3"};
        assertEquals(actualValue, expectedValue);
    }

    @Test
    public void testGetTrimmedStringsDefaults() {
        conf.setResourceName(CONF_NAME);
        String[] defaultStrings = new String[]{"string2", "string4", "string3"};
        String[] actualValue = conf.getTrimmedStrings("testStringsDefaults", defaultStrings);
        assertEquals(actualValue, defaultStrings);
    }

    @Test
    public void testGetInts() {
        conf.setResourceName(CONF_NAME);
        int[] expectedValue = new int[]{2, 1, 3};
        int[] actualValue = conf.getInts("testInts");
        assertEquals(actualValue, expectedValue);
    }

    @Test
    public void testGetLong() {
        conf.setResourceName(CONF_NAME);
        long defaultValue = 0l;
        long expectedValue = 2l;
        long actualValue = conf.getLong("testLong", defaultValue);
        assertEquals(actualValue, expectedValue);
        actualValue = conf.getLong("testLongDefaults", defaultValue);
        assertEquals(actualValue, defaultValue);
    }

    @Test
    public void testGetFloat() {
        conf.setResourceName(CONF_NAME);
        float defaultValue = 0f;
        float expectedValue = 3.1415926f;
        float actualValue = conf.getFloat("testFloat", defaultValue);
        assertEquals(actualValue, expectedValue);
        actualValue = conf.getFloat("testFloatDefaults", defaultValue);
        assertEquals(actualValue, defaultValue);
    }

    @Test
    public void testGetDouble() {
        conf.setResourceName(CONF_NAME);
        double defaultValue = 0;
        double expectedValue = 12.345678;
        double actualValue = conf.getDouble("testDouble", defaultValue);
        assertEquals(actualValue, expectedValue);
        actualValue = conf.getDouble("testDoubleDefaults", defaultValue);
        assertEquals(actualValue, defaultValue);
    }

    @Test
    public void testGetBoolean() {
        conf.setResourceName(CONF_NAME);
        boolean defaultValue = false;
        boolean expectedValue = true;
        boolean actualValue = conf.getBoolean("testBoolean", defaultValue);
        assertEquals(actualValue, expectedValue);
        actualValue = conf.getBoolean("testBooleanDefaults", defaultValue);
        assertEquals(actualValue, defaultValue);
    }

    @Test
    public void testSize() {
        conf.setResourceName(CONF_NAME);
        assertEquals(conf.size(), 11);
    }
}
