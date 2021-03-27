package ru.reybos.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {
    private static final Logger LOG = LoggerFactory.getLogger(AuthFilter.class.getName());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * если авторизованный пользователь хочет зайти на страницу регистрации и авторизации
     * его перенаправляет на главную страницу
     */
    @Override
    public void doFilter(ServletRequest sreq, ServletResponse sresp, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) sreq;
        HttpServletResponse resp = (HttpServletResponse) sresp;
        String uri = req.getRequestURI();
        String query = req.getQueryString() == null ? "" : req.getQueryString();
        if (
                (query.endsWith("login") || query.endsWith("registration"))
                && req.getSession().getAttribute("user") != null
        ) {
            resp.sendRedirect(req.getContextPath() + "/");
            return;
        }
        if (
                query.endsWith("login") || query.endsWith("registration")
                || uri.endsWith("/photo") || query.endsWith("get-all-announcement")
                || uri.endsWith("/") || uri.endsWith(".css") || uri.endsWith(".js")
                || uri.endsWith(".jpeg")
        ) {
            chain.doFilter(sreq, sresp);
            return;
        }
        if (req.getSession().getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/user?page=login");
            return;
        }
        chain.doFilter(sreq, sresp);
    }

    @Override
    public void destroy() {

    }
}
