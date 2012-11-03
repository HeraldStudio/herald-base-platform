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
package cn.edu.seu.herald.session.app;

import cn.edu.seu.herald.session.SessionResourceConstants;
import cn.edu.seu.herald.session.core.rest.SessionCollectionResource;
import cn.edu.seu.herald.session.core.rest.SessionInstanceResource;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.restlet.Context;
import org.restlet.routing.Router;

/**
 *
 * @author rAy <predator.ray@gmail.com>
 */
public class SessionRouter extends Router {

    private static final Logger logger = Logger.getLogger(
            SessionRouter.class.getName());

    public SessionRouter(Context appContext) {
        super(appContext);
        logger.log(Level.INFO, "initializing the session router");
        init();
    }

    private void init() {
        logger.log(Level.INFO,
                "attaching SessionCollectionResource to path: "
                + SessionResourceConstants.SESSION_COLLECTION_PATH);
        this.attach(SessionResourceConstants.SESSION_COLLECTION_PATH,
                SessionCollectionResource.class);
        logger.log(Level.INFO,
                "attaching SessionInstanceResource to path: "
                + SessionResourceConstants.SESSION_INSTANCE_PATH);
        this.attach(SessionResourceConstants.SESSION_INSTANCE_PATH,
                SessionInstanceResource.class);
    }
}
