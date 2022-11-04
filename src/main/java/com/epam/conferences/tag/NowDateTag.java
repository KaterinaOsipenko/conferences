package com.epam.conferences.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.time.LocalDateTime;

public class NowDateTag extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter jspWriter = pageContext.getOut();
        try {
            jspWriter.print(LocalDateTime.now());
        } catch (IOException e) {
            e.getMessage();
        }
        return SKIP_BODY;
    }
}
