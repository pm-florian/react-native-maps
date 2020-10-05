package com.airbnb.android.react.maps;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Tile;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.TileProvider;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AirMapSecuredUrlTile extends AirMapFeature {

  class AIRMapSecuredUrlTileProvider implements TileProvider {
    private static final int BUFFER_SIZE = 16 * 1024;
    private static final int TILE_SIZE = 256;
    private String urlTemplate;

    public AIRMapSecuredUrlTileProvider(String urlTemplate) {
      this.urlTemplate = urlTemplate;
    }

    @Override
    public Tile getTile(int x, int y, int z) {
      try {
        byte[] image = readTileImage(x, y, z);
        return image == null ? TileProvider.NO_TILE : new Tile(TILE_SIZE, TILE_SIZE, image);
      } catch (IOException e) {
        return TileProvider.NO_TILE;
      }
    }

    public void setUrlTemplate(String urlTemplate) {
      this.urlTemplate = urlTemplate;
    }

    private byte[] readTileImage(int x, int y, int z) throws IOException {
      if (AirMapSecuredUrlTile.this.flipY) {
        y = (1 << z) - y - 1;
      }
      URL url = this.getTileUrl(x, y, z);
      if (AirMapSecuredUrlTile.this.maximumZ > 0 && z > maximumZ ||
              AirMapSecuredUrlTile.this.minimumZ > 0 && z < minimumZ ||
              url == null
      ) {
        return null;
      }


      InputStream stream = null;
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestProperty("Authorization", AirMapSecuredUrlTile.this.accessToken);
      stream = connection.getInputStream();
      byte[] byteChunk = new byte[BUFFER_SIZE];
      int n;
      while((n = stream.read(byteChunk)) > 0) {
        outputStream.write(byteChunk, 0, n);
      }
      stream.close();
      return outputStream.toByteArray();
    }

    private URL getTileUrl(int x, int y, int z) {
      try {
        return new URL(this.urlTemplate
                .replace("{x}", Integer.toString(x))
                .replace("{y}", Integer.toString(y))
                .replace("{z}", Integer.toString(z)));
      } catch (MalformedURLException e) {
        return null;
      }
    }
  }

  private TileOverlayOptions tileOverlayOptions;
  private TileOverlay tileOverlay;
  private AIRMapSecuredUrlTileProvider tileProvider;

  private String urlTemplate;
  private float zIndex;
  private float maximumZ;
  private float minimumZ;
  private boolean flipY;
  private String accessToken;

  public AirMapSecuredUrlTile(Context context) {
    super(context);
  }

  public void setUrlTemplate(String urlTemplate) {
    this.urlTemplate = urlTemplate;
    if (tileProvider != null) {
      tileProvider.setUrlTemplate(urlTemplate);
    }
    if (tileOverlay != null) {
      tileOverlay.clearTileCache();
    }
  }

  public void setZIndex(float zIndex) {
    this.zIndex = zIndex;
    if (tileOverlay != null) {
      tileOverlay.setZIndex(zIndex);
    }
  }

  public void setMaximumZ(float maximumZ) {
    this.maximumZ = maximumZ;
    if (tileOverlay != null) {
      tileOverlay.clearTileCache();
    }
  }

  public void setMinimumZ(float minimumZ) {
    this.minimumZ = minimumZ;
    if (tileOverlay != null) {
      tileOverlay.clearTileCache();
    }
  }

  public void setFlipY(boolean flipY) {
    this.flipY = flipY;
    if (tileOverlay != null) {
      tileOverlay.clearTileCache();
    }
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
    if (tileOverlay != null) {
      tileOverlay.clearTileCache();
    }
  }

  public TileOverlayOptions getTileOverlayOptions() {
    if (tileOverlayOptions == null) {
      tileOverlayOptions = createTileOverlayOptions();
    }
    return tileOverlayOptions;
  }

  private TileOverlayOptions createTileOverlayOptions() {
    TileOverlayOptions options = new TileOverlayOptions();
    options.zIndex(zIndex);
    this.tileProvider = new AIRMapSecuredUrlTileProvider(this.urlTemplate);
    options.tileProvider(this.tileProvider);
    return options;
  }

  @Override
  public Object getFeature() {
    return tileOverlay;
  }

  @Override
  public void addToMap(GoogleMap map) {
    this.tileOverlay = map.addTileOverlay(getTileOverlayOptions());
  }

  @Override
  public void removeFromMap(GoogleMap map) {
    tileOverlay.remove();
  }
}
