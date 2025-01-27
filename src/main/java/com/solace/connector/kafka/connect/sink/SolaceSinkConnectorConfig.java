/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.solace.connector.kafka.connect.sink;

import java.util.Map;
import java.util.Optional;

import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigDef.Importance;
import org.apache.kafka.common.config.ConfigDef.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SolaceSinkConnectorConfig extends AbstractConfig {

  private static final Logger log = LoggerFactory.getLogger(SolaceSinkConnectorConfig.class);

  /**
   * Create Solace Configuration Properties from JSON or Properties file.
   * @param properties returns Properties
   */
  public SolaceSinkConnectorConfig(Map<String, String> properties) {
    super(config, properties);

    log.info("==================Initialize Connnector properties");
  }

  /**
   * Creates Kafka ConfigDef Object.
   * @return ConfigDef
   */
  public static ConfigDef solaceConfigDef() {
    return new ConfigDef()
        .define(SolaceSinkConstants.SOL_USERNAME,
            Type.STRING, "default", Importance.HIGH, "Solace username")
        .define(SolaceSinkConstants.SOL_PASSWORD,
            Type.PASSWORD, "default", Importance.HIGH, "Solace user password")
        .define(SolaceSinkConstants.SOL_HOST, Type.STRING, null, Importance.HIGH,
            "host to connect with, can be comma delimited for HA/DR")
        .define(SolaceSinkConstants.SOL_VPN_NAME, Type.STRING, "default", Importance.HIGH,
            "Solace VPN to connect with ")
        .define(SolaceSinkConstants.SOL_TOPICS, Type.STRING, null, Importance.MEDIUM,
            "Solace topic or list of topics to subscribe from")
        .define(SolaceSinkConstants.SOl_QUEUE,
            Type.STRING, null, Importance.MEDIUM, "Solace queue to consume from")
        .define(SolaceSinkConstants.SOL_RECORD_PROCESSOR,
            Type.CLASS, SolRecordProcessorIF.class, Importance.HIGH,
            "default Solace message processor to use against Kafka Sink Records")
        .define(SolaceSinkConstants.SOL_RECORD_PROCESSOR_IGNORE_ERROR,
            Type.BOOLEAN, false, Importance.MEDIUM,
            "If enabled, records that throw record processor errors will be discarded")
        .define(SolaceSinkConstants.SOL_LOCALHOST, Type.STRING, null, Importance.LOW,
            "The hostname or IP address of the machine on which the application "
            + "is running. On a multihomed machine, it is strongly recommended "
            + "to provide this parameter to ensure JCSMP uses the correct network interface")
        .define(SolaceSinkConstants.SOL_CLIENT_NAME, Type.STRING, "default", Importance.LOW,
            "Overrides the system generated name, must be unique in VPN")
        .define(SolaceSinkConstants.SOL_GENERATE_SENDER_ID, Type.BOOLEAN, false, Importance.LOW,
            "Indicates whether the client name should be included in the "
            + "SenderID message header parameter")
        .define(SolaceSinkConstants.SOL_REAPPLY_SUBSCRIPTIONS, Type.BOOLEAN, true, Importance.LOW,
            "If enabled, the API maintains a local cache of subscriptions "
            + "and reapplies them when the subscriber connection is reestablished")
        .define(SolaceSinkConstants.SOL_GENERATE_SEND_TIMESTAMPS,
            Type.BOOLEAN, false, Importance.LOW,
            "indicates whether to generate a send timestamp in outgoing messages")
        .define(SolaceSinkConstants.SOL_GENERATE_RCV_TIMESTAMPS,
            Type.BOOLEAN, false, Importance.LOW,
            "Indicates whether to generate a receive timestamp on incoming messages")
        .define(SolaceSinkConstants.SOL_GENERATE_SEQUENCE_NUMBERS,
            Type.BOOLEAN, false, Importance.LOW,
            "Indicates whether to generate a sequence number in outgoing messages")
        .define(SolaceSinkConstants.SOL_CALCULATE_MESSAGE_EXPIRATION,
            Type.BOOLEAN, false, Importance.LOW,
            "Indicates whether to calculate message expiration time in "
            + "outgoing messages and incoming messages")
        .define(SolaceSinkConstants.SOL_PUB_MULTI_THREAD, Type.BOOLEAN, true, Importance.LOW,
            "If enabled (default), the XMLMessageProducer is safe to access from multiple threads")
        .define(SolaceSinkConstants.SOL_PUB_USE_INTERMEDIATE_DIRECT_BUF,
            Type.BOOLEAN, true, Importance.LOW,
            "If enabled, during send operations, the XMLMessageProducer "
            + "concatenates all published data. This can result in higher throughput "
            + "for certain send operations. It can, however, lead to performance "
            + "degradation for some scenarios with large messages")
        .define(SolaceSinkConstants.SOL_MESSAGE_CALLBACK_ON_REACTOR,
            Type.BOOLEAN, false, Importance.LOW,
            "If enabled, messages delivered asynchronously to an XMLMessageListener are "
            + "delivered directly from the I/O thread instead of a consumer notification thread. "
            + "An application making use of this setting MUST return quickly from the "
            + "onReceive() callback, and MUST NOT call ANY session methods from the I/O thread")
        .define(SolaceSinkConstants.SOL_IGNORE_DUPLICATE_SUBSCRIPTION_ERROR,
            Type.BOOLEAN, false, Importance.LOW,
            "ignore errors caused by subscriptions being already presents")
        .define(SolaceSinkConstants.SOL_IGNORE_SUBSCRIPTION_NOT_FOUND_ERROR,
            Type.BOOLEAN, false, Importance.LOW,
            "When removing subscriptions ignore errors caused by subscriptions not being found.")
        .define(SolaceSinkConstants.SOL_NO_LOCAL, Type.BOOLEAN, false, Importance.LOW,
            "If this property is true, messages published on the session will not be "
            + "received on the same session even if the client has a subscription "
            + "that matches the published topic.")
        .define(SolaceSinkConstants.SOL_SUB_ACK_WINDOW_SIZE,
            Type.INT, 255, Importance.LOW,
            "The size of the sliding subscriber ACK window. The valid range is 1-255")
        .define(SolaceSinkConstants.SOL_AUTOFLUSH_SIZE,
            Type.INT, 200, Importance.LOW,
            "Number of outstanding transacted messages before autoflush. Must be lower than "
            + "max PubSub+ transaction size (255). The valid range is 1-200")
        .define(SolaceSinkConstants.SOl_AUTHENTICATION_SCHEME,
            Type.STRING, "AUTHENTICATION_SCHEME_BASIC",
            Importance.MEDIUM, "String property specifying the authentication scheme.")
        .define(SolaceSinkConstants.SOL_KRB_SERVICE_NAME,
            Type.STRING, "solace", Importance.MEDIUM,
            "This property is used to specify the ServiceName portion of the "
            + "Service Principal Name (SPN) that has a format of ServiceName/ApplianceName@REALM.")
        .define(SolaceSinkConstants.SOL_SSL_CONNECTION_DOWNGRADE_TO,
            Type.STRING, "", Importance.MEDIUM,
            "Session property specifying a transport protocol that SSL session "
            + "connection will be downgraded to after client authentication. "
            + "Allowed values: TRANSPORT_PROTOCOL_PLAIN_TEXT.")
        .define(SolaceSinkConstants.SOl_USE_TRANSACTIONS_FOR_QUEUE,
            Type.BOOLEAN, true, Importance.LOW,
            "Specifies if writing messages to queue destination shall use transactions.")
        .define(SolaceSinkConstants.SOl_USE_TRANSACTIONS_FOR_TOPICS,
            Type.BOOLEAN, false, Importance.LOW,
            "When true, messages published to topics will use persistent delivery type using transactions.")
        .define(SolaceSinkConstants.SOL_CHANNEL_PROPERTY_connectTimeoutInMillis,
            Type.INT, 30000, Importance.MEDIUM,
            "Timeout value (in ms) for creating an initial connection to Solace")
        .define(SolaceSinkConstants.SOL_CHANNEL_PROPERTY_readTimeoutInMillis,
            Type.INT, 10000, Importance.MEDIUM,
            "Timeout value (in ms) for reading a reply from Solace")
        .define(SolaceSinkConstants.SOL_CHANNEL_PROPERTY_connectRetries,
            Type.INT, 0, Importance.MEDIUM,
            "The number of times to attempt and retry a connection to the host appliance "
            + "(or list of appliances) during initial connection setup")
        .define(SolaceSinkConstants.SOL_CHANNEL_PROPERTY_reconnectRetries,
            Type.INT, 0, Importance.MEDIUM,
            "The number of times to attempt to reconnect to the appliance "
            + "(or list of appliances) after an initial connected session goes down")
        .define(SolaceSinkConstants.SOL_CHANNEL_PROPERTY_connectRetriesPerHost,
            Type.INT, 0, Importance.MEDIUM,
            "This property defines how many times to try to connect or reconnect to a "
            + "single host before moving to the next host in the list")
        .define(SolaceSinkConstants.SOL_CHANNEL_PROPERTY_reconnectRetryWaitInMillis,
            Type.INT, 3000, Importance.MEDIUM,
            "How much time in (MS) to wait between each attempt to connect or reconnect to a host")
        .define(SolaceSinkConstants.SOL_CHANNEL_PROPERTY_keepAliveIntervalInMillis,
            Type.INT, 3000, Importance.MEDIUM,
            "The amount of time (in ms) to wait between sending out keep-alive messages")
        .define(SolaceSinkConstants.SOL_CHANNEL_PROPERTY_keepAliveLimit,
            Type.INT, 10, Importance.MEDIUM,
            "The maximum number of consecutive keep-alive messages that can be sent "
            + "without receiving a response before the connection is closed by the API")
        .define(SolaceSinkConstants.SOL_CHANNEL_PROPERTY_sendBuffer,
            Type.INT, 65536, Importance.MEDIUM,
            "The size (in bytes) of the send socket buffer.")
        .define(SolaceSinkConstants.SOL_CHANNEL_PROPERTY_receiveBuffer,
            Type.INT, 65536, Importance.MEDIUM,
            "The size (in bytes) of the receive socket buffer.")
        .define(SolaceSinkConstants.SOL_CHANNEL_PROPERTY_tcpNoDelay,
            Type.BOOLEAN, true, Importance.LOW,
            "Whether to set the TCP_NODELAY option. When enabled, this "
            + "option disables the Nagle's algorithm.")
        .define(SolaceSinkConstants.SOL_CHANNEL_PROPERTY_compressionLevel,
            Type.INT, 0, Importance.MEDIUM,
            "A compressionLevel setting of 1-9 sets the ZLIB compression level to use; "
            + "a setting of 0 disables compression entirely.")
        .define(SolaceSinkConstants.SOL_SUBSCRIBER_LOCAL_PRIORITY, Type.INT, 1, Importance.MEDIUM,
            "Subscriber priority is used to choose a client to receive messages "
            + "sent with the DeliverToOne property set.")
        .define(SolaceSinkConstants.SOL_SUBSCRIBER_NETWORK_PRIORITY, Type.INT, 1, Importance.MEDIUM,
            "Subscriber priority is used to choose a client to receive messages "
            + "sent with the DeliverToOne property set.")
        .define(SolaceSinkConstants.SOL_SUBSCRIBER_DTO_OVERRIDE, Type.BOOLEAN, true, Importance.LOW,
            "When adding topic subscriptions override DTO processing for any "
            + "messages with DTO flags .")
        // .define(SolaceSinkConstants.SOL_SSL_PROTOCOL, Type.STRING,
        // "SSLv3,TLSv1,TLSv1.1,TLSv1.2", Importance.LOW,
        // "This property is used to specify a comma separated list of SSL protocols to
        // use.")
        .define(SolaceSinkConstants.SOL_SSL_EXCLUDED_PROTOCOLS, Type.STRING, "", Importance.LOW,
            "This property is used to specify a comma separated list of SSL protocols NOT to use")
        .define(SolaceSinkConstants.SOL_SSL_CIPHER_SUITES, Type.STRING, "", Importance.LOW,
            "This property is used to specify a comma separated list of "
            + "cipher suites in order of preference used for SSL connections. ")
        .define(SolaceSinkConstants.SOL_SSL_VALIDATE_CERTIFICATE,
            Type.BOOLEAN, true, Importance.LOW,
            "This property is used to specify whether the API should validate server certificates ")
        .define(SolaceSinkConstants.SOL_SSL_VALIDATE_CERTIFICATE_DATE,
            Type.BOOLEAN, true, Importance.LOW,
            "This property is used to specify whether the API should validate "
            + "server certificate's expiry")
        .define(SolaceSinkConstants.SOL_SSL_TRUST_STORE,
            Type.STRING, "/lib/security/jssecacerts", Importance.LOW,
            "This property is used to specify the truststore file to use in URL or path format.")
        .define(SolaceSinkConstants.SOL_SSL_TRUST_STORE_PASSWORD, Type.PASSWORD, "", Importance.LOW,
            "This property is used to specify the password of the truststore "
            + "given in SSL_TRUST_STORE")
        .define(SolaceSinkConstants.SOL_SSL_TRUST_STORE_FORMAT,
            Type.STRING, "JKS", Importance.LOW,
            "This property is used to specify the format of the truststore "
            + "given in SSL_TRUST_STORE.")
        .define(SolaceSinkConstants.SOL_SSL_TRUSTED_COMMON_NAME_LIST,
            Type.STRING, "", Importance.LOW,
            "This property is used to specify a comma separated list of acceptable "
            + "common names for matching with server certificates.")
        .define(SolaceSinkConstants.SOL_SSL_KEY_STORE, Type.STRING, "", Importance.LOW,
            "This property is used to specify the keystore file to use in URL or path format.")
        .define(SolaceSinkConstants.SOL_SSL_KEY_STORE_PASSWORD, Type.PASSWORD, "", Importance.LOW,
            "This property is used to specify the password of the "
            + "keystore specified by SSL_KEY_STORE.")
        .define(SolaceSinkConstants.SOL_SSL_KEY_STORE_FORMAT, Type.STRING, "JKS", Importance.LOW,
            "This property is used to specify the format of the keystore given in SSL_KEY_STORE.")
        .define(SolaceSinkConstants.SOL_SSL_KEY_STORE_NORMALIZED_FORMAT,
            Type.STRING, "JKS", Importance.LOW,
            "This property is used to specify the format of an internal normalized "
            + "representation of the keystore if it needs to be different from the default format.")
        .define(SolaceSinkConstants.SOL_SSL_PRIVATE_KEY_ALIAS,
            Type.STRING, "", Importance.LOW,
            "This property is used to specify the alias of the private key to use "
            + "for client certificate authentication.")
        .define(SolaceSinkConstants.SOL_SSL_PRIVATE_KEY_PASSWORD,
            Type.PASSWORD, "", Importance.LOW,
            "This property is used to specify the password that deciphers "
            + "the private key from the key store.")
        .define(SolaceSinkConstants.SOL_ACK_EVENT_MODE,
            Type.STRING, "SUPPORTED_ACK_EVENT_MODE_WINDOWED",
            Importance.MEDIUM,
            "API sends out message acknowledgement event for Guaranteed Messages,"
            + " windowed or per message.")
        .define(SolaceSinkConstants.SOL_PUBLISHER_WINDOW_SIZE, Type.INT, 18, Importance.MEDIUM,
            "The size of the sliding publisher window for Guaranteed messages.")
        .define(SolaceSinkConstants.SOL_KAFKA_MESSAGE_KEY, Type.STRING, "NONE", Importance.MEDIUM,
            "This propert determines if a Kafka key record is created and the key to be used")
        .define(SolaceSinkConstants.SOL_KAFKA_REPLAY_OFFSET, Type.LONG, null, Importance.LOW,
            "This propert determines the offset to start processing on Connector Startup"
            + ". Default of null means start from last saved offset, "
            + "0 means from beginning of topic.")
        .define(SolaceSinkConstants.SOL_DYNAMIC_DESTINATION, Type.BOOLEAN, false, Importance.MEDIUM,
            "Indicates whether the SolRecordProcessor should control the destination generation")
        .define(SolaceSinkConstants.SOL_KERBEROS_KRB5_CONFIG, Type.STRING, "", Importance.LOW,
                "The location of the KRB5 configuration file for the Kerberos Server Detail")
        .define(SolaceSinkConstants.SOL_KERBEROS_LOGIN_CONFIG, Type.STRING, "", Importance.LOW,
            "Location of the Kerberos Login Configuration File")

        .define(
                SolaceSinkConstants.SOL_EMIT_KAFKA_RECORD_HEADERS_ENABLED,
                Type.BOOLEAN,
                false,
                Importance.LOW,
                "Should Kafka headers be automatically copied to Solace messages as user properties."
        )
        ;

  }

  public String[] getTopics() {
    return Optional.ofNullable(getString(SolaceSinkConstants.SOL_TOPICS))
            .map(s -> s.split(","))
            .orElse(new String[0]);
  }

  public boolean isEmitKafkaRecordHeadersEnabled() {
    return getBoolean(SolaceSinkConstants.SOL_EMIT_KAFKA_RECORD_HEADERS_ENABLED);
  }

  static ConfigDef config = solaceConfigDef();

}
