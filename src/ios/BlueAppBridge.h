#import <Cordova/CDV.h>
#import "AppDelegate.h"

@interface BlueAppBridge : CDVPlugin

- (void)init:(CDVInvokedUrlCommand*)command;
- (void)message:(CDVInvokedUrlCommand*)command;

@end