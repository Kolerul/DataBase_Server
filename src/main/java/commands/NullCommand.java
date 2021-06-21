package commands;

import MainPart.WorkingWithMainStack;

public class NullCommand extends Commands {

    @Override
    public Reporting execute(WorkingWithMainStack presenter) {
        return null;
    }
}
