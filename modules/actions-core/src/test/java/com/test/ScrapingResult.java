package com.test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScrapingResult implements Serializable
{
  private String      title;
  private double      titleScore;

  private String      description;
  private double      descriptionScore;

  private String      fullText;
  private double      fullTextScore;

  private List<Image> images;

  public ScrapingResult()
  {
    this.titleScore = 0;
    this.descriptionScore = 0;
    this.fullTextScore = 0;

    this.images = new ArrayList<Image>();
  }

  public ScrapingResult merge(ScrapingResult otherResult)
  {
    ScrapingResult result = new ScrapingResult();
    
    result.title = this.titleScore > otherResult.titleScore ? this.title : otherResult.title;
    result.titleScore = Math.max(this.titleScore, otherResult.titleScore);

    result.description = this.descriptionScore > otherResult.descriptionScore ? this.description : otherResult.description;
    result.descriptionScore = Math.max(this.descriptionScore, otherResult.descriptionScore);

    result.fullText = this.fullTextScore > otherResult.fullTextScore ? this.fullText : otherResult.fullText;
    result.fullTextScore = Math.max(this.fullTextScore, otherResult.fullTextScore);

    //TODO merge images
    
    return result;
  }
  
  public String getTitle()
  {
    return title;
  }

  public double getTitleScore()
  {
    return titleScore;
  }

  public String getDescription()
  {
    return description;
  }

  public double getDescriptionScore()
  {
    return descriptionScore;
  }

  public String getFullText()
  {
    return fullText;
  }

  public double getFullTextScore()
  {
    return fullTextScore;
  }

  public List<Image> getImages()
  {
    return Collections.unmodifiableList(this.images);
  }

  public void setTitle(String title)
  {
    this.title = title;
  }

  public void setTitleScore(double titleScore)
  {
    this.titleScore = titleScore;
  }

  public void setDescription(String description)
  {
    this.description = description;
  }

  public void setDescriptionScore(double descriptionScore)
  {
    this.descriptionScore = descriptionScore;
  }

  public void setFullText(String fullText)
  {
    this.fullText = fullText;
  }

  public void setFullTextScore(double fullTextScore)
  {
    this.fullTextScore = fullTextScore;
  }
}
