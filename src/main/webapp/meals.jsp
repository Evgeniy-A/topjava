<%@ page contentType="text/html;charset=UTF-8"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <title>Meal list</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<a href="<c:url value='/meals'>
         <c:param name='action' value='add' />
         </c:url>">Add Meal</a>
<table border="1">
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>

    <jsp:useBean id="mealsTo" scope="request" type="java.util.List"/>
    <jsp:useBean id="dateTimeFormatter" scope="request" type="java.time.format.DateTimeFormatter"/>
    <c:forEach items="${mealsTo}" var="mealTo">

    <tr style="color: ${mealTo.excess ? 'red' : 'green'};" >
        <td>
                ${dateTimeFormatter.format(mealTo.dateTime)}
        </td>
        <td>${mealTo.description}</td>
        <td>${mealTo.calories}</td>
        <td>
            <a href="<c:url value='/meals'>
            <c:param name='action' value='update' />
            <c:param name='id' value='${mealTo.id}' />
            </c:url>">Update</a>
        </td>
        <td>
            <a href="<c:url value='/meals'>
            <c:param name='action' value='delete' />
            <c:param name='id' value='${mealTo.id}' />
            </c:url>">Delete</a>
        </td>
    </tr>

    </c:forEach>

</table>
</body>
</html>