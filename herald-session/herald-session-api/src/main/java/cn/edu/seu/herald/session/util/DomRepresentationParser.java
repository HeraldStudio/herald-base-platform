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
package cn.edu.seu.herald.session.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.restlet.ext.xml.DomRepresentation;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author rAy <predator.ray@gmail.com>
 */
public class DomRepresentationParser {

    public Object getXmlObject(DomRepresentation representation, Class<?> cls)
            throws IOException, JAXBException {
        InputSource inputSource = representation.getInputSource();
        JAXBContext jaxbContext = JAXBContext.newInstance(cls);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return jaxbUnmarshaller.unmarshal(inputSource);
    }

    public DomRepresentation getRepresentation(Object obj)
            throws JAXBException, IOException, ParserConfigurationException,
            SAXException {
        JAXBContext jaxbContext = JAXBContext.newInstance(obj.getClass());
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        Writer stringWriter = new StringWriter();
        try {
            jaxbMarshaller.marshal(obj, stringWriter);
        } finally {
            stringWriter.close();
        }

        String xmlString = stringWriter.toString();
        DocumentBuilderFactory docBuilderFactory =
                DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        Document outputDocument = docBuilder.parse(new ByteArrayInputStream(
                xmlString.getBytes("UTF-8")));
        DomRepresentation outputRepresentation = new DomRepresentation();
        outputRepresentation.setDocument(outputDocument);
        return outputRepresentation;
    }
}
