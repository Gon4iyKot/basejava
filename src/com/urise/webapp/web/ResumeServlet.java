package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.*;
import com.urise.webapp.storage.Storage;
import com.urise.webapp.util.Resurrection;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    private Storage storage;
    private Resurrection resurrection;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
        resurrection = new Resurrection();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullNameForCreation = request.getParameter("fullNameForCreation");
        if (fullNameForCreation != null && fullNameForCreation.trim().length() != 0) {
            storage.save(new Resume(fullNameForCreation));
            response.sendRedirect("resume");
        }
        String fullName = request.getParameter("fullName");
        Resume resume = storage.get(uuid);
        resume.setFullName(fullName);
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                resume.addContact(type, value);
            } else {
                resume.getContacts().remove(type);
            }
        }
        for (SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name());
            String[] values = request.getParameterValues(type.name());
            if (values[values.length - 1] != null && values[values.length - 1].trim().length() != 0) {
                switch (type) {
                    case PERSONAL:
                    case OBJECTIVE:
                        resume.addSection(type, new TextSection(value));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        resume.addSection(type, new ListSection(value.split("\n")));
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        List<Organization> organizations = new ArrayList<>();
                        String[] name = request.getParameterValues(type.name());
                        String[] url = request.getParameterValues(type.name() + "url");
                        for (int i = value.equals("") ? 1 : 0; i < values.length; i++) {
                            List<Organization.Position> positions = new ArrayList<>();
                            String[] startDate = request.getParameterValues(type.name() + "startDate" + i);
                            String[] endDate = request.getParameterValues(type.name() + "endDate" + i);
                            String[] title = request.getParameterValues(type.name() + "title" + i);
                            String[] description = request.getParameterValues(type.name() + "description" + i);
                            for (int a = 0; a < startDate.length; a++) {
                                if (startDate[a].equals("")) {
                                    continue;
                                }
                                positions.add(new Organization.Position(LocalDate.parse(startDate[a]), LocalDate.parse(endDate[a]), title[a], description[a]));
                            }
                            organizations.add(new Organization(new Link(name[i], url[i]), positions));
                        }
                        resume.addSection(type, new OrganizationSection(organizations));
                        break;
                }
            } else {
                resume.getSections().remove(type);
            }
        }
        storage.update(resume);
        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume resume;
        switch (action) {
            case "create":
                storage.save(new Resume(fullName));
                response.sendRedirect("resume");
                return;
            case "resurrect":
                resurrection.resurrect(Config.get().getStorage());
                response.sendRedirect("resume");
                return;
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
            case "edit":
                resume = storage.get(uuid);
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegel");
        }
        request.setAttribute("resume", resume);
        request.getRequestDispatcher("view".equals(action) ? "WEB-INF/jsp/view.jsp" : "WEB-INF/jsp/edit.jsp").forward(request, response);
    }
}
