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

package cn.edu.seu.herald.session;

import cn.edu.seu.herald.session.exception.SessionCacheAccessException;
import cn.edu.seu.herald.session.util.DomRepresentationParser;
import org.restlet.data.Status;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;

/**
 *
 * @author rAy <predator.ray@gmail.com>
 */
public class SessionCacheService implements SessionResourceConstants {
    
    private ClientResourceFactory clientResourceFactory;
    
    private DomRepresentationParser parser;
    
    public SessionCacheService() {}
    
    public SessionCacheService(ClientResourceFactory clientResourceFactory) {
        this.clientResourceFactory = clientResourceFactory;
    }
    
    public void setClientResource(ClientResourceFactory clientResourceFactory) {
        this.clientResourceFactory = clientResourceFactory;
    }
    
    public void setDomRepresentationParser(DomRepresentationParser parser) {
        this.parser = parser;
    }

    public Session getSessionById(String id) throws SessionCacheAccessException {
        try {
            ClientResource clientResource = clientResourceFactory.newClientResource();
            clientResource.getReference().addQueryParameter(
                    SessionResourceConstants.SESSION_ID_QUERY_PARAM, id);
            Representation sessionRepr = clientResource.get();
            
            Status responseStatus = clientResource.getResponse().getStatus();
            if (Status.SUCCESS_OK.equals(responseStatus)) {
                DomRepresentation sessionDomRepr = new DomRepresentation(sessionRepr);
                return (Session) parser.getXmlObject(sessionDomRepr, Session.class);
            }
            if (Status.CLIENT_ERROR_BAD_REQUEST.equals(responseStatus)) {
                // TODO throw client bad request exception
            }
            if (Status.CLIENT_ERROR_NOT_FOUND.equals(responseStatus)) {
                return null; // TODO throw id not found, maybe expired or invalid
            }
            if (Status.SERVER_ERROR_INTERNAL.equals(responseStatus)) {
                return null; // TODO throw server internal error
            }
            return null; // TODO throw unknown error
        } catch (Exception ex) {
            throw new SessionCacheAccessException(ex);
        }
    }

    public void updateSession(Session session) throws SessionCacheAccessException {
        try {
            Representation sessionRepr = parser.getRepresentation(session);
            ClientResource clientResource = clientResourceFactory.newClientResource();
            Representation resultRepr = clientResource.post(sessionRepr);
            
            Status responseStatus = clientResource.getResponse().getStatus();
            if (Status.SUCCESS_OK.equals(responseStatus)) {
                return;
            }
            if (Status.SERVER_ERROR_INTERNAL.equals(responseStatus)) {
                // TODO throw server internal error exception
            }
            // TODO throw unknown exception
        } catch (Exception ex) {
            throw new SessionCacheAccessException(ex);
        }
    }

    public void removeSessionById(String id) throws SessionCacheAccessException {
        try {
            ClientResource clientResource = clientResourceFactory.newClientResource();
            clientResource.getReference().addQueryParameter(
                    SessionResourceConstants.SESSION_ID_QUERY_PARAM, id);
            Representation resultRepr = clientResource.delete();
            
            Status responseStatus = clientResource.getResponse().getStatus();
            if (Status.SUCCESS_OK.equals(responseStatus)) {
                return;
            }
            if (Status.CLIENT_ERROR_BAD_REQUEST.equals(responseStatus)) {
                // TODO throw client bad request exception
            }
            if (Status.SERVER_ERROR_INTERNAL.equals(responseStatus)) {
                // TODO throw server internal error exception
            }
            // TODO throw unknown exception
        } catch (Exception ex) {
            throw new SessionCacheAccessException(ex);
        }
    }

    public void extendSessionExpireTime(Session session, long extraDelta) throws SessionCacheAccessException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
