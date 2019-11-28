<%@ page import="ru.javawebinar.basejava.model.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" Type="text/css" href="css/style.css">
    <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2><p>
    <c:forEach var="contactEntry" items="${resume.contacts}">
        <jsp:useBean id="contactEntry" type="java.util.Map.Entry<ru.javawebinar.basejava.model.ContactType, ru.javawebinar.basejava.model.Contact>"/>
            <%=contactEntry.getKey().toHtml(contactEntry.getValue().getContact())%><br/>
    </c:forEach><p>

    <c:forEach var="sectionEntry" items="${resume.sections}">
        <jsp:useBean id="sectionEntry"
                     type="java.util.Map.Entry<ru.javawebinar.basejava.model.SectionType, ru.javawebinar.basejava.model.AbstractSection>"/>
            <c:set var="type" value="${sectionEntry.key}"/>
            <c:set var="section" value="${sectionEntry.value}"/>
            <jsp:useBean id="section" type="ru.javawebinar.basejava.model.AbstractSection"/>
                <c:choose>
                    <c:when test="${type=='OBJECTIVE' || type=='PERSONAL'}">
                        <b><dl>${sectionEntry.key.title}</dl></b>
                        <%=((TextSection) section).getText()%><p>
                    </c:when>
                    <c:when test="${type=='ACHIEVEMENT' || type=='QUALIFICATIONS'}">
                        <b><dl>${sectionEntry.key.title}</dl></b>
                        <c:forEach var="string" items="<%=((ListSection) section).getText()%>">
                        <ul>
                            <li>
                                ${string}
                            </li>
                        </ul>
                        </c:forEach><p>
                    </c:when>
                    <c:when test="${type=='EXPERIENCE' || type=='EDUCATION'}">
                        <b><dl>${sectionEntry.key.title}</dl></b>
                        <c:forEach var="organization" items="<%=((OrganizationSection) section).getOrganizations()%>">
                            <a href="${organization.link.url}">${organization.link.name}</a>
                            <c:forEach var="period" items="${organization.periods}">
                                <jsp:useBean id="period" type="ru.javawebinar.basejava.model.Organization.OrganizationPeriod"/>
                                    <c:forEach var="string" items="<%=period.periodToHtml()%>">
                                    ${string}
                                    </c:forEach><p>
                            </c:forEach>
                            <hr>
                        </c:forEach>
                    </c:when>
                </c:choose>
    </c:forEach><p>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>