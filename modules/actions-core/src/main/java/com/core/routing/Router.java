package com.core.routing;

import com.core.api.Element;

public interface Router extends Element {

	Object accept (RouterVisitor visitor);
}
