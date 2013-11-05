package com.abstractions.common;

import java.io.Serializable;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 *
 * @author Guido J. Celada
 */
public class Icon implements Serializable {
    
    private long id;
    private byte[] image;
    
    public Icon() {} //Hibernate needs this
    
    public Icon(byte[] image) {
       this.setImage(image);
    }

    /**
     * @return the image
     */
    public byte[] getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(byte[] image) {
        this.image = image;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

}
