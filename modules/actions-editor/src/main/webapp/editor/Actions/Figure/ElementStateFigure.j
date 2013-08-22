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
@implementation ElementStateFigure : MultiStateFigure 
{ 
	id _elementModel;
	id _lastState;
} 

+ (Figure) newAt: (CGPoint) aPoint elementModel: (id) aModel
{
	return [[super newAt: aPoint] initializeWithModel: aModel];
}

- (id) initializeWithModel: aModel
{ 
	[super init];
	
	var state1 = [ImageFigure initializeWithImage: "Resources/cross.gif" x: 0 y: 0];
	var state2 = [ImageFigure initializeWithImage: "Resources/sync.gif"  x: 0 y: 0];
	var state3 = [ImageFigure initializeWithImage: "Resources/tick.gif"  x: 0 y: 0];

    [state1 showBorder: NO];
    [state2 showBorder: NO];
    [state3 showBorder: NO];
	
	[self addFigure: state1 forState: @"NOT_IN_SYNC"];
	[self addFigure: state2 forState: @"SYNCING"];
	[self addFigure: state3 forState: @"SYNCED"];
	
	_elementModel = aModel;
	[self setHidden: YES];
	
	[[CPNotificationCenter defaultCenter] 
			addObserver: self 
			selector: @selector(updateStateFigure) 
			name: ModelPropertyChangedNotification 
			object: _elementModel];
			
	return self;
}

- (void) updateStateFigure
{
	var state = [_elementModel state];

	if ((_lastState == nil) || (![_lastState isEqualToString: state])) {
		if ([state isEqualToString: @"NOT_IN_SYNC"]) {
			[self switchToState: @"NOT_IN_SYNC"];
			[self fadeIn];
		}

		if ([state isEqualToString: @"SYNCING"]) {
			[self switchToState: @"SYNCING"];
		}

		if ([state isEqualToString: @"SYNCED"]) {
			[self switchToState: @"SYNCED"];
			[self fadeOut];
		}
	}
}
@end