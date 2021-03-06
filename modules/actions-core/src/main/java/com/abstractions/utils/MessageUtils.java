package com.abstractions.utils;

public class MessageUtils {

	public static final String BASE_PROPERTY = "actions";
	
	public static final String HTTP_BASE_PROPERTY = BASE_PROPERTY + ".http";
	public static final String FILE_BASE_PROPERTY = BASE_PROPERTY + ".file";

	public static final String APPLICATION_BASE_PROPERTY = BASE_PROPERTY + ".application";
  public static final String PROPERTY_BASE = APPLICATION_BASE_PROPERTY + ".properties.";

	public static final String APPLICATION_ID_PROPERTY = APPLICATION_BASE_PROPERTY + ".id";
	public static final String APPLICATION_CDN_PROPERTY = APPLICATION_BASE_PROPERTY + ".cdn";
	public static final String APPLICATION_CLOUDFRONT_SECRET = APPLICATION_CDN_PROPERTY + ".aws_secret";
	public static final String APPLICATION_CLOUDFRONT_ACCESS = APPLICATION_CDN_PROPERTY + ".aws_access";
	public static final String APPLICATION_PROPERTY_BASE_PROPERTY = APPLICATION_BASE_PROPERTY + ".property";

  public static final String CDN_DUMMY_RESOURCE_NAME = "_ok";

  public static final String HTTP_REQUEST_URL = HTTP_BASE_PROPERTY + ".requestURL";

  public static final String HTTP_REQUEST_PROPERTY = HTTP_BASE_PROPERTY + "._request";
  public static final String HTTP_RESPONSE_PROPERTY = HTTP_BASE_PROPERTY + "._response";
}
