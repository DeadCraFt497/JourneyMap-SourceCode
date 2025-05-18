/*     */ package journeymap.client.model.region;
/*     */ 
/*     */ import com.google.common.base.MoreObjects;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.Path;
/*     */ import java.util.HashSet;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ import journeymap.client.io.RegionImageHandler;
/*     */ import journeymap.client.log.StatTimer;
/*     */ import journeymap.client.model.map.MapType;
/*     */ import journeymap.client.render.RenderWrapper;
/*     */ import journeymap.client.texture.ImageUtil;
/*     */ import journeymap.client.texture.RegionTexture;
/*     */ import journeymap.common.Journeymap;
/*     */ import journeymap.common.log.LogFormatter;
/*     */ import net.minecraft.class_1011;
/*     */ import net.minecraft.class_156;
/*     */ import net.minecraft.class_1923;
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.apache.logging.log4j.Logger;
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
/*     */ public class ImageHolder
/*     */ {
/*  36 */   static final Logger logger = Journeymap.getLogger();
/*     */   final MapType mapType;
/*     */   final Path imagePath;
/*     */   final int imageSize;
/*     */   boolean blank = true;
/*     */   boolean dirty = true;
/*     */   boolean partialUpdate;
/*  43 */   private volatile ReentrantLock writeLock = new ReentrantLock();
/*     */   private volatile RegionTexture texture;
/*     */   private boolean debug;
/*  46 */   private HashSet<class_1923> updatedChunks = new HashSet<>();
/*     */ 
/*     */   
/*     */   ImageHolder(MapType mapType, File imageFile, int imageSize) {
/*  50 */     this.mapType = mapType;
/*  51 */     this.imagePath = imageFile.toPath();
/*  52 */     this.imageSize = imageSize;
/*  53 */     this.debug = logger.isEnabled(Level.DEBUG);
/*  54 */     getTexture();
/*     */   }
/*     */ 
/*     */   
/*     */   File getFile() {
/*  59 */     return this.imagePath.toFile();
/*     */   }
/*     */ 
/*     */   
/*     */   MapType getMapType() {
/*  64 */     return this.mapType;
/*     */   }
/*     */ 
/*     */   
/*     */   class_1011 getImage() {
/*  69 */     return this.texture.getNativeImage();
/*     */   }
/*     */ 
/*     */   
/*     */   void setImage(class_1011 image) {
/*  74 */     this.texture.setNativeImage(image, true);
/*  75 */     setDirty();
/*     */   }
/*     */ 
/*     */   
/*     */   void partialImageUpdate(class_1011 imagePart, int startX, int startY) {
/*  80 */     this.writeLock.lock();
/*  81 */     StatTimer timer = StatTimer.get("ImageHolder.partialImageUpdate", 5, 500);
/*  82 */     timer.start();
/*     */     
/*     */     try {
/*  85 */       if (this.texture != null)
/*     */       {
/*  87 */         this.blank = false;
/*  88 */         int width = imagePart.method_4307();
/*  89 */         int height = imagePart.method_4323();
/*  90 */         for (int y = 0; y < height; y++) {
/*     */           
/*  92 */           for (int x = 0; x < width; x++)
/*     */           {
/*  94 */             this.texture.getNativeImage().method_61941(x + startX, y + startY, imagePart.method_61940(x, y));
/*     */           }
/*     */         } 
/*  97 */         this.partialUpdate = true;
/*  98 */         this.updatedChunks.add(new class_1923(startX, startY));
/*  99 */         setDirty();
/*     */       }
/*     */       else
/*     */       {
/* 103 */         logger.warn(String.valueOf(this) + " can't partialImageUpdate without a texture.");
/*     */       }
/*     */     
/*     */     } finally {
/*     */       
/* 108 */       timer.stop();
/* 109 */       this.writeLock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   void finishPartialImageUpdates() {
/* 115 */     this.writeLock.lock();
/*     */     
/*     */     try {
/* 118 */       if (this.partialUpdate && !this.updatedChunks.isEmpty())
/*     */       {
/* 120 */         class_1011 textureImage = this.texture.getNativeImage();
/*     */         
/* 122 */         this.texture.setNativeImage(textureImage, true, this.updatedChunks);
/* 123 */         setDirty();
/* 124 */         this.partialUpdate = false;
/* 125 */         this.updatedChunks.clear();
/*     */       }
/*     */     
/*     */     } finally {
/*     */       
/* 130 */       this.writeLock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasTexture() {
/* 136 */     return (this.texture != null && !this.texture.isDefunct());
/*     */   }
/*     */ 
/*     */   
/*     */   public RegionTexture getTexture() {
/* 141 */     if (!hasTexture()) {
/*     */       class_1011 image;
/* 143 */       if (!this.imagePath.toFile().exists()) {
/*     */ 
/*     */         
/* 146 */         File temp = new File(String.valueOf(this.imagePath) + ".new");
/* 147 */         if (temp.exists()) {
/*     */           
/* 149 */           Journeymap.getLogger().warn("Recovered image file: {}", temp);
/* 150 */           temp.renameTo(this.imagePath.toFile());
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 157 */         image = RegionImageHandler.readRegionImage(this.imagePath.toFile());
/*     */       }
/* 159 */       catch (Throwable t) {
/*     */         
/* 161 */         Journeymap.getLogger().warn("Error reading image {}, {}", this.imagePath, t);
/* 162 */         image = null;
/*     */       } 
/*     */       
/* 165 */       if (image == null || image.field_4988 == 0L || image.method_4307() != this.imageSize || image.method_4323() != this.imageSize) {
/*     */         
/* 167 */         image = ImageUtil.getNewBlankImage(this.imageSize, this.imageSize);
/* 168 */         this.blank = true;
/*     */       }
/*     */       else {
/*     */         
/* 172 */         this.blank = false;
/*     */       } 
/* 174 */       this.dirty = false;
/* 175 */       this.texture = new RegionTexture(image, this.imagePath.toString());
/*     */     } 
/* 177 */     return this.texture;
/*     */   }
/*     */ 
/*     */   
/*     */   private void setDirty() {
/* 182 */     this.dirty = true;
/*     */   }
/*     */ 
/*     */   
/*     */   boolean isDirty() {
/* 187 */     return this.dirty;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean writeToDisk(boolean async) {
/* 198 */     if (this.blank || this.texture == null || !this.texture.hasImage())
/*     */     {
/* 200 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 204 */     if (async) {
/*     */ 
/*     */       
/*     */       try {
/* 208 */         class_156.method_27958().execute(this::writeNextIO);
/*     */       }
/* 210 */       catch (Exception e) {
/*     */         
/* 212 */         Journeymap.getLogger().warn("Failed to write image async: " + String.valueOf(this));
/*     */       } 
/* 214 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 218 */     int tries = 0;
/* 219 */     boolean success = false;
/* 220 */     while (tries < 5) {
/*     */       
/* 222 */       if (writeNextIO()) {
/*     */         
/* 224 */         tries++;
/*     */         
/*     */         continue;
/*     */       } 
/* 228 */       success = true;
/*     */     } 
/*     */ 
/*     */     
/* 232 */     if (!success)
/*     */     {
/* 234 */       Journeymap.getLogger().warn("Couldn't write file after 5 tries: " + String.valueOf(this));
/*     */     }
/* 236 */     return success;
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
/*     */   public boolean writeNextIO() {
/* 250 */     if (this.texture == null || !this.texture.hasImage())
/*     */     {
/* 252 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 257 */       if (this.writeLock.tryLock(250L, TimeUnit.MILLISECONDS)) {
/*     */         
/* 259 */         writeImageToFile();
/* 260 */         this.writeLock.unlock();
/* 261 */         return false;
/*     */       } 
/*     */ 
/*     */       
/* 265 */       logger.warn("Couldn't get write lock for file: " + String.valueOf(this.writeLock) + " for " + String.valueOf(this));
/* 266 */       return true;
/*     */     
/*     */     }
/* 269 */     catch (InterruptedException e) {
/*     */       
/* 271 */       logger.warn("Timeout waiting for write lock  " + String.valueOf(this.writeLock) + " for " + String.valueOf(this));
/* 272 */       return true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeImageToFile() {
/* 278 */     File imageFile = this.imagePath.toFile();
/*     */ 
/*     */     
/*     */     try {
/* 282 */       class_1011 image = this.texture.getNativeImage();
/* 283 */       if (image != null && image.field_4988 > 0L)
/*     */       {
/* 285 */         if (!imageFile.exists())
/*     */         {
/* 287 */           imageFile.getParentFile().mkdirs();
/*     */         }
/*     */         
/* 290 */         File temp = new File(imageFile.getParentFile(), imageFile.getName() + ".new");
/*     */         
/* 292 */         image.method_4325(temp);
/*     */         
/* 294 */         if (imageFile.exists())
/*     */         {
/* 296 */           if (!imageFile.delete())
/*     */           {
/* 298 */             logger.warn("Couldn't delete old file " + imageFile.getName());
/*     */           }
/*     */         }
/*     */         
/* 302 */         if (temp.renameTo(imageFile)) {
/*     */           
/* 304 */           this.dirty = false;
/*     */         }
/*     */         else {
/*     */           
/* 308 */           logger.warn("Couldn't rename temp file to " + imageFile.getName());
/*     */         } 
/*     */         
/* 311 */         if (this.debug)
/*     */         {
/* 313 */           logger.debug("Wrote to disk: " + String.valueOf(imageFile));
/*     */         }
/*     */       }
/*     */     
/* 317 */     } catch (IOException e) {
/*     */       
/* 319 */       if (imageFile.exists()) {
/*     */         
/*     */         try
/*     */         {
/* 323 */           logger.error("IOException updating file, will delete and retry: " + String.valueOf(this) + ": " + LogFormatter.toPartialString(e));
/* 324 */           imageFile.delete();
/* 325 */           writeImageToFile();
/*     */         }
/* 327 */         catch (Throwable e2)
/*     */         {
/* 329 */           logger.error("Exception after delete/retry: " + String.valueOf(this) + ": " + LogFormatter.toPartialString(e));
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 334 */         logger.error("IOException creating file: " + String.valueOf(this) + ": " + LogFormatter.toPartialString(e));
/*     */       }
/*     */     
/* 337 */     } catch (Throwable e) {
/*     */       
/* 339 */       logger.error("Exception writing to disk: " + String.valueOf(this) + ": " + LogFormatter.toPartialString(e));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 346 */     return MoreObjects.toStringHelper(this)
/* 347 */       .add("mapType", this.mapType)
/* 348 */       .add("texture null", (this.texture == null))
/* 349 */       .add("texture bound", (this.texture != null && this.texture.isBound()))
/* 350 */       .add("textureId", (this.texture != null) ? this.texture.id() : -1)
/* 351 */       .add("dirty", this.dirty)
/* 352 */       .add("imagePath", this.imagePath)
/* 353 */       .toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 360 */     if (!RenderSystem.isOnRenderThread()) {
/*     */       
/* 362 */       RenderWrapper.recordRenderQueue(() -> {
/*     */             this.writeLock.lock();
/*     */             
/*     */             this.texture.close();
/*     */             
/*     */             this.texture = null;
/*     */             this.writeLock.unlock();
/*     */           });
/*     */     } else {
/* 371 */       this.writeLock.lock();
/* 372 */       this.texture.close();
/* 373 */       this.texture = null;
/* 374 */       this.writeLock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public long getImageTimestamp() {
/* 380 */     if (this.texture != null)
/*     */     {
/* 382 */       return this.texture.getLastImageUpdate();
/*     */     }
/* 384 */     return 0L;
/*     */   }
/*     */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\model\region\ImageHolder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */