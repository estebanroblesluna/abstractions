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
 * @author "Guido José Celada <celadaguido@gmail.com>"
 */
@implementation ScriptFieldView : CPControl
{
	var _script @accessors(property=script);
	var _button @accessors(property=button);
}


-(id) initWithFrame:(CGRect) aFrame {
    self = [super initWithFrame: aFrame];
    if (self) {
        _script = [CPTextField new];
       // [_script setEditable: YES];
       // [_script setTextColor:[CPColor grayColor]];
       // [self addSubview: _script];

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
    [[self script] setStringValue:[object]];

    [[self script] sizeToFit];
}

- (void)editScript:(id)sender { //called when the edit button is clicked
	var oldScriptDiv = document.getElementById("scriptString");
	if ( oldScriptDiv != null)
		oldScriptDiv.parentNode.removeChild(oldScriptDiv);

	var oldIframe = document.getElementById("simple-editor-iframe");
	if ( oldIframe != null)
		oldIframe.parentNode.removeChild(oldIframe);

	var scriptString = [_script stringValue];
	scriptStringDiv = document.createElement("div");
    scriptStringDiv.style.display = "none";
    scriptStringDiv.setAttribute("id", "scriptString");
    scriptStringDiv.setAttribute("text", scriptString);
    document.body.appendChild(scriptStringDiv);

	ifrm = document.createElement("iframe");
    ifrm.setAttribute("src", "/simple-editor/");
    ifrm.style.width = 640+"px";
    ifrm.style.height = 480+"px";
    ifrm.style.frameBorder = "0";
    ifrm.setAttribute("id", "simple-editor-iframe");
    document.body.appendChild(ifrm);
    //TODO: make the iframe movable
}

/*
function saveScript() {
    var newScriptDiv = document.getElementById("newScriptString");
    newScriptDiv.parentNode.removeChild(newScriptDiv);
} */


@end



