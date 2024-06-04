package com.example.aircraftwar2024.supply;


import com.example.aircraftwar2024.aircraft.HeroAircraft;
import com.example.aircraftwar2024.basic.EnemyObject;

import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.List;

/**
 * 炸弹道具，自动触发
 * <p>
 * 使用效果：清除界面上除BOSS机外的所有敌机（包括子弹）
 * <p>
 * 【观察者模式】
 *
 * @author hitsz
 */
public class BombSupply extends AbstractFlyingSupply {


    public BombSupply(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    private static ArrayList<EnemyObject> enemyList = new ArrayList<EnemyObject>();;

    public static void addEnemy(EnemyObject enemyObject) {
        enemyList.add(enemyObject);
    }

    public static void addEnemy(List<EnemyObject> enemyObjects) {
        for (EnemyObject enemyObject: enemyObjects) {
            addEnemy(enemyObject);
        }
    }

    public static void removeEnemy(EnemyObject enemyObject) {
        enemyList.remove(enemyObject);
    }

    public void bombAllEnemy() {
        for(EnemyObject enemyObject: enemyList) {
            enemyObject.bomb();
        }
    }

    @Override
    public void activate() {
        System.out.println("BombSupply active!");
        bombAllEnemy();
        vanish();
    }

}
