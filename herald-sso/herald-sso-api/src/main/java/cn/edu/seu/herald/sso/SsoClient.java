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

import cn.edu.seu.herald.sso.domain.SingleSignOnContext;

/**
 *
 * @author rAy <predator.ray@gmail.com>
 */
public class SsoClient {

    public SsoClient() {
        
    }

    public SingleSignOnContext authenticate(String username, String password) {
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
 
}
