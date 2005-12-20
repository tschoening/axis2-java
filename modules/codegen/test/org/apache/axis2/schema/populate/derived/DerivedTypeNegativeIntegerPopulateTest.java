package org.apache.axis2.schema.populate.derived;

import org.apache.axis2.schema.types.NegativeInteger;
import org.apache.axis2.schema.util.ConverterUtil;

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

public class DerivedTypeNegativeIntegerPopulateTest extends AbstractDerivedPopulater{
    
     private String values[]= {
            "-18443",
            "-1",
            "-2633",
            "267582233",
            "0",
    };
    private String xmlString[] = {
            "<DerivedNegativeInteger>"+values[0]+"</DerivedNegativeInteger>",
            "<DerivedNegativeInteger>"+values[1]+"</DerivedNegativeInteger>",
            "<DerivedNegativeInteger>"+values[2]+"</DerivedNegativeInteger>",
            "<DerivedNegativeInteger>"+values[3]+"</DerivedNegativeInteger>",
            "<DerivedNegativeInteger>"+values[4]+"</DerivedNegativeInteger>"
    };

    protected void setUp() throws Exception {
        className = "org.soapinterop.DerivedNegativeInteger";
        propertyClass = NegativeInteger.class;
    }

    // force others to implement this method
    public void testPopulate() throws Exception {
        for (int i = 0; i < 3; i++) {
            checkValue(xmlString[i],values[i]);
        }

        for (int i = 3; i < values.length; i++) {
            try {
                checkValue(xmlString[i],values[i]);
                fail();
            } catch (Exception e) {

            }
        }

    }

    protected String convertToString(Object o) {
        return ConverterUtil.convertToString((NegativeInteger)o);
    }
}
