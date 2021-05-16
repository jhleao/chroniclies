package leao.historinhas.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class IpStoryBlock {
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column
  private String ip;

  @ManyToOne
  private Story story;

  public IpStoryBlock(){
  }

  public IpStoryBlock(String ip, Story story){
    this.ip = ip;
    this.story = story;
  }

  public String getIp(){
    return this.ip;
  }

  public Story getStory(){
    return this.story;
  }

  public void setIp(String ip){
    this.ip = ip;
  }

  public void setStory(Story story){
    this.story = story;
  }

  @Override
  public String toString(){
    return "IpBlock{" +
            "id: " + id +
            " | ip: " + ip +
            " | storyId: " + story.getId() +
            "}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    IpStoryBlock ipStoryBlock = (IpStoryBlock) o;

    return id != null ? id.equals(ipStoryBlock.id) : ipStoryBlock.id == null;
  }

  @Override
  public int hashCode(){
    return id != null ? id.hashCode() : 0;
  }

}
