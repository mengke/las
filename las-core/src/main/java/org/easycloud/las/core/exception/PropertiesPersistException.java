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

package org.easycloud.las.core.exception;

/**
 * Created by IntelliJ IDEA.
 * User: Meng, Ke
 * Date: 13-5-22
 */
public class PropertiesPersistException extends RuntimeException {

	public PropertiesPersistException() {
	}

	public PropertiesPersistException(String message) {
		super(message);
	}

	public PropertiesPersistException(String message, Throwable cause) {
		super(message, cause);
	}

	public PropertiesPersistException(Throwable cause) {
		super(cause);
	}

	public PropertiesPersistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
