package commands;

import MainPart.WorkingWithMainStack;
import Objects.City;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;
import java.util.stream.Collectors;

public class FilterContainsNameCommand extends Commands {

    String line;

    public FilterContainsNameCommand(String line){
        this.line = line;
    }

    @Override
    public Reporting execute(WorkingWithMainStack presenter) {
        String result = "";
        try{
            Stack<City> resultStack = presenter.dataBase.cities.stream().filter(x -> x.getName().contains(line))
                    .collect(Collectors.toCollection(Stack::new));
            Iterator<City> iterator = resultStack.iterator();
            if (!iterator.hasNext()){
                throw new NoSuchElementException();
            }
            while (iterator.hasNext()){
                result = result + iterator.next();
                if (iterator.hasNext()){
                    result = result + "\n";
                }
            }
        }catch (NoSuchElementException e){
            result = "Ничего не удалось найти";
        }
        presenter.addToHistory("filter_contains_name name");
        return new Reporting(0, result);
    }
}
