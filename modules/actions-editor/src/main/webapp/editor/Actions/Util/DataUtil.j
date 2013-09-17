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

/**
 * @author "Esteban Robles Luna <esteban.roblesluna@gmail.com>"
 */
@implementation DataUtil : CPObject
{
}

+ (id) var: (id) aVarName
{
	var current = window;
	var value = current[aVarName];
	while (value == undefined && window.top != current) {
		current = current.parent;
		value = current[aVarName];
	}
	
	if (value != undefined) {
		return value;
	} else {
		return nil;
	}
}

@end