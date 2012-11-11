/*
 * Copyright 2012 Herald Studio, Southeast University.
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
package cn.edu.seu.herald.sso.web;

import cn.edu.seu.herald.session.SessionResourceConstants;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author rAy <predator.ray@gmail.com>
 */
public class SingleSignOnContextListener implements ServletContextListener {

    public static final String SESSION_SERVICE_URL_PARAM_NAME =
            "cn.edu.seu.herald.session.sessionServiceUrl";
    private static final Logger logger = Logger.getLogger(
            SingleSignOnContextListener.class.getName());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();
        String sessionServiceUrl = ctx.getInitParameter(
                SESSION_SERVICE_URL_PARAM_NAME);

        if (sessionServiceUrl != null) {
            String info = new StringBuilder()
                    .append("using sessionResourceUrl: ")
                    .append(sessionServiceUrl).toString();
            logger.log(Level.INFO, info);
            return;
        }
        ctx.setInitParameter(SESSION_SERVICE_URL_PARAM_NAME,
                SessionResourceConstants.DEFAULT_SERVICE_BASE_URI);
        String info = new StringBuilder()
                .append("using default sessionResourceUrl: ")
                .append(sessionServiceUrl).toString();
        logger.log(Level.INFO, info);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
