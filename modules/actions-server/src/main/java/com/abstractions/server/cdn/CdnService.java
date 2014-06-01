package com.abstractions.server.cdn;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import com.abstractions.cdn.CdnStatus;
import com.abstractions.cdn.CdnStatusManager;
import com.abstractions.http.HttpStrategy;
import com.abstractions.server.core.ActionsServer;
import com.abstractions.service.core.PropertiesLoader;
import com.abstractions.utils.MessageUtils;

public class CdnService {

  private PropertiesLoader propertiesLoader;
  private ActionsServer actionServer;
  private CdnStatusManager cdnStatusManager;
  private HttpStrategy httpStrategy;
  
  public CdnService(HttpStrategy httpStrategy, PropertiesLoader propertiesLoader, ActionsServer actionServer, CdnStatusManager cdnStatusManager) {
    super();
    this.propertiesLoader = propertiesLoader;
    this.actionServer = actionServer;
    this.cdnStatusManager = cdnStatusManager;
    this.httpStrategy = httpStrategy;
  }
  
  public void update() throws ClientProtocolException, IOException {
    for (long id : this.actionServer.getApplicationIds()) {
      String cdnUrl = this.propertiesLoader.loadPropertiesOf(id).get(MessageUtils.APPLICATION_CDN_PROPERTY);
      if (cdnUrl != null) {
        this.cdnStatusManager.addCdnStatus(new CdnStatus(id, this.httpStrategy, cdnUrl));
      }
    }
    
    this.cdnStatusManager.update();
  }  
}
