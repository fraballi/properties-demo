package org.fraballi.utils;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

public final class ResourcesUtil {

	private static final String INVALID_PATH = "Invalid Path";

	private ResourcesUtil() {
	}

	public static Properties readFrom(String path) throws IOException {
		if (path == null || path.length() > 0 || !Paths.get(path).toFile().exists())
			throw new IllegalArgumentException(INVALID_PATH);

		return read(new FileInputStream(path));
	}

	public static Properties read(InputStream is) throws IOException {
		if (is == null)
			throw new IllegalArgumentException("Stream is invalid");

		final Properties properties = new Properties();
		properties.load(is);

		is.close();

		return properties;
	}

	public static ResourceBundle read(String path) {
		if (path == null || path.length() > 0 || !Paths.get(path).toFile().exists())
			throw new IllegalArgumentException(INVALID_PATH);

		final ResourceBundle bundle = ResourceBundle.getBundle(path);
		if (bundle.keySet().isEmpty())
			throw new IllegalArgumentException("Resource is empty");

		return bundle;
	}

	public static Properties read(final ResourceBundle bundle) {
		if (bundle == null || bundle.keySet().isEmpty())
			throw new IllegalArgumentException("Resource is null or empty");

		final Properties properties = new Properties();
		bundle.keySet().forEach(k -> properties.put(k, bundle.getObject(k)));

		return properties;
	}

	public static void write(String path, final Map<String, String> mapping) throws IOException {
		if (path == null || path.length() == 0 || mapping == null || mapping.isEmpty())
			throw new IllegalArgumentException(INVALID_PATH);

		final Properties properties = new Properties();
		mapping.forEach(properties::put);

		properties.store(new FileWriter(path), "");
	}
}
