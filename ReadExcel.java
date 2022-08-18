package marathon;

import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcel {
	
	public static String[][] readData(String fileName) throws IOException
	{
		//read the excel file
		XSSFWorkbook wBook = new XSSFWorkbook("data/"+fileName+".xlsx");
		
		// get the Sheet with index 0
		XSSFSheet sheet = wBook.getSheetAt(0);
		
		int rowCount = sheet.getLastRowNum();
		
		short columnCount = sheet.getRow(0).getLastCellNum();
		
		String data[][] = new String[rowCount][columnCount];
		
		for (int i=1; i<=rowCount; i++)
		{
			XSSFRow row = sheet.getRow(i);
			for (int j=0; j<columnCount; j++)
			{
				XSSFCell cell = row.getCell(j);
				System.out.println(cell.getStringCellValue());
				data[i-1][j] = cell.getStringCellValue();
			}
		}
		
		wBook.close();
		return data;
	}

}
