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
 * @author "Guido Jose Celada <celadaguido@gmail.com>"
 */


@implementation ScriptFieldView : CPControl
{
    var buttonBar; //TODO: move this to scriptWebView
	var _script @accessors(property=script);
	var _button @accessors(property=button);
    var _scriptWebView @accessors(property=scriptWebView);
    CPPanel _panel @accessors(property=panel);
}

-(id) initWithFrame:(CGRect) aFrame {
    self = [super initWithFrame: aFrame];
    if (self) {
        _script = [CPTextField new];

        _button = [CPButton new];
        [_button setTitle:"Edit script"];
        [_button setTarget:self];
		[_button setAction:@selector(editScript:)];
        [_button sizeToFit];
        [self addSubview: _button];
    }
    return self;
}

- (void)setObjectValue:(id)anObject {
	object = anObject;

    [_script setStringValue:[object]];

    [_script sizeToFit];
}

- (void)editScript:(id)sender { //called when the edit button is clicked
    //create a hidden div so that the simple-editor can get the script
	var scriptString = [_script stringValue];
	scriptStringDiv = document.createElement("div");
    scriptStringDiv.style.display = "none";
    scriptStringDiv.setAttribute("id", "scriptString");
    scriptStringDiv.setAttribute("text", scriptString);
    document.body.appendChild(scriptStringDiv);

    //create a window for the script iframe
    _panel = [[CPWindow alloc]
    initWithContentRect:CGRectMake(70, 40, 700, 427)
    styleMask:CPClosableWindowMask | CPClosableWindowMask];
    [_panel orderFront:self];
    [_panel setTitle: "Edit Groovy script"];
    [_panel setDelegate: self];

    //set the script editor iframe
    _scriptWebView = [[CPWebView alloc] init];
    [_scriptWebView setFrame:CGRectMake(0, 0, 700, 427)];
    [_scriptWebView setMainFrameURL:"/simple-editor/"]
    [_scriptWebView setBackgroundColor:[CPColor blackColor]];

    //set the save button
    //var saveButton = [[CPButton alloc] initWithFrame:CGRectMake(0,448,638,30)];
    //[saveButton setTitle:"Save"]
    //[saveButton setValue:[CPColor grayColor] forThemeAttribute:@"bezel-color"]
    //[saveButton setValue:[CPColor blackColor] forThemeAttribute:@"text-color"]
    //[saveButton setAction:@selector(saveEditorContent)];
    //[saveButton setTarget:self];
    //[saveButton setEnabled:YES];

    //[_scriptWebView addSubview: saveButton]

	var contentView = [_panel contentView];

    [contentView addSubview: _scriptWebView];

    //var drawing = [[[[[self superview] superview] superview] superview] superview];
    //[drawing addFigure: _panel];

}

- (BOOL)windowShouldClose: (id)aWindow {
	//save the script
    var scriptString = [_scriptWebView stringByEvaluatingJavaScriptFromString: "myCodeMirror.getValue()"];
    var propertiesFigure = [[[[self superview] superview] superview] superview];
    [propertiesFigure saveScript: scriptString];

	//remove the child and close
    var scriptString = document.getElementById("scriptString");
    scriptString.parentNode.removeChild(scriptString);
    return true;
}

//- (void) saveEditorContent { //called when the user clicks the save button on the script editor
//}

@end



