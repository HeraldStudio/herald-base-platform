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

import cn.edu.seu.herald.sso.core.AuthenticationException;
import cn.edu.seu.herald.sso.core.StudentUserAccountService;
import cn.edu.seu.herald.sso.domain.SingleSignOnContext;
import cn.edu.seu.herald.sso.domain.StudentUser;
import com.wiscom.is.IdentityFactory;
import com.wiscom.is.IdentityManager;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rAy <predator.ray@gmail.com>
 */
public class StudentUserAccountServiceImpl
        implements StudentUserAccountService {

    private static final Logger logger = Logger.getLogger(
            StudentUserAccountServiceImpl.class.getName());

    private static final String CONFIG_LOCATION =
            "/config/com.wiscom.is.client.properties";

    private IdentityFactory factory;

    public StudentUserAccountServiceImpl() {
        logger.log(Level.INFO, "constructing IdentityFactory");
        try {
            String userHome = System.getProperty("user.home");
            factory = IdentityFactory.createFactory(userHome + CONFIG_LOCATION);
        logger.log(Level.INFO, "IdentityFactory constructed");
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            throw new RuntimeException("fail to create IdentityFactory");
        }
    }

    @Override
    public SingleSignOnContext authenticate(String cardNumber,
            String password) throws AuthenticationException {
        logger.log(Level.INFO, new StringBuilder()
                .append("authenticating ")
                .append(cardNumber).toString());
        IdentityManager im = factory.getIdentityManager();
        boolean pass = im.checkPassword(cardNumber, password);
        if (!pass) {
            logger.log(Level.INFO, new StringBuilder()
                    .append("fail to authenticate ")
                    .append(cardNumber).toString());
            throw new AuthenticationException();
        }

        String fullName = im.getUserNameByID(cardNumber);
        StudentUser user = new StudentUser();
        user.setCardNumber(Integer.valueOf(cardNumber));
        user.setFullName(fullName);
        ConcreteSsoContext ssoContext = new ConcreteSsoContext();
        ssoContext.setLogOnStudentUser(user);

        logger.log(Level.INFO, new StringBuilder()
                .append("user(")
                .append(cardNumber).append(", ")
                .append(fullName).append(") authorized").toString());

        return ssoContext;
    }

}
