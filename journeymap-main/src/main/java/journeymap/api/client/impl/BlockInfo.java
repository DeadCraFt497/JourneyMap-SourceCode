/*     */ package journeymap.api.client.impl;
/*     */ 
/*     */ import journeymap.api.v2.client.fullscreen.IBlockInfo;
/*     */ import net.minecraft.class_1923;
/*     */ import net.minecraft.class_1959;
/*     */ import net.minecraft.class_2248;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_2680;
/*     */ import net.minecraft.class_2818;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockInfo
/*     */   implements IBlockInfo
/*     */ {
/*     */   private class_2338 blockPos;
/*     */   private class_2248 block;
/*     */   private class_2680 blockState;
/*     */   private class_1959 biome;
/*     */   private class_2818 chunk;
/*     */   private class_1923 chunkPos;
/*     */   private Integer regionX;
/*     */   private Integer regionZ;
/*     */   
/*     */   private BlockInfo(Builder builder) {
/*  26 */     this.blockPos = builder.blockPos;
/*  27 */     this.block = builder.block;
/*  28 */     this.blockState = builder.blockState;
/*  29 */     this.biome = builder.biome;
/*  30 */     this.chunk = builder.chunk;
/*  31 */     this.chunkPos = builder.chunkPos;
/*  32 */     this.regionX = builder.regionX;
/*  33 */     this.regionZ = builder.regionZ;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public class_2338 getBlockPos() {
/*  39 */     return this.blockPos;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public class_2248 getBlock() {
/*  45 */     return this.block;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public class_2680 getBlockState() {
/*  51 */     return this.blockState;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public class_1959 getBiome() {
/*  57 */     return this.biome;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public class_2818 getChunk() {
/*  63 */     return this.chunk;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public class_1923 getChunkPos() {
/*  69 */     return this.chunkPos;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getRegionX() {
/*  75 */     return this.regionX;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getRegionZ() {
/*  81 */     return this.regionZ;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Builder builder() {
/*  86 */     return new Builder();
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Builder
/*     */   {
/*     */     private class_2338 blockPos;
/*     */     private class_2248 block;
/*     */     private class_2680 blockState;
/*     */     private class_1959 biome;
/*     */     private class_2818 chunk;
/*     */     private class_1923 chunkPos;
/*     */     private Integer regionX;
/*     */     private Integer regionZ;
/*     */     
/*     */     public BlockInfo build() {
/* 102 */       return new BlockInfo(this);
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder withBlockPos(class_2338 blockPos) {
/* 107 */       this.blockPos = blockPos;
/* 108 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder withBlock(class_2248 block) {
/* 113 */       this.block = block;
/* 114 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder withBlockState(class_2680 blockState) {
/* 119 */       this.blockState = blockState;
/* 120 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder withBiome(class_1959 biome) {
/* 125 */       this.biome = biome;
/* 126 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder withChunk(class_2818 chunk) {
/* 131 */       this.chunk = chunk;
/* 132 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder withChunkPos(class_1923 chunkPos) {
/* 137 */       this.chunkPos = chunkPos;
/* 138 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder withRegionX(Integer regionX) {
/* 143 */       this.regionX = regionX;
/* 144 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder withRegionZ(Integer regionZ) {
/* 149 */       this.regionZ = regionZ;
/* 150 */       return this;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\api\client\impl\BlockInfo.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */