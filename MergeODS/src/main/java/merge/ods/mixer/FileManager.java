package merge.ods.mixer;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class FileManager extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7230481050345251742L;

	public FileManager(){
		super("Mezclador");
		setVisible(true);
		setSize(400, 400);
		
		JButton boton = new JButton("Seleccione los 3 archivos a mezclar");
		boton.addMouseListener(new MouseListener() {
			
			
			
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			public void mouseClicked(MouseEvent e) {
				openFileOpener();
			}
		});
		
		getContentPane().add(boton);
	}
	
	public void openFileOpener(){
		FileChooser fileChooser = new FileChooser(this);
		fileChooser.showOpenDialog(this);
	}
	
	public static void main(String[] args) {
		FileManager manager = new FileManager();

	}
}
