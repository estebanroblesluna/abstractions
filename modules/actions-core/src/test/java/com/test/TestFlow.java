package com.test;

import junit.framework.TestCase;

import com.abstractions.api.Processor;

public class TestFlow extends TestCase
{
//  private KeyStore store;
  private Processor processor;
  
  public void setUp()
  {
//    this.processor = this.buildFlow();
  }
  
  public void test() {
	  
  }
  
//  public void atestFlowStatus()
//  {
//    Message message = new Message();
//    message.putProperty("path", "/status");
//    
//    Message response = processor.process(message);
//    
//    assertEquals("OK", response.getPayload());
//  }
//  
//  public void atestFlowVersion()
//  {
//    Message message = new Message();
//    message.putProperty("path", "/version");
//    
//    Message response = processor.process(message);
//    
//    assertEquals("0.1", response.getPayload());
//  }
//  
//  public void atestFlowQueryText()
//  {
//    String url = "http://www.nbc.com/";
//    Message message = new Message();
//    message.putProperty("path", "/query");
//    message.putProperty("url", url);
//    
//    Message response = processor.process(message);
//    
//    String md5 = DigestUtils.md5Hex(url);
//    assertEquals(md5, response.getProperty("urlHash"));
//    assertNull(response.getProperty("cachedVersion"));
//    assertNull(response.getProperty("storedVersion"));
//    assertNotNull(this.store.get(md5));
//    
//    ScrapingResult result = (ScrapingResult) response.getPayload();
//    assertNotNull(result.getTitle());
//    assertNotNull(result.getDescription());
//    assertNotNull(result.getFullText());
//  }
//  
//  public void atestFlowQueryText2()
//  {
//    String url = "http://www.nytimes.com/2013/02/01/us/politics/sharp-exchanges-expected-in-hearing-on-hagel-nomination.html?hp&_r=0";
//    Message message = new Message();
//    message.putProperty("path", "/query");
//    message.putProperty("url", url);
//    
//    Message response = processor.process(message);
//    
//    String md5 = DigestUtils.md5Hex(url);
//    assertEquals(md5, response.getProperty("urlHash"));
//    assertNull(response.getProperty("cachedVersion"));
//    assertNull(response.getProperty("storedVersion"));
//    assertNotNull(this.store.get(md5));
//    
//    ScrapingResult result = (ScrapingResult) response.getPayload();
//    assertNotNull(result.getTitle());
//    assertNotNull(result.getDescription());
//    assertNotNull(result.getFullText());
//  }
//  
//  public void atestFlowQueryImage()
//  {
//    String url = "http://www.google.com/logos/2013/jackie_robinsons_94th_birthday-1015005-hp.jpg";
//    Message message = new Message();
//    message.putProperty("path", "/query");
//    message.putProperty("url", url);
//    
//    Message response = processor.process(message);
//    
//    ImageInfo result = (ImageInfo) response.getPayload();
//    assertEquals(205, result.getHeight());
//    assertEquals(371, result.getWidth());
//  }
//  
//  public Processor buildFlow()
//  {
//    this.store = new InMemoryKeyStore();
//    this.cache = new InMemoryCache();
//    //ConditionalProcessor condition = new ConditionalProcessor();
//
//    //status case
//    ScriptingExpression statusExpression = new ScriptingExpression(ScriptingLanguage.GROOVY, "message.properties.path ==~ '^/status'");
//    PayloadReplacerProcessor returnOK = new PayloadReplacerProcessor("OK");
//
//    //version case
//    ScriptingExpression versionExpression = new ScriptingExpression(ScriptingLanguage.GROOVY, "message.properties.path ==~ '^/version'");
//    PayloadReplacerProcessor returnVersion = new PayloadReplacerProcessor("0.1");
//
//    //query case
//    ScriptingExpression queryExpression = new ScriptingExpression(ScriptingLanguage.GROOVY, "message.properties.path ==~ '^/query'");
//    ChainProcessor queryChain = this.getQueryChain();
//    
//    //condition.addCondition(statusExpression, returnOK);
//    //condition.addCondition(versionExpression, returnVersion);
//    //condition.addCondition(queryExpression, queryChain);
//    
//    return null;
//  }
//
//  private ChainProcessor getQueryChain()
//  {
//    ChainProcessor queryChain = new ChainProcessor();
//
//    ScriptingProcessor md5HashProcessor = new ScriptingProcessor(ScriptingLanguage.GROOVY, "org.apache.commons.codec.digest.DigestUtils.md5Hex(message.properties.url)");
//    AddPropertyProcessor urlHashProcessor = new AddPropertyProcessor("urlHash", md5HashProcessor);
//
//    GetCacheProcessor cacheProcessor = new GetCacheProcessor(this.cache, new ScriptingExpression(ScriptingLanguage.GROOVY, "message.properties.urlHash"));
//    AddPropertyProcessor cachedVersionProcessor = new AddPropertyProcessor("cachedVersion", cacheProcessor);
//    
//    ExpressionPayloadReplacerProcessor returnCached = new ExpressionPayloadReplacerProcessor(new ScriptingExpression(ScriptingLanguage.GROOVY, "message.properties.cachedVersion"));
//    //ConditionalProcessor ifInCache = new ConditionalProcessor(new ScriptingExpression(ScriptingLanguage.GROOVY, "message.properties.cachedVersion != null"), returnCached, this.getInStore());
//    
//    queryChain.addProcessor(urlHashProcessor);
//    queryChain.addProcessor(cachedVersionProcessor);
//    //queryChain.addProcessor(ifInCache);
//
//    return queryChain;
//  }
//  
//  private Processor getInStore()
//  {
//    ChainProcessor inStoreChain = new ChainProcessor();
//
//    //recover from store and save into cache
//    ChainProcessor saveCacheAndReturnChain = new ChainProcessor();
//
//    ExpressionPayloadReplacerProcessor returnStored = new ExpressionPayloadReplacerProcessor(new ScriptingExpression(ScriptingLanguage.GROOVY, "message.properties.storedVersion"));
//    PutCacheProcessor putInCache = new PutCacheProcessor(this.cache, new ScriptingExpression(ScriptingLanguage.GROOVY, "message.properties.urlHash"), new ScriptingExpression(ScriptingLanguage.GROOVY, "message.properties.storedVersion"));
//    
//    saveCacheAndReturnChain.addProcessor(putInCache);
//    saveCacheAndReturnChain.addProcessor(returnStored);
//    
//    
//    GetKeyProcessor keyValueProcessor = new GetKeyProcessor(store, new ScriptingExpression(ScriptingLanguage.GROOVY, "message.properties.urlHash"));
//    AddPropertyProcessor storedVersionProcessor = new AddPropertyProcessor("storedVersion", keyValueProcessor);
//    
//    //ConditionalProcessor ifInCache = new ConditionalProcessor(new ScriptingExpression(ScriptingLanguage.GROOVY, "message.properties.storedVersion != null"), saveCacheAndReturnChain, this.getComputeProcessor());
//    
//    
//    inStoreChain.addProcessor(storedVersionProcessor);
//    //inStoreChain.addProcessor(ifInCache);
//    
//    return inStoreChain;
//  }
//  
//  private Processor getComputeProcessor()
//  {
//    ChainProcessor computeChain = new ChainProcessor();
//
//    HttpFetcherProcessor fetch = new HttpFetcherProcessor();
//    fetch.setUrlExpression(new ScriptingExpression(ScriptingLanguage.GROOVY, "message.properties.url"));
//    fetch.setFetchMode(FetchMode.GET);
//    fetch.setStreaming(true);
//    
//    ScriptingExpression imageExpression = new ScriptingExpression(ScriptingLanguage.GROOVY, "message.properties['http.Content-Type'].startsWith('image')");
//    ScriptingExpression textExpression = new ScriptingExpression(ScriptingLanguage.GROOVY, "message.properties['http.Content-Type'].startsWith('text')");
//    ScriptingExpression videoExpression = new ScriptingExpression(ScriptingLanguage.GROOVY, "message.properties['http.Content-Type'].startsWith('video')");
//
//    
//    //ConditionalProcessor contentTypeConditional = new ConditionalProcessor();
//    //contentTypeConditional.addCondition(imageExpression, this.getImageChain());
//    //contentTypeConditional.addCondition(textExpression, this.getTextChain());
//    //contentTypeConditional.addCondition(videoExpression, this.getVideoChain());
//    
//    PutKeyValueProcessor putValue =
//        new PutKeyValueProcessor(store,
//                                 new ScriptingExpression(ScriptingLanguage.GROOVY, "message.properties.urlHash"),
//                                 new ScriptingExpression(ScriptingLanguage.GROOVY, "message.payload"));
//    
//    
//    computeChain.addProcessor(fetch);
//    //computeChain.addProcessor(contentTypeConditional);
//    computeChain.addProcessor(putValue);
//    
//    return computeChain;
//  }
//  
//  private Processor getVideoChain()
//  {
//    ChainProcessor chain = new ChainProcessor();
//    
//    //TODO complete
//    return chain;
//  }
//
//  private Processor getImageChain()
//  {
//    ChainProcessor chain = new ChainProcessor();
//    
//    chain.addProcessor(new ImageReaderProcessor());
//    return chain;
//  }
//
//  private Processor getTextChain()
//  {
//    ChainProcessor chain = new ChainProcessor();
//    
//    AllRouter allScrap = new AllRouter();
//    allScrap.addConnection(new AllConnection(allScrap, this.getOGChain()));
//    allScrap.addConnection(new AllConnection(allScrap, this.getBoilerpipeChain()));
//    allScrap.addConnection(new AllConnection(allScrap, this.getDOMChain()));
//    
//    ScoreAndMergeProcessor scoreAndMerge = new ScoreAndMergeProcessor();
//
//    chain.addProcessor(new ToStringProcessor());
//    chain.addProcessor(allScrap);
//    chain.addProcessor(scoreAndMerge);
//
//    return chain;
//  }
//
//  private Processor getOGChain()
//  {
//    ChainProcessor chain = new ChainProcessor();
//    
//    chain.addProcessor(new JsoupTransformer());
//    chain.addProcessor(new OgTransformer());
//    chain.addProcessor(new OgToScrapeResultTransformer());
//    
//    return chain;
//  }
//
//  private Processor getBoilerpipeChain()
//  {
//    ChainProcessor chain = new ChainProcessor();
//    
//    chain.addProcessor(new BoilerpipeTextProcessor());
//    chain.addProcessor(new BoilerpipeResultTransformer());
//    
//    return chain;
//  }
//  
//  private Processor getDOMChain()
//  {
//    ChainProcessor chain = new ChainProcessor();
//
//    chain.addProcessor(new JsoupTransformer());
//    return chain;
//  }
}
