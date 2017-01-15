/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.axis2.transport.http.impl.httpclient3;

import java.io.IOException;
import java.net.URL;

import org.apache.axis2.AxisFault;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.transport.MessageFormatter;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.axis2.transport.http.Request;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

class PostRequest implements Request {
    private static final Log log = LogFactory.getLog(PostRequest.class);

    private final HTTPSenderImpl sender;
    private final URL url;
    private final MessageContext msgContext;
    private final String soapActionString;

    PostRequest(HTTPSenderImpl sender, URL url, MessageContext msgContext, String soapActionString) {
        this.sender = sender;
        this.url = url;
        this.msgContext = msgContext;
        this.soapActionString = soapActionString;
    }

    @Override
    public void execute() throws AxisFault {
        HttpClient httpClient = sender.getHttpClient(msgContext);

        PostMethod method = new PostMethod();
        if (log.isTraceEnabled()) {
            log.trace(Thread.currentThread() + " PostMethod " + method + " / " + httpClient);
        }
        MessageFormatter messageFormatter = sender.populateCommonProperties(msgContext, url, method,
                httpClient, soapActionString);

        method.setRequestEntity(new AxisRequestEntityImpl(messageFormatter, msgContext, sender.getFormat(),
                soapActionString, sender.isChunked(), sender.isAllowedRetry()));

        if (!sender.getHttpVersion().equals(HTTPConstants.HEADER_PROTOCOL_10) && sender.isChunked()) {
            method.setContentChunked(true);
        }

        String soapAction = messageFormatter.formatSOAPAction(msgContext, sender.getFormat(), soapActionString);

        if (soapAction != null && !msgContext.isDoingREST()) {
            method.setRequestHeader(HTTPConstants.HEADER_SOAP_ACTION, soapAction);
        }

        /*
         * main excecution takes place..
         */
        try {
            sender.executeMethod(httpClient, msgContext, url, method);
            sender.handleResponse(msgContext, method);
        } catch (IOException e) {
            log.info("Unable to sendViaPost to url[" + url + "]", e);
            throw AxisFault.makeFault(e);
        } finally {
            sender.cleanup(msgContext, method);
        }
    }
}