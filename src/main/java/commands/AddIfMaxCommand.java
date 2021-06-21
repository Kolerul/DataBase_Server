package commands;

import MainPart.WorkingWithMainStack;
import Objects.City;

public class AddIfMaxCommand extends Commands {

    City city;

    public AddIfMaxCommand(City city){
        this.city = city;
    }

    @Override
    public Reporting execute(WorkingWithMainStack presenter) {
        String result = "";
        City applicant = new City(presenter.setID(), city);
        if (presenter.dataBase.numberOfObjectsInStack >= 1){
            City max = presenter.searchMax();
            int checkCounter = 0;
            if (applicant.getArea() >= max.getArea()){
                checkCounter++;
            }
            if (applicant.getPopulation() >= max.getPopulation()){
                checkCounter++;
            }
            if ((applicant.getPopulation() / applicant.getArea() >= max.getPopulation() / max.getArea())){
                checkCounter++;
            }
            if (checkCounter >=2 ){
                presenter.dataBase.cities.push(applicant);
                presenter.dataBase.numberOfObjectsInStack++;
                result = "Элемент добавлен";
            }else{
                result = "Элемент не оказался больше наибольшего. Элемент не был добавлен";
                presenter.dataBase.id--;
            }
        }else{
            presenter.dataBase.cities.push(applicant);
            presenter.dataBase.numberOfObjectsInStack++;
            presenter.connector.saveToDataBase(applicant);
            result = "Элемент добавлен";
        }
        presenter.addToHistory("add_if_max { element }");
        return new Reporting(0, result);
    }
}
