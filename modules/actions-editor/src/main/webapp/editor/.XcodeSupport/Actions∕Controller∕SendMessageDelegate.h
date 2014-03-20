#import <Cocoa/Cocoa.h>
#import "xcc_general_include.h"

@interface SendMessageDelegate : NSObject

@property (assign) IBOutlet NSWindow* _window;
@property (assign) IBOutlet id _payloadTF;
@property (assign) IBOutlet id _isExpressionCB;
@property (assign) IBOutlet id _tableView;
@property (assign) IBOutlet id _nameColumn;

- (IBAction)run:(id)aSender;
- (IBAction)debug:(id)aSender;
- (IBAction)addProperty:(id)aSender;
- (IBAction)removeProperty:(id)aSender;

@end
