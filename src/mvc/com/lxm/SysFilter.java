package com.lxm;
import javax.servlet.*;
import java.io.*;

public class SysFilter implements Filter {   
    protected String sEncodingName;   

    protected FilterConfig sysFilter;   

    public void destroy() {   

    }   

    public void doFilter(ServletRequest arg0, ServletResponse arg1,   
            FilterChain arg2) throws IOException, ServletException {   
        // TODO Auto-generated method stub   
            try {     
                arg0.setCharacterEncoding(this.sEncodingName);   
                arg1.setContentType("text/html;charset=" + this.sEncodingName);   
                arg1.setCharacterEncoding(this.sEncodingName);   
                arg2.doFilter(arg0, arg1);   
            } catch (Exception e) {   
            //....
            }  finally{
                 arg2.doFilter(arg0, arg1);   
            }
    }   

    public void init(FilterConfig arg0) throws ServletException {   
        // TODO Auto-generated method stub   
        this.sysFilter = arg0;   
        this.sEncodingName = this.sysFilter.getInitParameter("encoding");   
    }   
}   