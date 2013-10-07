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
 
ELEMENT_FAKE_ID = @"FAKE_ID";

@implementation ElementModel : Model
{
	id _elementAPI;
	id _delegate;
}

+ (id) contextId: (id) aContextId
{
	return [[super new] initialize: aContextId];
}

+ (id) contextId: (id) aContextId elementName: (id) anElementName
{
	return [[super new] initialize: aContextId elementName: anElementName];
}

+ (id) contextId: (id) aContextId elementId: (id) anElementId hasBreakpoint: (id) hasBreakpoint
{
	return [[super new] initialize: aContextId elementId: anElementId hasBreakpoint: hasBreakpoint];
}

- (id) init
{
	[super init];
	
	CPLog.debug("[ElementModel] Adding properties of ElementModel");
	
	return self;
}

- (id) initialize: (id) aContextId
{
	_elementAPI = [ElementAPI newIn: aContextId elementName: [self elementName]];
	
	CPLog.debug("[ElementModel] Setting delegate: " + self);
	[_elementAPI delegate: self];
	
	return self;
}

- (id) initialize: (id) aContextId elementName: (id) anElementName
{
	_elementAPI = [ElementAPI newIn: aContextId elementName: anElementName];
	
	CPLog.debug("[ElementModel] Setting delegate: " + self);
	[_elementAPI delegate: self];
	
	return self;
}

- (id) initialize: (id) aContextId elementId: (id) anElementId hasBreakpoint: (id) hasBreakpoint
{
	_elementAPI = [ElementAPI newIn: aContextId elementId: anElementId hasBreakpoint: hasBreakpoint];
	
	CPLog.debug("[ElementModel] Setting delegate: " + self);
	[_elementAPI delegate: self];
	
	return self;
}

- (void) delegate: (id) aDelegate
{
	_delegate = aDelegate;
}

- (id) defaultNameValue
{
	[CPException raise: @"Subclass responsibility" reason: @"Subclass should implement"];
}

- (id) elementName
{
	[CPException raise: @"Subclass responsibility" reason: @"Subclass should implement"];
}

- (void) propertyValue: (id) aName be: (id) aValue
{
	if (_fireNotifications) {
		[super propertyValue: aName be: aValue];
		[_elementAPI set: aName value: aValue];
	}
}

- (void) propertyValueAt: (id) anIndex be: (id) aValue
{
	if (_fireNotifications) {
		[super propertyValueAt: anIndex be: aValue];

		var aName = [self propertyNameAt: anIndex];
		[_elementAPI set: aName value: aValue];
	}
}

- (void) deleteFromServer
{
	[_elementAPI delete];
}

- (void) addCache
{
	[_elementAPI addCache];
}

- (id) elementId
{
	return [_elementAPI elementId];
}

- (void) elementId: (id) anElementId
{
	CPLog.debug("[ElementModel] Element created with id " + anElementId);
   
   if ([_delegate != nil]) {
     [_delegate elementId: anElementId];
   }
}

- (void) loggerLines: (id) loggerInfo
{
   if ([_delegate != nil]) {
     [_delegate loggerLines: loggerInfo];
   }
}

- (void) deleted
{
   if ([_delegate != nil]) {
     [_delegate deleted];
   }
}

- (id) api
{
	return _elementAPI;
}

- (id) nextInChain: (id) anElementId
{
	CPLog.debug("[ElementModel] Setting next in chain");
	return [_elementAPI nextInChain: anElementId];
}

- (id) addAllConnection: (id) anElementId
{
	CPLog.debug("[ElementModel] By default ignore");
}

- (id) addChoiceConnection: (id) anElementId
{
	CPLog.debug("[ElementModel] By default ignore");
}

- (void) nextInChainId: (id) anId for: (id) anElementId
{
   if ([_delegate != nil]) {
     [_delegate nextInChainId: anId for: anElementId];
   }
}

- (id) state
{
	return [_elementAPI state];
}

- (id) hasBreakpoint
{
	return [_elementAPI hasBreakpoint];
}

- (void) setBreakpoint: (id) aBreakpoint
{
	[_elementAPI setBreakpoint: aBreakpoint];
}
@end
