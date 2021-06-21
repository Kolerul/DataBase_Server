package commands;

import MainPart.WorkingWithMainStack;

public class HelpCommand extends Commands {
    //WorkingWithMainStack presenter;

    @Override
    public Reporting execute(WorkingWithMainStack presenter) {
        //super.presenter = presenter;
        presenter.addToHistory("help");
        return new Reporting(0, presenter.dataBase.cheatSheet);
    }
}
