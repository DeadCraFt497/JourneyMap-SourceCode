/*     */ package journeymap.client.task.main;
/*     */ 
/*     */ import journeymap.client.JourneymapClient;
/*     */ import journeymap.client.data.DataCache;
/*     */ import journeymap.client.log.ChatLog;
/*     */ import journeymap.client.model.map.MapType;
/*     */ import journeymap.client.model.region.RegionCoord;
/*     */ import journeymap.client.model.region.RegionImageCache;
/*     */ import journeymap.client.render.map.MapRenderer;
/*     */ import journeymap.client.task.multi.MapPlayerTask;
/*     */ import journeymap.client.task.multi.MapRegionTask;
/*     */ import journeymap.client.ui.fullscreen.Fullscreen;
/*     */ import journeymap.common.Journeymap;
/*     */ import journeymap.common.nbt.RegionDataStorageHandler;
/*     */ import net.minecraft.class_1923;
/*     */ import net.minecraft.class_310;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DeleteMapTask
/*     */   implements IMainThreadTask
/*     */ {
/*  26 */   private static String NAME = "Tick." + MappingMonitorTask.class.getSimpleName();
/*  27 */   private static Logger LOGGER = Journeymap.getLogger();
/*     */   
/*     */   boolean allDims;
/*     */   
/*     */   RegionCoord regionCoord;
/*     */   MapType mapType;
/*     */   
/*     */   private DeleteMapTask(boolean allDims) {
/*  35 */     this.allDims = allDims;
/*     */   }
/*     */ 
/*     */   
/*     */   public DeleteMapTask(RegionCoord regionCoord, MapType mapType) {
/*  40 */     this.regionCoord = regionCoord;
/*  41 */     this.mapType = mapType;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void queue(boolean allDims) {
/*  46 */     JourneymapClient.getInstance().queueMainThreadTask(new DeleteMapTask(allDims));
/*     */   }
/*     */ 
/*     */   
/*     */   public static void queue(RegionCoord regionCoord, MapType mapType) {
/*  51 */     JourneymapClient.getInstance().queueMainThreadTask(new DeleteMapTask(regionCoord, mapType));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final IMainThreadTask perform(class_310 mc, JourneymapClient jm) {
/*  57 */     if (this.regionCoord != null) {
/*     */       
/*  59 */       deleteRegion(jm);
/*     */     }
/*     */     else {
/*     */       
/*  63 */       deleteMap(jm);
/*     */     } 
/*  65 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void deleteRegion(JourneymapClient jm) {
/*     */     try {
/*  72 */       jm.toggleTask(MapPlayerTask.Manager.class, false, Boolean.valueOf(false));
/*  73 */       jm.toggleTask(MapRegionTask.Manager.class, false, Boolean.valueOf(false));
/*  74 */       MapRenderer.setEnabled(false);
/*     */       
/*  76 */       for (class_1923 chunkPos : this.regionCoord.getChunkCoordsInRegion())
/*     */       {
/*  78 */         DataCache.INSTANCE.invalidateChunkMD(chunkPos);
/*     */       }
/*     */       
/*  81 */       RegionDataStorageHandler.getInstance().removeRegion(this.regionCoord, Fullscreen.state().getMapType());
/*  82 */       boolean ok = RegionImageCache.INSTANCE.deleteRegion(this.regionCoord, Fullscreen.state());
/*  83 */       if (ok)
/*     */       {
/*  85 */         ChatLog.announceI18N("jm.common.delete_region_status_done", new Object[0]);
/*     */       }
/*     */       else
/*     */       {
/*  89 */         ChatLog.announceI18N("jm.common.delete_region_status_error", new Object[0]);
/*     */       }
/*     */     
/*  92 */     } catch (Throwable t) {
/*     */       
/*  94 */       Journeymap.getLogger().error("Error deleting region: ", t);
/*     */     }
/*     */     finally {
/*     */       
/*  98 */       MapRenderer.setEnabled(true);
/*  99 */       jm.toggleTask(MapPlayerTask.Manager.class, true, Boolean.valueOf(true));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void deleteMap(JourneymapClient jm) {
/*     */     try {
/* 107 */       jm.toggleTask(MapPlayerTask.Manager.class, false, Boolean.valueOf(false));
/* 108 */       jm.toggleTask(MapRegionTask.Manager.class, false, Boolean.valueOf(false));
/* 109 */       MapRenderer.setEnabled(false);
/*     */       
/* 111 */       boolean wasMapping = JourneymapClient.getInstance().isMapping().booleanValue();
/* 112 */       if (wasMapping)
/*     */       {
/* 114 */         JourneymapClient.getInstance().stopMapping();
/*     */       }
/*     */       
/* 117 */       DataCache.INSTANCE.invalidateChunkMDCache();
/* 118 */       RegionDataStorageHandler.getInstance().deleteCache();
/* 119 */       boolean ok = RegionImageCache.INSTANCE.deleteMap(Fullscreen.state(), this.allDims);
/* 120 */       if (ok) {
/*     */         
/* 122 */         ChatLog.announceI18N("jm.common.deletemap_status_done", new Object[0]);
/*     */       }
/*     */       else {
/*     */         
/* 126 */         ChatLog.announceI18N("jm.common.deletemap_status_error", new Object[0]);
/*     */       } 
/*     */       
/* 129 */       if (wasMapping) {
/*     */         
/* 131 */         JourneymapClient.getInstance().startMapping();
/* 132 */         MapPlayerTask.forceNearbyRemap();
/*     */       } 
/*     */       
/* 135 */       Fullscreen.state().requireRefresh();
/*     */     }
/*     */     finally {
/*     */       
/* 139 */       MapRenderer.setEnabled(true);
/* 140 */       jm.toggleTask(MapPlayerTask.Manager.class, true, Boolean.valueOf(true));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 147 */     return NAME;
/*     */   }
/*     */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\task\main\DeleteMapTask.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */