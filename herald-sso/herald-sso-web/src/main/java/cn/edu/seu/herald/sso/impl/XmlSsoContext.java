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
package cn.edu.seu.herald.sso.impl;

import cn.edu.seu.herald.sso.domain.StudentUser;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author rAy <predator.ray@gmail.com>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "singleSignOnContext")
public class XmlSsoContext {

    @XmlElement(name = "studentUser")
    private StudentUser logOnStudentUser;
    @XmlElement(name = "properties")
    @XmlJavaTypeAdapter(MapAdapter.class)
    private Map<String, Object> contextAttributes;

    public XmlSsoContext() {
        contextAttributes = new LinkedHashMap<String, Object>();
    }

    public StudentUser getLogOnStudentUser() {
        return logOnStudentUser;
    }

    public void setLogOnStudentUser(StudentUser logOnStudentUser) {
        this.logOnStudentUser = logOnStudentUser;
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

    public void removeAttribute(String name) {
        contextAttributes.remove(name);
    }
}
