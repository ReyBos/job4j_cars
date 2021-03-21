package ru.reybos.store;

import ru.reybos.model.User;

public interface Store {
    User findUserByLogin(String login);

    User save(User user);

    User delete(User user);
}