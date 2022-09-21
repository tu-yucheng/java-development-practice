package cn.tuyucheng.taketoday.entitygraph.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import cn.tuyucheng.taketoday.entitygraph.model.Characteristic;

public interface CharacteristicsRepository extends JpaRepository<Characteristic, Long> {
    
    @EntityGraph(attributePaths = {"item"})
    Characteristic findByType(String type);
}