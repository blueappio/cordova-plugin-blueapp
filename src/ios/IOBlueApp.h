#import <Cordova/CDV.h>
#import "AppDelegate.h"

@interface IOBlueApp : CDVPlugin

- (void)init:(CDVInvokedUrlCommand*)command;
- (void)message:(CDVInvokedUrlCommand*)command;

@end