/*     */ package journeymap.client.model.chunk;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.HashMap;
/*     */ import java.util.Optional;
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nullable;
/*     */ import journeymap.client.JourneymapClient;
/*     */ import journeymap.client.model.block.BlockDataArrays;
/*     */ import journeymap.client.model.block.BlockFlag;
/*     */ import journeymap.client.model.block.BlockMD;
/*     */ import journeymap.client.model.map.MapType;
/*     */ import journeymap.client.world.JmBlockAccess;
/*     */ import journeymap.common.Journeymap;
/*     */ import net.minecraft.class_1923;
/*     */ import net.minecraft.class_1937;
/*     */ import net.minecraft.class_1944;
/*     */ import net.minecraft.class_1959;
/*     */ import net.minecraft.class_1972;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_2680;
/*     */ import net.minecraft.class_2804;
/*     */ import net.minecraft.class_2818;
/*     */ import net.minecraft.class_2902;
/*     */ import net.minecraft.class_2919;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_3218;
/*     */ import net.minecraft.class_4076;
/*     */ import net.minecraft.class_5321;
/*     */ import net.minecraft.class_638;
/*     */ import net.minecraft.class_6880;
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
/*     */ public class ChunkMD
/*     */ {
/*     */   public static final String PROP_IS_SLIME_CHUNK = "isSlimeChunk";
/*     */   public static final String PROP_LOADED = "loaded";
/*     */   public static final String PROP_LAST_RENDERED = "lastRendered";
/*     */   protected final WeakReference<class_2818> chunkReference;
/*     */   private final class_1923 coord;
/*  61 */   private final HashMap<String, Serializable> properties = new HashMap<>();
/*  62 */   private BlockDataArrays blockDataArrays = new BlockDataArrays();
/*     */ 
/*     */   
/*     */   private final Random random;
/*     */ 
/*     */   
/*     */   protected class_2818 retainedChunk;
/*     */ 
/*     */   
/*     */   private class_2804 lights;
/*     */ 
/*     */   
/*     */   public ChunkMD(class_2818 chunk) {
/*  75 */     this(chunk, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChunkMD(class_2818 chunk, boolean forceRetain, class_2804 lights) {
/*  86 */     this(chunk, forceRetain);
/*  87 */     this.lights = lights;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChunkMD(class_2818 chunk, boolean forceRetain) {
/*  98 */     if (chunk == null)
/*     */     {
/* 100 */       throw new IllegalArgumentException("Chunk can't be null");
/*     */     }
/* 102 */     this.random = new Random();
/* 103 */     this.coord = new class_1923((chunk.method_12004()).field_9181, (chunk.method_12004()).field_9180);
/*     */ 
/*     */     
/* 106 */     setProperty("loaded", Long.valueOf(System.currentTimeMillis()));
/*     */ 
/*     */     
/* 109 */     this.properties.put("isSlimeChunk", Boolean.valueOf(isSlimeChunk(chunk)));
/*     */     
/* 111 */     this.chunkReference = new WeakReference<>(chunk);
/* 112 */     if (forceRetain)
/*     */     {
/* 114 */       this.retainedChunk = chunk;
/*     */     }
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
/*     */   public class_2680 getBlockState(int localX, int y, int localZ) {
/* 128 */     if (localX < 0 || localX > 15 || localZ < 0 || localZ > 15)
/*     */     {
/* 130 */       Journeymap.getLogger().warn("Expected local coords, got global coords");
/*     */     }
/* 132 */     return getBlockState(new class_2338(toWorldX(localX), y, toWorldZ(localZ)));
/*     */   }
/*     */ 
/*     */   
/*     */   public class_2680 getChunkBlockState(class_2338 blockPos) {
/* 137 */     return getChunk().method_8320(blockPos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public class_2680 getBlockState(class_2338 blockPos) {
/* 148 */     return JmBlockAccess.INSTANCE.method_8320(blockPos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockMD getBlockMD(class_2338 blockPos) {
/* 159 */     return BlockMD.getBlockMD(this, blockPos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public class_6880<class_1959> getBiomeHolder(class_2338 pos) {
/* 170 */     return getChunk().method_16359(pos.method_10263() >> 2, pos.method_10264() >> 2, pos.method_10260() >> 2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public class_1959 getBiome(class_2338 pos) {
/* 181 */     class_6880<class_1959> holder = getBiomeHolder(pos);
/* 182 */     return (holder != null) ? (class_1959)holder.comp_349() : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public class_5321<class_1959> getBiomeKey(class_2338 pos) {
/* 187 */     class_6880<class_1959> holder = getBiomeHolder(pos);
/* 188 */     if (holder != null) {
/*     */       
/* 190 */       Optional<class_5321<class_1959>> key = holder.method_40230();
/* 191 */       return key.orElse(class_1972.field_9451);
/*     */     } 
/* 193 */     return class_1972.field_9451;
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
/* 207 */     if (this.lights != null) {
/*     */       
/*     */       try {
/*     */ 
/*     */         
/* 212 */         int localY = class_4076.method_18684(y);
/* 213 */         return this.lights.method_12139(localX, localY, localZ);
/*     */       }
/* 215 */       catch (Exception exception) {}
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 223 */       return getChunk().method_12200().method_22336().method_15562(class_1944.field_9282).method_15543(getBlockPos(localX, y, localZ));
/*     */     }
/* 225 */     catch (ArrayIndexOutOfBoundsException e) {
/*     */ 
/*     */       
/* 228 */       return 1;
/*     */     } 
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
/*     */   public BlockMD getBlockMD(int localX, int y, int localZ) {
/* 242 */     return BlockMD.getBlockMD(this, getBlockPos(localX, y, localZ));
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
/*     */   public int ceiling(int localX, int localZ) {
/* 254 */     int y = getPrecipitationHeight(getBlockPos(localX, 0, localZ));
/* 255 */     class_2338 blockPos = null;
/*     */ 
/*     */     
/*     */     try {
/* 259 */       while (y >= getMinY().intValue())
/*     */       {
/* 261 */         blockPos = getBlockPos(localX, y, localZ);
/* 262 */         BlockMD blockMD = getBlockMD(blockPos);
/*     */         
/* 264 */         if (blockMD == null) {
/*     */           
/* 266 */           y--; continue;
/*     */         } 
/* 268 */         if (blockMD.isIgnore() || blockMD.hasFlag(BlockFlag.OpenToSky)) {
/*     */           
/* 270 */           y--; continue;
/*     */         } 
/* 272 */         if (canBlockSeeTheSky(localX, y, localZ))
/*     */         {
/* 274 */           y--;
/*     */         
/*     */         }
/*     */       
/*     */       }
/*     */ 
/*     */     
/*     */     }
/* 282 */     catch (Exception e) {
/*     */       
/* 284 */       Journeymap.getLogger().warn(String.valueOf(e) + " at " + String.valueOf(e), e);
/*     */     } 
/* 286 */     return Math.max(getMinY().intValue(), y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasChunk() {
/* 296 */     class_2818 chunk = this.chunkReference.get();
/* 297 */     boolean result = (chunk != null && !(chunk instanceof net.minecraft.class_2812));
/* 298 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHeight(class_2338 blockPos) {
/* 309 */     return getPrecipitationHeight(blockPos);
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
/*     */   public int getPrecipitationHeight(int localX, int localZ) {
/* 321 */     return getPrecipitationHeight(getBlockPos(localX, 0, localZ));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPrecipitationHeight(class_2338 blockPos) {
/* 332 */     if (!(JourneymapClient.getInstance().getCoreProperties()).ignoreHeightmaps.get().booleanValue())
/*     */     {
/* 334 */       return getChunk().method_12005(class_2902.class_2903.field_13202, blockPos.method_10263(), blockPos.method_10260());
/*     */     }
/* 336 */     return getChunk().method_31600();
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
/*     */   
/*     */   public int getLightOpacity(BlockMD blockMD, int localX, int y, int localZ) {
/* 351 */     return blockMD.getBlockState().method_26193();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Serializable getProperty(String name) {
/* 362 */     return this.properties.get(name);
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
/*     */   public Serializable getProperty(String name, Serializable defaultValue) {
/* 374 */     Serializable currentValue = getProperty(name);
/* 375 */     if (currentValue == null) {
/*     */       
/* 377 */       setProperty(name, defaultValue);
/* 378 */       currentValue = defaultValue;
/*     */     } 
/* 380 */     return currentValue;
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
/*     */   public Serializable setProperty(String name, Serializable value) {
/* 392 */     return this.properties.put(name, value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 398 */     return getCoord().hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 404 */     if (this == obj)
/*     */     {
/* 406 */       return true;
/*     */     }
/* 408 */     if (obj == null)
/*     */     {
/* 410 */       return false;
/*     */     }
/* 412 */     if (getClass() != obj.getClass())
/*     */     {
/* 414 */       return false;
/*     */     }
/* 416 */     ChunkMD other = (ChunkMD)obj;
/* 417 */     return getCoord().equals(other.getCoord());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public class_2818 getChunk() {
/* 427 */     class_2818 chunk = this.chunkReference.get();
/* 428 */     if (chunk == null)
/*     */     {
/* 430 */       throw new ChunkMissingException(getCoord());
/*     */     }
/* 432 */     return chunk;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public class_638 getWorld() {
/* 442 */     class_310 mc = class_310.method_1551();
/*     */     
/*     */     try {
/* 445 */       return (mc.field_1724 == null) ? mc.field_1687 : mc.field_1724.field_17892;
/*     */     }
/* 447 */     catch (Throwable t) {
/*     */ 
/*     */       
/* 450 */       return mc.field_1687;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWorldActualHeight() {
/* 462 */     return getWorld().method_8597().comp_653() + 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Boolean hasNoSky() {
/* 472 */     return Boolean.valueOf(!getWorld().method_8597().comp_645());
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
/*     */   public boolean canBlockSeeTheSky(int localX, int y, int localZ) {
/* 485 */     int i = localX & 0xF;
/* 486 */     int k = localZ & 0xF;
/* 487 */     if (this.lights != null)
/*     */     {
/* 489 */       return (getSavedLightValue(localX, y, localZ) > 0);
/*     */     }
/*     */     
/* 492 */     return getChunk().method_12200().method_8311(getBlockPos(localX, y, localZ));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public class_1923 getCoord() {
/* 502 */     return this.coord;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isSlimeChunk(class_2818 chunk) {
/* 512 */     if (chunk.method_12200() instanceof class_3218)
/*     */     {
/* 514 */       return (class_2919.method_12662((chunk.method_12004()).field_9181, (chunk.method_12004()).field_9180, ((class_3218)chunk.method_12200()).method_8412(), 987234911L).method_43048(10) == 0);
/*     */     }
/*     */ 
/*     */     
/* 518 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getLoaded() {
/* 529 */     return ((Long)getProperty("loaded", Long.valueOf(0L))).longValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetRenderTimes() {
/* 537 */     getRenderTimes().clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetRenderTime(MapType mapType) {
/* 547 */     getRenderTimes().put(mapType, Long.valueOf(0L));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetBlockData(MapType mapType) {
/* 557 */     getBlockData().get(mapType).clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected HashMap<MapType, Long> getRenderTimes() {
/* 567 */     Serializable<Object, Object> obj = this.properties.get("lastRendered");
/* 568 */     if (!(obj instanceof HashMap)) {
/*     */       
/* 570 */       obj = new HashMap<>();
/* 571 */       this.properties.put("lastRendered", obj);
/*     */     } 
/* 573 */     return (HashMap)obj;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getLastRendered(MapType mapType) {
/* 584 */     return ((Long)getRenderTimes().getOrDefault(mapType, Long.valueOf(0L))).longValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long setRendered(MapType mapType) {
/* 595 */     long now = System.currentTimeMillis();
/* 596 */     getRenderTimes().put(mapType, Long.valueOf(now));
/* 597 */     return now;
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
/*     */   public class_2338 getBlockPos(int localX, int y, int localZ) {
/* 610 */     return new class_2338(toWorldX(localX), y, toWorldZ(localZ));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int toWorldX(int localX) {
/* 621 */     return (this.coord.field_9181 << 4) + localX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int toWorldZ(int localZ) {
/* 632 */     return (this.coord.field_9180 << 4) + localZ;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockDataArrays getBlockData() {
/* 642 */     return this.blockDataArrays;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockDataArrays.DataArray<Integer> getBlockDataInts(MapType mapType) {
/* 653 */     return this.blockDataArrays.get(mapType).ints();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockDataArrays.DataArray<Float> getBlockDataFloats(MapType mapType) {
/* 664 */     return this.blockDataArrays.get(mapType).floats();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockDataArrays.DataArray<Boolean> getBlockDataBooleans(MapType mapType) {
/* 675 */     return this.blockDataArrays.get(mapType).booleans();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 681 */     return "ChunkMD{coord=" + String.valueOf(this.coord) + ", properties=" + String.valueOf(this.properties) + "}";
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
/*     */   public class_5321<class_1937> getDimension() {
/* 694 */     return getWorld().method_27983();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void stopChunkRetention() {
/* 702 */     this.retainedChunk = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasRetainedChunk() {
/* 707 */     return (this.retainedChunk != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public Integer getMinY() {
/* 712 */     return Integer.valueOf(getWorld().method_31607());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean fromNbt() {
/* 717 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public long getLongCoord() {
/* 722 */     return this.coord.method_8324();
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
/*     */   public static class ChunkMissingException
/*     */     extends RuntimeException
/*     */   {
/*     */     ChunkMissingException(class_1923 coord) {
/* 737 */       super("Chunk missing: " + String.valueOf(coord));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\model\chunk\ChunkMD.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */