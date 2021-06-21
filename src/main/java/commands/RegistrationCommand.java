package commands;

import MainPart.WorkingWithMainStack;
import Objects.User;

public class RegistrationCommand extends Commands{
    User user;

    public RegistrationCommand(String login, String password){
        user = new User(login, password);
    }

    @Override
    public Reporting execute(WorkingWithMainStack presenter) {
        if (presenter.dataBase.userList.get(user.name) != null){
            presenter.connector.addUserToDataBase(user);
            return new Reporting(1, "К сожалению, данный логин уже занят");
        }
        presenter.dataBase.userList.put(user.name, user.password);
        presenter.connector.addUserToDataBase(user);
        return new Reporting(0, "Регистрация прошла успешно. Добро пожаловать, " + user.name);
    }
}
