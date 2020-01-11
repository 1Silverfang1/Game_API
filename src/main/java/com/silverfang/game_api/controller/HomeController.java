package com.silverfang.game_api.controller;

import com.silverfang.game_api.database.LoadData;
import com.silverfang.game_api.model.AuthRequest;
import com.silverfang.game_api.model.Games;
import com.silverfang.game_api.dao.GameServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
@RestController
public class HomeController {
    @Autowired
    private GameServiceInterface gameServiceInterface;
    @Autowired
    private LoadData loadData;
    @GetMapping("/load")
    public void loadData()
    {
        loadData.loadDataInDatabase();
    }
    @GetMapping("/")
    public ResponseEntity<?> getMyGames()
    {
        List<Games> allGames;
        try{
            allGames= gameServiceInterface.getAllGame();
        }catch (Exception e)
        {
            return new ResponseEntity<>("Error while fetching data from the database", HttpStatus.SERVICE_UNAVAILABLE);
        }
        return new ResponseEntity<>(allGames, HttpStatus.OK);
    }
    @GetMapping("/all-game-name")
    public ResponseEntity<?> getAllGamesName()
    {
        List<Games> allGames;
        try{
            allGames= gameServiceInterface.getAllGame();
        }catch (Exception e)
        {
            return new ResponseEntity<>("Error while fetching data from the database",HttpStatus.SERVICE_UNAVAILABLE);
        }
        ArrayList<String> gameName =new ArrayList<>();
        for(Games games:allGames)
        {

            gameName.add(games.getTitle());
        }
        gameName.remove(0);
        return new ResponseEntity<>(gameName, HttpStatus.OK);
    }
    @GetMapping("/search")
    public ResponseEntity<?> getMySearch(@RequestBody AuthRequest request) {
        if(request.getKey()==null)
        {
            return new ResponseEntity<>("Key is not Present in the Request Body", HttpStatus.BAD_REQUEST);
        }
            String key = request.getKey();
        try {
            List<Games> gamesList = gameServiceInterface.searchGame(key);
            return new ResponseEntity<>(gamesList, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("Error while Retrieving Data from the database",HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}
