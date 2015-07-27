//
// Loads a csv or tsv file into a table structure.
//
// Code taken from Ben Fry's "Visualizing Data" book, example ch03-usmap
// and extended by Pierre Dragicevic. Uses regex parsing code from Nathan Spears.
//
// @author Ben Fry, Pierre Dragicevic, Nathan Spears.
//
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import utils.CSVUtils;

class Table {
	int rowCount = 0;
	int colCount = 0;
	String[][] data = null;
	// The following code replaces split() since it's not capable of handling
	// cell values occasionally surrounded by double quotes.
	// Code taken from Nathan Spears,
	// http://stackoverflow.com/questions/1441556/parsing-csv-input-with-a-regex-in-java

	private ArrayList<String> allMatches = new ArrayList<String>();
	private int size;

	// Creates a Table from a csv or tsv file.
	//
	// The separator between rows in the input file has to be a carriage return.
	// The separator between columns is provided as a constructor argument and
	// can be anything like "\t", ",", ";", etc.
	// Double quote delimiters are properly handled, i.e., formats like
	// 1000,"100,000",10.
	// All empty lines and lines starting with # are silently skipped. The third
	// argument speficies additional lines to
	// ignore (line counting starts from 1) and can be set to null. The first
	// unskipped line in the input file needs to
	// contain column names.
	Table(String filename, String separator, int[] linesToIgnore)
			throws IOException {
		System.out.print("Loading file " + filename + "... ");
		data = CSVUtils.fetchCsvFile(filename);
		rowCount = data.length-1;
		colCount = data[0].length;
	}

	// Copies the table
	Table(Table table) {
		for (int idx = 0; idx < table.data.length; ++idx) {
			data[idx] = table.data[idx];
		}
		rowCount = data.length;
		colCount = data[0].length;
	}

	// Returns the number of rows, not counting the first row with column names
	int getRowCount() {
		return rowCount;
	}

	// Returns the number of columns
	int getColumnCount() {
		return colCount;
	}

	// Find a column by its name, returns -1 if no column found
	int getColumnIndex(String name) {
		for (int i = 0; i < colCount; i++) {
			if (data[0][i].equals(name)) {
				return i;
			}
		}
		System.out.println("No column named '" + name + "' was found");
		return -1;
	}

	// Returns the name of the column of given index
	String getColumnName(int column) {
		return data[0][column];
	}

	// Reads a cell value. Rows and column indices start from zero.
	String getS(int column, int row) {
		return data[row+1][column]; // do not count column names
	}

	// Reads a cell value as an int. Rows and column indices start from zero.
	// This method will try to parse dirty numerical formats by removing spaces
	// and commas.
	// If the cell cannot be parsed as an integer, returns Integer.MAX_VALUE.
	// Avoid calling this method multiples times, as it is rather slow. See
	// getColumnAsInts()
	// for an alternative.
	int getInt(int column, int row) {
		String value = getS(column, row);
		int intValue = 0;
		try{
			intValue = Integer.parseInt(value);
		} catch(NumberFormatException e){
			//e.printStackTrace();
		}
		return intValue;
	}

	// Reads a cell value as a float. Rows and column indices start from zero.
	// This method will try to parse dirty numerical formats by removing spaces
	// and commas.
	// If the cell cannot be parsed as an integer, returns NaN.
	// Avoid calling this method multiples times, as it is rather slow. See
	// getColumnAsFloats()
	// for an alternative.
	float getFloat(int column, int row) {
		String stringValue = getS(column, row);
		float floatValue = 0.0f;
		try {
			floatValue = Float.parseFloat(stringValue);
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return floatValue;
	}

	// Extracts a column as an array of strings.
	String[] getColumn(int column) {
		String[] col = new String[rowCount];
		for (int i = 0; i < rowCount; i++)
			col[i] = getS(column, i);
		return col;
	}

	// Extracts a column as a numerical array for a more optimized processing in
	// case
	// numerical values have to be read multiple times.
	// See getInt() for how non-numerical cell values are handled.
	int[] getColumnAsInts(int column) {
		int[] col = new int[rowCount];
		for (int i = 0; i < rowCount; i++)
			col[i] = smartParseInt(getS(column, i));
		return col;
	}

	// Extracts a column as a numerical array for a more optimized processing in
	// case
	// numerical values have to be read multiple times.
	// See getFloat() for how non-numerical cell values are handled.
	float[] getColumnAsFloats(int column) {
		float[] col = new float[rowCount];
		for (int i = 0; i < rowCount; i++)
			col[i] = smartParseFloat(getS(column, i));
		return col;
	}

	// Modifies a cell. Rows and column indices start from zero.
	void set(int column, int row, String value) {
		data[row + 1][column] = value;
	}

	// Saves the table to a csv file
	void save(String filename, String separator) throws FileNotFoundException {
		PrintWriter output = new PrintWriter(filename);
		for (int c = 0; c < colCount; c++) {
			String cell = getColumnName(c);
			if (cell.indexOf(separator) != -1)
				cell = "\"" + cell + "\"";
			output.print(cell + ((c < colCount - 1) ? separator : "\n"));
		}
		for (int r = 0; r < rowCount; r++) {
			for (int c = 0; c < colCount; c++) {
				String cell = getS(c, r);
				if (cell.indexOf(separator) != -1)
					cell = "\"" + cell + "\"";
				output.print(cell + ((c < colCount - 1) ? separator : "\n"));
			}
		}
		output.flush();
		output.close();
	}

	// /////////////////////////////////////////////////////////////////////////////////////////////////
	// utility functions
	// /////////////////////////////////////////////////////////////////////////////////////////////////

	private boolean contains(int[] arr, int value) {
		for (int i = 0; i < arr.length; i++)
			if (arr[i] == value)
				return true;
		return false;
	}

	int smartParseInt(String s) {
		// We use isInt here because contrary to Processing's specification,
		// parseInt does not return NaN if the string is not parseable.
		if (isInt(s))
			return Integer.parseInt(s);
		else {
			s = s.replaceAll(" ", ""); // remove spaces
			s = s.replaceAll(",", ""); // remove commas
			if (isInt(s)) {
				return Integer.parseInt(s);
			}
		}
		return Integer.MAX_VALUE;
	}

	float smartParseFloat(String s) {
		float value = Float.parseFloat(s);
		if (Float.isNaN(value)) {
			s = s.replaceAll(" ", ""); // remove spaces
			s = s.replaceAll(",", ""); // remove commas
			value = Float.parseFloat(s);
		}
		return value;
	}

	boolean isInt(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}