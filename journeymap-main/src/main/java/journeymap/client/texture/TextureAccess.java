package journeymap.client.texture;

import net.minecraft.class_1043;

public interface TextureAccess {
  int journeymap$getWidth();
  
  int journeymap$getHeight();
  
  void journeymap$setDisplayWidth(int paramInt);
  
  void journeymap$setDisplayHeight(int paramInt);
  
  class_1043 journeymap$getScaledImage(float paramFloat);
  
  void journeymap$putScale(float paramFloat, class_1043 paramclass_1043);
  
  boolean journeymap$hasImage();
}


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\texture\TextureAccess.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */