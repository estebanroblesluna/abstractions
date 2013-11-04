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
@implementation ActionsController : CPObject
{
	id _drawing;
	CPDictionary _interpreters;
	CPDictionary _interpretersWindows;
}

+ (id) drawing: (id) aDrawing
{
	return [[super new] initialize: aDrawing];
}

- (id) initialize: (id) aDrawing
{
	_drawing = aDrawing;
	_interpreters = [CPDictionary dictionary]; 
	_interpretersWindows = [CPDictionary dictionary]; 
	
	[self setupNotifications];
	
	return self;
}

- (void) initiateMessageSendFrom: (id) aProcessorFigure
{
	var myController = [[InterpreterAPIWindowController alloc] initWithWindowCibName: "SendMessageWindow"];
	[myController showWindow: nil];
	
	var interpreterAPI = [InterpreterAPI 
		newIn: [_drawing contextId] 
		elementId: [aProcessorFigure elementId]];
	[interpreterAPI delegate: self];
	[interpreterAPI createInterpreter];
	
	[myController interpreterAPI: interpreterAPI];
}

- (void) createInterpreter: anInterpreterId with: anInterpreterAPI
{
	[_interpreters setObject: anInterpreterAPI forKey: anInterpreterId];
	//create the controller but don't show it yet
}

- (void) setStatus: (id) aMessage
{
	//TODO
	//[_statusFigure setText: aMessage];
}

- (void) receiveMessage: (id) aMessage
{
	[self setStatus: aMessage];
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
}

- (void) processDebugMessage: (id) aMessage
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
	
	var interpreterId = aMessage.interpreterId;
	var eventType = aMessage.eventType;
	var currentMessage = aMessage.currentMessage;
	var processorId = aMessage.processorId;
	var jsonCurrentMessage = JSON.stringify(aMessage);
	var exception = aMessage.exception;

	CPLog.debug("[ActionsController] Processor id " + processorId);
	CPLog.debug("[ActionsController] " + jsonCurrentMessage);

	[_label setStringValue: jsonCurrentMessage];
    [_label sizeToFit];
	
	if ([eventType isEqualToString: "breakpoint"]) {
		[self processBreakpoint: currentMessage processorId: processorId];
	} else if ([eventType isEqualToString: "before-step"]) {
		[self processBeforeStep: currentMessage processorId: processorId];
	} else if ([eventType isEqualToString: "after-step"]) {
		[self processAfterStep: currentMessage processorId: processorId];
	} else if ([eventType isEqualToString: "uncaught-exception"]) {
		[self processUncaughtException: currentMessage processorId: processorId exception: exception];
	} else if ([eventType isEqualToString: "finish-interpretation"]) {
		[_interpreters removeObjectForKey: interpreterId];
		var window = [_interpretersWindows objectForKey: interpreterId];
		[window close];
		[_interpretersWindows removeObjectForKey: interpreterId];
	}
}
@end