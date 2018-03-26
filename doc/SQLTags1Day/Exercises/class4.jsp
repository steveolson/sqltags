<%@page contentType="text/html"%>
<%@taglib prefix="x" uri="demoTags.jar" %>
<html>
<head><title>JSP Page</title></head>
<body>
<x:connection id="con">
   <x:emp id="e" properties="true" buttonName="f" >
    <x:exception name="e"/> <%-- report errors --%>
    <form method="post" action="class4.jsp">
      <input name='EMPNO' value="<%= e.getEMPNO() %>" > EMPNO <br>
      <input name='ENAME' value="<%= e.getENAME() %>" > ENAME <br>
      <input name='JOB'   value="<%= e.getJOB() %>"   > JOB <br>
      <input type=submit name="f" value="Insert">
      <input type=submit name="f" value="Update">
      <input type=submit name="f" value="Delete">
    </form>
   </x:emp>
    <hr>
    <x:emp id="e2" where='order by ENAME'>
        <A HREF='./class4.jsp?EMPNO=<%=e2.getEMPNO()%>&f=select'><%= e2.getENAME() %></A>
        <br>
    </x:emp>
</x:connection>
</body>
</html>

