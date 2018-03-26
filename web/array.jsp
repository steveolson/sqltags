<%-- 
    $Id: array.jsp,v 1.9 2002/03/19 20:08:01 solson Exp $
    $Log: array.jsp,v $
    Revision 1.9  2002/03/19 20:08:01  solson
    added commented out connection tag for mySQL database

    Revision 1.8  2002/03/15 14:59:10  solson
    added License, ID, and Log

    Revision 1.7  2002/03/10 02:35:02  solson
    added CVS variables and lots of comments

    Revision 1.6  2002/03/08 20:23:40  solson
    added header link ... added some features to array

    Revision 1.5  2002/03/08 18:45:49  solson
    selected header
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
<%@ page import="com.aitworks.sqltags.jsptags.*,com.aitworks.demo.sqltags.*" %>
<%@ taglib uri="/WEB-INF/lib/demoTags.jar" prefix="demo" %>
<html>
<head>
<SCRIPT language='javascript'>
    function setOperation(fld,key,op){
        document.f.elements[ fld+"["+key+"]" ].value = op;
    }
</SCRIPT>
</head>
<body>
<%-- Include Standard Header .. BTW, include cannot be within a custom tag.
  --%>
<jsp:include page="header.jsp" flush="true" />

<%-- OK, open a connection to the database; using the "properties" file
     found in /WEB-INF named oracle.properties, the ConnectionTag can be
     referenced within the page using "connect" scriptlet variable and
     the Initialization properties from oracle.properties are available
     from via the name "init".

<demo:connection id="connect" initSrc="/WEB-INF/mysql.properties" name="init">
     Uncomment the above line and comment out the line below and to run this page 
     against the mySQL demo database!
  --%> 
<demo:connection id="connect" initSrc="/WEB-INF/oracle.properties" name="init">
    <%-- OK, here's where it get's a bit complicated ...
         The next tag processes an entire Array of values submitted to the page.
         variables are in the format of:
            ENAME[n]="namen", JOB[n]="jobn",OP[n]="Update"
            ENAME[m]="namem", JOB[m]="jobm",OP[m]="Insert"
         The tag automatically groups the column-values into rows based on the
         ArrayIndexes.  So, in my crude example above, you'd have essentially
         two rows: n and m.  Row n would get UPDATED and row m would be INSERTED

         The <FORM> below sets up the Array based on EMPNO.  See below ... 
      --%>
    <demo:emp id="eReq" columns="EMPNO,ENAME,JOB,DEPTNO" buttonName="OP" properties="true">
        <%-- display any exceptions that may be encountered --%>
        <demo:exception name="eReq" clearException="false" fontColor="RED"/>
    </demo:emp>
<h2> Select a Department </h2>
  <BLOCKQUOTE>
    <%-- Display all departments in DNAME order --%>
    <demo:dept id="d" orderBy="order by dname">
        <%-- Build links to this page with DEPTNO passed in to indicate which
             Department was selected from this list
          --%>
       <A HREF=array.jsp?deptno=<%=d.getDEPTNO()%>><%=d.getDNAME()%></A> |
       <%-- close out the dept listing --%>
    </demo:dept>
  </BLOCKQUOTE>

<TABLE border=0>
<%-- This next tag uses several concepts.
     1- properties=true grabs the DEPTNO from the request object from the
        link above.
     2- buttonName=deptButton is used to avoid clashes with buttonName for
        processing the emp tag up near the top of the page.  NOTE, there is
        only 1 request object so everything is in there.
     3- operation=select causes the tag to run a query based on the dept 
        table's PRIMARY KEY (which is DEPTNO) and fetch all of the other
        columns from the table into the tag.
  --%>
<demo:dept id="dReq" properties="true" buttonName="deptButton" operation="select">
<TR><TD COLSPAN=1>
        <TABLE border=2>
        <%-- Note that there is ONE FORM for the entire page.  All rows from
             the query below will be included ONE FORM.  The rows of data are
             separated by using an "array" syntax: ENAME[i], JOB[i], etc.
          --%>
        <FORM NAME=f METHOD=POST ACTION="array.jsp">
        <%-- OK, now that we've got a DEPT .. we can list the EMPs that have
             been assigned to it base on the FK_DEPTNO foreign key.  This is
             accomplished with two attributes:
                foreignKey="FK_DEPTNO" and
                parentName="dReq" which is the outer dept tag 
             Also note the orderBy attribute, it is used when the tag is nested
             because the where clause is defined by the foreign key relationship
          --%>
        <demo:emp id="e" foreignKey="FK_DEPTNO" parentName="dReq" orderBy="order by ename">

            <%-- Here we make use of the FIRST tag.  This is only output on the
                 first row of output.  NOTE: nothing is output if no rows are
                 found by this query.  This is a good way to hide table headers
                 where there are no rows to display.
              --%>
            <demo:first name="e">
                <BR>
                <%-- Below we demonstrate referencing the parent DEPT using the
                     getter for the parent FK. NOTE: getFK_DEPTNO_PARENT()
                  --%>
                <h2>Employees of the <%= e.getFK_DEPTNO_PARENT().getDNAME() %> Department</h2>
                <TR bgcolor='lightBlue'><TH>Employee ID</TH>
                    <TH>Name</TH>
                    <TH>Job</TH>
                    <TH>Operation</TH>
                </TR>
            </demo:first>
            <%-- Now we are beyond the FIRST tag, this part is output for 
                 every row returned by the EMP tag.
              --%>
            <TR><TD>
                    <%-- Below is demonstrated the getArrayIndexName() method.
                         It provides a easier syntax for generating the array
                         index names. Its use is strictly optional.  So, given
                         EMPNO=1000 the following INPUT would have a NAME set to
                         NAME="EMPNO[1000]" and the VALUE="1000"
                      --%>
                    <INPUT NAME='<%=e.getArrayInputName("EMPNO",e.getEMPNO())%>'
                           VALUE="<%=e.getEMPNO()%>"
                           TYPE="hidden"
                           >
                    <%-- Again, using the getArrayIndexName helper --%>
                    <INPUT NAME='<%=e.getArrayInputName("DEPTNO",e.getEMPNO())%>'
                           VALUE="<%=e.getDEPTNO()%>" 
                           TYPE="hidden">
                    <%=e.getEMPNO()%>
                </TD>
                <TD>
                    <%-- Ok, trying to get a little cute on this one ... 
                         using JavaScript function onChange to set the OP[i] for
                         this row; so whenever this INPUT element is "changed"
                         OP[i] is set to "Update" so this row gets UPDATED!  In 
                         the "real world" i'd do something similar however, I'd
                         likely make the OP[i] INPUT hidden.
                      --%>
                    <INPUT NAME='<%=e.getArrayInputName("ENAME",e.getEMPNO())%>'
                           VALUE="<%=e.getENAME()%>" 
                           onChange='<%= "setOperation(\"OP\"," +"\""+e.getEMPNO()+"\""+",\"Update\")"%>'
                           >
                </TD>
                <TD>
                    <%-- Another input, setting OP[i] and using ArrayIndex...
                      --%>
                    <INPUT NAME='<%=e.getArrayInputName("JOB",e.getEMPNO())%>'
                           VALUE="<%=e.getJOB()%>"
                           onChange='<%= "setOperation(\"OP\"," +"\""+e.getEMPNO()+"\""+",\"Update\")"%>'
                           >
                </TD>
                <TD>
                    <%-- OK, This is the controlling operation for the row.  
                         This can be set to: INSERT, UPDATE, DELETE, or SELECT.
                         Whenever any of the other fields in this row are 
                         "changed" a JavaScript function sets this value to
                         Updated.
                      --%>
                    <INPUT name='<%=e.getArrayInputName("OP",e.getEMPNO())%>' size='6'>
                </TD>
            </TR>
            <%-- OK, this demonstrates the LAST tag.  This is only output on
                 the last row of data output for the tag.  If the tag returns
                 Zero rows, this section is NOT output!
              --%>
            <demo:last name="e">
                <TR><TD bgcolor='lightBlue' COLSPAN=4>Insert a new record below ... </TD></TR>
                <TR><TD>
                        <%-- OK, this is an extra row that is not connected to
                             the database.  It is a blank row for INSERTs.
                             Using a default ArrayIndex of 0 for this row. 
                             Should pick a value that will not collide with
                             a real EMPNO.

                             The JavaScript onChange sets OP[0]=Insert
                          --%>
                        <INPUT NAME='<%=e.getArrayInputName("EMPNO","0")%>'
                               VALUE=""
                               SIZE="5"
                               onChange='<%= "setOperation(\"OP\"," +"\"0\""+",\"Insert\")"%>'
                               >
                        <%-- more of the same ... --%>
                        <INPUT NAME='<%=e.getArrayInputName("DEPTNO","0")%>'
                               VALUE="<%=e.getDEPTNO()%>" TYPE="hidden">
                    </TD>
                    <TD>
                        <%-- more .. --%>
                        <INPUT NAME='<%=e.getArrayInputName("ENAME","0")%>'
                               VALUE="" 
                               onChange='<%= "setOperation(\"OP\"," +"\"0\""+",\"Insert\")"%>'
                               >
                    </TD>
                    <TD>
                        <%-- ... --%>
                        <INPUT NAME='<%=e.getArrayInputName("JOB","0")%>'
                               VALUE=""
                               onChange='<%= "setOperation(\"OP\"," +"\"0\""+",\"Insert\")"%>'
                               >
                    </TD>
                    <TD>
                        <%-- OK, here is the OP[0] field; used to hold the 
                             INSERT operation
                          --%>
                        <INPUT name='<%=e.getArrayInputName("OP","0")%>' size='6'>
                    </TD>
                </TR>
                <%-- A generic submit button to send the page and all rows
                     of data for processing ...
                  --%>
                <TR><TD COLSPAN=4><INPUT NAME=button type="submit" value='OK'>
                    </TD>
                </TR>

            <%-- close out the LAST tag --%>
            </demo:last>

        <%-- close out the nested EMP tag --%>
        </demo:emp>
        </FORM>
        </TABLE>
    </TD>
</TR>

<%-- close out the outer dept tag --%>
</demo:dept>
</TABLE>

<%-- give back the connection to the pool --%>
</demo:connection>
 </body>
</html>
