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

package cn.edu.seu.herald.sso.web.controller;

import cn.edu.seu.herald.session.ClientResourceFactory;
import cn.edu.seu.herald.session.SessionService;
import cn.edu.seu.herald.sso.core.SingleSignOnSessionService;
import cn.edu.seu.herald.sso.core.StudentUserAccountService;
import cn.edu.seu.herald.sso.core.impl.SingleSignOnSessionServiceImpl;
import cn.edu.seu.herald.sso.core.impl.StudentUserAccountServiceImpl;
import cn.edu.seu.herald.sso.web.SingleSignOnContextListener;
import cn.edu.seu.herald.sso.web.view.auth.AuthenticationView;
import cn.edu.seu.herald.sso.web.view.auth.AuthenticationViewFactory;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author rAy <predator.ray@gmail.com>
 */
public class AuthenticationDispatcher extends HttpServlet {

    private StudentUserAccountService sUserAccountService;

    private SingleSignOnSessionService ssoSessionService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        sUserAccountService = new StudentUserAccountServiceImpl();

        String serviceUrl = config.getServletContext().getInitParameter(
                SingleSignOnContextListener.SESSION_SERVICE_URL_PARAM_NAME);
        ClientResourceFactory clientResourceFactory =
                new ClientResourceFactory(serviceUrl);
        SessionService sessionService = new SessionService(
                clientResourceFactory);
        ssoSessionService = new SingleSignOnSessionServiceImpl(sessionService);
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {
        String typeName = request.getParameter(
                AuthenticationView.INVOKE_TYPE_PARAM_NAME);
        AuthenticationViewFactory factory = new AuthenticationViewFactory(
                sUserAccountService, ssoSessionService);
        AuthenticationView view = factory.getAuthenticationViewByType(typeName);

        if (view == null) {
            response.sendError(500, "no such invoke type: " + typeName);
            return;
        }

        Map<String, Object> model = new HashMap<String, Object>();
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            String paramValue = request.getParameter(paramName);
            model.put(paramName, paramValue);
        }

        view.render(model, request, response);
    }

}
