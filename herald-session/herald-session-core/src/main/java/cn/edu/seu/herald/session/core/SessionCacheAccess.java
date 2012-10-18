/*
 * Copyright (C) 2012 Herald, Southeast University
 */
package cn.edu.seu.herald.session.core;

import cn.edu.seu.herald.session.Session;
import cn.edu.seu.herald.session.exception.SessionCacheAccessException;

/**
 * Provides the access to the session cache
 * @author rAy <predator.ray@gmail.com>
 */
public interface SessionCacheAccess {

    /**
     * Gets the session by its id
     * @param id the identifier of the session
     * @return the session if exists, or null
     * @throws if an exception occurred during retrieving the session
     */
    Session getSessionById(String id) throws SessionCacheAccessException;
    
    /**
     * Stores the new session in the cache and expires where the time comes,
     * or updates the session if already exists
     * @param session the session object to be stored
     * @param expireDelta the delta time in milliseconds from stored to expired
     * @throws if an exception occurred during storing the session
     */
    void storeSession(Session session, long expireDelta)
            throws SessionCacheAccessException;
    
    /**
     * Removes the session from the cache by its id if exists
     * @param id the id of the session to be removed
     * @throws if an exception occurred during removing the session
     */
    void removeSessionById(String id) throws SessionCacheAccessException;
    
    /**
     * Extends the expire time of a session
     * @param extraDelta the delta time from now on
     * @throws if an exception occurred during extending the session expire time
     */
    void extendSessionExpireTime(Session session, long extraDelta)
             throws SessionCacheAccessException;

}
