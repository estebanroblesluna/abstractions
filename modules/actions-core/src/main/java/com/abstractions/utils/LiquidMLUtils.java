package com.abstractions.utils;

import org.hibernate.service.spi.Stoppable;

import com.abstractions.api.Startable;
import com.abstractions.api.Terminable;

public class LiquidMLUtils {

  public static void start(Object o) {
    if (o instanceof Startable) {
      ((Startable) o).start();
    }
  }

  public static void stop(Object o) {
    if (o instanceof Stoppable) {
      ((Stoppable) o).stop();
    }
  }

  public static void terminate(Object o) {
    if (o instanceof Terminable) {
      ((Terminable) o).terminate();
    }
  }
}
