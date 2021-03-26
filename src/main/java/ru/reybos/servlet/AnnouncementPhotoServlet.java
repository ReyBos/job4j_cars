package ru.reybos.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.reybos.model.car.CarPhoto;
import ru.reybos.store.HbmStore;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@WebServlet("/announcement/photo")
public class AnnouncementPhotoServlet extends HttpServlet {
    private static final Logger LOG =
            LoggerFactory.getLogger(AnnouncementPhotoServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String folderName = "images" + File.separator + "photo";
        Files.createDirectories(Paths.get(folderName));
        String name = req.getParameter("name");
        resp.setContentType("image");
        resp.setHeader("Content-Disposition", "attachment; filename=\"" + name + "\"");
        File file = new File(folderName + File.separator + name);
        try (FileInputStream in = new FileInputStream(file)) {
            resp.getOutputStream().write(in.readAllBytes());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletContext servletContext = this.getServletConfig().getServletContext();
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(repository);
        ServletFileUpload upload = new ServletFileUpload(factory);
        String announcementId = null;
        try {
            String folderName = "images" + File.separator + "photo";
            Files.createDirectories(Paths.get(folderName));
            List<FileItem> items = upload.parseRequest(req);
            File folder = new File(folderName);
            for (FileItem item : items) {
                if (item.isFormField()) {
                    announcementId = item.getString();
                }
            }
            for (FileItem item : items) {
                if (!item.isFormField()) {
                    CarPhoto carPhoto = new CarPhoto();
                    HbmStore.instOf().saveCarPhoto(carPhoto, Integer.parseInt(announcementId));
                    File file = new File(folder + File.separator + carPhoto.getId());
                    try (FileOutputStream out = new FileOutputStream(file)) {
                        out.write(item.getInputStream().readAllBytes());
                    }
                }
            }
        } catch (FileUploadException e) {
            LOG.error("Error", e);
        }
        resp.sendRedirect(req.getContextPath() + "/announcement?page=view&id=" + announcementId);
    }
}
