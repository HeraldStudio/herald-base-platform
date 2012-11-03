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

import org.restlet.resource.ClientResource;

/**
 *
 * @author rAy <predator.ray@gmail.com>
 */
public class ClientResourceFactory {

    private String sessionResourceUri;

    public ClientResourceFactory(String sessionResourceUri) {
        this.sessionResourceUri = sessionResourceUri;
    }

    public ClientResource newResource(String uri) {
        return new ClientResource(uri);
    }

    public ClientResource newSessionCollectionResource() {
        return new ClientResource(sessionResourceUri);
    }

    public ClientResource newSessionInstanceResource(String sessionId) {
        StringBuilder uriBuilder = new StringBuilder();
        uriBuilder.append(sessionResourceUri).append("/").append(sessionId);
        return new ClientResource(uriBuilder.toString());
    }
}
