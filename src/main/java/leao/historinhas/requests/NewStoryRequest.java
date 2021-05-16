package leao.historinhas.requests;

public class NewStoryRequest {

  private String content;

  private String email;

  
  public NewStoryRequest(){
  }


  public NewStoryRequest(String content, String email){
    this.content = content;
    this.email = email;
  }

  public NewStoryRequest(String content){
    this.content = content;
  }


  public String getContent(){
    return this.content;
  }

  public String getEmail(){
    return this.email;
  }

  public void setContent(String content){
    this.content = content;
  }

  public void setEmail(String email){
    this.email = email;
  }

  @Override
  public String toString(){
    return "NewStoryRequest{" +
            " | email: " + email +
            " | content: " + content +
            "}";
  }

}