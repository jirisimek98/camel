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
package org.apache.camel.telemetry.propagation;

import java.util.Map;

import org.apache.camel.telemetry.SpanContextPropagationInjector;

public final class CamelJMSHeadersSpanContextPropagationInjector implements SpanContextPropagationInjector {

    // As per the JMS specs, header names must be valid Java identifier part
    // characters.
    // This means that any header names that contain illegal characters ("-", for
    // example) should be handled correctly,
    // Opentracing java-jms does it as follows.
    static final String JMS_DASH = "__dash__";
    private final Map<String, Object> map;

    public CamelJMSHeadersSpanContextPropagationInjector(final Map<String, Object> map) {
        this.map = map;
    }

    @Override
    public void put(String key, String value) {
        // Assume any header property that begins with 'Camel' is for internal use
        if (!key.startsWith("Camel")) {
            this.map.put(encodeDash(key), value);
        }
    }

    /**
     * Encode all dashes because JMS specification doesn't allow them in property names
     */
    private String encodeDash(String key) {
        if (key == null || key.isEmpty()) {
            return key;
        }

        return key.replace("-", JMS_DASH);
    }
}
