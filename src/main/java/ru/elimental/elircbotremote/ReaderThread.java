package ru.elimental.elircbotremote;

import java.io.BufferedReader;
import java.io.IOException;

public class ReaderThread extends Thread {

    private final BufferedReader br;

    public ReaderThread(BufferedReader br) {
        this.br = br;
        this.setDaemon(true);
    }

    @Override
    public void run() {
        try {
            while (true) {
                String input = br.readLine();
                System.out.println(input);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
