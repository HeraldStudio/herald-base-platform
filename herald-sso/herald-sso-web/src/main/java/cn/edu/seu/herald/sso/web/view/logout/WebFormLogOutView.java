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

package cn.edu.seu.herald.sso.web.view.logout;

import cn.edu.seu.herald.session.Session;
import cn.edu.seu.herald.session.SessionService;
import cn.edu.seu.herald.session.SessionServiceFactory;
import cn.edu.seu.herald.session.exception.SessionAccessException;
import cn.edu.seu.herald.session.jee.SessionServiceClient;
import cn.edu.seu.herald.sso.core.SingleSignOnSessionService;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author rAy <predator.ray@gmail.com>
 */
public class WebFormLogOutView implements LogOutView {

    public static final String REDIRECT_URL_PARAM_NAME = "url";

    private static final Logger logger = Logger.getLogger(
            WebFormLogOutView.class.getName());

    private SingleSignOnSessionService ssoSessionService;

    private SessionServiceClient sessionServiceClient;

    public WebFormLogOutView(SingleSignOnSessionService ssoSessionService) {
        this.ssoSessionService = ssoSessionService;

        SessionServiceFactory sessionServiceFactory =
                SessionServiceFactory.getInstance();
        SessionService sessionService = sessionServiceFactory
                .getSessionService();
        sessionServiceClient =
                new SessionServiceClient(sessionService);
    }

    @Override
    public void render(Map<String, Object> model, HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {
        try {
            Session currentSession = sessionServiceClient.getSession(
                    request, response);
            ssoSessionService.removeSingleSignOnContextInSession(
                    currentSession);
        } catch (SessionAccessException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        String redirectUrl = (String) model.get(REDIRECT_URL_PARAM_NAME);
        response.sendRedirect(redirectUrl);
    }

}
