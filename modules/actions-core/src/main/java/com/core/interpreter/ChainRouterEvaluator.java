package com.core.interpreter;

import java.util.Collections;
import java.util.List;

import com.core.impl.ConnectionType;
import com.service.core.BeanUtils;
import com.service.core.ObjectDefinition;

public class ChainRouterEvaluator implements Evaluator {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void evaluate(Thread thread) {
		ObjectDefinition chainRouter = thread.getCurrentObjectDefinition();
		
		String connections = chainRouter.getProperty("__connections" + ConnectionType.CHAIN_CONNECTION.getElementName());
		List<String> urns = BeanUtils.getUrnsFromList(connections);
		Collections.reverse(urns);
		
		//push all the elements in reverse order
		for (String urn : urns) {
			ObjectDefinition inChain = thread.getContext().resolve(urn);
			if (inChain != null) {
				ObjectDefinition target = thread.getContext().resolve(inChain.getProperty("target"));
				thread.pushCurrentContext();
				thread.setCurrentElement(target);
			}
		}
	}
}
