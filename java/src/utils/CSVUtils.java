package utils;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;

public class CSVUtils {

	public static String[][] fetchCsvFile(String fileName) throws IOException {
		String[][] tableArray;

		try (CSVReader csvReader = new CSVReader(new FileReader(fileName))) {

			List<String[]> lines = csvReader.readAll();

			tableArray = new String[lines.size()][lines.get(0).length];

			for (int i = 0; i < lines.size(); i++) {
				tableArray[i] = lines.get(i);
			}
		}

		return tableArray;
	}
}
