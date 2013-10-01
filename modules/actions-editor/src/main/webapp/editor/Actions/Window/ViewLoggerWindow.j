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
@implementation ViewLoggerWindow : CPPanel 
{
	id _lines;
}

+ (AddLoggerWindow) newAt: (id) aPoint
{
	var frame = CGRectMake(aPoint.x, aPoint.y, 663, 250);
	var window = [self alloc];
	[window initWithContentRect: frame styleMask: CPHUDBackgroundWindowMask | CPClosableWindowMask | CPResizableWindowMask]; //CPResizableWindowMask
	return [window initialize];
}

- (id) initialize
{
	var contentView = [self contentView];

	_lines = [[CPTextField alloc] initWithFrame:CGRectMakeZero()];
    [_lines setStringValue: @""];
    [_lines setBezeled: YES];
    [_lines setTextColor: [CPColor blackColor]];
    [_lines setEditable: YES];
    [_lines sizeToFit];

	var panelFrameSize = [contentView frameSize];
	var expressionFrameSize = [_lines frameSize];

    [_lines setFrameSize: CGSizeMake(panelFrameSize.width, 150)];
	[_lines setFrameOrigin: CGPointMake(0, 30)];
	[contentView addSubview: _lines];

	[self setTitle: @"Logger view"];

	return self;
}

- (void) loggerInfo: (id) loggerInfo
{
	var string = loggerInfo.join('\n');
	[_lines setStringValue: string];
}

@end