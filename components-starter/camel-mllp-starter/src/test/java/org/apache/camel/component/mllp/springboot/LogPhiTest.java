/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.component.mllp.springboot;


import static org.apache.camel.ExchangePattern.InOut;

import java.util.function.Consumer;

import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.ServiceStatus;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mllp.MllpComponent;
import org.apache.camel.component.mllp.springboot.rule.MllpServerResource;
import org.apache.camel.spring.boot.CamelAutoConfiguration;
import org.apache.camel.spring.boot.CamelContextConfiguration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;
import org.apache.camel.test.AvailablePortFinder;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;


@DirtiesContext
@CamelSpringBootTest
@SpringBootTest(
    classes = {
        CamelAutoConfiguration.class,
        LogPhiTest.class,
        LogPhiTest.TestConfiguration.class
    }
)
public class LogPhiTest {
    
    static final int SERVER_ACKNOWLEDGEMENT_DELAY = 10000;

    @RegisterExtension
    public MllpServerResource mllpServer = new MllpServerResource();

    @EndpointInject("direct:startNoLogPhi")
    private Endpoint startNoLogPhi;

    @EndpointInject("direct:startLogPhi")
    private Endpoint startLogPhi;

    @EndpointInject("direct:start")
    private Endpoint startDefaultPhi;
    
    @Autowired
    ProducerTemplate template;
    
    @Autowired
    CamelContext context;
    
    @Bean
    CamelContextConfiguration contextConfiguration() {
        return new CamelContextConfiguration() {
            @Override
            public void beforeApplicationStart(CamelContext context) {
                MllpComponent mllpComponentNoLogPhi = new MllpComponent();
                mllpComponentNoLogPhi.setLogPhi(Boolean.FALSE);

                MllpComponent mllpComponentLogPhi = new MllpComponent();
                mllpComponentLogPhi.setLogPhi(Boolean.TRUE);

                MllpComponent mllpComponentLogPhiDefault = new MllpComponent();

                context.addComponent("mllpnologphi", mllpComponentNoLogPhi);
                context.addComponent("mllplogphi", mllpComponentLogPhi);
                context.addComponent("mllpdefault", mllpComponentLogPhiDefault);
            }

            @Override
            public void afterApplicationStart(CamelContext camelContext) {
                // TODO Auto-generated method stub

            }
        };
    }

    
    @Test
    public void testLogPhiFalse() throws Exception {
        testLogPhi(startNoLogPhi, exceptionMessage -> assertFalse(exceptionMessage.contains("hl7Message")));
    }

    @Test
    public void testLogPhiTrue() throws Exception {
        testLogPhi(startLogPhi, exceptionMessage -> assertTrue(exceptionMessage.contains("hl7Message")));
    }

    @Test
    public void testLogPhiDefault() throws Exception {
        testLogPhi(startDefaultPhi, exceptionMessage -> assertTrue(exceptionMessage.contains("hl7Message")));
    }

    public void testLogPhi(Endpoint endpoint, Consumer<String> contains) throws Exception {
        Exchange exchange = endpoint.createExchange(InOut);
        String message = Hl7TestMessageGenerator.generateMessage();
        exchange.getIn().setBody(message);
        assertEquals(ServiceStatus.Started, context.getStatus());
        template.send(endpoint, exchange);
        assertTrue(exchange.isFailed(), "Should be failed");
        contains.accept(exchange.getException().getMessage());
    }

    // *************************************
    // Config
    // *************************************

    @Configuration
    public class TestConfiguration {

        @Bean
        public RouteBuilder routeBuilder() {
            return new RouteBuilder() {
                @Override
                public void configure() throws Exception {
                    mllpServer.setListenHost("localhost");
                    mllpServer.setListenPort(AvailablePortFinder.getNextAvailable());
                    mllpServer.setDelayDuringAcknowledgement(SERVER_ACKNOWLEDGEMENT_DELAY);
                    mllpServer.startup();
                    assertTrue(mllpServer.isActive());
                    from(startNoLogPhi).toF("mllpnologphi://%s:%d?receiveTimeout=%d",
                                            mllpServer.getListenHost(), mllpServer.getListenPort(),
                                            SERVER_ACKNOWLEDGEMENT_DELAY / 2);

                    from(startLogPhi).toF("mllplogphi://%s:%d?receiveTimeout=%d", mllpServer.getListenHost(),
                                          mllpServer.getListenPort(), SERVER_ACKNOWLEDGEMENT_DELAY / 2);

                    from(startDefaultPhi).toF("mllpdefault://%s:%d?receiveTimeout=%d",
                                              mllpServer.getListenHost(), mllpServer.getListenPort(),
                                              SERVER_ACKNOWLEDGEMENT_DELAY / 2);
                }
            };
        }
    }
    
   

}
