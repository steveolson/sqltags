<%--
 $Id: dept.jsp,v 1.4 2002/03/15 14:59:10 solson Exp $
 $Log: dept.jsp,v $
 Revision 1.4  2002/03/15 14:59:10  solson
 added License, ID, and Log

 Revision 1.3  2002/03/10 00:16:23  solson
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
<head><title>Departments</title></head>
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

<table bgcolor="FFFFFF" width="700" border="0">
<tr align="center">
<td>
Department
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
        <td>
        <br>
        </td>
        </tr>
        <%-- end header for table --%>

        <%-- list all departments 
             order by dname (department name)
             since there is a "where clause" that returns rows, the dept tag
             will automatically iterate through all the returned rows and 
             evaluate the contained context for each one.
        --%>
        <sqltags:dept
         id="dept"
         where="order by dname"
        >
        <tr bgcolor="<%=row_colors[(++i)%2]%>">
        <td>
        <%-- display data from DEPT table using "getter" for columns --%>
        <%=dept.getDNAME()%>
        </td>
        <td>
        <%-- more getters ... --%>
        <%=dept.getLOC()%>
        </td>
        <td>
        <%-- Use the "getter" within an A tag.  For a non-custom tag, snipplets
             can be used anywhere (as below); however, when processing a 
             custom tag either the entire string must be constant or the 
             entire string must be an Java snipplet expression.  
             more on that in later examples ... 
          --%>
        <a href="emp.jsp?deptno=<%=dept.getDEPTNO()%>">Employees</a>
        </td>
        </tr>
        <%-- end the "dept" tag's scope --%>
        </sqltags:dept>
        </table>
    </td>
    </tr>
    </table>
</td>
</tr>
</table>

<%-- give connection back to the pool. --%>
</sqltags:connection>
</body>
</html>
