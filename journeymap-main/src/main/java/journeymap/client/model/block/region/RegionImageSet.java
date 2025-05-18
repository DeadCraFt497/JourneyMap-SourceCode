/*     */ package journeymap.client.model.region;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.util.StringJoiner;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.Executor;
/*     */ import journeymap.client.io.RegionImageHandler;
/*     */ import journeymap.client.model.chunk.ChunkMD;
/*     */ import journeymap.client.model.map.MapType;
/*     */ import journeymap.client.texture.ComparableNativeImage;
/*     */ import journeymap.client.texture.ImageUtil;
/*     */ import net.minecraft.class_1011;
/*     */ import net.minecraft.class_156;
/*     */ import net.minecraft.class_1937;
/*     */ import net.minecraft.class_5321;
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
/*     */ public class RegionImageSet
/*     */   extends ImageSet
/*     */ {
/*     */   protected final Key key;
/*     */   
/*     */   public RegionImageSet(Key key) {
/*  35 */     this.key = key;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ImageHolder getHolder(MapType mapType) {
/*  41 */     synchronized (this.imageHolders) {
/*     */       
/*  43 */       ImageHolder imageHolder = this.imageHolders.get(mapType);
/*  44 */       if (imageHolder == null || imageHolder.getImage() == null || (imageHolder.getImage()).field_4988 == 0L) {
/*     */ 
/*     */         
/*  47 */         File imageFile = RegionImageHandler.getRegionImageFile(getRegionCoord(), mapType);
/*     */ 
/*     */         
/*  50 */         imageHolder = addHolder(mapType, imageFile);
/*     */       } 
/*  52 */       return imageHolder;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ImageHolder getHolderAsyncLoad(MapType mapType) {
/*  59 */     synchronized (this.imageHolders) {
/*     */       
/*  61 */       ImageHolder imageHolder = this.imageHolders.get(mapType);
/*  62 */       if (imageHolder == null || imageHolder.getImage() == null || (imageHolder.getImage()).field_4988 == 0L)
/*     */       {
/*  64 */         CompletableFuture.supplyAsync(() -> RegionImageHandler.getRegionImageFile(getRegionCoord(), mapType), (Executor)class_156.method_18349())
/*  65 */           .whenComplete((file, throwable) -> addHolder(mapType, file));
/*     */       }
/*  67 */       return imageHolder;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ImageHolder getExistingHolder(MapType mapType) {
/*  74 */     synchronized (this.imageHolders) {
/*     */       
/*  76 */       return this.imageHolders.get(mapType);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ComparableNativeImage getChunkImage(ChunkMD chunkMd, MapType mapType) {
/*  82 */     RegionCoord regionCoord = getRegionCoord();
/*  83 */     class_1011 regionImage = getHolder(mapType).getImage();
/*     */     
/*  85 */     ComparableNativeImage sub = ImageUtil.getComparableSubImage(regionCoord
/*  86 */         .getXOffset((chunkMd.getCoord()).field_9181), regionCoord
/*  87 */         .getZOffset((chunkMd.getCoord()).field_9180), 16, 16, regionImage, false);
/*     */     
/*  89 */     return sub;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setChunkImage(ChunkMD chunkMd, MapType mapType, ComparableNativeImage chunkImage) {
/*  94 */     ImageHolder holder = getHolder(mapType);
/*  95 */     boolean wasBlank = holder.blank;
/*  96 */     if (chunkImage.isChanged() || wasBlank) {
/*     */       
/*  98 */       RegionCoord regionCoord = getRegionCoord();
/*  99 */       holder.partialImageUpdate((class_1011)chunkImage, regionCoord.getXOffset((chunkMd.getCoord()).field_9181), regionCoord.getZOffset((chunkMd.getCoord()).field_9180));
/*     */     } 
/* 101 */     if (wasBlank) {
/*     */       
/* 103 */       holder.getTexture();
/* 104 */       holder.finishPartialImageUpdates();
/* 105 */       RegionImageCache.INSTANCE.getRegionImageSet(getRegionCoord());
/*     */     } 
/* 107 */     chunkMd.setRendered(mapType);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasChunkUpdates() {
/* 112 */     synchronized (this.imageHolders) {
/*     */       
/* 114 */       for (ImageHolder holder : this.imageHolders.values()) {
/*     */         
/* 116 */         if (holder.partialUpdate)
/*     */         {
/* 118 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/* 122 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void finishChunkUpdates() {
/* 127 */     synchronized (this.imageHolders) {
/*     */       
/* 129 */       for (ImageHolder holder : this.imageHolders.values())
/*     */       {
/* 131 */         holder.finishPartialImageUpdates();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public RegionCoord getRegionCoord() {
/* 138 */     return RegionCoord.fromRegionPos(this.key.worldDir, this.key.regionX, this.key.regionZ, this.key.dimension);
/*     */   }
/*     */ 
/*     */   
/*     */   public long getOldestTimestamp() {
/* 143 */     long time = System.currentTimeMillis();
/* 144 */     synchronized (this.imageHolders) {
/*     */       
/* 146 */       for (ImageHolder holder : this.imageHolders.values()) {
/*     */         
/* 148 */         if (holder != null)
/*     */         {
/* 150 */           time = Math.min(time, holder.getImageTimestamp());
/*     */         }
/*     */       } 
/*     */     } 
/* 154 */     return time;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 160 */     return this.key.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 166 */     if (this == obj)
/*     */     {
/* 168 */       return true;
/*     */     }
/* 170 */     if (obj == null)
/*     */     {
/* 172 */       return false;
/*     */     }
/* 174 */     if (getClass() != obj.getClass())
/*     */     {
/* 176 */       return false;
/*     */     }
/* 178 */     return this.key.equals(((RegionImageSet)obj).key);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getImageSize() {
/* 184 */     return 512;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Key
/*     */   {
/*     */     private final File worldDir;
/*     */     private final int regionX;
/*     */     private final int regionZ;
/*     */     private final class_5321<class_1937> dimension;
/*     */     
/*     */     private Key(File worldDir, int regionX, int regionZ, class_5321<class_1937> dimension) {
/* 196 */       this.worldDir = worldDir;
/* 197 */       this.regionX = regionX;
/* 198 */       this.regionZ = regionZ;
/* 199 */       this.dimension = dimension;
/*     */     }
/*     */ 
/*     */     
/*     */     public static Key from(RegionCoord rCoord) {
/* 204 */       return new Key(rCoord.worldDir, rCoord.regionX, rCoord.regionZ, rCoord.dimension);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 210 */       if (this == o)
/*     */       {
/* 212 */         return true;
/*     */       }
/* 214 */       if (o == null || getClass() != o.getClass())
/*     */       {
/* 216 */         return false;
/*     */       }
/*     */       
/* 219 */       Key key = (Key)o;
/*     */       
/* 221 */       if (this.dimension != key.dimension)
/*     */       {
/* 223 */         return false;
/*     */       }
/* 225 */       if (this.regionX != key.regionX)
/*     */       {
/* 227 */         return false;
/*     */       }
/* 229 */       if (this.regionZ != key.regionZ)
/*     */       {
/* 231 */         return false;
/*     */       }
/* 233 */       if (!this.worldDir.equals(key.worldDir))
/*     */       {
/* 235 */         return false;
/*     */       }
/*     */       
/* 238 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 244 */       int result = this.worldDir.hashCode();
/* 245 */       result = 31 * result + this.regionX;
/* 246 */       result = 31 * result + this.regionZ;
/* 247 */       result = 31 * result + this.dimension.hashCode();
/* 248 */       return result;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 254 */       return (new StringJoiner(", ", Key.class.getSimpleName() + "[", "]"))
/* 255 */         .add("worldDir=" + String.valueOf(this.worldDir))
/* 256 */         .add("regionX=" + this.regionX)
/* 257 */         .add("regionZ=" + this.regionZ)
/* 258 */         .add("dimension=" + String.valueOf(this.dimension))
/* 259 */         .toString();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\model\region\RegionImageSet.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */