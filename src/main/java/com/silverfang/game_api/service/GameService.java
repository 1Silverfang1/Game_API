package com.silverfang.game_api.service;

import com.silverfang.game_api.model.Games;
import com.silverfang.game_api.dao.GameServiceInterface;
import com.silverfang.game_api.repository.GamesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class GameService implements GameServiceInterface {
    @Autowired
    private GamesRepository gamesRepository;
    @Override
    public void saveGame(Games games) {
    gamesRepository.save(games);
    }

    @Override
    public List<Games> getAllGame() {
        return gamesRepository.findAll();
    }

    @Override
    public void deleteGame(Games games) {
        gamesRepository.delete(games);
    }

    @Override
    public Games findMyGame(int gameId) {
        return gamesRepository.findById(gameId).get();
    }

    @Override
    public List<Games> searchGame(String gameName) {
        return gamesRepository.findAllByTitleContaining(gameName);
    }
}
