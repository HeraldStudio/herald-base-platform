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
package cn.edu.seu.herald.session.core.rest;

import cn.edu.seu.herald.session.SessionUpdateResult;
import cn.edu.seu.herald.session.util.DomRepresentationParser;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.xml.sax.SAXException;

/**
 *
 * @author rAy <predator.ray@gmail.com>
 */
public class SessionResourceUtils {

    private static final Logger logger = Logger.getLogger(
            SessionResourceUtils.class.getName());
    private static final DomRepresentationParser parser =
            new DomRepresentationParser();

    public static Representation getErrorRepresentation(Exception cause) {
        try {
            SessionUpdateResult failure = new SessionUpdateResult(cause);
            return parser.getRepresentation(failure);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            return getDefaultErrorRepresentation();
        }
    }

    public static Representation getErrorRepresentation(String msg) {
        try {
            SessionUpdateResult failure =
                    new SessionUpdateResult(SessionUpdateResult.ResultType.FAILURE, msg);
            return parser.getRepresentation(failure);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            return getDefaultErrorRepresentation();
        }
    }
    private static final String DEFAULT_ERROR_MSG = "unknown error";

    public static Representation getDefaultErrorRepresentation() {
        return new StringRepresentation(DEFAULT_ERROR_MSG);
    }

    public static Representation getSuccessRepresentation()
            throws JAXBException, IOException, IOException,
            ParserConfigurationException, SAXException {
        SessionUpdateResult success =
                new SessionUpdateResult(SessionUpdateResult.ResultType.SUCCESS);
        return parser.getRepresentation(success);
    }
}
