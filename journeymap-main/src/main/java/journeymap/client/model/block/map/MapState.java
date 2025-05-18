/*     */ package journeymap.client.model.map;
/*     */ 
/*     */ import com.google.common.base.Objects;
/*     */ import com.google.common.collect.Iterables;
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import journeymap.api.client.impl.ClientAPI;
/*     */ import journeymap.client.JourneymapClient;
/*     */ import journeymap.client.data.DataCache;
/*     */ import journeymap.client.feature.Feature;
/*     */ import journeymap.client.feature.FeatureManager;
/*     */ import journeymap.client.io.FileHandler;
/*     */ import journeymap.client.log.StatTimer;
/*     */ import journeymap.client.model.entity.EntityDTO;
/*     */ import journeymap.client.model.entity.EntityHelper;
/*     */ import journeymap.client.properties.CoreProperties;
/*     */ import journeymap.client.properties.InGameMapProperties;
/*     */ import journeymap.client.properties.MapProperties;
/*     */ import journeymap.client.render.draw.DrawStep;
/*     */ import journeymap.client.render.draw.DrawWayPointStep;
/*     */ import journeymap.client.render.draw.RadarDrawStepFactory;
/*     */ import journeymap.client.render.draw.WaypointDrawStepFactory;
/*     */ import journeymap.client.render.map.Renderer;
/*     */ import journeymap.client.task.multi.MapPlayerTask;
/*     */ import journeymap.common.Journeymap;
/*     */ import journeymap.common.log.LogFormatter;
/*     */ import journeymap.common.properties.catagory.Category;
/*     */ import journeymap.common.properties.config.IntegerField;
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_1657;
/*     */ import net.minecraft.class_1937;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_5321;
/*     */ import net.minecraft.class_638;
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
/*     */ public class MapState
/*     */ {
/*     */   public final int minZoom;
/*  61 */   public final int maxZoom = 16384;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  67 */   public AtomicBoolean follow = new AtomicBoolean(true);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  72 */   public String playerLastPos = "0,0";
/*     */   
/*  74 */   private StatTimer refreshTimer = StatTimer.get("MapState.refresh");
/*  75 */   private StatTimer generateDrawStepsTimer = StatTimer.get("MapState.generateDrawSteps");
/*     */   
/*     */   private MapType lastMapType;
/*     */   
/*  79 */   private File worldDir = null;
/*  80 */   private long lastRefresh = 0L;
/*  81 */   private long lastMapTypeChange = 0L;
/*     */   
/*  83 */   private IntegerField lastSlice = new IntegerField(Category.Hidden, "", -4, 15, 4);
/*     */   private boolean surfaceMappingAllowed = false;
/*     */   private boolean caveMappingAllowed = false;
/*     */   private boolean caveMappingEnabled = false;
/*     */   private boolean topoMappingAllowed = false;
/*     */   private boolean biomeMappingAllowed = false;
/*  89 */   private List<DrawStep> drawStepList = new ArrayList<>();
/*  90 */   private List<DrawWayPointStep> drawWaypointStepList = new ArrayList<>();
/*  91 */   private String playerBiome = "";
/*  92 */   private InGameMapProperties lastMapProperties = null;
/*  93 */   private List<EntityDTO> entityList = new ArrayList<>(32);
/*  94 */   private int lastPlayerChunkX = 0;
/*  95 */   private int lastPlayerChunkY = 0;
/*  96 */   private int lastPlayerChunkZ = 0;
/*     */ 
/*     */   
/*     */   private String lastWorldName;
/*     */   
/*     */   private boolean forceRefreshState = false;
/*     */ 
/*     */   
/*     */   public MapState() {
/* 105 */     this.minZoom = 2;
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
/*     */   public void refresh(class_310 mc, class_1657 player, InGameMapProperties mapProperties) {
/* 117 */     class_638 class_638 = mc.field_1687;
/* 118 */     if (class_638 == null || class_638.method_8597() == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 123 */     this.refreshTimer.start();
/*     */     
/*     */     try {
/* 126 */       CoreProperties coreProperties = JourneymapClient.getInstance().getCoreProperties();
/* 127 */       this.lastMapProperties = mapProperties;
/*     */ 
/*     */       
/* 130 */       if (!FileHandler.getWorldDirectoryName(mc).equals(this.lastWorldName)) {
/*     */         
/* 132 */         this.worldDir = FileHandler.getJMWorldDir(mc);
/* 133 */         this.lastWorldName = FileHandler.getWorldDirectoryName(mc);
/*     */       } 
/*     */       
/* 136 */       int dimHeight = Math.max(class_638.method_8597().comp_652(), class_638.method_8597().comp_653());
/*     */       
/* 138 */       if (class_638 != null && this.lastSlice.getMaxValue() != dimHeight / 16 - 1) {
/*     */         
/* 140 */         int maxSlice = dimHeight / 16 - 1;
/* 141 */         int minSlice = class_638.method_8597().comp_651() / 16;
/* 142 */         int seaLevel = Math.round((class_638.method_8615() >> 4));
/* 143 */         int currentSlice = this.lastSlice.get().intValue();
/* 144 */         this.lastSlice = new IntegerField(Category.Hidden, "", minSlice, maxSlice, seaLevel);
/* 145 */         this.lastSlice.set(Integer.valueOf(currentSlice));
/*     */       } 
/* 147 */       this.caveMappingAllowed = FeatureManager.getInstance().isAllowed(Feature.MapCaves);
/* 148 */       this.caveMappingEnabled = (this.caveMappingAllowed && mapProperties.showCaves.get().booleanValue());
/* 149 */       this.surfaceMappingAllowed = FeatureManager.getInstance().isAllowed(Feature.MapSurface);
/* 150 */       this.topoMappingAllowed = (FeatureManager.getInstance().isAllowed(Feature.MapTopo) && coreProperties.mapTopography.get().booleanValue());
/* 151 */       this.biomeMappingAllowed = (FeatureManager.getInstance().isAllowed(Feature.MapBiome) && coreProperties.mapBiome.get().booleanValue());
/* 152 */       this.lastPlayerChunkX = (player.method_31476()).field_9181;
/* 153 */       this.lastPlayerChunkY = player.method_31478() >> 4;
/* 154 */       this.lastPlayerChunkZ = (player.method_31476()).field_9180;
/* 155 */       EntityDTO playerDTO = DataCache.getPlayer();
/* 156 */       this.playerBiome = playerDTO.biome;
/*     */       
/* 158 */       if (this.lastMapType != null)
/*     */       {
/* 160 */         if (player.method_37908().method_27983() != this.lastMapType.dimension) {
/*     */           
/* 162 */           this.lastMapType = null;
/*     */         }
/* 164 */         else if (this.caveMappingEnabled && this.follow.get() && playerDTO.underground.booleanValue() && !this.lastMapType.isUnderground()) {
/*     */           
/* 166 */           this.lastMapType = null;
/*     */         }
/* 168 */         else if (this.caveMappingEnabled && this.follow.get() && !playerDTO.underground.booleanValue() && this.lastMapType.isUnderground()) {
/*     */           
/* 170 */           this.lastMapType = null;
/*     */         }
/* 172 */         else if (!this.lastMapType.isAllowed()) {
/*     */           
/* 174 */           this.lastMapType = null;
/*     */         } 
/*     */       }
/*     */       
/* 178 */       this.lastMapType = getMapType();
/*     */       
/* 180 */       updateLastRefresh();
/*     */     }
/* 182 */     catch (Exception e) {
/*     */       
/* 184 */       Journeymap.getLogger().error("Error refreshing MapState: " + LogFormatter.toPartialString(e));
/*     */     }
/*     */     finally {
/*     */       
/* 188 */       this.refreshTimer.stop();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MapType setMapType(MapType.Name mapTypeName) {
/* 199 */     return setMapType(MapType.from(mapTypeName, DataCache.getPlayer()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MapType toggleMapType() {
/* 207 */     MapType.Name next = getNextMapType((getMapType()).name);
/* 208 */     return setMapType(next);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MapType.Name getNextMapType(MapType.Name name) {
/* 218 */     EntityDTO player = DataCache.getPlayer();
/* 219 */     class_1297 playerEntity = player.entityRef.get();
/* 220 */     if (playerEntity == null)
/*     */     {
/* 222 */       return name;
/*     */     }
/*     */     
/* 225 */     List<MapType.Name> types = new ArrayList<>(4);
/*     */     
/* 227 */     if (this.surfaceMappingAllowed) {
/*     */       
/* 229 */       types.add(MapType.Name.day);
/* 230 */       types.add(MapType.Name.night);
/*     */     } 
/*     */     
/* 233 */     if (this.caveMappingAllowed && (player.underground.booleanValue() || name == MapType.Name.underground))
/*     */     {
/* 235 */       types.add(MapType.Name.underground);
/*     */     }
/*     */     
/* 238 */     if (this.topoMappingAllowed)
/*     */     {
/* 240 */       types.add(MapType.Name.topo);
/*     */     }
/*     */     
/* 243 */     if (this.biomeMappingAllowed)
/*     */     {
/* 245 */       types.add(MapType.Name.biome);
/*     */     }
/*     */     
/* 248 */     if (name == MapType.Name.none && !types.isEmpty())
/*     */     {
/* 250 */       return types.get(0);
/*     */     }
/*     */     
/* 253 */     if (types.contains(name)) {
/*     */       
/* 255 */       Iterator<MapType.Name> cyclingIterator = Iterables.cycle(types).iterator();
/* 256 */       while (cyclingIterator.hasNext()) {
/*     */         
/* 258 */         MapType.Name current = cyclingIterator.next();
/* 259 */         if (current == name)
/*     */         {
/* 261 */           return cyclingIterator.next();
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 267 */     return name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MapType setMapType(MapType mapType) {
/* 277 */     if (!mapType.isAllowed()) {
/*     */       
/* 279 */       mapType = MapType.from(getNextMapType(mapType.name), DataCache.getPlayer());
/*     */       
/* 281 */       if (!mapType.isAllowed())
/*     */       {
/* 283 */         mapType = MapType.none();
/*     */       }
/*     */     } 
/*     */     
/* 287 */     EntityDTO player = DataCache.getPlayer();
/* 288 */     if (player.underground.booleanValue() != mapType.isUnderground())
/*     */     {
/* 290 */       this.follow.set(false);
/*     */     }
/*     */     
/* 293 */     if (mapType.isUnderground()) {
/*     */       
/* 295 */       if (player.chunkCoordY != mapType.vSlice.intValue())
/*     */       {
/* 297 */         this.follow.set(false);
/*     */       }
/* 299 */       this.lastSlice.set(mapType.vSlice);
/*     */     }
/* 301 */     else if (this.lastMapProperties != null && mapType.name != MapType.Name.none && this.lastMapProperties.preferredMapType.get() != mapType.name) {
/*     */       
/* 303 */       this.lastMapProperties.preferredMapType.set(mapType.name);
/* 304 */       this.lastMapProperties.save();
/*     */     } 
/*     */     
/* 307 */     setLastMapTypeChange(mapType);
/*     */     
/* 309 */     return this.lastMapType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MapType getMapType() {
/* 319 */     if (this.lastMapType == null) {
/*     */       
/* 321 */       EntityDTO player = DataCache.getPlayer();
/* 322 */       MapType mapType = null;
/*     */       
/*     */       try {
/* 325 */         if (this.caveMappingEnabled && player.underground.booleanValue()) {
/*     */           
/* 327 */           mapType = MapType.underground(player);
/*     */         }
/* 329 */         else if (this.follow.get()) {
/*     */           
/* 331 */           if (this.surfaceMappingAllowed && !player.underground.booleanValue()) {
/*     */             
/* 333 */             mapType = MapType.day(player);
/*     */           }
/* 335 */           else if (this.topoMappingAllowed && !player.underground.booleanValue()) {
/*     */             
/* 337 */             mapType = MapType.topo(player);
/*     */           }
/* 339 */           else if (this.biomeMappingAllowed && !player.underground.booleanValue()) {
/*     */             
/* 341 */             mapType = MapType.biome(player);
/*     */           } 
/*     */         } 
/*     */         
/* 345 */         if (mapType == null)
/*     */         {
/* 347 */           mapType = MapType.from((MapType.Name)this.lastMapProperties.preferredMapType.get(), player);
/*     */         }
/*     */       }
/* 350 */       catch (Exception e) {
/*     */         
/* 352 */         mapType = MapType.day(player);
/*     */       } 
/*     */       
/* 355 */       setMapType(mapType);
/*     */     } 
/*     */     
/* 358 */     return this.lastMapType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getLastMapTypeChange() {
/* 368 */     return this.lastMapTypeChange;
/*     */   }
/*     */ 
/*     */   
/*     */   private void setLastMapTypeChange(MapType mapType) {
/* 373 */     if (!Objects.equal(mapType, this.lastMapType)) {
/*     */       
/* 375 */       this.lastMapTypeChange = System.currentTimeMillis();
/* 376 */       requireRefresh();
/*     */     } 
/* 378 */     this.lastMapType = mapType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUnderground() {
/* 388 */     return getMapType().isUnderground();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public File getWorldDir() {
/* 398 */     return this.worldDir;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPlayerBiome() {
/* 408 */     return this.playerBiome;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<? extends DrawStep> getDrawSteps() {
/* 418 */     return this.drawStepList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<DrawWayPointStep> getDrawWaypointSteps() {
/* 428 */     return this.drawWaypointStepList;
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
/*     */   public void generateDrawSteps(class_310 mc, Renderer renderer, WaypointDrawStepFactory waypointRenderer, RadarDrawStepFactory radarRenderer, InGameMapProperties mapProperties, boolean checkWaypointDistance) {
/* 443 */     this.generateDrawStepsTimer.start();
/* 444 */     this.lastMapProperties = mapProperties;
/*     */     
/* 446 */     this.drawStepList.clear();
/* 447 */     this.drawWaypointStepList.clear();
/* 448 */     this.entityList.clear();
/*     */     
/* 450 */     ClientAPI.INSTANCE.getDrawSteps(this.drawStepList, renderer.getUIState());
/*     */     
/* 452 */     if (FeatureManager.getInstance().isAllowed(Feature.RadarAnimals)) {
/*     */       
/* 454 */       if (mapProperties.showAnimals.get().booleanValue() || mapProperties.showPets.get().booleanValue())
/*     */       {
/* 456 */         this.entityList.addAll(DataCache.INSTANCE.getAnimals(false).values());
/*     */       }
/* 458 */       if (mapProperties.showAmbientCreatures.get().booleanValue())
/*     */       {
/* 460 */         this.entityList.addAll(DataCache.INSTANCE.getAmbientCreatures(false).values());
/*     */       }
/*     */     } 
/* 463 */     if (FeatureManager.getInstance().isAllowed(Feature.RadarVillagers))
/*     */     {
/* 465 */       if (mapProperties.showVillagers.get().booleanValue())
/*     */       {
/* 467 */         this.entityList.addAll(DataCache.INSTANCE.getVillagers(false).values());
/*     */       }
/*     */     }
/* 470 */     if (FeatureManager.getInstance().isAllowed(Feature.RadarMobs))
/*     */     {
/* 472 */       if (mapProperties.showMobs.get().booleanValue())
/*     */       {
/* 474 */         this.entityList.addAll(DataCache.INSTANCE.getMobs(false).values());
/*     */       }
/*     */     }
/*     */     
/* 478 */     if (FeatureManager.getInstance().isAllowed(Feature.RadarPlayers))
/*     */     {
/* 480 */       if (mapProperties.showPlayers.get().booleanValue()) {
/*     */         
/* 482 */         mc.field_1724.method_37908().method_18456();
/* 483 */         Collection<EntityDTO> cachedPlayers = DataCache.INSTANCE.getPlayers(false).values();
/* 484 */         if (cachedPlayers.size() != mc.method_1562().method_2880().size())
/*     */         {
/* 486 */           cachedPlayers = DataCache.INSTANCE.getPlayers(true).values();
/*     */         }
/* 488 */         this.entityList.addAll(cachedPlayers);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 493 */     if (!this.entityList.isEmpty()) {
/*     */       
/* 495 */       this.entityList.sort((Comparator<? super EntityDTO>)EntityHelper.entityMapComparator);
/* 496 */       this.drawStepList.addAll(radarRenderer.prepareSteps(this.entityList, renderer, mapProperties));
/*     */     } 
/*     */ 
/*     */     
/* 500 */     if (mapProperties.showWaypoints.get().booleanValue()) {
/*     */       
/* 502 */       boolean showLabel = mapProperties.showWaypointLabels.get().booleanValue();
/* 503 */       this.drawWaypointStepList.addAll(waypointRenderer.prepareSteps(DataCache.INSTANCE.getWaypoints(false), renderer, checkWaypointDistance, showLabel));
/*     */     } 
/*     */     
/* 506 */     this.generateDrawStepsTimer.stop();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean minimapZoomIn() {
/* 516 */     if (this.lastMapProperties.zoomLevel.get().intValue() < 16384)
/*     */     {
/* 518 */       return setZoom(this.lastMapProperties.zoomLevel.get().intValue() * 2);
/*     */     }
/* 520 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean minimapZoomOut() {
/* 530 */     if (this.lastMapProperties.zoomLevel.get().intValue() > 256)
/*     */     {
/* 532 */       return setZoom(this.lastMapProperties.zoomLevel.get().intValue() / 2);
/*     */     }
/* 534 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean setZoom(int zoom) {
/* 545 */     if (zoom > 16384 || zoom < this.minZoom || zoom == this.lastMapProperties.zoomLevel.get().intValue())
/*     */     {
/* 547 */       return false;
/*     */     }
/* 549 */     this.lastMapProperties.zoomLevel.set(Integer.valueOf(zoom));
/* 550 */     requireRefresh();
/* 551 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getZoom() {
/* 561 */     return this.lastMapProperties.zoomLevel.get().intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void requireRefresh() {
/* 569 */     this.lastRefresh = 0L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateLastRefresh() {
/* 577 */     this.lastRefresh = System.currentTimeMillis();
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
/*     */   public boolean shouldRefresh(class_310 mc, MapProperties mapProperties) {
/* 589 */     if (ClientAPI.INSTANCE.isDrawStepsUpdateNeeded())
/*     */     {
/* 591 */       return true;
/*     */     }
/*     */     
/* 594 */     if (MapPlayerTask.getlastTaskCompleted() - this.lastRefresh > 500L)
/*     */     {
/* 596 */       return true;
/*     */     }
/*     */     
/* 599 */     if (this.lastMapType == null)
/*     */     {
/* 601 */       return true;
/*     */     }
/*     */     
/* 604 */     EntityDTO player = DataCache.getPlayer();
/*     */     
/* 606 */     if (!player.dimension.equals((getMapType()).dimension))
/*     */     {
/* 608 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 612 */     double d0 = (this.lastPlayerChunkX - player.chunkCoordX);
/* 613 */     double d1 = (this.lastPlayerChunkY - player.chunkCoordY);
/* 614 */     double d2 = (this.lastPlayerChunkZ - player.chunkCoordZ);
/* 615 */     double diff = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
/*     */     
/* 617 */     if (diff > 2.0D)
/*     */     {
/* 619 */       return true;
/*     */     }
/*     */     
/* 622 */     if (this.lastMapProperties == null || !this.lastMapProperties.equals(mapProperties))
/*     */     {
/* 624 */       return true;
/*     */     }
/*     */     
/* 627 */     if (this.forceRefreshState) {
/*     */       
/* 629 */       this.forceRefreshState = false;
/* 630 */       return true;
/*     */     } 
/*     */     
/* 633 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setForceRefreshState(boolean force) {
/* 638 */     this.forceRefreshState = force;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCaveMappingAllowed() {
/* 649 */     return this.caveMappingAllowed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCaveMappingEnabled() {
/* 659 */     return this.caveMappingEnabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSurfaceMappingAllowed() {
/* 669 */     return this.surfaceMappingAllowed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTopoMappingAllowed() {
/* 680 */     return this.topoMappingAllowed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public class_5321<class_1937> getDimension() {
/* 690 */     return (getMapType()).dimension;
/*     */   }
/*     */ 
/*     */   
/*     */   public IntegerField getLastSlice() {
/* 695 */     return this.lastSlice;
/*     */   }
/*     */ 
/*     */   
/*     */   public void resetMapType() {
/* 700 */     this.lastMapType = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBiomeMappingAllowed() {
/* 705 */     return this.biomeMappingAllowed;
/*     */   }
/*     */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\model\map\MapState.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */