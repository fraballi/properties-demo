package org.fraballi.main;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

	public static void main(String[] args) {

		final ClassLoader loader = Main.class.getClassLoader();

		try (final InputStream is = loader.getResourceAsStream("application.properties")) {

			final Scanner scan = new Scanner(is);
			final Map<String, String> map = new HashMap<>();

			while (scan.hasNextLine()) {
				final String line = scan.nextLine();
				log.debug(line);
				final String[] segments = line.split("=");
				map.put(segments[0], segments[1]);
			}

			log.debug("Properties: {}", map);

		} catch (Exception e) {
			log.error("Resource Not Found: {}", e.getMessage());
		}
	}
}
