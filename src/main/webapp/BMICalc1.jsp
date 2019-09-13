<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head><title>BMI Calculator</title></head>
    <body>
        <%--suppress HtmlUnknownTarget --%>
        <form action = "BMICalc1" method = "POST">
            <label>
                Height in centimeters:
                <input type="number" placeholder="" step="0.01" min="0.01" max="500" name="height" required/>
            </label>
            <br/>
            <label>
                Weight in kilos:
                <input type="number" placeholder="" step="0.01" min="0" max="1000" name="weight" required/>
            </label>
            <br/>
            <input type = "submit" value = "Submit" />
        </form>

        <c:if test="${!(empty requestScope.BMI)}">
            Your Body Mass Index is
            <fmt:formatNumber type = "number" maxFractionDigits="1" value = "${requestScope.BMI}" />
            <br/>
        </c:if>
        <c:if test="${!(empty requestScope.BMI_CATEGORY)}">
            Your BMI category is: ${requestScope.BMI_CATEGORY}
            <br/>
        </c:if>
        <c:if test="${!(empty requestScope.SAVED_DATA)}">
        <br/><br/> Previously saved data: <br/>
            <table>
                <tr>
                    <th>Height</th>
                    <th>Weight</th>
                    <th>BMI</th>
                    <th>Category</th>
                </tr>

                <c:forEach items = "${requestScope.SAVED_DATA}" var = "row">
                    <tr>
                        <td><fmt:formatNumber type = "number" maxFractionDigits="1" value = "${row.height}" /></td>
                        <td><fmt:formatNumber type = "number" maxFractionDigits="1" value = "${row.weight}" /></td>
                        <td><fmt:formatNumber type = "number" maxFractionDigits="1" value = "${row.bmi}" /></td>
                        <td>${row.category}</td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
    </body>
    <br/>
    <a href="index.jsp">Go back</a>
</html>
