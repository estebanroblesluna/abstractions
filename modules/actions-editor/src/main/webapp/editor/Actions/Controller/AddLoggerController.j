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

@import <Foundation/CPObject.j>

/**
 * @author "Esteban Robles Luna <esteban.roblesluna@gmail.com>"
 */
@implementation AddLoggerController : CPObject 
{
	@outlet CPWindow _window;
	@outlet CPTextField _beforeExpression;
	@outlet CPTextField _afterExpression;
}

- (IBAction) add: (id) aSender
{
	var beforeExpressionValue = [_beforeExpression stringValue];
	var afterExpressionValue = [_afterExpression stringValue];
	
	[[[_window windowController] elementAPI]
		addLogger: beforeExpressionValue
		afterExpression: afterExpressionValue];
	
	[_window close];
}
@end