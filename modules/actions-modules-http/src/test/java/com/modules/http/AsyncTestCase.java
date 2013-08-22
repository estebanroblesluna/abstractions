package com.modules.http;

import junit.framework.TestCase;

import org.eclipse.jetty.servlet.ServletHolder;
import org.modules.jetty.JettyHttpServer;

public class AsyncTestCase extends TestCase
{
  private JettyHttpServer server;
  
  @Override
  protected void setUp() throws Exception
  {
    super.setUp();
    server = new JettyHttpServer(1234);
  }

  @Override
  protected void tearDown() throws Exception
  {
    super.tearDown();
    server.stop();
  }
  
  public void testAsync() throws Exception
  {
    ServletHolder holder = server.addServlet(HttpReceiver.class, "/receiver");
    server.start();

    HttpReceiver receiver = (HttpReceiver) holder.getServlet();
  }
}
