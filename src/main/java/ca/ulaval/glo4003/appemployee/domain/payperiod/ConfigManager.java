package ca.ulaval.glo4003.appemployee.domain.payperiod;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

public class ConfigManager {
	
	private String payPeriodfile = "payPeriods.txt";
	List<Map.Entry<String, String>> payPeriodDates = new java.util.ArrayList<>();
	private static ConfigManager instance = null;

	public ConfigManager() {
		try {
			loadPayPeriodsFile(payPeriodfile);
		} catch (FileNotFoundException e) {
			System.out.println("File could not be found: " + payPeriodfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void loadPayPeriodsFile(String fileName) throws IOException {
		
		List<Map.Entry<String, String>> payPeriodDates = new java.util.ArrayList<>();
		
		FileReader input = new FileReader(fileName);
		BufferedReader reader = new BufferedReader(input);
		String myLine = null;
			while ( (myLine = reader.readLine()) != null)
			{    
			    String[] arrayOfDates = myLine.split(";");
			    payPeriodDates.add(new AbstractMap.SimpleEntry<>(arrayOfDates[0], arrayOfDates[1]));
			}
			reader.close();		
		
	}

	public static ConfigManager getInstance() {
		if (instance == null) {
			instance = new ConfigManager();
		}
		return instance;
	}

	public List<Map.Entry<String, String>> getPayPeriodDates() {
		return payPeriodDates;
	}
}
	