#import <Cocoa/Cocoa.h>
#import "xcc_general_include.h"

@interface AddLoggerController : NSObject

@property (assign) IBOutlet NSWindow* _window;
@property (assign) IBOutlet id _beforeConditionalContainerTF;
@property (assign) IBOutlet id _afterConditionalContainerTF;
@property (assign) IBOutlet id _beforeContainerTF;
@property (assign) IBOutlet id _afterContainerTF;
@property (assign) IBOutlet id _isBeforeConditionalCB;
@property (assign) IBOutlet id _isAfterConditionalCB;

- (IBAction)add:(id)aSender;

@end
