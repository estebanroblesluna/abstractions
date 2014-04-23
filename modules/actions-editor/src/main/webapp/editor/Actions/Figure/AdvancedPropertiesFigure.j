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

PropertyTypeScript = @"TYPE_SCRIPT";


@implementation AdvancedPropertiesFigure : PropertiesFigure
{
}


- (id) tableView: (CPTableView) aTableView dataViewForTableColumn: (CPTableColumn) aTableColumn row: (CPInteger) aRowIndex
{
	var _model = [_selectedFigure model];
	var tableColumnId = [aTableColumn identifier];
	var propertyName = [_model propertyDisplayNameAt: aRowIndex];
	var viewKind = "view_kind_" + _model + "_" + propertyName + "_" + tableColumnId;
	var view = [aTableView makeViewWithIdentifier: viewKind owner: self];

	if (view == null) {
		if (aRowIndex < 0 || _model == nil) {
			view = [[CPTableCellView alloc] initWithFrame:CGRectMakeZero()];
		} else {
			if (aTableColumn == _valueColumn) {
				var propertyType = [_model propertyTypeAt: aRowIndex];
				if ([propertyType isEqual: PropertyTypeBoolean]) {
					var editableView = [CPCheckBox checkBoxWithTitle:@""];
		        	[editableView sizeToFit];
		        	[editableView setTarget: self];
		        	[editableView setSendsActionOnEndEditing: YES];
		        	[editableView setAction: @selector(toggleCheckbox:)];
		        	[_checkboxMapping setObject: aRowIndex forKey: editableView];

		        	view = editableView;
				} else if (([propertyType isEqual: PropertyTypeScript])) {
					var scriptView = [ScriptFieldView new];
					view = scriptView;
				} else {
					view = [aTableColumn _newDataView];
				}
			} else {
				view = [aTableColumn _newDataView];
			}
		}

		[view setIdentifier: viewKind];
	}

	var value = @"Undefined"
	if (aTableColumn == _valueColumn) {
		value = [_model propertyValueAt: aRowIndex];
	} else {
		value = [_model propertyDisplayNameAt: aRowIndex];
	}

	[view setObjectValue: value];

	return view;
}

- (void) saveScript: (CPString) script
{
	var model = [_selectedFigure model];
	[model propertyValue: @"script" be: script];
}



@end
