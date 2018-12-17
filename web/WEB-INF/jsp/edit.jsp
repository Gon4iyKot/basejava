<%@ page import="com.urise.webapp.model.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="с" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}" title="Имя"></dd>
        </dl>
        <h3>Контакты:</h3>
        <p>
            <c:forEach var="type" items="<%=ContactType.values()%>">
        <dl>
            <dt>${type.title}</dt>
            <dd><input type="text" name="${type.name()}" size=50 value="${resume.getContact(type)}"
                       title="${type.title}"></dd>
        </dl>
        </c:forEach>
        <h3>Секции:</h3>
        <%--
                <с:set var="sectionEntry" value="${resume.getSection(type)}"/>
                <jsp:useBean id="sectionEntry" type="java.util.Map.Entry<com.urise.webapp.model.SectionType, com.urise.webapp.model.Section>"/>
        --%>
        <c:forEach var="type" items="<%=SectionType.values()%>">
            <jsp:useBean id="type" type="com.urise.webapp.model.SectionType"/>
            <dl>
                <c:choose>
                    <с:when test="${type=='PERSONAL' || type=='OBJECTIVE'}">
                        <dt>${type.title}</dt>
                        <dd><input type="text" name="${type}" size=50
                                   value="<%=(resume.getSection(type)==null)?"":((TextSection)resume.getSection(type)).getTextInfo()%>"
                                   title="${type.title}"></dd>
                    </с:when>
                    <с:when test="${type=='ACHIEVEMENT' || type=='QUALIFICATIONS'}">
                        <dt>${type.title}</dt>
                        <dd><textarea name="${type}" cols="30"
                                      rows="2"
                                      title="${type.title}"><%=(resume.getSection(type) == null) ? "" : String.join("\n", ((ListSection) resume.getSection(type)).getItems())%></textarea>
                        </dd>
                    </с:when>
                    <с:when test="${type=='EDUCATION' || type=='EXPERIENCE'}">
                        <h3>
                            <dt>${type.title}</dt>
                        </h3>
                        <dt><span style="font-size: x-large; font-weight: bold">Место:</span></dt>
                        <dd><input type="text" name="${type}" size=50
                                   value="" title="${type.title}"></dd>
                        <dt>Ссылка:</dt>
                        <dd><input type="text" name="${type}url" size=50
                                   value="" title="${type.title}"></dd>
                        <dt>Дата начала:</dt>
                        <dd><input type="text" name="${type}startDate0" size=50
                                   value="" title="${type.title}"></dd>
                        <dt>Дата конца:</dt>
                        <dd><input type="text" name="${type}endDate0" size=50
                                   value="" title="${type.title}"></dd>
                        <dt>Роль:</dt>
                        <dd><input type="text" name="${type}title0" size=50
                                   value="" title="${type.title}"></dd>
                        <dt>Описание:</dt>
                        <dd><input type="text" name="${type}description0" size=50
                                   value="" title="${type.title}"></dd>
                        <c:if test="<%=resume.getSection(type)!=null%>">
                            <c:forEach var="organisation"
                                       items="<%=((OrganizationSection)resume.getSection(type)).getOrganizations()%>"
                                       varStatus="count">
                                <dt><span style="font-size: x-large; font-weight: bold">Место:</span></dt>
                                <dd><input type="text" name="${type}" size=50
                                           value="${organisation.homePage.name}" title="${type.title}"></dd>
                                <dt>Ссылка:</dt>
                                <dd><input type="text" name="${type}url" size=50
                                           value="${organisation.homePage.url}" title="${type.title}"></dd>
                                <c:forEach var="position"
                                           items="${organisation.positions}">
                                    <dt>Дата начала:</dt>
                                    <dd><input type="text" name="${type}startDate${count.count}" size=50
                                               value="${position.startDate}" title="${type.title}"></dd>
                                    <dd><input type="text" name="${type}startDate${count.count}" size=50
                                               value="" title="${type.title}"></dd>
                                    <dt>Дата конца:</dt>
                                    <dd><input type="text" name="${type}endDate${count.count}" size=50
                                               value="${position.endDate}" title="${type.title}"></dd>
                                    <dd><input type="text" name="${type}endDate${count.count}" size=50
                                               value="" title="${type.title}"></dd>
                                    <dt>Роль:</dt>
                                    <dd><input type="text" name="${type}title${count.count}" size=50
                                               value="${position.title}" title="${type.title}"></dd>
                                    <dd><input type="text" name="${type}title${count.count}" size=50
                                               value="" title="${type.title}"></dd>
                                    <dt>Описание:</dt>
                                    <dd><input type="text" name="${type}description${count.count}" size=50
                                               value="${position.description}" title="${type.title}"></dd>
                                    <dd><input type="text" name="${type}description${count.count}" size=50
                                               value="" title="${type.title}"></dd>

                                </c:forEach>
                            </c:forEach>
                        </c:if>
                    </с:when>
                </c:choose>
            </dl>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
