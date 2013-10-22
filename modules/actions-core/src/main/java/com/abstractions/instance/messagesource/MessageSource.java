package com.abstractions.instance.messagesource;

import com.abstractions.api.Element;
import com.abstractions.api.Identificable;

public interface MessageSource extends Identificable, Element {

	void addListener(MessageSourceListener listener);
	
	void removeListener(MessageSourceListener listener);
	
	void setMainListener(MessageSourceListener listener);
}
