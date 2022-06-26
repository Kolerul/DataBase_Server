package MainPart;

import Objects.City;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Model {

    public Stack<City> cities = new Stack<>();

    public Map<String, String> userList = new HashMap<>();
    public int numberOfObjectsInStack;
    /**
     * Поле, хранящее путь до файла, от куда изначально берутся данные для коллекции
     */
    public File startingFile;
    public File defaultFile = new File("csv.txt");
    /**
     * Поле счетчик ID
     */
    public long id;
    /**
     * Поле дата создания коллекции
     */
    public Date dateOfCreation;
    /**
     * Массив, хранящий последние 11 введенных команд
     */
    public String[] history = new String[11];
    /**
     * Поле счетчик свободного места в массиве истории
     */
    int historySpaceCounter = 0;
    /**
     * Поле шпаргалка, которая содержит список комманд и их описание
     */
    public final String cheatSheet = "1. help - справка по доступным командам \n" +
            "2. info - информация о коллекции\n" +
            "3. show - вывести на экран все элементы коллекции\n" +
            "4. add { element } - добавить элемент\n" +
            "5. update id { element } - обновить значение элемента коллекции\n" +
            "6. remove_by_id id - удалить элемент из коллекции по его id\n" +
            "7. clear - очистить коллекцию\n" +
            "8. execute_script file_name - считать и исполнить скрипт из указанного файла\n" +
            "9. exit - завершение программы (без сохранения в файл)\n" +
            "10. remove_first - удалить первый элемент из коллекции\n" +
            "11. add_if_max { element } - добавляет элемент в коллекцию, если по площади, населению и плотности он превосходит остальные\n" +
            "12. history - последние введеные 11 команд (без аргументов)\n" +
            "13. group_counting_by_id - сгруппировать элементы коллекции по id и вывести количество элементов в каждой группе\n" +
            "14. filter_contains_name name - выводит все элементы, значение поля name которых содержит данную подстроку\n" +
            "15. print_field_ascending_capital - выводит значение поля capital у элементов стека в порядке возрастания";

    public String dataBaseUser = "postgres";

    public String dataBasePassword = "JustPassword";

    public String dataBaseUrl = "jdbc:postgresql://localhost:4445/postgres";
}