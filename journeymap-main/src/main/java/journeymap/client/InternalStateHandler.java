/*     */ package journeymap.client;
/*     */ 
/*     */ import journeymap.api.v2.common.waypoint.WaypointGroup;
/*     */ import journeymap.client.feature.FeatureManager;
/*     */ import journeymap.client.log.ChatLog;
/*     */ import journeymap.client.task.multi.RenderSpec;
/*     */ import journeymap.common.Journeymap;
/*     */ import journeymap.common.network.model.ClientState;
/*     */ import journeymap.common.properties.GlobalProperties;
/*     */ import journeymap.common.properties.ServerOption;
/*     */ import journeymap.common.waypoint.WaypointGroupImpl;
/*     */ import journeymap.common.waypoint.WaypointGroupStore;
/*     */ import journeymap.common.waypoint.WaypointStore;
/*     */ import net.minecraft.class_310;
/*     */ 
/*     */ public class InternalStateHandler {
/*     */   private boolean journeyMapServerConnection = false;
/*     */   private boolean moddedServerConnection = false;
/*     */   private boolean expandedRadarEnabled = false;
/*     */   private boolean teleportEnabled = false;
/*     */   private boolean serverAdmin = false;
/*     */   private boolean useServerFullscreenBiomes = false;
/*     */   private boolean allowDeathPoints = true;
/*     */   private boolean showInGameBeacons = true;
/*     */   private boolean waypointsAllowed = true;
/*     */   private boolean multiplayerOptionsAllowed = true;
/*     */   private boolean readOnlyServerAdmin = true;
/*  28 */   private int maxSurfaceRenderDistance = 0;
/*  29 */   private int maxCaveRenderDistance = 0;
/*     */   
/*     */   private boolean rightClickTeleport = true;
/*     */   
/*     */   private boolean playerRadarNamesEnabled = true;
/*  34 */   private int radarLateralDistance = 512;
/*  35 */   private int radarVerticalDistance = 320;
/*  36 */   private int maxAnimalsData = 128;
/*  37 */   private int maxAmbientCreaturesData = 128;
/*  38 */   private int maxMobsData = 128;
/*  39 */   private int maxPlayersData = 128;
/*  40 */   private int maxVillagersData = 128;
/*     */ 
/*     */   
/*     */   private boolean minimapEnabledApi = true;
/*     */ 
/*     */   
/*     */   private boolean minimapEnabled = true;
/*     */ 
/*     */   
/*     */   private boolean hideCoordinates = false;
/*     */ 
/*     */   
/*     */   public void setStates(ClientState state) {
/*  53 */     GlobalProperties prop = (GlobalProperties)(new GlobalProperties()).loadForClient(state.getPayload(), false);
/*     */     
/*  55 */     if (!prop.journeymapEnabled.get().booleanValue()) {
/*     */       
/*  57 */       JourneymapClient.getInstance().disable();
/*  58 */       Journeymap.getLogger().info("Journeymap is Disabled by the server.");
/*  59 */       ChatLog.announceI18N("jm.common.disabled_by_server", new Object[0]);
/*     */     }
/*  61 */     else if (!JourneymapClient.getInstance().isInitialized().booleanValue()) {
/*     */       
/*  63 */       Journeymap.getLogger().info("Journeymap is enabled by the server.");
/*  64 */       ChatLog.announceI18N("jm.common.enabled_by_server", new Object[0]);
/*  65 */       JourneymapClient.getInstance().enable();
/*     */     } 
/*  67 */     setModdedServerConnection(state.hasServerMod());
/*  68 */     setJourneyMapServerConnection(state.hasServerMod());
/*  69 */     setTeleportEnabled(prop.teleportEnabled.get().booleanValue());
/*  70 */     setExpandedRadarEnabled(((ServerOption)prop.worldPlayerRadar.get()).enabled());
/*  71 */     setServerAdmin(state.isServerAdmin());
/*  72 */     setAllowDeathPoints(prop.allowDeathPoints.get().booleanValue());
/*  73 */     setShowInGameBeacons(prop.showInGameBeacons.get().booleanValue());
/*  74 */     setWaypointsAllowed(prop.allowWaypoints.get().booleanValue());
/*  75 */     setReadOnlyServerAdmin(prop.viewOnlyServerProperties.get().booleanValue());
/*  76 */     setSurfaceMaxRenderDistance(prop.surfaceRenderRange.get().intValue());
/*  77 */     setCaveMaxRenderDistance(prop.caveRenderRange.get().intValue());
/*  78 */     setMultiplayerOptionsAllowed(((ServerOption)prop.allowMultiplayerSettings.get()).enabled());
/*  79 */     setAllowRightClickTeleport(prop.allowRightClickTeleport.get().booleanValue());
/*  80 */     setRadarLateralDistance(prop.radarLateralDistance.get().intValue());
/*  81 */     setRadarVerticalDistance(prop.radarVerticalDistance.get().intValue());
/*  82 */     setMaxAnimalsData(prop.maxAnimalsData.get().intValue());
/*  83 */     setMaxAmbientCreaturesData(prop.maxAmbientCreaturesData.get().intValue());
/*  84 */     setMaxMobsData(prop.maxMobsData.get().intValue());
/*  85 */     setMaxPlayersData(prop.maxPlayersData.get().intValue());
/*  86 */     setMaxVillagersData(prop.maxVillagersData.get().intValue());
/*  87 */     setPlayerRadarNamesEnabled(prop.playerRadarNamesEnabled.get().booleanValue());
/*  88 */     setMinimapEnabled(prop.minimapEnabled.get().booleanValue());
/*  89 */     setHideCoordinates(prop.hideCoordinates.get().booleanValue());
/*  90 */     FeatureManager.getInstance().updateDimensionFeatures(prop);
/*     */   }
/*     */ 
/*     */   
/*     */   public void reset() {
/*  95 */     this.minimapEnabled = true;
/*  96 */     this.hideCoordinates = false;
/*  97 */     this.journeyMapServerConnection = false;
/*  98 */     this.moddedServerConnection = false;
/*  99 */     this.expandedRadarEnabled = false;
/* 100 */     this.teleportEnabled = false;
/* 101 */     this.rightClickTeleport = true;
/* 102 */     this.serverAdmin = false;
/* 103 */     this.useServerFullscreenBiomes = false;
/* 104 */     this.allowDeathPoints = true;
/* 105 */     this.showInGameBeacons = true;
/* 106 */     this.waypointsAllowed = true;
/* 107 */     this.readOnlyServerAdmin = false;
/* 108 */     this.multiplayerOptionsAllowed = true;
/* 109 */     this.playerRadarNamesEnabled = true;
/* 110 */     this.maxCaveRenderDistance = 0;
/* 111 */     this.maxSurfaceRenderDistance = 0;
/* 112 */     this.radarLateralDistance = 512;
/* 113 */     this.radarVerticalDistance = 320;
/* 114 */     this.maxAnimalsData = 128;
/* 115 */     this.maxAmbientCreaturesData = 128;
/* 116 */     this.maxMobsData = 128;
/* 117 */     this.maxPlayersData = 128;
/* 118 */     this.maxVillagersData = 128;
/*     */     
/* 120 */     FeatureManager.getInstance().reset();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAllowDeathPoints() {
/* 125 */     return this.allowDeathPoints;
/*     */   }
/*     */ 
/*     */   
/*     */   private void setAllowDeathPoints(boolean allowDeathPoints) {
/* 130 */     this.allowDeathPoints = allowDeathPoints;
/* 131 */     if (!allowDeathPoints) {
/*     */       
/* 133 */       WaypointGroupImpl deathGroup = WaypointGroupStore.getInstance().get(WaypointGroupStore.DEATH.getGuid());
/* 134 */       if (deathGroup != null && !deathGroup.getWaypointIds().isEmpty()) {
/*     */         
/* 136 */         Journeymap.getLogger().info("Death Points disabled by the server, deleting existing.");
/* 137 */         WaypointStore.getInstance().remove((WaypointGroup)deathGroup);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isModdedServerConnection() {
/* 144 */     return this.moddedServerConnection;
/*     */   }
/*     */ 
/*     */   
/*     */   private void setModdedServerConnection(boolean moddedServerConnection) {
/* 149 */     this.moddedServerConnection = moddedServerConnection;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isJourneyMapServerConnection() {
/* 154 */     return this.journeyMapServerConnection;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setJourneyMapServerConnection(boolean journeyMapServerConnection) {
/* 159 */     Journeymap.getLogger().info("Connection initiated with Journeymap Server: {}", Boolean.valueOf(journeyMapServerConnection));
/* 160 */     this.journeyMapServerConnection = journeyMapServerConnection;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isExpandedRadarEnabled() {
/* 165 */     return this.expandedRadarEnabled;
/*     */   }
/*     */ 
/*     */   
/*     */   private void setExpandedRadarEnabled(boolean expandedRadarEnabled) {
/* 170 */     if (class_310.method_1551().method_1496() && class_310.method_1551().method_1558() != null && class_310.method_1551().method_1558().method_2994()) {
/*     */       
/* 172 */       this.expandedRadarEnabled = false;
/*     */       return;
/*     */     } 
/* 175 */     Journeymap.getLogger().info("Expanded Radar Enabled: {}", Boolean.valueOf(expandedRadarEnabled));
/* 176 */     this.expandedRadarEnabled = expandedRadarEnabled;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTeleportEnabled() {
/* 182 */     return this.teleportEnabled;
/*     */   }
/*     */ 
/*     */   
/*     */   private void setTeleportEnabled(boolean teleportEnabled) {
/* 187 */     Journeymap.getLogger().info("Teleport Enabled: {}", Boolean.valueOf(teleportEnabled));
/* 188 */     this.teleportEnabled = teleportEnabled;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isServerAdmin() {
/* 193 */     return this.serverAdmin;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canServerAdmin() {
/* 198 */     return (this.serverAdmin || class_310.method_1551().method_1496());
/*     */   }
/*     */ 
/*     */   
/*     */   private void setServerAdmin(boolean serverAdmin) {
/* 203 */     Journeymap.getLogger().info("Server Admin Enabled: {}", Boolean.valueOf(serverAdmin));
/* 204 */     this.serverAdmin = serverAdmin;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean useServerFullscreenBiomes() {
/* 209 */     return this.useServerFullscreenBiomes;
/*     */   }
/*     */ 
/*     */   
/*     */   private void setUseServerFullscreenBiomes(boolean useServerFullscreenBiomes) {
/* 214 */     Journeymap.getLogger().info("Server fullscreen biomes: {}", Boolean.valueOf(useServerFullscreenBiomes));
/* 215 */     this.useServerFullscreenBiomes = useServerFullscreenBiomes;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canShowInGameBeacons() {
/* 220 */     return this.showInGameBeacons;
/*     */   }
/*     */ 
/*     */   
/*     */   private void setShowInGameBeacons(boolean showInGameBeacons) {
/* 225 */     Journeymap.getLogger().info("Server set show in-game beacons: {}", Boolean.valueOf(showInGameBeacons));
/* 226 */     this.showInGameBeacons = showInGameBeacons;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isWaypointsAllowed() {
/* 231 */     return this.waypointsAllowed;
/*     */   }
/*     */ 
/*     */   
/*     */   private void setWaypointsAllowed(boolean waypointsAllowed) {
/* 236 */     Journeymap.getLogger().info("Server set waypoints allowed: {}", Boolean.valueOf(waypointsAllowed));
/* 237 */     this.waypointsAllowed = waypointsAllowed;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isReadOnlyServerAdmin() {
/* 242 */     return this.readOnlyServerAdmin;
/*     */   }
/*     */ 
/*     */   
/*     */   private void setReadOnlyServerAdmin(boolean readOnlyServerAdmin) {
/* 247 */     Journeymap.getLogger().info("Server set Server Admin read only mode: {}", Boolean.valueOf(readOnlyServerAdmin));
/* 248 */     this.readOnlyServerAdmin = readOnlyServerAdmin;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxSurfaceRenderDistance() {
/* 253 */     return this.maxSurfaceRenderDistance;
/*     */   }
/*     */ 
/*     */   
/*     */   private void setSurfaceMaxRenderDistance(int maxSurfaceRenderDistance) {
/* 258 */     Journeymap.getLogger().info("Server set maxSurfaceRenderDistance to: {}", Integer.valueOf(maxSurfaceRenderDistance));
/* 259 */     this.maxSurfaceRenderDistance = maxSurfaceRenderDistance;
/* 260 */     RenderSpec.resetRenderSpecs();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxCaveRenderDistance() {
/* 265 */     return this.maxCaveRenderDistance;
/*     */   }
/*     */ 
/*     */   
/*     */   private void setCaveMaxRenderDistance(int maxCaveRenderDistance) {
/* 270 */     Journeymap.getLogger().info("Server setmaxCaveRenderDistance to: {}", Integer.valueOf(maxCaveRenderDistance));
/* 271 */     this.maxCaveRenderDistance = maxCaveRenderDistance;
/* 272 */     RenderSpec.resetRenderSpecs();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isMultiplayerOptionsAllowed() {
/* 277 */     return this.multiplayerOptionsAllowed;
/*     */   }
/*     */ 
/*     */   
/*     */   private void setMultiplayerOptionsAllowed(boolean multiplayerOptionsAllowed) {
/* 282 */     Journeymap.getLogger().info("Server set allow mutliplayer options: {}", Boolean.valueOf(multiplayerOptionsAllowed));
/* 283 */     this.multiplayerOptionsAllowed = multiplayerOptionsAllowed;
/*     */   }
/*     */ 
/*     */   
/*     */   private void setAllowRightClickTeleport(boolean rightClickTeleport) {
/* 288 */     Journeymap.getLogger().info("Server set allow rightClickTeleport options: {}", Boolean.valueOf(rightClickTeleport));
/* 289 */     this.rightClickTeleport = rightClickTeleport;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAllowRightClickTeleport() {
/* 294 */     return this.rightClickTeleport;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRadarLateralDistance() {
/* 299 */     return this.radarLateralDistance;
/*     */   }
/*     */ 
/*     */   
/*     */   private void setRadarLateralDistance(int radarLateralDistance) {
/* 304 */     Journeymap.getLogger().info("Server set allow radarLateralDistance options: {}", Integer.valueOf(radarLateralDistance));
/* 305 */     this.radarLateralDistance = radarLateralDistance;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRadarVerticalDistance() {
/* 310 */     return this.radarVerticalDistance;
/*     */   }
/*     */ 
/*     */   
/*     */   private void setRadarVerticalDistance(int radarVerticalDistance) {
/* 315 */     Journeymap.getLogger().info("Server set allow radarVerticalDistance options: {}", Integer.valueOf(radarVerticalDistance));
/* 316 */     this.radarVerticalDistance = radarVerticalDistance;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxAnimalsData() {
/* 321 */     return this.maxAnimalsData;
/*     */   }
/*     */ 
/*     */   
/*     */   private void setMaxAnimalsData(int maxAnimalsData) {
/* 326 */     Journeymap.getLogger().info("Server set allow maxAnimalsData options: {}", Integer.valueOf(maxAnimalsData));
/* 327 */     this.maxAnimalsData = maxAnimalsData;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxAmbientCreaturesData() {
/* 332 */     return this.maxAmbientCreaturesData;
/*     */   }
/*     */ 
/*     */   
/*     */   private void setMaxAmbientCreaturesData(int maxAmbientCreaturesData) {
/* 337 */     Journeymap.getLogger().info("Server set allow maxAmbientCreaturesData options: {}", Integer.valueOf(maxAmbientCreaturesData));
/* 338 */     this.maxAmbientCreaturesData = maxAmbientCreaturesData;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxMobsData() {
/* 343 */     return this.maxMobsData;
/*     */   }
/*     */ 
/*     */   
/*     */   private void setMaxMobsData(int maxMobsData) {
/* 348 */     Journeymap.getLogger().info("Server set allow maxMobsData options: {}", Integer.valueOf(maxMobsData));
/* 349 */     this.maxMobsData = maxMobsData;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxPlayersData() {
/* 354 */     return this.maxPlayersData;
/*     */   }
/*     */ 
/*     */   
/*     */   private void setMaxPlayersData(int maxPlayersData) {
/* 359 */     Journeymap.getLogger().info("Server set allow maxPlayersData options: {}", Integer.valueOf(maxPlayersData));
/* 360 */     this.maxPlayersData = maxPlayersData;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxVillagersData() {
/* 365 */     return this.maxVillagersData;
/*     */   }
/*     */ 
/*     */   
/*     */   private void setMaxVillagersData(int maxVillagersData) {
/* 370 */     Journeymap.getLogger().info("Server set allow maxVillagersData options: {}", Integer.valueOf(maxVillagersData));
/* 371 */     this.maxVillagersData = maxVillagersData;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPlayerRadarNamesEnabled() {
/* 376 */     return this.playerRadarNamesEnabled;
/*     */   }
/*     */ 
/*     */   
/*     */   private void setPlayerRadarNamesEnabled(boolean playerRadarNamesEnabled) {
/* 381 */     Journeymap.getLogger().info("Server set allow playerRadarNamesEnabled options: {}", Boolean.valueOf(playerRadarNamesEnabled));
/* 382 */     this.playerRadarNamesEnabled = playerRadarNamesEnabled;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isMinimapEnabledApi() {
/* 387 */     return this.minimapEnabledApi;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMinimapEnabledApi(boolean minimapEnabledApi) {
/* 392 */     this.minimapEnabledApi = minimapEnabledApi;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isMinimapEnabled() {
/* 397 */     return (this.minimapEnabled && this.minimapEnabledApi);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMinimapEnabled(boolean minimapEnabled) {
/* 402 */     Journeymap.getLogger().info("Server set allow minimapEnabled options: {}", Boolean.valueOf(minimapEnabled));
/* 403 */     this.minimapEnabled = minimapEnabled;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isHideCoordinates() {
/* 408 */     return this.hideCoordinates;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setHideCoordinates(boolean hideCoordinates) {
/* 413 */     Journeymap.getLogger().info("Server set allow hideCoordinates options: {}", Boolean.valueOf(hideCoordinates));
/* 414 */     this.hideCoordinates = hideCoordinates;
/*     */   }
/*     */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\InternalStateHandler.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */