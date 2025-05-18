/*     */ package journeymap.client.model.region;
/*     */ 
/*     */ import com.google.common.base.MoreObjects;
/*     */ import java.io.File;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import journeymap.client.model.map.MapType;
/*     */ import journeymap.common.Journeymap;
/*     */ import net.minecraft.class_1011;
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
/*     */ public abstract class ImageSet
/*     */ {
/*  29 */   protected final Map<MapType, ImageHolder> imageHolders = Collections.synchronizedMap(new HashMap<>(8));
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract ImageHolder getHolder(MapType paramMapType);
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract ImageHolder getExistingHolder(MapType paramMapType);
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract ImageHolder getHolderAsyncLoad(MapType paramMapType);
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int hashCode();
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract boolean equals(Object paramObject);
/*     */ 
/*     */   
/*     */   public class_1011 getImage(MapType mapType) {
/*  53 */     return getHolder(mapType).getImage();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int writeToDiskAsync(boolean force) {
/*  63 */     return writeToDisk(force, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int writeToDisk(boolean force) {
/*  73 */     return writeToDisk(force, false);
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
/*     */   private int writeToDisk(boolean force, boolean async) {
/*  85 */     long now = System.currentTimeMillis();
/*  86 */     int count = 0;
/*     */     
/*     */     try {
/*  89 */       synchronized (this.imageHolders) {
/*     */         
/*  91 */         for (ImageHolder imageHolder : this.imageHolders.values())
/*     */         {
/*  93 */           if (imageHolder.isDirty() || force)
/*     */           {
/*  95 */             if (now - imageHolder.getImageTimestamp() > 10000L)
/*     */             {
/*  97 */               imageHolder.writeToDisk(async);
/*  98 */               count++;
/*     */             }
/*     */           
/*     */           }
/*     */         }
/*     */       
/*     */       } 
/* 105 */     } catch (Throwable t) {
/*     */       
/* 107 */       Journeymap.getLogger().error("Error writing ImageSet to disk: " + String.valueOf(t));
/*     */     } 
/* 109 */     return count;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean updatedSince(MapType mapType, long time) {
/* 114 */     synchronized (this.imageHolders) {
/*     */       
/* 116 */       if (mapType == null) {
/*     */         
/* 118 */         for (ImageHolder holder : this.imageHolders.values())
/*     */         {
/* 120 */           if (holder != null && holder.getImageTimestamp() >= time)
/*     */           {
/* 122 */             return true;
/*     */           }
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 128 */         ImageHolder imageHolder = this.imageHolders.get(mapType);
/* 129 */         if (imageHolder != null && imageHolder.getImageTimestamp() >= time)
/*     */         {
/* 131 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/* 135 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 140 */     synchronized (this.imageHolders) {
/*     */       
/* 142 */       for (ImageHolder imageHolder : this.imageHolders.values())
/*     */       {
/* 144 */         imageHolder.clear();
/*     */       }
/* 146 */       this.imageHolders.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 153 */     return MoreObjects.toStringHelper(this)
/* 154 */       .add("imageHolders", this.imageHolders.entrySet())
/* 155 */       .toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract int getImageSize();
/*     */ 
/*     */ 
/*     */   
/*     */   protected ImageHolder addHolder(MapType mapType, File imageFile) {
/* 166 */     return addHolder(new ImageHolder(mapType, imageFile, getImageSize()));
/*     */   }
/*     */ 
/*     */   
/*     */   protected ImageHolder addHolder(ImageHolder imageHolder) {
/* 171 */     ImageHolder old = this.imageHolders.put(imageHolder.mapType, imageHolder);
/* 172 */     if (old != null)
/*     */     {
/* 174 */       old.clear();
/*     */     }
/* 176 */     return imageHolder;
/*     */   }
/*     */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\model\region\ImageSet.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */