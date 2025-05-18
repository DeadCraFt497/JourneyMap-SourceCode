/*     */ package journeymap.client.model.chunk;
/*     */ 
/*     */ import journeymap.client.model.block.BlockMD;
/*     */ import journeymap.client.model.map.MapType;
/*     */ import journeymap.common.helper.BiomeHelper;
/*     */ import journeymap.common.nbt.RegionData;
/*     */ import net.minecraft.class_1923;
/*     */ import net.minecraft.class_1959;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_2487;
/*     */ import net.minecraft.class_2680;
/*     */ import net.minecraft.class_2818;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NBTChunkMD
/*     */   extends ChunkMD
/*     */ {
/*     */   final class_2487 data;
/*     */   final class_2818 chunk;
/*     */   final class_1923 chunkPos;
/*     */   final MapType mapType;
/*     */   
/*     */   public NBTChunkMD(class_2818 chunk, class_1923 chunkPos, class_2487 data, MapType mapType) {
/*  28 */     super(chunk, false);
/*  29 */     this.chunk = chunk;
/*  30 */     this.mapType = mapType;
/*  31 */     this.data = data;
/*  32 */     this.chunkPos = chunkPos;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasChunk() {
/*  43 */     return (this.data != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public class_2818 getChunk() {
/*  48 */     return this.chunk;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPrecipitationHeight(int localX, int localZ) {
/*  61 */     return getPrecipitationHeight(getBlockPos(localX, 0, localZ));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public class_2680 getChunkBlockState(class_2338 blockPos) {
/*  67 */     class_2680 state = getBlockState(blockPos);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  72 */     return state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSavedLightValue(int localX, int y, int localZ) {
/*  86 */     class_2338 pos = getBlockPos(localX, y, localZ);
/*  87 */     return getGetLightValue(pos).intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockMD getBlockMD(class_2338 blockPos) {
/* 100 */     return BlockMD.get(getBlockState(blockPos));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBlockSeeTheSky(int localX, int y, int localZ) {
/* 106 */     return !this.mapType.isUnderground();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPrecipitationHeight(class_2338 blockPos) {
/* 118 */     return getTopY(blockPos).intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHeight(class_2338 blockPos) {
/* 124 */     class_2338 pos = new class_2338(toWorldX(blockPos.method_10263()), blockPos.method_10264(), toWorldZ(blockPos.method_10260()));
/* 125 */     Integer surfaceY = getSurfaceY(pos);
/* 126 */     if (surfaceY == null)
/*     */     {
/* 128 */       surfaceY = getTopY(pos);
/*     */     }
/* 130 */     return surfaceY.intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockMD getBlockMD(int localX, int y, int localZ) {
/* 135 */     return BlockMD.get(getBlockState(getBlockPos(localX, y, localZ)));
/*     */   }
/*     */ 
/*     */   
/*     */   public class_1959 getBiome(class_2338 blockPos) {
/* 140 */     class_2487 blockData = getBlockNBT(blockPos);
/* 141 */     if (blockData.method_10545("biome_name")) {
/*     */       
/* 143 */       String biomeName = blockData.method_10558("biome_name").get();
/* 144 */       return BiomeHelper.getBiomeFromResourceString(biomeName);
/*     */     } 
/* 146 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Integer getTopY(class_2338 blockPos) {
/* 151 */     class_2487 blockData = getBlockNBT(blockPos);
/* 152 */     if (blockData.method_10545("top_y"))
/*     */     {
/* 154 */       return blockData.method_10550("top_y").get();
/*     */     }
/*     */     
/* 157 */     return Integer.valueOf(blockPos.method_10264());
/*     */   }
/*     */ 
/*     */   
/*     */   public Integer getGetLightValue(class_2338 blockPos) {
/* 162 */     class_2487 blockData = getBlockNBT(blockPos);
/* 163 */     if (blockData.method_10545("light_value"))
/*     */     {
/* 165 */       return blockData.method_10550("light_value").get();
/*     */     }
/*     */     
/* 168 */     return Integer.valueOf(0);
/*     */   }
/*     */ 
/*     */   
/*     */   private Integer getSurfaceY(class_2338 blockPos) {
/* 173 */     class_2487 blockData = getBlockNBT(blockPos);
/* 174 */     if (blockData.method_10545("surface_y"))
/*     */     {
/* 176 */       return blockData.method_10550("surface_y").get();
/*     */     }
/* 178 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public class_2680 getBlockState(class_2338 blockPos) {
/* 184 */     class_2487 blockData = getBlockNBT(blockPos);
/* 185 */     class_2680 blockState = RegionData.getBlockState(blockData, blockPos, this.mapType);
/* 186 */     return blockState;
/*     */   }
/*     */ 
/*     */   
/*     */   private class_2487 getBlockNBT(class_2338 blockPos) {
/* 191 */     return RegionData.getBlockDataForChunk(this.data, blockPos.method_10263(), blockPos.method_10260());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean fromNbt() {
/* 197 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\model\chunk\NBTChunkMD.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */