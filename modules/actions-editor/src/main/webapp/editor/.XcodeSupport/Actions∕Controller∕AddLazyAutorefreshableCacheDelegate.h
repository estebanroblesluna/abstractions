#import <Cocoa/Cocoa.h>
#import "xcc_general_include.h"

@interface AddLazyAutorefreshableCacheDelegate : NSObject

@property (assign) IBOutlet NSWindow* _window;
@property (assign) IBOutlet id _memcachedURLTF;
@property (assign) IBOutlet id _oldCacheEntryInMillsTF;
@property (assign) IBOutlet id _keyExpressionTF;
@property (assign) IBOutlet id _cacheExpressionsTF;

- (IBAction)add:(id)aSender;

@end
