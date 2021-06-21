package commands;

import MainPart.WorkingWithMainStack;
import Objects.City;
import Objects.User;

import java.util.NoSuchElementException;
import java.util.Stack;
import java.util.stream.Collectors;

public class RemoveByIdCommand extends Commands {

    long id;

    public RemoveByIdCommand(long id, User user){
        this.id = id;
        this.user = user;
    }
    @Override
    public Reporting execute(WorkingWithMainStack presenter) {
        String result = "";
        try{
            City city1 = presenter.dataBase.cities.stream().filter(city -> city.getId() == id & (city.getAuthor().equals(user.name) || city.getAuthor().trim().equals("-"))).findFirst().get();
            presenter.dataBase.cities = presenter.dataBase.cities.stream().filter(city -> city.getId() != id).collect(Collectors.toCollection(Stack::new));
            presenter.connector.deleteFromDataBase(city1);
            presenter.dataBase.numberOfObjectsInStack--;
            result = "Элемент удален";
        }catch (NoSuchElementException e){
            result = "Элемент с данным id не удалось найти или у вас недостаточный уровень доступа, чтобы его удалить";
        }
        presenter.addToHistory("remove_by_id id");
        return new Reporting(0, result);
    }
}
