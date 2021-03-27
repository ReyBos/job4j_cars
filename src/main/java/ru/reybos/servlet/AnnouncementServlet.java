package ru.reybos.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.reybos.model.User;
import ru.reybos.model.announcement.Announcement;
import ru.reybos.service.AnnouncementService;
import ru.reybos.store.HbmStore;
import ru.reybos.store.Store;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

/**
 * обрабатывает запросы связанные с одним объявлением
 */
@WebServlet("/announcement")
public class AnnouncementServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(AnnouncementServlet.class.getName());

    /**
     * принимает запрос типа /announcement?page=some_page и возвращает нужную страницу
     *
     * ?page=add -> announcement/add.jsp - страница добавления нового объявления
     * ?page=view&id=id -> announcement/view.jsp?id=id - страница с подробной информацией
     * об объявлении, где id - айди объявления в базе данных
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        StringBuilder url = new StringBuilder("announcement/");
        String page = req.getParameter("page");
        url.append(page);
        url.append(".jsp");
        String id = req.getParameter("id");
        if (id != null) {
            Store store = HbmStore.instOf();
            Announcement announcement = store.findAnnouncementById(Integer.parseInt(id));
            req.setAttribute("announcement", announcement);
            HttpSession session = req.getSession();
            User currentUser = (User) session.getAttribute("user");
            req.setAttribute("user", currentUser);
        }
        req.getRequestDispatcher(url.toString()).forward(req, resp);
    }

    /**
     * принимает запрос типа /announcement?action=some_action и json представление объяекта
     * announcement и производит нужные действия
     *
     * ?action=save -> сохранение нового объявления
     * ?action=update -> обновить объявление
     * ?action=get-form-fields -> получить данные для полей формы добавления нового объявления
     * ?action=get-user-announcement&id=id -> получть объявления пользователя
     * ?action=get-all-announcement -> получть все объявления
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        AnnouncementService service = AnnouncementService.getInstance();
        req.setCharacterEncoding("UTF-8");
        Optional<String> rsl = service.execute(req);
        if (rsl.isEmpty()) {
            throw new IllegalStateException("Ошибка при выполнении операции!");
        }
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.write(rsl.get());
        writer.flush();
    }
}
