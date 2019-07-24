package common.resources;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

public class CignitiProperties {

	private String cignitiPropertyFile = "common/resources/cigniti.properties";
	Properties properties;

	public CignitiProperties() {
		fileProcessor();
	}

	public CignitiProperties(String serviceName) {
		if (serviceName.equalsIgnoreCase("weather"))
			cignitiPropertyFile = "restful/resources/weather.properties";
		else if (serviceName.equalsIgnoreCase("aon"))
			cignitiPropertyFile = "soap/resources/aon.properties";
		else if (serviceName.equalsIgnoreCase("reqres"))
			cignitiPropertyFile = "restful/resources/reqres.properties";
		else if (serviceName.equalsIgnoreCase("reqres1"))
			cignitiPropertyFile = "pact/resources/reqres.properties";
		else
			cignitiPropertyFile = "common/resources/cigniti.properties";
		fileProcessor();
	}

	public void fileProcessor() {
		properties = new Properties();
		try {
			InputStream inputStream = CignitiProperties.class.getClassLoader().getResourceAsStream(cignitiPropertyFile);
			properties.load(inputStream);
		} catch (FileNotFoundException e) {
			System.out.println("Property File could not be found: " + cignitiPropertyFile);
		} catch (Exception e) {
			System.out.println("Issues reading properties of :" + cignitiPropertyFile);
		}
	}

	public String getProperty(String key) {
		String value = "";
		try {
			value = properties.getProperty(key);
		} catch (Exception e) {
			System.out.println("Unable to find the key : " + key);
		} finally {
			return value;
		}
	}
}
