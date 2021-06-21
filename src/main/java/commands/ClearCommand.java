package commands;

import MainPart.WorkingWithMainStack;
import Objects.City;
import Objects.User;

import java.util.Stack;

public class ClearCommand extends Commands {
    public ClearCommand(User user){
        this.user = user;
    }

    @Override
    public Reporting execute(WorkingWithMainStack presenter) {
        String result = "";
        City city;
        int counter = 0;
        Stack<City> cityStack = new Stack<>();
        while (!presenter.dataBase.cities.empty()){
            city = presenter.dataBase.cities.pop();
            if (city.getAuthor().equals(user.name) || city.getAuthor().trim().equals("-")){
                counter++;
            }else{
                cityStack.push(city);
            }
        }
        presenter.dataBase.cities = cityStack;
        presenter.dataBase.numberOfObjectsInStack = presenter.dataBase.numberOfObjectsInStack - counter;
        result = "Коллекция очищена.";
        presenter.addToHistory("clear");
        return new Reporting(0, result);
    }
}
