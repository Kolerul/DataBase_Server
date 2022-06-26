package MainPart;

import commands.Commands;
import commands.Reporting;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Logger;

public class ClientHandler implements Runnable {
    WorkingWithMainStack presenter;
    ObjectInputStream inputStream;
    ObjectOutputStream outputStream;
    static Logger LOGGER;
    private Commands input;
    private Reporting report;
    Socket clientSocket;

    public ClientHandler(Socket clientSocket/*, WorkingWithMainStack presenter*/){
        //this.presenter = presenter;
        this.clientSocket = clientSocket;
        presenter = new WorkingWithMainStack();
    }

    @Override
    public void run() {
        presenter.createCollection();
        //LOGGER.info("База данных подключена");
        System.out.println(2);
        try {

            //LOGGER.info("Создан канал выхода");



            do {
                inputStream = new ObjectInputStream(new BufferedInputStream(clientSocket.getInputStream()));
                System.out.println("Создан канал выхода");

                outputStream = new ObjectOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
                //LOGGER.info("Создан канал входа");
                System.out.println("Создан канал входа");


                input = (Commands) inputStream.readObject();

                report = input.execute(presenter);
                outputStream = new ObjectOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
                //LOGGER.info("Создан канал выхода");
                byte[] bytes = serialize(report);
                Integer length = bytes.length;
                //LOGGER.info("Количество байт: " + length);
                String sstrl = length.toString();
                outputStream.writeObject(sstrl);
                outputStream.flush();
                inputStream = new ObjectInputStream(new BufferedInputStream(clientSocket.getInputStream()));
                input = (Commands) inputStream.readObject();
                //LOGGER.info("подготовка к отправке ответа прошла успешно");
                outputStream = new ObjectOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
                outputStream.writeObject(report);
                outputStream.flush();
                //LOGGER.info("Отправлен ответ клиенту");
            } while (true);
        } catch ( SocketException | EOFException e ) {
            System.out.println("Клиент отключился");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            //LOGGER.info("Клиент отключен от сервера.");
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public byte[] serialize(Object obj) {
        //System.out.println("Внутри сереализатора");
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(obj);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}