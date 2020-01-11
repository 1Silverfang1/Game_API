package com.silverfang.game_api.database;

import com.silverfang.game_api.model.Games;
import com.silverfang.game_api.repository.GamesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Component
public class LoadData {
    @Autowired
    private GamesRepository gamesRepository;
    public void loadDataInDatabase() {
        String csvFile = "gamesc2b2088.csv";
        String line = "";
        String cvsSplitBy = ",";

        try (
                BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {
                String[] firstRow = line.split(cvsSplitBy);

                Games games= new Games();
                int rowLength=firstRow.length;
                if(rowLength==5) {
                    games.setEditors_choice(firstRow[4]);
                    games.setGenre(firstRow[3]);
                    games.setPlatform(firstRow[1]);
                    games.setScore(firstRow[2]);
                    games.setTitle(firstRow[0]);
                    gamesRepository.save(games);
                }
                else
                {
                    games.setPlatform(firstRow[1]);
                    games.setScore(firstRow[2]);
                    games.setTitle(firstRow[0]);
                    games.setEditors_choice(firstRow[rowLength-1]);
                    String gen= "";
                    for(int i=3;i<rowLength-1;i++)
                    {
                        gen+=firstRow[i];
                    }
                    games.setGenre(gen);
                    gamesRepository.save(games);
                }
                }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}