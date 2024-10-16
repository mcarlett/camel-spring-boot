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
package org.apache.camel.spring.boot.security;

import org.apache.camel.spring.boot.CamelAutoConfiguration;
import org.apache.camel.support.jsse.GlobalSSLContextParametersSupplier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;

/**
 * Testing that the ssl configuration is not created if a subproperty of "camel.ssl.config."
 * (note the last dot) is not present.
 */
@CamelSpringBootTest
@SpringBootApplication
@DirtiesContext
@ContextConfiguration(classes = {CamelSSLAutoConfiguration.class, CamelAutoConfiguration.class})
@SpringBootTest(
    properties = {
        "camel.ssl.configxxx=true"
    }
)
public class CamelSSLNoConfigTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void checkSSLPropertiesNotPresent() {
        Assertions.assertThrows(NoSuchBeanDefinitionException.class,
            () -> applicationContext.getBean(GlobalSSLContextParametersSupplier.class));
    }

}
