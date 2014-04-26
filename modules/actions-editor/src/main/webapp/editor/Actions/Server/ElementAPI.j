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
@implementation ElementAPI : CPObject
{
  id _contextId;
  id _elementId;
  id _state;
  id _delegate;
  id _hasBreakpoint;
}

+ (ElementAPI) newIn: (id) contextId elementName: (id) elementName
{
  return [[self new] initWithContextId: contextId elementName: elementName];
}

+ (ElementAPI) newIn: (id) contextId elementId: (id) elementId hasBreakpoint: (id) hasBreakpoint
{
  return [[self new] initWithContextId: contextId elementId: elementId hasBreakpoint: hasBreakpoint];
}

- (id) initWithContextId: (id) contextId elementName: (id) elementName
{
	_contextId = contextId;
	_state = @"NOT_IN_SYNC";
	_hasBreakpoint = NO;
	
	[self createElement: elementName];
	
	return self;
}

- (id) initWithContextId: (id) contextId elementId: (id) elementId hasBreakpoint: (id) hasBreakpoint
{
	_contextId = contextId;
	_elementId = elementId;
	_state = @"SYNCED";
	_hasBreakpoint = hasBreakpoint;
	
	return self;
}

- (void) delegate: (id) aDelegate
{
	_delegate = aDelegate;
}

- (id) state
{
	return _state;
}

- (id) hasBreakpoint
{
	return _hasBreakpoint;
}

- (id) contextId
{
	return _contextId;
}

- (id) elementId
{
	return _elementId;
}

- (id) createElement: (id) elementName
{
	CPLog.debug("Creating element with name: " + elementName);
	_state = @"NOT_IN_SYNC";
	$.ajax({
		type: "POST",
		url: "../service/element/" + _contextId + "/" + elementName
	}).done(function( result ) {
		_elementId = result.id;
		_state = @"SYNCED";
		CPLog.debug("[ElementAPI] Element created with id " + _elementId);
		[_delegate elementId: _elementId];
		[self changed];
	});
	
	[self changed];
	return self;
}

- (id) addProfiler
{
	CPLog.debug("Adding profiler to element " + _elementId);
	_state = @"NOT_IN_SYNC";
	
	$.ajax({
		type: "PUT",
		url: "../service/element/" + _contextId + "/" + _elementId + "/profiler/" + [Actions deploymentId]
	}).done(function( result ) {
		_state = @"SYNCED";
		[self changed];
		CPLog.debug("Profiler added to " + _elementId);
	});
}

- (id) addLazyComputedCache: (id) memcachedURL ttl: (id) ttl keyExpression: (id) keyExpression cacheExpressions: (id) cacheExpressions
{
	CPLog.debug("Adding cache to element " + _elementId);
	_state = @"NOT_IN_SYNC";

	$.ajax({
		type: "POST",
		url: "../service/element/" + _contextId + "/" + _elementId + "/cache/computed/" + [Actions deploymentId],
		data: {
			"memcachedURL" : memcachedURL, 
			"ttl" : ttl, 
			"keyExpression" : keyExpression, 
			"cacheExpressions" : cacheExpressions 
		}
	}).done(function( result ) {
		_state = @"SYNCED";
		[self changed];
		CPLog.debug("Cache added to " + _elementId);
	});
}

- (id) addLazyAutorefreshableCache: (id) memcachedURL oldCacheEntryInMills: (id) oldCacheEntryInMills keyExpression: (id) keyExpression cacheExpressions: (id) cacheExpressions
{
	CPLog.debug("Adding cache to element " + _elementId);
	_state = @"NOT_IN_SYNC";
	
	$.ajax({
		type: "POST",
		url: "../service/element/" + _contextId + "/" + _elementId + "/cache/autorefreshable/" + [Actions deploymentId],
		data: {
			"memcachedURL" : memcachedURL, 
			"oldCacheEntryInMills" : oldCacheEntryInMills, 
			"keyExpression" : keyExpression, 
			"cacheExpressions" : cacheExpressions 
		}
	}).done(function( result ) {
		_state = @"SYNCED";
		[self changed];
		CPLog.debug("Cache added to " + _elementId);
	});
}


- (id) removeProfiler
{
	CPLog.debug("Removing profiler to element " + _elementId);
	_state = @"NOT_IN_SYNC";
	$.ajax({
		type: "DELETE",
		url: "../service/element/" + _contextId + "/" + _elementId + "/profiler/" + [Actions deploymentId]
	}).done(function( result ) {
		_state = @"SYNCED";
		[self changed];
		CPLog.debug("Profiler deleted to " + _elementId);
	});
}

- (id) addLogger: (id) beforeExpression afterExpression: (id) afterExpression beforeConditionalExpressionValue: beforeConditionalExpressionValue afterConditionalExpressionValue: afterConditionalExpressionValue
{
	CPLog.debug("Adding logger to element " + _elementId);
	_state = @"NOT_IN_SYNC";

	var isBeforeConditional = beforeConditionalExpressionValue != nil;
	var isAfterConditional = afterConditionalExpressionValue != nil;

	$.ajax({
		type: "POST",
		data: {
			"beforeExpression" : beforeExpression, 
			"afterExpression": afterExpression,
			"isBeforeConditional": isBeforeConditional,
			"isAfterConditional": isAfterConditional,
			"beforeConditionalExpressionValue": beforeConditionalExpressionValue,
			"afterConditionalExpressionValue": afterConditionalExpressionValue
		},
		url: "../service/element/" + _contextId + "/" + _elementId + "/logger/" + [Actions deploymentId]
	}).done(function( result ) {
		_state = @"SYNCED";
		[self changed];
		CPLog.debug("Logger added to " + _elementId);
	});
}

- (id) removeLogger
{
	CPLog.debug("Removing logger of element " + _elementId);
	_state = @"NOT_IN_SYNC";
	$.ajax({
		type: "DELETE",
		url: "../service/element/" + _contextId + "/" + _elementId + "/logger/" + [Actions deploymentId]
	}).done(function( result ) {
		_state = @"SYNCED";
		[self changed];
		CPLog.debug("Logger removed to " + _elementId);
	});
}

- (id) getLogger
{
	CPLog.debug("Getting logger of element " + _elementId);
	$.ajax({
		type: "GET",
		url: "../service/element/" + _contextId + "/" + _elementId + "/logger/" + [Actions deploymentId]
	}).done(function( result ) {
		[_delegate loggerLines: result];
		CPLog.debug("Logger removed to " + _elementId);
	});
}

- (id) delete
{
	CPLog.debug("Deleting element " + _elementId);
	_state = @"NOT_IN_SYNC";
	$.ajax({
		type: "DELETE",
		url: "../service/element/" + _contextId + "/" + _elementId
	}).done(function( result ) {
		_state = @"DELETED";
		[self changed];
		[_delegate deleted];
		[[Actions drawing] removeFigures: result.deleted.deleted];
		CPLog.debug("Element deleted " + _elementId);
	});
	
	[self changed];
	return self;
}

- (id) set: (id) propertyName value: (id) propertyValue
{
	CPLog.debug("Setting property of " + _elementId + " property " + propertyName + " value " + propertyValue);
	
	$.ajax({
		type: "POST",
		url: "../service/element/" + _contextId + "/" + _elementId + "/property/" + propertyName,
		data: {"propertyValue" : propertyValue }
	}).done(function( result ) {
		_state = @"NOT_IN_SYNC";
		[self changed];
		CPLog.debug("Element property set of " + _elementId + " property " + propertyName + " value " + propertyValue);
	});
	
	[self changed];
	return self;
}

- (id) addConnectionType: (id) aConnectionType to: (id) anElementId identificable: (id) anIdentificable
{
	CPLog.debug("[ElementAPI] Adding connection from " + _elementId + " to " + anElementId + " of type " + aConnectionType);
	_state = @"NOT_IN_SYNC";
	
	$.ajax({
		type: "POST",
		url: "../service/element/" + _contextId + "/" + _elementId + "/" + anElementId + "/connection/" + aConnectionType
	}).done(function( result ) {
		_state = @"SYNCED";
		var id = result.id;
		[anIdentificable id: id];
		[self changed];
		CPLog.debug("[ElementAPI] Added connection from " + _elementId + " to " + anElementId + " of type " + aConnectionType);
	});
	
	[self changed];
	return self;
}

- (id) setBreakpoint: (id) aBreakpoint
{
	CPLog.debug("Setting breakpoint of " + _elementId + " is " + aBreakpoint);
	_state = @"NOT_IN_SYNC";
	
	$.ajax({
		type: "PUT",
		url: "../service/element/" + _contextId + "/" + _elementId + "/breakpoint/" + aBreakpoint
	}).done(function( result ) {
		_state = @"SYNCED";
		_hasBreakpoint = aBreakpoint;
		[self changed];
		CPLog.debug("Set breakpoint of " + _elementId + " next is " + aBreakpoint);
	});
	
	[self changed];
	return self;
}

- (id) perform: (id) actionName arguments: (id) anArrayOfArguments
{
	return [self perform: actionName arguments: anArrayOfArguments onResponse: nil];
}

- (id) perform: (id) actionName arguments: (id) anArrayOfArguments onResponse: (id) aCallback
{
	CPLog.debug("Performing action of " + _elementId + " action " + actionName + " arguments " + anArrayOfArguments);
	$.ajax({
		type: "POST",
		url: "../service/element/" + _contextId + "/" + _elementId + "/action/" + actionName
	}).done(function( result ) {
		CPLog.debug("Action performed of " + _elementId + " action " + actionName + " arguments " + anArrayOfArguments);
		if (aCallback != nil) {
			aCallback.call(aCallback, result.result);
		}
	});

	return self;
}

- (id) sync
{
	CPLog.debug("Syncing element " + _elementId);
	_state = @"SYNCING";
	[self changed];

	$.ajax({
		type: "POST",
		url: "../service/element/" + _contextId + "/" + _elementId + "/sync"
	}).done(function( result ) {
		_state = @"SYNCED";
		[self changed];
		CPLog.debug("Element synced " + _elementId);
	});

	return self;
}

- (id) changed
{
	if (_elementId != nil && _delegate != nil) {
		CPLog.debug("Object " + _elementId + " changed");
		[_delegate changed];
		
		if ([_state isEqualToString: @"NOT_IN_SYNC"]) {
			var _timer = [CPTimer 
				scheduledTimerWithTimeInterval: 2 
				target:self 
				selector: @selector(sync) 
				userInfo: nil 
				repeats: NO];
		}
		
	}
}

- (id) id: (id) anID
{
	_elementId = anID;
}
@end