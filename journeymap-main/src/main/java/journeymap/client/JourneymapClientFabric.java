/*    */ package journeymap.client;
/*    */ 
/*    */ import journeymap.client.event.FabricEventHandlerManager;
/*    */ import journeymap.client.event.FabricKeyEvents;
/*    */ import journeymap.client.event.handlers.keymapping.KeyEvent;
/*    */ import net.fabricmc.api.ClientModInitializer;
/*    */ import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
/*    */ import net.minecraft.class_310;
/*    */ 
/*    */ public class JourneymapClientFabric
/*    */   implements ClientModInitializer {
/*    */   private final JourneymapClient client;
/*    */   
/*    */   public JourneymapClientFabric() {
/* 15 */     this.client = new JourneymapClient();
/* 16 */     this.client.setKeyEvents((KeyEvent)new FabricKeyEvents());
/* 17 */     FabricEventHandlerManager.getInstance();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onInitializeClient() {
/* 23 */     ClientLifecycleEvents.CLIENT_STARTED.register(listener -> {
/*    */           this.client.commonSetup();
/*    */           this.client.init();
/*    */           this.client.postInit();
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\JourneymapClientFabric.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */