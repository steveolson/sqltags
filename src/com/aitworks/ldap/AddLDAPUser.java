package com.aitworks.ldap;  
import java.io.IOException;  
import java.util.Vector;  
import java.util.StringTokenizer;  
import javax.servlet.jsp.JspWriter;  
import netscape.ldap.LDAPAttribute;  
import netscape.ldap.LDAPAttributeSet;  
import netscape.ldap.LDAPConnection;  
import netscape.ldap.LDAPEntry;  
import netscape.ldap.LDAPException;  
  
  
final public class AddLDAPUser {  
   //***************************************************************************  
   //Constructors  
   //***************************************************************************  
   /**  
    * Class constructor.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   nameVector users to add to rolodex  
    * @param   unit the unit were adding the user to  
    * @param   org the org were adding the user to  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public AddLDAPUser(Vector vector){  
   //-------------------------------------------------------------------------  
      nameVector=vector;  
      processData();  
   }  
  
   //-------------------------------------------------------------------------  
   public AddLDAPUser(Vector vector, String stringOne,   
                      String stringTwo, JspWriter jspWriter){  
   //-------------------------------------------------------------------------  
      unit=stringOne;  
      org=stringTwo;  
      nameVector=vector;  
      out=jspWriter;  
      processData();  
   }  
  
   //**************************************************************************  
   //Finalize Method  
   //**************************************************************************  
   /**  
    * This is the finalizer method for this class  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   protected void finalize() throws Throwable{  
   //-------------------------------------------------------------------------  
      super.finalize();  
   }// finalizer() ENDS  
  
   //***************************************************************************  
   //Public Methods  
   //***************************************************************************  
   /**  
    * This method adds an enty to LDAP  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public boolean addEntry(){  
   //-------------------------------------------------------------------------  
      boolean operationValid=true;  
      String dn="uid="+loginid+", ou="+unit+", o="+org;  
      LDAPAttribute attr1=new LDAPAttribute("objectclass",objectClassValues);  
      //Person required attributes  
      LDAPAttribute attr2=new LDAPAttribute("cn",fullName);  
      LDAPAttribute attr3=new LDAPAttribute("sn",lastName);  
  
      LDAPAttribute attr4=new LDAPAttribute("userPassword",password);  
      LDAPAttribute attr5=new LDAPAttribute("telephoneNumber",phone);  
      LDAPAttribute attr6=new LDAPAttribute("uid",loginid);  
      LDAPAttribute attr7=new LDAPAttribute("giveNname",firstName);  
      LDAPAttributeSet attrSet = new LDAPAttributeSet();  
      attrSet.add(attr1);  
      attrSet.add(attr2);  
      attrSet.add(attr3);  
      attrSet.add(attr4);  
      attrSet.add(attr5);  
      attrSet.add(attr6);  
      attrSet.add(attr7);  
      LDAPEntry myEntry = new LDAPEntry(dn,attrSet);  
  
      try {  
	 /* Connect to server */  
	 ld.connect(host,port);  
         ld.authenticate(authenticateName,authenticatePassword);  
  
	 if(debug){  
	    System.out.println("host="+host);  
	    System.out.println("port="+port);  
	    System.out.println("name="+authenticateName);  
	    System.out.println("password="+authenticatePassword);  
	    System.out.println("dn="+dn);  
	 }  
  
	 /* Now add the entry to the directory */  
	 ld.add(myEntry);  
         out.println("Entry added: "+fullName+"<br>");  
         out.flush();  
      }  
      catch(IOException e) {  
      }  
      catch( LDAPException e ) {  
	 if ( e.getLDAPResultCode() == LDAPException.ENTRY_ALREADY_EXISTS )  
	    System.out.println( "Error: Entry already present" );  
	 else{  
	    System.out.println( "Error: " + e.toString() );  
	    operationValid=false;  
         }  
      }  
  
      return operationValid;  
   }//addEntry() ENDS  
  
   /**  
    * This method sets the name we will use to authenticate ourself to the  
    * server.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   authenticateName the name use to authenticate ourself.  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public void setAuthenticateName(String string){  
   //-------------------------------------------------------------------------  
      authenticateName=string;  
   }// setAuthenticateName() ENDS  
  
   /**  
    * This method sets the password used to authenticate ourself to the  
    * server.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   authenticatePassword the password use to authenticate ourself.  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public void setAuthenticatePassword(String string){  
   //-------------------------------------------------------------------------  
      authenticatePassword=string;  
   }// setAuthenticatePassword() ENDS  
  
   /**  
    * This method sets the object class values. These values are used when the  
    * entry in inserted into LDAP.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   authenticatePassword the name use to authenticate ourself.  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public void setObjectClassValues(String[] string){  
   //-------------------------------------------------------------------------  
      objectClassValues=string;  
   }// setObjectClassValues() ENDS  
  
   /**  
    * This method sets the host used to authenticate ourself to the  
    * server.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   string the host name  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public void setHost(String string){  
   //-------------------------------------------------------------------------  
      host=string;  
   }// setHost() ENDS  
  
   /**  
    * This method sets the port used to authenticate ourself to the  
    * server.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   port the port use to authenticate ourself.  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public void setPort(int value){  
   //-------------------------------------------------------------------------  
      port=value;  
   }// setPort() ENDS  
  
   /**  
    * <code>toString</code>  
    * <p>  
    * This method provides a default print for the class.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param <code>none</code>  
    */  
   //---------------------------------------------------------------------------  
   public String toString(){  
   //---------------------------------------------------------------------------  
      String cr="\n";  
      String tab="\t";  
      StringBuffer buffer=new StringBuffer(cr+"*****AddLDAPUser: "+cr);  
      buffer.append(tab+"authenticateName="+authenticateName+cr);  
      buffer.append(tab+"authenticatePassword="+authenticatePassword+cr);  
      buffer.append(tab+"debug="+debug+cr);  
      buffer.append(tab+"dn="+dn+cr);  
      buffer.append(tab+"email="+email+cr);  
      buffer.append(tab+"firstName="+firstName+cr);  
      buffer.append(tab+"fullName="+fullName+cr);  
      buffer.append(tab+"host="+host+cr);  
      buffer.append(tab+"lastName="+lastName+cr);  
      buffer.append(tab+"loginid="+loginid+cr);  
      buffer.append(tab+"nameVector="+nameVector+cr);  
      buffer.append(tab+"objectClassValues="+objectClassValues+cr);  
      buffer.append(tab+"org="+org+cr);  
      buffer.append(tab+"out="+out+cr);  
      buffer.append(tab+"password="+password+cr);  
      buffer.append(tab+"port="+port+cr);  
      buffer.append(tab+"title="+title+cr);  
      buffer.append(tab+"uid="+uid+cr);  
      buffer.append(tab+"unit="+unit+cr);  
      return buffer.toString();  
   }// toString() ENDS  
  
   //***************************************************************************  
   //Private Methods  
   //***************************************************************************  
   /**  
    * This method processes the information stored within the name vector  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   private void processData(){  
   //-------------------------------------------------------------------------  
      int vectorSize=nameVector.size();  
      String token=",";  
      firstName="";  
      lastName="";  
      email="";  
      phone="";  
      loginid="";  
      title="";  
      password="tacklebox";  
      ld=new LDAPConnection();  
  
      for(int index=0;index<vectorSize;index++){  
         String element=(String)nameVector.get(index);  
         StringTokenizer tokenizer=new StringTokenizer(element,token);  
         firstName=tokenizer.nextToken();  
         lastName=tokenizer.nextToken();  
         fullName=firstName+" "+lastName;  
         email=tokenizer.nextToken();  
         phone=tokenizer.nextToken();  
         loginid=tokenizer.nextToken();  
         title=tokenizer.nextToken();  
         password="tacklebox";  
  
         if(!addEntry())  
            ;//break;  
      }  
  
      /* Done, so disconnect */  
      if ( (ld != null) && ld.isConnected() ) {  
         try {  
            ld.disconnect();  
         }         
         catch ( LDAPException e ) {  
            System.out.println( "Error: " + e.toString() );  
         }  
      }  
   }//processData() ENDS  
  
   //***************************************************************************  
   //Class Variables  
   //***************************************************************************  
   private String         authenticateName="cn=Directory Manager";  
   private String         authenticatePassword="o2byoung";  
   private boolean        debug=false;  
   private StringBuffer   dn=new StringBuffer();  
   private String         email;  
   private String         firstName;  
   private String         fullName;  
   private String         host="localhost";  
   private String         lastName;  
   private LDAPConnection ld;  
   private String         loginid;  
   private Vector         nameVector;  
   private String         objectClassValues[]={"top","person",  
                                               "organizationalPerson",  
                                               "inetOrgPerson",  
                                               "passwordPolicy",  
                                               "passwordObject"  
                                              };  
   private String         org;  
   private JspWriter      out;  
   private String         password="o2byoung";  
   private String         phone;  
   private int            port=389;  
   private String         title;  
   private String         uid;  
   private String         unit;  
}//AddLDAPUser() ENDS  
  
