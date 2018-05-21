import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.TitledBorder;


public class BlogResultDialog extends JDialog {

  private static final int FIELD_WIDTH = 50;
  private JPanel mainPanel = null;
  private JPanel textPanel = null;
  private JPanel buttonPanel = null;
  private JScrollPane postPane = null;
  private JTextField title = null;
  private JTextArea post = null;
  private JTextField tags = null;
  private JButton close = null;
  
  public BlogResultDialog(BlogToolWindow parent) {
    super(parent);
    this.setModal(true);
    this.setTitle("Poszt");
    this.setLocation(parent.getLocation().x + 30, parent.getLocation().y + 50);
    this.setContentPane(getMainPanel());
    this.pack();
  }
  
  private JPanel bp(JComponent c, String t) {
    JPanel borderPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 5, 5));
    borderPanel.setBorder(new TitledBorder(t));
    borderPanel.add(c);
    return borderPanel;
  }

  public JPanel getMainPanel() {
    mainPanel = new JPanel(new BorderLayout(0, 0));
    mainPanel.add(getTextPanel(), BorderLayout.CENTER);
    mainPanel.add(getButtonPanel(), BorderLayout.SOUTH);
    return mainPanel;
  }

  public JPanel getTextPanel() {
    textPanel = new JPanel(new BorderLayout(0, 0));
    textPanel.add(bp(getTitleField(), "Cím"), BorderLayout.NORTH);
    textPanel.add(bp(getPostPane(), "Szöveg"), BorderLayout.CENTER);
    textPanel.add(bp(getTags(), "Címkék"), BorderLayout.SOUTH);
    return textPanel;
  }

  public JPanel getButtonPanel() {
    buttonPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING, 5, 5));
    buttonPanel.add(getClose());
    return buttonPanel;
  }
  
  public JScrollPane getPostPane() {
    postPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    postPane.setViewportView(getPost());
    return postPane;
  }

  public JTextField getTitleField() {
    if (title != null)
      return title;
    title = new JTextField(FIELD_WIDTH);
    title.addMouseListener(ClipBoardPopup.getInstance());
    return title;
  }

  public JTextArea getPost() {
    if (post != null)
      return post;
    post = new JTextArea(10, FIELD_WIDTH);
    post.setFont(new JTextField().getFont());
    post.setWrapStyleWord(true);
    post.setLineWrap(true);
    post.addMouseListener(ClipBoardPopup.getInstance());
    InputMap iMap = post.getInputMap();
    iMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), "TAB");
    ActionMap aMap = post.getActionMap();
    aMap.put("TAB", new AbstractAction("TAB") {
      public void actionPerformed(ActionEvent e) {
        getTags().grabFocus();
      }
    });
    return post;
  }

  public JTextField getTags() {
    if (tags != null)
      return tags;
    tags = new JTextField(FIELD_WIDTH);
    tags.addMouseListener(ClipBoardPopup.getInstance());
    return tags;
  }

  public JButton getClose() {
    close = new JButton("Bezár");
    close.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        dispose();
      }
    });
    return close;
  }
  
  
}
