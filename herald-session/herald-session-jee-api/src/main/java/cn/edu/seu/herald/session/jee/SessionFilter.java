/*
 * Copyright 2012 Herald Studio, Southeast University.
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
package cn.edu.seu.herald.session.jee;

import cn.edu.seu.herald.session.Session;
import cn.edu.seu.herald.session.SessionService;
import cn.edu.seu.herald.session.SessionServiceFactory;
import cn.edu.seu.herald.session.exception.SessionAccessException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author rAy <predator.ray@gmail.com>
 */
public class SessionFilter implements Filter {

    private static final Logger logger = Logger.getLogger(
            SessionFilter.class.getName());
    private SessionServiceClient sessionServiceClient;
    private SessionService sessionService;

    public void init(FilterConfig filterConfig) throws ServletException {
        SessionServiceFactory sessionServiceFactory =
                SessionServiceFactory.getInstance();
        sessionService = sessionServiceFactory
                .getSessionService();
        sessionServiceClient = new SessionServiceClient(sessionService);
    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try {
            Session currentSession = sessionServiceClient
                    .getSession(httpRequest, httpResponse);
            httpRequest.setAttribute(JeeSessionConstants.SESSION_ATTRIB_NAME,
                    currentSession);
            chain.doFilter(request, response);
            sessionService.updateSession(currentSession);
        } catch (SessionAccessException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            httpResponse.sendError(500, "session service is not available");
        }
    }

    public void destroy() {
    }
}
