#import "IOBlueApp.h"
#import <GATT-IP-LIB/GATTIP.h>

@interface IOBlueApp () <GATTIPDelegate> {
}

@property(nonatomic, strong)GATTIP *gattip;
@end

@implementation IOBlueApp

NSString* callbackId;

- (void)init:(CDVInvokedUrlCommand*)command
{
    _gattip = [[GATTIP alloc] init];
    [_gattip setDelegate:self];
}

- (void)message:(CDVInvokedUrlCommand*)command
{
    [self.commandDelegate runInBackground:^{
        callbackId = [command callbackId];
        NSString* request = [[command arguments] objectAtIndex:0];
        NSLog(@"IOBlueApp Request <<<<<<<<<< : %@",request);
        
        NSData *stringValueData = [request dataUsingEncoding:NSUTF8StringEncoding];
        if(_gattip && stringValueData){
            [_gattip request:stringValueData];
        }
    }];
}

-(void)response:(NSData *)gattipMesg
{
    [self.commandDelegate runInBackground:^{
        NSString *gattipResponse =  [[NSString alloc] initWithData:gattipMesg encoding:NSUTF8StringEncoding];
        NSLog(@"IOBlueApp Response >>>>>>>>>> : %@",gattipResponse);
        
        CDVPluginResult* result = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:gattipResponse];
        result.keepCallback = [NSNumber numberWithInt:1];
        [self.commandDelegate sendPluginResult:result callbackId:callbackId];
    }];
}

- (void)close:(CDVInvokedUrlCommand*)command
{
    [self.commandDelegate runInBackground:^{
        if(_gattip != nil){
            _gattip = nil;
        }
    }];
}

@end