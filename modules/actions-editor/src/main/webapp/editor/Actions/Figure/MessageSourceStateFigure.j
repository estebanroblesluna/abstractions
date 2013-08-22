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
@implementation MessageSourceStateFigure : MultiStateFigure 
{ 
	id _lookModel;
	id _lastState;
} 

+ (Figure) newAt: (CGPoint) aPoint model: (id) aModel
{
	return [[super newAt: aPoint] initializeWithModel: aModel];
}

- (id) initializeWithModel: aModel
{ 
	[super init];
	
	var state1 = [ImageFigure initializeWithImage: "Resources/service_stop.png" x: 0 y: 0];
	var state2 = [ImageFigure initializeWithImage: "Resources/service_running.png"  x: 0 y: 0];

    [state1 showBorder: NO];
    [state2 showBorder: NO];
	
	[self addFigure: state1 forState: @"STOP"];
	[self addFigure: state2 forState: @"START"];
	
	_lookModel = aModel;
	[self setHidden: NO];
	
	[[CPNotificationCenter defaultCenter] 
			addObserver: self 
			selector: @selector(updateStateFigure) 
			name: ModelPropertyChangedNotification 
			object: _lookModel];
			
	return self;
}

- (void) updateStateFigure
{
	var state = [_lookModel messageSourceState];

	if ((_lastState == nil) || (![_lastState isEqualToString: state])) {
		if ([state isEqualToString: @"STOP"]) {
			[self switchToState: @"STOP"];
		}

		if ([state isEqualToString: @"START"]) {
			[self switchToState: @"START"];
		}
	}
}
@end