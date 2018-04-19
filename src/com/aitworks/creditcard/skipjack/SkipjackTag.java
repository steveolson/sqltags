// Decompiled by DJ v3.5.5.77 Copyright 2003 Atanas Neshkov  Date: 3/11/2004 3:04:49 PM
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   SkipjackTag.java

package com.aitworks.creditcard.skipjack;

import java.util.Enumeration;
import javax.servlet.jsp.JspException;

// Referenced classes of package com.aitworks.creditcard.skipjack:
//            SkipjackBean, SkipjackBeanException

public class SkipjackTag extends SkipjackBean
{

    public SkipjackTag()
    {
    }

	public int doEndTag() throws JspException {
		initialize();
		return super.doEndTag();
	}

    public int doStartTag()
    {
        super.pageContext.setAttribute(getId(), this);
        try
        {
            execute();
            return 1;
        }
        catch(SkipjackBeanException e)
        {
			setAttribute("szReturnCodeText","Exception: " +e);
        }
        return 1;
    }

    public void setPost_url(String s) { setPostUrl(s); }
    public void setSerial_number(String s) { setAttribute("serialnumber", s); }
    public void setOrder_number(String s) { setAttribute("ordernumber", s); }
    public void setOrder_string(String s) { setAttribute("orderstring", s); }
    public void setTransaction_amount(String s) { setAttribute("transactionamount", s); }
    public void setTax(String s) { setAttribute("tax", s); }
    public void setCvv2(String s) { setAttribute("cvv2", s); }
    public void setAccount_number(String s) { setAttribute("accountnumber", s); }
    public void setSjname(String s) { setAttribute("sjname", s); }
    public void setCompany(String s) { setAttribute("company", s); }
    public void setMonth(String s) { setAttribute("month", s); }
    public void setYear(String s) { setAttribute("year", s); }
    public void setStreet_address(String s) { setAttribute("streetaddress", s); }
    public void setStreet_address2(String s) { setAttribute("streetaddress2", s); }
    public void setCity(String s) { setAttribute("city", s); }
    public void setState(String s) { setAttribute("state", s); }
    public void setZip_code(String s) { setAttribute("zipcode", s); }
    public void setCountry(String s) { setAttribute("country", s); }
    public void setEmail(String s) { setAttribute("email", s); }
    public void setPhone(String s) { setAttribute("phone", s); }
    public void setShip_to_company(String s) { setAttribute("shiptocompany", s); }
    public void setShip_to_address(String s) { setAttribute("shiptoaddress", s); }
    public void setShip_to_city(String s) { setAttribute("shiptocity", s); }
    public void setShip_to_state(String s) { setAttribute("shiptostate", s); }
    public void setShip_to_zipcode(String s) { setAttribute("shiptozipcode", s); }
    public void setShip_to_country(String s) { setAttribute("shiptocountry", s); }
    public void setShip_to_phone(String s) { setAttribute("shiptophone", s); }

	// utility function ...
    public void setExp_date(String s)
	{
		setMonth(s.substring(0,2));
		setYear(s.substring(2));
	}
	// some custom logic
    public void setDeveloper_serial_number(String s)
	{
		// when we launch, we don't want an empty Developer Serial Number
		// so we only include the attribute if it isn't empty
		if(s!=null && s.length()>0) {
			setAttribute("developerserialnumber", s);
		}
	}

	// custom fields ...
    public void setSport(String s) { setAttribute("sport", s); }
    public void setRegistration_number(String s) { setAttribute("registration_number", s); }
    public void setLate_fee(String s) { setAttribute("latefee", s); }
    public void setCap_fee(String s) { setAttribute("capfee", s); }
    public void setProg_fee(String s) { setAttribute("capfee", s); }
}