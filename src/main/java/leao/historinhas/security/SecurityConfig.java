package leao.historinhas.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import leao.historinhas.services.MyUserDetailsService;

@EnableWebSecurity
// @Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{

  private UserDetailsService userDetailsService;

  @Autowired
  public SecurityConfig(MyUserDetailsService myUserDetailsService){
    this.userDetailsService = myUserDetailsService;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception{
    auth.userDetailsService(userDetailsService);
  }

  @Bean
  public PasswordEncoder getPasswordEncoder(){
    return new BCryptPasswordEncoder(5);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
      .antMatchers("/admin").hasAnyRole("MODERATOR", "ADMIN")
      .antMatchers("/", "static/css").permitAll()
      .and().formLogin()
      .loginPage("/admin/login")
      .defaultSuccessUrl("/admin", true)
      .permitAll()
      .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/admin/logout"))
      .logoutSuccessUrl("/admin/login");
  }

}
