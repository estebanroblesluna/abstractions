package com.abstractions.runtime.interpreter;

import java.util.Collections;
import java.util.List;

import com.abstractions.instance.core.ConnectionType;
import com.abstractions.service.core.BeanUtils;
import com.abstractions.template.ElementTemplate;

public class ChainRouterEvaluator implements Evaluator {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void evaluate(Thread thread) {
		ElementTemplate chainRouter = thread.getCurrentObjectDefinition();
		
		String connections = chainRouter.getProperty("__connections" + ConnectionType.CHAIN_CONNECTION.getElementName());
		List<String> urns = BeanUtils.getUrnsFromList(connections);
		Collections.reverse(urns);
		
		//push all the elements in reverse order
		for (String urn : urns) {
			ElementTemplate inChain = thread.getComposite().resolve(urn);
			if (inChain != null) {
				ElementTemplate target = thread.getComposite().resolve(inChain.getProperty("target"));
				thread.pushCurrentContext();
				thread.setCurrentElement(target);
			}
		}
	}
}
