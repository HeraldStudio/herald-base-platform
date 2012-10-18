/*
 * Copyright (C) 2012 Herald, Southeast University
 */

package cn.edu.seu.herald.sso.core.domain;

import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author rAy <predator.ray@gmail.com>
 */
public class SingleSignOnContext {
    
    private StudentUser logOnStudentUser;
    
    private Map<String, Object> contextAttributes;
    
    public SingleSignOnContext() {
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
