package MainPart;


import Objects.City;
import commands.Commands;
import commands.Reporting;

import java.io.*;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;


public class Server {
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private Socket clientSocket;
    private ServerSocket serverSocket;
    private int port;
    private Commands input;
    private WorkingWithMainStack presenter;
    private Reporting report;
    static Logger LOGGER;
    private City city;
    private int limit = 10000;


    public Server() throws IOException {
        port = 4446;
        LogManager.getLogManager().readConfiguration();
        LOGGER = Logger.getLogger(Server.class.getName());
        try {
            serverSocket = new ServerSocket(port);
        }catch (BindException e){
            LOGGER.warning("Порт уже занят, поменяй");
            System.exit(0);
        }

        presenter = new WorkingWithMainStack();

        Thread scannerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                //System.err.println("Я жив!");
                LOGGER.log(Level.INFO, "Создается второй поток");
                Scanner scanner = new Scanner(System.in);
                do {
                    String line = scanner.nextLine().trim();
                    if (StringManipulation.commandWithArgumentsCheck(line, "save")) {
                        File file;
                        try {
                            file = new File(StringManipulation.returnLastWords(line));
                        }catch (ArrayIndexOutOfBoundsException e){
                            file = presenter.dataBase.startingFile;
                        }
                        try {
                            try {
                                presenter.save(file);
                                //System.err.println("Данные сохранены");
                            }catch (NullPointerException e){
                                presenter.save(presenter.dataBase.defaultFile);
                            }
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                    if(line.equals("exit")){
                        break;
                    }
                }while (true) ;
                //System.err.println("Я умер!");
                LOGGER.log(Level.INFO, "Второй поток завершает работу, а вместе с ним и вся программа");
                System.exit(0);
            }
        });

        scannerThread.start();

    }

    public void doServerBuisness() throws IOException, ClassNotFoundException {
        //System.out.println("Сервер запущен!");
        LOGGER.info("Запуск основного метода сервера");
        try {
            clientSocket = serverSocket.accept();
            ExecutorService executorService = Executors.newFixedThreadPool(3);
            //System.out.println("Клиент подключился");
            LOGGER.info("Клиент подключился");
            executorService.submit(new ClientHandler(clientSocket/*, presenter*/));

        } catch (SocketException | EOFException e){
            //System.out.println(input.toString());
            try{
                File file = new File(presenter.dataBase.startingFile.getAbsolutePath());
                presenter.save(file);
            }catch (NullPointerException ex){
                File file = presenter.dataBase.defaultFile;
                presenter.save(file);
            }

            LOGGER.info("Соединение разорвано. Данные сохранены в файл");
            presenter.clearStack();
            presenter = new WorkingWithMainStack();
            return;
        }

    }
}

