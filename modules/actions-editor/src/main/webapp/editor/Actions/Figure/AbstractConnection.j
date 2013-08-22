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
@implementation AbstractConnection : Connection 
{
	id _id;
}

+ (void) connect: (id) aConnection with: (Figure) aSourceFigure
{
	[CPException raise: "Subclass responsibility" reason: "Subclass responsibility"];
}

- (id) initWithSource: (Figure) aSourceFigure target: (Figure) aTargetFigure points: (id) anArrayOfPoints
{ 
	self = [super initWithSource: aSourceFigure target: aTargetFigure points: anArrayOfPoints];
	_foregroundColor = [CPColor colorWithHexString: [self defaultColor]];
	
	var contextMenu = [[CPMenu alloc] init]; 
    [contextMenu setDelegate: self]; 

    var deleteMenu = [[CPMenuItem alloc] initWithTitle:@"Delete" action: @selector(deleteFromServer) keyEquivalent:@""]; 
    [deleteMenu setTarget: self]; 
    [deleteMenu setEnabled: YES]; 
    [contextMenu addItem: deleteMenu]; 
    
	[self setMenu: contextMenu];
	
	return self;
}

- (id) id
{
	return _id;
}

- (void) id: anId
{
	_id = anId;
	[self model: [[self modelClass] 
					contextId: [[self drawing] contextId] 
					elementId: _id 
					hasBreakpoint: NO]];
}

- (void) deleteFromServer
{
	[[self model] deleteFromServer];
	[self removeFromSuperview];
}

- (id) modelClass
{
	[CPException raise: "Subclass responsibility" reason: "Subclass responsibility"];
}

- (id) defaultColor
{
	[CPException raise: "Subclass responsibility" reason: "Subclass responsibility"];
}
@end