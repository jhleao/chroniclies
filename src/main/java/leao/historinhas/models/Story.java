package leao.historinhas.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Story {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column
  private String content;

  @Column
  private String email;
  
  @Column
  private StoryStatus status;

  @OneToMany
  @JoinColumn(name = "story_id")
  private Set<IpStoryBlock> blockedIps = new HashSet<>();
  
  public Story(){
  }

  public Story(String content, String email, StoryStatus status){
    this.status = status;
    this.content = content;
    this.email = email;
  }

  public Story(String content, String email){
    this.status = StoryStatus.PENDING;
    this.content = content;
    this.email = email;
  }

  public Story(String content){
    this.status = StoryStatus.PENDING;
    this.content = content;
  }

  public void approve(){
    this.status = StoryStatus.APPROVED;
  }


  public Integer getId(){
    return this.id;
  }

  public String getFormattedId(){
    return String.format("%03d", this.id);
  }

  public String getContent(){
    return this.content;
  }

  public String getEmail(){
    return this.email;
  }


  public void setId(Integer id){
    this.id = id;
  }

  public void setContent(String content){
    this.content = content;
  }

  public void setEmail(String email){
    this.email = email;
  }

  @Override
  public String toString(){
    return "Story{" +
            "id: " + id +
            " | status: " + status +
            " | email: " + email +
            " | content: " + content +
            "}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Story story = (Story) o;

    return id != null ? id.equals(story.id) : story.id == null;
  }

  @Override
  public int hashCode(){
    return id != null ? id.hashCode() : 0;
  }
}
