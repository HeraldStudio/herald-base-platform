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
import org.apache.jcs.JCS;
import org.apache.jcs.access.exception.CacheException;

/**
 *
 * @author rAy <predator.ray@gmail.com>
 */
public class SessionCacheAccessImpl implements SessionCacheAccess {

    private static final String NAMESPACE_PREFIX = "cn.edu.seu.herald.session.";

    private static final Logger logger = Logger.getLogger(
            SessionCacheAccessImpl.class.getName());

    private JCS jcsClient;

    public SessionCacheAccessImpl(JCS jcsClient) {
        this.jcsClient = jcsClient;
    }

    @Override
    public Session getSessionById(String id) throws SessionAccessException {
        return (Session) jcsClient.get(id);
    }

    @Override
    public void storeSession(Session session) throws SessionAccessException {
        try {
            jcsClient.put(session.getId(), session);
        } catch (CacheException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            throw new SessionAccessException(ex);
        }
    }

    @Override
    public void removeSessionById(String id) throws SessionAccessException {
        try {
            jcsClient.remove(id);
        } catch (CacheException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            throw new SessionAccessException(ex);
        }
    }

    @Override
    public void updateSession(Session session)
            throws SessionAccessException {
        try {
            Object cachedSession = jcsClient.get(session);
            if (cachedSession != null) {
                jcsClient.put(session.getId(), session);
            }
        } catch (CacheException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            throw new SessionAccessException(ex);
        }
    }

}
