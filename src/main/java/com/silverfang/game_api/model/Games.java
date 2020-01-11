package com.silverfang.game_api.model;

import javax.persistence.*;

@Entity
public class Games {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    int postId;

    public int getPostId() {
        return postId;
    }

    private String title;
    private String platform;
    private String score;
    private String genre;
    private String editors_choice;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Games() {
    }

    public String getEditors_choice() {
        return editors_choice;
    }

    public void setEditors_choice(String editors_choice) {
        this.editors_choice = editors_choice;
    }
}
