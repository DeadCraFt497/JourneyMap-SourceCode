/*    */ package journeymap.client.render;
/*    */ 
/*    */ import journeymap.client.Constants;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ShaderKeys
/*    */   extends Record
/*    */ {
/*    */   private final String id;
/*    */   private final int index;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Ljourneymap/client/render/RegionTileShaders$ShaderKeys;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #31	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Ljourneymap/client/render/RegionTileShaders$ShaderKeys;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Ljourneymap/client/render/RegionTileShaders$ShaderKeys;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #31	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Ljourneymap/client/render/RegionTileShaders$ShaderKeys;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object o) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Ljourneymap/client/render/RegionTileShaders$ShaderKeys;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #31	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Ljourneymap/client/render/RegionTileShaders$ShaderKeys;
/*    */     //   0	8	1	o	Ljava/lang/Object;
/*    */   }
/*    */   
/*    */   public ShaderKeys(String id, int index) {
/* 31 */     this.id = id; this.index = index; } public String id() { return this.id; } public int index() { return this.index; }
/*    */ 
/*    */   
/*    */   public String key() {
/* 35 */     return String.format("jm.common.map_filter.%s", new Object[] { id() });
/*    */   }
/*    */ 
/*    */   
/*    */   public String name() {
/* 40 */     return Constants.getString("jm.common.map_filter.%s", new Object[] { id() });
/*    */   }
/*    */ 
/*    */   
/*    */   public String tooltip() {
/* 45 */     return Constants.getString("jm.common.map_filter.%s.tooltip", new Object[] { id() });
/*    */   }
/*    */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\render\RegionTileShaders$ShaderKeys.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */