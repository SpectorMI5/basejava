<%@ page import="ru.javawebinar.basejava.model.ContactType" %>
<%@ page import="ru.javawebinar.basejava.model.ListSection" %>
<%@ page import="ru.javawebinar.basejava.model.OrganizationSection" %>
<%@ page import="ru.javawebinar.basejava.model.SectionType" %>
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

        <br><h3 style="margin-top: 0px">Секции:</h3>
        <table border="1" align="center" cellpadding="5" cellspacing="5">
            <tr>
                <c:forEach var="type" items="<%=SectionType.values()%>">
                    <td>
                        ${type.title}
                        <a href="resume?uuid=${resume.uuid}&section type=${type}&action=add section"><img src="img/add.png"></a>
                        <a href="resume?uuid=${resume.uuid}&section type=${type}&action=delete section"><img src="img/delete.png"></a>
                    </td>
                </c:forEach>
            </tr>
        </table>
        <hr>

        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry"
                        type="java.util.Map.Entry<ru.javawebinar.basejava.model.SectionType, ru.javawebinar.basejava.model.AbstractSection>"/>
            <c:set var="type" value="${sectionEntry.key}"/>
            <c:set var="section" value="${sectionEntry.value}"/>
            <jsp:useBean id="section" type="ru.javawebinar.basejava.model.AbstractSection"/>
            <dl>
                <b>${type.title}</b>
                <c:choose>
                    <c:when test="${type=='EXPERIENCE' || type=='EDUCATION'}">
                        <a href="resume?uuid=${resume.uuid}&section type=${type}&action=add organization"><img src="img/add.png"></a>
                    </c:when>
                </c:choose>
            </dl>

            <c:choose>
            <c:when test="${type=='OBJECTIVE' || type=='PERSONAL'}">
                <dl>
                    <dd style="margin-left: 0px"><input type="text" name="${type.name()}" size=142 value="${resume.getSection(type)}"></dd>
                </dl>
            </c:when>

            <c:when test="${type=='ACHIEVEMENT' || type=='QUALIFICATIONS'}">
                <c:set var="string" value='<%=String.join("\n", ((ListSection) section).getText())%>'/>
                <dl>
                    <textarea typeof="text" name="${type}" cols="128">${string}</textarea>
                </dl>
            </c:when>

            <c:when test="${type=='EXPERIENCE' || type=='EDUCATION'}">
                <c:forEach var="organization" items="<%=((OrganizationSection) section).getOrganizations()%>">
                    <c:set var="name" value="${organization.link.name}"/>
                    <dl>
                        <dt style="margin-bottom: 8px">
                            Название организации<a href="resume?uuid=${resume.uuid}&section type=${type}&organization name=${name}&action=delete organization"><img src="img/delete.png"></a>
                        </dt>
                        <input type="hidden" name="${type.name()}" value="${name}">
                        <dd><input type="text" name="/${name}" size=113 value="${name}"></dd>
                        <dt>url организации</dt>
                        <dd><input type="text" name="${name}.url" size=113 value="${organization.link.url}"></dd>
                    </dl>
                    <c:forEach var="period" items="${organization.periods}">
                        <dl>
                            <c:forEach var="periodEntry" items="${period.periodToMap()}">
                                <jsp:useBean id="periodEntry"
                                             type="java.util.Map.Entry<java.lang.String, java.lang.String>"/>
                                <c:set var="fieldName" value="${periodEntry.key}"/>
                                <c:set var="value" value="${periodEntry.value}"/>
                                <dt style="margin-bottom: 8px">
                                    ${fieldName}
                                    <c:choose>
                                        <c:when test="${fieldName.equals('Дата начала')}">
                                            <a href="resume?uuid=${resume.uuid}&section type=${type}&organization name=${name}&period title=${period.title.replace('+', '%2B')}&action=delete period"><img src="img/delete.png"></a>
                                        </c:when>
                                    </c:choose>
                                </dt>
                                <c:choose>
                                    <c:when test="${fieldName.equals('Дата начала')}">
                                        <dd><input type="text" name="${fieldName}-${name}" size=113 value="${value}"></dd>
                                    </c:when>
                                    <c:when test="${fieldName.equals('Дата окончания')}">
                                        <dd><input type="text" name="${fieldName}-${name}" size=113 value="${value}"></dd>
                                    </c:when>
                                    <c:when test="${fieldName.equals('Заголовок')}">
                                        <dd><input type="text" name="${fieldName}-${name}" size=113 value="${value}"></dd>
                                    </c:when>
                                    <c:when test="${fieldName.equals('Описание')}">
                                        <dd><input type="text" name="${fieldName}-${name}" size=113 value="${value}"></dd>
                                    </c:when>
                                </c:choose>
                            </c:forEach>
                        </dl>
                    </c:forEach>
                    <a href="resume?uuid=${resume.uuid}&section type=${type}&organization name=${name}&action=add period"><img src="img/add.png"></a>
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