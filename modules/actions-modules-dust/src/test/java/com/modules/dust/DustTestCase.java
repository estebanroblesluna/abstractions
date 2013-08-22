package com.modules.dust;

import junit.framework.TestCase;

import com.core.api.Message;
import com.core.utils.ExpressionUtils;

public class DustTestCase extends TestCase {

	public void testDust() {
		DustConnector connector = new DustConnector();
		connector.putTemplate("welcome", "Hello {name}! You have {count} new messages.");
		
		DustRendererProcessor processor = new DustRendererProcessor();
		processor.setTemplate(ExpressionUtils.groovy("'Hello {name}! You have {count} new messages.'"));
		processor.setJsonData(ExpressionUtils.groovy("'{\"name\": \"Mick\",\"count\": 30}'"));
		processor.setConnector(connector);
		
		Message message = new Message();
		processor.process(message);
		
		assertEquals("Hello Mick! You have 30 new messages.", message.getPayload());
	}
}
