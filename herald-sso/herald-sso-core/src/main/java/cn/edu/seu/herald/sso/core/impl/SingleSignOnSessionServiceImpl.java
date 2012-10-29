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

package cn.edu.seu.herald.sso.core.impl;

import cn.edu.seu.herald.session.Session;
import cn.edu.seu.herald.session.SessionService;
import cn.edu.seu.herald.session.exception.SessionAccessException;
import cn.edu.seu.herald.sso.SsoServiceConstants;
import cn.edu.seu.herald.sso.core.SingleSignOnSessionService;
import cn.edu.seu.herald.sso.domain.SingleSignOnContext;
import cn.edu.seu.herald.sso.domain.StudentUser;
import java.util.Enumeration;

/**
 *
 * @author rAy <predator.ray@gmail.com>
 */
public class SingleSignOnSessionServiceImpl
        implements SingleSignOnSessionService {

    private SessionService sessionService;

    public SingleSignOnSessionServiceImpl(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    public void shareSingleSignOnContextInSession(
            SingleSignOnContext ssoContext, Session session)
            throws SessionAccessException {
        StudentUser studentUser = ssoContext.getLogOnStudentUser();
        int cardNumber = studentUser.getCardNumber();
        String fullName = studentUser.getFullName();
        Enumeration<String> attributeNames = ssoContext.getAttributeNames();

        // write then into the session
        // write the properties of the student user
        session.setAttribute(
                SsoServiceConstants.CARD_NUMBER_NODE_NAME,
                cardNumber);
        session.setAttribute(
                SsoServiceConstants.STUDENT_FULL_NAME_NODE_NAME,
                fullName);
        // write the attributes of the context
        while (attributeNames.hasMoreElements()) {
            String attriName = attributeNames.nextElement();
            Object attriValue = ssoContext.getAttribute(attriName);
            session.setAttribute(
                    SsoServiceConstants.SSO_CONTEXT_PROPERTIES_PREFIX +
                    attriName, attriValue);
        }

        // update the session
        sessionService.updateSession(session);
    }

}
