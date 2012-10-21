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

package cn.edu.seu.herald.session.example;

import cn.edu.seu.herald.session.Session;
import cn.edu.seu.herald.session.SessionService;
import cn.edu.seu.herald.session.SessionServiceFactory;
import cn.edu.seu.herald.session.exception.SessionAccessException;
import java.util.Enumeration;

/**
 *
 * @author rAy <predator.ray@gmail.com>
 */
public class Demo {

    public static void main(String[] args) {
        // get an instance of session service factory
        SessionServiceFactory sessionServiceFactory =
                SessionServiceFactory.getInstance();
        // get the session service by the factory
        SessionService sessionService = sessionServiceFactory.getSessionService();
        
        try {
            // start a new session
            Session newSession = sessionService.getSession();
            
            // get the identifier of the session
            String sessionid = newSession.getId();
            System.out.println("session id: " + sessionid);
            
            // get the creation time of the session
            long sessionCreationTime = newSession.getCreationTime();
            System.out.println("session creation time: " + sessionCreationTime);
            
            // get the last accessed time of the session
            long lastAccessedTime = newSession.getLastAccessedTime();
            System.out.println("session last accessed time: " + lastAccessedTime);
            
            // get the names of the attributes
            System.out.println("session attributes:");
            Enumeration<String> attributeNames = newSession.getAttributeNames();
            while (attributeNames.hasMoreElements()) {
                String attributeName = attributeNames.nextElement();
                // get an attribute from the session
                Object attribute = newSession.getAttribute(attributeName);
                System.out.println("\t" + attributeName + " : " + attribute);
            }
        } catch (SessionAccessException ex) {
            ex.printStackTrace();
        }
    }

}
