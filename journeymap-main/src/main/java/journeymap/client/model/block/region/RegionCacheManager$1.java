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
/*    */ class null
/*    */   implements RemovalListener<RegionKey, RegionHolder>
/*    */ {
/*    */   null(RegionCacheManager this$0) {}
/*    */   
/*    */   @ParametersAreNonnullByDefault
/*    */   public void onRemoval(RemovalNotification<RegionKey, RegionHolder> notification) {
/* 27 */     RegionHolder holder = (RegionHolder)notification.getValue();
/* 28 */     if (holder != null) {
/*    */       
/* 30 */       int count = holder.writeImage(false);
/* 31 */       if (count > 0 && Journeymap.getLogger().isDebugEnabled())
/*    */       {
/* 33 */         Journeymap.getLogger().debug("Wrote to disk before removal from cache: " + String.valueOf(holder));
/*    */       }
/* 35 */       holder.clear();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\model\region\RegionCacheManager$1.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */