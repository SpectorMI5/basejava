<%@ page import="ru.javawebinar.basejava.model.ContactType" %>
<%@ page import="ru.javawebinar.basejava.model.ListSection" %>
<%@ page import="ru.javawebinar.basejava.model.OrganizationSection" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <b><dt>Имя:</dt></b>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <h3>Секции:</h3>
        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry"
                        type="java.util.Map.Entry<ru.javawebinar.basejava.model.SectionType, ru.javawebinar.basejava.model.AbstractSection>"/>
            <c:set var="type" value="${sectionEntry.key}"/>
            <c:set var="section" value="${sectionEntry.value}"/>
            <jsp:useBean id="section" type="ru.javawebinar.basejava.model.AbstractSection"/>
            <c:choose>
            <c:when test="${type=='OBJECTIVE' || type=='PERSONAL'}">
                <dl>
                    <b><dt>${type.title}</dt></b>
                    <dd><input type="text" name="${type.name()}" size=113 value="${resume.getSection(type)}"></dd>
                </dl>
            </c:when>

            <c:when test="${type=='ACHIEVEMENT' || type=='QUALIFICATIONS'}">
                <b>
                    <dl>${sectionEntry.key.title}</dl>
                </b>
                <c:forEach var="string" items="<%=((ListSection) section).getTextWithoutSlash()%>">
                    <dl>
                        <dd style="margin-left: 0px"><input type="text" name="${type.name()}" size=142 value="${string}"></dd>
                    </dl>
                </c:forEach>
                <p>
            </c:when>

            <c:when test="${type=='EXPERIENCE' || type=='EDUCATION'}">
                <b>
                    <dl>${sectionEntry.key.title}</dl>
                </b>
                <c:forEach var="organization" items="<%=((OrganizationSection) section).getOrganizations()%>">
                    <dl>
                        <dt style="margin-bottom: 8px">Название организации</dt>
                        <dd><input type="text" name="Название организации" size=113 value="${organization.link.name}"></dd>
                        <dt>url организации</dt>
                        <dd><input type="text" name="url организации" size=113 value="${organization.link.url}"></dd>
                    </dl>
                    <c:forEach var="period" items="${organization.periods}">
                        <dl>
                            <c:forEach var="periodEntry" items="${period.periodToMap()}">
                                <jsp:useBean id="periodEntry"
                                             type="java.util.Map.Entry<java.lang.String, java.lang.String>"/>
                                <c:set var="fieldName" value="${periodEntry.key}"/>
                                <c:set var="value" value="${periodEntry.value}"/>
                                <dt style="margin-bottom: 8px">${fieldName}</dt>
                                <c:choose>
                                    <c:when test="${fieldName.equals('Дата начала')}">
                                        <dd><input type="text" name="Дата начала" size=113 value="${value}"></dd>
                                    </c:when>
                                    <c:when test="${fieldName.equals('Дата окончания')}">
                                        <dd><input type="text" name="Дата окончания" size=113 value="${value}"></dd>
                                    </c:when>
                                    <c:when test="${fieldName.equals('Заголовок')}">
                                        <dd><input type="text" name="Заголовок" size=113 value="${value}"></dd>
                                    </c:when>
                                    <c:when test="${fieldName.equals('Описание')}">
                                        <dd><input type="text" name="Описание" size=113 value="${value}"></dd>
                                    </c:when>
                                </c:choose>
                            </c:forEach>
                        </dl>
                    </c:forEach>
                    <hr>
                </c:forEach>
            </c:when>
            </c:choose>
        </c:forEach>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>