package commands;

import MainPart.WorkingWithMainStack;
import Objects.City;
import Objects.User;

import java.util.NoSuchElementException;
import java.util.Stack;
import java.util.stream.Collectors;

public class UpdateIdCommand extends Commands {
    City city;

    public UpdateIdCommand(City city, User user){
        this.city = city;
        this.user = user;
    }

    @Override
    public Reporting execute(WorkingWithMainStack presenter) {
        super.presenter = presenter;
        String result = "";
        long id = city.getId();
        try{
            presenter.dataBase.cities.stream()
                    .filter(city -> city.getId() == id & (city.getAuthor().equals(user.name) || city.getAuthor().trim().equals("-"))).findFirst().get();
            presenter.dataBase.cities = presenter.dataBase.cities.stream().filter(city -> city.getId() != id).collect(Collectors.toCollection(Stack::new));
            result = addElement(id, city);
            presenter.connector.updateIntoDataBase(new City(id, city));
        }catch (NoSuchElementException e){
            result = "Элемент с данным id не удалось найти или у вас недостаточный для редактирования уровень доступа";
        }

        presenter.addToHistory("update id { element }");
        return new Reporting(0, result);
    }

    public String addElement(long id, City city){
        presenter.dataBase.cities.push(new City(id, city));
        presenter.addToHistory("add { element }");
        return "Элемент обновлен";
    }

}
