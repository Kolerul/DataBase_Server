package commands;

import MainPart.WorkingWithMainStack;
import Objects.City;

import java.util.Stack;

public class GroupCountingByIdCommand extends Commands {

    @Override
    public Reporting execute(WorkingWithMainStack presenter) {
        String result = "";
        int mem1 = 1337, mem2 = 1488, mem3 = 228, mem4 = 282, mem5 = 80, mem6 = 47, mem7 = 69;
        Stack<City> citiesClone = (Stack<City>) presenter.dataBase.cities.clone();
        int evenId = 0, oddId = 0, memId = 0, randomMax = (int) (Math.random() * 10000), smallerMaxId = 0;

        for (int i = 0; i < presenter.dataBase.numberOfObjectsInStack; i++){
            if (citiesClone.peek().getId() % 2 == 0){
                evenId++;
            }else{
                oddId++;
            }
            if (citiesClone.peek().getId() == mem1 || citiesClone.peek().getId() == mem2 || citiesClone.peek().getId() == mem3 ||
                    citiesClone.peek().getId() == mem4 || citiesClone.peek().getId() == mem5 || citiesClone.peek().getId() == mem6 ||
                    citiesClone.peek().getId() == mem7){
                memId++;
            }
            if (citiesClone.pop().getId() <= randomMax){
                smallerMaxId++;
            }
        }
        result = "Четных id в коллекции: " + evenId + "\n" +
                "Нечетных id в коллекции: " + oddId + "\n" +
                "Мемных id в коллекции: " + memId + "\n" +
                "id меньше рандомного числа (" + randomMax + "): " + smallerMaxId;
        presenter.addToHistory("group_counting_by_id");
        return new Reporting(0, result);
    }
}
