import java.awt.FlowLayout;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;


public class BlogTagsPanel extends JPanel {

  private JTextField fTags = null;
  
  public BlogTagsPanel() {
    this.setBorder(new TitledBorder("Címkék"));
    this.setLayout(new FlowLayout(FlowLayout.LEADING, 5, 5));
    this.add(getFTags());
  }
  
  public JTextField getFTags() {
    if (fTags != null)
      return fTags;
    fTags = new JTextField(50);
    fTags.setToolTipText("Vesszõvel elválasztva: Del Piero,Trezeguet,Nedved");
    return fTags;
  }
}
