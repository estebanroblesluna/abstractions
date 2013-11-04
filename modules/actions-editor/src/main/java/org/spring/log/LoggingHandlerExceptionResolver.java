package org.spring.log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.*;
import org.springframework.web.servlet.*;

/**
 *This class logs any errors of spring to the console
 * 
 * @author Guido J. Celada
 */
public class LoggingHandlerExceptionResolver 
implements HandlerExceptionResolver, Ordered {
    
    @Override
    public int getOrder() {
        return Integer.MIN_VALUE; 
    }

    @Override
    public ModelAndView resolveException(
        HttpServletRequest aReq, HttpServletResponse aRes,
        Object aHandler, Exception anExc
    ) {
        anExc.printStackTrace(); 
        return null; // trigger other HandlerExceptionResolver's
    }
}
