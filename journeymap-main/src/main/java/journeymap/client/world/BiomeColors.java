/*    */ package journeymap.client.world;
/*    */ import journeymap.client.model.block.BiomeMD;
/*    */ import net.minecraft.class_1920;
/*    */ import net.minecraft.class_1959;
/*    */ import net.minecraft.class_2338;
/*    */ import net.minecraft.class_6539;
/*    */ 
/*    */ public class BiomeColors {
/*    */   static {
/* 10 */     GRASS_COLOR_RESOLVER = ((biome, x, z) -> BiomeMD.get(biome).getGrassColor(x, z));
/* 11 */     FOLIAGE_COLOR_RESOLVER = ((biome, x, z) -> BiomeMD.get(biome).getFoliageColor());
/* 12 */     WATER_COLOR_RESOLVER = ((biome, x, z) -> BiomeMD.get(biome).getWaterColor());
/*    */   }
/*    */   
/*    */   public static final class_6539 GRASS_COLOR_RESOLVER;
/*    */   public static final class_6539 FOLIAGE_COLOR_RESOLVER;
/*    */   public static final class_6539 WATER_COLOR_RESOLVER;
/*    */   
/*    */   private static int getAverageColor(class_1920 level, class_2338 blockPos, class_6539 colorResolver) {
/* 20 */     return level.method_23752(blockPos, colorResolver);
/*    */   }
/*    */ 
/*    */   
/*    */   public static int getAverageGrassColor(class_1920 level, class_2338 blockPos) {
/* 25 */     return getAverageColor(level, blockPos, GRASS_COLOR_RESOLVER);
/*    */   }
/*    */ 
/*    */   
/*    */   public static int getAverageFoliageColor(class_1920 level, class_2338 blockPos) {
/* 30 */     return getAverageColor(level, blockPos, FOLIAGE_COLOR_RESOLVER);
/*    */   }
/*    */ 
/*    */   
/*    */   public static int getAverageWaterColor(class_1920 level, class_2338 blockPos) {
/* 35 */     return getAverageColor(level, blockPos, WATER_COLOR_RESOLVER);
/*    */   }
/*    */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\world\BiomeColors.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */