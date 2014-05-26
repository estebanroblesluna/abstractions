package com.abstractions.cdn;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;


public class CdnStatusManager {

  private Map<Long, CdnStatus> cdnUpForApplication;

  public CdnStatusManager() {
    this.cdnUpForApplication = new HashMap<Long, CdnStatus>();
  }
  
  public void update() throws ClientProtocolException, IOException {
    for (long id : this.cdnUpForApplication.keySet()) {
      this.cdnUpForApplication.get(id).update();
    }
  }
  
  public String getCdnForApplication(long applicationId) {
    CdnStatus cdnStatus = this.cdnUpForApplication.get(applicationId);
    if (cdnStatus == null) {
      return null;
    }
    if (cdnStatus.isUp()) {
      return cdnStatus.getCdnUrl();
    } 
    return null;
  }

  public void addCdnStatus(CdnStatus cdnStatus) {
    this.cdnUpForApplication.put(cdnStatus.getApplicationId(), cdnStatus);
  }  
  
}
