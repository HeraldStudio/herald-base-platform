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

package cn.edu.seu.herald.sso.impl;

import cn.edu.seu.herald.sso.domain.SingleSignOnContext;
import java.util.Enumeration;

/**
 *
 * @author rAy <predator.ray@gmail.com>
 */
public class XmlSsoContextParser {

    public XmlSsoContext parse(SingleSignOnContext ssoContext) {
        XmlSsoContext xmlSsoContext = new XmlSsoContext();
        xmlSsoContext.setLogOnStudentUser(ssoContext.getLogOnStudentUser());
        Enumeration<String> attribNames = ssoContext.getAttributeNames();
        while (attribNames.hasMoreElements()) {
            String attribName = attribNames.nextElement();
            xmlSsoContext.setAttribute(attribName,
                    ssoContext.getAttribute(attribName));
        }
        return xmlSsoContext;
    }
}
