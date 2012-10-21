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
package cn.edu.seu.herald.session.jee.example;

import cn.edu.seu.herald.session.Session;
import cn.edu.seu.herald.session.SessionService;
import cn.edu.seu.herald.session.SessionServiceFactory;
import cn.edu.seu.herald.session.exception.SessionAccessException;
import cn.edu.seu.herald.session.jee.SessionServiceClient;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author rAy <predator.ray@gmail.com>
 */
public class DemoServlet extends HttpServlet {
    
    private SessionServiceClient sessionServiceClient;
    
    @Override
    public void init(ServletConfig config) {
        // get an instance of session service factory
        SessionServiceFactory sessionServiceFactory =
                SessionServiceFactory.getInstance();
        // get the session service by the factory
        SessionService sessionService = sessionServiceFactory.getSessionService();
        sessionServiceClient = new SessionServiceClient(sessionService);
    }

    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        try {
            Session currentSession = sessionServiceClient.getSession(request, response);
            
            // get the identifier of the session
            String sessionid = currentSession.getId();
            out.println("session id: " + sessionid);
            
            // get the creation time of the session
            long sessionCreationTime = currentSession.getCreationTime();
            out.println("session creation time: " + sessionCreationTime);
            
            // get the last accessed time of the session
            long lastAccessedTime = currentSession.getLastAccessedTime();
            out.println("session last accessed time: " + lastAccessedTime);
            
            // get the names of the attributes
            out.println("session attributes:");
            Enumeration<String> attributeNames = currentSession.getAttributeNames();
            while (attributeNames.hasMoreElements()) {
                String attributeName = attributeNames.nextElement();
                // get an attribute from the session
                Object attribute = currentSession.getAttribute(attributeName);
                System.out.println("\t" + attributeName + " : " + attribute);
            }
        } catch (SessionAccessException ex) {
            response.sendError(500, ex.getMessage());
        } finally {
            out.close();
        }
    }

}
