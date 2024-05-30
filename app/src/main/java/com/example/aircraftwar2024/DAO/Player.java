package com.example.aircraftwar2024.DAO;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Player implements Serializable ,Comparable<Player>{
    private String name;
    private int score;
    private LocalDateTime date;

//    private static final long serialVersionUID = 7677585238188092433L;

    public Player(String name,int score,LocalDateTime date){
        this.name = name;
        this.score = score;
        this.date = date;
    }

    public Player(){

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd HH:mm:ss");
        return date.format(formatter);
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    /*用于列表排序*/
    public int compareTo(Player player){
        return Integer.compare(player.getScore(),this.getScore());
    }
}

