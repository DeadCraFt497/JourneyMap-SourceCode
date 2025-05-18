/*    */ package journeymap.client.task.main;
/*    */ 
/*    */ import journeymap.client.JourneymapClient;
/*    */ import journeymap.client.data.DataCache;
/*    */ import journeymap.client.io.ThemeLoader;
/*    */ import journeymap.client.log.JMLogger;
/*    */ import journeymap.client.ui.UIManager;
/*    */ import journeymap.client.ui.fullscreen.Fullscreen;
/*    */ import journeymap.client.ui.minimap.MiniMap;
/*    */ import journeymap.client.waypoint.WaypointHandler;
/*    */ import journeymap.common.Journeymap;
/*    */ import net.minecraft.class_310;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SoftResetTask
/*    */   implements IMainThreadTask
/*    */ {
/* 25 */   private static String NAME = "Tick." + SoftResetTask.class.getSimpleName();
/* 26 */   Logger logger = Journeymap.getLogger();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void queue() {
/* 34 */     JourneymapClient.getInstance().queueMainThreadTask(new SoftResetTask());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IMainThreadTask perform(class_310 mc, JourneymapClient jm) {
/* 40 */     jm.loadConfigProperties();
/* 41 */     JMLogger.setLevelFromProperties();
/* 42 */     DataCache.INSTANCE.purge();
/* 43 */     UIManager.INSTANCE.reset();
/* 44 */     WaypointHandler.getInstance().reset();
/* 45 */     ThemeLoader.getCurrentTheme(true);
/* 46 */     MiniMap.state().requireRefresh();
/* 47 */     Fullscreen.state().requireRefresh();
/* 48 */     UIManager.INSTANCE.getMiniMap().updateDisplayVars(true);
/* 49 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 55 */     return NAME;
/*    */   }
/*    */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\task\main\SoftResetTask.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */