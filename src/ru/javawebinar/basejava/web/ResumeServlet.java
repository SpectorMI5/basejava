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
            if (r.getSection(type) != null) {
                switch (type) {
                    case OBJECTIVE:
                    case PERSONAL:
                        String value = request.getParameter(type.name());
                        if (value != null && value.trim().length() != 0) {
                            r.addSection(type, new TextSection(value));
                        } else {
                            r.getSections().remove(type);
                        }
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        List<String> stringList = ((ListSection) r.getSection(type)).getText();
                        List<String> strings = new LinkedList<>();
                        for (String s : stringList) {
                            String str = request.getParameter(type.getTitle() + "/" + s);
                            if (str != null && str.trim().length() != 0) {
                                strings.add(str);
                            }
                        }
                        if (!strings.isEmpty()) {
                            r.addSection(type, new ListSection(strings));
                        } else {
                            r.getSections().remove(type);
                        }
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                    /*List<Organization> organizations = new LinkedList<>();
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
                    r.addSection(type, new OrganizationSection(organizations));*/
                        break;
                }
            }
        }
        storage.update(r);
        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        String sectionTypeString = request.getParameter("section type");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r = storage.get(uuid);
        SectionType sectionType;
        switch (action) {
            case "delete resume":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "delete section":
                sectionType = setSectionType(sectionTypeString);
                r.getSections().remove(sectionType);
                storage.update(r);
                response.sendRedirect("resume?uuid=" + uuid + "&action=edit");
                return;
            case "delete string":
                sectionType = setSectionType(sectionTypeString);
                String string = request.getParameter("string");
                ((ListSection) r.getSection(sectionType)).getText().remove(string);
                if (((ListSection) r.getSection(sectionType)).getText().isEmpty()) {
                    r.getSections().remove(sectionType);
                }
                storage.update(r);
                response.sendRedirect("resume?uuid=" + uuid + "&action=edit");
                return;
            case "add section":
                sectionType = setSectionType(sectionTypeString);
                if (!r.getSections().containsKey(sectionType)) {
                    AbstractSection abstractSection = createSection(sectionType);
                    r.addSection(sectionType, abstractSection);
                    storage.update(r);
                }
                response.sendRedirect("resume?uuid=" + uuid + "&action=edit");
                return;
            case "add string":
                sectionType = setSectionType(sectionTypeString);
                String str = "" + (((ListSection) r.getSection(sectionType)).getText().size() + 1);
                ((ListSection) r.getSection(sectionType)).getText().add(str);
                storage.update(r);
                response.sendRedirect("resume?uuid=" + uuid + "&action=edit");
                return;
            case "view":
            case "edit":
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }

    private SectionType setSectionType(String sectionType) {
        switch (sectionType) {
            case "OBJECTIVE":
                return OBJECTIVE;
            case "PERSONAL":
                return PERSONAL;
            case "ACHIEVEMENT":
                return ACHIEVEMENT;
            case "QUALIFICATIONS":
                return QUALIFICATIONS;
            case "EXPERIENCE":
                return EXPERIENCE;
            case "EDUCATION":
                return EDUCATION;
            default:
                throw new IllegalArgumentException("SectionType " + sectionType + " is illegal");
        }
    }

    private AbstractSection createSection(SectionType sectionType) {
        switch (sectionType) {
            case OBJECTIVE:
            case PERSONAL:
                return new TextSection("");
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                return new ListSection("");
            case EXPERIENCE:
            case EDUCATION:
                return new OrganizationSection();
            default:
                throw new IllegalArgumentException("SectionType " + sectionType + " is illegal");
        }
    }
}