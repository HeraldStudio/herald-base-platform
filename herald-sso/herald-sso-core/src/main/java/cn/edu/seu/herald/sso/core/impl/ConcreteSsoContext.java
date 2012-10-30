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

package cn.edu.seu.herald.sso.core.impl;

import cn.edu.seu.herald.sso.domain.SingleSignOnContext;
import cn.edu.seu.herald.sso.domain.StudentUser;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author rAy <predator.ray@gmail.com>
 */
public class ConcreteSsoContext implements SingleSignOnContext {

    private StudentUser logOnStudentUser;

    private Map<String, Object> contextAttributes;

    public ConcreteSsoContext() {
        contextAttributes = new HashMap<String, Object>();
    }

    public void setLogOnStudentUser(StudentUser logOnStudentUser) {
        this.logOnStudentUser = logOnStudentUser;
    }

    public StudentUser getLogOnStudentUser() {
        return logOnStudentUser;
    }

    public void setAttribute(String name, Object value) {
        contextAttributes.put(name, value);
    }

    public Object getAttribute(String name) {
        return contextAttributes.get(name);
    }

    public Enumeration<String> getAttributeNames() {
        return Collections.enumeration(contextAttributes.keySet());
    }

}
