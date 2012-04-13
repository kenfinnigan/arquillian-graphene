/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.arquillian.graphene.remote.reusable.ftest;

import static junit.framework.Assert.assertTrue;

import java.io.Serializable;

import org.jboss.arquillian.drone.api.annotation.Drone;
import org.junit.Test;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * @author Lukas Fryc
 */
public class TestCapabilitiesSerialization extends AbstractInBrowserTest {

    @Drone
    RemoteWebDriver driver;
    
    @Test
    public void whenGetCapabilitiesFromRunningSessionThenItShouldBeSerializable() {
        Capabilities initializedCapabilities = driver.getCapabilities();

        assertTrue("Capabilities obtained from running session should be serializable",
                initializedCapabilities instanceof Serializable);
        
        driver.quit();
    }
}