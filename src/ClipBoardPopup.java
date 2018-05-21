

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.text.JTextComponent;

public class ClipBoardPopup extends MouseAdapter implements ActionListener {
	
	private JMenuItem copy = new JMenuItem("Másolás");
	private JPopupMenu menu = new JPopupMenu();
	
	private JTextComponent owner;
	
	private static ClipBoardPopup me;
	
	/**
	 * 
	 */
	private ClipBoardPopup() {
		copy.addActionListener(this);
		menu.add(copy);
	}
	
	public static ClipBoardPopup getInstance() {
		if(me == null) {
			me = new ClipBoardPopup();
		}
		return me;
	}
	
	public void actionPerformed(ActionEvent e) {
		if(owner != null) {
			if(e.getSource() == copy) {
        owner.setSelectionStart(0);
        owner.setSelectionEnd(owner.getText().length());
				owner.copy();
			}
		}
	}
	
	public void mouseClicked(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON3 && e.getSource() instanceof JTextComponent) {
			owner = (JTextComponent)e.getSource();
			menu.show(owner,e.getX(),e.getY());
		}
	}
	
}
