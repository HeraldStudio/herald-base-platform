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
package cn.edu.seu.herald.session;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Adaptes a java.util.Map Object and a keyvalue-paired object, implements the
 * marshal and unmarshal of the properties node
 *
 * @author rAy <predator.ray@gmail.com>
 */
class MapAdapter extends XmlAdapter<Object, Map<String, Object>> {

    private static final String ROOT_NODE_NAME = "properties";

    @Override
    public Map<String, Object> unmarshal(Object domTree) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        Element rootElement = (Element) domTree;
        NodeList childNodes = rootElement.getChildNodes();
        for (int x = 0; x < childNodes.getLength(); ++x) {
            Node childNode = childNodes.item(x);
            if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                map.put(childNode.getLocalName(), childNode.getTextContent());
            }
        }
        return map;
    }

    @Override
    public Object marshal(Map<String, Object> map) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.newDocument();
        Element customXml = doc.createElement(ROOT_NODE_NAME);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            Element keyValuePair = doc.createElement(entry.getKey());
            appendChild(doc, keyValuePair, entry.getValue());
            customXml.appendChild(keyValuePair);
        }
        return customXml;
    }

    private static void appendChild(Document doc, Element keyValuePair,
            Object obj) {
        try {
            JAXBContext jc = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(obj, keyValuePair);
        } catch (JAXBException ex) {
            keyValuePair.appendChild(doc.createTextNode(obj.toString()));
        }
    }
}