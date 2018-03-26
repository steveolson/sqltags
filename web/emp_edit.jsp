<%-- 
 $Id: emp_edit.jsp,v 1.5 2002/03/15 14:59:10 solson Exp $
 $Log: emp_edit.jsp,v $
 Revision 1.5  2002/03/15 14:59:10  solson
 added License, ID, and Log

 Revision 1.4  2002/03/10 02:00:56  solson
 removed emp_edit_save page and processed insert/update/delete within
 this page directly.  makes for better exception handling.

 Revision 1.3  2002/03/10 01:17:08  solson
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

<%-- Ok, create an "emp" tag, and load column-values from the "request object"
     because of the "properties=true" attribute. For instance, if the request
     object contains ENAME or ename the column ENAME will be set; if the request
     object contains JOB or job then the JOB column will be set, and so forth.

     Now, the values (from the request object) will be processed based on the 
     value of the "btn" parameter because of the buttonName=btn attribute in the
     tag.  Thus, the tag will automatically perform either an: INSERT, UPDATE, 
     DELETE, or  SELECT depending on the value of  "btn". NOTE: within the 
     <FORM> below there are four submit-type inputs named btn with the 
     values: Insert, Update, Delete, and Select; so when the user clicks on 
     one of those buttons the corresponding operation will be carried out 
     on the EMP table.

     Also, in this example, we are formatting the HIREDATE to mm/dd/yyyy
  --%>
<sqltags:emp
 id="emp"
 HIREDATE_SELECT="to_char(hiredate, 'mm/dd/yyyy')"
 HIREDATE_BIND="to_date(?,'mm/dd/yyyy')"
 properties="true"
 buttonName="btn"
>

<%-- exception are displayed by the exception tag. --%>
<sqltags:exception name="emp"/>

<%-- build a form that submits to itself  --%>
<form name="form" method="post" action="emp_edit.jsp">
<table bgcolor="FFFFFF" width="700" border="0">
<tr align="center">
<td>
Employee
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
        <tr bgcolor="<%=row_colors[(++i)%2]%>">
        <td>
        Employee Name
        </td>
        <td>
        <%-- It is important to note that the NAME of this "INPUT" element
             matches the column name from the EMP table.  This will make
             the programming in the submitted-to page much easier
          --%>
        <input type="text" name="ename" value="<%=emp.getENAME()%>">
        </td>
        </tr>

        <tr bgcolor="<%=row_colors[(++i)%2]%>">
        <td>
        Job Title
        </td>
        <td>
        <%-- again, using COLUMN NAMES from the table for INPUT elements
          --%>
        <input type="text" name="job" value="<%=emp.getJOB()%>">
        </td>
        </tr>

        <tr bgcolor="<%=row_colors[(++i)%2]%>">
        <td>
        Manager
        </td>
        <td>
        <%-- OK, create an EMP tag used to list the possible "MANAGERS" for
             the current employee.  Will limit drop-down to emp's in the
             same department.

             NOTE: the where clause "deptno = :deptno"; this is an example of
             using BIND variables.  The major limitation on BIND variables is
             that they can only be columns from the table; and you must set
             their values prior to using them.  So, the example below
             we are assigning the DEPTNO to emp.getDEPTNO() (That's the 
             deptno from the outer emp tag) and then referencing it in the
             WHERE clause as specified above.  The where clause is evaluated
             when the query is executed at doStart so the value of DEPTNO is
             availabe then.

             Also, within the drop-down, we have added some logic to set the
             "SELECTED" attribute for the current Manager.
          --%>
        <select name="mgr">
        <sqltags:emp 
         id="manager"
         DEPTNO="<%=emp.getDEPTNO()%>"
         where="where deptno=:deptno"
        >
        <%-- create select-items, check for current mamager --%>
        <option value="<%=manager.getEMPNO()%>" <%if(manager.getEMPNO().equals(emp.getMGR())){%>selected<%}%>><%=manager.getENAME()%></option>

        <%-- end of drop-down --%>
        </sqltags:emp>
        </select>
        </td>
        </tr>

        <tr bgcolor="<%=row_colors[(++i)%2]%>">
        <td>
        Salary
        </td>
        <td>
        <%-- using COLUMN NAMES and setting values --%>
        <input type="text" name="sal" value="<%=emp.getSAL()%>">
        </td>
        </tr>

        <tr bgcolor="<%=row_colors[(++i)%2]%>">
        <td>
        Commission
        </td>
        <td>
        <%-- using COLUMN NAMES and setting values --%>
        <input type="text" name="comm" value="<%=emp.getCOMM()%>">
        </td>
        </tr>

        <tr bgcolor="<%=row_colors[(++i)%2]%>">
        <td>
        Hire Date
        </td>
        <td>
        <%-- using COLUMN NAMES and setting values; format as spec'd in _SELECT
          --%>
        <input type="text" name="hiredate" value="<%=emp.getHIREDATE()%>">
        </td>
        </tr>

        <tr bgcolor="FFFFFF">
        <td colspan=2 align="center">
        <%-- include additional fields that are not displayed in the page
             so that the submitted to page can have access to these values
          --%>
        <input type="hidden" name="deptno" value="<%=emp.getDEPTNO()%>">
        <input type="hidden" name="empno" value="<%=emp.getEMPNO()%>">

        <%-- submit button --%>
        <input type="submit" name="btn" value="Insert">
        <input type="submit" name="btn" value="Update">
        <input type="submit" name="btn" value="Delete">
        <input type="submit" name="btn" value="Select">
        </td>
        </tr>
        </table>
    </td>
    </tr>
    </table>
</td>
</tr>
</table>
</form>
<%-- close out the emp scope --%>
</sqltags:emp>

<%-- give back the connection to the pool --%>
</sqltags:connection>
</body>
</html>

