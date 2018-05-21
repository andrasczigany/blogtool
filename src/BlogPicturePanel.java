import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class BlogPicturePanel extends JPanel {

  private BlogToolWindow mainWindow = null;
  
  private JFileChooser fileChooser = null;
  private JList list = null;
  private DefaultListModel listModel = null;
  private JScrollPane listPane = null;
  private JPanel southPanel = null;
  private JLabel lWebDir = null;
  private JTextField iWebDir = null;
  private JLabel lMore = null;
  private JTextField iMore = null;
  private JButton selectButton = null;

  public BlogPicturePanel(BlogToolWindow main) {
    mainWindow = main;
    this.setBorder(new TitledBorder("Képek"));
    this.setLayout(new BorderLayout(0, 0));
    this.add(getListPane(), BorderLayout.CENTER);
    this.add(getSouthPanel(), BorderLayout.SOUTH);
  }
  
  public File[] getFileList() {
    File[] files = new File[getList().getModel().getSize()];
    for (int i=0; i<getList().getModel().getSize(); i++) {
      files[i] = (File)getList().getModel().getElementAt(i);
    }
    return files;
  }

  private JScrollPane getListPane() {
    if (listPane  == null) {
      listPane = new JScrollPane();
      listPane.setAutoscrolls(true);
      listPane.setWheelScrollingEnabled(true);
      listPane.setPreferredSize(new Dimension(60, 100));
      listPane.setViewportView(getList());
    }
    return listPane;
  }

  private JList getList() {
    if (list == null) {
      list = new JList(new DefaultListModel());
    }
    return list;
  }

  private JFileChooser getFileChooser() {
    if (fileChooser == null) {
      fileChooser = new JFileChooser();
      fileChooser.setDialogTitle("Képek");
      fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
      fileChooser.setMultiSelectionEnabled(true);
      fileChooser.setCurrentDirectory(new File("D:\\stuff\\dl\\"));
    }
    return fileChooser;
  }

  public JLabel getLWebDir() {
    lWebDir = new JLabel("Blog.hu mappa");
    return lWebDir;
  }

  public JTextField getIWebDir() {
    if (iWebDir != null)
      return iWebDir;
    iWebDir = new JTextField(10);
    return iWebDir;
  }

  public JLabel getLMore() {
    lMore = new JLabel("\"Tovább\" pozíciója");
    return lMore;
  }

  public JTextField getIMore() {
    if (iMore != null)
      return iMore;
    iMore = new JTextField("0", 2);
    return iMore;
  }

  private JPanel getSouthPanel() {
    southPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 5, 5));
    southPanel.add(getLWebDir());
    southPanel.add(getIWebDir());
    southPanel.add(getLMore());
    southPanel.add(getIMore());
    southPanel.add(getSelectButton());
    return southPanel;
  }

  private JButton getSelectButton() {
    selectButton = new JButton("Képek megnyitása");
    selectButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        openFileChooser();
      }
    });
    return selectButton;
  }

  private void openFileChooser() {
    if (!(getFileChooser().showDialog(this, "OK") == JFileChooser.CANCEL_OPTION)) {
      listModel = ((DefaultListModel) getList().getModel());
      for (int i = 0; i < getFileChooser().getSelectedFiles().length; i++) {
        listModel.addElement(getFileChooser().getSelectedFiles()[i]);
      }
      getList().setModel(listModel);
      
      // fill webDir field
      String date = mainWindow.getBlogTextPanel().getIDate().getText();
      if (!"".equals(date)) {
        StringBuilder dir = new StringBuilder("");
        dir.append(date.substring(0, 4)).append("-").append(date.substring(5, 7)).append("-").append(date.substring(8, 10));
        dir.append("_").append(mainWindow.getBlogTextPanel().getIOpponent().getText().toLowerCase().replaceAll(" ", "_"));
        getIWebDir().setText(dir.toString());
      }
    }
  }

}
