import java.io.File;


public class BlogToolModel {

  private static final String JUVENTUS = "Juventus";
  private static final String COMMA = ", ";
  private static final String DASH = "-";
  private static final String SPACE = " ";
  private static final String DDOT = ": ";
  private static final String NEW_LINE = "<br />";
  private static final String EMPTY_LINE = "<p style=\"text-align: justify;\">&nbsp;</p>";

  private static final String COMMENT_BEGIN = "<p style=\"text-align: justify;\">";
  private static final String COMMENT_END = "</p>";

  private static final String MATCH_BEGIN = "<p style=\"text-align: justify;\">";
  private static final String MATCH_TITLE_BEGIN = "<u><strong>";
  private static final String MATCH_TITLE_END = "</strong></u>";
  private static final String MATCH_TEAM_BEGIN = "<em><strong>";
  private static final String MATCH_TEAM_END = "</strong></em>";
  private static final String MATCH_OTHER_BEGIN = "<em>";
  private static final String MATCH_OTHER_END = "</em>";
  private static final String MATCH_END = "</p>";
  
  private static final String URL_BEGIN = "<a href=\"";
  private static final String URL_END1 = "\">";
  private static final String URL_END2 = "</a>";

  private static BlogToolModel instance = null;
  private static BlogToolWindow mainWindow = null;

  private BlogToolModel() {

  }

  public static BlogToolModel getInstance() {
    if (instance == null) {
      instance = new BlogToolModel();
    }
    return instance;
  }

  public static BlogToolModel getInstance(BlogToolWindow window) {
    if (instance == null) {
      instance = new BlogToolModel();
      mainWindow = window;
    }
    return instance;
  }

  public void compose() {
    BlogResultDialog dialog = new BlogResultDialog(mainWindow);
    dialog.getTitleField().setText(composeTitle());
    dialog.getPost().setText(composePost());
    dialog.getTags().setText(composeTags());
    dialog.getTitleField().selectAll();
    dialog.setVisible(true);
  }

  private String composeTitle() {
    BlogTextPanel tp = mainWindow.getBlogTextPanel();
    String opponent = tp.getIOpponent().getText();
    if ("".equals(opponent))
      return "";
    return (tp.getIHome().isSelected() ? ("Juventus" + "-" + opponent) : (opponent + "-" + "Juventus")) + " "
        + tp.getIResult().getText();
  }

  private String composePost() {
    /*
    <p style="text-align: justify;">Kiegyenlített, nehéz mérkõzésen, Del Piero hihetetlen szabadrúgásgóljával sikerült legyõzni a friss UEFA-kupa és Európai Szuperkupa-gyõztes Zenit Szentpétervár csapatát. A Juve két év után tehát diadallal tért vissza a Bajnokok Ligájába.</p>
    <p style="text-align: justify;"><u><strong>Bajnokok Ligája, 1. forduló, Juventus-Zenit 1-0</strong></u><br />
    2008.09.17. 20:45, Stadio Olimpico, Torino, vezette: De Bleeckere (Belgium)<br />
    <em><strong>Juventus:</strong></em> Buffon, Grygera, Legrottaglie, Chiellini, Molinaro (De Ceglie 57), Camoranesi (Salihamidzic 32), Sissoko, Poulsen, Nedved, Del Piero, Trezeguet (Amauri 87). - Edzõ: Ranieri<br />
    <em><strong>Zenit:</strong></em> Malafeev, Anyukov, Puygrenier, Krizanac, Sirl, Zyryanov (Dominguez 80), Tymoshchuk, Denisov, Danny, Arshavin, Pogrebnyak. - Edzõ: Advocaat<br />
    <em>Gólszerzõ:</em> Del Piero 76</p>
    <p style="text-align: justify;">&nbsp;</p>    
    */

    BlogTextPanel tp = mainWindow.getBlogTextPanel();
    BlogPicturePanel pp = mainWindow.getBlogPicturePanel();
    BlogVideoPanel vp = mainWindow.getBlogVideoPanel();

    StringBuilder free = new StringBuilder("");
    if (!"".equals(tp.getIFreeText().getText()))
      free.append(COMMENT_BEGIN).append(tp.getIFreeText().getText()).append(COMMENT_END).append("\n");

    // comp, round, home-away x-y BR
    StringBuilder matchTitle = new StringBuilder("");
    if (!"".equals(tp.getIOpponent().getText()))
    matchTitle.append(MATCH_TITLE_BEGIN).append(tp.getICompetition().getSelectedItem()).append(COMMA).append(
        tp.getIRound().getText()).append(COMMA).append(
        tp.getIHome().isSelected() ? JUVENTUS : tp.getIOpponent().getText()).append(DASH).append(
        tp.getIHome().isSelected() ? tp.getIOpponent().getText() : JUVENTUS).append(SPACE).append(
        tp.getIResult().getText()).append(MATCH_TITLE_END).append(NEW_LINE).append("\n");

    // date, stadium, "Játékvezetõ: " + referee BR
    StringBuilder matchInfo = new StringBuilder("");
    if (!"".equals(tp.getIReferee().getText()))
    matchInfo.append(tp.getIDate().getText()).append(COMMA).append(tp.getIStadium().getText()).append(COMMA).append(
        "Játékvezetõ: ").append(tp.getIReferee().getText()).append(NEW_LINE).append("\n");

    // _'team:'_ squad "- Edzõ: " + coach BR 
    StringBuilder teamJuve = new StringBuilder("");
    if (!"".equals(tp.getISquadJuventus().getText()))
    teamJuve.append(MATCH_TEAM_BEGIN).append(JUVENTUS).append(DDOT).append(MATCH_TEAM_END).append(
        tp.getISquadJuventus().getText()).append(SPACE).append(DASH).append(SPACE).append("Edzõ: ").append(
        tp.getICoachJuventus().getText()).append(NEW_LINE).append("\n");

    // _'team:'_ squad "- Edzõ: " + coach BR 
    StringBuilder teamOpponent = new StringBuilder("");
    if (!"".equals(tp.getISquadOpponent().getText()))
    teamOpponent.append(MATCH_TEAM_BEGIN).append(tp.getIOpponent().getText()).append(DDOT).append(MATCH_TEAM_END)
        .append(tp.getISquadOpponent().getText()).append(SPACE).append(DASH).append(SPACE).append("Edzõ: ").append(
            tp.getICoachOpponent().getText()).append(NEW_LINE).append("\n");

    // 'Gólszerzõ: ' scorers BR
    StringBuilder goals = new StringBuilder("");
    if (!"".equals(tp.getIScorers().getText()))
      goals.append(MATCH_OTHER_BEGIN).append("Gólszerzõ: ").append(MATCH_OTHER_END).append(tp.getIScorers().getText())
          .append(NEW_LINE).append("\n");

    // 'Lapok: ' cards BR
    StringBuilder cards = new StringBuilder("");
    if (!"".equals(tp.getICards().getText()))
      cards.append(MATCH_OTHER_BEGIN).append("Lapok: ").append(MATCH_OTHER_END).append(tp.getICards().getText())
          .append(NEW_LINE).append("\n");

    // pictures
    StringBuilder pics = new StringBuilder("");
    File[] files = pp.getFileList();
    if (files.length > 0) {
      BlogPictureHelper ph = new BlogPictureHelper(pp.getIWebDir().getText(), "0".equals(pp.getIMore().getText()) ? "-1" : pp.getIMore().getText());
      pics.append(EMPTY_LINE).append(ph.process(files)).append("\n");
    }

    // video
    StringBuilder video = new StringBuilder("");
    if (!"".equals(vp.getIUrl().getText())) {
      video.append(EMPTY_LINE).append(vp.getIUrl().getText());
    }
    StringBuilder video2 = new StringBuilder("");
    String url = vp.getIUrl2().getText().replaceAll("www.youtube", "youtube");
    if (!"".equals(url)) {
      video2.append("Link: ").append(URL_BEGIN).append(url).append(URL_END1).append(url).append(URL_END2).append("\n");
    }

    boolean isMatch = matchTitle.length() > 0 || matchInfo.length() > 0 || teamJuve.length() > 0;
    return free.append(isMatch ? MATCH_BEGIN : "")
      .append(matchTitle)
      .append(matchInfo)
      .append(tp.getIHome().isSelected() ? teamJuve.append(teamOpponent) : teamOpponent.append(teamJuve))
      .append(goals)
      .append(cards)
      .append(isMatch ? MATCH_END : "")
      .append(video).append(video2)
      .append(pics)
      .toString();
  }

  private String composeTags() {
    BlogTagsPanel tp = mainWindow.getBlogTagsPanel();
    BlogTextPanel textp = mainWindow.getBlogTextPanel();
    BlogPicturePanel pp = mainWindow.getBlogPicturePanel();
    BlogVideoPanel vp = mainWindow.getBlogVideoPanel();
    StringBuilder tags = new StringBuilder("r:meccsek");
    String competition = textp.getICompetition().getSelectedItem().toString();
    if (competition != null) {
      if (competition.indexOf("Serie") > -1)
        tags.append(SPACE).append("serie_a");
      else if (competition.indexOf("Bajnokok") > -1)
        tags.append(SPACE).append("bl 2008-2009");
      else if (competition.indexOf("Coppa") > -1)
        tags.append(SPACE).append("coppa_italia");
    }

    tags.append(SPACE).append(textp.getIOpponent().getText().replaceAll(" ", "_"));

    if (pp.getFileList().length > 0)
      tags.append(SPACE).append("r:képek");

    if (!"".equals(vp.getIUrl().getText()))
      tags.append(SPACE).append("r:videók");

    tags.append(SPACE).append(tp.getFTags().getText().replaceAll(" ", "_").replaceAll(",", SPACE));
    return tags.toString().toLowerCase();
  }
}
