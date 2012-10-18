/*
 * Copyright (C) 2012 Herald, Southeast University
 */

package cn.edu.seu.herald.session.impl;

import cn.edu.seu.herald.session.ClientResourceFactory;
import cn.edu.seu.herald.session.ISessionResource;
import cn.edu.seu.herald.session.Session;
import cn.edu.seu.herald.session.SessionCacheAccess;
import cn.edu.seu.herald.session.SessionCacheAccessException;
import cn.edu.seu.herald.session.util.DomRepresentationParser;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;

/**
 *
 * @author rAy <predator.ray@gmail.com>
 */
public class SessionCacheAccessImpl implements SessionCacheAccess,
        ISessionResource {
    
    private ClientResourceFactory clientResourceFactory;
    
    private DomRepresentationParser parser;
    
    public SessionCacheAccessImpl() {}
    
    public SessionCacheAccessImpl(ClientResourceFactory clientResourceFactory) {
        this.clientResourceFactory = clientResourceFactory;
    }
    
    public void setClientResource(ClientResourceFactory clientResourceFactory) {
        this.clientResourceFactory = clientResourceFactory;
    }
    
    public void setDomRepresentationParser(DomRepresentationParser parser) {
        this.parser = parser;
    }

    @Override
    public Session getSessionById(String id) throws SessionCacheAccessException {
        try {
            ClientResource clientResource = clientResourceFactory.newClientResource();
            clientResource.getReference().addQueryParameter(
                    ISessionResource.SESSION_ID_QUERY_PARAM, id);
            Representation sessionRepr = clientResource.get();
            DomRepresentation sessionDomRepr = new DomRepresentation(sessionRepr);
            return (Session) parser.getXmlObject(sessionDomRepr, Session.class);
        } catch (Exception ex) {
            throw new SessionCacheAccessException(ex);
        }
    }

    @Override
    public void storeSession(Session session, long expireDelta) throws SessionCacheAccessException {
        try {
            Representation sessionRepr = parser.getRepresentation(session);
            ClientResource clientResource = clientResourceFactory.newClientResource();
            Representation resultRepr = clientResource.post(sessionRepr);
            // TODO if the result is failure, get the cause and throw the exception
        } catch (Exception ex) {
            throw new SessionCacheAccessException(ex);
        }
    }

    @Override
    public void removeSessionById(String id) throws SessionCacheAccessException {
        try {
            ClientResource clientResource = clientResourceFactory.newClientResource();
            clientResource.getReference().addQueryParameter(
                    ISessionResource.SESSION_ID_QUERY_PARAM, id);
            Representation resultRepr = clientResource.delete();
            // TODO if the result is failure, get the cause and throw the exception
        } catch (Exception ex) {
            throw new SessionCacheAccessException(ex);
        }
    }

    @Override
    public void extendSessionExpireTime(Session session, long extraDelta) throws SessionCacheAccessException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
