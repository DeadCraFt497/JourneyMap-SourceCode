/*     */ package journeymap.api.plugins;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import journeymap.api.client.impl.ClientAPI;
/*     */ import journeymap.api.v2.client.IClientAPI;
/*     */ import journeymap.api.v2.client.IClientPlugin;
/*     */ import journeymap.api.v2.client.JourneyMapPlugin;
/*     */ import journeymap.api.v2.client.display.DisplayType;
/*     */ import journeymap.api.v2.client.display.Overlay;
/*     */ import journeymap.api.v2.client.display.PolygonOverlay;
/*     */ import journeymap.api.v2.client.event.FullscreenDisplayEvent;
/*     */ import journeymap.api.v2.client.event.MappingEvent;
/*     */ import journeymap.api.v2.client.model.MapPolygon;
/*     */ import journeymap.api.v2.client.model.ShapeProperties;
/*     */ import journeymap.api.v2.common.event.ClientEventRegistry;
/*     */ import journeymap.api.v2.common.event.FullscreenEventRegistry;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_310;
/*     */ 
/*     */ 
/*     */ 
/*     */ @JourneyMapPlugin(apiVersion = "2.0.0")
/*     */ public class LoadedChunkOverlayPlugin
/*     */   implements IClientPlugin
/*     */ {
/*     */   private static final String CHUNK_OVERLAY_ID = "journeymap_chunk_overlay";
/*     */   private IClientAPI api;
/*     */   private static LoadedChunkOverlayPlugin instance;
/*     */   
/*     */   public static LoadedChunkOverlayPlugin getInstance() {
/*  37 */     return instance;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void initialize(IClientAPI api) {
/*  43 */     instance = this;
/*  44 */     this.api = api;
/*  45 */     ClientEventRegistry.MAPPING_EVENT.subscribe(getModId(), this::onMappingEvent);
/*  46 */     FullscreenEventRegistry.ADDON_BUTTON_DISPLAY_EVENT.subscribe(getModId(), this::onAddonButtonEvent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void onAddonButtonEvent(FullscreenDisplayEvent.AddonButtonDisplayEvent addonButtonDisplayEvent) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public String getModId() {
/*  57 */     return "journeymap_chunk_overlay";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void onMappingEvent(MappingEvent mappingEvent) {
/*  63 */     this.api.removeAll(getModId(), DisplayType.Polygon);
/*     */   }
/*     */ 
/*     */   
/*     */   public void displayOverlay(String packet) {
/*  68 */     ArrayList<PolygonOverlay> overlayList = new ArrayList<>();
/*  69 */     JsonObject node = (JsonObject)(new Gson()).fromJson(packet, JsonObject.class);
/*  70 */     if (node.get("full") != null)
/*     */     {
/*  72 */       overlayList.addAll(buildPolygon(node.get("full").getAsJsonArray(), (new ShapeProperties())
/*  73 */             .setFillColor(255)
/*  74 */             .setStrokeWidth(0.0F)
/*  75 */             .setFillOpacity(0.2F)));
/*     */     }
/*  77 */     if (node.get("entity") != null)
/*     */     {
/*  79 */       overlayList.addAll(buildPolygon(node.get("entity").getAsJsonArray(), (new ShapeProperties())
/*  80 */             .setFillColor(16711680)
/*  81 */             .setStrokeWidth(0.0F)
/*  82 */             .setFillOpacity(0.2F)));
/*     */     }
/*  84 */     if (node.get("block") != null)
/*     */     {
/*  86 */       overlayList.addAll(buildPolygon(node.get("block").getAsJsonArray(), (new ShapeProperties())
/*  87 */             .setFillColor(65280)
/*  88 */             .setStrokeWidth(0.0F)
/*  89 */             .setFillOpacity(0.2F)));
/*     */     }
/*  91 */     if (node.get("inaccessible") != null)
/*     */     {
/*  93 */       overlayList.addAll(buildPolygon(node.get("inaccessible").getAsJsonArray(), (new ShapeProperties())
/*  94 */             .setStrokeWidth(0.0F)
/*  95 */             .setFillColor(16777215)
/*  96 */             .setFillOpacity(0.2F)));
/*     */     }
/*     */     
/*  99 */     ArrayList<Overlay> toRemove = new ArrayList<>(ClientAPI.INSTANCE.getOverlays("journeymap_chunk_overlay", (class_310.method_1551()).field_1724.method_5770().method_27983()).columnKeySet());
/*     */     
/* 101 */     Objects.requireNonNull(ClientAPI.INSTANCE); overlayList.forEach(ClientAPI.INSTANCE::show);
/* 102 */     Objects.requireNonNull(ClientAPI.INSTANCE); toRemove.forEach(ClientAPI.INSTANCE::remove);
/*     */   }
/*     */ 
/*     */   
/*     */   private List<PolygonOverlay> buildPolygon(JsonArray node, ShapeProperties prop) {
/* 107 */     ArrayList<PolygonOverlay> overlayList = new ArrayList<>();
/* 108 */     for (JsonElement area : node) {
/*     */       
/* 110 */       JsonObject obj = area.getAsJsonObject();
/* 111 */       JsonObject outer = obj.get("outerArea").getAsJsonObject();
/* 112 */       List<class_2338> points = new ArrayList<>();
/* 113 */       List<MapPolygon> holes = new ArrayList<>();
/* 114 */       for (JsonElement point : outer.get("points").getAsJsonArray())
/*     */       {
/* 116 */         points.add(class_2338.method_10092(point.getAsLong()));
/*     */       }
/*     */       
/* 119 */       if (obj.has("holes")) {
/*     */         
/* 121 */         JsonElement holesnode = obj.get("holes");
/* 122 */         for (JsonElement hole : holesnode.getAsJsonArray()) {
/*     */           
/* 124 */           List<class_2338> holePoints = new ArrayList<>();
/* 125 */           for (JsonElement point : hole.getAsJsonObject().get("points").getAsJsonArray())
/*     */           {
/* 127 */             holePoints.add(class_2338.method_10092(point.getAsLong()));
/*     */           }
/* 129 */           holes.add(new MapPolygon(holePoints));
/*     */         } 
/*     */       } 
/* 132 */       PolygonOverlay polygonOverlay = new PolygonOverlay("journeymap_chunk_overlay", (class_310.method_1551()).field_1724.field_17892.method_27983(), prop, new MapPolygon(points), holes);
/*     */       
/* 134 */       overlayList.add(polygonOverlay);
/*     */     } 
/*     */     
/* 137 */     return overlayList;
/*     */   }
/*     */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\api\plugins\LoadedChunkOverlayPlugin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */