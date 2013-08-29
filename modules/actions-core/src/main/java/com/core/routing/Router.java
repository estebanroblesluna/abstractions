package com.core.routing;

import com.core.api.Element;
import com.core.api.Evaluable;

public interface Router extends Element, Evaluable {

	Object accept (RouterVisitor visitor);
}
