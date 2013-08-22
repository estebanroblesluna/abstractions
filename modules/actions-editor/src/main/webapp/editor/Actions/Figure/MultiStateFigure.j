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
@implementation MultiStateFigure : Figure 
{ 
	CPDictionary _stateMapping;
} 

+ (Figure) newAt: (CGPoint) aPoint
{
	return [super newAt: aPoint];
}

- (id) init
{ 
	[super init];
	
	[self selectable: NO];
	[self moveable: YES];
	[self editable: NO];
	
	_stateMapping = [CPDictionary dictionary];
	
	return self;
}

- (void) addFigure: (id) aFigure forState: (id) aState
{
	[_stateMapping setObject: aFigure forKey: aState];
}

- (void) switchToState: (id) aState
{
	CPLog.debug("Switching to state " + aState);
	var figure = [_stateMapping objectForKey: aState];
	if (figure != nil) {
		[self setSubviews: [CPMutableArray array]];
		[self addSubview: figure];

		var frameSize = [figure frameSize];
		[self setFrameSize: frameSize];
		[self invalidate];
		CPLog.debug("Switched to state " + aState);
	}
}
@end