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

package cn.edu.seu.herald.sso.web.view.auth;

import cn.edu.seu.herald.session.Session;
import cn.edu.seu.herald.session.SessionService;
import cn.edu.seu.herald.session.SessionServiceFactory;
import cn.edu.seu.herald.session.exception.SessionAccessException;
import cn.edu.seu.herald.session.jee.SessionServiceClient;
import cn.edu.seu.herald.sso.core.AuthenticationException;
import cn.edu.seu.herald.sso.core.SingleSignOnSessionService;
import cn.edu.seu.herald.sso.core.StudentUserAccountService;
import cn.edu.seu.herald.sso.domain.SingleSignOnContext;
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
public class WebFormAuthenticationView implements AuthenticationView {

    public static final String SUCCESS_REDIRECT_URL_PARAM = "success";

    public static final String FAILURE_REDIRECT_URL_PARAM = "failure";

    private static final Logger logger = Logger.getLogger(
            WebFormAuthenticationView.class.getName());

    private StudentUserAccountService studentUserAccountService;

    private SingleSignOnSessionService ssoSessionService;

    public WebFormAuthenticationView(
            StudentUserAccountService studentUserAccountService,
            SingleSignOnSessionService ssoSessionService) {
        this.studentUserAccountService = studentUserAccountService;
        this.ssoSessionService = ssoSessionService;
    }

    @Override
    public void render(Map<String, Object> model, HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {
        String username = (String) model.get(USERNAME_PARAM_NAME);
        String password = (String) model.get(PASSWORD_PARAM_NAME);
        String successRedirectUrl = (String)
                model.get(SUCCESS_REDIRECT_URL_PARAM);
        String failureRedirectUrl = (String)
                model.get(FAILURE_REDIRECT_URL_PARAM);

        try {
            SingleSignOnContext ssoContext =
                    studentUserAccountService.authenticate(username, password);
            shareSsoContextInSession(request, response, ssoContext);
            response.sendRedirect(successRedirectUrl);
        } catch (AuthenticationException ex) {
            response.sendRedirect(failureRedirectUrl);
        } catch (SessionAccessException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            response.sendRedirect(failureRedirectUrl);
        }
    }

    private void shareSsoContextInSession(HttpServletRequest request,
            HttpServletResponse response, SingleSignOnContext ssoContext)
            throws SessionAccessException {
        SessionServiceFactory sessionFactory =
                SessionServiceFactory.getInstance();
        SessionService sessionService = sessionFactory.getSessionService();
        SessionServiceClient sessionServiceClient =
                new SessionServiceClient(sessionService);
        Session currentSession = sessionServiceClient.getSession(
                request, response);
        ssoSessionService.shareSingleSignOnContextInSession(
                ssoContext, currentSession);
    }

}
