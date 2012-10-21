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

package cn.edu.seu.herald.session.core.impl;

import cn.edu.seu.herald.session.Session;
import cn.edu.seu.herald.session.core.SessionCacheAccess;
import cn.edu.seu.herald.session.exception.SessionAccessException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.rubyeye.xmemcached.MemcachedClient;

/**
 *
 * @author rAy <predator.ray@gmail.com>
 */
public class SessionCacheAccessImpl implements SessionCacheAccess {
    
    private static final String NAMESPACE_PREFIX = "cn.edu.seu.herald.session.";
    
    private static final Logger logger = Logger.getLogger(
            SessionCacheAccessImpl.class.getName());
    
    private MemcachedClient memcachedClient;
    
    public SessionCacheAccessImpl(MemcachedClient memcachedClient) {
        this.memcachedClient = memcachedClient;
    }

    @Override
    public Session getSessionById(String id) throws SessionAccessException {
        try {
            String cacheKey = NAMESPACE_PREFIX + id;
            return (Session) memcachedClient.get(cacheKey);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            throw new SessionAccessException(ex);
        }
    }

    @Override
    public boolean storeSession(Session session, long expireDelta)
            throws SessionAccessException {
        try {
            String cacheKey = NAMESPACE_PREFIX + session.getId();
            int expireTimeInSeconds = (int) (expireDelta / 1000);
            return memcachedClient.add(cacheKey, expireTimeInSeconds, session);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            throw new SessionAccessException(ex);
        }
    }

    @Override
    public void removeSessionById(String id) throws SessionAccessException {
        try {
            String cacheKey = NAMESPACE_PREFIX + id;
            memcachedClient.delete(cacheKey);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            throw new SessionAccessException(ex);
        }
    }

    @Override
    public boolean updateSession(Session session, long expireDelta)
            throws SessionAccessException {
        try {
            String cacheKey = NAMESPACE_PREFIX + session.getId();
            int  expireTimeInSeconds = (int) (expireDelta / 1000);
            return memcachedClient.replace(cacheKey, expireTimeInSeconds, session);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            throw new SessionAccessException(ex);
        }
    }

}
