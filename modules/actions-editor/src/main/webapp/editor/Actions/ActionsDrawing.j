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
@implementation ActionsDrawing : Drawing
{
  id _contextAPI;
  id _libraryAPI;
  id _timer;
  id _dirty;
  id _generator;
  
  id _statusFigure;
  id _openInNewWindowFigure;

  CPDictionary _debugFigures;
  CPDictionary _processorFigures;
}

+ (ActionsDrawing) frame: (id) aFrame contextId: (id) aContextId
{
	return [[super frame: aFrame] initWith: aContextId];
}

- (id) initWith: (id) aContextId
{
	_dirty = false;
	_debugFigures = [CPDictionary dictionary];
	_processorFigures = [CPDictionary dictionary];
	
	var isDevelopment = [Actions isDevelopment];
	
	_statusFigure = [LabelFigure initializeWithText: @"" at: CGPointMake(0,20)];
	_openInNewWindowFigure = [LinkFigure initializeWithText: @"" at: CGPointMake(0,0)];
	
	[_statusFigure setHidden: !isDevelopment];

	[self addFigure: _statusFigure];
	[self addFigure: _openInNewWindowFigure];
	
	if (aContextId == nil) {
		_contextAPI = [ContextAPI new];
	} else {
		_contextAPI = [ContextAPI newWith: aContextId];
		[self setupNotifications];
	}
	
	[_contextAPI delegate: self];
	[[self model] propertyValue: @"showGrid" be: YES];
	[[self model] propertyValue: @"gridSize" be: 25];
	
	return self;
}

- (void) timer: (id) aTimer
{
	_timer = aTimer;
}

- (void) removeFigures: aListOfIds
{
	var figures = [self figures];
	
	for (var i = [figures count] -1; i >= 0 ; i--) { 
	    var figure = [figures objectAtIndex:i];
		if ([figure respondsToSelector: @selector(elementId)]) {
			var elementId = [figure elementId];
			if ($.inArray(elementId, aListOfIds) != -1) {
				[figure removeMyself];
			}
		}
	}
}

- (void) addProcessor: (id) aProcessorFigure
{
	CPLog.debug("Adding processor");

	[self addFigure: aProcessorFigure];
	[aProcessorFigure delegate: self];
	
	[[Actions mode] postAddProcessor: aProcessorFigure];
	
	_dirty = true;
}

- (void) addMessageSource: (id) aMessageSourceFigure
{
	CPLog.debug("Adding message source");

	[self addFigure: aMessageSourceFigure];
	[aMessageSourceFigure delegate: self];
	
	_dirty = true;
}

- (void) willRemoveSubview: (id) figure
{
	if ([figure isKindOfClass: [ProcessorFigure class]]) {
		[figure deleteFromServer];
	}
}

- (void) sync
{
	_dirty = false;
	[_contextAPI sync];
}

- (id) contextId
{
	return [_contextAPI contextId];
}

- (void) update: (CPTimer) aTimer
{
	[self sync];
}

- (void) contextCreated: (id) aContextId
{
	var host = location.host;
	var path = location.pathname;
	var url = "http://" + host + path + "?contextId=" + aContextId
	
	[_openInNewWindowFigure setText: url];
	[self setupNotifications];	
}

- (void) updateProfilingInfo 
{
	[_contextAPI profilingInfo];
}

- (void) profilingInfo: (id) aProfilingInfo
{
	var servers = aProfilingInfo.profilingInfo.servers;
	for (var i = 0; i < servers.length; i++) {
		var serverInfo = servers[i];
		var serverProfilingInfo = serverInfo.profilingInfo.averages;
		
		for (var key in serverProfilingInfo) {
			//it's an element id
			var value = serverProfilingInfo[key].toFixed(2);
			var processorFigure = [self processorFor: key];
			if (processorFigure != nil) {
				var dataFigure = [processorFigure dataFigure];
				[dataFigure setText: value + " ms"];
				[dataFigure setHidden: NO];
			}
		}
	}
}

- (void) setStatus: (id) aMessage
{
	[_statusFigure setText: aMessage];
}

- (void) initiateMessageSendFrom: (id) aProcessorFigure
{
	var debugWindow = [DebugWindow
						newAt: [aProcessorFigure frameOrigin]
						contextId: [self contextId]
						elementId: [aProcessorFigure elementId]
						drawing: self];
						
	[debugWindow orderFront:self];
}

- (void) setupNotifications
{
	[self setStatus: @"Connecting..."];
	
      var socket = $.atmosphere;
      var request = { url: '../atmo/' + [self contextId],
                      contentType : "application/json",
                      logLevel : 'debug',
                      transport : 'websocket' ,
                      fallbackTransport: 'long-polling'};
  
  
      request.onOpen = function(response) {
          [self setStatus: @"Connected"];
      };
  
      request.onMessage = function (response) {
          var message = $.parseJSON(response.responseBody);
          
          if ([message.interpreterId != nil]) {
          	[self processDebugMessage: message];
          } else {
	          [self receiveMessage: message];
          }
      };
  
      request.onError = function(response) {
          [self receiveMessage: @"Error connecting to the server"];
      };
  
      var subSocket = socket.subscribe(request);
  
      //subSocket.push(JSON.stringify({ author: author, message: msg }));
}

- (void) processDebugMessage: (id) message
{
	var id = message.interpreterId + "#" + message.threadId;
	var debugFigure = [_debugFigures objectForKey: id];
	
	if (debugFigure == nil) {
		//could be a different thread so create a new window
		var processorId = message.processorId;
		var processorFigure = [self processorFor: processorId];
		
		CPLog.debug("[ActionsDrawing] Processing debug message for processor id " + processorId + " figure: " + processorFigure + " thread id " + id);
		var debugWindow = [DebugWindow
							newAt: [processorFigure frameOrigin]
							contextId: [self contextId]
							elementId: [processorFigure elementId]
							drawing: self
							interpreterId: message.interpreterId
							threadId: message.threadId];
						
		[debugWindow orderFront: self];
		[self registerDebug: debugWindow for: id];
		debugFigure = debugWindow;
	}

	[debugFigure process: message];
}

- (id) generator
{
	return _generator;
}

- (id) generator: (id) aGenerator
{
	_generator = aGenerator;
}

- (void) registerDebug: (id) aDebugFigure for: (id) anInterpreterIdAndThreadId
{
	[_debugFigures setObject: aDebugFigure forKey: anInterpreterIdAndThreadId];
}

- (void) registerElement: (id) anElementFigure for: (id) anElementId
{
	CPLog.debug("[ActionsDrawing] Register element: " + anElementId);
	[_processorFigures setObject: anElementFigure forKey: anElementId];
}

- (id) processorFor: (id) aProcessorId
{
	var processorFigure = [_processorFigures objectForKey: aProcessorId];
	return processorFigure;
}
@end