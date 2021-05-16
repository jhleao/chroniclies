package leao.historinhas.models;

import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;

@Entity
public class Moderator implements UserDetails {
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column
  private String username;

  @Column
  private String password;

  @Column
  private UserRole role = UserRole.MODERATOR;

  public Moderator(){
  }

  public Moderator(String username, String password){
    this.username = username;
    this.password = hash(password);
  }

  public void setUsername(String username){
    this.username = username;
  }

  public void setPassword(String password){
    this.password = hash(password);
  }

  public String hash(String toHash){
    return BCrypt.hashpw(toHash, BCrypt.gensalt(5));
  }

  @Override
  public String getUsername() {
    return this.username;
  }

  @Override
  public String getPassword() {
    return this.password;
  }
  
  public UserRole getRole(){
    return this.role;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Arrays.asList(new SimpleGrantedAuthority("ROLE_MODERATOR"));
  }

  @Override
  public String toString(){
    return "Story{" +
            "id: " + id +
            " | username: " + username +
            " | password: " + password +
            "}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Moderator moderator = (Moderator) o;

    return id != null ? id.equals(moderator.id) : moderator.id == null;
  }

  @Override
  public int hashCode(){
    return id != null ? id.hashCode() : 0;
  }

}
