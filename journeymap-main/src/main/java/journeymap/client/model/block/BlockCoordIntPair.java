/*    */ package journeymap.client.model.block;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockCoordIntPair
/*    */ {
/*    */   public int x;
/*    */   public int z;
/*    */   
/*    */   public BlockCoordIntPair() {
/* 16 */     setLocation(0, 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockCoordIntPair(int x, int z) {
/* 21 */     setLocation(x, z);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setLocation(int x, int z) {
/* 26 */     this.x = x;
/* 27 */     this.z = z;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 33 */     if (this == o)
/*    */     {
/* 35 */       return true;
/*    */     }
/* 37 */     if (o == null || getClass() != o.getClass())
/*    */     {
/* 39 */       return false;
/*    */     }
/*    */     
/* 42 */     BlockCoordIntPair that = (BlockCoordIntPair)o;
/*    */     
/* 44 */     if (this.x != that.x)
/*    */     {
/* 46 */       return false;
/*    */     }
/* 48 */     if (this.z != that.z)
/*    */     {
/* 50 */       return false;
/*    */     }
/*    */     
/* 53 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 59 */     int result = this.x;
/* 60 */     result = 31 * result + this.z;
/* 61 */     return result;
/*    */   }
/*    */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\model\block\BlockCoordIntPair.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */