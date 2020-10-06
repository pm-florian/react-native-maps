//
//  AIRMapSecuredUrlTileOverlay.m
//  AirMaps
//
//  Created by Florian on 06.10.20.
//  Copyright © 2020 Christopher. All rights reserved.
//

#import "AIRMapSecuredUrlTileOverlay.h"

@interface AIRMapSecuredUrlTileOverlay ()

@end

@implementation AIRMapSecuredUrlTileOverlay
@synthesize AccessToken;

-(id) initWithURLTemplateAndAccessToken:(NSString *)URLTemplate :(NSString *)accessToken {
    self = [super initWithURLTemplate:URLTemplate];
    self.AccessToken = accessToken;
    return self;
}

- (void)loadTileAtPath:(MKTileOverlayPath)path result:(void (^)(NSData *, NSError *))result {
    NSMutableString *tileUrlString = [self.URLTemplate mutableCopy];
    [tileUrlString replaceOccurrencesOfString:@"{x}" withString:[NSString stringWithFormat:@"%li", (long)path.x] options:0 range:NSMakeRange(0, tileUrlString.length)];
    [tileUrlString replaceOccurrencesOfString:@"{y}" withString:[NSString stringWithFormat:@"%li", (long)path.y] options:0 range:NSMakeRange(0, tileUrlString.length)];
    [tileUrlString replaceOccurrencesOfString:@"{z}" withString:[NSString stringWithFormat:@"%li", (long)path.z] options:0 range:NSMakeRange(0, tileUrlString.length)];

    NSURL *url = [NSURL URLWithString:tileUrlString];
    NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:url];
    [request setValue:self.AccessToken forHTTPHeaderField:@"Authorization"];
    NSURLSession *session = [NSURLSession sharedSession];
    [[session dataTaskWithRequest:request completionHandler:^(NSData *data, NSURLResponse *response, NSError *error) {
        result(data, error);
    }] resume];
}

@end
