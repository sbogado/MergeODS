package merge.ods.mixer;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

public class Mixer {
	
	public static final String RESULT_FILE_NAME = "originalChanged.ods"; 
	public static final String ORIGINAL_FILE_NAME = "original.ods"; 
	public static final String FIRST_CHANGED_FILE_NAME = "firstChange.ods"; 
	public static final String SECOND_CHANGED_FILE_NAME = "secondChange.ods"; 

	public File mixCell(File original, File firstChange, File secondChange,String cellLocation) throws IOException {
		final Sheet sheetOriginal = SpreadSheet.createFromFile(original).getSheet("Activos");
		final Sheet sheetFirstChange = SpreadSheet.createFromFile(firstChange).getSheet("Activos");
		final Sheet sheetSecondChange = SpreadSheet.createFromFile(secondChange).getSheet("Activos");
		
		if(sheetOriginal.getCellAt(cellLocation).getValue().equals(sheetFirstChange.getCellAt(cellLocation).getValue())){
			return setCellValue(sheetOriginal,sheetSecondChange.getCellAt(cellLocation).getValue().toString(),cellLocation);
		}
		
		if(sheetOriginal.getCellAt(cellLocation).getValue().equals(sheetSecondChange.getCellAt(cellLocation).getValue())){
			return setCellValue(sheetOriginal,sheetFirstChange.getCellAt(cellLocation).getValue().toString(),cellLocation);
		}
		setCellValue(sheetOriginal,sheetFirstChange.getCellAt(cellLocation).getValue()+","+sheetSecondChange.getCellAt(cellLocation).getValue(),cellLocation);
		sheetOriginal.getCellAt(cellLocation).setBackgroundColor(Color.RED);
		return sheetOriginal.getSpreadSheet().saveAs(new File(RESULT_FILE_NAME));
	}
	
	private File setCellValue(Sheet sheetOriginal,String value,String cellLocation) throws FileNotFoundException, IOException{
		sheetOriginal.getCellAt(cellLocation).setValue(value);
		return sheetOriginal.getSpreadSheet().saveAs(new File(RESULT_FILE_NAME));
	}

}
