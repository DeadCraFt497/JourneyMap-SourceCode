/*     */ package journeymap.client;
/*     */ 
/*     */ import commonnetwork.api.Network;
/*     */ import java.io.File;
/*     */ import journeymap.api.client.impl.ClientAPI;
/*     */ import journeymap.api.client.impl.EntityRegistrationFactory;
/*     */ import journeymap.api.client.impl.OptionsDisplayFactory;
/*     */ import journeymap.api.plugins.PokemonOptionsPlugin;
/*     */ import journeymap.api.services.CommonPlatformService;
/*     */ import journeymap.api.services.Services;
/*     */ import journeymap.api.services.WebMapService;
/*     */ import journeymap.api.v2.client.IClientAPI;
/*     */ import journeymap.api.v2.client.util.PluginHelper;
/*     */ import journeymap.client.cartography.ChunkRenderController;
/*     */ import journeymap.client.cartography.color.ColorManager;
/*     */ import journeymap.client.data.DataCache;
/*     */ import journeymap.client.event.handlers.ChunkMonitorHandler;
/*     */ import journeymap.client.event.handlers.EntityRadarUpdateEventHandler;
/*     */ import journeymap.client.event.handlers.PopupMenuEventHandler;
/*     */ import journeymap.client.event.handlers.ResourceReloadHandler;
/*     */ import journeymap.client.event.handlers.keymapping.KeyEvent;
/*     */ import journeymap.client.io.FileHandler;
/*     */ import journeymap.client.io.IconSetFileHandler;
/*     */ import journeymap.client.io.ThemeLoader;
/*     */ import journeymap.client.log.ChatLog;
/*     */ import journeymap.client.log.JMLogger;
/*     */ import journeymap.client.log.StatTimer;
/*     */ import journeymap.client.model.region.RegionImageCache;
/*     */ import journeymap.client.properties.CoreProperties;
/*     */ import journeymap.client.properties.FullMapProperties;
/*     */ import journeymap.client.properties.MiniMapProperties;
/*     */ import journeymap.client.properties.RenderingProperties;
/*     */ import journeymap.client.properties.TopoProperties;
/*     */ import journeymap.client.properties.WaypointProperties;
/*     */ import journeymap.client.properties.WebMapProperties;
/*     */ import journeymap.client.task.main.IMainThreadTask;
/*     */ import journeymap.client.task.main.MainTaskController;
/*     */ import journeymap.client.task.main.MappingMonitorTask;
/*     */ import journeymap.client.task.multi.ITaskManager;
/*     */ import journeymap.client.task.multi.TaskController;
/*     */ import journeymap.client.ui.UIManager;
/*     */ import journeymap.client.ui.fullscreen.Fullscreen;
/*     */ import journeymap.client.waypoint.WaypointHandler;
/*     */ import journeymap.common.Journeymap;
/*     */ import journeymap.common.log.LogFormatter;
/*     */ import journeymap.common.migrate.Migration;
/*     */ import journeymap.common.nbt.RegionDataStorageHandler;
/*     */ import journeymap.common.network.dispatch.ClientNetworkDispatcher;
/*     */ import journeymap.common.network.handler.ClientPacketHandler;
/*     */ import journeymap.common.version.VersionCheck;
/*     */ import net.minecraft.class_1937;
/*     */ import net.minecraft.class_2960;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_3300;
/*     */ import net.minecraft.class_3302;
/*     */ import net.minecraft.class_3304;
/*     */ import net.minecraft.class_5321;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class JourneymapClient
/*     */ {
/*  63 */   public static final String FULL_VERSION = Journeymap.MC_VERSION + "-" + Journeymap.MC_VERSION;
/*  64 */   public static final String MOD_NAME = Journeymap.SHORT_MOD_NAME + " " + Journeymap.SHORT_MOD_NAME;
/*     */ 
/*     */   
/*  67 */   private volatile String currentWorldId = null;
/*     */   
/*     */   private volatile CoreProperties coreProperties;
/*     */   private volatile FullMapProperties fullMapProperties;
/*     */   private volatile MiniMapProperties miniMapProperties1;
/*     */   private volatile MiniMapProperties miniMapProperties2;
/*     */   private volatile TopoProperties topoProperties;
/*     */   private volatile WebMapProperties webMapProperties;
/*     */   private volatile RenderingProperties renderingProperties;
/*     */   private volatile WaypointProperties waypointProperties;
/*     */   private volatile OptionsDisplayFactory optionsDisplayFactory;
/*  78 */   private volatile Boolean initialized = Boolean.valueOf(false);
/*     */   
/*     */   private final InternalStateHandler stateHandler;
/*  81 */   private Logger logger = LogManager.getLogger("journeymap");
/*     */   
/*     */   private boolean enabled = true;
/*     */   
/*     */   private boolean threadLogging = false;
/*     */   
/*     */   public boolean hasOptifine = false;
/*     */   
/*     */   private boolean started = false;
/*     */   
/*     */   private ClientNetworkDispatcher dispatcher;
/*     */   
/*     */   private ClientPacketHandler packetHandler;
/*     */   
/*     */   private TaskController multithreadTaskController;
/*     */   
/*     */   private ChunkRenderController chunkRenderController;
/*     */   private final MainTaskController mainThreadTaskController;
/*     */   private KeyEvent keyEvents;
/*     */   private static JourneymapClient instance;
/*     */   
/*     */   public JourneymapClient() {
/* 103 */     instance = this;
/* 104 */     this.mainThreadTaskController = new MainTaskController();
/* 105 */     this.stateHandler = new InternalStateHandler();
/* 106 */     this.packetHandler = new ClientPacketHandler();
/*     */   }
/*     */ 
/*     */   
/*     */   public static JourneymapClient getInstance() {
/* 111 */     return instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {
/* 119 */     StatTimer timer = null;
/*     */     
/*     */     try {
/* 122 */       timer = StatTimer.getDisposable("elapsed").start();
/*     */ 
/*     */       
/* 125 */       boolean migrationOk = (new Migration("journeymap.common.migrate")).performTasks();
/*     */ 
/*     */       
/* 128 */       this.logger = JMLogger.init();
/* 129 */       this.logger.info("initialize ENTER");
/*     */       
/* 131 */       if (this.initialized.booleanValue()) {
/*     */         
/* 133 */         this.logger.warn("Already initialized, aborting");
/*     */         
/*     */         return;
/*     */       } 
/* 137 */       PluginHelper.INSTANCE.initPlugins((IClientAPI)ClientAPI.INSTANCE);
/* 138 */       if (Services.COMMON_SERVICE.isModLoaded("pixelmon") || Services.COMMON_SERVICE.isModLoaded("cobblemon")) {
/*     */         
/* 140 */         String modId = Services.COMMON_SERVICE.isModLoaded("pixelmon") ? "pixelmon" : "cobblemon";
/* 141 */         (new PokemonOptionsPlugin(modId)).initialize((IClientAPI)ClientAPI.INSTANCE);
/*     */       } 
/*     */       
/* 144 */       this.logger.debug("Loading and Generating Journeymap configs");
/* 145 */       loadConfigProperties();
/*     */       
/* 147 */       JMLogger.logProperties();
/*     */ 
/*     */       
/* 150 */       this.threadLogging = false;
/* 151 */       EntityRadarUpdateEventHandler.init();
/* 152 */       EntityRegistrationFactory.init();
/* 153 */       PopupMenuEventHandler.init();
/*     */       
/* 155 */       this.keyEvents.getHandler().registerActions();
/* 156 */       this.logger.info("initialize EXIT, " + ((timer == null) ? "" : timer.getLogReportString()));
/*     */     }
/* 158 */     catch (Throwable t) {
/*     */       
/* 160 */       if (this.logger == null)
/*     */       {
/* 162 */         this.logger = LogManager.getLogger("journeymap");
/*     */       }
/* 164 */       this.logger.error(LogFormatter.toString(t));
/* 165 */       throw t;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void postInit() {
/* 174 */     StatTimer timer = null;
/*     */     
/*     */     try {
/* 177 */       this.logger.debug("postInitialize ENTER");
/* 178 */       timer = StatTimer.getDisposable("elapsed").start();
/*     */ 
/*     */       
/* 181 */       queueMainThreadTask((IMainThreadTask)new MappingMonitorTask());
/*     */ 
/*     */       
/* 184 */       IconSetFileHandler.initialize();
/*     */ 
/*     */       
/* 187 */       ThemeLoader.initialize(true);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 192 */       if (Services.COMMON_SERVICE.getLoader() != CommonPlatformService.Loader.NEOFORGE) {
/*     */         
/* 194 */         class_3300 resourceManager = class_310.method_1551().method_1478();
/* 195 */         ResourceReloadHandler.getInstance()
/* 196 */           .getOnResourceReloadHandlers()
/* 197 */           .forEach((resource, listener) -> ((class_3304)resourceManager).method_14477(listener));
/*     */       } 
/*     */ 
/*     */       
/* 201 */       ResourceReloadHandler.getInstance().onResourceReload();
/*     */ 
/*     */       
/* 204 */       if (this.webMapProperties.enabled.get().booleanValue() && getWebMap() != null) {
/*     */         
/*     */         try {
/*     */           
/* 208 */           startWebMap();
/*     */         }
/* 210 */         catch (Throwable e) {
/*     */           
/* 212 */           this.logger.warn("Webmap not found, likely in forge dev environment.");
/*     */         } 
/*     */       }
/*     */       
/* 216 */       ChatLog.announceMod(false);
/* 217 */       this.initialized = Boolean.valueOf(true);
/*     */       
/* 219 */       VersionCheck.getVersionAvailable();
/*     */ 
/*     */       
/* 222 */       optifineCheck();
/*     */ 
/*     */       
/* 225 */       if (-1 == (getKeyEvents().getHandler()).kbToggleWaypointRendering.getKeyValue().method_1444() && 
/* 226 */         !(getInstance().getWaypointProperties()).renderWaypoints.get().booleanValue())
/*     */       {
/* 228 */         (getInstance().getWaypointProperties()).renderWaypoints.set(Boolean.valueOf(true));
/*     */       
/*     */       }
/*     */     }
/* 232 */     catch (Throwable t) {
/*     */       
/* 234 */       if (this.logger == null)
/*     */       {
/* 236 */         this.logger = LogManager.getLogger("journeymap");
/*     */       }
/* 238 */       this.logger.error(LogFormatter.toString(t));
/*     */     }
/*     */     finally {
/*     */       
/* 242 */       this.logger.info("postInitialize EXIT, " + ((timer == null) ? "" : timer.getLogReportString()));
/*     */     } 
/*     */     
/* 245 */     JMLogger.setLevelFromProperties();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void commonSetup() {
/*     */     try {
/* 253 */       PluginHelper.INSTANCE.preInitPlugins(Services.COMMON_SERVICE.getClientPluginScanResult());
/*     */     }
/* 255 */     catch (Throwable t) {
/*     */       
/* 257 */       t.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void optifineCheck() {
/*     */     try {
/* 265 */       Class<?> optifine = Class.forName("net.optifine.override.ChunkCacheOF");
/* 266 */       this.logger.info("OptiFine detected.");
/* 267 */       this.hasOptifine = (optifine != null);
/*     */     }
/* 269 */     catch (ClassNotFoundException e) {
/*     */       
/* 271 */       this.hasOptifine = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CoreProperties getCoreProperties() {
/* 280 */     return this.coreProperties;
/*     */   }
/*     */ 
/*     */   
/*     */   public RenderingProperties getRenderingProperties() {
/* 285 */     return this.renderingProperties;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FullMapProperties getFullMapProperties() {
/* 293 */     return this.fullMapProperties;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TopoProperties getTopoProperties() {
/* 301 */     return this.topoProperties;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void disable() {
/* 309 */     this.initialized = Boolean.valueOf(false);
/* 310 */     this.enabled = false;
/* 311 */     stopMapping();
/* 312 */     ClientAPI.INSTANCE.purge();
/* 313 */     DataCache.INSTANCE.purge();
/*     */   }
/*     */ 
/*     */   
/*     */   public void enable() {
/* 318 */     this.enabled = true;
/* 319 */     if (!this.initialized.booleanValue()) {
/*     */ 
/*     */       
/* 322 */       this.initialized = Boolean.valueOf(true);
/* 323 */       if ((getInstance().getCoreProperties()).mappingEnabled.get().booleanValue()) {
/*     */         
/* 325 */         startMapping();
/*     */       }
/*     */       else {
/*     */         
/* 329 */         reset();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MiniMapProperties getMiniMapProperties(int which) {
/* 339 */     switch (which) {
/*     */ 
/*     */       
/*     */       case 2:
/* 343 */         this.miniMapProperties2.setActive(true);
/* 344 */         this.miniMapProperties1.setActive(false);
/* 345 */         return getMiniMapProperties2();
/*     */     } 
/*     */ 
/*     */     
/* 349 */     this.miniMapProperties1.setActive(true);
/* 350 */     this.miniMapProperties2.setActive(false);
/* 351 */     return getMiniMapProperties1();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MiniMapProperties getActiveMiniMapProperties() {
/* 358 */     if (this.miniMapProperties1.isActive())
/*     */     {
/* 360 */       return getMiniMapProperties1();
/*     */     }
/*     */ 
/*     */     
/* 364 */     return getMiniMapProperties2();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getActiveMinimapId() {
/* 373 */     if (this.miniMapProperties1.isActive())
/*     */     {
/* 375 */       return 1;
/*     */     }
/*     */ 
/*     */     
/* 379 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MiniMapProperties getMiniMapProperties1() {
/* 388 */     return this.miniMapProperties1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MiniMapProperties getMiniMapProperties2() {
/* 396 */     return this.miniMapProperties2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WebMapProperties getWebMapProperties() {
/* 404 */     return this.webMapProperties;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WaypointProperties getWaypointProperties() {
/* 412 */     return this.waypointProperties;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isUpdateCheckEnabled() {
/* 417 */     return (getCoreProperties()).checkUpdates.get().booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Boolean isInitialized() {
/* 427 */     return this.initialized;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Boolean started() {
/* 438 */     return Boolean.valueOf(this.started);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Boolean isMapping() {
/* 449 */     return Boolean.valueOf((this.initialized.booleanValue() && this.multithreadTaskController != null && this.multithreadTaskController.isActive().booleanValue()));
/*     */   }
/*     */ 
/*     */   
/*     */   public MainTaskController getMainThreadTaskController() {
/* 454 */     return this.mainThreadTaskController;
/*     */   }
/*     */ 
/*     */   
/*     */   public TaskController getMultithreadTaskController() {
/* 459 */     return this.multithreadTaskController;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Boolean isThreadLogging() {
/* 469 */     return Boolean.valueOf(this.threadLogging);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean enabled() {
/* 474 */     return this.enabled;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setEnable() {
/* 479 */     this.initialized = Boolean.valueOf(true);
/* 480 */     this.enabled = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WebMapService getWebMap() {
/*     */     try {
/* 492 */       return Services.WEB_MAP_SERVICE;
/*     */     }
/* 494 */     catch (Throwable e) {
/*     */       
/* 496 */       JMLogger.logOnce("Webmap not found.");
/*     */       
/* 498 */       (getWebMapProperties()).enabled.set(Boolean.valueOf(false));
/* 499 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void startWebMap() {
/* 505 */     if (getWebMap() != null) {
/*     */       
/* 507 */       String webmapVersion = null;
/*     */       
/*     */       try {
/* 510 */         webmapVersion = getWebMap().getVersion();
/*     */         
/* 512 */         getWebMap().start();
/*     */       }
/* 514 */       catch (Throwable e) {
/*     */         
/* 516 */         (getWebMapProperties()).enabled.set(Boolean.valueOf(false));
/* 517 */         webmapVersion = Services.COMMON_SERVICE.getModVersion("journeymap_webmap");
/* 518 */         ChatLog.announceError("Invalid WebMap version: " + webmapVersion + " need WebMap version: 1.0.8 or higher.");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void stopWebMap() {
/* 525 */     if (getWebMap() != null)
/*     */     {
/* 527 */       getWebMap().stop();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void queueOneOff(Runnable runnable) throws Exception {
/* 538 */     if (this.multithreadTaskController != null)
/*     */     {
/* 540 */       this.multithreadTaskController.queueOneOff(runnable);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void toggleTask(Class<? extends ITaskManager> managerClass, boolean enable, Object params) {
/* 551 */     if (this.multithreadTaskController != null)
/*     */     {
/* 553 */       this.multithreadTaskController.toggleTask(managerClass, enable, params);
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
/*     */   public boolean isTaskManagerEnabled(Class<? extends ITaskManager> managerClass) {
/* 565 */     if (this.multithreadTaskController != null)
/*     */     {
/* 567 */       return this.multithreadTaskController.isTaskManagerEnabled(managerClass);
/*     */     }
/*     */ 
/*     */     
/* 571 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMainThreadTaskActive() {
/* 582 */     if (this.mainThreadTaskController != null)
/*     */     {
/* 584 */       return this.mainThreadTaskController.isActive();
/*     */     }
/*     */ 
/*     */     
/* 588 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startMapping() {
/* 597 */     synchronized (this) {
/*     */ 
/*     */       
/* 600 */       class_310 mc = class_310.method_1551();
/*     */       
/* 602 */       if (mc == null || mc.field_1687 == null || !this.initialized.booleanValue()) {
/*     */         return;
/*     */       }
/*     */       
/* 606 */       this.started = true;
/* 607 */       if (!this.coreProperties.mappingEnabled.get().booleanValue()) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 612 */       File worldDir = FileHandler.getJMWorldDir(mc, this.currentWorldId);
/* 613 */       if (worldDir == null) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 618 */       if (!worldDir.exists()) {
/*     */         
/* 620 */         boolean created = worldDir.mkdirs();
/* 621 */         if (!created) {
/*     */           
/* 623 */           JMLogger.logOnce("CANNOT CREATE DATA DIRECTORY FOR WORLD: " + worldDir.getPath());
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/* 628 */       reset();
/*     */       
/* 630 */       this.multithreadTaskController = new TaskController();
/* 631 */       this.multithreadTaskController.enableTasks();
/*     */       
/* 633 */       ColorManager.INSTANCE.loadPalettes();
/*     */       
/* 635 */       long totalMB = Runtime.getRuntime().totalMemory() / 1024L / 1024L;
/* 636 */       long freeMB = Runtime.getRuntime().freeMemory() / 1024L / 1024L;
/* 637 */       String memory = String.format("Memory: %sMB total, %sMB free", new Object[] { Long.valueOf(totalMB), Long.valueOf(freeMB) });
/* 638 */       class_5321<class_1937> dimension = mc.field_1687.method_27983();
/* 639 */       this.logger.info(String.format("Mapping started in %s%s%s. %s ", new Object[] { FileHandler.getJMWorldDir(mc, this.currentWorldId), File.separator, 
/*     */               
/* 641 */               FileHandler.getDimNameForPath(FileHandler.getJMWorldDir(mc, this.currentWorldId), dimension), memory }));
/*     */ 
/*     */ 
/*     */       
/* 645 */       if (this.stateHandler.isJourneyMapServerConnection() || this.stateHandler.isModdedServerConnection() || class_310.method_1551().method_1496())
/*     */       {
/*     */         
/* 648 */         getDispatcher().sendPermissionRequest();
/*     */       }
/* 650 */       ClientAPI.INSTANCE.getClientEventManager().fireMappingEvent(true, dimension);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void stopMapping() {
/* 659 */     synchronized (this) {
/*     */       
/* 661 */       this.started = false;
/* 662 */       ChunkMonitorHandler.getInstance().reset();
/*     */       
/* 664 */       class_310 mc = class_310.method_1551();
/* 665 */       if (isMapping().booleanValue() && mc != null) {
/*     */         
/* 667 */         this.logger.info(String.format("Mapping halted in %s%s%s", new Object[] { FileHandler.getJMWorldDir(mc, this.currentWorldId), File.separator, mc.field_1687
/* 668 */                 .method_27983().method_29177() }));
/* 669 */         RegionImageCache.INSTANCE.flushToDiskAsync(true);
/*     */       } 
/*     */       
/* 672 */       if (this.multithreadTaskController != null) {
/*     */         
/* 674 */         this.multithreadTaskController.disableTasks();
/* 675 */         this.multithreadTaskController.clear();
/* 676 */         this.multithreadTaskController = null;
/*     */       } 
/*     */       
/* 679 */       if (mc != null) {
/*     */         
/* 681 */         class_5321<class_1937> dimension = (mc.field_1687 != null) ? mc.field_1687.method_27983() : class_1937.field_25179;
/* 682 */         ClientAPI.INSTANCE.getClientEventManager().fireMappingEvent(false, dimension);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {
/* 692 */     this.stateHandler.reset();
/* 693 */     if (!class_310.method_1551().method_1496()) {
/*     */       
/*     */       try {
/*     */         
/* 697 */         getDispatcher().sendPermissionRequest();
/* 698 */         getDispatcher().sendWorldIdRequest();
/*     */       }
/* 700 */       catch (Exception e) {
/*     */         
/* 702 */         JMLogger.throwLogOnce("error sending packet request, likely due to being on singleplayer", e);
/*     */       } 
/*     */     }
/*     */     
/* 706 */     loadConfigProperties();
/* 707 */     DataCache.INSTANCE.purge();
/* 708 */     ChunkMonitorHandler.getInstance().reset();
/* 709 */     this.chunkRenderController = new ChunkRenderController();
/* 710 */     (Fullscreen.state()).follow.set(true);
/* 711 */     StatTimer.resetAll();
/* 712 */     UIManager.INSTANCE.reset();
/* 713 */     WaypointHandler.getInstance().reset();
/* 714 */     RegionDataStorageHandler.getInstance().flushDataCache();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void queueMainThreadTask(IMainThreadTask task) {
/* 724 */     this.mainThreadTaskController.addTask(task);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void performMainThreadTasks() {
/* 732 */     this.mainThreadTaskController.performTasks();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void performMultithreadTasks() {
/*     */     try {
/* 742 */       synchronized (this)
/*     */       {
/* 744 */         if (isMapping().booleanValue())
/*     */         {
/* 746 */           this.multithreadTaskController.performTasks();
/*     */         }
/*     */       }
/*     */     
/* 750 */     } catch (Throwable t) {
/*     */       
/* 752 */       String error = "Error in JourneyMap.performMultithreadTasks(): " + t.getMessage();
/* 753 */       ChatLog.announceError(error);
/* 754 */       this.logger.error(LogFormatter.toString(t));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChunkRenderController getChunkRenderController() {
/* 765 */     return this.chunkRenderController;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveConfigProperties() {
/* 773 */     if (this.coreProperties != null)
/*     */     {
/* 775 */       this.coreProperties.save();
/*     */     }
/* 777 */     if (this.fullMapProperties != null)
/*     */     {
/* 779 */       this.fullMapProperties.save();
/*     */     }
/* 781 */     if (this.miniMapProperties1 != null)
/*     */     {
/* 783 */       this.miniMapProperties1.save();
/*     */     }
/* 785 */     if (this.miniMapProperties2 != null)
/*     */     {
/* 787 */       this.miniMapProperties2.save();
/*     */     }
/* 789 */     if (this.topoProperties != null)
/*     */     {
/* 791 */       this.topoProperties.save();
/*     */     }
/* 793 */     if (this.webMapProperties != null)
/*     */     {
/* 795 */       this.webMapProperties.save();
/*     */     }
/* 797 */     if (this.waypointProperties != null)
/*     */     {
/* 799 */       this.waypointProperties.save();
/*     */     }
/* 801 */     if (this.optionsDisplayFactory != null)
/*     */     {
/* 803 */       this.optionsDisplayFactory.save();
/*     */     }
/* 805 */     if (this.renderingProperties != null)
/*     */     {
/* 807 */       this.renderingProperties.save();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadConfigProperties() {
/* 816 */     saveConfigProperties();
/* 817 */     this.optionsDisplayFactory = (new OptionsDisplayFactory()).buildAddonProperties().load();
/* 818 */     this.coreProperties = (CoreProperties)(new CoreProperties()).load();
/* 819 */     this.fullMapProperties = (FullMapProperties)(new FullMapProperties()).load();
/* 820 */     this.miniMapProperties1 = (MiniMapProperties)(new MiniMapProperties(1)).load();
/* 821 */     this.miniMapProperties2 = (MiniMapProperties)(new MiniMapProperties(2)).load();
/* 822 */     this.topoProperties = (TopoProperties)(new TopoProperties()).load();
/* 823 */     this.webMapProperties = (WebMapProperties)(new WebMapProperties()).load();
/* 824 */     this.waypointProperties = (WaypointProperties)(new WaypointProperties()).load();
/* 825 */     this.renderingProperties = (RenderingProperties)(new RenderingProperties()).load();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCurrentWorldId() {
/* 835 */     return this.currentWorldId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCurrentWorldId(String worldId) {
/* 845 */     synchronized (this) {
/*     */       
/* 847 */       class_310 mc = class_310.method_1551();
/* 848 */       if (!mc.method_1496()) {
/*     */         
/* 850 */         File currentWorldDirectory = FileHandler.getJMWorldDirForWorldId(mc, this.currentWorldId);
/* 851 */         File newWorldDirectory = FileHandler.getJMWorldDir(mc, worldId);
/*     */         
/* 853 */         boolean worldIdUnchanged = Constants.safeEqual(worldId, this.currentWorldId);
/* 854 */         boolean directoryUnchanged = (currentWorldDirectory != null && newWorldDirectory != null && currentWorldDirectory.getPath().equals(newWorldDirectory.getPath()));
/*     */         
/* 856 */         if (worldIdUnchanged && directoryUnchanged) {
/*     */           
/* 858 */           Journeymap.getLogger().debug("World UID hasn't changed: " + worldId);
/*     */           
/*     */           return;
/*     */         } 
/* 862 */         boolean wasMapping = isMapping().booleanValue();
/* 863 */         if (wasMapping)
/*     */         {
/* 865 */           stopMapping();
/*     */         }
/*     */         
/* 868 */         this.currentWorldId = worldId;
/* 869 */         Journeymap.getLogger().info("World UID is set to: " + worldId);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ClientNetworkDispatcher getDispatcher() {
/* 876 */     if (this.dispatcher == null)
/*     */     {
/* 878 */       this.dispatcher = new ClientNetworkDispatcher(Network.getNetworkHandler());
/*     */     }
/* 880 */     return this.dispatcher;
/*     */   }
/*     */ 
/*     */   
/*     */   public ClientPacketHandler getPacketHandler() {
/* 885 */     return this.packetHandler;
/*     */   }
/*     */ 
/*     */   
/*     */   public InternalStateHandler getStateHandler() {
/* 890 */     return this.stateHandler;
/*     */   }
/*     */ 
/*     */   
/*     */   public KeyEvent getKeyEvents() {
/* 895 */     return this.keyEvents;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setKeyEvents(KeyEvent keyEvents) {
/* 900 */     this.keyEvents = keyEvents;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRenderDistance(boolean underground) {
/* 905 */     int gameRenderDistance = Math.max(1, (class_310.method_1551()).field_1690.method_38521() - 1);
/* 906 */     if (getInstance().getStateHandler().isJourneyMapServerConnection()) {
/*     */       
/* 908 */       InternalStateHandler handler = getInstance().getStateHandler();
/* 909 */       int serverDistance = underground ? handler.getMaxCaveRenderDistance() : handler.getMaxSurfaceRenderDistance();
/* 910 */       gameRenderDistance = (serverDistance == 0) ? gameRenderDistance : Math.min(serverDistance, gameRenderDistance);
/*     */     } 
/*     */     
/* 913 */     return gameRenderDistance;
/*     */   }
/*     */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\JourneymapClient.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */