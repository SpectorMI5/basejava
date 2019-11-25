package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.YearMonth;
import java.util.LinkedList;
import java.util.List;

import static ru.javawebinar.basejava.model.SectionType.*;

public class ResumeServlet extends HttpServlet {
    private Storage storage = Config.get().getStorage();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume r = storage.get(uuid);
        r.setFullName(fullName);
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                r.addContact(type, new Contact(value));
            } else {
                r.getContacts().remove(type);
            }
        }

        for (SectionType type : SectionType.values()) {
            if (type.equals(OBJECTIVE) || type.equals(PERSONAL)) {
                String value = request.getParameter(type.name());
                if (value != null && value.trim().length() != 0) {
                    r.addSection(type, new TextSection(value));
                } else {
                    r.getSections().remove(type);
                }
            } else if (type.equals(ACHIEVEMENT) || type.equals(QUALIFICATIONS)) {
                List<String> stringList = new LinkedList<>();
                int size = ((ListSection) r.getSection(type)).getText().size();
                for (int i = 0; i < size; i++) {
                    String value = request.getParameter(type.name());
                    if (value != null && value.trim().length() != 0) {
                        stringList.add(value);
                    }
                }
                r.addSection(type, new ListSection(stringList));
            } else if (type.equals(EXPERIENCE) || type.equals(EDUCATION)) {
                List<Organization> organizations = new LinkedList<>();
                List<Organization> organizationList = ((OrganizationSection) r.getSection(type)).getOrganizations();
                for (Organization organization : organizationList) {
                    String name = request.getParameter("Название организации");
                    String url = request.getParameter("url организации");
                    Link homePage = new Link(name, url);
                    List<Organization.OrganizationPeriod> periods = new LinkedList<>();
                    int n = organization.getPeriods().size();
                    for (int i = 0; i < n; i++) {
                        Organization.OrganizationPeriod period;
                        YearMonth startDate = YearMonth.parse(request.getParameter("Дата начала"));
                        String title = request.getParameter("Заголовок");
                        String description = request.getParameter("Описание");
                        String end = request.getParameter("Дата окончания");
                        if (end.equals("По настоящее время") || end.equals("")) {
                            period = new Organization.OrganizationPeriod(startDate, title, description);
                        } else {
                            YearMonth endDate = YearMonth.parse(end);
                            period = new Organization.OrganizationPeriod(startDate, endDate, title, description);
                        }
                        periods.add(period);
                    }
                    organizations.add(new Organization(homePage, periods));
                }
                r.addSection(type, new OrganizationSection(organizations));
            }
        }
        storage.update(r);
        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
            case "edit":
                r = storage.get(uuid);
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }
}