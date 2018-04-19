// Decompiled by DJ v3.5.5.77 Copyright 2003 Atanas Neshkov  Date: 3/11/2004 3:04:29 PM
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   SkipjackExtraInfo.java

package com.aitworks.creditcard.skipjack;

import javax.servlet.jsp.tagext.*;

public final class SkipjackExtraInfo extends TagExtraInfo
{

    public SkipjackExtraInfo()
    {
    }

    protected void finalize()
        throws Throwable
    {
        super.finalize();
    }

    public VariableInfo[] getVariableInfo(TagData value)
    {
        return (new VariableInfo[] {
            new VariableInfo(value.getId(), "SkipjackTag", true, 0)
        });
    }

    public String toString()
    {
        String cr = "\n";
        StringBuffer buffer = new StringBuffer(cr + "*****SkipjackExtraInfo: " + cr);
        return buffer.toString();
    }
}