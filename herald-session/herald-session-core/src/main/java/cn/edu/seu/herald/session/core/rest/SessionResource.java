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

package cn.edu.seu.herald.session.core.rest;

import cn.edu.seu.herald.session.Session;
import cn.edu.seu.herald.session.SessionResourceConstants;
import cn.edu.seu.herald.session.core.SessionCacheAccess;
import cn.edu.seu.herald.session.util.DomRepresentationParser;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.restlet.data.Parameter;
import org.restlet.data.Status;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author rAy <predator.ray@gmail.com>
 */
public class SessionResource extends ServerResource
        implements SessionResourceConstants {

    private static final String SESSION_CACHE_ACCESS_BEAN_NAME =
            "sessionCacheAccess";

    private static final String SESSION_CONFIG_PATH =
            "classpath*:/cn/edu/seu/herald/session/herald-session-cache.xml";

    private static final String MISSING_PARAM_SESSIONID_MSG =
            "missing parameter: id";

    private static final String INVALID_OR_EXPIRED_SESSION_ID =
            "invalid or expired session id";

    private static final Logger logger = Logger.getLogger(
            SessionResource.class.getName());

    private SessionCacheAccess sessionCacheAccess;

    private SessionFactory sessionFactory;

    public SessionResource() {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                SESSION_CONFIG_PATH);
        sessionCacheAccess = (SessionCacheAccess) context
                .getBean(SESSION_CACHE_ACCESS_BEAN_NAME);
        sessionFactory = SessionFactory.getInstance();
    }

    @Get("xml")
    public Representation getSession() {
        try {
            Parameter sessionIdParam = getQuery()
                    .getFirst(SESSION_ID_QUERY_PARAM);

            if (sessionIdParam == null) {
                // create a new session
                Session newSession = sessionFactory.newSession();
                sessionCacheAccess.storeSession(newSession);

                DomRepresentationParser parser = new DomRepresentationParser();
                return parser.getRepresentation(newSession);
            }

            String sessionId = sessionIdParam.getValue();
            Session cachedSession = sessionCacheAccess
                    .getSessionById(sessionId);
            if (cachedSession == null) {
                getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);
                return SessionResourceUtils.getErrorRepresentation(
                        INVALID_OR_EXPIRED_SESSION_ID);
            }

            DomRepresentationParser parser = new DomRepresentationParser();
            return parser.getRepresentation(cachedSession);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            getResponse().setStatus(Status.SERVER_ERROR_INTERNAL);
            return SessionResourceUtils.getErrorRepresentation(ex);
        }
    }

    @Post("xml:xml")
    public Representation updateSession(Representation sessionRepr) {
        try {
            DomRepresentation sessionDomRepr =
                    new DomRepresentation(sessionRepr);
            DomRepresentationParser parser = new DomRepresentationParser();
            Session sessionToUpdate = (Session) parser
                    .getXmlObject(sessionDomRepr, Session.class);
            sessionCacheAccess.updateSession(sessionToUpdate);
            return SessionResourceUtils.getSuccessRepresentation();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            getResponse().setStatus(Status.SERVER_ERROR_INTERNAL);
            return SessionResourceUtils.getErrorRepresentation(ex);
        }
    }

    @Delete("xml:xml")
    public Representation deleteSession() {
        try {
            Parameter sessionIdParam = getQuery()
                    .getFirst(SESSION_ID_QUERY_PARAM);
            String sessionId = sessionIdParam.getValue();
            if (sessionId == null) {
                getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
                return SessionResourceUtils.getErrorRepresentation(
                        MISSING_PARAM_SESSIONID_MSG);
            }

            sessionCacheAccess.removeSessionById(sessionId);
            return SessionResourceUtils.getSuccessRepresentation();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            getResponse().setStatus(Status.SERVER_ERROR_INTERNAL);
            return SessionResourceUtils.getErrorRepresentation(ex);
        }
    }

}
