<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="с" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Title</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <a href="resume?action=resurrect">Восстановить тестовые записи</a>

    <table border="1" cellpadding="8" cellspacing="0">
        <tr>
            <th>Имя</th>
            <th>E-mail</th>
            <th>Удаление</th>
            <th>Редактирование</th>
        </tr>
        <с:forEach items="${resumes}" var="resume">
            <jsp:useBean id="resume" type="com.urise.webapp.model.Resume"/>
            <tr>
                <td><a href="resume?uuid=${resume.uuid}&action=view">${resume.fullName}</a></td>
                <td><%=ContactType.EMAIL.toHtml(resume.getContact(ContactType.EMAIL))%>
                </td>
                <td><a href="resume?uuid=${resume.uuid}&action=delete"><img src="img/delete.png"></a></td>
                <td><a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></td>
            </tr>
        </с:forEach>
    </table>
</section>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <dl>
            <dt>Для быстрого создания резюме ведите полное имя и нажмите "Создать"</dt>
            <dd><input type="text" name="fullNameForCreation" size=50 value="" title="Полное имя"></dd>
        </dl>
        <button type="submit">создать</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>