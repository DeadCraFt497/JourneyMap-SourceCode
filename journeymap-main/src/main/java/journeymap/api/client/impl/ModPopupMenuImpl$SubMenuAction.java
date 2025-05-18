package journeymap.api.client.impl;

import journeymap.api.v2.client.fullscreen.ModPopupMenu;
import journeymap.client.ui.component.buttons.Button;
import net.minecraft.class_2338;

public interface SubMenuAction extends ModPopupMenu.Action {
  default void doAction(class_2338 blockPos) {}
  
  void doAction(class_2338 paramclass_2338, Button paramButton);
  
  void onHoverState(class_2338 paramclass_2338, Button paramButton, boolean paramBoolean);
}


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\api\client\impl\ModPopupMenuImpl$SubMenuAction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */