package com.Monitor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class Alarm extends Thread {
    private static String path = null;

    private static FileInputStream stream = null;

    private static Player player = null;

    private static File file = null;

    static {
        path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        file = new File(path + "13978.mp3");
    }

    public void run() {
        try {
            stream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            player = new Player(stream);
            player.play();
        } catch (JavaLayerException e) {
            e.printStackTrace();
        }
    }
}
