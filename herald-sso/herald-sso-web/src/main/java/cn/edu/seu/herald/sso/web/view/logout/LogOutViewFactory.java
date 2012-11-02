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

package cn.edu.seu.herald.sso.web.view.logout;

import cn.edu.seu.herald.sso.core.SingleSignOnSessionService;

/**
 *
 * @author rAy <predator.ray@gmail.com>
 */
public class LogOutViewFactory {

    public static final String TYPE_WEB_FORM = "form";

    public static final String TYPE_AJAX = "ajax";

    private SingleSignOnSessionService ssoSessionService;

    public LogOutViewFactory(SingleSignOnSessionService ssoSessionService) {
        this.ssoSessionService = ssoSessionService;
    }

    public LogOutView getLogOutViewByName(String name) {
        if (TYPE_WEB_FORM.equals(name)) {
            return new WebFormLogOutView(ssoSessionService);
        }
        if (TYPE_AJAX.equals(name)) {
            return new AjaxLogOutView(ssoSessionService);
        }
        return null;
    }

}
