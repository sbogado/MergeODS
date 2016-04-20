package merge.ods.mixer;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class FileChooser extends JFileChooser {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3471631501989332189L;
	
	private JFrame mainWindow;
	
	public FileChooser(JFrame mainWindow){
		this.mainWindow = mainWindow;
	}

	
}
