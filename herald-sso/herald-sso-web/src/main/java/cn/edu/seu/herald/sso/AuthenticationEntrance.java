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
package cn.edu.seu.herald.sso;

import cn.edu.seu.herald.sso.account.StudentUserAccountService;
import cn.edu.seu.herald.sso.domain.SingleSignOnContext;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author rAy <predator.ray@gmail.com>
 */
public class AuthenticationEntrance extends HttpServlet {

    private static final String USERNAME_PARAM = "username";
    
    private static final String PASSWORD_PARAM = "password";
    
    private static final String CONTEXT_CONFIG_PARAM_NAME = "context-config";
    
    private static final String STUDENT_USER_ACCOUNT_SERVICE_BEAN_NAME =
            "studentUserAccountService";
    
    private StudentUserAccountService sUserAccountService;
    
    @Override
    public void init(ServletConfig config) {
        String contextConfig = config.getInitParameter(CONTEXT_CONFIG_PARAM_NAME);
        ApplicationContext appContext = new ClassPathXmlApplicationContext(
                contextConfig);
        sUserAccountService = (StudentUserAccountService) appContext.getBean(
                STUDENT_USER_ACCOUNT_SERVICE_BEAN_NAME);
    }

    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
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
        response.setContentType("text/xml");
        String username = request.getParameter(USERNAME_PARAM);
        String password = request.getParameter(PASSWORD_PARAM);
        SingleSignOnContext ssoContext =
                sUserAccountService.authenticate(username, password);
        boolean authenticated = (ssoContext != null);
        
        if (!authenticated) {
            response.sendError(401);
            return;
        }
        
        PrintWriter out  = response.getWriter();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(
                    SingleSignOnContext.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(ssoContext, out);
        } catch (Exception ex) {
            response.sendError(500, ex.getMessage());
        } finally {
            out.close();
        }
    }

}
