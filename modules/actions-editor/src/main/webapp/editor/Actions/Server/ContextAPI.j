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
@import <Foundation/Foundation.j>

/**
 * @author "Esteban Robles Luna <esteban.roblesluna@gmail.com>"
 */
@implementation ContextAPI : CPObject
{
  id _contextId;
  id _delegate;
}

+ (ContextAPI) new
{
  return [[super new] initWithContextId: nil];
}

+ (ContextAPI) newWith: (id) contextId
{
  return [[super new] initWithContextId: contextId];
}

- (void) delegate: (id) aDelegate
{
	_delegate = aDelegate;
}

- (id) initWithContextId: (id) contextId
{
	_contextId = contextId;
	
	if (_contextId == nil) {
		[self createContext];
	}
	
	return self;
}

- (id) contextId
{
	return _contextId;
}

- (id) createContext
{
	if (_contextId != nil) {
		return self;
	}

	CPLog.debug("Creating context");
	$.ajax({
		type: "POST",
		url: "../service/context/"
	}).done(function( result ) {
		_contextId = result.id;
		[_delegate contextCreated: _contextId];
		CPLog.debug("Context created with id:" + _contextId);
	});
	
	return self;
}

- (id) sync
{
	CPLog.debug("Syncing context " + _contextId);
	$.ajax({
		type: "POST",
		url: "../service/context/" + _contextId + "/sync"
	}).done(function( result ) {
		CPLog.debug("Context synced " + _contextId);
	});

	return self;
}

@end