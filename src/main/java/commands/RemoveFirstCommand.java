package commands;

import MainPart.WorkingWithMainStack;
import Objects.City;
import Objects.User;

public class RemoveFirstCommand extends Commands {

    public RemoveFirstCommand(User user){
        this.user = user;
    }

    @Override
    public Reporting execute(WorkingWithMainStack presenter) {
        String result = "";
        presenter.addToHistory("remove_first");
        if (presenter.dataBase.numberOfObjectsInStack <= 0){
            result = "Извините, удалять нечего, так как коллекция пуста";
            return new Reporting(0, result);
        }
        presenter.reversedSort();
        City city = presenter.dataBase.cities.pop();
        if (city.getAuthor().equals(user.name) || city.getAuthor().trim().equals("-")){
            presenter.connector.deleteFromDataBase(city);
            presenter.dataBase.numberOfObjectsInStack--;
            result = "Первый элемент удален";
        }else{
            presenter.dataBase.cities.push(city);
            result = "У вас недостаточный уровень доступа, чтобы удалить этот объект";
        }

        return new Reporting(0, result);
    }
}
