/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * @author "Esteban Robles Luna <esteban.roblesluna@gmail.com>"
 */
@implementation CreateNextInChainTool : AbstractCreateConnectionTool
{
}

+ (id) drawing: (Drawing) aDrawing
{
	return [super drawing: aDrawing figure: [NextInChainConnection class]];
}

- (id) acceptsNewStartingConnection: (id) aFigure
{
	if (aFigure == nil) {
		return false;
	}
	
	if (![aFigure isKindOfClass: [ElementFigure class]]) {
		CPLog.debug("[CreateNextInChainTool] Not a processor");
		return false;
	}

	var acceptsNewStartingChain = [aFigure acceptsNewStartingChain];
	if (acceptsNewStartingChain) {
		CPLog.debug("[CreateNextInChainTool] Accepts new starting chain");
	} else {
		CPLog.debug("[CreateNextInChainTool] NOT Accepts new starting chain");
	}
		
	return acceptsNewStartingChain;
}

- (id) acceptsNewEndingConnection: (id) aFigure
{
	if (aFigure == nil) {
		return false;
	}
	
	if (![aFigure isKindOfClass: [ElementFigure class]]) {
		CPLog.debug("[CreateNextInChainTool] Not a processor");
		return false;
	}

	var acceptsNewEndingChain = [aFigure acceptsNewEndingChain];
	if (acceptsNewEndingChain) {
		CPLog.debug("[CreateNextInChainTool] Accepts new ending chain");
	} else {
		CPLog.debug("[CreateNextInChainTool] NOT Accepts new ending chain");
	}
		
	return acceptsNewEndingChain;
}

@end