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
import cn.edu.seu.herald.sso.core.SingleSignOnSessionService;
import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author rAy <predator.ray@gmail.com>
 */
public class AjaxLogOutView implements LogOutView {

    public static final String SESSION_ID_PARAM_NAME = "sessionid";

    private SingleSignOnSessionService ssoSessionService;

    public AjaxLogOutView(SingleSignOnSessionService ssoSessionService) {
        this.ssoSessionService = ssoSessionService;
    }

    @Override
    public void render(Map<String, Object> model, HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {
        String sessionId = (String) model.get(SESSION_ID_PARAM_NAME);
        SessionServiceFactory sessionFactory =
                SessionServiceFactory.getInstance();
        SessionService sessionService = sessionFactory.getSessionService();
        try {
            Session currentSession = sessionService.getSessionById(sessionId);
            ssoSessionService.removeSingleSignOnContextInSession(
                    currentSession);
        } catch (SessionAccessException ex) {
            response.sendError(500, ex.getMessage());
        }
    }

}
