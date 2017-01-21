package driver;

/**
 * Created by Felipe on 21/02/16.
 * 
 * The File class represents a file path object that has content that one can
 * set and get.
 */
public class File extends PathObject {

  // the content of the file
  private String content;

  /**
   * Constructs a File object with a specified name and empty content.
   *
   * @param name Name of the File to be created.
   */
  public File(String name) {
    super(name);
    content = "";
  }// end constructor

  /**
   * Change the content of the File
   *
   * @param content New content of the File
   */
  public void setContent(String content) {
    this.content = content;
  }// end method setContent

  /**
   * Returns the content of the file
   *
   * @return Returns the content of the file
   */
  public String getContent() {
    return content;
  }// end method getContent

}// end class File
