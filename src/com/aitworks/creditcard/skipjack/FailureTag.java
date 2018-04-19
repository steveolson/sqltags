// Decompiled by DJ v3.5.5.77 Copyright 2003 Atanas Neshkov  Date: 3/11/2004 3:45:16 PM
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   FailureTag.java

package com.aitworks.creditcard.skipjack;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

// Referenced classes of package com.aitworks.creditcard.skipjack:
//            SkipjackTag, SkipjackBean

public class FailureTag extends TagSupport
{

    public FailureTag()
    {
    }

    public int doStartTag()
        throws JspTagException
    {
        SkipjackTag vtag = (SkipjackTag)TagSupport.findAncestorWithClass(this, com.aitworks.creditcard.skipjack.SkipjackTag.class);
        if(vtag == null)
            throw new JspTagException("missing SkipjackTag");
        return !vtag.isFailure() ? 0 : 1;
    }
}