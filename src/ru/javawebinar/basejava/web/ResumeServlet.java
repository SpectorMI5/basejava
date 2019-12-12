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
                        List<String> strings = new LinkedList<>();
                        for (String s : request.getParameter(type.name()).split("\r\n")) {
                            if (s != null && s.trim().length() != 0) {
                                strings.add(s);
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
                        List<Organization> organizations = new LinkedList<>();
                        String[] organizationNames = request.getParameterValues(type.name());
                        for (String organizationName : organizationNames) {
                            String newName = request.getParameter("/" + organizationName);
                            String url = request.getParameter(organizationName + ".url");
                            Link homePage = new Link(newName, url);
                            List<Organization.OrganizationPeriod> periods = new LinkedList<>();
                            String[] periodsTitle = request.getParameterValues("Заголовок-" + organizationName);
                            String[] periodsDescription = request.getParameterValues("Описание-" + organizationName);
                            String[] periodsStartDate = request.getParameterValues("Дата начала-" + organizationName);
                            String[] periodsEndDate = request.getParameterValues("Дата окончания-" + organizationName);
                            for (int i = 0; i < periodsTitle.length; i++) {
                                Organization.OrganizationPeriod period;
                                if (periodsEndDate[i].equals("По настоящее время") || periodsEndDate[i].equals("")) {
                                    period = new Organization.OrganizationPeriod(YearMonth.parse(periodsStartDate[i]),
                                            periodsTitle[i], periodsDescription[i]);
                                } else {
                                    period = new Organization.OrganizationPeriod(YearMonth.parse(periodsStartDate[i]),
                                            YearMonth.parse(periodsEndDate[i]), periodsTitle[i], periodsDescription[i]);
                                }
                                periods.add(period);
                            }
                            organizations.add(new Organization(homePage, periods));
                        }
                        r.addSection(type, new OrganizationSection(organizations));
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
        Resume r = new Resume();
        if (!uuid.equals("new uuid")) {
            r = storage.get(uuid);
        }
        SectionType sectionType;
        String organizationName;
        List<Organization> organizations;
        int index;
        switch (action) {
            case "add organization":
                sectionType = SectionType.valueOf(sectionTypeString);
                ((OrganizationSection) r.getSection(sectionType)).getOrganizations().add(new Organization("", "",
                        new Organization.OrganizationPeriod(YearMonth.now(), YearMonth.now(), "", "")));
                storage.update(r);
                response.sendRedirect("resume?uuid=" + uuid + "&action=edit");
                return;
            case "add period":
                organizationName = request.getParameter("organization name");
                sectionType = SectionType.valueOf(sectionTypeString);
                createPeriod(((OrganizationSection) r.getSection(sectionType)).getOrganizations(), organizationName);
                storage.update(r);
                response.sendRedirect("resume?uuid=" + uuid + "&action=edit");
                return;
            case "add resume":
                r = new Resume("");
                storage.save(r);
                response.sendRedirect("resume?uuid=" + r.getUuid() + "&action=edit");
                return;
            case "add section":
                sectionType = SectionType.valueOf(sectionTypeString);
                if (!r.getSections().containsKey(sectionType)) {
                    AbstractSection abstractSection = createSection(sectionType);
                    r.addSection(sectionType, abstractSection);
                    storage.update(r);
                }
                response.sendRedirect("resume?uuid=" + uuid + "&action=edit");
                return;
            case "delete organization":
                organizationName = request.getParameter("organization name");
                sectionType = SectionType.valueOf(sectionTypeString);
                organizations = ((OrganizationSection) r.getSection(sectionType)).getOrganizations();
                index = getIndexOfOrganization(organizations, organizationName);
                if (index >= 0) {
                    organizations.remove(index);
                }
                if (organizations.isEmpty()) {
                    r.getSections().remove(sectionType);
                }
                storage.update(r);
                response.sendRedirect("resume?uuid=" + uuid + "&action=edit");
                return;
            case "delete period":
                organizationName = request.getParameter("organization name");
                String periodTitle = request.getParameter("period title");
                sectionType = SectionType.valueOf(sectionTypeString);
                organizations = ((OrganizationSection) r.getSection(sectionType)).getOrganizations();
                deletePeriod(request, organizations, organizationName, periodTitle);
                storage.update(r);
                response.sendRedirect("resume?uuid=" + uuid + "&action=edit");
                return;
            case "delete resume":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "delete section":
                sectionType = SectionType.valueOf(sectionTypeString);
                r.getSections().remove(sectionType);
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

    private void createPeriod(List<Organization> organizations, String organizationName) {
        for (Organization organization : organizations) {
            if (organization.getLink().getName().equals(organizationName)) {
                organization.getPeriods().add(new Organization.OrganizationPeriod(YearMonth.now(), YearMonth.now(), "", ""));
            }
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
                return new OrganizationSection(new Organization("", "", new Organization.OrganizationPeriod(YearMonth.now(),
                        YearMonth.now(), "", "")));
            default:
                throw new IllegalArgumentException("SectionType " + sectionType + " is illegal");
        }
    }

    private void deletePeriod(HttpServletRequest request, List<Organization> organizations, String organizationName, String periodTitle) {
        for (Organization organization : organizations) {
            if (organization.getLink().getName().equals(organizationName)) {
                organization.getPeriods().removeIf(period -> period.getTitle().equals(periodTitle));
                break;
            }
        }
    }

    private int getIndexOfOrganization(List<Organization> organizations, String organizationName) {
        for (int i = 0; i < organizations.size(); i++) {
            if (organizations.get(i).getLink().getName().equals(organizationName)) {
                return i;
            }
        }
        return -1;
    }
}