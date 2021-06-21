package commands;

import MainPart.WorkingWithMainStack;

public class InfoCommand extends Commands {

    @Override
    public Reporting execute(WorkingWithMainStack presenter) {
        //super.presenter = presenter;
        String result = "Тип коллекции: " + presenter.dataBase.cities.getClass() + "\n" + "Время создания коллекции: " +
                presenter.dataBase.dateOfCreation + "\n" + "Количество элементов коллекции: " +
                presenter.dataBase.numberOfObjectsInStack;
        presenter.addToHistory("info");
        return new Reporting(0, result);
    }
}
