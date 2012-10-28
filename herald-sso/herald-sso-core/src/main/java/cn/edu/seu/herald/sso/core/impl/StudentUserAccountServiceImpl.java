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

import cn.edu.seu.herald.sso.core.StudentUserAccountService;
import cn.edu.seu.herald.sso.domain.SingleSignOnContext;
import cn.edu.seu.herald.sso.domain.StudentUser;
import java.util.Enumeration;

/**
 *
 * @author rAy <predator.ray@gmail.com>
 */
public class StudentUserAccountServiceImpl
        implements StudentUserAccountService {

    @Override
    public SingleSignOnContext authenticate(String cardNumber,
            String password) {
        // a mock implementation
        if ("213100000".equals(cardNumber) && "12345678".equals(password)) {
            return new SingleSignOnContext() {
                public StudentUser getLogOnStudentUser() {
                    StudentUser sUsr = new StudentUser();
                    sUsr.setCardNumber(213100000);
                    sUsr.setFullName("Alice");
                    sUsr.setStudentId("71110300");
                    return sUsr;
                }

                public Object getAttribute(String name) {
                    return null;
                }

                public Enumeration<String> getAttributeNames() {
                    return new Enumeration<String>() {
                        public boolean hasMoreElements() {
                            return false;
                        }

                        public String nextElement() {
                            return null;
                        }
                    };
                }
            };
        }
        return null;
    }

}
