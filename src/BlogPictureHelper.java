import java.io.File;


public class BlogPictureHelper {

  private String webpath = "";    // fodlername on blog.hu
  private int more = 3;   // place of more tag
  
  private String prefix = "<img src=\"http://juve.blog.hu/media/image/";
  private String postfix = "\" alt=\"\" />";
  
  public BlogPictureHelper(String wp, String m) {
    
    if (wp != null)
      webpath = wp;
    
    if (!"".equals(webpath) && (!webpath.endsWith("/") || !webpath.endsWith("\\")))
      webpath += "/";
    
    if (m != null)
      try {
        more = Integer.parseInt(m);
      } catch (Exception e) {}
  }
  
  public String process(File[] files) {
    StringBuffer res = new StringBuffer();
    int counter = 0;
    
    // collect file names
    for (File f : files) {
      res.append(prefix).append(webpath).append(f.getName()).append(postfix).append("\n");
      counter++;
      if (counter == more)
        res.append("<!--more-->").append("\n");
    }
    
    return res.toString();
  }
  
}
