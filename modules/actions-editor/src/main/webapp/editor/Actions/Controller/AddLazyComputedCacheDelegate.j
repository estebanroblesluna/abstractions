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
@implementation AddLazyComputedCacheDelegate : CPObject 
{
	@outlet CPWindow _window;

	@outlet id _memcachedURLTF;
	@outlet id _ttlTF;
	@outlet id _keyExpressionTF;
	@outlet id _cacheExpressionsTF;
}

- (IBAction) add: (id) aSender
{
	var memcachedURL = [_memcachedURLTF stringValue];
	var ttl = [_ttlTF stringValue];
	var keyExpression = [_keyExpressionTF stringValue];
	var cacheExpressions = [_cacheExpressionsTF stringValue];
	
	[[[_window windowController] elementAPI]
		addLazyComputedCache: memcachedURL 
		ttl: ttl 
		keyExpression: keyExpression 
		cacheExpressions: cacheExpressions];
	
	[_window close];
}
@end