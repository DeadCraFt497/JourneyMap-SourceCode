/*    */ package journeymap.client.model.region;
/*    */ 
/*    */ import com.google.common.cache.RemovalListener;
/*    */ import com.google.common.cache.RemovalNotification;
/*    */ import javax.annotation.ParametersAreNonnullByDefault;
/*    */ import journeymap.common.Journeymap;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class null
/*    */   implements RemovalListener<RegionImageSet.Key, RegionImageSet>
/*    */ {
/*    */   null(RegionImageCache this$0) {}
/*    */   
/*    */   @ParametersAreNonnullByDefault
/*    */   public void onRemoval(RemovalNotification<RegionImageSet.Key, RegionImageSet> notification) {
/* 64 */     RegionImageSet regionImageSet = (RegionImageSet)notification.getValue();
/* 65 */     if (regionImageSet != null) {
/*    */       
/* 67 */       int count = regionImageSet.writeToDisk(false);
/* 68 */       if (count > 0 && Journeymap.getLogger().isDebugEnabled())
/*    */       {
/* 70 */         Journeymap.getLogger().debug("Wrote to disk before removal from cache: " + String.valueOf(regionImageSet));
/*    */       }
/* 72 */       regionImageSet.clear();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\model\region\RegionImageCache$1.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */