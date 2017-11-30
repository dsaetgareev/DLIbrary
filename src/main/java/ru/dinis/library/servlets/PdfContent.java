package ru.dinis.library.servlets;


import ru.dinis.library.controllers.BooksController;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;


public class PdfContent extends HttpServlet {

    public void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("application/pdf");
        OutputStream out = response.getOutputStream();
        try {
           int id = Integer.valueOf(request.getParameter("content_id"));
            Boolean save = Boolean.valueOf(request.getParameter("save"));
            String filename = request.getParameter("filename");
            String name =  URLEncoder.encode(filename, "utf-8") + ".pdf";
            BooksController booksController =
                    (BooksController) request.getSession(false).getAttribute("booksController");
            byte[] content = booksController.getContent(id);
            response.setContentLength(content.length);
            if (save) {
                response.setHeader("Content-Disposition",
                        "attachment;filename=" + name);
            }
            out.write(content);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.processRequest(req, resp);
    }
}
