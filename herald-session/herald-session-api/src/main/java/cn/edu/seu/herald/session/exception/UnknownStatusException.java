/*
 * Copyright 2012 Herald, Southeast University.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.edu.seu.herald.session.exception;

import org.restlet.data.Status;

/**
 *
 * @author rAy <predator.ray@gmail.com>
 */
public class UnknownStatusException extends SessionAccessException {

    public UnknownStatusException() {
        super("unknown status");
    }

    public UnknownStatusException(Status status) {
        super(new StringBuilder()
                .append("unknown status ")
                .append(status.getCode())
                .append(" ")
                .append(status.getName()).toString());
    }
}
