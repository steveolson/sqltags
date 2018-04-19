// Decompiled by DJ v3.5.5.77 Copyright 2003 Atanas Neshkov  Date: 3/11/2004 3:03:46 PM
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ApprovedTag.java

package com.aitworks.creditcard.skipjack;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

// Referenced classes of package com.aitworks.creditcard.skipjack:
//            SkipjackTag, SkipjackBean

public class ApprovedTag extends TagSupport
{

    public ApprovedTag()
    {
    }

    public int doStartTag()
        throws JspTagException
    {
        SkipjackTag vtag = (SkipjackTag)TagSupport.findAncestorWithClass(this, com.aitworks.creditcard.skipjack.SkipjackTag.class);
        if(vtag == null)
            throw new JspTagException("missing SkipjackTag");
        return !vtag.isApproved() ? 0 : 1;
    }
}