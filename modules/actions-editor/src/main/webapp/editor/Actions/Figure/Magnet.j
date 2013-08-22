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
@implementation Magnet : CPObject
{
	Figure _sourceFigure;
	Figure _targetFigure;
	id _selector;
}

+ (id) newWithSource: (id) aSourceFigure target: (id) aTargetFigure selector: (id) aSelector
{ 
	return [[self new] initWithSource: aSourceFigure target: aTargetFigure selector: aSelector];
}

- (id) initWithSource: (id) aSourceFigure target: (id) aTargetFigure selector: (id) aSelector
{ 
	_sourceFigure = aSourceFigure;
	_targetFigure = aTargetFigure;
	_selector = aSelector;

	[[CPNotificationCenter defaultCenter] 
			addObserver: self 
			selector: @selector(updateLocation:) 
			name: @"CPViewFrameDidChangeNotification" 
			object: _sourceFigure];

	return self;
}

- (void) updateLocation: aNotification
{
	var position = [_sourceFigure performSelector: _selector];
	
	var targetFrame = [_targetFigure frame];
	
	var xShift = 0; //[targetFrame.size.width / 2];
	var yShift = 0; //[targetFrame.size.height / 2];
	
	var newTopLeft = CGPointMake(position.x - xShift, position.y - yShift);

	CPLog.debug(newTopLeft.x, newTopLeft.y);

	[_targetFigure setFrameOrigin: newTopLeft];
}
@end
