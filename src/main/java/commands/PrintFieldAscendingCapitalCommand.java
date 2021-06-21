package commands;

import MainPart.WorkingWithMainStack;
import Objects.City;

import java.util.Iterator;

public class PrintFieldAscendingCapitalCommand extends Commands {

    @Override
    public Reporting execute(WorkingWithMainStack presenter) {
        String result = "";
        if (presenter.dataBase.numberOfObjectsInStack == 0){
            result = "Коллекция пуста";
        }else{
            Iterator<City> iterator = presenter.filterByCapital().iterator();
            while (iterator.hasNext()){
                result = result + iterator.next();
                if (iterator.hasNext()){
                    result = result + "\n";
                }
            }
        }
        presenter.addToHistory("print_field_ascending_capital");
        return new Reporting(0, result);
    }
}
