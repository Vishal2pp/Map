package org.cdac.charts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.SheetUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class XLSXtoJSONConverter {
	private String DATE_PATTERN;
	private int DATE_CELL;
	private int DATE_ROW;
	private int TOTAL_VALUES_ROW;
	private int CBSE_SCHOOLS_INDEX;
	private int CBSE_SCHOOLS_TRAINED_INDEX;
	private int TEACHER_TRAINED_INDEX;
	private int STATES_NAME_INDEX;
	private int START_ROW_OF_STATES;
	private int LAST_ROW_OF_STATES;
	private String FIRST_ROW_DATA_VALIDATOR;
	
	private int STATE_SCHOOLS_INDEX;
	private int STATE_TEACHER_TRAINED_INDEX;
	private int STATE_SCHOOLS_TRAINED_INDEX;

	public String completeJSON = null;
	private XSSFSheet spreadsheet;

	public XLSXtoJSONConverter(AMChartsConfigurator amc) {
		DATE_PATTERN = amc.getDatePattern();
		DATE_CELL = amc.getDateCell();
		DATE_ROW = amc.getDateRow();
		TOTAL_VALUES_ROW = amc.getTotalValueRow();
		CBSE_SCHOOLS_INDEX = amc.getCbseSchoolIndex();
		CBSE_SCHOOLS_TRAINED_INDEX = amc.getCbseSchoolTrainedIndex();
		TEACHER_TRAINED_INDEX = amc.getTeacherTrainedIndex();
		STATES_NAME_INDEX = amc.getStatesNameIndex();
		START_ROW_OF_STATES = amc.getStartRowOfStates();
		LAST_ROW_OF_STATES = amc.getLastRowOfStates();
		FIRST_ROW_DATA_VALIDATOR = amc.getFirstRowDataValidator();
		
		STATE_SCHOOLS_INDEX = amc.getStateSchoolIndex();
		STATE_TEACHER_TRAINED_INDEX = amc.getStateTeacherTrainedIndex();
		STATE_SCHOOLS_TRAINED_INDEX = amc.getStateSchoolTrainedIndex();
	}

	public XSSFSheet getSpreadsheet() {
		return spreadsheet;
	}

	public void setSpreadsheet(XSSFSheet spreadsheet) {
		this.spreadsheet = spreadsheet;
	}

	public boolean readAndCheck(String path) {
		readFirstSheet(path);
		boolean isValid = checkForValidity(spreadsheet);
		return isValid;
	}

	public void convert() {
		JSONObject jsonValue = extractValues(spreadsheet);
		JSONObject summaryJsonValue = extractTotalValues(spreadsheet);
		JSONObject jsonDate = extractDateValues(spreadsheet);
		String staticJSONString = readStaticJSONWithStream("static.json");
		JSONParser parser = new JSONParser();
		JSONObject json = null;

		try {

			json = (JSONObject) parser.parse(staticJSONString);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (jsonValue != null) {
			json.put("states", jsonValue);
			json.put("total", summaryJsonValue);
			json.put("date", jsonDate);
			completeJSON = json.toJSONString();
			URL resourceUrl = XLSXtoJSONConverter.class.getResource("/data.json");
			System.out.println(resourceUrl.getPath());
			try {
				writeToJSONFile(resourceUrl.toURI(), json.toJSONString());
			} catch (URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
	}

	private void readFirstSheet(String path) {
		XSSFWorkbook workbook = readExcelSheet(path);
		XSSFSheet spreadsheet = workbook.getSheetAt(0);
		this.spreadsheet = spreadsheet;
	}

	JSONObject stateNameJson = new JSONObject();
	JSONObject teacherData = null;

	private JSONObject extractValues(XSSFSheet spreadsheet) 
	{
		Iterator<Row> rowIterator = spreadsheet.iterator();
		XSSFRow row = null;
		while (rowIterator.hasNext()) 
		{
			row = (XSSFRow) rowIterator.next();
			int rowNumber = row.getRowNum();
			if (rowNumber >= START_ROW_OF_STATES && rowNumber <= LAST_ROW_OF_STATES) 
			{
				Iterator<Cell> cellIterator = row.cellIterator();
				teacherData = new JSONObject();

				while (cellIterator.hasNext()) 
				{
					Cell cell = cellIterator.next();
					populateSingleRowToJSON(cell);
				}

				JSONObject statsData = new JSONObject();
				statsData.put("data", teacherData);
				stateNameJson.put(stateName, statsData);

			} 
			else if (IsEndOfRows(rowNumber))
				break;
		}
		return stateNameJson;
	}

	private boolean IsEndOfRows(int rowNumber) 
	{
		return rowNumber > LAST_ROW_OF_STATES;
	}

	private boolean checkForValidity(XSSFSheet spreadsheet) 
	{
		return spreadsheet.getRow(0).getCell(0).toString().equals(FIRST_ROW_DATA_VALIDATOR);
	}

	String stateName = null;

	private void populateSingleRowToJSON(Cell cell) 
	{
		if (cell.getColumnIndex() == STATES_NAME_INDEX) 
		{
			stateName = cell.getStringCellValue();
		}

		if (cell.getColumnIndex() == TEACHER_TRAINED_INDEX) 
		{
			teacherData.put("TeachersTrained", (int) cell.getNumericCellValue());
		}

		if (cell.getColumnIndex() == CBSE_SCHOOLS_TRAINED_INDEX) 
		{
			teacherData.put("CBSESchoolsTrained", (int) cell.getNumericCellValue());
		}

		if (cell.getColumnIndex() == CBSE_SCHOOLS_INDEX) 
		{
			teacherData.put("TotalCBSESchools", (int) cell.getNumericCellValue());
		}
		
		if(cell.getColumnIndex()==STATE_TEACHER_TRAINED_INDEX)
		{
			teacherData.put("StateTeachersTrained", (int) cell.getNumericCellValue());
		}
		
		if(cell.getColumnIndex()==STATE_SCHOOLS_TRAINED_INDEX)
		{
			teacherData.put("StateSchoolsTrained", (int) cell.getNumericCellValue());
		}
		if (cell.getColumnIndex() == STATE_SCHOOLS_INDEX) 
		{
			//System.out.println( cell.getNumericCellValue());
			teacherData.put("TotalStateSchools", (int) cell.getNumericCellValue());
		}
		
	}

	private JSONObject extractTotalValues(XSSFSheet spreadsheet) 
	{

		XSSFRow row = spreadsheet.getRow(TOTAL_VALUES_ROW);

		JSONObject summaryJson = new JSONObject();

		summaryJson.put("Total_CBSESchoolTeachersTrained",
				(int) SheetUtil.getCellWithMerges(spreadsheet, 1, 1).getNumericCellValue());
		
		summaryJson.put("Total_CBSESchoolsTrained",
				(int) SheetUtil.getCellWithMerges(spreadsheet, 1, 4).getNumericCellValue());
		
		summaryJson.put("TotalCBSESchools", (int) SheetUtil.getCellWithMerges(spreadsheet, 1, 7).getNumericCellValue());

		summaryJson.put("Total_StateSchoolTeachersTrained",
				(int) SheetUtil.getCellWithMerges(spreadsheet, 1, 9).getNumericCellValue());
		
		summaryJson.put("Total_StateSchoolsTrained",
				(int) SheetUtil.getCellWithMerges(spreadsheet, 1, 12).getNumericCellValue());
		
		summaryJson.put("TotalStateSchools", (int) SheetUtil.getCellWithMerges(spreadsheet, 1, 15).getNumericCellValue());
		return summaryJson;
	}

	private JSONObject extractDateValues(XSSFSheet spreadsheet) 
	{
		XSSFCell cell = spreadsheet.getRow(DATE_ROW).getCell(DATE_CELL);
		JSONObject DateJson = new JSONObject();
		DateFormat df = new SimpleDateFormat(DATE_PATTERN);
		Date date = cell.getDateCellValue();
		String reportDate = df.format(date);
		DateJson.put("Last_Date_Upadted", "As of " + reportDate);
		return DateJson;
	}

	private XSSFWorkbook readExcelSheet(String path) 
	{
		FileInputStream fis = null;
		XSSFWorkbook workbook = null;
		try {
			fis = new FileInputStream(new File(path));
			workbook = new XSSFWorkbook(fis);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return workbook;
	}

	private void writeToJSONFile(URI uri, String jsonValue) 
	{
		PrintWriter fos = null;
		try {
			File f = new File(uri);
			fos = new PrintWriter(f);
			fos.write(jsonValue.toString());

		} catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} finally {

			fos.close();

		}

	}

	private String readStaticJSONWithStream(String fileName) 
	{
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream input = classLoader.getResourceAsStream(fileName);

		BufferedReader br = null;
		StringBuilder sb = null;
		br = new BufferedReader(new InputStreamReader(input));
		try {
			sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return sb.toString();

	}

	private String readStaticJSON(String path) {

		BufferedReader br = null;
		StringBuilder sb = null;
		try {
			br = new BufferedReader(new FileReader(path));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {

			sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return sb.toString();
		
		
	}
	
}
