package com.example.aircraftwar2024.DAO;

import java.io.*;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class PlayerDAOImpl implements PlayerDAO {
    private List<Player> players = new LinkedList<>();
    private String filename = "players.txt";

//    //判断当前模式对应的文件
//    public void JudgeMode(){
//        if(AircraftWarGUI.getGame() instanceof EasyGame){
//            filename = "Easy_players.txt";
//        } else if (AircraftWarGUI.getGame() instanceof NormalGame) {
//            filename = "Normal_players.txt";
//        } else if (AircraftWarGUI.getGame() instanceof HardGame) {
//            filename = "Hard_players.txt";
//        }
//    }

    /*将玩家信息添加到文件中*/
    public void doAdd(Player player)throws IOException {
//        JudgeMode();
//        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("players.txt",true));;
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename,true));
        oos.writeObject(player);
        oos.close();
    }

    //历史记录删除并更新文档
    public void doDelete(int i){
//        JudgeMode();
        players.remove(i);
        try{
            FileWriter fw = new FileWriter(filename);
            fw.write("");
            fw.flush();
            fw.close();
            for(Player player:players){
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("players.txt",true));
                oos.writeObject(player);
                oos.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public void findByName(String name)throws Exception{
        players = getAllPlayer();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd HH:mm:ss");
        for(Player p:players){
            if(p.getName().equals(name)){
                System.out.println(p.getName()+","+p.getScore()+","+p.getDate().format(formatter));
            }
        }
    }
    /*从文件中获取全部玩家信息*/
    public List<Player> getAllPlayer()throws Exception{
//        JudgeMode();
        try {
            FileInputStream fs = new FileInputStream(filename);
            ObjectInputStream ois = new ObjectInputStream(fs);
            Player p;
            while (true) {
                try{
                    p = (Player) ois.readObject();
                    fs.skip(4);
                    players.add(p);
                }catch (EOFException e){
                    break;
                }

            }
            ois.close();
            fs.close();
        }catch(EOFException e){
            Collections.sort(players);
        }
        Collections.sort(players);
        return players;
    }

}
