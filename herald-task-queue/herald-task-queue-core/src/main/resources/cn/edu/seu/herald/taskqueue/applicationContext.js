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
/**
 * @author rAy
 * this javascript is to initialize the task queue script application context,
 * and declare the host objects
 */
var mongo = (function() {
    var contextClzPath = "cn/edu/seu/herald/taskqueue/applicationContext.xml";
    var appContext = new Packages.org.springframework.context.support.
        ClassPathXmlApplicationContext(contextClzPath);
    return appContext.getBean("mongo");
}());