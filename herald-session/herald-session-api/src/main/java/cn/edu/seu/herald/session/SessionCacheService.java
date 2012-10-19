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

import cn.edu.seu.herald.session.exception.BadRequestException;
import cn.edu.seu.herald.session.exception.InvalidSessionIdException;
import cn.edu.seu.herald.session.exception.ServerInternalErrorException;
import cn.edu.seu.herald.session.exception.SessionCacheAccessException;
import cn.edu.seu.herald.session.exception.UnknownStatusException;
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
    
    private static Exception getStatusException(Status responseStatus) {
        if (Status.CLIENT_ERROR_BAD_REQUEST.equals(responseStatus)) {
            return new BadRequestException();
        }
        if (Status.CLIENT_ERROR_NOT_FOUND.equals(responseStatus)) {
            return new InvalidSessionIdException();
        }
        if (Status.SERVER_ERROR_INTERNAL.equals(responseStatus)) {
            return new ServerInternalErrorException();
        }
        return new UnknownStatusException();
    }
    
    public SessionCacheService() {}
    
    public SessionCacheService(ClientResourceFactory clientResourceFactory) {
        this.clientResourceFactory = clientResourceFactory;
    }
    
    public void setClientResourceFactory(
            ClientResourceFactory clientResourceFactory) {
        this.clientResourceFactory = clientResourceFactory;
    }
    
    public void setDomRepresentationParser(DomRepresentationParser parser) {
        this.parser = parser;
    }
    
    /**
     * Retrieves a new session from the session service
     * @return a new session
     * @throws SessionCacheAccessException 
     */
    public Session getSession() throws SessionCacheAccessException {
        try {
            ClientResource clientResource = clientResourceFactory.newClientResource();
            Representation sessionRepr = clientResource.get();
            
            Status responseStatus = clientResource.getResponse().getStatus();
            if (Status.SUCCESS_OK.equals(responseStatus)) {
                DomRepresentation sessionDomRepr = new DomRepresentation(sessionRepr);
                return (Session) parser.getXmlObject(sessionDomRepr, Session.class);
            }
            throw getStatusException(responseStatus);
        } catch (Exception ex) {
            throw new SessionCacheAccessException(ex);
        }
    }

    /**
     * Retrieves session by its identifier from the session service
     * @param id the identifier of the session
     * @return the session of the identifier
     * @throws SessionCacheAccessException when session is not found
     * or server error occurs
     */
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
            throw getStatusException(responseStatus);
        } catch (Exception ex) {
            throw new SessionCacheAccessException(ex);
        }
    }

    /**
     * Updates the session
     * @param session the session to be updated
     * @throws SessionCacheAccessException when session is invalid 
     * or server error occurs
     */
    public void updateSession(Session session) throws SessionCacheAccessException {
        try {
            Representation sessionRepr = parser.getRepresentation(session);
            ClientResource clientResource = clientResourceFactory.newClientResource();
            Representation resultRepr = clientResource.post(sessionRepr);
            
            Status responseStatus = clientResource.getResponse().getStatus();
            if (Status.SUCCESS_OK.equals(responseStatus)) {
                return;
            }
            throw getStatusException(responseStatus);
        } catch (Exception ex) {
            throw new SessionCacheAccessException(ex);
        }
    }

    /**
     * Removes the session by its identifier
     * @param id the identifier of the session
     * @throws SessionCacheAccessException when session is not found
     * or server error occurs
     */
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
            throw getStatusException(responseStatus);
        } catch (Exception ex) {
            throw new SessionCacheAccessException(ex);
        }
    }

}
