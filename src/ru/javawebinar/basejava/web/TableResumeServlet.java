package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.SqlHelper;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;

public class TableResumeServlet extends HttpServlet {
    private final SqlHelper sqlHelper = new SqlHelper(() -> DriverManager.getConnection("jdbc:postgresql://localhost:5432/resumes",
            "postgres", "postgres"));

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
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

        Map<String, Resume> resumes = sqlHelper.execute("SELECT * FROM resume ORDER BY full_name,uuid", st -> {
            ResultSet rs = st.executeQuery();
            Map<String, Resume> map = new LinkedHashMap<>();
            while (rs.next()) {
                String uuid = rs.getString("uuid");
                map.put(uuid, new Resume(uuid, rs.getString("full_name")));
            }
            return map;
        });

        for (Map.Entry<String, Resume> e : resumes.entrySet()) {
            response.getWriter().write("" +
                    "<tr>" +
                    "   <td>" + e.getKey() + "</td>" +
                    "   <td>" + e.getValue().getFullName() + "</td>" +
                    "</tr>"
            );
        }

        response.getWriter().write("" +
                "   </table>" +
                "</body>"
        );
    }
}