<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Edit meal</h2>
<form method="post" action="meals">
    <table>
        <tr>
            <td>Date Time:</td>
            <td><input type="datetime-local" name="dateTime" value="${meal.dateTime}"></td>
        </tr>
        <tr>
            <td>Description:</td>
            <td><input type="text" name="description" value="${meal.description}"></td>
        </tr>
        <tr>
            <td>Calories:</td>
            <td><input type="number" name="calories" value="${meal.calories}"></td>
        </tr>
    </table>
    <input type="hidden" name="id" value="${meal.id}">
    <input type="submit" value="Save">
    <button onclick="window.history.back()" type="button">Cancel</button>
</form>
</body>
</html>
