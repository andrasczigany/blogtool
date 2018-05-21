import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;


public class BlogVideoPanel extends JPanel {

  private JLabel lUrl = null;
  private JTextField iUrl = null;
  private JLabel lUrl2 = null;
  private JTextField iUrl2 = null;
  
  public BlogVideoPanel() {
    this.setBorder(new TitledBorder("Videó"));
    this.setLayout(new FlowLayout(FlowLayout.LEADING, 5, 5));
    this.add(getLUrl());
    this.add(getIUrl());
    this.add(getLUrl2());
    this.add(getIUrl2());
  }

  public JLabel getLUrl() {
    lUrl = new JLabel("Link");
    return lUrl;
  }

  public JTextField getIUrl() {
    if (iUrl != null)
      return iUrl;
    iUrl = new JTextField(24);
    return iUrl;
  }
  
  public JLabel getLUrl2() {
    lUrl2 = new JLabel("Rövid link");
    return lUrl2;
  }

  public JTextField getIUrl2() {
    if (iUrl2 != null)
      return iUrl2;
    iUrl2 = new JTextField(12);
    return iUrl2;
  }
  
}
