/*    */ package journeymap.client.task.main;
/*    */ 
/*    */ import journeymap.client.JourneymapClient;
/*    */ import journeymap.client.cartography.color.ColorManager;
/*    */ import journeymap.client.data.DataCache;
/*    */ import journeymap.client.task.multi.MapPlayerTask;
/*    */ import net.minecraft.class_310;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EnsureCurrentColorsTask
/*    */   implements IMainThreadTask
/*    */ {
/*    */   final boolean forceRemap;
/*    */   
/*    */   public EnsureCurrentColorsTask() {
/* 23 */     this(false);
/*    */   }
/*    */ 
/*    */   
/*    */   public EnsureCurrentColorsTask(boolean forceRemap) {
/* 28 */     this.forceRemap = forceRemap;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IMainThreadTask perform(class_310 mc, JourneymapClient jm) {
/* 34 */     if (this.forceRemap)
/*    */     {
/* 36 */       DataCache.INSTANCE.resetBlockMetadata();
/*    */     }
/* 38 */     ColorManager.INSTANCE.ensureCurrent();
/*    */ 
/*    */     
/* 41 */     if (this.forceRemap)
/*    */     {
/* 43 */       MapPlayerTask.forceNearbyRemap();
/*    */     }
/*    */     
/* 46 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 52 */     return "EnsureCurrentColorsTask";
/*    */   }
/*    */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\task\main\EnsureCurrentColorsTask.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */