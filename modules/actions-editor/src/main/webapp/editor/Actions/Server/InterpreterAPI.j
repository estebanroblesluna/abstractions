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
@implementation InterpreterAPI : CPObject
{
  id _contextId;
  id _elementId;
  id _interpreterId;
  id _threadId;
  id _delegate;
}

+ (InterpreterAPI) newIn: (id) contextId elementId: (id) elementId
{
  return [[self new] initWithContextId: contextId elementId: elementId];
}

- (id) initWithContextId: (id) contextId elementId: (id) elementId
{
	_contextId = contextId;
	_elementId = elementId;
	
	return self;
}


- (void) interpreterId: (id) anInterpreterId threadId: (id) aThreadId
{
	_interpreterId = anInterpreterId;
	_threadId = aThreadId;
}

- (id) threadId
{
	return _threadId;
}

- (id) createInterpreter
{
	CPLog.debug("Creating interpreter");
	
	$.ajax({
		type: "POST",
		url: "../service/interpreter/" + [Actions applicationId] + "/" + _contextId + "/create",
		data: {"initialProcessorId": _elementId }
	}).done(function( result ) {
		_interpreterId = result.id;
		_threadId = "1";
		CPLog.debug("Interpreter created with id:" + _interpreterId);
		[_delegate createInterpreter: _interpreterId with: self];
	});
	
	return self;
}

- (id) run: (id) initialMessage
{
	CPLog.debug("Running interpreter " + _interpreterId);
	
	var message = JSON.stringify(initialMessage);
	
	$.ajax({
		type: "POST",
		url: "../service/interpreter/" + _interpreterId + "/run",
		data: {"initialMessage" : message}
	}).done(function( result ) {
		CPLog.debug("Interpreter run " + _interpreterId);
	});
	
	return self;
}

- (id) debug: (id) initialMessage
{
	CPLog.debug("Debugging interpreter " + _interpreterId);

	var message = JSON.stringify(initialMessage);
	
	$.ajax({
		type: "POST",
		url: "../service/interpreter/" + _interpreterId + "/debug",
		data: {"initialMessage" : message}
	}).done(function( result ) {
		CPLog.debug("Interpreter debugged " + _interpreterId);
	});
	
	return self;
}

- (id) step
{
	CPLog.debug("Stepping interpreter " + _interpreterId);
	
	$.ajax({
		type: "POST",
		url: "../service/interpreter/" + _interpreterId + "/" + _threadId + "/step"
	}).done(function( result ) {
		CPLog.debug("Thread " + _threadId + " of interpreter " + _interpreterId + " has stepped");
	});
	
	return self;
}

- (id) resume
{
	CPLog.debug("Resuming interpreter " + _interpreterId);
	
	$.ajax({
		type: "POST",
		url: "../service/interpreter/" + _interpreterId + "/" + _threadId + "/resume"
	}).done(function( result ) {
		CPLog.debug("Thread " + _threadId + " of interpreter " + _interpreterId + " has resumed");
	});
	
	return self;
}

- (id) evaluate: (id) anExpression
{
	CPLog.debug("Evaluating " + anExpression);

	$.ajax({
		type: "POST",
		url: "../service/interpreter/" + _interpreterId + "/" + _threadId + "/evaluate",
		data: {"expression" : anExpression}
	}).done(function( r ) {
		var result = $.parseJSON(r.result);
		
		[_delegate 
			evaluationResult: result.evaluationResult 
			currentMessage: result];
		CPLog.debug("Evaluated expression in " + _interpreterId + " thread " + _threadId);
	});
	
	return self;
}

- (void) delegate: (id) aDelegate
{
	_delegate = aDelegate;
}
@end