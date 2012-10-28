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

import cn.edu.seu.herald.sso.core.SingleSignOnSessionService;
import cn.edu.seu.herald.sso.core.StudentUserAccountService;
import cn.edu.seu.herald.sso.web.view.AuthenticationView;
import cn.edu.seu.herald.sso.web.view.AuthenticationViewFactory;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author rAy <predator.ray@gmail.com>
 */
public class AuthenticationDispatcher extends HttpServlet {

    public static final String STUDENT_USER_ACCOUNT_SERVICE_PARAM_NAME =
            "student-user-account-service";

    private static final String CONTEXT_CONFIG_PARAM_NAME = "context-config";

    private static final String STUDENT_USER_ACCOUNT_SERVICE_BEAN_NAME =
            "studentUserAccountService";

    private static final String SINGLE_SIGN_ON_SESSION_SERVICE_BEAN_NAME =
            "singleSignOnSessionService";

    private StudentUserAccountService sUserAccountService;

    private SingleSignOnSessionService ssoSessionService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        String contextConfig = config.getInitParameter(CONTEXT_CONFIG_PARAM_NAME);
        ApplicationContext appContext = new ClassPathXmlApplicationContext(
                contextConfig);
        sUserAccountService = (StudentUserAccountService) appContext.getBean(
                STUDENT_USER_ACCOUNT_SERVICE_BEAN_NAME);
        ssoSessionService = (SingleSignOnSessionService) appContext.getBean(
                SINGLE_SIGN_ON_SESSION_SERVICE_BEAN_NAME);
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {
        String typeName = request.getParameter(
                AuthenticationView.INVOKE_TYPE_PARAM_NAME);
        AuthenticationViewFactory factory = new AuthenticationViewFactory(
                sUserAccountService, ssoSessionService);
        AuthenticationView view = factory.getAuthenticationViewByType(typeName);

        Map<String, Object> model = new HashMap<String, Object>();
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            String paramValue = request.getParameter(typeName);
            model.put(paramName, paramValue);
        }

        view.render(model, request, response);
    }

}
