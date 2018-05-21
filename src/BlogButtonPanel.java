import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class BlogButtonPanel extends JPanel {

  private JFrame parent = null;
  public BlogButtonPanel(JFrame p) {
    this.parent = p;
    
    JButton bCompose = new JButton("Mehet");
    bCompose.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        BlogToolModel.getInstance().compose();
      }
    });
    
    JButton bExit = new JButton("Kilépés");
    bExit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        parent.dispose();
      }
    });
    
    int buttonWidth = Math.max(bCompose.getPreferredSize().width, bExit.getPreferredSize().width);
    bCompose.setPreferredSize(new Dimension(buttonWidth, bCompose.getPreferredSize().height));
    bExit.setPreferredSize(new Dimension(buttonWidth, bExit.getPreferredSize().height));

    this.setLayout(new FlowLayout(FlowLayout.TRAILING, 5, 5));
    this.add(bCompose);
    this.add(bExit);
  }
}
