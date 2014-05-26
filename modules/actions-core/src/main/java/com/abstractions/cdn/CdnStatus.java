package com.abstractions.cdn;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;

import com.abstractions.http.HttpStrategy;
import com.abstractions.utils.MessageUtils;

public class CdnStatus {

  private Boolean up;
  private String cdnUrl;
  private HttpStrategy strategy;
  private long applicationId;
  
  private static Log log = LogFactory.getLog(CdnStatus.class);


  public CdnStatus(long applicationId, HttpStrategy httpStrategy, String cdnUrl) {
    super();
    this.applicationId = applicationId;
    this.up = false;
    this.cdnUrl = cdnUrl;
    this.strategy = httpStrategy;
  }

  public Boolean getUp() {
    return up;
  }

  public String getCdnUrl() {
    return cdnUrl;
  }
  
  public void update() {
    if (this.up){
      return;
    }
    log.info("Checkikng CDN status for application " + this.applicationId + 
            "(" + this.cdnUrl + ")");
    HttpResponse response = null;
    try {
      response = this.strategy
        .get(this.cdnUrl + "/" + MessageUtils.CDN_DUMMY_RESOURCE_NAME)
        .execute();
    } catch (Exception e) { }
    if (response != null && response.getStatusLine().getStatusCode() == 200) {
      log.info("CDN for application " + this.applicationId + " is up");
      this.up = true;
      return;
    }
    log.info("CDN for application " + this.applicationId + " is still down");
  }
  
  public boolean isUp() {
    return this.up;
  }
  
  public long getApplicationId() {
    return this.applicationId;
  }

}
