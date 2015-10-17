<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Customer Form</title>

    <style type="text/css">
        .tg  {border-collapse:collapse;border-spacing:0;border-color:#ccc;}
        .tg td{font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#fff;}
        .tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#f0f0f0;}
        .tg .tg-4eph{background-color:#f9f9f9}
        .tb {border-collapse:collapse;border-spacing:10;border-color:#ccc;font-family:Arial, sans-serif;font-size:14px;}
        .error{color:red}
    </style>

</head>
<body>

<h2>Customer Information</h2>
<form:form method="POST" modelAttribute="customer" action="/profit-webapp/addCustomer">
   <table class="tb">
    <tr>
        <td><form:label path="firstname">Firstname</form:label></td>
        <td><form:input path="firstname" /></td>
        <td><form:errors path="firstname" cssClass="error"/></td>
    </tr>
    <tr>
        <td><form:label path="lastname">Lastname</form:label></td>
        <td><form:input path="lastname" /></td>
        <td><form:errors path="lastname" cssClass="error"/></td>
    </tr>
    <tr>
        <td><form:label path="dateOfBirth">Date of birth</form:label></td>
        <td><form:input path="dateOfBirth" placeholder="yyyy-MM-dd"/></td>
        <td><form:errors path="dateOfBirth" cssClass="error"/></td>
    </tr>
    <tr>
        <td><form:label path="username">Username</form:label></td>
        <td><form:input path="username" /></td>
        <td><form:errors path="username" cssClass="error"/></td>
    </tr>
      <tr>
        <td><form:label path="password">Password</form:label></td>
        <td><form:input path="password" type="password" /></td>
        <td><form:errors path="password" cssClass="error"/></td>
    </tr>
    <tr>
        <td colspan="2">
            <input type="submit" value="Create Customer"/>
        </td>
    </tr>
</table>  
</form:form>

<c:if test="${not empty list}">
	<table class="tg">
		<tr>
			<th>Firstname</th>
			<th>Lastname</th>
			<th>Date of birth</th>
			<th>Username</th>
			<th>Update</th>
			<th>Delete</th>
		</tr>
		
		<c:forEach var="customer" items="${list}">
			<tr>
				<td>${customer.firstname}</td>
				<td>${customer.lastname}</td>
				<td>${customer.dateOfBirth}</td>
				<td>${customer.username}</td>
				<td><a href="<c:url value='/edit/${customer.id}' />" >Update</a></td>
				<td><a href="<c:url value='/remove/${customer.id}' />" >Delete</a></td>
			</tr>
		</c:forEach>
		
	</table>
	</c:if>
</body>
</html>