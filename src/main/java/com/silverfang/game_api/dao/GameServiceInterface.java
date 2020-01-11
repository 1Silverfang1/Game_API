package com.silverfang.game_api.dao;

import com.silverfang.game_api.model.Games;

import java.util.List;

public interface GameServiceInterface {
    void saveGame(Games games);
    List<Games> getAllGame();
    void deleteGame(Games games);
    Games findMyGame(int gameId);
    List<Games> searchGame(String gameName);
}
