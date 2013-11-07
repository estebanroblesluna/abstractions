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
	
	@outlet id _beforeConditionalContainerTF;
	@outlet id _afterConditionalContainerTF;
	@outlet id _beforeContainerTF;
	@outlet id _afterContainerTF;
	
	@outlet id _isBeforeConditionalCB;
	@outlet id _isAfterConditionalCB;

	id _beforeConditionalTF;
	id _afterConditionalTF;
	id _beforeTF;
	id _afterTF;
}

- (void) awakeFromCib
{
	_beforeConditionalTF = [ActionsDrawing createMultilineText: [_beforeConditionalContainerTF frame]];
	_afterConditionalTF = [ActionsDrawing createMultilineText: [_afterConditionalContainerTF frame]];
	_beforeTF = [ActionsDrawing createMultilineText: [_beforeContainerTF frame]];
	_afterTF = [ActionsDrawing createMultilineText: [_afterContainerTF frame]];

	[_beforeConditionalContainerTF addSubview: _beforeConditionalTF];
	[_afterConditionalContainerTF addSubview: _afterConditionalTF];
	[_beforeContainerTF addSubview: _beforeTF];
	[_afterContainerTF addSubview: _afterTF];
}

- (IBAction) add: (id) aSender
{
	var beforeExpressionValue = [_beforeTF stringValue];
	var afterExpressionValue = [_afterTF stringValue];
	var isBeforeConditional = [_isBeforeConditionalCB stringValue] == "1";
	var isAfterConditional = [_isAfterConditionalCB stringValue] == "1";
	var beforeConditionalExpressionValue = isBeforeConditional ? [_beforeConditionalTF stringValue] : nil;
	var afterConditionalExpressionValue = isAfterConditional ? [_afterConditionalTF stringValue] : nil;
	
	[[[_window windowController] elementAPI]
		addLogger: beforeExpressionValue
		afterExpression: afterExpressionValue
		beforeConditionalExpressionValue: beforeConditionalExpressionValue
		afterConditionalExpressionValue: afterConditionalExpressionValue];
	
	[_window close];
}
@end