package ru.reybos.service;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.reybos.model.User;
import ru.reybos.store.HbmStore;
import ru.reybos.store.Store;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class UserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserService.class.getName());
    private final Store store = HbmStore.instOf();
    private final Map<String, Function<HttpServletRequest, Boolean>> dispatch = new HashMap<>();

    private UserService() {
        this.load("registration", registration());
        this.load("login", login());
        this.load("logout", logout());
    }

    public static UserService getInstance() {
        return Holder.INSTANCE;
    }

    private static final class Holder {
        private static final UserService INSTANCE = new UserService();
    }

    public void load(String action, Function<HttpServletRequest, Boolean> handle) {
        this.dispatch.put(action, handle);
    }

    private Function<HttpServletRequest, Boolean> registration() {
        return request -> {
            boolean rsl = true;
            try {
                User userFromForm = new Gson().fromJson(request.getReader(), User.class);
                store.save(userFromForm);
                if (userFromForm.getId() == 0) {
                    throw new IllegalStateException("Ошибка сохранения пользователя");
                }
                userFromForm.clearPassword();
                HttpSession sc = request.getSession();
                sc.setAttribute("user", userFromForm);
            } catch (Exception e) {
                LOG.error("Ошибка регистрации", e);
                rsl = false;
            }
            return rsl;
        };
    }

    private Function<HttpServletRequest, Boolean> login() {
        return request -> {
            boolean rsl = true;
            try {
                User userFromForm = new Gson().fromJson(request.getReader(), User.class);
                User userFromDb = store.findUserByLogin(userFromForm.getLogin());
                if (userFromDb == null
                        || !userFromDb.getPassword().equals(userFromForm.getPassword())) {
                    throw new IllegalArgumentException("Пользователь не найден");
                }
                HttpSession sc = request.getSession();
                sc.setAttribute("user", userFromDb);
            } catch (Exception e) {
                LOG.error("Ошибка авторизации", e);
                rsl = false;
            }
            return rsl;
        };
    }

    private Function<HttpServletRequest, Boolean> logout() {
        return request -> {
            HttpSession sc = request.getSession();
            sc.removeAttribute("user");
            return true;
        };
    }

    public boolean execute(HttpServletRequest request) {
        String action = request.getParameter("action");
        if (!dispatch.containsKey(action)) {
            throw new IllegalArgumentException("Действие не найдено");
        }
        return dispatch.get(action).apply(request);
    }
}
