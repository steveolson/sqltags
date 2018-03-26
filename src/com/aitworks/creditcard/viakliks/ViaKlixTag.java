/* $Id: ViaKlixTag.java,v 1.2 2002/05/03 19:14:20 jpoon Exp $
 * $Log: ViaKlixTag.java,v $
 * Revision 1.2  2002/05/03 19:14:20  jpoon
 * add settes
 *
 * Revision 1.1  2002/05/03 06:22:09  solson
 * new Tags
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
 */
package com.aitworks.creditcard.viakliks;
import java.util.*;
import javax.servlet.jsp.tagext.TagSupport;

/**
 *
 * @author  solson
 */
public class ViaKlixTag extends ViaKlixBean {

    /** Creates a new instance of ViaKlixTag */
    public ViaKlixTag() {
        super();
    }
    public int doStartTag(){ 
        pageContext.setAttribute(getId(),this);

        Enumeration enum = pageContext.getRequest().getParameterNames();
        while(enum.hasMoreElements()){
            String param = (String)enum.nextElement();
            if( ( param.startsWith("ssl_"))
                && getAttribute(param)==null) 
            {
                setAttribute(param,pageContext.getRequest().getParameter(param));
            }
        }
        
        try{
            execute();
            return EVAL_BODY_INCLUDE;
        }
        catch(ViaKlixBeanException e){
            System.out.println("Yep, got an Exception: "+e);
            return EVAL_BODY_INCLUDE;
        }
    }// doStartTag() ENDS 
    
   
    //
    // OK a big bunch of SETTERS ... ouch!
    //
    // public string getId(){ return getAttribute("id");}
    // public void setId(String id){setAttribute("id",id);}
    public void setSsl_merchant_id(String s){setAttribute("ssl_merchant_id",s);}
    public void setSsl_pin(String s){setAttribute("ssl_pin",s);}
    public void setSsl_amount(String s){setAttribute("ssl_amount",s);}
    public void setSsl_invoice_number(String s){setAttribute("ssl_invoice_number",s);}
    public void setSsl_salestax(String s){setAttribute("ssl_salestax",s);}
    public void setSsl_test_mode(String s){setAttribute("ssl_test_mode",s);}
    public void setSsl_transaction_type(String s){setAttribute("ssl_transaction_type",s);}
    public void setSsl_card_number(String s){setAttribute("ssl_card_number",s);}
    public void setSsl_exp_date(String s){setAttribute("ssl_exp_date",s);}
    public void setSsl_description(String s){setAttribute("ssl_description",s);}
    public void setSsl_company(String s){setAttribute("ssl_company",s);}
    public void setSsl_first_name(String s){setAttribute("ssl_first_name",s);}
    public void setSsl_last_name(String s){setAttribute("ssl_last_name",s);}
    public void setSsl_avs_address(String s){setAttribute("ssl_avs_address",s);}
    public void setSsl_address2(String s){setAttribute("ssl_address2",s);}
    public void setSsl_city(String s){setAttribute("ssl_city",s);}
    public void setSsl_state(String s){setAttribute("ssl_state",s);}
    public void setSsl_country(String s){setAttribute("ssl_country",s);}
    public void setSsl_phone(String s){setAttribute("ssl_phone",s);}
    public void setSsl_email(String s){setAttribute("ssl_email",s);}
    public void setSsl_avs_zip(String s){setAttribute("ssl_avs_zip",s);}    
    public void setSsl_ship_to_company(String s){setAttribute("ssl_ship_to_company",s);}
    public void setSsl_ship_to_first_name(String s){setAttribute("ssl_ship_to_first_name",s);}
    public void setSsl_ship_to_last_name(String s){setAttribute("ssl_ship_to_last_name",s);}
    public void setSsl_ship_to_address(String s){setAttribute("ssl_ship_to_address",s);}
    public void setSsl_ship_to_city(String s){setAttribute("ssl_ship_to_city",s);}
    public void setSsl_ship_to_state(String s){setAttribute("ssl_ship_to_state",s);}
    public void setSsl_ship_to_zip(String s){setAttribute("ssl_ship_to_zip",s);}
    public void setSsl_ship_to_country(String s){setAttribute("ssl_ship_to_country",s);}    
    public void setSsl_avs_response(String s){setAttribute("ssl_avs_response",s);}
    public void setSsl_show_form(String s){setAttribute("ssl_show_form",s);}
    public void setSsl_header_html(String s){setAttribute("ssl_header_html",s);}
    public void setSsl_footer_html(String s){setAttribute("ssl_footer_html",s);}
    public void setSsl_result(String s){setAttribute("ssl_result",s);}
    public void setSsl_result_message(String s){setAttribute("ssl_result_message",s);}
    public void setSsl_txt_id(String s){setAttribute("ssl_txn_id",s);}
    public void setSsl_approval_code(String s){setAttribute("ssl_approval_code",s);}
    public void setSsl_result_format(String s){setAttribute("ssl_result_format",s);}
    public void setSsl_receipt_header_html(String s){setAttribute("ssl_receipt_header_html",s);}
    public void setSsl_receipt_footer_html(String s){setAttribute("ssl_receipt_footer_html",s);}
    public void setSsl_receipt_link_method(String s){setAttribute("ssl_receipt_link_method",s);}
    public void setSsl_receipt_link_url(String s){setAttribute("ssl_receipt_link_url",s);}
    public void setSsl_receipt_link_text(String s){setAttribute("ssl_receipt_link_text",s);}
    public void setSsl_email_header(String s){setAttribute("ssl_email_header",s);}
    public void setSsl_email_footer(String s){setAttribute("ssl_email_footer",s);}
    public void setSsl_do_customer_email(String s){setAttribute("ssl_do_customer_email",s);}
    public void setSsl_customer_code(String s){setAttribute("ssl_customer_code",s);}
    public void setSsl_do_merchant_email(String s){setAttribute("ssl_do_merchant_email",s);}
    public void setSsl_merchant_email(String s){setAttribute("ssl_merchant_email",s);}
    public void setSsl_header_color(String s){setAttribute("ssl_header_color",s);}
    public void setSsl_text_color(String s){setAttribute("ssl_text_color",s);}
    public void setSsl_background_color(String s){setAttribute("ssl_background_color",s);}
    public void setSsl_table_color(String s){setAttribute("ssl_table_color",s);}
    public void setSsl_cvv2(String s){setAttribute("ssl_cvv2",s);}
    public void setSsl_cvv2cvc2(String s){setAttribute("ssl_cvv2cvc2",s);}
    public void setSsl_hcyp_sport(String s){setAttribute("ssl_hcyp_sport",s);}
    public void setSsl_hcyp_registration_nbr(String s){setAttribute("ssl_hcyp_registration_nbr",s);}
}
