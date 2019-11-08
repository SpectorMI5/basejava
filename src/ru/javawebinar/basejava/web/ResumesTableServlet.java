package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.Storage;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ResumesTableServlet extends HttpServlet {
    private Storage storage = Config.get().getStorage();
    private List<Resume> resumes = storage.getAllSorted();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        response.getWriter().write("" +
                "<head>" +
                "   <title>Таблица резюме</title>" +
                "</head>" +
                "<body>" +
                "   <table border=\"1\">" +
                "       <tr>" +
                "           <td>UUID</td>" +
                "           <td>Full Name</td>" +
                "       </tr>"
        );

        for (Resume resume : resumes) {
            response.getWriter().write("" +
                    "<tr>" +
                    "   <td>" + resume.getUuid() + "</td>" +
                    "   <td>" + resume.getFullName() + "</td>" +
                    "</tr>"
            );
        }

        response.getWriter().write("" +
                "   </table>" +
                "</body>"
        );
    }
}