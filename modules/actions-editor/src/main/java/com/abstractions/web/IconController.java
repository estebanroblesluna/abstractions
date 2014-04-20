package com.abstractions.web;

import com.abstractions.common.Icon;
import com.abstractions.service.IconService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Guido J. Celada (celadaguido@gmail.com)
 */
@Controller
@RequestMapping("/icon")
public class IconController {
    
    @Autowired
    IconService service;
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody 
    public byte[] show(@PathVariable("id") long id) {
        Icon icon = this.service.get(id);
		return icon.getImage();
	}
}
