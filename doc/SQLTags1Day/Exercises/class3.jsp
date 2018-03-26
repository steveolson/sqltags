<%@ taglib uri="demoTags.jar" prefix="sqltags" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<html>
<head><title>JSP Page</title></head>
<body>
<sqltags:connection id="connect" >

   <sqltags:dept id="d" where="order by DNAME">
      <c:out value="${d.DNAME}" />
      <BLOCKQUOTE>
      <sqltags:emp id="e" foreignKey="FK_DEPTNO" parentName="d">
        <c:out value="${e.ENAME}"/>
        <br>
      </sqltags:emp>
      </BLOCKQUOTE>
   </sqltags:dept>

</sqltags:connection>
</body>
</html>

