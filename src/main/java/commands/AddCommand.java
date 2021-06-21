package commands;

import MainPart.WorkingWithMainStack;
import Objects.City;

public class AddCommand extends Commands {
    City city;

    public AddCommand(City city){
        this.city = city;
    }

    @Override
    public Reporting execute(WorkingWithMainStack presenter) {
        City city1 = new City(presenter.setID(), city);
        presenter.dataBase.cities.push(city1);
        presenter.dataBase.numberOfObjectsInStack++;
        presenter.connector.saveToDataBase(city1);
        presenter.addToHistory("add { element }");
        return new Reporting(0, "Элемент добавлен");
    }
}
