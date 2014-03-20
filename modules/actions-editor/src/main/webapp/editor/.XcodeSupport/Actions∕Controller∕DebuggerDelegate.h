#import <Cocoa/Cocoa.h>
#import "xcc_general_include.h"

@interface DebuggerDelegate : NSObject

@property (assign) IBOutlet NSWindow* _window;
@property (assign) IBOutlet id _payloadContainerTF;
@property (assign) IBOutlet id _exceptionContainerTF;
@property (assign) IBOutlet id _evaluateContainerTF;
@property (assign) IBOutlet id _evaluateResultContainerTF;
@property (assign) IBOutlet id _tableView;
@property (assign) IBOutlet id _nameColumn;
@property (assign) IBOutlet id _stepButton;
@property (assign) IBOutlet id _resumeButton;
@property (assign) IBOutlet id _evaluateButton;

- (IBAction)resume:(id)aSender;
- (IBAction)step:(id)aSender;
- (IBAction)evaluate:(id)aSender;

@end
