package leao.historinhas.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import leao.historinhas.models.Story;
import leao.historinhas.models.StoryStatus;

@Repository
public interface StoryRepository extends JpaRepository<Story, Integer>{
  public List<Story> findAllByStatus(StoryStatus s);
}
