<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>   <!-- JSP에서 제공하는 jstl 기능 - 코드를 더 간단히 짤 수 있음. --!>
<html>
<head>
 <meta charset="UTF-8">
 <title>Title</title>
</head>
<body>
<a href="/index.html">메인</a>
<table>
   <thead>
     <th>id</th>
     <th>username</th>
     <th>age</th>
   </thead>
 <tbody>
    <c:forEach var="item" items="${allMembers}">
      <tr>
        <td>${item.id}</td>
        <td>${item.username}</td>
        <td>${item.age}</td>
     </tr>
   </c:forEach>
 </tbody>
</table>
</body>
</html>