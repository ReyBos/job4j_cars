package ru.reybos.servlet.navigation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/announcement")
public class AnnouncementServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(AnnouncementServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        req.getRequestDispatcher("announcement/" + action + ".jsp").forward(req, resp);
    }
}