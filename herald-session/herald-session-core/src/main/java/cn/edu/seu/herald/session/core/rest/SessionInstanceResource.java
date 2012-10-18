/*
 * Copyright (C) 2012 Herald, Southeast University
 */

package cn.edu.seu.herald.session.core.rest;

import cn.edu.seu.herald.session.Session;
import cn.edu.seu.herald.session.SessionCacheAccess;
import cn.edu.seu.herald.session.SessionCacheAccessException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.restlet.data.Parameter;
import org.restlet.representation.Representation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

/**
 *
 * @author rAy <predator.ray@gmail.com>
 */
public class SessionInstanceResource extends ServerResource {
    
    private static final String SESSION_ID_PARAM = "id";
    
    private static final Logger logger = Logger.getLogger(
            SessionInstanceResource.class.getName());
    
    private SessionCacheAccess sessionCacheAccess;
    
    public void setSessionCacheAccess(SessionCacheAccess sessionCacheAccess) {
        this.sessionCacheAccess = sessionCacheAccess;
    }
    
    @Get("xml")
    public Representation getSession() {
        try {
            Parameter sessionIdParam = getQuery().getFirst(SESSION_ID_PARAM);
            String sessionId = sessionIdParam.getValue();
            if (sessionId == null) {
                return null; // TODO return error representation
            }
            Session cachedSession = sessionCacheAccess.getSessionById(sessionId);
            if (cachedSession == null) {
                return null; // TODO return error representation
            }

            // TODO return the xml representation of the cachedSession
            return null;
        } catch (SessionCacheAccessException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            return null; // TODO return error representation
        }
    }
    
    @Post("xml:xml")
    public Representation createOrUpdateSession(Representation sessionRepr) {
        try {
            if (true) { // TODO if there is no session representation in the request
                long currentTime = System.currentTimeMillis();
                UUID uuid  =  UUID.randomUUID();
                String newSessionId = uuid.toString();

                Session newSession = new Session(newSessionId, currentTime);
                sessionCacheAccess.storeSession(newSession, 0); // TODO formulate the expire time

                // TODO return the representation of the new session
                return null;
            }

            // TODO if there is a session representation in the request
            Session newSession = null; // TODO get the new session to update
            sessionCacheAccess.storeSession(newSession, 0); // TODO formulate the expire time
            return null; // TODO return the representation of the new session
        } catch (SessionCacheAccessException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            return null; // TODO return error representation
        }
    }
    
    @Delete("xml:xml")
    public Representation deleteSession() {
        try {
            Parameter sessionIdParam = getQuery().getFirst(SESSION_ID_PARAM);
            String sessionId = sessionIdParam.getValue();
            if (sessionId == null) {
                return null; // TODO return error representation
            }

            sessionCacheAccess.removeSessionById(sessionId);
            return null; // TODO return a representation of success
        } catch (SessionCacheAccessException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            return null; // TODO return error representation
        }
    }

}
