/*
 * Copyright 2004,2005 The Apache Software Foundation.
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
package org.apache.axis2.rmi.metadata.xml;

import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.apache.axis2.rmi.exception.SchemaGenerationException;
import org.apache.axis2.rmi.util.Constants;

import javax.xml.namespace.QName;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Iterator;


public interface XmlType {

    public void addElement(XmlElement xmlElement);

    public void addAttribute(XmlAttribute xmlAttribute);

    /**
     * this generates the complex type only if it is annonymous and
     * is not a simple type
     * @param document
     * @param namespacesToPrefixMap
     * @throws SchemaGenerationException
     */
    public void generateWSDLSchema(Document document, Map namespacesToPrefixMap) throws SchemaGenerationException;

    public QName getQname();

    public void setQname(QName qname);

    public boolean isAnonymous();

    public void setAnonymous(boolean anonymous);

    public boolean isSimpleType();

    public void setSimpleType(boolean simpleType);

    public List getElements();

    public void setElements(List elements);

    public Element getTypeElement();

    public void setTypeElement(Element typeElement);

    public XmlType getParentType();

    public void setParentType(XmlType parentType);

    public List getAttributes();

    public void setAttributes(List attributes);
    
}
