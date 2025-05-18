package journeymap.client.render.draw;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import journeymap.api.v2.client.display.Overlay;
import journeymap.client.render.map.Renderer;
import org.jetbrains.annotations.Nullable;

public interface OverlayDrawStep extends DrawStep {
  Overlay getOverlay();
  
  Rectangle2D.Double getBounds();
  
  boolean isOnScreen(double paramDouble1, double paramDouble2, Renderer paramRenderer, double paramDouble3);
  
  void setTitlePosition(@Nullable Point2D.Double paramDouble);
  
  void setEnabled(boolean paramBoolean);
}


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\render\draw\OverlayDrawStep.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */