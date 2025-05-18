/*     */ package journeymap.client.texture;
/*     */ 
/*     */ import com.google.common.base.MoreObjects;
/*     */ import com.mojang.blaze3d.opengl.GlConst;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import com.mojang.blaze3d.textures.TextureFormat;
/*     */ import java.lang.ref.Reference;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ import journeymap.client.JourneymapClient;
/*     */ import journeymap.client.cartography.color.RGB;
/*     */ import journeymap.client.log.JMLogger;
/*     */ import journeymap.client.render.JMRenderTypes;
/*     */ import journeymap.client.render.RenderWrapper;
/*     */ import journeymap.common.Journeymap;
/*     */ import net.minecraft.class_1011;
/*     */ import net.minecraft.class_1044;
/*     */ import net.minecraft.class_10868;
/*     */ import net.minecraft.class_1923;
/*     */ 
/*     */ public class RegionTexture
/*     */   extends class_1044
/*     */ {
/*  29 */   protected final ReentrantLock bufferLock = new ReentrantLock();
/*  30 */   protected Set<class_1923> dirtyChunks = new HashSet<>();
/*  31 */   protected List<WeakReference<Listener<RegionTexture>>> listeners = new ArrayList<>(0);
/*     */   
/*     */   protected long lastImageUpdate;
/*     */   
/*     */   protected String description;
/*     */   protected boolean bindNeeded;
/*     */   protected long lastBound;
/*     */   protected class_1011 image;
/*     */   protected class_1011[] mipmaps;
/*     */   protected int width;
/*     */   protected int height;
/*  42 */   protected int textureId = -1;
/*     */   
/*     */   protected final int mipmapLevels;
/*     */ 
/*     */   
/*     */   public RegionTexture(class_1011 pixels, String description) {
/*  48 */     this.mipmapLevels = (JourneymapClient.getInstance().getCoreProperties()).mipmapLevels.get().intValue();
/*  49 */     setNativeImage(pixels, true);
/*  50 */     this.description = description;
/*  51 */     this.mipmaps = RegionMipmapGenerator.generateMipmaps(this.image, this.mipmapLevels);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void bind() {
/*  57 */     bindRegionTexture();
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
/*     */   public void bindRegionTexture() {
/*  71 */     if (this.bindNeeded && RenderSystem.isOnRenderThread()) {
/*     */       
/*  73 */       if (this.bufferLock.tryLock()) {
/*     */         try
/*     */         {
/*     */           
/*  77 */           if (this.field_56974 == null)
/*     */           {
/*  79 */             this.field_56974 = RenderSystem.getDevice().createTexture("region-tex", TextureFormat.RGBA8, this.image.method_4307(), this.image.method_4323(), this.mipmapLevels + 1);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*  84 */           this.textureId = ((class_10868)this.field_56974).method_68427();
/*     */           
/*  86 */           if (this.lastBound == 0L || this.dirtyChunks.isEmpty()) {
/*     */             
/*  88 */             if (this.mipmaps == null)
/*     */             {
/*  90 */               this.mipmaps = RegionMipmapGenerator.generateMipmaps(this.image, this.mipmapLevels);
/*     */             }
/*     */             
/*  93 */             RenderWrapper.texParameter(3553, 10241, 9984);
/*  94 */             RenderWrapper.texParameter(3553, 10240, 9728);
/*  95 */             RenderWrapper.texParameter(3553, 10242, 33071);
/*  96 */             RenderWrapper.texParameter(3553, 10243, 33071);
/*     */             
/*  98 */             RenderWrapper.texParameter(3553, 33085, this.mipmapLevels);
/*  99 */             RenderWrapper.texParameter(3553, 33082, 0);
/* 100 */             RenderWrapper.texParameter(3553, 33083, this.mipmapLevels);
/* 101 */             RenderWrapper.texParameter(3553, 34049, 0);
/*     */             int i;
/* 103 */             for (i = 0; i <= this.mipmapLevels; i++) {
/*     */               
/* 105 */               class_1011 image = this.mipmaps[i];
/* 106 */               RenderWrapper.texImage2D(3553, i, GlConst.toGl(this.image.method_4318()), image
/* 107 */                   .method_4307(), image.method_4323(), 0, 6408, 5121, null);
/*     */             } 
/*     */ 
/*     */             
/* 111 */             for (i = 0; i <= this.mipmapLevels; i++) {
/*     */               
/* 113 */               if (this.image != null && this.image.field_4988 != 0L && id() != -1) {
/*     */                 
/* 115 */                 method_4527(false, true);
/* 116 */                 RenderSystem.getDevice().createCommandEncoder().writeToTexture(this.field_56974, this.mipmaps[i], i, 0, 0, this.mipmaps[i].method_4307(), this.mipmaps[i].method_4323(), 0, 0);
/*     */               } 
/*     */             } 
/*     */ 
/*     */             
/* 121 */             this.bindNeeded = false;
/* 122 */             this.lastBound = System.currentTimeMillis();
/*     */             
/*     */             return;
/*     */           } 
/*     */           
/* 127 */           uploadDirtyChunks();
/*     */           
/*     */           int err;
/* 130 */           while ((err = RenderWrapper.getError()) != 0)
/*     */           {
/* 132 */             JMLogger.logOnce("GL Error in RegionTexture after upload: " + err + " in " + String.valueOf(this));
/*     */           }
/*     */ 
/*     */           
/* 136 */           this.dirtyChunks.clear();
/* 137 */           this.lastBound = System.currentTimeMillis();
/* 138 */           this.bindNeeded = false;
/*     */         }
/* 140 */         catch (Throwable t)
/*     */         {
/* 142 */           Journeymap.getLogger().warn("Can't bind texture: ", t);
/*     */         }
/*     */         finally
/*     */         {
/* 146 */           this.bufferLock.unlock();
/*     */         }
/*     */       
/*     */       }
/* 150 */     } else if (!RenderSystem.isOnRenderThread()) {
/*     */       
/* 152 */       JMLogger.logOnce("Attempted to bind off renderthread: " + this.description);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void uploadDirtyChunks() {
/* 158 */     for (class_1923 pos : this.dirtyChunks) {
/*     */       
/* 160 */       class_1011 chunkImage = ImageUtil.getSubImage(pos.field_9181, pos.field_9180, 16, 16, this.image, false);
/*     */       
/* 162 */       try { if (chunkImage.field_4988 != 0L) {
/*     */           
/* 164 */           chunkImage.method_47594(this.image, 0, 0, pos.field_9181, pos.field_9180, 16, 16, false, false);
/* 165 */           method_4527(false, true);
/* 166 */           RenderSystem.getDevice().createCommandEncoder().writeToTexture(this.field_56974, chunkImage, 0, pos.field_9181, pos.field_9180, 16, 16, 0, 0);
/*     */         } 
/*     */ 
/*     */         
/* 170 */         if (chunkImage != null) chunkImage.close();  } catch (Throwable throwable) { if (chunkImage != null)
/*     */           try { chunkImage.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */             throw throwable; }
/*     */     
/* 174 */     }  RegionMipmapGenerator.updateMipmapsAndUpload(this.mipmaps, this.dirtyChunks, this.field_56974);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public long getLastImageUpdate() {
/* 180 */     return this.lastImageUpdate;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBound() {
/* 185 */     return (this.field_56974 != null && id() != -1);
/*     */   }
/*     */ 
/*     */   
/*     */   public int id() {
/* 190 */     return this.textureId;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDefunct() {
/* 195 */     return ((this.image == null && id() == -1) || (this.image != null && this.image.field_4988 == 0L));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setNativeImage(class_1011 image, boolean retainImage) {
/* 200 */     if (image == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 205 */     handleImage(image, retainImage);
/* 206 */     this.lastImageUpdate = System.currentTimeMillis();
/* 207 */     notifyListeners();
/*     */   }
/*     */ 
/*     */   
/*     */   public Integer getRGB(int x, int y) {
/* 212 */     int rgba = this.image.method_61940(x, y);
/* 213 */     return Integer.valueOf(RGB.rgbaToRgb(rgba));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNativeImage(class_1011 image, boolean retainImage, HashSet<class_1923> updatedChunks) {
/* 219 */     if (image == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 224 */     handleImage(image, retainImage);
/* 225 */     this.dirtyChunks.addAll(updatedChunks);
/* 226 */     this.lastImageUpdate = System.currentTimeMillis();
/* 227 */     notifyListeners();
/*     */   }
/*     */ 
/*     */   
/*     */   private void handleImage(class_1011 image, boolean retainImage) {
/* 232 */     this.bindNeeded = true;
/*     */     
/*     */     try {
/* 235 */       this.bufferLock.lock();
/* 236 */       this.width = image.method_4307();
/* 237 */       this.height = image.method_4323();
/* 238 */       if (retainImage)
/*     */       {
/* 240 */         if (this.image == null) {
/*     */           
/* 242 */           this.image = image;
/*     */         }
/* 244 */         else if (image.field_4988 != this.image.field_4988) {
/*     */           
/* 246 */           this.image.method_4317(image);
/*     */         } 
/*     */       }
/* 249 */       if (image.field_4988 != this.image.field_4988)
/*     */       {
/*     */         
/* 252 */         clearMipmaps();
/* 253 */         image.close();
/*     */       }
/*     */     
/*     */     } finally {
/*     */       
/* 258 */       this.bufferLock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void addListener(Listener<RegionTexture> addedListener) {
/* 264 */     Iterator<WeakReference<Listener<RegionTexture>>> iter = this.listeners.iterator();
/* 265 */     while (iter.hasNext()) {
/*     */       
/* 267 */       WeakReference<Listener<RegionTexture>> ref = iter.next();
/* 268 */       Listener<RegionTexture> listener = ref.get();
/* 269 */       if (listener == null) {
/*     */         
/* 271 */         iter.remove();
/*     */         
/*     */         continue;
/*     */       } 
/* 275 */       if (addedListener == listener) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 281 */     this.listeners.add(new WeakReference<>(addedListener));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void notifyListeners() {
/* 286 */     Iterator<WeakReference<Listener<RegionTexture>>> iter = this.listeners.iterator();
/* 287 */     while (iter.hasNext()) {
/*     */       
/* 289 */       WeakReference<Listener<RegionTexture>> ref = iter.next();
/* 290 */       Listener<RegionTexture> listener = ref.get();
/* 291 */       if (listener == null) {
/*     */         
/* 293 */         iter.remove();
/*     */         
/*     */         continue;
/*     */       } 
/* 297 */       listener.textureImageUpdated(this);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 305 */     return MoreObjects.toStringHelper(this).add("glid", id()).add("description", this.description).add("lastImageUpdate", this.lastImageUpdate).toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean bindNeeded() {
/* 310 */     return this.bindNeeded;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWidth() {
/* 315 */     return this.image.method_4307();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHeight() {
/* 320 */     return this.image.method_4323();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasImage() {
/* 325 */     return (this.image != null && this.image.field_4988 > 0L);
/*     */   }
/*     */ 
/*     */   
/*     */   private void clearMipmaps() {
/* 330 */     if (this.mipmaps != null) {
/*     */       
/* 332 */       for (int i = 0; i < this.mipmaps.length; i++) {
/*     */         
/* 334 */         if (this.mipmaps[i] != null) {
/*     */           
/* 336 */           this.mipmaps[i].close();
/* 337 */           this.mipmaps[i] = null;
/*     */         } 
/*     */       } 
/* 340 */       this.mipmaps = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {
/* 347 */     this.listeners.forEach(Reference::clear);
/* 348 */     if (this.image != null)
/*     */     {
/* 350 */       this.image.close();
/*     */     }
/* 352 */     if (this.field_56974 != null) {
/*     */       
/* 354 */       JMRenderTypes.clearRegionRenderTypes(((class_10868)this.field_56974).method_68427());
/* 355 */       this.field_56974.close();
/*     */     } 
/* 357 */     this.bindNeeded = false;
/* 358 */     this.lastImageUpdate = 0L;
/* 359 */     this.lastBound = 0L;
/* 360 */     clearMipmaps();
/* 361 */     this.image = null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public class_1011 getNativeImage() {
/* 367 */     return this.image;
/*     */   }
/*     */   
/*     */   public static interface Listener<T extends class_1044> {
/*     */     void textureImageUpdated(T param1T);
/*     */   }
/*     */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\texture\RegionTexture.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */