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
package cn.edu.seu.herald.session.core.rest;

import cn.edu.seu.herald.session.Session;
import cn.edu.seu.herald.session.SessionResourceConstants;
import cn.edu.seu.herald.session.core.SessionCacheAccess;
import cn.edu.seu.herald.session.util.DomRepresentationParser;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author rAy <predator.ray@gmail.com>
 */
public class SessionCollectionResource extends ServerResource
        implements SessionResourceConstants, ResourceConfigConstants {

    private static final Logger logger = Logger.getLogger(
            SessionCollectionResource.class.getName());
    private SessionCacheAccess sessionCacheAccess;
    private SessionFactory sessionFactory;

    public SessionCollectionResource() {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                SESSION_CONFIG_PATH);
        sessionCacheAccess = (SessionCacheAccess) context
                .getBean(SESSION_CACHE_ACCESS_BEAN_NAME);
        sessionFactory = new SessionFactory();
    }

    @Post("xml:xml")
    public Representation createNewSession(Representation nullRepr) {
        try {
            Session newSession = sessionFactory.newSession();
            sessionCacheAccess.storeSession(newSession);

            DomRepresentationParser parser = new DomRepresentationParser();
            return parser.getRepresentation(newSession);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            getResponse().setStatus(Status.SERVER_ERROR_INTERNAL);
            return SessionResourceUtils.getErrorRepresentation(ex);
        }
    }
}
