package com.silverfang.game_api.controller;

import com.silverfang.game_api.model.AuthRequest;
import com.silverfang.game_api.model.Games;
import com.silverfang.game_api.dao.GameServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/game")
public class ControllerCrud {
    @Autowired
    private GameServiceInterface gameServiceInterface;
    @DeleteMapping(headers = "Accept=application/json", value = "/{GameId}", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteMyPost(@PathVariable("GameId") int gameId) {
        Games games= gameServiceInterface.findMyGame(gameId);
        if(games!=null)
        {
            gameServiceInterface.deleteGame(games);
            return  new ResponseEntity<>("Game deleted",HttpStatus.OK);
        }
        else
            return new ResponseEntity<>("Game with this id not found",HttpStatus.OK);
    }
    @PutMapping(headers = "Accept=application/json", value = "/{gameId}", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> editMyPost(@PathVariable("gameId") int gameId, @RequestBody AuthRequest authRequest)
            {
                if(authRequest.getEditors_choice()==null||authRequest.getGenre()==null||authRequest.getTitle()==null||authRequest.getPlatform()==null||authRequest.getScore()==null)
                  return new ResponseEntity<>("Enter all the values ",HttpStatus.BAD_REQUEST);
                Games games= gameServiceInterface.findMyGame(gameId);
                if(games!=null)
                {
                    games.setGenre(authRequest.getGenre());
                    games.setEditors_choice(authRequest.getEditors_choice());
                    games.setTitle(authRequest.getTitle());
                    games.setScore(authRequest.getScore());
                    games.setPlatform(authRequest.getPlatform());
                    gameServiceInterface.saveGame(games);
                    return new ResponseEntity<>("Games Edited",HttpStatus.OK);
                }
            else
            return new ResponseEntity<>("Cannot edit this Game", HttpStatus.BAD_REQUEST);
    }
    @GetMapping("")
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

    @PostMapping("")
    public ResponseEntity<?> saveMyGames(@RequestBody AuthRequest authRequest)
    {
        if(authRequest.getPlatform()==null||authRequest.getScore()==null||authRequest.getTitle()==null||authRequest.getGenre()==null||authRequest.getEditors_choice()==null)
        {
            return new ResponseEntity<>("Required values are not Present for game",HttpStatus.BAD_REQUEST);
        }
        Games games= new Games();
        games.setPlatform(authRequest.getPlatform());
        games.setScore(authRequest.getScore());
        games.setTitle(authRequest.getTitle());
        games.setEditors_choice(authRequest.getEditors_choice());
        games.setGenre(authRequest.getGenre());
        gameServiceInterface.saveGame(games);
        return new ResponseEntity<>("Game Saved", HttpStatus.OK);
    }
}
