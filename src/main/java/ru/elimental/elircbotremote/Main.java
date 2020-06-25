package ru.elimental.elircbotremote;

import java.io.*;
import java.net.Socket;
import java.util.Properties;
import java.util.Scanner;

public class Main {

    private String host;
    private int port;
    private String secret;

    public static void main(String[] args) throws IOException {
        Main main = new Main();
        main.init();
        main.start();
    }

    private void init() throws IOException {
        Properties properties = new Properties();
        String file = "application.properties";
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(file);
        if (inputStream != null) {
            properties.load(inputStream);
        } else {
            throw new FileNotFoundException("application.properties not found in the classpath");
        }
        host = properties.getProperty("host");
        port = Integer.parseInt(properties.getProperty("port"));
        secret = properties.getProperty("secret");
    }

    private void start() throws IOException {
        Socket socket = new Socket(host, port);
        System.out.println(String.format("Connected to %s:%s", host, port));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        Scanner scanner = new Scanner(System.in);
        new ReaderThread(br).start();
        while (true) {
            String input = scanner.nextLine();
            if ("exit".equals(input.trim())) {
                System.out.println("Bye bye");
                bw.close();
                br.close();
                socket.close();
                break;
            } else {
                bw.write(secret + " " + input.trim() + "\n");
                bw.flush();
            }
        }
    }
}
