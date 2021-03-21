package ru.reybos.store;

import ru.reybos.model.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemStore implements Store {
    private static final Store INST = new MemStore();
    private final Map<Integer, User> users = new ConcurrentHashMap<>();
    private final AtomicInteger userId = new AtomicInteger(1);

    private MemStore() { }

    public static Store instOf() {
        return INST;
    }

    @Override
    public User findUserByLogin(String login) {
        for (User user : users.values()) {
            if (user.getLogin().equals(login)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User save(User user) {
        if (findUserByLogin(user.getLogin()) != null) {
            return user;
        }
        user.setId(userId.getAndIncrement());
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User delete(User user) {
        users.remove(user.getId(), user);
        return user;
    }
}
