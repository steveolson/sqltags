<%-- 
 $Id: emp.jsp,v 1.7 2002/03/15 14:59:10 solson Exp $
 $Log: emp.jsp,v $
 Revision 1.7  2002/03/15 14:59:10  solson
 added License, ID, and Log

 Revision 1.6  2002/03/10 02:02:17  solson
 added btn=select to link to emp_edit as part of removal of emp_edit_save.

 Revision 1.5  2002/03/10 01:16:23  solson
 fixed ID header

 Revision 1.4  2002/03/10 01:06:42  solson
 fixed wrong Foreign key name.

 Revision 1.3  2002/03/10 00:45:25  solson
 added comments and CVS variables

 * ====================================================================
 *
 * Applied Information Technologies, Inc.
 * Steve A. Olson
 *
 * Copyright (c) 2002 Applied Information Technologies, Inc.  
 * Copyright (c) 2002 Steve A. Olson
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:
 *       "This product includes software developed by 
 *    Applied Information Technologies, Inc. (http://www.ait-inc.com/)."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "Applied Information Technologies, Inc.", "AIT", "AITWorks", 
 *    "SQLTags", and "<SQLTags:>" must not be used to endorse or promote 
 *    products derived from this software without prior written permission. 
 *    For written permission, please contact support@ait-inc.com.
 *
 * 5. Products derived from this software may not be called "SQLTags" or
 *    "<SQLTags:>" nor may "SQLTags" or "<SQLTags:>" appear in their 
 *    names without prior written permission of the Applied Information 
 *    Technologies, Inc..
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL APPLIED INFORMATION TECHNOLOGIES, 
 * INC. OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of Applied Information Technologies, Inc.  For more
 * information on Applied Information Technologies, Inc., please see
 * <http://www.ait-inc.com/>.
 *
 */
  --%>
<%@ page import="com.aitworks.sqltags.jsptags.*,
                 com.aitworks.demo.sqltags.*" %>
<%@ taglib uri="/WEB-INF/lib/demoTags.jar" prefix="sqltags" %>

<% 
   String[] row_colors = new String[2]; 
   row_colors[0]="B0E0E6";
   row_colors[1]="FFFFFF";

   int i=0;
%>

<html>
<head><title>Employees</title></head>
<body>
<%-- Include Standard Header .. BTW, include cannot be within a custom tag.
  --%>
<jsp:include page="header.jsp" flush="true" />

<%-- OK, open a connection to the database; using the "properties" file
     found in /WEB-INF named oracle.properties, the ConnectionTag can be
     referenced within the page using "connect" scriptlet variable and
     the Initialization properties from oracle.properties are available
     from via the name "init".
  --%>
<sqltags:connection id="connect" name="init" initSrc="/WEB-INF/oracle.properties">

<%-- Ok, create a "dept" tag, and load column-values from the "request object"
     because of the "properties=true" attribute.  The "operation=select" will
     cause the tag to issue a select statement based on the PRIMARY KEY as
     defined in the database for this table; 

     So the real effect of combining properties=true and operation=select is 
     that you expect to obtain the primary key values from the request object 
     and you query for the rest of the columns from the database. 
  --%>
<sqltags:dept
 id="dept"
 properties="true"
 operation="select"
>
<table bgcolor="FFFFFF" width="700" border="0">
<tr align="center">
<td>
Departments
</td>
</tr>
<tr align="center">
<td>
    <table bgcolor="black" width="600" border=0>
    <tr>
    <td align="center">
        <table border="0" width="100%" bgcolor="white">
        <%-- header for table --%>
        <tr bgcolor="4169E1">
        <td>
        Department Name
        </td>
        <td>
        Location
        </td>
        </tr>
        <%-- end header for table --%>

        <%-- list dept into --%>

        <tr bgcolor="FFFFFF">
        <td>
        <%-- eventhough DNAME was not pulled from Request Object it is available
             because of the operation=select; and that the PRIMARY KEY, DEPTNO,
             was passed in from the request object.
          --%>
        <%=dept.getDNAME()%>
        </td>
        <td>
        <%=dept.getLOC()%>
        </td>
        </tr>
        </table>
    </td>
    </tr>
    </table>
</td>
</tr>

<tr align="center">
<td>
Employees
</td>
</tr>
<tr align="center">
<td>
    <%-- list all employees --%>
    <table bgcolor="black" width="600" border=0>
    <tr>
    <td align="center">
        <table border="0" width="100%" bgcolor="white">
        <%-- header for table --%>
        <tr bgcolor="4169E1">
        <td>
        Employee Name
        </td>
        <td>
        Job Title
        </td>
        <td>
        Manager
        </td>
        <td>
        Salary
        </td>
        <td>
        Commission
        </td>
        <td>
        Hire Date
        </td>
        <td>
        <br>
        </td>
        </tr>
        <%-- end header for table --%>

        <%-- list dept into --%>
        <%-- Ok, now we create a "emp" tag to display the employees that have
             been assigned to the department in the above "dept" tag.  This
             is an example of a MASTER/DETAIL relationship.  Each dept and
             many employees assigned to it; so we nest a emp tag within the
             dept tag and identify the foreign key that defines the relation-
             ship between the tables.

             The critical attributes for nesting based on FOREIGN KEY are:
                parentName -- identifies the parent tag's "id"
                foreignKey -- identifies the foreign key relationship between
                              the two tables

             Also, in the example below, we demonstrate how to format the date
             string for display.  Typically, there'd be a corresponding _BIND
             attribute; however, because this is only querying the database
             and not inserting or updating the _BIND attribute is extraenous.
          --%>
        <sqltags:emp
         id="emp"
         HIREDATE_SELECT="to_char(hiredate, 'mm/dd/yyyy')"
         foreignKey="FK_DEPTNO"
         parentName="dept"
        >
        <tr bgcolor="<%=row_colors[(++i%2)]%>">
        <td>
        <%-- display data from the table --%>
        <%=emp.getENAME()%>
        </td>
        <td>
        <%=emp.getJOB()%>
        </td>
        <td>
        <%-- OK, here is an example of pulling in a column from another table,
             again, based on a FOREIGN KEY.  However, this time the foreign
             key is to a parent record-- there is only one parent value, so
             we can simply reference the values directly from the other table.  

             In this example, we are referencing the FOREIGN KEY named, FK_MGR,
             which is a relationship back to the EMP pointing to the current 
             emp's manager.  
          --%>
        <%=emp.getFK_MGR_PARENT().getENAME()%>
        </td>
        <td>
        <%-- simple getter --%>
        <%=emp.getSAL()%>
        </td>
        <td>
        <%-- simple getter --%>
        <%=emp.getCOMM()%>
        </td>
        <td>
        <%-- simple getter, 
             note, value will be formatted based on _SELECT attribute --%>
        <%=emp.getHIREDATE()%>
        </td>
        <td>
        <a href="emp_edit.jsp?empno=<%=emp.getEMPNO()%>&btn=select">Edit</a>
        </td>        
        </tr>
        <%-- close up the nested emp tag --%>
        </sqltags:emp>
        </table>
    </td>
    </tr>
    </table>
</td>
</tr>
</table>

<%-- close up the dept tag --%>
</sqltags:dept>

<%-- give the connection back to the pool --%>
</sqltags:connection>
</body>
</html>
