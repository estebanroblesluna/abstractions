package org.spring.log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.*;
import org.springframework.web.servlet.*;

/**
 *This class logs any errors of spring to the console
 * 
 * @author Guido J. Celada (celadaguido@gmail.com)
 */
public class LoggingHandlerExceptionResolver 
implements HandlerExceptionResolver, Ordered {
    
    private static final Log log = LogFactory.getLog(LoggingHandlerExceptionResolver.class);
    
    @Override
    public int getOrder() {
        return Integer.MIN_VALUE; 
    }

    @Override
    public ModelAndView resolveException(
        HttpServletRequest aReq, HttpServletResponse aRes,
        Object aHandler, Exception anExc
    ) {
        log.error("Error in the request: ", anExc);
        return null; // trigger other HandlerExceptionResolver's
    }
}
