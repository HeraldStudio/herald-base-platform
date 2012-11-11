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

import java.util.logging.Level;
import java.util.logging.Logger;
import org.restlet.resource.ClientResource;

/**
 *
 * @author rAy <predator.ray@gmail.com>
 */
public class ClientResourceFactory {

    private static final Logger logger = Logger.getLogger(
            ClientResourceFactory.class.getName());
    private String sessionServiceUri;

    public ClientResourceFactory(String sessionServiceUri) {
        logConstructingFactory(sessionServiceUri);
        this.sessionServiceUri = sessionServiceUri;
    }

    public ClientResource newResource(String uri) {
        return new ClientResource(uri);
    }

    public ClientResource newSessionCollectionResource() {
        StringBuilder uriBuilder = new StringBuilder();
        uriBuilder.append(sessionServiceUri)
                .append(SessionResourceConstants.SESSION_COLLECTION_PATH);
        String collectionUri = uriBuilder.toString();
        logRetrievingResource(collectionUri);
        return new ClientResource(collectionUri);
    }

    public ClientResource newSessionInstanceResource(String sessionId) {
        StringBuilder uriBuilder = new StringBuilder();
        uriBuilder.append(sessionServiceUri)
                .append(SessionResourceConstants.SESSION_COLLECTION_PATH)
                .append("/").append(sessionId);
        String instanceUri = uriBuilder.toString();
        logRetrievingResource(instanceUri);
        return new ClientResource(instanceUri);
    }

    private static void logConstructingFactory(String sessionResourceUri) {
        StringBuilder logBuilder = new StringBuilder();
        logBuilder.append("constructing ClientResourceFactory: ")
                .append(sessionResourceUri);
        logger.log(Level.INFO, logBuilder.toString());
    }

    private static void logRetrievingResource(String resourceUri) {
        StringBuilder logBuilder = new StringBuilder();
        logBuilder.append("retrieving resource: ")
                .append(resourceUri);
        logger.log(Level.INFO, logBuilder.toString());
    }
}
