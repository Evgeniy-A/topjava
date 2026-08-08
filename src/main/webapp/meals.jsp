<%@ page contentType="text/html;charset=UTF-8"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="javatime" uri="http://sargue.net/jsptags/time" %>
<html lang="ru">
<head>
    <title>Meal list</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<table border="1">
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>

    <jsp:useBean id="mealsTo" scope="request" type="java.util.List"/>
    <c:forEach items="${mealsTo}" var="mealTo">

    <tr style="color: ${mealTo.excess ? 'red' : 'green'};" >
        <td>
            <javatime:format value="${mealTo.dateTime}" pattern="dd.MM.yyyy HH:mm" />
        </td>
        <td>${mealTo.description}</td>
        <td>${mealTo.calories}</td>
    </tr>
    </c:forEach>
</table>
</body>
</html>