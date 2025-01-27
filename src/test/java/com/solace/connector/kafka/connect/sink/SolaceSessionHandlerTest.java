package com.solace.connector.kafka.connect.sink;

import com.solacesystems.jcsmp.JCSMPProperties;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SolaceSessionHandlerTest {
	@ParameterizedTest
	@CsvSource({
			SolaceSinkConstants.SOL_PASSWORD + ',' + JCSMPProperties.PASSWORD,
			SolaceSinkConstants.SOL_SSL_KEY_STORE_PASSWORD + ',' + JCSMPProperties.SSL_KEY_STORE_PASSWORD,
			SolaceSinkConstants.SOL_SSL_PRIVATE_KEY_PASSWORD + ',' + JCSMPProperties.SSL_PRIVATE_KEY_PASSWORD,
			SolaceSinkConstants.SOL_SSL_TRUST_STORE_PASSWORD + ',' + JCSMPProperties.SSL_TRUST_STORE_PASSWORD
	})
	public void testConfigurePasswords(String connectorProperty, String jcsmpProperty) {
		Map<String, String> properties = new HashMap<>();
		properties.put(connectorProperty, RandomStringUtils.randomAlphanumeric(30));
		SolSessionHandler sessionHandler = new SolSessionHandler(new SolaceSinkConnectorConfig(properties));
		sessionHandler.configureSession();
		assertEquals(properties.get(connectorProperty),
				sessionHandler.properties.getStringProperty(jcsmpProperty));
	}

	@ParameterizedTest
	@CsvSource({
			SolaceSinkConstants.SOL_PASSWORD + ',' + JCSMPProperties.PASSWORD,
			SolaceSinkConstants.SOL_SSL_KEY_STORE_PASSWORD + ',' + JCSMPProperties.SSL_KEY_STORE_PASSWORD,
			SolaceSinkConstants.SOL_SSL_PRIVATE_KEY_PASSWORD + ',' + JCSMPProperties.SSL_PRIVATE_KEY_PASSWORD,
			SolaceSinkConstants.SOL_SSL_TRUST_STORE_PASSWORD + ',' + JCSMPProperties.SSL_TRUST_STORE_PASSWORD
	})
	public void testConfigureNullPasswords(String connectorProperty, String jcsmpProperty) {
		Map<String, String> properties = new HashMap<>();
		properties.put(connectorProperty, null);
		SolSessionHandler sessionHandler = new SolSessionHandler(new SolaceSinkConnectorConfig(properties));
		sessionHandler.configureSession();
		assertEquals(properties.get(connectorProperty), sessionHandler.properties.getStringProperty(jcsmpProperty));
	}
}
