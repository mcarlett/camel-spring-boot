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
package org.apache.camel.itest.springboot;

import org.apache.camel.itest.springboot.util.ArquillianPackager;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(Arquillian.class)
public class CamelGoogleBigqueryTest extends AbstractSpringBootTestSupport {

    @Deployment
    public static Archive<?> createSpringBootPackage() throws Exception {
        return ArquillianPackager.springBootPackage(createTestConfig());
    }

    public static ITestConfig createTestConfig() {
        return new ITestConfigBuilder()
                .module(inferModuleName(CamelGoogleBigqueryTest.class))
                .dependency("com.google.api:gax:1.60.0")
                .dependency("com.google.api:gax-httpjson:0.77.0")
                .dependency("com.google.cloud:google-cloud-bigquery:1.124.7")
                .dependency("com.google.cloud:google-cloud-core-http:1.93.10")
                .dependency("com.google.cloud:google-cloud-core:1.93.10")
                .dependency("com.google.http-client:google-http-client-appengine:1.38.0")
                .dependency("com.google.http-client:google-http-client-jackson2:1.37.0")
                .dependency("com.google.http-client:google-http-client:1.37.0")
                .build();
    }

    @Test
    public void componentTests() throws Exception {
        this.runComponentTest(config);
    }

}
