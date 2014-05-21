package com.abstractions.service.core;

import java.util.Map;

public interface PropertiesLoader {

  Map<String, String> loadPropertiesOf(long applicationId);
}
