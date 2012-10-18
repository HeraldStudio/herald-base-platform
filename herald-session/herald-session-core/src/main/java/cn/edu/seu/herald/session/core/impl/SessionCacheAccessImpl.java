/*
 * Copyright (C) 2012 Herald, Southeast University
 */

package cn.edu.seu.herald.session.core.impl;

import cn.edu.seu.herald.session.Session;
import cn.edu.seu.herald.session.SessionCacheAccess;
import cn.edu.seu.herald.session.SessionCacheAccessException;

/**
 *
 * @author rAy <predator.ray@gmail.com>
 */
public class SessionCacheAccessImpl implements SessionCacheAccess {

    @Override
    public Session getSessionById(String id) throws SessionCacheAccessException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void storeSession(Session session, long expireDelta) throws SessionCacheAccessException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeSessionById(String id) throws SessionCacheAccessException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void extendSessionExpireTime(Session session, long extraDelta) throws SessionCacheAccessException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
