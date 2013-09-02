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

@import <AppKit/CPView.j>
@import <AppKit/CPTextField.j>

@import "Model/ElementModel.j"
@import "Model/DynamicElementModel.j"
@import "Model/DynamicMessageSourceModel.j"
@import "Model/DynamicModelGenerator.j"

@import "Figure/Magnet.j"
@import "Figure/MultiStateFigure.j"

@import "Figure/ElementFigure.j"
@import "Figure/ElementStateFigure.j"
@import "Figure/ProcessorFigure.j"
@import "Figure/MessageSourceFigure.j"
@import "Figure/MessageSourceStateFigure.j"
@import "Figure/DebugFigure.j"
@import "Figure/ElementConnection.j"

@import "Window/NewMessagePanel.j"
@import "Window/ViewMessagePanel.j"
@import "Window/DebugWindow.j"

@import "Tool/CreateProcessorTool.j"
@import "Tool/CreateElementConnectionTool.j"
@import "Tool/CreateMessageSourceTool.j"

@import "Command/DeployCommand.j"
@import "Command/LoadActionsCommand.j"
@import "Command/SaveActionsCommand.j"

@import "Server/ContextAPI.j"
@import "Server/ElementAPI.j"
@import "Server/InterpreterAPI.j"
@import "Server/LibraryAPI.j"

@import "ActionsDrawing.j"

/**
 * @author "Esteban Robles Luna <esteban.roblesluna@gmail.com>"
 */
@implementation Actions : CPObject
{
}

+ (id) isDevelopment
{
	return false;
}
@end