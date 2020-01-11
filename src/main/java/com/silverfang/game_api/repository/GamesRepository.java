package com.silverfang.game_api.repository;

import com.silverfang.game_api.model.Games;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GamesRepository extends JpaRepository<Games,Integer> {
    List<Games> findAllByTitleContaining(String key);

}