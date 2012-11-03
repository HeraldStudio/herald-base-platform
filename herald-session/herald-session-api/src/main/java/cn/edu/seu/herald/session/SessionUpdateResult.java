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
package cn.edu.seu.herald.session;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author rAy <predator.ray@gmail.com>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "result")
public class SessionUpdateResult {

    private ResultType type;
    private String message;
    private String exception;

    public SessionUpdateResult() {
        this(ResultType.SUCCESS);
    }

    public SessionUpdateResult(ResultType resultType) {
        this.type = resultType;
    }

    public SessionUpdateResult(ResultType resultType, String message) {
        this.type = resultType;
        this.message = message;
    }

    public SessionUpdateResult(Exception ex) {
        this(ResultType.FAILURE);
        this.exception = ex.getClass().getName();
        this.message = ex.getMessage();
    }

    public ResultType getResultType() {
        return type;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setExceptionName(String exceptionName) {
        this.exception = exceptionName;
    }

    public String getExceptionName() {
        return exception;
    }

    public static enum ResultType {

        SUCCESS, FAILURE
    }
}
