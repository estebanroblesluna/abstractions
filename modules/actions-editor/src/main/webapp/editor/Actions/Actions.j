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

@import "Model/ChoiceModel.j" 
@import "Model/ChoiceConnectionModel.j" 
@import "Model/AllModel.j" 
@import "Model/AllConnectionModel.j" 
@import "Model/NextInChainConnectionModel.j" 
@import "Model/WireTapModel.j" 
@import "Model/WireTapConnectionModel.j" 


@import "Model/GroovyModel.j" 
@import "Model/HttpFetcherModel.j"
@import "Model/HttpMessageSourceModel.j"
@import "Model/FileReaderModel.j" 
@import "Model/DustRendererModel.j" 
@import "Model/LogModel.j" 

@import "Figure/Magnet.j"
@import "Figure/MultiStateFigure.j"

@import "Figure/ElementFigure.j"
@import "Figure/ElementStateFigure.j"
@import "Figure/ProcessorFigure.j"
@import "Figure/MessageSourceFigure.j"
@import "Figure/MessageSourceStateFigure.j"
@import "Figure/DebugFigure.j"

@import "Figure/AbstractConnection.j"
@import "Figure/NextInChainConnection.j"
@import "Figure/AllConnection.j"
@import "Figure/ChoiceConnection.j"
@import "Figure/WireTapConnection.j"


@import "Window/NewMessagePanel.j"
@import "Window/ViewMessagePanel.j"
@import "Window/DebugWindow.j"

@import "Tool/CreateProcessorTool.j"
@import "Tool/CreateConnectionTool.j"
@import "Tool/CreateNextInChainTool.j"
@import "Tool/CreateAllConnectionTool.j"
@import "Tool/CreateChoiceConnectionTool.j"
@import "Tool/CreateMessageSourceTool.j"
@import "Tool/CreateWireTapConnectionTool.j"


@import "Command/LoadActionsCommand.j"
@import "Command/SaveActionsCommand.j"

@import "Server/ContextAPI.j"
@import "Server/ElementAPI.j"
@import "Server/InterpreterAPI.j"

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