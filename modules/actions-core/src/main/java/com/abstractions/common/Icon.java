package com.abstractions.common;

import java.io.Serializable;

/**
 * 
 * @author Guido J. Celada (celadaguido@gmail.com)
 */
public class Icon implements Serializable {

  private static final long serialVersionUID = 1L;

  private long id;
  private byte[] image;

  protected Icon() {
  }

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
   * @param image
   *          the image to set
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
   * @param id
   *          the id to set
   */
  public void setId(long id) {
    this.id = id;
  }
}
