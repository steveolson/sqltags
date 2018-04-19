/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aitworks.creditcard.skipjack;

import com.sun.net.ssl.internal.ssl.Provider;
import java.io.*;
import java.net.*;
import java.security.Security;
import java.util.*;
import javax.servlet.jsp.tagext.TagSupport;

// Referenced classes of package com.aitworks.creditcard.skipjack:
//            SkipjackBeanException

public class SkipjackBean extends TagSupport
{
	private String postUrl  = "https://developer.skipjackic.com/scripts/evolvcc.dll?Authorize";
    private int result = 3; // UNKNOWN
    private boolean verbose = false;
		// private boolean verbose = true;
    private Vector requiredAttributes;
    private Hashtable attributes;

private static final String [][] returnCodeArray = {
	{"-1", "Error in request Data was not by received intact by Skipjack Transaction Network."},
	{"0", "Communication Failure Error in Request and Response at IP level."},
	{"1", "Success Valid Data."},
	{"-35", "Invalid credit card number Credit card number does not comply with the Mod10 check."},
	{"-37", "Merchant Processor Unavailable. Skipjack is unable to communicate with payment Processor."},
	{"-39", "Length or value of HTML Serial Number Invalid serial number."},
	{"-51", "Length or value of zip code The value or length for billing zip code is incorrect."},
	{"-52", "Length or value in shipto zip code The value or length for shipping zip code is incorrect."},
	{"-53", "Length or value in expiration date The value or length for credit card expiration month is incorrect."},
	{"-54", "Length or value of month or year of credit card account number"},
	{"-55", "Length or value in street address "},
	{"-56", "Length or value in shipto streetaddress"},
	{"-57", "Length or value in transaction amount "},
	{"-58", "Length or value in Merchant Name "},
	{"-59", "Length or value in Merchant Address "},
	{"-60", "Length or value in Merchant State "},
	{"-61", "Error length of value in shipto state"},
	{"-62", "Error length or value in order string"},
	{"-64", "Error invalid phone number "},
	{"-65", "Error empty billing name "},
	{"-66", "Error empty e-mail "},
	{"-67", "Error empty street address "},
	{"-68", "Error empty city "},
	{"-69", "Error empty state"},
	{"-70", "Empty Zip Code field is empty."},
	{"-71", "Empty Ordernumber field is empty."},
	{"-72", "Empty Account number field is empty"},
	{"-73", "Empty Month field is empty."},
	{"-74", "Empty Year field is empty."},
	{"-75", "Empty Serial number field is empty."},
	{"-76", "Empty Transaction amount field is empty."},
	{"-77", "Empty Order string field is empty."},
	{"-78", "Empty Shipto phone field is empty."},
	{"-79", "Length or value billing name "},
	{"-80", "Length shipto name "},
	{"-81", "Length or value of Customer location"},
	{"-82", "Length or value of state "},
	{"-83", "Length or value shipto phone "},
	{"-84", "Duplicate ordernumber "},
	{"-85", "Airline leg info invalid "},
	{"-86", "Airline ticket info invalid "},
	{"-87", "Point of Sale check routing number must be 9 numeric digits"},
	{"-88", "Point of Sale check account number"},
	{"-89", "Point of Sale check MICR missing or invalid"},
	{"-90", "Point of Sale check number missing or invalid"},
	{"-91", "CVV2 Invalid or empty "},
	{"-92", "Approval Code Invalid is a 6 digit code."},
	{"-93", "Blind Credits Request Refused "},
	{"-94", "Blind Credits Failed"},
	{"-95", "Voice Authorization Request Refused "},
	{"-96", "Voice Authorizations Failed"},
	{"-97", "Fraud Rejection Violates Velocity Settling."},
	{"-98", "Invalid Discount Amount"},
	{"-99", "POS PIN Debit Pin Block Debit-specific"},
	{"-100", "POS PIN Debit Invalid "},
	{"-101", "Invalid Authentication Data Data for Verified by Visa/MC "},
	{"-102", "Authentication Data Not Allowed"},
	{"-103", "POS Check Invalid Birth Date POS birth date in an incorrect format. "},
	{"-104", "POS Check Invalid Identification Type POS check "},
	{"-105", "Invalid trackdata Track Data is in invalid format."},
	{"-106", "POS Check Invalid Account Type"},
	{"-107", "POS PIN Debit Invalid Sequence Number"},
	{"-108", "Invalid Transaction ID"},
	{"-109", "Invalid From Account Type"},
	{"-110", "Pos Error Invalid To Account Type"},
	{"-112", "Pos Error Invalid Auth Option"},
	{"-113", "Pos Error Transaction Failed"},
	{"-114", "Pos Error Invalid Incoming Eci"},
	{"-115", "POS Check Invalid Check Type"},
	{"-116", "POS Check Invalid Lane Number "},
	{"-117", "POS Check Invalid Cashier Number"}
	{"-118", "Invalid POST Url"}
	{"-119", "General Error"}
	{"-120", "Invalid Record Count"}
	{"-121", "Invalid File Boundry"}
	{"-122", "Not Allowed"}
	{"-123", "Developer Serial Number Invalid"}
	{"-124", "Invalid File Type"}
	{"-125", "Invalid Debit Batch Number"}
	{"-126", "POS Card Auth Not Allowed"}
	{"-127", "POS Error Balance Inquiry"}
	{"-128", "POS Reversal Not Allowed"}
};

    public SkipjackBean()
    {
        initialize();
    }

    public SkipjackBean(String url)
    {
		this();
		this.setPostUrl(url);
	}

    protected void initialize()
    {
        attributes = new Hashtable();
        requiredAttributes = new Vector();

        requiredAttributes.add("accountnumber"); // credit card number
        requiredAttributes.add("sjname"); // name on card
        requiredAttributes.add("email"); // email addy for confirmations
        requiredAttributes.add("transactionamount"); // amount charged
        requiredAttributes.add("month"); // expire month
        requiredAttributes.add("year"); // expire year
        requiredAttributes.add("streetaddress");
        requiredAttributes.add("city");
        requiredAttributes.add("state");
        requiredAttributes.add("zipcode");
        requiredAttributes.add("shiptophone");
        requiredAttributes.add("serialnumber");
    }

    public int execute()
        throws SkipjackBeanException
    {
        if(verbose) System.out.println("execute");
        setResult(2);
        validate();
        String queryString = buildQueryString();
        if(verbose) System.out.println("Posting to Skipjack: " + getPostUrl() + "?" + queryString);
        String urlOutput = postURL(getPostUrl(), queryString);
        if(verbose) System.out.println("processed URL...");
        parseResult(urlOutput);
        String r = getAttribute("szIsApproved");

        if(r == null)
            setResult(2);
        else
        if(r.trim().equals("1"))
             setResult(1);
        else setResult(0);

        return getResult();
    }

    private void validate()
        throws SkipjackBeanException
    {
        Enumeration enum1 = requiredAttributes.elements();
        boolean hasMissingRequiredAttributes = false;
        String missingAttributes = new String("");
        String sep = "";
        while(enum1.hasMoreElements())
        {
            String key = (String)enum1.nextElement();
            if(!attributes.containsKey(key))
            {
                hasMissingRequiredAttributes = true;
                missingAttributes = missingAttributes + sep + key;
                sep = ", ";
            }
        }
        if(hasMissingRequiredAttributes)
        {
            SkipjackBeanException e = new SkipjackBeanException("Missing required field(s): " + missingAttributes);
            setAttribute("exception", e.toString());
            if(verbose) System.out.println(e.toString());
            throw e;
        } else
        {
            return;
        }
    }

    private String buildQueryString()
    {
        Enumeration enum2 = attributes.keys();
        StringBuffer urlBuffer = new StringBuffer();
        String sep = "";
        String name = "";
        String value = "";
        while(enum2.hasMoreElements())
        {
            name = (String)enum2.nextElement();
            value = URLEncoder.encode((String)attributes.get(name));
            urlBuffer.append(sep + name + "=" + value);
            sep = "&";
        }
        return urlBuffer.toString();
    }

    private String postURL(String inURL, String queryString)
        throws SkipjackBeanException
    {
        String input = new String();
        Security.addProvider(new Provider());
        System.setProperty("java.protocol.handler.pkgs", "com.sun.net.ssl.internal.www.protocol");
        try
        {
            URL url = new URL(inURL);
            URLConnection urlConnection = url.openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            OutputStream out = urlConnection.getOutputStream();
            out.write(queryString.getBytes());
            out.flush();
            out.close();
            setAttribute("query_string", queryString);
            InputStream in = urlConnection.getInputStream();
            StringBuffer sb = new StringBuffer();
            int i;
            while((i = in.read()) != -1)
                sb.append((char)i);
            input = new String(sb.toString());
            setAttribute("url_reply", input);
        }
        catch(Exception ex)
        {
            SkipjackBeanException e = new SkipjackBeanException("Exception in PostURL(" + inURL + ", " + queryString + "): " + ex.toString());
            setAttribute("exception", e.toString());
            if(verbose) System.out.println(e.toString());
            throw e;
        }
        return input;
    }

    private void parseAPIResult(String in)
	{
		//
		// not implemented yet
		//
		// header is list of fields
		// second line is list of field values
		//
	}

    private void parseResult(String in)
    {
        int start = 0;
        int end = 0;
		int count=0;
        String line = in;
        String line2 = "";
        String name = "";
        String value = "";
        do
        {
			start = line.indexOf("<!--");
			if(start<0) {
                // System.out.println("WARN: no start <!-- " + line);
				break;
			}
			start = start + 4; // length of "<!--" :-))
            end = line.indexOf("-->", start);
            if(end<0 && end>start) {
                // System.out.println("WARN: no end --> " + line);
                break;
			}
            line2 = line.substring(start, end);
            int equal = line2.indexOf("=");
            if(equal > 0)
            {
                name = line2.substring(0, equal);
                value = line2.substring(equal + 1);
                attributes.put(name, value);
				if(verbose) System.out.println("put("+name+","+value+")");
				if(name.equalsIgnoreCase("szReturnCode")) {
					for( int i=0; i<returnCodeArray.length ; i++){
						if(returnCodeArray[i][0].equalsIgnoreCase(value)) {
							setAttribute("szReturnCodeText", returnCodeArray[i][1]);
							break;
						}
					}
				}
            } else
            {
                // if(verbose) System.out.println("WARN: missing '=' sign, skipping: " + line2);
            }
            start = end + 1;
			line = line.substring(end);
        } while(start < in.length() && count++ <30 );
    }

    public boolean isApproved()
    {
        return getResult() == 1;
    }

    public boolean isDeclined()
    {
        return getResult() == 0;
    }

    public boolean isFailure()
    {
        return (getResult() != 0 && getResult() != 1 );
    }

    public void setResult(int result)
    {
        this.result = result;
    }

    public int getResult()
    {
        return result;
    }

    public void setAttribute(String name, String value)
    {
        attributes.put(name, value);
		if(verbose)System.out.println("put("+name+","+value+")");
    }

    public String getAttribute(String name)
    {
        return (String)attributes.get(name);
    }

    public void setVerbose(boolean b)
    {
        verbose = b;
    }

    public boolean getVerbose()
    {
        return verbose;
    }

    public static void main(String argv[])
    {
        SkipjackBean vb = new SkipjackBean("https://developer.skipjackic.com/scripts/evolvcc.dll?Authorize");
        vb.setVerbose(true);
	/*
	 * SerialNumber
	   DeveloperSerialNumber
	 * SJName
	 * Email
	 * StreetAddress
	 * City
	 * State
	 * ZipCode
	 * OrderNumber
	 * OrderString
	 * AccountNumber
	 * Month
	 * Year
	 * TransactionAmount
	 * ShipToPhone
	  */
		// this is the URL that processes the requests...
		vb.setPostUrl( "https://developer.skipjackic.com/scripts/evolvcc.dll?Authorize" );
		// vb.setPostUrl( "https://developer.skipjackic.com/scripts/evolvcc.dll?AuthorizeAPI" );

		// vb.setAttribute("developerserialnumber", "100204118870");
		vb.setAttribute("serialnumber", "000545388719"); // VITAL
		// vb.setAttribute("serialnumber", "000860255304"); // NOVA
			// NOVA is not currently working ... wondering if it's one of those complex vendors ...

        vb.setAttribute("ordernumber", "4055");
		vb.setAttribute("orderstring","1~Registration~125.00~1~N~||");
			// 1. item number
			// 2. item description
			// 3. item cost
			// 4. item quantity
			// 5. taxable "Y or N"
			// 6. ignore AVS switch "||1" to override acct settings

        vb.setAttribute("transactionamount", "25.00");
        vb.setAttribute("sjname", "John E. Doe");
        //vb.setAttribute("accountnumber", "4111111111111111");
        vb.setAttribute("accountnumber","4445999922225");
        vb.setAttribute("month", "12");
        vb.setAttribute("year", "12");
        vb.setAttribute("streetaddress", "8320");
        vb.setAttribute("city", "columbia");
        vb.setAttribute("state", "MD");
        vb.setAttribute("zipcode", "85284");
        vb.setAttribute("email", "me@privacy.net");
		vb.setAttribute("shiptophone", "000-0000");
        try {
			int result = vb.execute();
            System.out.println("survey says: " + result);
            System.out.println("AUTHCODE: " + vb.getAttribute("AUTHCODE") +"-"+ vb.getAttribute("szReturnCodeText"));
            // System.out.println(vb.attributes.toString());
        }
        catch(SkipjackBeanException e)
        {
            System.out.println("Execute Exception: " + e);
        }
    }

	/**
	 * @return the postUrl
	 */
	public String getPostUrl() {
		return this.postUrl;
	}

	/**
	 * @param postUrl the postUrl to set
	 */
	public void setPostUrl(String postUrl) {
		this.postUrl = postUrl;
	}


}
