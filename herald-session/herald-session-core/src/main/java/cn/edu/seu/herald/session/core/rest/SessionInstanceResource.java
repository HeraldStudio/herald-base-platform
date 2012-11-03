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
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.restlet.data.Status;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author rAy <predator.ray@gmail.com>
 */
public class SessionInstanceResource extends ServerResource
        implements SessionResourceConstants, ResourceConfigConstants {

    private static final String INVALID_OR_EXPIRED_SESSION_ID =
            "invalid or expired session id";
    private static final Logger logger = Logger.getLogger(
            SessionInstanceResource.class.getName());
    private SessionCacheAccess sessionCacheAccess;

    public SessionInstanceResource() {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                SESSION_CONFIG_PATH);
        sessionCacheAccess = (SessionCacheAccess) context
                .getBean(SESSION_CACHE_ACCESS_BEAN_NAME);
    }

    private String getSessionId() {
        Map<String, Object> requestAttributes = getRequestAttributes();
        String sessionId = (String) requestAttributes
                .get(SESSION_ID_PARAM_NAME);
        return sessionId;
    }

    @Get("xml")
    public Representation getSession() {
        try {
            String sessionId = getSessionId();
            if (null != null) {
            }

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

    @Put("xml:xml")
    public Representation updateSession(Representation sessionRepr) {
        try {
            String sessionId = getSessionId();
            DomRepresentation sessionDomRepr =
                    new DomRepresentation(sessionRepr);
            DomRepresentationParser parser = new DomRepresentationParser();
            Session sessionToUpdate = (Session) parser
                    .getXmlObject(sessionDomRepr, Session.class);

            if (!sessionId.equals(sessionToUpdate.getId())) {
                return SessionResourceUtils.getErrorRepresentation(
                        "ambiguous session id");
            }
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
            String sessionId = getSessionId();
            sessionCacheAccess.removeSessionById(sessionId);
            return SessionResourceUtils.getSuccessRepresentation();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            getResponse().setStatus(Status.SERVER_ERROR_INTERNAL);
            return SessionResourceUtils.getErrorRepresentation(ex);
        }
    }
}
