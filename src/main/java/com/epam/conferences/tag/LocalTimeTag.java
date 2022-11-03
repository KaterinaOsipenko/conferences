package com.epam.conferences.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.time.LocalDateTime;

public class LocalTimeTag extends TagSupport {

    private LocalDateTime time;

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public int doStartTag() throws JspException {
        JspWriter jspWriter = pageContext.getOut();
        try {
            StringBuilder min = new StringBuilder(String.valueOf(time.toLocalTime().getMinute()));
            if (min.length() == 1) {
                min.append("0");
            }
            jspWriter.print(time.toLocalTime().getHour() + ":" + min);
        } catch (IOException e) {
            e.getMessage();
        }
        return SKIP_BODY;
    }
}
