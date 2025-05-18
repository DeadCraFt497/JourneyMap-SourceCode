package journeymap.client.render.map;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import journeymap.api.v2.client.display.Context;
import journeymap.api.v2.client.util.UIState;
import journeymap.client.model.map.MapType;
import journeymap.client.ui.fullscreen.Fullscreen;
import net.minecraft.class_2338;

public interface Renderer {
  Fullscreen getFullscreen();
  
  class_2338 getBlockAtPixel(Point2D.Double paramDouble);
  
  MapType getMapType();
  
  UIState getUIState();
  
  Point2D.Double getPixel(double paramDouble1, double paramDouble2);
  
  void ensureOnScreen(Point2D paramPoint2D);
  
  Point2D.Double getBlockPixelInGrid(class_2338 paramclass_2338);
  
  Point2D.Double getBlockPixelInGrid(double paramDouble1, double paramDouble2);
  
  Point2D shiftWindowPosition(double paramDouble1, double paramDouble2, int paramInt1, int paramInt2);
  
  Point2D.Double getWindowPosition(Point2D.Double paramDouble);
  
  boolean isOnScreen(Point2D.Double paramDouble);
  
  boolean isOnScreen(Rectangle2D.Double paramDouble);
  
  int getZoom();
  
  int getMouseX();
  
  int getMouseY();
  
  Context.UI getContext();
  
  int getGridSize();
  
  int getWidth();
  
  int getHeight();
}


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\render\map\Renderer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */