package com.example.aircraftwar2024.DAO;

import java.io.IOException;
import java.util.List;

public interface PlayerDAO {
    public void doAdd(Player player) throws IOException;
    public void doDelete(int i);
    public void findByName(String name)throws Exception;
    public List<Player>getAllPlayer()throws Exception;
}