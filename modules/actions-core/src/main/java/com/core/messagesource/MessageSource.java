package com.core.messagesource;

import com.core.api.Element;
import com.core.api.Identificable;

public interface MessageSource extends Identificable, Element {

	void addListener(MessageSourceListener listener);
	
	void removeListener(MessageSourceListener listener);
	
	void setMainListener(MessageSourceListener listener);
}
