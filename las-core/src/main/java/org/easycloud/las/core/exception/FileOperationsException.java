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
 * File operations exception
 * <p/>
 * Created by IntelliJ IDEA.
 * User: Meng, Ke
 * Date: 13-5-9
 */
public class FileOperationsException extends RuntimeException {

    /**
     * Constructor
     *
     * @param msg a user provided msg
     */
    public FileOperationsException(String msg) {
        super(msg);
    }

    /**
     * Constructor
     *
     * @param t the throwable we are wrapping
     */
    public FileOperationsException(Throwable t) {
        super(t);
    }

    /**
     * Constructor
     *
     * @param msg a user provided msg
     * @param t   the throwable we are wrapping
     */
    public FileOperationsException(String msg, Throwable t) {
        super(msg, t);
    }

    /**
     * Unwrap to the root exception
     */
    @Override
    public Throwable getCause() {
        Throwable t = super.getCause();
        Throwable c = t;
        while (t != null) {
            c = t;
            t = t.getCause();
        }
        return c;
    }

}
