package commands;


import MainPart.WorkingWithMainStack;
import Objects.User;

import java.io.Serializable;

public abstract class Commands implements Serializable {
    protected WorkingWithMainStack presenter;
    protected User user;

    public abstract Reporting execute(WorkingWithMainStack presenter);
}
