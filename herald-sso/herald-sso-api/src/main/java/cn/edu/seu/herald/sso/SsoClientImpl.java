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

import cn.edu.seu.herald.session.Session;
import cn.edu.seu.herald.session.SessionService;
import cn.edu.seu.herald.session.exception.SessionAccessException;
import cn.edu.seu.herald.sso.domain.SingleSignOnContext;
import cn.edu.seu.herald.sso.domain.StudentUser;
import cn.edu.seu.herald.sso.exception.SingleSignOnServiceException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rAy <predator.ray@gmail.com>
 */
class SsoClientImpl implements SsoClient {

    private static final Logger logger = Logger.getLogger(
            SsoClientImpl.class.getName());

    private SessionService sessionService;

    public SsoClientImpl(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Override
    public SingleSignOnContext authenticate(String username, String password)
            throws SingleSignOnServiceException {
        // GET /authentication?username={username}&password={password}
        // response will be like:
        //    HTTP/1.1 200 OK
        //    <singleSignOnContext>
        //       <studentUser>...</studentUser>
        //    </singleSignOnContext>
        // or:
        //    HTTP/1.1 401 Not Authorzied
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void shareSignOnContext(Session currentSession,
            SingleSignOnContext singleSignOnContext)
            throws SingleSignOnServiceException {
        // get all information of the single sign-on context
        StudentUser studentUser = singleSignOnContext.getLogOnStudentUser();
        int cardNumber = studentUser.getCardNumber();
        String studentId = studentUser.getStudentId();
        String fullName = studentUser.getFullName();
        Enumeration<String> attributeNames = singleSignOnContext.getAttributeNames();
        
        // write then into the session
        // write the properties of the student user
        currentSession.setAttribute(SsoClientUtils.getSsoStudentNodeName(
                SsoServiceConstants.CARD_NUMBER_NODE_NAME),
                cardNumber);
        currentSession.setAttribute(SsoClientUtils.getSsoStudentNodeName(
                SsoServiceConstants.STUDENT_ID_NODE_NAME),
                studentId);
        currentSession.setAttribute(SsoClientUtils.getSsoStudentNodeName(
                SsoServiceConstants.STUDENT_FULL_NAME_NODE_NAME),
                fullName);
        // write the attributes of the context
        while (attributeNames.hasMoreElements()) {
            String attriName = attributeNames.nextElement();
            Object attriValue = singleSignOnContext.getAttribute(attriName);
            currentSession.setAttribute(
                    SsoClientUtils.getSsoPropertiesNodeName(attriName), attriValue);
        }
        
        // update the session
        try {
            sessionService.updateSession(currentSession);
        } catch (SessionAccessException ex) {
            logger.log(Level.SEVERE, null, ex);
            throw new SingleSignOnServiceException("session service is not available",
                    ex);
        }
    }
 
}
