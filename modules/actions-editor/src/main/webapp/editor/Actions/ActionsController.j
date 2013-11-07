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
	CPDictionary _interpretersControllers;
	CPDictionary _interpretersDelegates;
}

+ (id) drawing: (id) aDrawing
{
	return [[super new] initialize: aDrawing];
}

- (id) initialize: (id) aDrawing
{
	_drawing = aDrawing;
	_interpreters = [CPDictionary dictionary]; 
	_interpretersControllers = [CPDictionary dictionary]; 
	_interpretersDelegates = [CPDictionary dictionary]; 
	
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
    var request = { url: '../atmo/' + [_drawing contextId],
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
	var id = aMessage.interpreterId + "#" + aMessage.threadId;
	var interpreterAPI = [_interpreters objectForKey: aMessage.interpreterId];
	var controller = [_interpretersControllers objectForKey: id];
	
	if (controller == nil) {
		controller = [[DebuggerWindowController alloc] initWithWindowCibName: "DebuggerWindow"];
		[controller id: id];
		[controller interpreterAPI: interpreterAPI];
		[_interpretersControllers setObject: controller forKey: id];
	}
	 
	[controller showWindow: nil];
	
	var delegate = [_interpretersDelegates objectForKey: id];
	[delegate process: aMessage];
	
	var eventType = aMessage.eventType;
	var interpreterId = aMessage.interpreterId;
	
	if ([eventType isEqualToString: "uncaught-exception"]) {
		[_interpreters removeObjectForKey: interpreterId];
		[_interpretersControllers removeObjectForKey: id];
	} else if ([eventType isEqualToString: "finish-interpretation"]) {
		[_interpreters removeObjectForKey: interpreterId];
		[_interpretersControllers removeObjectForKey: id];
	}
}

- (void) register: aDebuggerDelegate asDebugDelegateFor: anID
{
	[_interpretersDelegates setObject: aDebuggerDelegate forKey: anID];
}

- (void) evaluationResult: (id) result currentMessage: (id) aMessage
{
	var id = aMessage.interpreterId + "#" + aMessage.threadId;
	var delegate = [_interpretersDelegates objectForKey: id];
	[delegate evaluationResult: result];
}
@end