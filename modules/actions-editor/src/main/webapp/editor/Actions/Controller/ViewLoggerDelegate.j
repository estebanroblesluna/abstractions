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

/**
 * @author "Esteban Robles Luna <esteban.roblesluna@gmail.com>"
 */
@implementation ViewLoggerDelegate : CPObject 
{
	@outlet CPWindow _window;
	@outlet CPView _logTextContainer;
	LPMultiLineTextField _logText;
}

- (void) awakeFromCib
{
    _logText = [[LPMultiLineTextField alloc] initWithFrame:[_logTextContainer frame]];
    
    [_logText setFrameOrigin: CGPointMake(0.0, 0.0)];
    [_logText setAutoresizingMask:CPViewHeightSizable | CPViewWidthSizable];
    [_logText setBordered: YES];
    [_logText setScrollable: YES];
    [_logText setEditable: YES];
    [_logText setDrawsBackground: YES];
    [_logText setBezeled: YES];

    [_logTextContainer addSubview: _logText];
    
    [[_window windowController] logText: _logText];
}
@end