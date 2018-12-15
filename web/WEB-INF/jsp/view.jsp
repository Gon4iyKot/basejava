<%@ page import="com.urise.webapp.model.ListSection" %>
<%@ page import="com.urise.webapp.model.OrganizationSection" %>
<%@ page import="com.urise.webapp.model.TextSection" %>
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
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit">Edit</a></h2>
    <h2>Контакты</h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<com.urise.webapp.model.ContactType, java.lang.String>"/>
            <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    </p>
</section>
<section>
    <h2>Секции:</h2>
    <p>
        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<com.urise.webapp.model.SectionType, com.urise.webapp.model.Section>"/>
    <hr size="5" width="100%" color="#3399ff">
    <span style="font-size: x-large; font-weight: bold"><%=sectionEntry.getKey().getTitle()%></span><br/>
    <с:choose>
        <с:when test="<%=sectionEntry.getValue().getClass()==TextSection.class%>">
            <%=((TextSection) sectionEntry.getValue()).getTextInfo()%><br/>
        </с:when>
        <с:when test="<%=sectionEntry.getValue().getClass()==ListSection.class%>">
            <с:forEach var="listItem" items="<%=((ListSection) sectionEntry.getValue()).getItems()%>">
                - ${listItem}<br/>
            </с:forEach>
        </с:when>
        <с:when test="<%=sectionEntry.getValue().getClass()==OrganizationSection.class%>">
            <с:forEach var="listOrganization"
                       items="<%=((OrganizationSection) sectionEntry.getValue()).getOrganizations()%>">
                <a href="${listOrganization.homePage.url}">${listOrganization.homePage.name}</a><br/>
                <с:forEach var="listPosition" items="${listOrganization.positions}">
                    ${listPosition.startDate} ${listPosition.endDate}<br/>
                    <span style="font-size: large; font-weight: bold">${listPosition.title}<br/></span>
                    ${listPosition.description}
                </с:forEach>
            </с:forEach>
        </с:when>
    </с:choose>
    </c:forEach>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
