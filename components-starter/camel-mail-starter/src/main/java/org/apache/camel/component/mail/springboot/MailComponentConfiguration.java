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
package org.apache.camel.component.mail.springboot;

import java.util.Properties;
import jakarta.mail.Session;
import org.apache.camel.component.mail.AttachmentsContentTransferEncodingResolver;
import org.apache.camel.component.mail.ContentTypeResolver;
import org.apache.camel.component.mail.JavaMailSender;
import org.apache.camel.component.mail.MailAuthenticator;
import org.apache.camel.component.mail.MailConfiguration;
import org.apache.camel.spi.HeaderFilterStrategy;
import org.apache.camel.spring.boot.ComponentConfigurationPropertiesCommon;
import org.apache.camel.support.jsse.SSLContextParameters;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Send and receive emails using imap, pop3 and smtp protocols.
 * 
 * Generated by camel-package-maven-plugin - do not edit this file!
 */
@ConfigurationProperties(prefix = "camel.component.mail")
public class MailComponentConfiguration
        extends
            ComponentConfigurationPropertiesCommon {

    /**
     * Whether to enable auto configuration of the mail component. This is
     * enabled by default.
     */
    private Boolean enabled;
    /**
     * Allows for bridging the consumer to the Camel routing Error Handler,
     * which mean any exceptions occurred while the consumer is trying to pickup
     * incoming messages, or the likes, will now be processed as a message and
     * handled by the routing Error Handler. By default the consumer will use
     * the org.apache.camel.spi.ExceptionHandler to deal with exceptions, that
     * will be logged at WARN or ERROR level and ignored.
     */
    private Boolean bridgeErrorHandler = false;
    /**
     * Whether the consumer should close the folder after polling. Setting this
     * option to false and having disconnect=false as well, then the consumer
     * keep the folder open between polls.
     */
    private Boolean closeFolder = true;
    /**
     * After processing a mail message, it can be copied to a mail folder with
     * the given name. You can override this configuration value, with a header
     * with the key copyTo, allowing you to copy messages to folder names
     * configured at runtime.
     */
    private String copyTo;
    /**
     * If set to true, the MimeUtility.decodeText method will be used to decode
     * the filename. This is similar to setting JVM system property
     * mail.mime.encodefilename.
     */
    private Boolean decodeFilename = false;
    /**
     * Deletes the messages after they have been processed. This is done by
     * setting the DELETED flag on the mail message. If false, the SEEN flag is
     * set instead. As of Camel 2.10 you can override this configuration option
     * by setting a header with the key delete to determine if the mail should
     * be deleted or not.
     */
    private Boolean delete = false;
    /**
     * Whether the consumer should disconnect after polling. If enabled this
     * forces Camel to connect on each poll.
     */
    private Boolean disconnect = false;
    /**
     * If the mail consumer cannot retrieve a given mail message, then this
     * option allows to handle the caused exception by the consumer's error
     * handler. By enable the bridge error handler on the consumer, then the
     * Camel routing error handler can handle the exception instead. The default
     * behavior would be the consumer throws an exception and no mails from the
     * batch would be able to be routed by Camel.
     */
    private Boolean handleFailedMessage = false;
    /**
     * This option enables transparent MIME decoding and unfolding for mail
     * headers.
     */
    private Boolean mimeDecodeHeaders = false;
    /**
     * After processing a mail message, it can be moved to a mail folder with
     * the given name. You can override this configuration value, with a header
     * with the key moveTo, allowing you to move messages to folder names
     * configured at runtime.
     */
    private String moveTo;
    /**
     * Will mark the jakarta.mail.Message as peeked before processing the mail
     * message. This applies to IMAPMessage messages types only. By using peek
     * the mail will not be eager marked as SEEN on the mail server, which
     * allows us to rollback the mail message if there is an error processing in
     * Camel.
     */
    private Boolean peek = true;
    /**
     * If the mail consumer cannot retrieve a given mail message, then this
     * option allows to skip the message and move on to retrieve the next mail
     * message. The default behavior would be the consumer throws an exception
     * and no mails from the batch would be able to be routed by Camel.
     */
    private Boolean skipFailedMessage = false;
    /**
     * Whether to limit by unseen mails only.
     */
    private Boolean unseen = true;
    /**
     * Whether to fail processing the mail if the mail message contains
     * attachments with duplicate file names. If set to false, then the
     * duplicate attachment is skipped and a WARN is logged. If set to true then
     * an exception is thrown failing to process the mail message.
     */
    private Boolean failOnDuplicateFileAttachment = false;
    /**
     * Sets the maximum number of messages to consume during a poll. This can be
     * used to avoid overloading a mail server, if a mailbox folder contains a
     * lot of messages. Default value of -1 means no fetch size and all messages
     * will be consumed. Setting the value to 0 is a special corner case, where
     * Camel will not consume any messages at all.
     */
    private Integer fetchSize = -1;
    /**
     * The folder to poll.
     */
    private String folderName = "INBOX";
    /**
     * Set this to 'uuid' to set a UUID for the filename of the attachment if no
     * filename was set
     */
    private String generateMissingAttachmentNames;
    /**
     * Set the strategy to handle duplicate filenames of attachments never:
     * attachments that have a filename which is already present in the
     * attachments will be ignored unless failOnDuplicateFileAttachment is set
     * to true. uuidPrefix: this will prefix the duplicate attachment filenames
     * each with a uuid and underscore (uuid_filename.fileextension).
     * uuidSuffix: this will suffix the duplicate attachment filenames each with
     * a underscore and uuid (filename_uuid.fileextension).
     */
    private String handleDuplicateAttachmentNames;
    /**
     * Specifies whether Camel should map the received mail message to Camel
     * body/headers/attachments. If set to true, the body of the mail message is
     * mapped to the body of the Camel IN message, the mail headers are mapped
     * to IN headers, and the attachments to Camel IN attachment message. If
     * this option is set to false then the IN message contains a raw
     * jakarta.mail.Message. You can retrieve this raw message by calling
     * exchange.getIn().getBody(jakarta.mail.Message.class).
     */
    private Boolean mapMailMessage = true;
    /**
     * Sets the BCC email address. Separate multiple email addresses with comma.
     */
    private String bcc;
    /**
     * Sets the CC email address. Separate multiple email addresses with comma.
     */
    private String cc;
    /**
     * The from email address
     */
    private String from = "camel@localhost";
    /**
     * Whether the producer should be started lazy (on the first message). By
     * starting lazy you can use this to allow CamelContext and routes to
     * startup in situations where a producer may otherwise fail during starting
     * and cause the route to fail being started. By deferring this startup to
     * be lazy then the startup failure can be handled during routing messages
     * via Camel's routing error handlers. Beware that when the first message is
     * processed then creating and starting the producer may take a little time
     * and prolong the total processing time of the processing.
     */
    private Boolean lazyStartProducer = false;
    /**
     * The Reply-To recipients (the receivers of the response mail). Separate
     * multiple email addresses with a comma.
     */
    private String replyTo;
    /**
     * The Subject of the message being sent. Note: Setting the subject in the
     * header takes precedence over this option.
     */
    private String subject;
    /**
     * Sets the To email address. Separate multiple email addresses with comma.
     */
    private String to;
    /**
     * To use a custom org.apache.camel.component.mail.JavaMailSender for
     * sending emails. The option is a
     * org.apache.camel.component.mail.JavaMailSender type.
     */
    private JavaMailSender javaMailSender;
    /**
     * Sets additional java mail properties, that will append/override any
     * default properties that is set based on all the other options. This is
     * useful if you need to add some special options but want to keep the
     * others as is. The option is a java.util.Properties type.
     */
    private Properties additionalJavaMailProperties;
    /**
     * Specifies the key to an IN message header that contains an alternative
     * email body. For example, if you send emails in text/html format and want
     * to provide an alternative mail body for non-HTML email clients, set the
     * alternative mail body with this key as a header.
     */
    private String alternativeBodyHeader = "CamelMailAlternativeBody";
    /**
     * To use a custom AttachmentsContentTransferEncodingResolver to resolve
     * what content-type-encoding to use for attachments. The option is a
     * org.apache.camel.component.mail.AttachmentsContentTransferEncodingResolver type.
     */
    private AttachmentsContentTransferEncodingResolver attachmentsContentTransferEncodingResolver;
    /**
     * The authenticator for login. If set then the password and username are
     * ignored. Can be used for tokens which can expire and therefore must be
     * read dynamically. The option is a
     * org.apache.camel.component.mail.MailAuthenticator type.
     */
    private MailAuthenticator authenticator;
    /**
     * Whether autowiring is enabled. This is used for automatic autowiring
     * options (the option must be marked as autowired) by looking up in the
     * registry to find if there is a single instance of matching type, which
     * then gets configured on the component. This can be used for automatic
     * configuring JDBC data sources, JMS connection factories, AWS Clients,
     * etc.
     */
    private Boolean autowiredEnabled = true;
    /**
     * Sets the Mail configuration. The option is a
     * org.apache.camel.component.mail.MailConfiguration type.
     */
    private MailConfiguration configuration;
    /**
     * The connection timeout in milliseconds.
     */
    private Integer connectionTimeout = 30000;
    /**
     * The mail message content type. Use text/html for HTML mails.
     */
    private String contentType = "text/plain";
    /**
     * Resolver to determine Content-Type for file attachments. The option is a
     * org.apache.camel.component.mail.ContentTypeResolver type.
     */
    private ContentTypeResolver contentTypeResolver;
    /**
     * Enable debug mode on the underlying mail framework. The SUN Mail
     * framework logs the debug messages to System.out by default.
     */
    private Boolean debugMode = false;
    /**
     * Option to let Camel ignore unsupported charset in the local JVM when
     * sending mails. If the charset is unsupported then charset=XXX (where XXX
     * represents the unsupported charset) is removed from the content-type and
     * it relies on the platform default instead.
     */
    private Boolean ignoreUnsupportedCharset = false;
    /**
     * Option to let Camel ignore unsupported charset in the local JVM when
     * sending mails. If the charset is unsupported then charset=XXX (where XXX
     * represents the unsupported charset) is removed from the content-type and
     * it relies on the platform default instead.
     */
    private Boolean ignoreUriScheme = false;
    /**
     * Sets the java mail options. Will clear any default properties and only
     * use the properties provided for this method. The option is a
     * java.util.Properties type.
     */
    private Properties javaMailProperties;
    /**
     * Specifies the mail session that camel should use for all mail
     * interactions. Useful in scenarios where mail sessions are created and
     * managed by some other resource, such as a JavaEE container. When using a
     * custom mail session, then the hostname and port from the mail session
     * will be used (if configured on the session). The option is a
     * jakarta.mail.Session type.
     */
    private Session session;
    /**
     * Whether to use disposition inline or attachment.
     */
    private Boolean useInlineAttachments = false;
    /**
     * To use a custom org.apache.camel.spi.HeaderFilterStrategy to filter
     * header to and from Camel message. The option is a
     * org.apache.camel.spi.HeaderFilterStrategy type.
     */
    private HeaderFilterStrategy headerFilterStrategy;
    /**
     * Used for enabling or disabling all consumer based health checks from this
     * component
     */
    private Boolean healthCheckConsumerEnabled = true;
    /**
     * Used for enabling or disabling all health checks from this component
     */
    private Boolean healthCheckEnabled = true;
    /**
     * Used for enabling or disabling all producer based health checks from this
     * component
     */
    private Boolean healthCheckProducerEnabled = true;
    /**
     * The password for login. See also setAuthenticator(MailAuthenticator).
     */
    private String password;
    /**
     * To configure security using SSLContextParameters. The option is a
     * org.apache.camel.support.jsse.SSLContextParameters type.
     */
    private SSLContextParameters sslContextParameters;
    /**
     * Enable usage of global SSL context parameters.
     */
    private Boolean useGlobalSslContextParameters = false;
    /**
     * The username for login. See also setAuthenticator(MailAuthenticator).
     */
    private String username;

    public Boolean getBridgeErrorHandler() {
        return bridgeErrorHandler;
    }

    public void setBridgeErrorHandler(Boolean bridgeErrorHandler) {
        this.bridgeErrorHandler = bridgeErrorHandler;
    }

    public Boolean getCloseFolder() {
        return closeFolder;
    }

    public void setCloseFolder(Boolean closeFolder) {
        this.closeFolder = closeFolder;
    }

    public String getCopyTo() {
        return copyTo;
    }

    public void setCopyTo(String copyTo) {
        this.copyTo = copyTo;
    }

    public Boolean getDecodeFilename() {
        return decodeFilename;
    }

    public void setDecodeFilename(Boolean decodeFilename) {
        this.decodeFilename = decodeFilename;
    }

    public Boolean getDelete() {
        return delete;
    }

    public void setDelete(Boolean delete) {
        this.delete = delete;
    }

    public Boolean getDisconnect() {
        return disconnect;
    }

    public void setDisconnect(Boolean disconnect) {
        this.disconnect = disconnect;
    }

    public Boolean getHandleFailedMessage() {
        return handleFailedMessage;
    }

    public void setHandleFailedMessage(Boolean handleFailedMessage) {
        this.handleFailedMessage = handleFailedMessage;
    }

    public Boolean getMimeDecodeHeaders() {
        return mimeDecodeHeaders;
    }

    public void setMimeDecodeHeaders(Boolean mimeDecodeHeaders) {
        this.mimeDecodeHeaders = mimeDecodeHeaders;
    }

    public String getMoveTo() {
        return moveTo;
    }

    public void setMoveTo(String moveTo) {
        this.moveTo = moveTo;
    }

    public Boolean getPeek() {
        return peek;
    }

    public void setPeek(Boolean peek) {
        this.peek = peek;
    }

    public Boolean getSkipFailedMessage() {
        return skipFailedMessage;
    }

    public void setSkipFailedMessage(Boolean skipFailedMessage) {
        this.skipFailedMessage = skipFailedMessage;
    }

    public Boolean getUnseen() {
        return unseen;
    }

    public void setUnseen(Boolean unseen) {
        this.unseen = unseen;
    }

    public Boolean getFailOnDuplicateFileAttachment() {
        return failOnDuplicateFileAttachment;
    }

    public void setFailOnDuplicateFileAttachment(
            Boolean failOnDuplicateFileAttachment) {
        this.failOnDuplicateFileAttachment = failOnDuplicateFileAttachment;
    }

    public Integer getFetchSize() {
        return fetchSize;
    }

    public void setFetchSize(Integer fetchSize) {
        this.fetchSize = fetchSize;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getGenerateMissingAttachmentNames() {
        return generateMissingAttachmentNames;
    }

    public void setGenerateMissingAttachmentNames(
            String generateMissingAttachmentNames) {
        this.generateMissingAttachmentNames = generateMissingAttachmentNames;
    }

    public String getHandleDuplicateAttachmentNames() {
        return handleDuplicateAttachmentNames;
    }

    public void setHandleDuplicateAttachmentNames(
            String handleDuplicateAttachmentNames) {
        this.handleDuplicateAttachmentNames = handleDuplicateAttachmentNames;
    }

    public Boolean getMapMailMessage() {
        return mapMailMessage;
    }

    public void setMapMailMessage(Boolean mapMailMessage) {
        this.mapMailMessage = mapMailMessage;
    }

    public String getBcc() {
        return bcc;
    }

    public void setBcc(String bcc) {
        this.bcc = bcc;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Boolean getLazyStartProducer() {
        return lazyStartProducer;
    }

    public void setLazyStartProducer(Boolean lazyStartProducer) {
        this.lazyStartProducer = lazyStartProducer;
    }

    public String getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public JavaMailSender getJavaMailSender() {
        return javaMailSender;
    }

    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public Properties getAdditionalJavaMailProperties() {
        return additionalJavaMailProperties;
    }

    public void setAdditionalJavaMailProperties(
            Properties additionalJavaMailProperties) {
        this.additionalJavaMailProperties = additionalJavaMailProperties;
    }

    public String getAlternativeBodyHeader() {
        return alternativeBodyHeader;
    }

    public void setAlternativeBodyHeader(String alternativeBodyHeader) {
        this.alternativeBodyHeader = alternativeBodyHeader;
    }

    public AttachmentsContentTransferEncodingResolver getAttachmentsContentTransferEncodingResolver() {
        return attachmentsContentTransferEncodingResolver;
    }

    public void setAttachmentsContentTransferEncodingResolver(
            AttachmentsContentTransferEncodingResolver attachmentsContentTransferEncodingResolver) {
        this.attachmentsContentTransferEncodingResolver = attachmentsContentTransferEncodingResolver;
    }

    public MailAuthenticator getAuthenticator() {
        return authenticator;
    }

    public void setAuthenticator(MailAuthenticator authenticator) {
        this.authenticator = authenticator;
    }

    public Boolean getAutowiredEnabled() {
        return autowiredEnabled;
    }

    public void setAutowiredEnabled(Boolean autowiredEnabled) {
        this.autowiredEnabled = autowiredEnabled;
    }

    public MailConfiguration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(MailConfiguration configuration) {
        this.configuration = configuration;
    }

    public Integer getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(Integer connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public ContentTypeResolver getContentTypeResolver() {
        return contentTypeResolver;
    }

    public void setContentTypeResolver(ContentTypeResolver contentTypeResolver) {
        this.contentTypeResolver = contentTypeResolver;
    }

    public Boolean getDebugMode() {
        return debugMode;
    }

    public void setDebugMode(Boolean debugMode) {
        this.debugMode = debugMode;
    }

    public Boolean getIgnoreUnsupportedCharset() {
        return ignoreUnsupportedCharset;
    }

    public void setIgnoreUnsupportedCharset(Boolean ignoreUnsupportedCharset) {
        this.ignoreUnsupportedCharset = ignoreUnsupportedCharset;
    }

    public Boolean getIgnoreUriScheme() {
        return ignoreUriScheme;
    }

    public void setIgnoreUriScheme(Boolean ignoreUriScheme) {
        this.ignoreUriScheme = ignoreUriScheme;
    }

    public Properties getJavaMailProperties() {
        return javaMailProperties;
    }

    public void setJavaMailProperties(Properties javaMailProperties) {
        this.javaMailProperties = javaMailProperties;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Boolean getUseInlineAttachments() {
        return useInlineAttachments;
    }

    public void setUseInlineAttachments(Boolean useInlineAttachments) {
        this.useInlineAttachments = useInlineAttachments;
    }

    public HeaderFilterStrategy getHeaderFilterStrategy() {
        return headerFilterStrategy;
    }

    public void setHeaderFilterStrategy(
            HeaderFilterStrategy headerFilterStrategy) {
        this.headerFilterStrategy = headerFilterStrategy;
    }

    public Boolean getHealthCheckConsumerEnabled() {
        return healthCheckConsumerEnabled;
    }

    public void setHealthCheckConsumerEnabled(Boolean healthCheckConsumerEnabled) {
        this.healthCheckConsumerEnabled = healthCheckConsumerEnabled;
    }

    public Boolean getHealthCheckEnabled() {
        return healthCheckEnabled;
    }

    public void setHealthCheckEnabled(Boolean healthCheckEnabled) {
        this.healthCheckEnabled = healthCheckEnabled;
    }

    public Boolean getHealthCheckProducerEnabled() {
        return healthCheckProducerEnabled;
    }

    public void setHealthCheckProducerEnabled(Boolean healthCheckProducerEnabled) {
        this.healthCheckProducerEnabled = healthCheckProducerEnabled;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public SSLContextParameters getSslContextParameters() {
        return sslContextParameters;
    }

    public void setSslContextParameters(
            SSLContextParameters sslContextParameters) {
        this.sslContextParameters = sslContextParameters;
    }

    public Boolean getUseGlobalSslContextParameters() {
        return useGlobalSslContextParameters;
    }

    public void setUseGlobalSslContextParameters(
            Boolean useGlobalSslContextParameters) {
        this.useGlobalSslContextParameters = useGlobalSslContextParameters;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}