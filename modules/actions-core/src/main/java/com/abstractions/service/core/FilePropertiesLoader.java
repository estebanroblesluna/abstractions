package com.abstractions.service.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FilePropertiesLoader implements PropertiesLoader {

  private static final Log log = LogFactory.getLog(FilePropertiesLoader.class);

  private final String baseDirectory;

  public FilePropertiesLoader(String baseDirectory) {
    Validate.notNull(baseDirectory);
    
    this.baseDirectory = baseDirectory;
  }

  @Override
  public Map<String, String> loadPropertiesOf(long applicationId) {
    File mappingFile = new File(this.baseDirectory, applicationId + "/properties");
    Properties properties = new Properties();
    FileInputStream fileIO = null;

    try {
      fileIO = new FileInputStream(mappingFile);
      properties.load(fileIO);
    } catch (FileNotFoundException e) {
      log.warn("Error reading properties file " + StringUtils.defaultString(mappingFile.getAbsolutePath()), e);
    } catch (IOException e) {
      log.warn("Error reading properties file " + StringUtils.defaultString(mappingFile.getAbsolutePath()), e);
    } finally {
      IOUtils.closeQuietly(fileIO);
    }
    
    Map<String, String> result = new HashMap<String, String>();
    for (Map.Entry<Object, Object> entry : properties.entrySet()) {
      result.put(entry.getKey().toString(), entry.getValue().toString());
    }
    
    return result;
  }
}
