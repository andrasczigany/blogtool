import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 * File: BlogToolWindow.java <br>
 * Description: Tool for generating posts for http://juve.blog.hu <br>
 * <br>
 * @author <a href='mailto:andras.czigany@gmail.com'>Andras Czigany</a><br>
 * <br>
 */
public class BlogToolWindow extends JFrame {

  public final int MAIN_X = 300;
  public final int MAIN_Y = 200;
  
  private BlogTextPanel blogTextPanel = null;
  private BlogPicturePanel blogPicturePanel = null;
  private BlogVideoPanel blogVideoPanel = null;
  private BlogTagsPanel blogTagsPanel = null;
  private BlogButtonPanel blogButtonPanel = null;
  
  public BlogToolWindow() {
    BlogToolModel.getInstance(this);
    this.initialize();
    this.pack();
  }
  
  private void initialize() {
    try {
      UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    this.setTitle("BlogTool 1.0");
    this.setIconImage(new ImageIcon(getClass().getClassLoader().getResource("blogicon.jpg")).getImage());
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLocation(MAIN_X, MAIN_Y);
    
    this.setContentPane(getMainPanel());
  }
  
  private JPanel getMainPanel() {
    JPanel helperPanel = new JPanel(new BorderLayout(0, 0));
    helperPanel.add(getBlogPicturePanel(), BorderLayout.CENTER);
    helperPanel.add(getBlogVideoPanel(), BorderLayout.SOUTH);
    
    JPanel helperPanel2 = new JPanel(new BorderLayout(0, 0));
    helperPanel2.add(helperPanel, BorderLayout.CENTER);
    helperPanel2.add(getBlogTagsPanel(), BorderLayout.SOUTH);
    
    JPanel mainPanel = new JPanel(new BorderLayout(0, 0));
    mainPanel.add(getBlogTextPanel(), BorderLayout.NORTH);
    mainPanel.add(helperPanel2, BorderLayout.CENTER);
    mainPanel.add(getBlogButtonPanel(), BorderLayout.SOUTH);

    return mainPanel;
  }
  
  public BlogTextPanel getBlogTextPanel() {
    if (blogTextPanel == null)
      blogTextPanel = new BlogTextPanel(this);
    return blogTextPanel;
  }
  
  public BlogPicturePanel getBlogPicturePanel() {
    if (blogPicturePanel == null)
      blogPicturePanel = new BlogPicturePanel(this);
    return blogPicturePanel;
  }

  public BlogVideoPanel getBlogVideoPanel() {
    if (blogVideoPanel == null)
      blogVideoPanel = new BlogVideoPanel();
    return blogVideoPanel;
  }
  
  public BlogTagsPanel getBlogTagsPanel() {
    if (blogTagsPanel == null)
      blogTagsPanel = new BlogTagsPanel();
    return blogTagsPanel;
  }

  public BlogButtonPanel getBlogButtonPanel() {
    if (blogButtonPanel == null)
      blogButtonPanel = new BlogButtonPanel(this);
    return blogButtonPanel;
  }

  /**
   * @param args
   */
  public static void main(String[] args) {
    new BlogToolWindow().setVisible(true);
  }

}
