/*    */ package journeymap.client.model.region;
/*    */ 
/*    */ import journeymap.client.texture.RegionTexture;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RegionHolder
/*    */ {
/*    */   private RegionTexture texture;
/*    */   
/*    */   public RegionHolder(RegionKey key) {}
/*    */   
/*    */   public void clear() {
/* 17 */     this.texture.close();
/*    */   }
/*    */ 
/*    */   
/*    */   public int writeImage(boolean b) {
/* 22 */     return 1;
/*    */   }
/*    */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\model\region\RegionHolder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */