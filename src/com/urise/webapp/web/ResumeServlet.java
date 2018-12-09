package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.Storage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResumeServlet extends HttpServlet {

    private Storage storage = Config.get().getStorage();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
//        response.setContentType("text/html; charset=UTF-8");
        response.setHeader("Content-Type", "text/html; charset=UTF-8");
//        String name = request.getParameter("name");
//        response.getWriter().write(name == null ? "HELP BARBOS" : "HELP BARBOS " + name);
        response.getWriter().write(
                "<html>" +
                        "<head>" +
                        "<title>Таблица резюме</title>" +
                        "</head>" +
                        "<header><h2>Таблица резюме</h2></header>" +
                        "<body>" +
                        "<table border=\"1\">" +
                        "<tr>" +
                        "<td>" + "UUID пользователя" + "</td>" +
                        "<td>" + "Имя пользователя" + "</td>" +
                        "</tr>"
        );
        for (Resume resume : storage.getAllSorted()) {
            response.getWriter().write(
                    "<tr>" +
                            "<td>" + resume.getUuid() + "</td>" +
                            "<td>" + resume.getFullName() + "</td>" +
                            "</tr>"
            );
        }
        response.getWriter().write(
                "</table>" +
                        "</body>" +
                        "</html>");
    }
}
