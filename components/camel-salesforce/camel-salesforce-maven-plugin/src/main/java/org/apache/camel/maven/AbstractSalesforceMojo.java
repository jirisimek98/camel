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
package org.apache.camel.maven;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.util.Map;
import java.util.Set;

import org.apache.camel.component.salesforce.SalesforceEndpointConfig;
import org.apache.camel.component.salesforce.SalesforceLoginConfig;
import org.apache.camel.component.salesforce.codegen.AbstractSalesforceExecution;
import org.apache.camel.support.jsse.KeyStoreParameters;
import org.apache.camel.support.jsse.SSLContextParameters;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Base class for any Salesforce MOJO.
 */
public abstract class AbstractSalesforceMojo extends AbstractMojo {

    /**
     * Salesforce client id.
     */
    @Parameter(property = "camelSalesforce.clientId", required = true)
    String clientId;

    /**
     * Salesforce client secret.
     */
    @Parameter(property = "camelSalesforce.clientSecret")
    String clientSecret;

    /**
     * HTTP client properties.
     */
    @Parameter
    Map<String, Object> httpClientProperties;

    /**
     * Proxy authentication URI.
     */
    @Parameter(property = "camelSalesforce.httpProxyAuthUri")
    String httpProxyAuthUri;

    /**
     * Addresses to NOT Proxy.
     */
    @Parameter(property = "camelSalesforce.httpProxyExcludedAddresses")
    Set<String> httpProxyExcludedAddresses;

    /**
     * HTTP Proxy host.
     */
    @Parameter(property = "camelSalesforce.httpProxyHost")
    String httpProxyHost;

    /**
     * Addresses to Proxy.
     */
    @Parameter(property = "camelSalesforce.httpProxyIncludedAddresses")
    Set<String> httpProxyIncludedAddresses;

    /**
     * Proxy authentication password.
     */
    @Parameter(property = "camelSalesforce.httpProxyPassword")
    String httpProxyPassword;

    /**
     * HTTP Proxy port.
     */
    @Parameter(property = "camelSalesforce.httpProxyPort")
    Integer httpProxyPort;

    /**
     * Proxy authentication realm.
     */
    @Parameter(property = "camelSalesforce.httpProxyRealm")
    String httpProxyRealm;

    /**
     * Proxy uses Digest authentication.
     */
    @Parameter(property = "camelSalesforce.httpProxyUseDigestAuth")
    boolean httpProxyUseDigestAuth;

    /**
     * Proxy authentication username.
     */
    @Parameter(property = "camelSalesforce.httpProxyUsername")
    String httpProxyUsername;

    /**
     * Is HTTP Proxy secure, i.e. using secure sockets, true by default.
     */
    @Parameter(property = "camelSalesforce.isHttpProxySecure")
    boolean isHttpProxySecure = true;

    /**
     * Is it a SOCKS4 Proxy?
     */
    @Parameter(property = "camelSalesforce.isHttpProxySocks4")
    boolean isHttpProxySocks4;

    /**
     * Salesforce login URL, defaults to https://login.salesforce.com.
     */
    @Parameter(property = "camelSalesforce.loginUrl", defaultValue = SalesforceLoginConfig.DEFAULT_LOGIN_URL)
    String loginUrl;

    /**
     * Salesforce password.
     */
    @Parameter(property = "camelSalesforce.password")
    String password;

    /**
     * SSL Context parameters.
     */
    @Parameter(property = "camelSalesforce.sslContextParameters")
    final SSLContextParameters sslContextParameters = new SSLContextParameters();

    /**
     * Salesforce username.
     */
    @Parameter(property = "camelSalesforce.userName", required = true)
    String userName;

    /**
     * Salesforce JWT Audience.
     */
    @Parameter(property = "camelSalesforce.jwtAudience", defaultValue = "https://login.salesforce.com")
    String jwtAudience;

    /**
     * Salesforce Keystore Path.
     */
    @Parameter(property = "camelSalesforce.keystore.resource")
    String keystoreResource;

    /**
     * Salesforce Keystore Password.
     */
    @Parameter(property = "camelSalesforce.keystore.password")
    String keystorePassword;

    /**
     * Salesforce Keystore Type.
     */
    @Parameter(property = "camelSalesforce.keystore.type", defaultValue = "jks")
    String keystoreType;

    /**
     * Salesforce API version.
     */
    @Parameter(property = "camelSalesforce.version", defaultValue = SalesforceEndpointConfig.DEFAULT_VERSION)
    String version;

    private AbstractSalesforceExecution execution;

    @Override
    public final void execute() throws MojoExecutionException, MojoFailureException {
        try {
            validateAuthenticationParameters();
            setup();
            execution.execute();
        } catch (Exception e) {
            throw new MojoExecutionException(e.getMessage(), e);
        }

    }

    protected abstract AbstractSalesforceExecution getSalesforceExecution();

    protected void setup() {
        execution = getSalesforceExecution();

        execution.setClientId(clientId);
        execution.setClientSecret(clientSecret);
        execution.setHttpClientProperties(httpClientProperties);
        execution.setHttpProxyAuthUri(httpProxyAuthUri);
        execution.setHttpProxyHost(httpProxyHost);
        execution.setHttpProxyPort(httpProxyPort);
        execution.setHttpProxyRealm(httpProxyRealm);
        execution.setHttpProxyUsername(httpProxyUsername);
        execution.setHttpProxyPassword(httpProxyPassword);
        execution.setHttpProxyExcludedAddresses(httpProxyExcludedAddresses);
        execution.setHttpProxyIncludedAddresses(httpProxyIncludedAddresses);
        execution.setHttpProxySocks4(isHttpProxySocks4);
        execution.setHttpProxySecure(isHttpProxySecure);
        execution.setHttpProxyUseDigestAuth(httpProxyUseDigestAuth);
        execution.setLoginUrl(loginUrl);
        execution.setUserName(userName);
        execution.setPassword(password);
        execution.setVersion(version);
        execution.setSslContextParameters(sslContextParameters);
        execution.setJwtAudience(jwtAudience);
        execution.setKeyStoreParameters(generateKeyStoreParameters());
    }

    private void validateAuthenticationParameters() throws MojoExecutionException {
        if (clientSecret == null && keystoreResource == null) {
            throw new MojoExecutionException(
                    "Either property: clientSecret or property: keystoreResource must be provided.");
        } else if (clientSecret != null && keystoreResource != null) {
            throw new MojoExecutionException(
                    "Property: clientSecret or property: keystoreResource must be provided.");
        }

        if (clientSecret != null) {
            if (password == null) {
                throw new MojoExecutionException(
                        generateRequiredErrorMessage("password", "clientSecret"));
            }
        }

        if (keystoreResource != null) {
            if (keystorePassword == null) {
                throw new MojoExecutionException(
                        generateRequiredErrorMessage("keystorePassword", "keystoreResource"));
            }
        }
    }

    private String generateRequiredErrorMessage(String parameter1, String parameter2) {
        return String.format("Property: %s must be provided when property: %s was provided.", parameter1, parameter2);
    }

    private KeyStoreParameters generateKeyStoreParameters() {
        if (keystoreResource == null) {
            return null;
        }

        KeyStoreParameters keyStoreParameters = new KeyStoreParameters();
        keyStoreParameters.setResource(keystoreResource);
        keyStoreParameters.setPassword(keystorePassword);
        keyStoreParameters.setType(keystoreType);

        try (InputStream is = new FileInputStream(keystoreResource)) {
            KeyStore ks = KeyStore.getInstance(keystoreType);
            ks.load(is, keystorePassword.toCharArray());
            keyStoreParameters.setKeyStore(ks);
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException(e);
        }

        return keyStoreParameters;
    }
}
