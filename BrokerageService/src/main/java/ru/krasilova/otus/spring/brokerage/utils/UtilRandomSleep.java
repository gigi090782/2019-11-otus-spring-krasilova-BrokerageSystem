package ru.krasilova.otus.spring.brokerage.utils;

import java.util.Random;

public class UtilRandomSleep {
    public static void getRandomSleep() {
        Random ran = new Random();
        int nxt = ran.nextInt(10);
        if (nxt <= 2)
            try {
                System.out.println("Переход на резервный вызов...." + System.currentTimeMillis());
                Thread.sleep(3000);
            } catch (InterruptedException e) {

                e.printStackTrace();
            }
    }
}
