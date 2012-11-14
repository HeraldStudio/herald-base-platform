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
package cn.edu.seu.herald.session.core.rest;

import cn.edu.seu.herald.session.Session;
import cn.edu.seu.herald.session.SessionResourceConstants;
import java.util.UUID;

/**
 *
 * @author rAy <predator.ray@gmail.com>
 */
public class SessionFactory {

    private String sessionServiceUrl;

    public SessionFactory() {
        this.sessionServiceUrl =
                SessionResourceConstants.DEFAULT_SERVICE_BASE_URI;
    }

    public SessionFactory(String sessionServiceUrl) {
        this.sessionServiceUrl = sessionServiceUrl;
    }

    public Session newSession() {
        long currentTime = System.currentTimeMillis();
        UUID uuid = UUID.randomUUID();
        String newSessionId = uuid.toString();
        Session newSession = new Session(newSessionId, currentTime);

        StringBuilder uriBuilder = new StringBuilder();
        uriBuilder.append(sessionServiceUrl)
                .append(SessionResourceConstants.SESSION_COLLECTION_PATH)
                .append("/")
                .append(newSessionId);
        newSession.setUri(uriBuilder.toString());
        return newSession;
    }
}
