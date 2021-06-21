package commands;

import MainPart.WorkingWithMainStack;

public class HistoryCommand extends Commands {

    @Override
    public Reporting execute(WorkingWithMainStack presenter) {
        String result = "";
        for (int i = 0; i < presenter.dataBase.history.length; i++){
            if (presenter.dataBase.history[i] != null) {
                result = result + "[ " + presenter.dataBase.history[i] + " ]";
                if (i != presenter.dataBase.history.length - 1){
                    result = result + "\n";
                }
            }
        }
        presenter.addToHistory("history");
        return new Reporting(0, result);
    }
}
