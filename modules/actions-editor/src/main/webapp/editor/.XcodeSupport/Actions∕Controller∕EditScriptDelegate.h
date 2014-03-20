#import <Cocoa/Cocoa.h>
#import "xcc_general_include.h"

@interface SendMessageDelegate : NSObject

@property (assign) IBOutlet NSWindow* _window;

- (IBAction)sayHello:(id)aSender;

@end
