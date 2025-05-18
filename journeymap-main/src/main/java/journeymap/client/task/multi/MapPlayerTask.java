/*     */ package journeymap.client.task.multi;
/*     */ 
/*     */ import com.google.common.cache.Cache;
/*     */ import com.google.common.cache.CacheBuilder;
/*     */ import java.io.File;
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import journeymap.client.Constants;
/*     */ import journeymap.client.JourneymapClient;
/*     */ import journeymap.client.cartography.ChunkRenderController;
/*     */ import journeymap.client.data.DataCache;
/*     */ import journeymap.client.feature.Feature;
/*     */ import journeymap.client.feature.FeatureManager;
/*     */ import journeymap.client.model.chunk.ChunkMD;
/*     */ import journeymap.client.model.entity.EntityDTO;
/*     */ import journeymap.client.model.map.MapType;
/*     */ import journeymap.client.properties.CoreProperties;
/*     */ import journeymap.common.Journeymap;
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_1923;
/*     */ import net.minecraft.class_1937;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_5569;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MapPlayerTask
/*     */   extends BaseMapTask
/*     */ {
/*  41 */   private static int MAX_STALE_MILLISECONDS = 30000;
/*  42 */   private static int MAX_BATCH_SIZE = 32;
/*  43 */   private static final DecimalFormat decFormat = new DecimalFormat("##.#");
/*     */   private static volatile long lastTaskCompleted;
/*     */   private static long lastTaskTime;
/*     */   private static double lastTaskAvgChunkTime;
/*  47 */   private static final Cache<String, String> tempDebugLines = CacheBuilder.newBuilder()
/*  48 */     .maximumSize(20L)
/*  49 */     .expireAfterWrite(1500L, TimeUnit.MILLISECONDS)
/*  50 */     .build();
/*     */   
/*  52 */   private final int maxRuntime = (JourneymapClient.getInstance().getCoreProperties()).renderDelay.get().intValue() * 3000;
/*  53 */   private int scheduledChunks = 0;
/*     */   
/*     */   private long startNs;
/*     */   private long elapsedNs;
/*     */   
/*     */   private MapPlayerTask(ChunkRenderController chunkRenderController, class_1937 world, MapType mapType, Collection<class_1923> chunkCoords) {
/*  59 */     super(chunkRenderController, world, mapType, chunkCoords, false, true, 10000);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void forceNearbyRemap() {
/*  67 */     synchronized (MapPlayerTask.class) {
/*     */       
/*  69 */       DataCache.INSTANCE.invalidateChunkMDCache();
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
/*     */   public static MapPlayerTaskBatch create(ChunkRenderController chunkRenderController, EntityDTO player) {
/*     */     MapType mapType;
/*  82 */     boolean surfaceAllowed = FeatureManager.getInstance().isAllowed(Feature.MapSurface);
/*  83 */     boolean cavesAllowed = FeatureManager.getInstance().isAllowed(Feature.MapCaves);
/*  84 */     boolean topoAllowed = FeatureManager.getInstance().isAllowed(Feature.MapTopo);
/*  85 */     boolean biomeAllowed = FeatureManager.getInstance().isAllowed(Feature.MapBiome);
/*  86 */     if (!surfaceAllowed && !cavesAllowed && !topoAllowed && !biomeAllowed)
/*     */     {
/*  88 */       return null;
/*     */     }
/*     */     
/*  91 */     class_1297 playerEntity = player.entityRef.get();
/*  92 */     if (playerEntity == null)
/*     */     {
/*  94 */       return null;
/*     */     }
/*  96 */     boolean underground = player.underground.booleanValue();
/*     */ 
/*     */     
/*  99 */     if (underground) {
/*     */       
/* 101 */       mapType = MapType.underground(player);
/*     */     }
/*     */     else {
/*     */       
/* 105 */       long time = playerEntity.method_5770().method_8401().method_217() % 24000L;
/* 106 */       mapType = (time < 13800L) ? MapType.day(player) : MapType.night(player);
/*     */     } 
/*     */     
/* 109 */     List<ITask> tasks = new ArrayList<>(2);
/* 110 */     tasks.add(new MapPlayerTask(chunkRenderController, playerEntity.method_5770(), mapType, new ArrayList<>()));
/*     */     
/* 112 */     if (underground) {
/*     */       
/* 114 */       if (surfaceAllowed && (JourneymapClient.getInstance().getCoreProperties()).alwaysMapSurface.get().booleanValue())
/*     */       {
/* 116 */         tasks.add(new MapPlayerTask(chunkRenderController, playerEntity.method_5770(), MapType.day(player), new ArrayList<>()));
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 121 */     else if (cavesAllowed && (JourneymapClient.getInstance().getCoreProperties()).alwaysMapCaves.get().booleanValue()) {
/*     */       
/* 123 */       tasks.add(new MapPlayerTask(chunkRenderController, playerEntity.method_5770(), MapType.underground(player), new ArrayList<>()));
/*     */     } 
/*     */ 
/*     */     
/* 127 */     if (topoAllowed && (JourneymapClient.getInstance().getCoreProperties()).mapTopography.get().booleanValue())
/*     */     {
/* 129 */       tasks.add(new MapPlayerTask(chunkRenderController, playerEntity.method_5770(), MapType.topo(player), new ArrayList<>()));
/*     */     }
/*     */     
/* 132 */     if (biomeAllowed && (JourneymapClient.getInstance().getCoreProperties()).mapBiome.get().booleanValue())
/*     */     {
/* 134 */       tasks.add(new MapPlayerTask(chunkRenderController, playerEntity.method_5770(), MapType.biome(player), new ArrayList<>()));
/*     */     }
/*     */     
/* 137 */     return new MapPlayerTaskBatch(tasks);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String[] getDebugStats() {
/*     */     try {
/* 149 */       CoreProperties coreProperties = JourneymapClient.getInstance().getCoreProperties();
/* 150 */       boolean underground = (DataCache.getPlayer()).underground.booleanValue();
/* 151 */       ArrayList<String> lines = new ArrayList<>(tempDebugLines.asMap().values());
/*     */       
/* 153 */       if (underground || coreProperties.alwaysMapCaves.get().booleanValue())
/*     */       {
/* 155 */         lines.add(RenderSpec.getUndergroundSpec().getDebugStats());
/*     */       }
/*     */       
/* 158 */       if (!underground || coreProperties.alwaysMapSurface.get().booleanValue())
/*     */       {
/* 160 */         lines.add(RenderSpec.getSurfaceSpec().getDebugStats());
/*     */       }
/*     */       
/* 163 */       if (!underground && coreProperties.mapTopography.get().booleanValue())
/*     */       {
/* 165 */         lines.add(RenderSpec.getTopoSpec().getDebugStats());
/*     */       }
/*     */       
/* 168 */       return lines.<String>toArray(new String[lines.size()]);
/*     */     }
/* 170 */     catch (Throwable t) {
/*     */       
/* 172 */       logger.error(t);
/* 173 */       return new String[0];
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
/*     */   public static void addTempDebugMessage(String key, String message) {
/* 185 */     if (class_310.method_1551().method_53526().method_53537())
/*     */     {
/* 187 */       tempDebugLines.put(key, message);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void removeTempDebugMessage(String key) {
/* 198 */     tempDebugLines.invalidate(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getSimpleStats() {
/* 208 */     int primaryRenderSize = 0;
/* 209 */     int secondaryRenderSize = 0;
/* 210 */     int totalChunks = 0;
/*     */     
/* 212 */     if ((DataCache.getPlayer()).underground.booleanValue() || (JourneymapClient.getInstance().getCoreProperties()).alwaysMapCaves.get().booleanValue()) {
/*     */       
/* 214 */       RenderSpec spec = RenderSpec.getUndergroundSpec();
/* 215 */       if (spec != null) {
/*     */         
/* 217 */         primaryRenderSize += spec.getPrimaryRenderSize();
/* 218 */         secondaryRenderSize += spec.getLastSecondaryRenderSize();
/* 219 */         totalChunks += spec.getLastTaskChunks();
/*     */       } 
/*     */     } 
/*     */     
/* 223 */     if (!(DataCache.getPlayer()).underground.booleanValue() || (JourneymapClient.getInstance().getCoreProperties()).alwaysMapSurface.get().booleanValue()) {
/*     */       
/* 225 */       RenderSpec spec = RenderSpec.getSurfaceSpec();
/* 226 */       if (spec != null) {
/*     */         
/* 228 */         primaryRenderSize += spec.getPrimaryRenderSize();
/* 229 */         secondaryRenderSize += spec.getLastSecondaryRenderSize();
/* 230 */         totalChunks += spec.getLastTaskChunks();
/*     */       } 
/*     */     } 
/*     */     
/* 234 */     return Constants.getString("jm.common.renderstats", new Object[] {
/* 235 */           Integer.valueOf(totalChunks), 
/* 236 */           Integer.valueOf(primaryRenderSize), 
/* 237 */           Integer.valueOf(secondaryRenderSize), 
/* 238 */           Long.valueOf(lastTaskTime), decFormat
/* 239 */           .format(lastTaskAvgChunkTime)
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long getlastTaskCompleted() {
/* 249 */     return lastTaskCompleted;
/*     */   }
/*     */   public void initTask(class_310 minecraft, JourneymapClient jm, File jmWorldDir, boolean threadLogging) {
/*     */     RenderSpec renderSpec;
/*     */     List<class_1923> renderArea;
/*     */     int maxBatchSize;
/* 255 */     this.startNs = System.nanoTime();
/*     */ 
/*     */ 
/*     */     
/* 259 */     if (this.mapType.isUnderground()) {
/*     */       
/* 261 */       renderSpec = RenderSpec.getUndergroundSpec();
/*     */     }
/* 263 */     else if (this.mapType.isTopo()) {
/*     */       
/* 265 */       renderSpec = RenderSpec.getTopoSpec();
/*     */     }
/*     */     else {
/*     */       
/* 269 */       renderSpec = RenderSpec.getSurfaceSpec();
/*     */     } 
/*     */     
/* 272 */     long now = System.currentTimeMillis();
/*     */ 
/*     */     
/* 275 */     if ((JourneymapClient.getInstance().getCoreProperties()).mapOnlyPlayerChunk.get().booleanValue()) {
/*     */       
/* 277 */       renderArea = Collections.singletonList((class_310.method_1551()).field_1724.method_31476());
/* 278 */       maxBatchSize = 1;
/*     */     }
/*     */     else {
/*     */       
/* 282 */       renderArea = renderSpec.getRenderAreaCoords();
/* 283 */       maxBatchSize = renderArea.size() / 4;
/*     */       
/* 285 */       renderArea.removeIf(chunkPos -> {
/*     */             ChunkMD chunkMD = DataCache.INSTANCE.getChunkMD(chunkPos.method_8324());
/*     */ 
/*     */ 
/*     */             
/*     */             if (chunkMD == null || !chunkMD.hasChunk() || now - chunkMD.getLastRendered(this.mapType) < 30000L) {
/*     */               return true;
/*     */             }
/*     */ 
/*     */ 
/*     */             
/*     */             if (!chunkMD.getDimension().equals(this.mapType.dimension)) {
/*     */               return true;
/*     */             }
/*     */ 
/*     */             
/*     */             chunkMD.resetBlockData(this.mapType);
/*     */ 
/*     */             
/*     */             return false;
/*     */           });
/*     */     } 
/*     */ 
/*     */     
/* 309 */     if (renderArea.size() <= maxBatchSize) {
/*     */       
/* 311 */       this.chunkCoords.addAll(renderArea);
/*     */     }
/*     */     else {
/*     */       
/* 315 */       List<class_1923> list = Arrays.asList(renderArea.<class_1923>toArray(new class_1923[renderArea.size()]));
/* 316 */       this.chunkCoords.addAll(list.subList(0, maxBatchSize));
/*     */     } 
/*     */     
/* 319 */     this.scheduledChunks = this.chunkCoords.size();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void complete(int mappedChunks, boolean cancelled, boolean hadError) {
/* 325 */     this.elapsedNs = System.nanoTime() - this.startNs;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxRuntime() {
/* 331 */     return this.maxRuntime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Manager
/*     */     implements ITaskManager
/*     */   {
/* 344 */     final int mapTaskDelay = (JourneymapClient.getInstance().getCoreProperties()).renderDelay.get().intValue() * 1000;
/*     */ 
/*     */ 
/*     */     
/*     */     boolean enabled;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Class<? extends BaseMapTask> getTaskClass() {
/* 354 */       return (Class)MapPlayerTask.class;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean enableTask(class_310 minecraft, Object params) {
/* 360 */       this.enabled = true;
/* 361 */       return this.enabled;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isEnabled(class_310 minecraft) {
/* 367 */       return this.enabled;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void disableTask(class_310 minecraft) {
/* 373 */       this.enabled = false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ITask getTask(class_310 minecraft) {
/* 380 */       if (this.enabled && minecraft.field_1724.field_26996 != class_5569.field_27243)
/*     */       {
/* 382 */         if (System.currentTimeMillis() - MapPlayerTask.lastTaskCompleted >= this.mapTaskDelay) {
/*     */           
/* 384 */           ChunkRenderController chunkRenderController = JourneymapClient.getInstance().getChunkRenderController();
/* 385 */           return MapPlayerTask.create(chunkRenderController, DataCache.getPlayer());
/*     */         } 
/*     */       }
/*     */       
/* 389 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void taskAccepted(ITask task, boolean accepted) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class MapPlayerTaskBatch
/*     */     extends TaskBatch
/*     */   {
/*     */     public MapPlayerTaskBatch(List<ITask> tasks) {
/* 412 */       super(tasks);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void performTask(class_310 mc, JourneymapClient jm, File jmWorldDir, boolean threadLogging) throws InterruptedException {
/* 418 */       if (mc.field_1724 == null) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 423 */       this.startNs = System.nanoTime();
/* 424 */       List<ITask> tasks = new ArrayList<>(this.taskList);
/* 425 */       super.performTask(mc, jm, jmWorldDir, threadLogging);
/*     */       
/* 427 */       this.elapsedNs = System.nanoTime() - this.startNs;
/* 428 */       MapPlayerTask.lastTaskTime = TimeUnit.NANOSECONDS.toMillis(this.elapsedNs);
/* 429 */       MapPlayerTask.lastTaskCompleted = System.currentTimeMillis();
/*     */ 
/*     */       
/* 432 */       int chunkCount = 0;
/* 433 */       for (ITask task : tasks) {
/*     */         
/* 435 */         if (task instanceof MapPlayerTask) {
/*     */           
/* 437 */           MapPlayerTask mapPlayerTask = (MapPlayerTask)task;
/* 438 */           chunkCount += mapPlayerTask.scheduledChunks;
/* 439 */           if (mapPlayerTask.mapType.isUnderground()) {
/*     */             
/* 441 */             RenderSpec.getUndergroundSpec().setLastTaskInfo(mapPlayerTask.scheduledChunks, mapPlayerTask.elapsedNs); continue;
/*     */           } 
/* 443 */           if (mapPlayerTask.mapType.isTopo()) {
/*     */             
/* 445 */             RenderSpec.getTopoSpec().setLastTaskInfo(mapPlayerTask.scheduledChunks, mapPlayerTask.elapsedNs);
/*     */             
/*     */             continue;
/*     */           } 
/* 449 */           RenderSpec.getSurfaceSpec().setLastTaskInfo(mapPlayerTask.scheduledChunks, mapPlayerTask.elapsedNs);
/*     */           
/*     */           continue;
/*     */         } 
/*     */         
/* 454 */         Journeymap.getLogger().warn("Unexpected task in batch: " + String.valueOf(task));
/*     */       } 
/*     */       
/* 457 */       MapPlayerTask.lastTaskAvgChunkTime = (this.elapsedNs / Math.max(1, chunkCount)) / 1000000.0D;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\task\multi\MapPlayerTask.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */