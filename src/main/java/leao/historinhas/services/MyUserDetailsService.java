package leao.historinhas.services;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import leao.historinhas.models.Moderator;
import leao.historinhas.repositories.ModeratorRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

  private final ModeratorRepository moderatorRepository;

  public MyUserDetailsService(ModeratorRepository moderatorRepository){
    this.moderatorRepository = moderatorRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<Moderator> optMod = moderatorRepository.findByUsername(username);

    if(!optMod.isPresent()) return null;

    Moderator mod = optMod.get();
    return mod;
  }
  
}
