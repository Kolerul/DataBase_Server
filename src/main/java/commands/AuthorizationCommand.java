package commands;

import MainPart.WorkingWithMainStack;
import Objects.User;

public class AuthorizationCommand extends Commands {
    User user;

    public AuthorizationCommand(String login, String password){
        user = new User(login, password);
    }

    @Override
    public Reporting execute(WorkingWithMainStack presenter) {
        if(presenter.dataBase.userList.containsKey(user.name)){
            String localPassword = presenter.dataBase.userList.get(user.name);
            if (localPassword.equals(user.password)){
                return new Reporting(0, "Пользователь успешно авторизирован. Добро пожаловать, " + user.name);
            }
        }

        return new Reporting(1, "Авторизация не удалась");
    }
}
