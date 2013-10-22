package com.core.interpreter;

import java.util.Collections;
import java.util.List;

import com.abstractions.clazz.core.ObjectClazz;
import com.core.impl.ConnectionType;
import com.service.core.BeanUtils;

public class ChainRouterEvaluator implements Evaluator {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void evaluate(Thread thread) {
		ObjectClazz chainRouter = thread.getCurrentObjectDefinition();
		
		String connections = chainRouter.getProperty("__connections" + ConnectionType.CHAIN_CONNECTION.getElementName());
		List<String> urns = BeanUtils.getUrnsFromList(connections);
		Collections.reverse(urns);
		
		//push all the elements in reverse order
		for (String urn : urns) {
			ObjectClazz inChain = thread.getContext().resolve(urn);
			if (inChain != null) {
				ObjectClazz target = thread.getContext().resolve(inChain.getProperty("target"));
				thread.pushCurrentContext();
				thread.setCurrentElement(target);
			}
		}
	}
}
