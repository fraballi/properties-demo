package org.fraballi.utils.tests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.fraballi.utils.ResourcesUtil;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResourcesUtilTests {

	private static final String validPath = "src/test/resources/the-properties-file.properties";
	private static final String invalidPath = "the-invalid-properties-file.properties";

	@Test(expected = IllegalArgumentException.class)
	public void checkInvalidBundle() {
		ResourcesUtil.read(invalidPath);
	}

	@Test(expected = IllegalArgumentException.class)
	public void checkInvalidPropertiesFile() {
		try {
			ResourcesUtil.readFrom(invalidPath);
		} catch (IOException e) {
			log.debug(e.getMessage());
			assertTrue(Boolean.TRUE);
		}
	}

	@Test
	public void checkWriteAndReadProperties() {
		final String appNameProperty = "application.name";
		final String appName = "Resource Tests";
		final Map<String, String> mapping = new HashMap<>();

		mapping.put(appNameProperty, appName);

		try {
			ResourcesUtil.write(validPath, mapping);
			final Properties properties = ResourcesUtil
					.read(this.getClass().getClassLoader().getResourceAsStream("the-properties-file.properties"));

			assertThat(properties.keySet()).hasSize(1).contains(appNameProperty);

			final Object propertyValue = properties.getOrDefault(appNameProperty, "");
			assertThat(String.valueOf(propertyValue)).isEqualTo(appName);

		} catch (IOException e) {
			log.error("Exception: {}", e.getMessage());
		}
	}
}
