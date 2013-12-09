package com.abstractions.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Guido J. Celada
 */
@Controller
public class SimpleRedirectController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String redirect() {
        return "redirect:/teams/";
    }
}
