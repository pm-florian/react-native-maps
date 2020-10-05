package com.airbnb.android.react.maps;

import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;

public class AirMapSecuredUrlTileManager extends ViewGroupManager<AirMapSecuredUrlTile> {
  private DisplayMetrics metrics;

  public AirMapSecuredUrlTileManager(ReactApplicationContext reactContext) {
    super();
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
      metrics = new DisplayMetrics();
      ((WindowManager) reactContext.getSystemService(Context.WINDOW_SERVICE))
          .getDefaultDisplay()
          .getRealMetrics(metrics);
    } else {
      metrics = reactContext.getResources().getDisplayMetrics();
    }
  }

  @Override
  public String getName() {
    return "AIRMapSecuredUrlTile";
  }

  @Override
  public AirMapSecuredUrlTile createViewInstance(ThemedReactContext context) {
    return new AirMapSecuredUrlTile(context);
  }

  @ReactProp(name = "urlTemplate")
  public void setUrlTemplate(AirMapSecuredUrlTile view, String urlTemplate) {
    view.setUrlTemplate(urlTemplate);
  }

  @ReactProp(name = "zIndex", defaultFloat = -1.0f)
  public void setZIndex(AirMapSecuredUrlTile view, float zIndex) {
    view.setZIndex(zIndex);
  }

  @ReactProp(name = "minimumZ", defaultFloat = 0.0f)
  public void setMinimumZ(AirMapSecuredUrlTile view, float minimumZ) {
    view.setMinimumZ(minimumZ);
  }

  @ReactProp(name = "maximumZ", defaultFloat = 100.0f)
  public void setMaximumZ(AirMapSecuredUrlTile view, float maximumZ) {
    view.setMaximumZ(maximumZ);
  }

  @ReactProp(name = "flipY", defaultBoolean = false)
  public void setFlipY(AirMapSecuredUrlTile view, boolean flipY) {
    view.setFlipY(flipY);
  }

  @ReactProp(name = "accessToken")
  public void setAccessToken(AirMapSecuredUrlTile view, String accessToken) {
    view.setAccessToken(accessToken);
  }

}
