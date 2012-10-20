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
import cn.edu.seu.herald.session.core.rest.SessionResource;
import org.restlet.Context;
import org.restlet.routing.Router;

/**
 *
 * @author rAy <predator.ray@gmail.com>
 */
public class SessionRouter extends Router {
    
    public SessionRouter(Context appContext) {
        super(appContext);
        init();
    }
    
    private void init() {
        this.attach(SessionResourceConstants.SESSION_RESOURCE_PATH,
                SessionResource.class);
    }

}
