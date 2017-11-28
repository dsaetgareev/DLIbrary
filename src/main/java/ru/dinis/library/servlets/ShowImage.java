package ru.dinis.library.servlets;

import ru.dinis.library.controllers.BooksController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

public class ShowImage extends HttpServlet {

    public void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/pdf");
        OutputStream out = resp.getOutputStream();
        try {
            int id = Integer.valueOf(req.getParameter("genre_id"));
            BooksController booksController = (BooksController) req.getSession(false).getAttribute("booksController");
            byte[] image = booksController.getImage(id);
            resp.setContentLength(image.length);
            out.write(image);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }
}
