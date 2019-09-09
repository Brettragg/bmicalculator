<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head><title>BMI Calculator</title></head>
    <body>
        <%--suppress HtmlUnknownTarget --%>
        <form action = "BMICalc" method = "POST">
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
        <c:if test="${!(empty requestScope.JSON_DATA)}">
            <c:if test="${!(empty requestScope.JSON_DATA.BMI)}">
                Your Body Mass Index is
                <fmt:parseNumber var = "parsedBmi" value = "${requestScope.JSON_DATA.BMI}" type = "number"/>
                <fmt:formatNumber type = "number" maxFractionDigits="1" value = "${parsedBmi}" />
            </c:if>
            <br/>
            <c:if test="${!(empty requestScope.JSON_DATA.BMI_CATEGORY)}">
                Your BMI category is: ${requestScope.JSON_DATA.BMI_CATEGORY}
            </c:if>
            <br/>
            <c:if test="${!(empty requestScope.JSON_DATA.SAVED_DATA)}">
                <br/><br/> Previously saved data: <br/>
                <table>
                    <tr>
                        <th>Height</th>
                        <th>Weight</th>
                        <th>BMI</th>
                        <th>Category</th>
                    </tr>

                    <c:forEach items = "${requestScope.JSON_DATA.SAVED_DATA}" var = "row">
                        <tr>
                            <td>
                                <fmt:parseNumber var = "parsedTHeight" value = "${row.HEIGHT}" type = "number"/>
                                <fmt:formatNumber type = "number" maxFractionDigits="1" value = "${parsedTHeight}" />
                            </td>
                            <td><fmt:parseNumber var = "parsedTWeight" value = "${row.WEIGHT}" type = "number"/>
                                <fmt:formatNumber type = "number" maxFractionDigits="1" value = "${parsedTWeight}" />
                            </td>
                            <td><fmt:parseNumber var = "parsedTBmi" value = "${row.BMI}" type = "number"/>
                                <fmt:formatNumber type = "number" maxFractionDigits="1" value = "${parsedTBmi}" />
                            </td>
                            <td>${row.BMI_CATEGORY}</td>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>
        </c:if>
    </body>
</html>
