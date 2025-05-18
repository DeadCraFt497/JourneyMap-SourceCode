/*     */ package journeymap.client.task.multi;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import journeymap.client.JourneymapClient;
/*     */ import journeymap.client.cartography.ChunkRenderController;
/*     */ import journeymap.client.data.DataCache;
/*     */ import journeymap.client.log.StatTimer;
/*     */ import journeymap.client.model.chunk.ChunkMD;
/*     */ import journeymap.client.model.map.MapType;
/*     */ import journeymap.client.model.region.RegionCoord;
/*     */ import journeymap.client.model.region.RegionImageCache;
/*     */ import journeymap.common.Journeymap;
/*     */ import journeymap.common.log.LogFormatter;
/*     */ import journeymap.common.nbt.RegionData;
/*     */ import journeymap.common.nbt.RegionDataStorageHandler;
/*     */ import net.minecraft.class_1923;
/*     */ import net.minecraft.class_1937;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_5321;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class BaseMapTask
/*     */   implements ITask
/*     */ {
/*  32 */   static final Logger logger = Journeymap.getLogger();
/*  33 */   protected static class_1923[] keepAliveOffsets = new class_1923[] { new class_1923(0, -1), new class_1923(-1, 0), new class_1923(-1, -1) };
/*     */   
/*     */   final class_1937 world;
/*     */   
/*     */   final Collection<class_1923> chunkCoords;
/*     */   final boolean flushCacheWhenDone;
/*     */   final ChunkRenderController renderController;
/*     */   final int elapsedLimit;
/*     */   final MapType mapType;
/*     */   final boolean asyncFileWrites;
/*     */   
/*     */   public BaseMapTask(ChunkRenderController renderController, class_1937 world, MapType mapType, Collection<class_1923> chunkCoords, boolean flushCacheWhenDone, boolean asyncFileWrites, int elapsedLimit) {
/*  45 */     this.renderController = renderController;
/*  46 */     this.world = world;
/*  47 */     this.mapType = mapType;
/*  48 */     this.chunkCoords = chunkCoords;
/*  49 */     this.asyncFileWrites = asyncFileWrites;
/*  50 */     this.flushCacheWhenDone = flushCacheWhenDone;
/*  51 */     this.elapsedLimit = elapsedLimit;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initTask(class_310 mc, JourneymapClient jm, File jmWorldDir, boolean threadLogging) throws InterruptedException {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void performTask(class_310 mc, JourneymapClient jm, File jmWorldDir, boolean threadLogging) throws InterruptedException {
/*  62 */     if (!this.mapType.isAllowed()) {
/*     */       
/*  64 */       complete(0, true, false);
/*     */       
/*     */       return;
/*     */     } 
/*  68 */     StatTimer timer = StatTimer.get(getClass().getSimpleName() + ".performTask", 5, this.elapsedLimit).start();
/*     */     
/*  70 */     initTask(mc, jm, jmWorldDir, threadLogging);
/*     */     
/*  72 */     int count = 0;
/*     */ 
/*     */     
/*     */     try {
/*  76 */       if (mc.field_1687 == null) {
/*     */         
/*  78 */         complete(count, true, false);
/*     */         
/*     */         return;
/*     */       } 
/*  82 */       Iterator<class_1923> chunkIter = this.chunkCoords.iterator();
/*     */ 
/*     */       
/*  85 */       class_5321<class_1937> currentDimension = (class_310.method_1551()).field_1724.method_5770().method_27983();
/*  86 */       if (!currentDimension.equals(this.mapType.dimension)) {
/*     */         
/*  88 */         if (threadLogging)
/*     */         {
/*  90 */           logger.debug("Dimension changed, map task obsolete.");
/*     */         }
/*  92 */         timer.cancel();
/*  93 */         complete(count, true, false);
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/*  98 */       while (chunkIter.hasNext()) {
/*     */         
/* 100 */         if (!jm.isMapping().booleanValue()) {
/*     */           
/* 102 */           if (threadLogging)
/*     */           {
/* 104 */             logger.debug("JM isn't mapping, aborting");
/*     */           }
/* 106 */           timer.cancel();
/* 107 */           complete(count, true, false);
/*     */           
/*     */           return;
/*     */         } 
/* 111 */         if (Thread.interrupted())
/*     */         {
/* 113 */           throw new InterruptedException();
/*     */         }
/*     */         
/* 116 */         class_1923 coord = chunkIter.next();
/* 117 */         ChunkMD chunkMd = DataCache.INSTANCE.getChunkMD(coord.method_8324());
/* 118 */         if (chunkMd != null && chunkMd.hasChunk()) {
/*     */           
/*     */           try {
/*     */             
/* 122 */             RegionCoord rCoord = RegionCoord.fromChunkPos(jmWorldDir, this.mapType, (chunkMd.getCoord()).field_9181, (chunkMd.getCoord()).field_9180);
/* 123 */             RegionDataStorageHandler.Key key = new RegionDataStorageHandler.Key(rCoord, this.mapType);
/* 124 */             RegionData regionData = RegionDataStorageHandler.getInstance().getRegionData(key);
/* 125 */             boolean rendered = this.renderController.renderChunk(rCoord, this.mapType, chunkMd, regionData);
/*     */             
/* 127 */             if (rendered)
/*     */             {
/* 129 */               count++;
/*     */             }
/*     */           }
/* 132 */           catch (Throwable t) {
/*     */             
/* 134 */             logger.warn("Error rendering chunk " + String.valueOf(chunkMd) + ": " + t.getMessage());
/*     */           } 
/*     */         }
/*     */       } 
/*     */       
/* 139 */       if (!jm.isMapping().booleanValue()) {
/*     */         
/* 141 */         if (threadLogging)
/*     */         {
/* 143 */           logger.debug("JM isn't mapping, aborting.");
/*     */         }
/* 145 */         timer.cancel();
/* 146 */         complete(count, true, false);
/*     */         
/*     */         return;
/*     */       } 
/* 150 */       if (Thread.interrupted()) {
/*     */         
/* 152 */         timer.cancel();
/* 153 */         throw new InterruptedException();
/*     */       } 
/*     */ 
/*     */       
/* 157 */       RegionImageCache.INSTANCE.updateTextures(this.flushCacheWhenDone, this.asyncFileWrites);
/*     */       
/* 159 */       this.chunkCoords.clear();
/* 160 */       complete(count, false, false);
/* 161 */       timer.stop();
/*     */     }
/* 163 */     catch (InterruptedException t) {
/*     */       
/* 165 */       Journeymap.getLogger().warn("Task thread interrupted: " + String.valueOf(this));
/* 166 */       timer.cancel();
/* 167 */       throw t;
/*     */     }
/* 169 */     catch (Throwable t) {
/*     */ 
/*     */       
/* 172 */       String error = "Unexpected error in BaseMapTask: " + LogFormatter.toString(t);
/* 173 */       Journeymap.getLogger().error(error);
/* 174 */       complete(count, false, true);
/* 175 */       timer.cancel();
/*     */     }
/*     */     finally {
/*     */       
/* 179 */       if (threadLogging)
/*     */       {
/* 181 */         timer.report();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract void complete(int paramInt, boolean paramBoolean1, boolean paramBoolean2);
/*     */ 
/*     */   
/*     */   public String toString() {
/* 191 */     return getClass().getSimpleName() + "{world=" + getClass().getSimpleName() + ", mapType=" + String.valueOf(this.world) + ", chunkCoords=" + String.valueOf(this.mapType) + ", flushCacheWhenDone=" + String.valueOf(this.chunkCoords) + "}";
/*     */   }
/*     */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\task\multi\BaseMapTask.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */