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
package org.apache.camel.converter.jaxb.springboot;


import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.example.PurchaseOrder;
import org.apache.camel.foo.bar.PersonType;
import org.apache.camel.spring.boot.CamelAutoConfiguration;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;



@DirtiesContext
@CamelSpringBootTest
@SpringBootTest(
    classes = {
        CamelAutoConfiguration.class,
        DataFormatTest.class,
        DataFormatTest.TestConfiguration.class
    }
)
public class DataFormatTest {
    
            
    @Autowired
    ProducerTemplate template;

    
    @EndpointInject("mock:result")
    private MockEndpoint resultEndpoint;

    @EndpointInject("mock:unmarshall")
    private MockEndpoint mockUnmarshall;

    
    

    @Test
    public void testMarshalThenUnmarshalBean() throws Exception {
        resultEndpoint.reset();
        PurchaseOrder bean = new PurchaseOrder();
        bean.setName("Beer");
        bean.setAmount(23);
        bean.setPrice(2.5);

        resultEndpoint.expectedBodiesReceived(bean);

        template.sendBody("direct:start", bean);

        resultEndpoint.assertIsSatisfied();
    }

    @Test
    public void testMarshalPrettyPrint() throws Exception {
        resultEndpoint.reset();
        
        PersonType person = new PersonType();
        person.setFirstName("Willem");
        person.setLastName("Jiang");
        resultEndpoint.expectedMessageCount(1);

        template.sendBody("direct:prettyPrint", person);

        resultEndpoint.assertIsSatisfied();

        Exchange exchange = resultEndpoint.getExchanges().get(0);

        String result = exchange.getIn().getBody(String.class);
        assertNotNull("The result should not be null", result);
        int indexPerson = result.indexOf("<Person>");
        int indexFirstName = result.indexOf("<firstName>");

        assertTrue(indexPerson > 0, "we should find the <Person>");
        assertTrue(indexFirstName > 0, "we should find the <firstName>");
        assertTrue(indexFirstName - indexPerson > 8, "There should some sapce between <Person> and <firstName>");
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
                public void configure() {

                    JaxbDataFormat example = new JaxbDataFormat("org.apache.camel.example");
                    JaxbDataFormat person = new JaxbDataFormat("org.apache.camel.foo.bar");
                    person.setPrettyPrint(true);

                    from("direct:start").marshal(example).to("direct:marshalled");

                    from("direct:marshalled").unmarshal().jaxb("org.apache.camel.example").to("mock:result");

                    from("direct:prettyPrint").marshal(person).to("mock:result");
                }
            };
        }
    }
}
