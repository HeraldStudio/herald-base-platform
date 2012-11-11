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
import java.util.Date;
import java.util.Enumeration;

/**
 * The demo program of the usage of Session API
 *
 * @author rAy <predator.ray@gmail.com>
 */
public class Demo {

    /**
     * the entrance of demo program
     *
     * @param args
     */
    public static void main(String[] args) {
        Demo demo = new Demo();
        try {
            demo.createSession();
            System.out.println();

            demo.updateSession();
            System.out.println();

            demo.retrieveSession();
            System.out.println();

            demo.deleteSession();
            System.out.println();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private SessionService sessionService;
    private Session session;

    public Demo() {
        // get an instance of session service factory
        SessionServiceFactory sessionServiceFactory =
                SessionServiceFactory.getInstance();
        // get the session service by the factory
        this.sessionService = sessionServiceFactory.getSessionService();
    }

    /*
     * The demo of creating a session
     */
    public void createSession() throws SessionAccessException {
        // start a new session
        Session newSession = sessionService.getSession();
        // print the session info
        printSessionInfo(newSession);
        // store the session retrieved
        this.session = newSession;
    }

    /*
     * The demo of updating a session by its id
     */
    public void updateSession() throws SessionAccessException {
        // set an atrribute of the session
        session.setAttribute("foo", "bar");
        // print the session info
        printSessionInfo(session);
        // update the session
        sessionService.updateSession(session);
    }

    public void retrieveSession() throws SessionAccessException {
        String sessionId = session.getId();
        // retrieve the session by its identifier
        Session sessionRetrieved = sessionService.getSessionById(sessionId);
        // print the session info
        // print the session info
        printSessionInfo(sessionRetrieved);
        printSessionInfo(session);
    }

    public void deleteSession() throws SessionAccessException {
        String sessionId = session.getId();
        // delete the session by its identifier
        sessionService.removeSessionById(sessionId);
        // if retrieve it again, 404(not found) will be returned
    }

    private static void printSessionInfo(Session session) {
        // get the identifier of the session
        String sessionid = session.getId();
        System.out.println("session id: " + sessionid);

        // get the creation time of the session
        long sessionCreationTime = session.getCreationTime();
        Date creationDate = new Date(sessionCreationTime);
        System.out.println("session creation time: " + creationDate);

        // get the last accessed time of the session
        long lastAccessedTime = session.getLastAccessedTime();
        Date lastAccessedDate = new Date(lastAccessedTime);
        System.out.println("session last accessed time: " + lastAccessedDate);

        // get the uri time of the session
        String uri = session.getUri();
        System.out.println("session uri: " + uri);

        // get the names of the attributes
        System.out.println("session attributes:");
        Enumeration<String> attributeNames = session.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String attributeName = attributeNames.nextElement();
            // get an attribute from the session
            Object attribute = session.getAttribute(attributeName);
            System.out.println("\t" + attributeName + " : " + attribute);
        }
    }
}
