<%@ taglib uri="demoTags.jar" prefix="x" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<html>
<head><title>SQLTags Page</title></head>
<body>   <h2>SQLTags Page</h2>
<x:connection id="con">

    <x:emp id="e" where="order by ENAME">
      <%= e.getENAME() %>
      <c:out value="${e.ENAME}"/>
      <%-- ${e.ENAME} // JSP 2.0 syntax (no tags required!) --%>
      <br>
    </x:emp>
    
</x:connection>
</body>
</html>
