package leao.historinhas.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import leao.historinhas.models.Moderator;

@Repository
public interface ModeratorRepository extends CrudRepository<Moderator, Integer> {
  public Optional<Moderator> findByUsername(String username);
}
