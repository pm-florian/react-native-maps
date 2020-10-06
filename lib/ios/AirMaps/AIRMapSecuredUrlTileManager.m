//
//  AIRMapSecuredUrlTileManager.m
//  AirMaps
//
//  Created by cascadian on 3/19/16.
//  Copyright © 2016. All rights reserved.
//

#import <React/RCTBridge.h>
#import <React/RCTConvert.h>
#import <React/RCTConvert+CoreLocation.h>
#import <React/RCTEventDispatcher.h>
#import <React/RCTViewManager.h>
#import <React/UIView+React.h>
#import "AIRMapMarker.h"
#import "AIRMapSecuredUrlTile.h"

#import "AIRMapSecuredUrlTileManager.h"

@interface AIRMapSecuredUrlTileManager()

@end

@implementation AIRMapSecuredUrlTileManager


RCT_EXPORT_MODULE()

- (UIView *)view
{
    AIRMapSecuredUrlTile *tile = [AIRMapSecuredUrlTile new];
    return tile;
}

RCT_EXPORT_VIEW_PROPERTY(urlTemplate, NSString)
RCT_EXPORT_VIEW_PROPERTY(accessToken, NSString)
RCT_EXPORT_VIEW_PROPERTY(maximumZ, NSInteger)
RCT_EXPORT_VIEW_PROPERTY(minimumZ, NSInteger)
RCT_EXPORT_VIEW_PROPERTY(flipY, BOOL)
RCT_EXPORT_VIEW_PROPERTY(shouldReplaceMapContent, BOOL)
RCT_EXPORT_VIEW_PROPERTY(tileSize, CGFloat)

@end
