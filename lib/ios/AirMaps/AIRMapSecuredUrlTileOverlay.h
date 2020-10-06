//
//  AIRMapSecuredUrlTileOverlay.h
//  AirMaps
//
//  Created by Florian on 06.10.20.
//  Copyright © 2020 Christopher. All rights reserved.
//

#import <MapKit/MapKit.h>

NS_ASSUME_NONNULL_BEGIN

@interface AIRMapSecuredUrlTileOverlay : MKTileOverlay
@property (nonatomic) NSString *AccessToken;

- (id)initWithURLTemplateAndAccessToken:(NSString *)urlTemplate :(NSString *)accessToken;

@end

NS_ASSUME_NONNULL_END
