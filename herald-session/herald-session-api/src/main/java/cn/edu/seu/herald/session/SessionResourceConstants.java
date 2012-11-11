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

/**
 *
 * @author rAy <predator.ray@gmail.com>
 */
public interface SessionResourceConstants {

    String SESSION_COLLECTION_PATH = "/sessions";
    String SESSION_ID_PARAM_NAME = "session-id";
    String SESSION_INSTANCE_PATH = SESSION_COLLECTION_PATH +
            "/{" + SESSION_ID_PARAM_NAME + "}";
    String DEFAULT_SERVICE_BASE_URI = "http://127.0.0.1/sessionservice";
    String DEFAULT_SESSION_COLLECTION_URI = DEFAULT_SERVICE_BASE_URI +
            SESSION_COLLECTION_PATH;
    String DEFAULT_SESSION_INSTANCE_URI = DEFAULT_SERVICE_BASE_URI +
            SESSION_INSTANCE_PATH;
    int SESSION_EXPIRE_TIME_IN_SECONDs = 60 * 30;
}
