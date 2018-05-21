import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.DefaultComboBoxModel;
import javax.swing.InputMap;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

public class BlogTextPanel extends JPanel {

  private static final int GAP_X = 3; 
  private static final int GAP_Y = 3; 
  
  private BlogToolWindow mainWindow = null;
  
  private JLabel lOpponent = null;
  private JTextField iOpponent = null;
  private JLabel lResult = null;
  private JTextField iResult = null;

  private JLabel lDate = null;
  private JTextField iDate = null;
  private JLabel lStadium = null;
  private JTextField iStadium = null;

  private JLabel lCompetition = null;
  private JComboBox iCompetition = null;
  private JLabel lRound = null;
  private JTextField iRound = null;
  
  private JLabel lReferee = null;
  private JTextField iReferee = null;
  private JLabel lHome = null;
  private JCheckBox iHome = null;
  
  private JLabel lSquadJuventus = null;
  private JTextArea iSquadJuventus = null;
  private JLabel lCoachJuventus = null;
  private JTextField iCoachJuventus = null;
  
  private JLabel lSquadOpponent = null;
  private JTextArea iSquadOpponent = null;
  private JLabel lCoachOpponent = null;
  private JTextField iCoachOpponent = null;
  
  private JLabel lScorers = null;
  private JTextField iScorers = null;
  private JLabel lCards = null;
  private JTextField iCards = null;
  
  private JLabel lFreeText = null;
  private JTextArea iFreeText = null;

  public BlogTextPanel(BlogToolWindow main) {
    mainWindow = main;
    
    FlexibleLayout fl = new FlexibleLayout();
    fl.setRowNum(8);
    fl.setRowType(0, FlexibleLayout.ROW_TYPE_SSSS);
    fl.setRowType(1, FlexibleLayout.ROW_TYPE_SSSS);
    fl.setRowType(2, FlexibleLayout.ROW_TYPE_SSSS);
    fl.setRowType(3, FlexibleLayout.ROW_TYPE_SSSS);
    fl.setRowType(4, FlexibleLayout.ROW_TYPE_SSSS);
    fl.setRowType(5, FlexibleLayout.ROW_TYPE_SSSS);
    fl.setRowType(6, FlexibleLayout.ROW_TYPE_SSSS);
    fl.setRowType(7, FlexibleLayout.ROW_TYPE_L);

    this.setLayout(fl);
    
    // row 1
    this.add(p(getLOpponent()), new FlexibleLayout.FlexibleDefinitor(0, 0));
    this.add(p(getIOpponent()), new FlexibleLayout.FlexibleDefinitor(0, 1));
    this.add(p(getLResult()), new FlexibleLayout.FlexibleDefinitor(0, 2));
    this.add(p(getIResult()), new FlexibleLayout.FlexibleDefinitor(0, 3));

    // row 2
    this.add(p(getLDate()), new FlexibleLayout.FlexibleDefinitor(1, 0));
    this.add(p(getIDate()), new FlexibleLayout.FlexibleDefinitor(1, 1));
    this.add(p(getLStadium()), new FlexibleLayout.FlexibleDefinitor(1, 2));
    this.add(p(getIStadium()), new FlexibleLayout.FlexibleDefinitor(1, 3));
    
    // row 3
    this.add(p(getLCompetition()), new FlexibleLayout.FlexibleDefinitor(2, 0));
    this.add(p(getICompetition()), new FlexibleLayout.FlexibleDefinitor(2, 1));
    this.add(p(getLRound()), new FlexibleLayout.FlexibleDefinitor(2, 2));
    this.add(p(getIRound()), new FlexibleLayout.FlexibleDefinitor(2, 3));
    
    // row 4
    this.add(p(getLReferee()), new FlexibleLayout.FlexibleDefinitor(3, 0));
    this.add(p(getIReferee()), new FlexibleLayout.FlexibleDefinitor(3, 1));
    this.add(p(getLHome()), new FlexibleLayout.FlexibleDefinitor(3, 2));
    this.add(p(getIHome(), new FlowLayout(FlowLayout.LEADING, 0, GAP_Y)), new FlexibleLayout.FlexibleDefinitor(3, 3));
    
    // row 5
    this.add(p(getLSquadJuventus()), new FlexibleLayout.FlexibleDefinitor(4, 0));
    this.add(p(s(getISquadJuventus())), new FlexibleLayout.FlexibleDefinitor(4, 1));
    this.add(p(getLCoachJuventus()), new FlexibleLayout.FlexibleDefinitor(4, 2));
    this.add(p(getICoachJuventus()), new FlexibleLayout.FlexibleDefinitor(4, 3));
    
    // row 6
    this.add(p(getLSquadOpponent()), new FlexibleLayout.FlexibleDefinitor(5, 0));
    this.add(p(s(getISquadOpponent())), new FlexibleLayout.FlexibleDefinitor(5, 1));
    this.add(p(getLCoachOpponent()), new FlexibleLayout.FlexibleDefinitor(5, 2));
    this.add(p(getICoachOpponent()), new FlexibleLayout.FlexibleDefinitor(5, 3));
    
    // row 7
    this.add(p(getLScorers()), new FlexibleLayout.FlexibleDefinitor(6, 0));
    this.add(p(getIScorers()), new FlexibleLayout.FlexibleDefinitor(6, 1));
    this.add(p(getLCards()), new FlexibleLayout.FlexibleDefinitor(6, 2));
    this.add(p(getICards()), new FlexibleLayout.FlexibleDefinitor(6, 3));
    
    // row 8
    FlexibleLayout fl2 = new FlexibleLayout();
    fl2.setRowNum(1);
    fl2.setRowType(0, FlexibleLayout.ROW_TYPE_MM);
    JPanel tmp = new JPanel();
    tmp.setLayout(fl2);
    tmp.add(p(getLFreeText()), new FlexibleLayout.FlexibleDefinitor(0, 0, true));
    tmp.add(p(s(getIFreeText()), new FlowLayout(FlowLayout.LEADING, 8, GAP_Y)), new FlexibleLayout.FlexibleDefinitor(0, 0));
    this.add(tmp, new FlexibleLayout.FlexibleDefinitor(7, 0));
  }
  
  private JPanel p(JComponent c) {
    return p(c, new FlowLayout(FlowLayout.LEADING, GAP_X, GAP_Y));
  }

  private JPanel p(JComponent c, LayoutManager layout) {
    JPanel containerPanel = new JPanel(layout);
    containerPanel.add(c);
    return containerPanel;
  }
  
  private JScrollPane s(JComponent c) {
    JScrollPane pane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    pane.setViewportView(c);
    return pane;
  }

  public JLabel getLOpponent() {
    lOpponent = new JLabel("Ellenfél");
    return lOpponent;
  }

  public JTextField getIOpponent() {
    if (iOpponent != null)
      return iOpponent;
    iOpponent = new JTextField(12);
    return iOpponent;
  }

  public JLabel getLResult() {
    lResult = new JLabel("Eredmény");
    return lResult;
  }

  public JTextField getIResult() {
    if (iResult != null)
      return iResult;
    iResult = new JTextField(6);
    return iResult;
  }

  public JLabel getLDate() {
    lDate = new JLabel("Dátum");
    return lDate;
  }

  public JTextField getIDate() {
    if (iDate != null)
      return iDate;
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.DATE, -1);
    iDate = new JTextField(new SimpleDateFormat("yyyy.MM.dd").format(cal.getTime()) + " 15:00", 10);
    return iDate;
  }

  public JLabel getLStadium() {
    lStadium = new JLabel("Helyszín");
    return lStadium;
  }

  public JTextField getIStadium() {
    if (iStadium != null)
      return iStadium;
    iStadium = new JTextField(16);
    return iStadium;
  }

  public JLabel getLCompetition() {
    lCompetition = new JLabel("Bajnokság");
    return lCompetition;
  }

  public JComboBox getICompetition() {
    if (iCompetition != null)
      return iCompetition;
    iCompetition = new JComboBox(new DefaultComboBoxModel(
        new String[] {"Serie A", "Coppa Italia", "Bajnokok Ligája", "Barátságos"}));
    return iCompetition;
  }

  public JLabel getLRound() {
    lRound = new JLabel("Forduló");
    return lRound;
  }

  public JTextField getIRound() {
    if (iRound != null)
      return iRound;
    iRound = new JTextField(10);
    return iRound;
  }

  public JLabel getLReferee() {
    lReferee = new JLabel("Bíró");
    return lReferee;
  }

  public JTextField getIReferee() {
    if (iReferee != null)
      return iReferee;
    iReferee = new JTextField(10);
    return iReferee;
  }

  public JLabel getLHome() {
    lHome = new JLabel("Hazai");
    return lHome;
  }

  public JCheckBox getIHome() {
    if (iHome != null)
      return iHome;
    iHome = new JCheckBox("");
    iHome.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (iHome.isSelected())
          getIStadium().setText("Stadio Olimpico, Torino");
        else
          getIStadium().setText("");
      }
    });
    return iHome;
  }

  public JLabel getLSquadJuventus() {
    lSquadJuventus = new JLabel("Juventus");
    return lSquadJuventus;
  }

  public JTextArea getISquadJuventus() {
    if (iSquadJuventus != null)
      return iSquadJuventus;
    iSquadJuventus = new JTextArea(4, 24);
    iSquadJuventus.setFont(new JTextField().getFont());
    iSquadJuventus.setLineWrap(true);
    iSquadJuventus.setWrapStyleWord(true);
    InputMap iMap = iSquadJuventus.getInputMap();
    iMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), "TAB");
    ActionMap aMap = iSquadJuventus.getActionMap();
    aMap.put("TAB", new AbstractAction("TAB") {
      public void actionPerformed(ActionEvent e) {
        getISquadOpponent().grabFocus();
      }
    });
    return iSquadJuventus;
  }

  public JLabel getLCoachJuventus() {
    lCoachJuventus = new JLabel("Juventus edzõ");
    return lCoachJuventus;
  }

  public JTextField getICoachJuventus() {
    if (iCoachJuventus != null)
      return iCoachJuventus;
    iCoachJuventus = new JTextField("Ranieri", 10);
    return iCoachJuventus;
  }

  public JLabel getLSquadOpponent() {
    lSquadOpponent = new JLabel("Ellenfél");
    return lSquadOpponent;
  }

  public JTextArea getISquadOpponent() {
    if (iSquadOpponent != null)
      return iSquadOpponent;
    iSquadOpponent = new JTextArea(4, 24);
    iSquadOpponent.setFont(new JTextField().getFont());
    iSquadOpponent.setLineWrap(true);
    iSquadOpponent.setWrapStyleWord(true);
    InputMap iMap = iSquadOpponent.getInputMap();
    iMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), "TAB");
    ActionMap aMap = iSquadOpponent.getActionMap();
    aMap.put("TAB", new AbstractAction("TAB") {
      public void actionPerformed(ActionEvent e) {
        getICoachOpponent().grabFocus();
      }
    });
    return iSquadOpponent;
  }

  public JLabel getLCoachOpponent() {
    lCoachOpponent = new JLabel("Ellenfél edzõ");
    return lCoachOpponent;
  }

  public JTextField getICoachOpponent() {
    if (iCoachOpponent != null)
      return iCoachOpponent;
    iCoachOpponent = new JTextField(10);
    return iCoachOpponent;
  }
  
  public JLabel getLScorers() {
    lScorers = new JLabel("Gólszerzõ");
    return lScorers;
  }

  public JTextField getIScorers() {
    if (iScorers != null)
      return iScorers;
    iScorers = new JTextField(20);
    return iScorers;
  }

  public JLabel getLCards() {
    lCards = new JLabel("Lapok");
    return lCards;
  }

  public JTextField getICards() {
    if (iCards != null)
      return iCards;
    iCards = new JTextField(20);
    return iCards;
  }
  
  public JLabel getLFreeText() {
    lFreeText = new JLabel("Komment");
    return lFreeText;
  }

  public JTextArea getIFreeText() {
    if (iFreeText != null)
      return iFreeText;
    iFreeText = new JTextArea(5, 36);
    iFreeText.setFont(new JTextField().getFont());
    iFreeText.setLineWrap(true);
    iFreeText.setWrapStyleWord(true);
    InputMap iMap = iFreeText.getInputMap();
    iMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), "TAB");
    ActionMap aMap = iFreeText.getActionMap();
    aMap.put("TAB", new AbstractAction("TAB") {
      public void actionPerformed(ActionEvent e) {
        mainWindow.getBlogTagsPanel().getFTags().grabFocus();
      }
    });
    return iFreeText;
  }

  
}
