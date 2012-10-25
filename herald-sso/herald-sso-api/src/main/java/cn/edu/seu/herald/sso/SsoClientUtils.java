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

import cn.edu.seu.herald.sso.SsoServiceConstants;

/**
 *
 * @author rAy <predator.ray@gmail.com>
 */
class SsoClientUtils {
    
    public static String getSsoStudentNodeName(String studentAttriName) {
        StringBuilder builder = new StringBuilder();
        builder.append(SsoServiceConstants.SSO_NODE_PREFIX)
                .append(SsoServiceConstants.SSO_STUDENT_USER_PREFIX)
                .append(studentAttriName);
        return builder.toString();
    }
    
    public static String getSsoPropertiesNodeName(String attributeName) {
        StringBuilder builder = new StringBuilder();
        builder.append(SsoServiceConstants.SSO_NODE_PREFIX)
                .append(SsoServiceConstants.SSO_CONTEXT_PROPERTIES_PREFIX)
                .append(attributeName);
        return builder.toString();
    }

}
