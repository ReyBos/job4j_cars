package ru.reybos.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.reybos.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * обрабатывает запросы связанные с пользователем
 */
@WebServlet("/user")
public class UserServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(UserServlet.class.getName());

    /**
     * принимает запрос типа /user?page=some_page и возвращает нужную страницу
     *
     * ?page=registration -> registration.jsp - страница регистрации
     * ?page=login -> login.jsp - страница авторизации
     * ?page=profile -> profile.jsp - профиль пользователя
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String page = req.getParameter("page");
        req.getRequestDispatcher(page + ".jsp").forward(req, resp);
    }

    /**
     * принимает запрос типа /user?action=some_action и производит нужные действия
     *
     * ?action=registration -> регистрация нового пользователя
     * ?action=login -> логиним пользователя по введеным данным
     * ?action=logout -> разлогиниваем пользователя
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        UserService service = UserService.getInstance();
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        if (!service.execute(req)) {
            throw new IllegalStateException("Ошибка при выполнении операции!");
        }
        writer.write("Операция выполнена успешно!");
        writer.flush();
    }
}