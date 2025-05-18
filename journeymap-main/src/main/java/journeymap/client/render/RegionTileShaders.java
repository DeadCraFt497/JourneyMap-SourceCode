/*    */ package journeymap.client.render;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import journeymap.client.Constants;
/*    */ import journeymap.common.properties.config.StringField;
/*    */ 
/*    */ public class RegionTileShaders
/*    */   implements StringField.ValuesProvider
/*    */ {
/* 13 */   public static final List<String> REGION_SHADERS = List.of("default", "grayscale", "sepia_1", "sepia_2", "sepia_3");
/* 14 */   private static final Map<String, ShaderKeys> idMap = new HashMap<>();
/* 15 */   private static final Map<String, ShaderKeys> keyMap = new HashMap<>();
/* 16 */   private static final Int2ObjectOpenHashMap<ShaderKeys> indexMap = new Int2ObjectOpenHashMap();
/*    */ 
/*    */   
/*    */   static {
/* 20 */     int index = 1;
/* 21 */     for (String id : REGION_SHADERS) {
/*    */       
/* 23 */       ShaderKeys shader = new ShaderKeys(id, index);
/* 24 */       idMap.put(id, shader);
/* 25 */       keyMap.put(shader.key(), shader);
/* 26 */       indexMap.put(index, shader);
/* 27 */       index++;
/*    */     } 
/*    */   }
/*    */   public static final class ShaderKeys extends Record { private final String id; private final int index;
/* 31 */     public ShaderKeys(String id, int index) { this.id = id; this.index = index; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Ljourneymap/client/render/RegionTileShaders$ShaderKeys;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #31	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/* 31 */       //   0	7	0	this	Ljourneymap/client/render/RegionTileShaders$ShaderKeys; } public String id() { return this.id; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Ljourneymap/client/render/RegionTileShaders$ShaderKeys;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #31	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Ljourneymap/client/render/RegionTileShaders$ShaderKeys; } public final boolean equals(Object o) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Ljourneymap/client/render/RegionTileShaders$ShaderKeys;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #31	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Ljourneymap/client/render/RegionTileShaders$ShaderKeys;
/* 31 */       //   0	8	1	o	Ljava/lang/Object; } public int index() { return this.index; }
/*    */ 
/*    */     
/*    */     public String key() {
/* 35 */       return String.format("jm.common.map_filter.%s", new Object[] { id() });
/*    */     }
/*    */ 
/*    */     
/*    */     public String name() {
/* 40 */       return Constants.getString("jm.common.map_filter.%s", new Object[] { id() });
/*    */     }
/*    */ 
/*    */     
/*    */     public String tooltip() {
/* 45 */       return Constants.getString("jm.common.map_filter.%s.tooltip", new Object[] { id() });
/*    */     } }
/*    */ 
/*    */ 
/*    */   
/*    */   public static int from(String key) {
/* 51 */     return ((ShaderKeys)keyMap.get(key)).index;
/*    */   }
/*    */ 
/*    */   
/*    */   public static String from(int key) {
/* 56 */     return ((ShaderKeys)indexMap.get(key)).id();
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> getStrings() {
/* 61 */     return REGION_SHADERS.stream().map(value -> ((ShaderKeys)idMap.get(value)).name()).toList();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getDefaultString() {
/* 67 */     return ((ShaderKeys)idMap.get(REGION_SHADERS.getFirst())).name();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getTooltip(String value) {
/* 73 */     return ((ShaderKeys)keyMap.get(value)).tooltip();
/*    */   }
/*    */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\render\RegionTileShaders.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */