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
@implementation HttpMessageSourceModel : ElementModel
{
	id _state;
}

- (id) init
{
	[super init];
	
	CPLog.debug("[HttpMessageSourceModel] Adding properties of HttpMessageSourceModel");
	[self addProperty: @"port" displayName:@"Port" value: @"8811"];
	[self addProperty: @"timeoutExpression" displayName:@"Timeout expression" value: @"return -1"];
	
	_state = "STOP";

	return self;
}

- (id) elementName
{
	return @"HTTP_MESSAGE_SOURCE";
}

- (id) defaultNameValue
{
	return @"Http message source";
}

- (id) messageSourceState
{
	return _state;
}

- (void) messageSourceState: (id) anState
{
	_state = anState;
	[self changed];
}
@end
