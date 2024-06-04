package com.example.aircraftwar2024.bullet;

import com.example.aircraftwar2024.basic.EnemyObject;

/**
 * @Author hitsz
 */
public class EnemyBullet extends AbstractBullet implements EnemyObject {


    public EnemyBullet(int locationX, int locationY, int speedX, int speedY, int power) {
        super(locationX, locationY, speedX, speedY, power);
    }

    public void bomb() {
        this.vanish();
    }

}
