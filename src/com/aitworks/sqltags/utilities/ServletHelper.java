/* $Id: ServletHelper.java,v 1.2 2002/03/15 14:33:30 solson Exp $
 * $Log: ServletHelper.java,v $
 * Revision 1.2  2002/03/15 14:33:30  solson
 * added License, ID, and Log
 *
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
package com.aitworks.sqltags.utilities;  
import java.io.*;  
import java.util.*;  
import javax.servlet.*;  
import javax.servlet.jsp.*;  
import javax.servlet.http.*;  
  
/**  
 * The Class is a helper class for the OJP Project. Methods will be  
 * inserted as needed.  
 * @author  Booker Northington II  
 * @version 1.0  
 * @return  none  
 * @since   JDK1.3  
 */  
public class ServletHelper{  
   private static PrintWriter LOG;  
   private String ojpErrorLog="errorlog";  
   private HttpServletRequest request;  
   private HttpServletResponse response;  
   private JspWriter jspWriter;  
   private PrintWriter responseWriter;  
   private PageContext pageContext;  
   private String tab="   ";  
   private String cr="\n";  
   private String[] parameters;  
   private String url="";  
   private int arraySize;  
   private int floodCount;  
   private RequestDispatcher dispatcher;  
   ServletContext servletContext;  
  
   /**  
    * Default constructor.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public ServletHelper(HttpServletResponse response){  
   //-------------------------------------------------------------------------  
      this.response=response;  
  
      try{  
         responseWriter=response.getWriter();  
      }  
      catch(IOException exception){}  
   }// Utilities() END  
     
   /**  
    * This constructor is used to create a JspWriter.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public ServletHelper(PageContext pageContext){  
   //-------------------------------------------------------------------------  
      this.pageContext=pageContext;  
      jspWriter=pageContext.getOut();  
   }// Utilities() END  
  
   /**  
    * This Constructor makes available the request and response objects.   
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public ServletHelper(HttpServletRequest request,   
                        HttpServletResponse response){  
   //-------------------------------------------------------------------------  
      this.request=request;  
      this.response=response;  
   }// Utilities() END  
  
   /**  
    * Default constructor.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public ServletHelper(HttpServletRequest request){  
   //-------------------------------------------------------------------------  
      this.request=request;  
      //LOG=new PrintWriter(new FileWriter(logFile,true),true);  
   }// Utilities() END  
  
  
   /**  
    * This methods returns the current parameter  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public String getParameter(HttpServletRequest request, String name){  
   //-------------------------------------------------------------------------  
      name=request.getParameter("name");  
  
      if(name!=null){  
         if(request.getParameter("name").equals("Submit"))  
           System.out.println("Submit was pressed");  
         else  
           System.out.println("Submit was not pressed");  
      }  
      return name;  
   }// getParameter() END  
  
  
   /**  
    * This method writes to the log.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   the class name.  
    * @return  the new class object.  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public static void errorLog(String msg){  
   //-------------------------------------------------------------------------  
      //LOG(msg);  
   }// errorLog() ENDS  
  
   /**  
    * This methods enumerates through a list.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public void enumerateList(Enumeration enum){  
   //-------------------------------------------------------------------------  
      for(;enum.hasMoreElements();)  
         System.out.println(enum.nextElement());  
   }// enumerateList() ENDS  
  
   /**  
    * This method returns a parameter stored within the request object.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   request the request object.  
    * @param   name the parameter whos values is requested.  
    * @return  the value of the requested parameter.  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public String getParameterValues(HttpServletRequest request,String name){  
   //-------------------------------------------------------------------------  
      String answer="";  
  
      if(request.getParameter(name) != null)  
	 answer=request.getParameter(name);  
  
      return answer;  
   }// getParameterValues() ENDS  
  
   /**  
    * This method fowards the page to another url.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   url the site were going to.  
    * @param   request the senders request object.  
    * @param   response the senders response object.  
    * @return  true if no errors were encountered  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public boolean forwardRequest(String url){  
   //-------------------------------------------------------------------------  
      boolean result=true;  
  
      try{  
	 RequestDispatcher rd = request.getRequestDispatcher(url);  
	 rd.forward(request,response);  
      }  
      catch(IOException exception){  
	 System.out.println(exception);  
         result=false;  
      }  
      catch(ServletException exception){  
	 System.out.println(exception);  
         result=false;  
      }  
  
      return result;  
   }// forwardRequest() ENDS  
  
  
   /**  
    * This method fowards the page to another url.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   url the site were going to.  
    * @param   request the senders request object.  
    * @param   response the senders response object.  
    * @return  true if no errors were encountered  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public boolean forwardRequest(String url, ServletRequest request,  
            ServletResponse response){  
   //-------------------------------------------------------------------------  
      boolean result=true;  
  
      try{  
	 RequestDispatcher rd = request.getRequestDispatcher(url);  
	 rd.forward(request,response);  
      }  
      catch(IOException exception){  
	 System.out.println(exception);  
         result=false;  
      }  
      catch(ServletException exception){  
	 System.out.println(exception);  
         result=false;  
      }  
  
      return result;  
   }// forwardRequest() ENDS  
  
   /**  
    * This method is used to write to the browser.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   message the data were writing  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public void tagWriter(String message){  
   //-------------------------------------------------------------------------  
      try{  
         jspWriter.print(message);   
      }  
      catch(IOException exception){  
      }  
   }  
  
   /**  
    * This method can be used to write to the browser. You must first   
    * send the response object to the constructor.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   message the information to send to the browser.  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public void responseWriter(String message){  
   //-------------------------------------------------------------------------  
      responseWriter.write(message);   
   }  
  
   /**  
    * Default  constructor.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public void floodSite(ServletConfig config, String[] parameters,  
                              String url, int floodCount){  
   //-------------------------------------------------------------------------  
      ServletContext context=config.getServletContext();  
      int arraySize=parameters.length;  
      int parameterIndex=0;  
      int index=0;  
      String newUrl="";  
  
      for(;index<floodCount;index++){  
System.out.println("looping= "+index);  
  
         if(parameterIndex>=arraySize)  
            parameterIndex=0;  
  
         newUrl=url+"?"+parameters[parameterIndex];  
System.out.println("URL = "+newUrl);  
         parameterIndex++;  
  
         RequestDispatcher dispatcher=context.getRequestDispatcher(newUrl);  
  
         try{  
         dispatcher.forward(request,response);  
	 }  
	 catch(IOException exception){  
	    System.out.println("IOE: "+exception);  
	 }  
	 catch(ServletException exception){  
	    System.out.println("Servlet: "+exception);  
	 }  
      }  
System.out.println("leaving servletHelper= "+index);  
   }  
  
   /**  
    * Default  constructor.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public void floodSite(HttpServletRequest request,  
                                      HttpServletResponse response,   
                                      ServletConfig config, String url){   
      dispatcher=request.getRequestDispatcher(url);  
  
      try{  
	 dispatcher.forward(request,response);  
      }  
      catch(IOException exception){  
	 System.out.println("IOE: "+exception);  
      }  
      catch(ServletException exception){  
	 System.out.println("Servlet: "+exception);  
      }  
   }  
}// ServletHelper() ENDS  
