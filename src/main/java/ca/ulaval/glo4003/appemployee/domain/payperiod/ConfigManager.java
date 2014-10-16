package ca.ulaval.glo4003.appemployee.domain.payperiod;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {
	
	private String fileName = "payPeriods.txt";
	private Properties properties = new Properties();
	private static ConfigManager instance = null;

	public ConfigManager() {
		try {
			properties.load(new FileInputStream(fileName));
		} catch (FileNotFoundException e) {
			System.out.println("File could not be found: " + fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static ConfigManager getInstance() {
		if (instance == null) {
			instance = new ConfigManager();
		}
		return instance;
	}

	public String getProperty(String key) {
		return properties.getProperty(key);
	}
}
	