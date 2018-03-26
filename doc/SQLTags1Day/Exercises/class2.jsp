<%@ taglib uri="demoTags.jar" prefix="x" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<html>
<head><title>SQLTags Page</title></head>
<body>   <h2>SQLTags Page</h2>
<x:connection id="con">

  <x:emp id="e" where="order by ENAME">
    <c:out value="${e.ENAME}"/> assigned to
    <c:out value="${e.FK_DEPTNO.DNAME}"/>
    <br>
  </x:emp>
    
</x:connection>
</body>
</html>
