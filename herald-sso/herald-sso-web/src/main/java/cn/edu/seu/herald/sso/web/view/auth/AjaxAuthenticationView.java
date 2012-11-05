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
import cn.edu.seu.herald.sso.core.AuthenticationException;
import cn.edu.seu.herald.sso.core.SingleSignOnSessionService;
import cn.edu.seu.herald.sso.core.StudentUserAccountService;
import cn.edu.seu.herald.sso.domain.SingleSignOnContext;
import cn.edu.seu.herald.sso.impl.XmlSsoContext;
import cn.edu.seu.herald.sso.impl.XmlSsoContextParser;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

/**
 *
 * @author rAy <predator.ray@gmail.com>
 */
public class AjaxAuthenticationView implements AuthenticationView {

    private static final Logger logger = Logger.getLogger(
            AjaxAuthenticationView.class.getName());
    public static final String SESSION_ID_PARAM_NAME = "sessionid";
    private StudentUserAccountService studentUserAccountService;
    private SingleSignOnSessionService ssoSessionService;

    public AjaxAuthenticationView(
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
        String sessionId = (String) model.get(SESSION_ID_PARAM_NAME);
        try {
            SingleSignOnContext ssoContext =
                    studentUserAccountService.authenticate(username, password);
            shareSsoContextInSession(ssoContext, sessionId);
            printSsoContext(response, ssoContext);
        } catch (AuthenticationException ex) {
            response.sendError(401);
        } catch (SessionAccessException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            response.sendError(500, ex.getMessage());
        }
    }

    private void shareSsoContextInSession(SingleSignOnContext ssoContext,
            String sessionId) throws SessionAccessException {
        SessionServiceFactory sessionFactory =
                SessionServiceFactory.getInstance();
        SessionService sessionService = sessionFactory.getSessionService();
        Session currentSession = sessionService.getSessionById(sessionId);
        ssoSessionService.shareSingleSignOnContextInSession(
                ssoContext, currentSession);
    }

    private void printSsoContext(HttpServletResponse response,
            SingleSignOnContext ssoContext) throws IOException {
        PrintWriter out = response.getWriter();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(
                    XmlSsoContext.class);
            XmlSsoContextParser ssoContextParser = new XmlSsoContextParser();
            XmlSsoContext xmlSsoContext = ssoContextParser.parse(ssoContext);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(xmlSsoContext, out);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            response.sendError(500, ex.getMessage());
        } finally {
            out.close();
        }
    }
}
