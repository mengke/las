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

package org.easycloud.las.core.util;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.easycloud.las.core.util.Assert.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertSame;

/**
 * Created by IntelliJ IDEA.
 * User: Meng, Ke
 * Date: 13-5-10
 */
public class AssertTests {

    final String testMessage = "any test text";
    final Throwable testCause = new IllegalStateException(); // any exception
    final ArrayList<String> testEmptyCollection = new ArrayList<String>();

    /**
     * argument not null - valid condition (i.e. pass a non-null argument)
     */
    @Test
    public void argNotNull_valid() {
        argNotNull(testMessage, testMessage);
    }

    /**
     * argument not null - invalid condition (i.e. pass a null argument)
     */
    @Test
    public void argNotNull_notValid() {
        try {
            argNotNull(null, testMessage);

            fail("argNotNull did not throw an exception");

        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), testMessage); // expect my message
        }
    }

    /**
     * argument not null - object, message, cause
     */
    @Test
    public void argNotNull_objectMessageCause() {
        try {
            argNotNull(null, testMessage, testCause);

            fail("argNotNull did not throw an exception");

        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), testMessage); // expect my message
            assertSame(e.getCause(), testCause);
        }
    }

    /**
     * argument not null - valid condition (i.e. pass a non-null argument)
     */
    @Test
    public void assertArgNotNull_valid() {
        assertArgNotNull(testMessage, testMessage);
    }

    /**
     * argument not null - invalid condition (i.e. pass a null argument)
     */
    @Test
    public void assertArgNotNull_notValid() {
        try {
            assertArgNotNull(null, testMessage);

            fail("assertArgNotNull did not throw an exception");

        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), testMessage); // expect my message
        }
    }

    /**
     * argument null - object, message, cause
     */
    @Test
    public void assertArgNotNull_objectMessageCause() {
        try {
            assertArgNotNull(null, testMessage, testCause);

            fail("assertArgNotNull did not throw an exception");

        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), testMessage); // expect my message
            assertSame(e.getCause(), testCause);
        }
    }

    /**
     * argument has length - valid String
     */
    @Test
    public void argHasLength_validString() {
        argHasLength(testMessage, testMessage);
    }

    /**
     * argument has length - null String  (incl null check)
     */
    @Test
    public void argHasLength_nullString() {
        try {
            argHasLength(null, testMessage);

            fail("argHasLength did not throw an exception");

        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), testMessage); // some default message provided
        }
    }

    /**
     * argument has length - empty String
     */
    @Test
    public void argHasLength_emptyString() {
        try {
            argHasLength("", testMessage);

            fail("argHasLength did not throw an exception");

        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), testMessage); // some default message provided
        }
    }

    /**
     * argument has length - object, message, cause
     */
    @Test
    public void argHasLength_objectMessageCause() {
        try {
            argHasLength("", testMessage, testCause);

            fail("argHasLength did not throw an exception");

        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), testMessage);
            assertSame(e.getCause(), testCause);
        }
    }

    /**
     * argument has length - valid String
     */
    @Test
    public void assertArgHasLength_validString() {
        assertArgHasLength(testMessage, testMessage);
    }

    /**
     * argument has length - null String  (incl null check)
     */
    @Test
    public void assertArgHasLength_nullString() {
        try {
            assertArgHasLength(null, testMessage);

            fail("assertArgHasLength did not throw an exception");

        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), testMessage);
        }
    }

    /**
     * argument has length - empty String
     */
    @Test
    public void assertArgHasLength_emptyString() {
        try {
            assertArgHasLength("", testMessage);

            fail("assertArgHasLength did not throw an exception");

        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), testMessage);
        }
    }

    /**
     * argument has length - object, message, cause
     */
    @Test
    public void assertArgHasLength_objectMessageCause() {
        try {
            assertArgHasLength("", testMessage, testCause);

            fail("assertArgHasLength did not throw an exception");

        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), testMessage);
            assertSame(e.getCause(), testCause);
        }
    }

    /**
     * argument not empty - valid Collection
     */
    @Test
    public void argNotEmpty_validCollection() {
        List<String> list = new ArrayList<String>();
        list.add(testMessage);
        argNotEmpty(list, testMessage);
    }

    /**
     * argument not empty - null Collection  (incl null check)
     */
    @Test
    public void argNotEmpty_nullCollection() {
        try {
            argNotEmpty(null, testMessage);

            fail("argNotEmpty did not throw an exception");

        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), testMessage);
        }
    }

    /**
     * argument not empty - empty Collection
     */
    @Test
    public void argNotEmpty_emptyCollection() {
        try {
            argNotEmpty(testEmptyCollection, testMessage);

            fail("argNotEmpty did not throw an exception");

        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), testMessage);
        }
    }

    /**
     * argument not empty - object, message, cause
     */
    @Test
    public void argNotEmpty_objectMessageCause() {
        try {
            argNotEmpty(testEmptyCollection, testMessage, testCause);

            fail("argNotEmpty did not throw an exception");

        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), testMessage);
            assertSame(e.getCause(), testCause);
        }
    }

    /**
     * argument not empty - valid Collection
     */
    @Test
    public void assertArgNotEmpty_validCollection() {
        List<String> list = new ArrayList<String>();
        list.add(testMessage);
        assertArgNotEmpty(list, testMessage);
    }

    /**
     * argument not empty - null Collection  (incl null check)
     */
    @Test
    public void assertArgNotEmpty_nullCollection() {
        try {
            assertArgNotEmpty((Collection<?>) null, testMessage);

            fail("assertArgNotEmpty did not throw an exception");

        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), testMessage); // some default message provided
        }
    }

    /**
     * argument not empty - empty Collection
     */
    @Test
    public void assertArgNotEmpty_emptyCollection() {
        try {
            assertArgNotEmpty(testEmptyCollection, testMessage);

            fail("assertArgNotEmpty did not throw an exception");

        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), testMessage); // some default message provided
        }
    }

    /**
     * argument not empty - object, message, cause
     */
    @Test
    public void assertArgNotEmpty_objectMessageCause() {
        try {
            assertArgNotEmpty(testEmptyCollection, testMessage, testCause);

            fail("assertArgNotEmpty did not throw an exception");

        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), testMessage);
            assertSame(e.getCause(), testCause);
        }
    }

    /**
     * state - valid condition (i.e. true expression)
     */
    @Test
    public void state_valid() {
        state(true, testMessage);
    }

    /**
     * state - invalid condition (i.e. false expression)
     */
    @Test
    public void state_notValid() {
        try {
            state(false, testMessage);

            fail("state did not throw an exception");

        } catch (IllegalStateException e) {
            assertEquals(e.getMessage(), testMessage); // expect my message
        }
    }

    /**
     * state - boolean, message, cause
     */
    @Test
    public void state_objectMessageCause() {
        try {
            state(false, testMessage, testCause);

            fail("state did not throw an exception");

        } catch (IllegalStateException e) {
            assertEquals(e.getMessage(), testMessage); // expect my message
            assertSame(e.getCause(), testCause);
        }
    }

    /**
     * state - valid condition (i.e. true expression)
     */
    @Test
    public void assertState_valid() {
        assertState(true, testMessage);
    }

    /**
     * state - invalid condition (i.e. false expression)
     */
    @Test
    public void assertState_notValid() {
        try {
            assertState(false, testMessage);

            fail("assertState did not throw an exception");

        } catch (IllegalStateException e) {
            assertEquals(e.getMessage(), testMessage); // expect my message
        }
    }

    /**
     * state - boolean, message, cause
     */
    @Test
    public void assertState_objectMessageCause() {
        try {
            assertState(false, testMessage, testCause);

            fail("assertState did not throw an exception");

        } catch (IllegalStateException e) {
            assertEquals(e.getMessage(), testMessage); // expect my message
            assertSame(e.getCause(), testCause);
        }
    }

    /**
     * state not null - valid condition
     */
    @Test
    public void stateNotNull_valid() {
        stateNotNull(testMessage, testMessage);
    }

    /**
     * state not null - invalid condition
     */
    @Test
    public void stateNotNull_notValid() {
        try {
            stateNotNull(null, testMessage);

            fail("stateNotNull did not throw an exception");

        } catch (IllegalStateException e) {
            assertEquals(e.getMessage(), testMessage);  // expect my message
        }
    }

    /**
     * state not null - object, message, cause
     */
    @Test
    public void stateNotNull_objectMessageCause() {
        try {
            stateNotNull(null, testMessage, testCause);

            fail("stateNotNull did not throw an exception");

        } catch (IllegalStateException e) {
            assertEquals(e.getMessage(), testMessage); // expect my message
            assertSame(e.getCause(), testCause);
        }
    }

    /**
     * state not null - valid condition
     */
    @Test
    public void assertStateNotNull_valid() {
        assertStateNotNull(testMessage, testMessage);
    }

    /**
     * state not null - invalid condition
     */
    @Test
    public void assertStateNotNull_notValid() {
        try {
            assertStateNotNull(null, testMessage);

            fail("assertStateNotNull did not throw an exception");

        } catch (IllegalStateException e) {
            assertEquals(e.getMessage(), testMessage); // expect my message
        }
    }

    /**
     * state not null - object, message, cause
     */
    @Test
    public void assertStateNotNull_objectMessageCause() {
        try {
            assertStateNotNull(null, testMessage, testCause);

            fail("assertStateNotNull did not throw an exception");

        } catch (IllegalStateException e) {
            assertEquals(e.getMessage(), testMessage); // expect my message
            assertSame(e.getCause(), testCause);
        }
    }

    /**
     * state has length - valid String
     */
    @Test
    public void stateHasLength_validString() {
        stateHasLength(testMessage, testMessage);
    }

    /**
     * state has length - null String  (incl null check)
     */
    @Test
    public void stateHasLength_nullString() {
        try {
            stateHasLength(null, testMessage);

            fail("stateHasLength did not throw an exception");

        } catch (IllegalStateException e) {
            assertEquals(e.getMessage(), testMessage); // expect my message
        }
    }

    /**
     * state has length - empty String
     */
    @Test
    public void stateHasLength_emptyString() {
        try {
            stateHasLength("", testMessage);

            fail("stateHasLength did not throw an exception");

        } catch (IllegalStateException e) {
            assertEquals(e.getMessage(), testMessage); // expect my message
        }
    }

    /**
     * state has length - object, message, cause
     */
    @Test
    public void stateHasLength_objectMessageCause() {
        try {
            stateHasLength("", testMessage, testCause);

            fail("stateHasLength did not throw an exception");

        } catch (IllegalStateException e) {
            assertEquals(e.getMessage(), testMessage);
            assertSame(e.getCause(), testCause);
        }
    }

    /**
     * state has length - valid String
     */
    @Test
    public void assertStateHasLength_validString() {
        assertStateHasLength(testMessage, testMessage);
    }

    /**
     * state has length - null String  (incl null check)
     */
    @Test
    public void assertStateHasLength_nullString() {
        try {
            assertStateHasLength(null, testMessage);

            fail("assertStateHasLength did not throw an exception");

        } catch (IllegalStateException e) {
            assertEquals(e.getMessage(), testMessage); // expect my message
        }
    }

    /**
     * state has length - empty String
     */
    @Test
    public void assertStateHasLength_emptyString() {
        try {
            assertStateHasLength("", testMessage);

            fail("assertStateHasLength did not throw an exception");

        } catch (IllegalStateException e) {
            assertEquals(e.getMessage(), testMessage); // expect my message
        }
    }

    /**
     * state has length - object, message, cause
     */
    @Test
    public void assertStateHasLength_objectMessageCause() {
        try {
            assertStateHasLength("", testMessage, testCause);

            fail("assertStateHasLength did not throw an exception");

        } catch (IllegalStateException e) {
            assertEquals(e.getMessage(), testMessage);
            assertSame(e.getCause(), testCause);
        }
    }

    /**
     * state not empty - valid collection
     */
    @Test
    public void stateNotEmpty_validCollection() {
        List<String> list = new ArrayList<String>();
        list.add(testMessage);
        stateNotEmpty(list, testMessage);
    }

    /**
     * state not empty - null Collection  (incl null check)
     */
    @Test
    public void stateNotEmpty_nullCollection() {
        try {
            stateNotEmpty((Collection<?>) null, testMessage);

            fail("stateNotEmpty did not throw an exception");

        } catch (IllegalStateException e) {
            assertEquals(e.getMessage(), testMessage); // some default message provided
        }
    }

    /**
     * state not empty - empty Collection
     */
    @Test
    public void stateNotEmpty_emptyCollection() {
        try {
            stateNotEmpty(testEmptyCollection, testMessage);

            fail("stateNotEmpty did not throw an exception");

        } catch (IllegalStateException e) {
            assertEquals(e.getMessage(), testMessage); // some default message provided
        }
    }

    /**
     * state not empty - object, message, cause
     */
    @Test
    public void stateNotEmpty_objectMessageCause() {
        try {
            stateNotEmpty(testEmptyCollection, testMessage, testCause);

            fail("stateNotEmpty did not throw an exception");

        } catch (IllegalStateException e) {
            assertEquals(e.getMessage(), testMessage); // expect my message
            assertSame(e.getCause(), testCause);
        }
    }

    /**
     * state not empty - valid collection
     */
    @Test
    public void assertStateNotEmpty_validCollection() {
        List<String> list = new ArrayList<String>();
        list.add(testMessage);
        assertStateNotEmpty(list, testMessage);
    }

    /**
     * state not empty - null Collection  (incl null check)
     */
    @Test
    public void assertStateNotEmpty_nullCollection() {
        try {
            assertStateNotEmpty((Collection<?>) null, testMessage);

            fail("assertStateNotEmpty did not throw an exception");

        } catch (IllegalStateException e) {
            assertEquals(e.getMessage(), testMessage); // expect my message
        }
    }

    /**
     * state not empty - empty Collection
     */
    @Test
    public void assertStateNotEmpty_emptyCollection() {
        try {
            assertStateNotEmpty(testEmptyCollection, testMessage);

            fail("assertStateNotEmpty did not throw an exception");

        } catch (IllegalStateException e) {
            assertEquals(e.getMessage(), testMessage); // expect my message
        }
    }

    /**
     * state not empty - object, message, cause
     */
    @Test
    public void assertStateNotEmpty_objectMessageCause() {
        try {
            assertStateNotEmpty(testEmptyCollection, testMessage, testCause);

            fail("assertStateNotEmpty did not throw an exception");

        } catch (IllegalStateException e) {
            assertEquals(e.getMessage(), testMessage);
            assertSame(e.getCause(), testCause);
        }
    }

    /**
     * state null - valid condition
     */
    @Test
    public void stateNull_valid() {
        String aNullValue = null;
        stateNull(aNullValue, testMessage);
    }

    /**
     * state null - invalid condition
     */
    @Test
    public void stateNull_notValid() {
        try {
            stateNull(testMessage, testMessage);

            fail("stateNull did not throw an exception");

        } catch (IllegalStateException e) {
            assertEquals(e.getMessage(), testMessage); // expect my message
        }
    }

    /**
     * state null - object, message, cause
     */
    @Test
    public void stateNull_objectMessageCause() {
        try {
            stateNull(testMessage, testMessage, testCause);

            fail("stateNull did not throw an exception");

        } catch (IllegalStateException e) {
            assertEquals(e.getMessage(), testMessage); // expect my message
            assertSame(e.getCause(), testCause);
        }
    }

    /**
     * state null - valid condition
     */
    @Test
    public void assertStateNull_valid() {
        assertStateNull(null, testMessage);
    }

    /**
     * state null - invalidCondition
     */
    @Test
    public void assertStateNull_object() {
        try {
            assertStateNull(testMessage, testMessage);

            fail("assertStateNull did not throw an exception");

        } catch (IllegalStateException e) {
            assertEquals(e.getMessage(), testMessage); // expect my message
        }
    }

    /**
     * state null - object, message, cause
     */
    @Test
    public void assertStateNull_objectMessageCause() {
        try {
            assertStateNull(testMessage, testMessage, testCause);

            fail("assertStateNull did not throw an exception");

        } catch (IllegalStateException e) {
            assertEquals(e.getMessage(), testMessage); // expect my message
            assertSame(e.getCause(), testCause);
        }
    }
}
