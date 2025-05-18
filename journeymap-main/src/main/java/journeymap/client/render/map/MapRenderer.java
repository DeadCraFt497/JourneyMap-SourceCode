/*     */ package journeymap.client.render.map;
/*     */ 
/*     */ import java.awt.geom.Point2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.File;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.util.Collections;
/*     */ import java.util.ConcurrentModificationException;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.SortedMap;
/*     */ import java.util.TreeMap;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.Executor;
/*     */ import journeymap.api.client.impl.ClientAPI;
/*     */ import journeymap.api.client.impl.ClientEventManager;
/*     */ import journeymap.api.services.EventBus;
/*     */ import journeymap.api.v2.client.display.Context;
/*     */ import journeymap.api.v2.client.event.DisplayUpdateEvent;
/*     */ import journeymap.api.v2.client.util.UIState;
/*     */ import journeymap.api.v2.common.event.impl.JourneyMapEvent;
/*     */ import journeymap.client.data.DataCache;
/*     */ import journeymap.client.io.RegionImageHandler;
/*     */ import journeymap.client.model.map.MapState;
/*     */ import journeymap.client.model.map.MapType;
/*     */ import journeymap.client.model.region.RegionCoord;
/*     */ import journeymap.client.model.region.RegionImageCache;
/*     */ import journeymap.client.model.region.RegionImageSet;
/*     */ import journeymap.client.render.RenderWrapper;
/*     */ import journeymap.client.render.draw.DrawEntityStep;
/*     */ import journeymap.client.render.draw.DrawStep;
/*     */ import journeymap.client.ui.UIManager;
/*     */ import journeymap.client.ui.fullscreen.Fullscreen;
/*     */ import journeymap.common.Journeymap;
/*     */ import net.minecraft.class_1041;
/*     */ import net.minecraft.class_156;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_238;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_3532;
/*     */ import net.minecraft.class_4597;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.joml.Matrix4f;
/*     */ import org.joml.Matrix4fc;
/*     */ import org.joml.Vector3f;
/*     */ import org.lwjgl.BufferUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MapRenderer
/*     */   implements Renderer
/*     */ {
/*     */   protected static final int BLOCK_PAD = 32;
/*     */   protected int zoom;
/*     */   private static boolean enabled = true;
/*  59 */   private final Logger logger = Journeymap.getLogger();
/*     */   
/*  61 */   SortedMap<RegionCoord, RegionTile> regions = Collections.synchronizedSortedMap(new TreeMap<>(RegionCoord.getComparator(null).reversed()));
/*  62 */   Set<String> mapTypeImageFiles = new HashSet<>();
/*     */   private File worldDir;
/*     */   protected MapType mapType;
/*  65 */   private class_238 blockBounds = null;
/*     */   private int gridSize;
/*     */   protected MapState state;
/*  68 */   protected int lastHeight = -1;
/*  69 */   protected int lastWidth = -1;
/*  70 */   private int glErrors = 0;
/*  71 */   private final int maxGlErrors = 20;
/*  72 */   protected Rectangle2D.Double viewPortBounds = null;
/*  73 */   protected Rectangle2D.Double screenBounds = null;
/*     */   
/*  75 */   protected Rectangle2D.Double regionBounds = null;
/*     */   
/*     */   protected UIState uiState;
/*     */   protected final Context.UI contextUi;
/*  79 */   private final Point2D.Double centerPixelOffset = new Point2D.Double();
/*     */   
/*     */   protected double centerBlockX;
/*     */   
/*     */   protected double centerBlockZ;
/*     */   protected RegionCoord centerRegion;
/*     */   protected RegionCoord sortingCenterRegion;
/*  86 */   protected final class_310 mc = class_310.method_1551();
/*     */   
/*  88 */   public int mouseX = 0;
/*  89 */   public int mouseY = 0;
/*  90 */   public Fullscreen fullscreen = null;
/*     */   
/*     */   private final FloatBuffer modelMatrixBuf;
/*     */   
/*     */   private final FloatBuffer projMatrixBuf;
/*     */   
/*     */   private final Vector3f windowPos;
/*     */   private final Vector3f objPose;
/*     */   private final int[] viewport;
/*     */   private final Matrix4f modelMatrix;
/*     */   private final Matrix4f projectionMatrix;
/*     */   private double currentRotation;
/*     */   protected final GridLines gridLines;
/*     */   private CompletableFuture<Set<String>> mapTypeFileFuture;
/*     */   private boolean renderReady = false;
/*     */   
/*     */   public MapRenderer(Context.UI contextUi) {
/* 107 */     this.contextUi = contextUi;
/* 108 */     this.gridLines = new GridLines(this);
/* 109 */     this.modelMatrixBuf = BufferUtils.createFloatBuffer(16);
/* 110 */     this.projMatrixBuf = BufferUtils.createFloatBuffer(16);
/* 111 */     this.windowPos = new Vector3f();
/* 112 */     this.objPose = new Vector3f();
/* 113 */     this.viewport = new int[4];
/* 114 */     this.modelMatrix = new Matrix4f(this.modelMatrixBuf);
/* 115 */     this.projectionMatrix = new Matrix4f(this.projMatrixBuf);
/*     */     
/* 117 */     this.centerBlockX = this.mc.field_1773.method_19418().method_19328().method_10263();
/* 118 */     this.centerBlockZ = this.mc.field_1773.method_19418().method_19328().method_10260();
/*     */     
/*     */     try {
/* 121 */       this.uiState = UIState.newInactive(contextUi, class_310.method_1551());
/*     */     }
/* 123 */     catch (Throwable t) {
/*     */       
/* 125 */       t.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateMapTypeImageFiles() {
/* 131 */     if (this.mapTypeFileFuture == null || this.mapTypeFileFuture.isDone()) {
/*     */       
/* 133 */       this.mapTypeFileFuture = CompletableFuture.supplyAsync(() -> RegionImageHandler.getImageFilesForMapType(class_310.method_1551(), this.mapType), 
/* 134 */           (Executor)class_156.method_18349());
/*     */       
/* 136 */       this.mapTypeFileFuture.whenCompleteAsync((set, throwable) -> {
/*     */             this.mapTypeImageFiles = set;
/*     */             updateGrid(this.centerRegion);
/* 139 */           }(Executor)class_156.method_18349());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void loadInMemoryRegions() {
/* 149 */     List<RegionTile> regionsInMemory = RegionImageCache.INSTANCE.getRegionImageSets().stream().filter(set -> (set.getExistingHolder(this.mapType) != null && set.getExistingHolder(this.mapType).hasTexture() && this.regions.get(set.getRegionCoord()) == null)).map(set -> new RegionTile(set.getRegionCoord(), this.state)).toList();
/* 150 */     for (RegionTile tile : regionsInMemory)
/*     */     {
/* 152 */       this.regions.putIfAbsent(tile.getRegionCoord(), tile);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void sortRegions() {
/* 158 */     if (this.sortingCenterRegion != null && this.centerRegion != null && this.centerRegion.compareTo(this.sortingCenterRegion) == 0) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 163 */     this.sortingCenterRegion = this.centerRegion;
/* 164 */     SortedMap<RegionCoord, RegionTile> oldRegions = this.regions;
/* 165 */     this.regions = Collections.synchronizedSortedMap(new TreeMap<>(RegionCoord.getComparator(this.sortingCenterRegion).reversed()));
/* 166 */     this.regions.putAll(oldRegions);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean setZoom(double zoom) {
/* 171 */     int minZoom = Context.UI.Fullscreen.equals(this.contextUi) ? 2 : 256;
/* 172 */     this.zoom = (int)class_3532.method_15350(zoom, minZoom, 16384.0D);
/* 173 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateGrid(RegionCoord centerRegion) {
/* 178 */     sortRegions();
/* 179 */     this.gridSize = getCalculatedGridSize(this.zoom);
/* 180 */     int regionCount = this.gridSize / 2;
/* 181 */     loadInMemoryRegions();
/* 182 */     this.regionBounds = new Rectangle2D.Double((centerRegion.regionX - regionCount), (centerRegion.regionZ - regionCount), this.gridSize, this.gridSize);
/* 183 */     for (int x = centerRegion.regionX - regionCount; x <= centerRegion.regionX + regionCount; x++) {
/*     */       
/* 185 */       for (int z = centerRegion.regionZ - regionCount; z <= centerRegion.regionZ + regionCount; z++) {
/*     */         
/* 187 */         if (this.mapTypeImageFiles.contains("" + x + "," + x + ".png")) {
/*     */           
/* 189 */           RegionCoord rCoord = RegionCoord.fromRegionPos(this.worldDir, x, z, this.state.getDimension());
/* 190 */           if (this.regions.get(rCoord) == null)
/*     */           {
/* 192 */             this.regions.put(rCoord, new RegionTile(rCoord, this.state));
/*     */           }
/*     */         }
/* 195 */         else if (this.mc.method_1576() == null || this.mc.method_1576().method_3724()) {
/*     */         
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 203 */     this.renderReady = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContext(MapState state) {
/* 208 */     this.worldDir = state.getWorldDir();
/* 209 */     this.mapType = state.getMapType();
/* 210 */     this.state = state;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(class_332 graphics, class_4597.class_4598 buffers, double offsetX, double offsetZ, float alpha, boolean showGrid) {
/*     */     try {
/* 217 */       if (enabled)
/*     */       {
/* 219 */         class_2338 cornerBlock = class_2338.method_49637(this.centerBlockX - 256.0D, 0.0D, this.centerBlockZ - 256.0D);
/* 220 */         Point2D.Double cornerPixel = getBlockPixelInGrid(cornerBlock);
/*     */ 
/*     */         
/*     */         try {
/* 224 */           if (this.renderReady)
/*     */           {
/* 226 */             for (RegionTile region : this.regions.sequencedValues())
/*     */             {
/* 228 */               region.setPosition(this.centerRegion, cornerPixel, this.regionBounds, offsetX, offsetZ, this.zoom);
/* 229 */               region.render(graphics, (class_4597)buffers, this.centerPixelOffset.x, this.centerPixelOffset.y, alpha);
/*     */             }
/*     */           
/*     */           }
/* 233 */         } catch (ConcurrentModificationException concurrentModificationException) {
/*     */ 
/*     */         
/*     */         }
/* 237 */         catch (Throwable t) {
/*     */           
/* 239 */           t.printStackTrace();
/* 240 */           this.logger.error("Error drawing map tiles", t);
/*     */         } 
/* 242 */         this.gridLines.draw(graphics, (class_4597)buffers, this.centerRegion, cornerPixel, this.regionBounds, this.centerPixelOffset.x, this.centerPixelOffset.y, offsetX, offsetZ, this.zoom, alpha, showGrid);
/*     */       }
/*     */       else
/*     */       {
/* 246 */         this.regions.values().forEach(RegionTile::close);
/* 247 */         this.regions.clear();
/*     */       }
/*     */     
/* 250 */     } catch (Throwable t) {
/*     */       
/* 252 */       this.logger.error("Error rendering map: ", t);
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
/*     */   public void draw(class_332 graphics, class_4597.class_4598 buffers, List<? extends DrawStep> drawStepList, Fullscreen fullscreen, int mouseX, int mouseY, double xOffset, double yOffset, double fontScale, double rotation) {
/* 266 */     this.mouseX = mouseX;
/* 267 */     this.mouseY = mouseY;
/* 268 */     this.fullscreen = fullscreen;
/* 269 */     draw(graphics, buffers, drawStepList, xOffset, yOffset, fontScale, rotation);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void draw(class_332 graphics, class_4597.class_4598 buffers, List<? extends DrawStep> drawStepList, double xOffset, double yOffset, double fontScale, double rotation) {
/* 275 */     if (!enabled || drawStepList == null || drawStepList.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/* 279 */     draw(graphics, buffers, xOffset, yOffset, fontScale, rotation, drawStepList.<DrawStep>toArray(new DrawStep[drawStepList.size()]));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void draw(class_332 graphics, class_4597.class_4598 buffers, double xOffset, double yOffset, double fontScale, double rotation, DrawStep... drawSteps) {
/* 288 */     if (enabled)
/*     */     {
/* 290 */       for (DrawStep.Pass pass : DrawStep.Pass.values()) {
/*     */ 
/*     */         
/* 293 */         int zLevel = 0;
/* 294 */         for (DrawStep drawStep : drawSteps) {
/*     */           boolean onScreen;
/* 296 */           if (drawStep instanceof DrawEntityStep) {
/*     */             
/* 298 */             Point2D.Double position = ((DrawEntityStep)drawStep).getPosition(0.0D, 0.0D, this, true);
/* 299 */             onScreen = isOnScreen(position);
/*     */           }
/*     */           else {
/*     */             
/* 303 */             onScreen = true;
/*     */           } 
/*     */           
/* 306 */           if (drawStep != null && onScreen)
/*     */           
/* 308 */           { zLevel++;
/* 309 */             graphics.method_51448().method_22903();
/* 310 */             graphics.method_51448().method_46416(0.0F, 0.0F, zLevel);
/* 311 */             drawStep.draw(graphics, (class_4597)buffers, pass, xOffset, yOffset, this, fontScale, rotation);
/* 312 */             graphics.method_51448().method_22909(); }
/*     */           
/* 314 */           else if (!onScreen && drawStep instanceof DrawEntityStep) { DrawEntityStep entityStep = (DrawEntityStep)drawStep;
/*     */             
/* 316 */             Point2D.Double position = ((DrawEntityStep)drawStep).getPosition(0.0D, 0.0D, this, true);
/* 317 */             ensureOnScreen(position);
/* 318 */             entityStep.drawOffscreen(graphics, (class_4597)buffers, pass, position, this, rotation, true); }
/*     */         
/*     */         } 
/* 321 */         buffers.method_22993();
/*     */       } 
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
/*     */   public Point2D.Double getPixel(double blockX, double blockZ) {
/* 335 */     Point2D.Double pixel = getBlockPixelInGrid(blockX, blockZ);
/* 336 */     if (isOnScreen(pixel))
/*     */     {
/* 338 */       return pixel;
/*     */     }
/*     */ 
/*     */     
/* 342 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private RegionCoord getCenterRegion(double blockX, double blockZ) {
/* 348 */     return RegionCoord.fromChunkPos(this.worldDir, this.mapType, (int)blockX >> 4, (int)blockZ >> 4);
/*     */   }
/*     */ 
/*     */   
/*     */   private double roundToScreenPixel(double value) {
/* 353 */     double pixelSizeInBlocks = 512.0D / this.zoom;
/* 354 */     return pixelSizeInBlocks * Math.floor(value / pixelSizeInBlocks);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Point2D.Double getBlockPixelInGrid(class_2338 pos) {
/* 360 */     return getBlockPixelInGrid(pos.method_10263(), pos.method_10260());
/*     */   }
/*     */ 
/*     */   
/*     */   public Point2D.Double getBlockPixelInGrid(double blockX, double blockZ) {
/* 365 */     double localBlockX = roundToScreenPixel(blockX) - this.centerBlockX;
/* 366 */     double localBlockZ = roundToScreenPixel(blockZ) - this.centerBlockZ;
/*     */     
/* 368 */     double blockSize = this.zoom / 512.0D;
/* 369 */     double pixelOffsetX = (this.mc.method_22683().method_4480() / 2) + localBlockX * blockSize;
/* 370 */     double pixelOffsetZ = (this.mc.method_22683().method_4507() / 2) + localBlockZ * blockSize;
/*     */     
/* 372 */     return new Point2D.Double(pixelOffsetX, pixelOffsetZ);
/*     */   }
/*     */   
/*     */   public class_2338 getBlockAtPixel(Point2D.Double pixel) {
/*     */     int y;
/* 377 */     double centerPixelX = this.lastWidth / 2.0D;
/* 378 */     double centerPixelZ = this.lastHeight / 2.0D;
/*     */     
/* 380 */     double deltaX = (centerPixelX - pixel.x) / this.zoom / 512.0D;
/* 381 */     double deltaZ = (centerPixelZ - this.lastHeight - pixel.y) / this.zoom / 512.0D;
/*     */     
/* 383 */     double x = Math.floor(this.centerBlockX - deltaX);
/* 384 */     double z = Math.floor(this.centerBlockZ + deltaZ);
/*     */ 
/*     */     
/* 387 */     if ((DataCache.getPlayer()).underground.booleanValue()) {
/*     */       
/* 389 */       y = class_3532.method_15357((DataCache.getPlayer()).posY);
/*     */     }
/*     */     else {
/*     */       
/* 393 */       y = (class_310.method_1551()).field_1687.method_8615();
/*     */     } 
/* 395 */     return class_2338.method_49637(x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateBounds() {
/* 400 */     int width = this.mc.method_22683().method_4480();
/* 401 */     int height = this.mc.method_22683().method_4507();
/*     */     
/* 403 */     if (this.screenBounds == null || this.lastWidth != width || this.lastHeight != height || this.blockBounds == null) {
/*     */       
/* 405 */       this.lastWidth = width;
/* 406 */       this.lastHeight = height;
/*     */       
/* 408 */       if (this.viewPortBounds == null) {
/*     */         
/* 410 */         this.screenBounds = new Rectangle2D.Double(-32.0D, -32.0D, (width + 32), (height + 32));
/*     */       }
/*     */       else {
/*     */         
/* 414 */         this.screenBounds = new Rectangle2D.Double((width - this.viewPortBounds.width) / 2.0D, (height - this.viewPortBounds.height) / 2.0D, this.viewPortBounds.width, this.viewPortBounds.height);
/*     */       } 
/*     */ 
/*     */       
/* 418 */       ClientAPI.INSTANCE.flagOverlaysForRerender();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateUIState(boolean isActive) {
/* 425 */     if (isActive && this.screenBounds == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 430 */     UIState newState = null;
/* 431 */     if (isActive) {
/*     */ 
/*     */       
/* 434 */       int worldHeight = (class_310.method_1551()).field_1687.method_8597().comp_653();
/*     */       
/* 436 */       class_2338 upperLeft = getBlockAtPixel(new Point2D.Double(this.screenBounds.getMinX(), this.screenBounds.getMinY()));
/* 437 */       class_2338 lowerRight = getBlockAtPixel(new Point2D.Double(this.screenBounds.getMaxX(), this.screenBounds.getMaxY()));
/*     */       
/* 439 */       this.blockBounds = class_238.method_54784(upperLeft.method_10069(-32, 0, -32), lowerRight.method_10069(32, worldHeight, 32));
/*     */ 
/*     */       
/*     */       try {
/* 443 */         newState = new UIState(this.contextUi, true, this.mapType.dimension, this.zoom, this.mapType.apiMapType, class_2338.method_49637(this.centerBlockX, 0.0D, this.centerBlockZ), this.mapType.vSlice, this.blockBounds, this.screenBounds);
/*     */       }
/* 445 */       catch (Exception e) {
/*     */         
/* 447 */         this.logger.error("Error Creating new UIState: ", e);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 452 */       newState = UIState.newInactive(this.uiState);
/*     */     } 
/*     */ 
/*     */     
/* 456 */     if ((this.uiState == null && newState != null) || (newState != null && !newState.equals(this.uiState))) {
/*     */       
/* 458 */       this.uiState = newState;
/* 459 */       ClientEventManager clientEventManager = ClientAPI.INSTANCE.getClientEventManager();
/* 460 */       DisplayUpdateEvent displayUpdateEvent = new DisplayUpdateEvent(this.uiState);
/* 461 */       if (EventBus.hasListeners((JourneyMapEvent)displayUpdateEvent))
/*     */       {
/* 463 */         clientEventManager.queueDisplayUpdateEvent(displayUpdateEvent);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTiles(MapType mapType, int zoom, boolean fullUpdate) {
/* 470 */     this.mapType = mapType;
/* 471 */     this.zoom = zoom;
/*     */     
/* 473 */     updateBounds();
/* 474 */     RegionTile region = this.regions.get(this.centerRegion);
/*     */ 
/*     */ 
/*     */     
/* 478 */     if (this.centerRegion == null || region == null || (region != null && region.getZoom() != this.zoom)) {
/*     */       
/* 480 */       this.centerRegion = getCenterRegion(this.centerBlockX, this.centerBlockZ);
/* 481 */       if (!fullUpdate)
/*     */       {
/* 483 */         CompletableFuture.runAsync(() -> updateGrid(this.centerRegion), (Executor)class_156.method_18349());
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 488 */     Point2D blockPixelOffset = blockPixelOffsetInRegion(this.centerRegion, this.centerBlockX, this.centerBlockZ);
/*     */     
/* 490 */     double blockSizeOffset = this.uiState.blockSize / 2.0D;
/*     */     
/* 492 */     double displayOffsetX = 0.0D;
/* 493 */     double displayOffsetY = 0.0D;
/*     */     
/* 495 */     if (this.centerBlockX < 0.0D) {
/*     */       
/* 497 */       displayOffsetX -= blockSizeOffset;
/*     */     }
/*     */     else {
/*     */       
/* 501 */       displayOffsetX += blockSizeOffset;
/*     */     } 
/*     */     
/* 504 */     if (this.centerBlockZ > 0.0D) {
/*     */       
/* 506 */       displayOffsetY += blockSizeOffset;
/*     */     }
/*     */     else {
/*     */       
/* 510 */       displayOffsetY -= blockSizeOffset;
/*     */     } 
/*     */     
/* 513 */     this.centerPixelOffset.setLocation(displayOffsetX + blockPixelOffset.getX(), displayOffsetY + blockPixelOffset.getY());
/* 514 */     if (fullUpdate)
/*     */     {
/*     */       
/* 517 */       updateMapTypeImageFiles();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Point2D blockPixelOffsetInRegion(RegionCoord centerRegion, double centerBlockX, double centerBlockZ) {
/* 523 */     double blockSize = (getUIState()).blockSize;
/* 524 */     double localBlockX = centerRegion.getMinChunkCoord().method_8326() - Math.floor(centerBlockX);
/* 525 */     double localBlockZ = centerRegion.getMinChunkCoord().method_8328() - Math.floor(centerBlockZ);
/* 526 */     if (centerBlockX < 0.0D)
/*     */     {
/* 528 */       localBlockX++;
/*     */     }
/* 530 */     if (centerBlockZ < 0.0D)
/*     */     {
/* 532 */       localBlockZ++;
/*     */     }
/* 534 */     double pixelOffsetX = (this.zoom >> 1) + localBlockX * blockSize - blockSize / 2.0D;
/* 535 */     double pixelOffsetZ = (this.zoom >> 1) + localBlockZ * blockSize - blockSize / 2.0D;
/* 536 */     return new Point2D.Double(pixelOffsetX, pixelOffsetZ);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean center() {
/* 541 */     return center(this.worldDir, this.mapType, this.centerBlockX, this.centerBlockZ, this.zoom);
/*     */   }
/*     */ 
/*     */   
/*     */   public void move(double deltaBlockX, double deltaBlockZ) {
/* 546 */     center(this.worldDir, this.mapType, this.centerBlockX + deltaBlockX, this.centerBlockZ + deltaBlockZ, this.zoom);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean center(File worldDir, MapType mapType, double blockX, double blockZ, int zoom) {
/* 551 */     boolean mapTypeChanged = (!Objects.equals(worldDir, this.worldDir) || !Objects.equals(mapType, this.mapType));
/*     */     
/* 553 */     if (!Objects.equals(worldDir, this.worldDir))
/*     */     {
/* 555 */       this.worldDir = worldDir;
/*     */     }
/*     */     
/* 558 */     blockX = roundToScreenPixel(blockX);
/* 559 */     blockZ = roundToScreenPixel(blockZ);
/*     */     
/* 561 */     if (blockX == this.centerBlockX && blockZ == this.centerBlockZ && zoom == this.zoom && !mapTypeChanged && !this.regions.isEmpty()) {
/*     */ 
/*     */       
/* 564 */       if (!Objects.equals(mapType.apiMapType, this.uiState.mapType))
/*     */       {
/* 566 */         updateUIState(true);
/*     */       }
/*     */       
/* 569 */       return false;
/*     */     } 
/*     */     
/* 572 */     this.centerBlockX = blockX;
/* 573 */     this.centerBlockZ = blockZ;
/* 574 */     this.zoom = zoom;
/*     */ 
/*     */     
/* 577 */     RegionCoord newCenterRegion = getCenterRegion(this.centerBlockX, this.centerBlockZ);
/* 578 */     boolean centerTileChanged = !newCenterRegion.equals(this.centerRegion);
/*     */     
/* 580 */     if (mapTypeChanged || centerTileChanged) {
/*     */ 
/*     */       
/* 583 */       this.centerRegion = newCenterRegion;
/* 584 */       CompletableFuture.runAsync(() -> updateGrid(this.centerRegion), (Executor)class_156.method_18349());
/*     */     } 
/*     */     
/* 587 */     updateUIState(true);
/* 588 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOnScreen(Point2D.Double pixel) {
/* 599 */     return this.screenBounds.contains(pixel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOnScreen(Rectangle2D.Double bounds) {
/* 610 */     return this.screenBounds.intersects(bounds);
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
/*     */   public boolean isOnScreen(double x, double y) {
/* 622 */     return this.screenBounds.contains(x, y);
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
/*     */   public boolean isOnScreen(double startX, double startY, int width, int height) {
/* 636 */     if (this.screenBounds == null)
/*     */     {
/* 638 */       return false;
/*     */     }
/*     */     
/* 641 */     return this.screenBounds.intersects(startX, startY, width, height);
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 646 */     if (this.mapTypeFileFuture != null) {
/*     */       
/* 648 */       this.mapTypeFileFuture.cancel(true);
/* 649 */       this.mapTypeFileFuture = null;
/*     */     } 
/* 651 */     this.renderReady = false;
/* 652 */     this.regions.values().forEach(RegionTile::close);
/* 653 */     this.regions.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public UIState getUIState() {
/* 658 */     return this.uiState;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Fullscreen getFullscreen() {
/* 664 */     return this.fullscreen;
/*     */   }
/*     */ 
/*     */   
/*     */   public MapType getMapType() {
/* 669 */     return this.mapType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearGlErrors(boolean report) {
/*     */     int err;
/* 678 */     while ((err = RenderWrapper.getError()) != 0) {
/*     */       
/* 680 */       if (report && this.glErrors <= 20) {
/*     */         
/* 682 */         this.glErrors++;
/* 683 */         if (this.glErrors < 20) {
/*     */           
/* 685 */           this.logger.warn("GL Error occurred during JourneyMap draw: " + err);
/*     */           
/*     */           continue;
/*     */         } 
/* 689 */         this.logger.warn("GL Error reporting during JourneyMap will be suppressed after max errors: 20");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasUnloadedTile() {
/*     */     try {
/* 699 */       for (RegionTile tile : this.regions.values())
/*     */       {
/* 701 */         if (this.mapTypeFileFuture == null || !this.mapTypeFileFuture.isDone())
/*     */         {
/* 703 */           return false;
/*     */         }
/* 705 */         if (isOnScreen(tile.getX(), tile.getY()))
/*     */         {
/* 707 */           if (tile.getTexture() != null && tile.getTexture().isDefunct() && tile.shouldRender())
/*     */           {
/* 709 */             return true;
/*     */           }
/*     */         }
/*     */       }
/*     */     
/* 714 */     } catch (ConcurrentModificationException concurrentModificationException) {}
/*     */ 
/*     */ 
/*     */     
/* 718 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void ensureOnScreen(Point2D pixel) {
/* 724 */     if (this.screenBounds == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 729 */     double x = pixel.getX();
/* 730 */     if (x <= 0.0D) {
/*     */       
/* 732 */       x = 0.0D;
/*     */     }
/* 734 */     else if (x > this.screenBounds.getMaxX()) {
/*     */       
/* 736 */       x = this.screenBounds.getMaxX();
/*     */     } 
/*     */     
/* 739 */     double y = pixel.getY();
/* 740 */     if (y <= 0.0D) {
/*     */       
/* 742 */       y = 0.0D;
/*     */     }
/* 744 */     else if (y > this.screenBounds.getMaxY()) {
/*     */       
/* 746 */       y = this.screenBounds.getMaxY();
/*     */     } 
/*     */     
/* 749 */     pixel.setLocation(x, y);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateRotation(class_332 graphics, double rotation) {
/* 754 */     this.currentRotation = rotation;
/* 755 */     this.viewport[0] = 0;
/* 756 */     this.viewport[1] = 0;
/* 757 */     this.viewport[2] = class_310.method_1551().method_22683().method_4489();
/* 758 */     this.viewport[3] = class_310.method_1551().method_22683().method_4506();
/* 759 */     graphics.method_51448().method_23760().method_23761().get(this.modelMatrixBuf);
/* 760 */     RenderWrapper.getProjectionMatrix().get(this.projMatrixBuf);
/* 761 */     this.modelMatrix.set(this.modelMatrixBuf);
/* 762 */     this.projectionMatrix.set(this.projMatrixBuf).mul((Matrix4fc)this.modelMatrix);
/*     */   }
/*     */ 
/*     */   
/*     */   public Point2D shiftWindowPosition(double x, double y, int shiftX, int shiftY) {
/* 767 */     if (this.currentRotation % 360.0D == 0.0D)
/*     */     {
/* 769 */       return new Point2D.Double(x + shiftX, y + shiftY);
/*     */     }
/*     */ 
/*     */     
/* 773 */     this.projectionMatrix.project((float)x, (float)y, 0.0F, this.viewport, this.windowPos);
/* 774 */     this.projectionMatrix.unproject(this.windowPos.get(0) + shiftX, this.windowPos.get(1) + shiftY, 0.0F, this.viewport, this.objPose);
/* 775 */     return new Point2D.Float(this.objPose.get(0), this.objPose.get(1));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Point2D.Double getWindowPosition(Point2D.Double matrixPixel) {
/* 781 */     if (this.currentRotation % 360.0D == 0.0D)
/*     */     {
/* 783 */       return matrixPixel;
/*     */     }
/*     */ 
/*     */     
/* 787 */     this.projectionMatrix.project((float)matrixPixel.getX(), (float)matrixPixel.getY(), 0.0F, this.viewport, this.windowPos);
/* 788 */     return new Point2D.Double(this.windowPos.get(0), this.windowPos.get(1));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setViewPortBounds(Rectangle2D.Double viewPortBounds) {
/* 794 */     this.viewPortBounds = viewPortBounds;
/* 795 */     this.screenBounds = null;
/* 796 */     updateBounds();
/*     */   }
/*     */ 
/*     */   
/*     */   public double getCenterBlockX() {
/* 801 */     return this.centerBlockX;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getCenterBlockZ() {
/* 806 */     return this.centerBlockZ;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getZoom() {
/* 812 */     return this.zoom;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMouseX() {
/* 817 */     return this.mouseX;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMouseY() {
/* 822 */     return this.mouseY;
/*     */   }
/*     */ 
/*     */   
/*     */   public Context.UI getContext() {
/* 827 */     return this.contextUi;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWidth() {
/* 832 */     return this.lastWidth;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHeight() {
/* 837 */     return this.lastHeight;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getGridSize() {
/* 842 */     return this.gridSize;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCalculatedGridSize(int zoom) {
/* 847 */     class_1041 mainWindow = class_310.method_1551().method_22683();
/* 848 */     int width = Context.UI.Fullscreen.equals((getUIState()).ui) ? mainWindow.method_4489() : (UIManager.INSTANCE.getMiniMap().getDisplayVars()).minimapWidth;
/*     */     
/* 850 */     int gridSize = (int)Math.ceil(width / zoom);
/*     */ 
/*     */     
/* 853 */     gridSize++;
/*     */     
/* 855 */     if (gridSize % 2 == 0)
/*     */     {
/* 857 */       gridSize++;
/*     */     }
/* 859 */     return gridSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setEnabled(boolean enabled) {
/* 869 */     MapRenderer.enabled = enabled;
/*     */   }
/*     */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\render\map\MapRenderer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */