package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    private Storage storage = Config.get().getStorage();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Resume> resumes = storage.getAllSorted();
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        Writer writer = response.getWriter();

        writer.write("" +
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
            writer.write("" +
                    "<tr>" +
                    "   <td>" + resume.getUuid() + "</td>" +
                    "   <td>" + resume.getFullName() + "</td>" +
                    "</tr>"
            );
        }

        writer.write("" +
                "   </table>" +
                "</body>"
        );
    }
}