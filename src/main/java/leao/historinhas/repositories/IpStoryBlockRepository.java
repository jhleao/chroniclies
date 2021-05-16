package leao.historinhas.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import leao.historinhas.models.IpStoryBlock;

@Repository
public interface IpStoryBlockRepository extends JpaRepository<IpStoryBlock, Integer> {
  List<IpStoryBlock> findAllByIp(String ip);
}
