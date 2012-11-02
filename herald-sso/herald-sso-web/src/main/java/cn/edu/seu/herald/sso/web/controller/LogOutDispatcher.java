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
package cn.edu.seu.herald.sso.web.controller;

import cn.edu.seu.herald.session.SessionService;
import cn.edu.seu.herald.session.SessionServiceFactory;
import cn.edu.seu.herald.sso.core.SingleSignOnSessionService;
import cn.edu.seu.herald.sso.core.impl.SingleSignOnSessionServiceImpl;
import cn.edu.seu.herald.sso.web.view.logout.LogOutView;
import cn.edu.seu.herald.sso.web.view.logout.LogOutViewFactory;
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
public class LogOutDispatcher extends HttpServlet {

    private SingleSignOnSessionService ssoSessionService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        SessionServiceFactory sessionServiceFactory =
                SessionServiceFactory.getInstance();
        SessionService sessionService =
                sessionServiceFactory.getSessionService();
        ssoSessionService = new SingleSignOnSessionServiceImpl(sessionService);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String typeName = request.getParameter(
                LogOutView.INVOKE_TYPE_PARAM_NAME);
        LogOutViewFactory logOutViewfactory = new LogOutViewFactory(
                ssoSessionService);
        LogOutView logOutView = logOutViewfactory.getLogOutViewByName(typeName);

        if (logOutView == null) {
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

        logOutView.render(model, request, response);
    }

}
