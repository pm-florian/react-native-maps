//
//  AIRSecuredUrlTileOverlay.m
//  AirMaps
//
//  Created by cascadian on 3/19/16.
//  Copyright © 2016. All rights reserved.
//

#import "AIRMapSecuredUrlTile.h"
#import <React/UIView+React.h>
#import "AIRMapSecuredUrlTileOverlay.h"

@implementation AIRMapSecuredUrlTile {
    BOOL _urlTemplateSet;
    BOOL _accessTokenSet;
    BOOL _tileSizeSet;
}

- (void)setShouldReplaceMapContent:(BOOL)shouldReplaceMapContent
{
  _shouldReplaceMapContent = shouldReplaceMapContent;
  if(self.tileOverlay) {
    self.tileOverlay.canReplaceMapContent = _shouldReplaceMapContent;
  }
  [self update];
}

- (void)setMaximumZ:(NSUInteger)maximumZ
{
  _maximumZ = maximumZ;
  if(self.tileOverlay) {
    self.tileOverlay.maximumZ = _maximumZ;
  }
  [self update];
}

- (void)setMinimumZ:(NSUInteger)minimumZ
{
  _minimumZ = minimumZ;
  if(self.tileOverlay) {
    self.tileOverlay.minimumZ = _minimumZ;
  }
  [self update];
}

- (void)setFlipY:(BOOL)flipY
{
  _flipY = flipY;
  if (self.tileOverlay) {
    self.tileOverlay.geometryFlipped = _flipY;
  }
}

- (void)setUrlTemplate:(NSString *)urlTemplate{
    _urlTemplate = urlTemplate;
    _urlTemplateSet = YES;
    [self createTileOverlayAndRendererIfPossible];
    [self update];
}

- (void)setAccessToken:(NSString *)accessToken{
    _accessToken = accessToken;
    _accessTokenSet = YES;
}

- (void)setTileSize:(CGFloat)tileSize{
    _tileSize = tileSize;
    _tileSizeSet = YES;
    [self createTileOverlayAndRendererIfPossible];
    [self update];
}

- (void) createTileOverlayAndRendererIfPossible
{
    if (!_urlTemplateSet || !_accessTokenSet) return;
    self.tileOverlay = [[AIRMapSecuredUrlTileOverlay alloc] initWithURLTemplateAndAccessToken:self.urlTemplate :_accessToken];

    self.tileOverlay.canReplaceMapContent = self.shouldReplaceMapContent;

    if(self.minimumZ) {
        self.tileOverlay.minimumZ = self.minimumZ;
    }
    if (self.maximumZ) {
        self.tileOverlay.maximumZ = self.maximumZ;
    }
    if (self.flipY) {
        self.tileOverlay.geometryFlipped = self.flipY;
    }
    if (_tileSizeSet) {
        self.tileOverlay.tileSize = CGSizeMake(self.tileSize, self.tileSize);
    }
    self.renderer = [[MKTileOverlayRenderer alloc] initWithTileOverlay:self.tileOverlay];
}

- (void) update
{
    if (!_renderer) return;
    
    if (_map == nil) return;
    [_map removeOverlay:self];
    [_map addOverlay:self level:MKOverlayLevelAboveLabels];
    for (id<MKOverlay> overlay in _map.overlays) {
        if ([overlay isKindOfClass:[AIRMapSecuredUrlTile class]]) {
            continue;
        }
        [_map removeOverlay:overlay];
        [_map addOverlay:overlay];
    }
}

#pragma mark MKOverlay implementation

- (CLLocationCoordinate2D) coordinate
{
    return self.tileOverlay.coordinate;
}

- (MKMapRect) boundingMapRect
{
    return self.tileOverlay.boundingMapRect;
}

- (BOOL)canReplaceMapContent
{
    return self.tileOverlay.canReplaceMapContent;
}

@end
