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
import cn.edu.seu.herald.session.exception.SessionCacheAccessException;

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
