package commands;

import MainPart.WorkingWithMainStack;
import Objects.City;

import java.util.Iterator;

public class ShowCommand extends Commands {
    @Override
    public Reporting execute(WorkingWithMainStack presenter) {
        String result = "";
        presenter.sort();
        Iterator<City> iterator = presenter.dataBase.cities.iterator();

        if (presenter.dataBase.numberOfObjectsInStack > 0){

            int i = 0;
            while (iterator.hasNext()){
                result = result + iterator.next().toString();
                if (i != presenter.dataBase.numberOfObjectsInStack - 1){
                    result = result + "\n";
                }
                i++;
            }
        }else {
            return new Reporting(0, "Извините, но коллекция пуста");
        }
        presenter.addToHistory("show");
        return new Reporting(0, result);
    }
}
