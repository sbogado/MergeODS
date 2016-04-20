package merge.ods.test;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import junit.framework.Assert;
import merge.ods.mixer.Mixer;

import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;
import org.junit.Before;
import org.junit.Test;

public class MixerTest {

	private File original;
	private File firstChange;
	private File secondChange;
	private Mixer mixer;

	@Before
	public void setup() throws FileNotFoundException, IOException {
		mixer = new Mixer();
		createOriginal();
		createFirstChange();
		createSecondChange();
	}

	private void createOriginal() throws FileNotFoundException, IOException {
		original = createBaseSpreadSheetFile(Mixer.ORIGINAL_FILE_NAME);
	}

	private void createFirstChange() throws FileNotFoundException, IOException {
		firstChange = createBaseSpreadSheetFile(Mixer.FIRST_CHANGED_FILE_NAME);
	}

	private void createSecondChange() throws FileNotFoundException, IOException {
		secondChange = createBaseSpreadSheetFile(Mixer.SECOND_CHANGED_FILE_NAME);
	}

	private File createBaseSpreadSheetFile(String name)
			throws FileNotFoundException, IOException {
		final Object[][] data = new Object[1][4];
		data[0] = new Object[] { "Sergio", "Bogado", 26, 0 };
		String[] columns = new String[] { "Nopmbre", "Apellido", "Edad",
				"Hijos" };
		TableModel model = new DefaultTableModel(data, columns);
		final File file = new File(name);
		SpreadSheet spreadSheet = SpreadSheet.createEmpty(model);
		spreadSheet.getFirstSheet().setName("Activos");
		spreadSheet.saveAs(file);
		return file;
	}

	@Test
	public void testMixCellOnlyFirstHaveAChange() throws IOException {
		final Sheet sheet1 = SpreadSheet.createFromFile(firstChange).getSheet("Activos");
		sheet1.getCellAt("A2").setValue("Sebastian");
		sheet1.getSpreadSheet().saveAs(firstChange);
		
		File result = mixer.mixCell(original, firstChange, secondChange,"A2");
		final Sheet resultSheet = SpreadSheet.createFromFile(result).getSheet("Activos");
		
		Assert.assertEquals("Sebastian", resultSheet.getCellAt("A2").getValue());
	}
	
	@Test
	public void testMixCellOnlySecondHaveAChange() throws IOException {
		final Sheet sheet1 = SpreadSheet.createFromFile(secondChange).getSheet("Activos");
		sheet1.getCellAt("A2").setValue("Juanito");
		sheet1.getSpreadSheet().saveAs(secondChange);
		
		File result = mixer.mixCell(original, firstChange, secondChange,"A2");
		final Sheet resultSheet = SpreadSheet.createFromFile(result).getSheet("Activos");
		
		Assert.assertEquals("Juanito", resultSheet.getCellAt("A2").getValue());
	}
	
	@Test
	public void testMixCellBothHaveAChanged() throws IOException {
		final Sheet sheet1 = SpreadSheet.createFromFile(firstChange).getSheet("Activos");
		sheet1.getCellAt("A2").setValue("Sebastian");
		sheet1.getSpreadSheet().saveAs(firstChange);
		
		final Sheet sheet2 = SpreadSheet.createFromFile(secondChange).getSheet("Activos");
		sheet2.getCellAt("A2").setValue("Junior");
		sheet2.getSpreadSheet().saveAs(secondChange);
		
		File result = mixer.mixCell(original, firstChange, secondChange,"A2");
		final Sheet resultSheet = SpreadSheet.createFromFile(result).getSheet("Activos");
		
		Assert.assertEquals("Sebastian,Junior", resultSheet.getCellAt("A2").getValue());
		Assert.assertEquals(Color.RED, resultSheet.getCellAt("A2").getStyle().getBackgroundColor(resultSheet.getCellAt("A2")));
	}
}
