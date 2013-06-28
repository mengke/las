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

import java.util.Collection;
import java.util.Map;

/**
 * Assertion utility class that assists in validating arguments. Useful for identifying programmer errors early and clearly at runtime.
 *
 * It is recommended that this class be statically imported so
 * that only the method names are in the code itself. For example
 * import static org.easycloud.las.core.util.las.core.util.Assert.assertArgNotNull;
 *
 * Each method name gives a clue as to the type of exception that will be thrown
 * if the test is false.
 * Method names with "Arg" will throw IllegalArgumentException;
 * method names with "State" will throw IllegalStateException.
 *
 * Each type of test (e.g. NotNull, HasLength, NotEmpty, etc.) will have
 * two signature flavors,
 * - one with the object and a string to be the message of the thrown exception
 * - one with the object, message, and a Throwable which will be set as the cause
 * of the thrown exception
 *
 * There are two version of each method name - one with "assert", one without.
 * Use the methods withOUT "assert" in their name when using the java assert keyword.
 * These methods return a boolean and work with the java assert keyword.
 * For example, assert argNotNull(param, "class method arg null");
 * The methods without assert in their name intentionally return boolean and
 * the methods with assert in their name intentionally return void.
 * This is so that you don't code something like this:  assert assertArgNotNull(...)
 * It doesn't "read" well and since the method returns a void, it won't work with the java assert.
 *
 * Note: It's strongly recommended that org.testng.Assert be used instead of this class in unit tests.
 *
 * Created by IntelliJ IDEA.
 * User: Meng, Ke
 * Date: 13-5-9
 */
public class Assert {

	/* argument not null */

	public static boolean argNotNull(Object object, String message) {
		return argNotNull(object, message, null);
	}

	public static void assertArgNotNull(Object object, String message) {
		assertArgNotNull(object, message, null);
	}

	public static boolean argNotNull(Object object, String message, Throwable cause) {
		if (object == null) {
			throw new IllegalArgumentException(message, cause);
		}

		return true;
	}

	public static void assertArgNotNull(Object object, String message, Throwable cause) {
		argNotNull(object, message, cause);
	}

	/* argument has length (includes not-null checking) */

	public static boolean argHasLength(String string, String message) {
		return argHasLength(string, message, null);
	}

	public static void assertArgHasLength(String string, String message) {
		assertArgHasLength(string, message, null);
	}

	public static boolean argHasLength(String string, String message, Throwable cause) {
		// first check for null
		assertArgNotNull(string, message, cause);

		if (string.length() == 0) {
			throw new IllegalArgumentException(message, cause);
		}

		return true;
	}

	public static void assertArgHasLength(String string, String message, Throwable cause) {
		argHasLength(string, message, cause);
	}

	/* argument not empty (includes not-null checking) for collection */

	public static void assertArgNotEmpty(Collection<?> collection, String message) {
		assertArgNotEmpty(collection, message, null);
	}

	public static boolean argNotEmpty(Collection<?> collection, String message, Throwable cause) {
		// first check for null
		assertArgNotNull(collection, message, cause);

		if (collection.size() == 0) {
			throw new IllegalArgumentException(message, cause);
		}
		return true;
	}

	public static void assertArgNotEmpty(Collection<?> collection, String message, Throwable cause) {
		argNotEmpty(collection, message, cause);
	}

	public static boolean argNotEmpty(Collection<?> collection, String message) {
		return argNotEmpty(collection, message, null);
	}

	/* argument not empty (includes not-null checking)  for map */

	public static void assertArgNotEmpty(Map<?, ?> map, String message) {
		assertArgNotEmpty(map, message, null);
	}

	public static void assertArgNotEmpty(Map<?, ?> map, String message, Throwable cause) {
		argNotEmpty(map, message, cause);
	}

	public static boolean argNotEmpty(Map<?, ?> map, String message, Throwable cause) {
		// first check for null
		assertArgNotNull(map, message, cause);

		if (map.size() == 0) {
			throw new IllegalArgumentException(message, cause);
		}
		return true;
	}

	/* argument true */

	public static boolean arg(boolean argumentCondition, String message) {
		return arg(argumentCondition, message, null);
	}

	public static void assertArg(boolean argumentCondition, String message) {
		assertArg(argumentCondition, message, null);
	}

	public static boolean arg(boolean argumentCondition, String message, Throwable cause) {
		if (argumentCondition != true) {
			throw new IllegalArgumentException(message, cause);
		}
		return true;
	}

	public static void assertArg(boolean argumentCondition, String message, Throwable cause) {
		arg(argumentCondition, message, cause);
	}

	/* state true */

	public static boolean state(boolean state, String message) {
		return state(state, message, null);
	}

	public static void assertState(boolean state, String message) {
		assertState(state, message, null);
	}

	public static boolean state(boolean state, String message, Throwable cause) {
		if (state != true) {
			throw new IllegalStateException(message, cause);
		}
		return true;
	}

	public static void assertState(boolean state, String message, Throwable cause) {
		state(state, message, cause);
	}

	/* state not null */

	public static boolean stateNotNull(Object object, String message) {
		return stateNotNull(object, message, null);
	}

	public static void assertStateNotNull(Object object, String message) {
		assertStateNotNull(object, message, null);
	}

	public static boolean stateNotNull(Object object, String message, Throwable cause) {
		if (object == null) {
			throw new IllegalStateException(message, cause);
		}

		return true;
	}

	public static void assertStateNotNull(Object object, String message, Throwable cause) {
		stateNotNull(object, message, cause);
	}

	/* state has length (includes not-null checking) */

	public static boolean stateHasLength(String string, String message) {
		return stateHasLength(string, message, null);
	}

	public static void assertStateHasLength(String string, String message) {
		assertStateHasLength(string, message, null);
	}

	public static boolean stateHasLength(String string, String message, Throwable cause) {
		// first check for null
		assertStateNotNull(string, message, cause);

		if (string.length() == 0) {
			throw new IllegalStateException(message, cause);
		}

		return true;
	}

	public static void assertStateHasLength(String string, String message, Throwable cause) {
		stateHasLength(string, message, cause);
	}

	/* state not empty (includes not-null checking) */

	public static boolean stateNotEmpty(Collection<?> collection, String message) {
		return stateNotEmpty(collection, message, null);
	}

	public static boolean stateNotEmpty(Map<?, ?> map, String message) {
		return stateNotEmpty(map, message, null);
	}

	public static void assertStateNotEmpty(Collection<?> collection, String message) {
		assertStateNotEmpty(collection, message, null);
	}

	public static void assertStateNotEmpty(Map<?, ?> map, String message) {
		assertStateNotEmpty(map, message, null);
	}

	public static boolean stateNotEmpty(Collection<?> collection, String message, Throwable cause) {
		// first check for null
		assertStateNotNull(collection, message, cause);

		if (collection.size() == 0) {
			throw new IllegalStateException(message, cause);
		}

		return true;
	}

	public static boolean stateNotEmpty(Map<?, ?> map, String message, Throwable cause) {
		// first check for null
		assertStateNotNull(map, message, cause);

		if (map.size() == 0) {
			throw new IllegalStateException(message, cause);
		}
		return true;
	}

	public static void assertStateNotEmpty(Collection<?> collection, String message, Throwable cause) {
		stateNotEmpty(collection, message, cause);
	}

	public static void assertStateNotEmpty(Map<?, ?> map, String message, Throwable cause) {
		stateNotEmpty(map, message, cause);
	}

	/* state null */

	public static boolean stateNull(Object object, String message) {
		return stateNull(object, message, null);
	}

	public static void assertStateNull(Object object, String message) {
		assertStateNull(object, message, null);
	}

	public static boolean stateNull(Object object, String message, Throwable cause) {
		if (object != null) {
			throw new IllegalStateException(message, cause);
		}

		return true;
	}

	public static void assertStateNull(Object object, String message, Throwable cause) {
		stateNull(object, message, cause);
	}

	public static boolean fail(String message) {
		return fail(message, null);
	}

	public static void assertFail(String message) {
		assertFail(message, null);
	}

	public static boolean fail(String message, Throwable cause) {
		throw new IllegalStateException(message, cause);
	}

	public static void assertFail(String message, Throwable cause) {
		fail(message, cause);
	}

}
