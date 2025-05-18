/*     */ package journeymap.api.client.impl;
/*     */ 
/*     */ import com.google.common.base.MoreObjects;
/*     */ import com.google.common.base.Objects;
/*     */ import com.google.common.collect.HashBasedTable;
/*     */ import com.google.common.collect.Table;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.annotation.Nullable;
/*     */ import javax.annotation.ParametersAreNonnullByDefault;
/*     */ import journeymap.api.v2.client.IClientPlugin;
/*     */ import journeymap.api.v2.client.display.DisplayType;
/*     */ import journeymap.api.v2.client.display.Displayable;
/*     */ import journeymap.api.v2.client.display.ImageOverlay;
/*     */ import journeymap.api.v2.client.display.MarkerOverlay;
/*     */ import journeymap.api.v2.client.display.Overlay;
/*     */ import journeymap.api.v2.client.display.PolygonOverlay;
/*     */ import journeymap.api.v2.client.model.ShapeProperties;
/*     */ import journeymap.api.v2.client.util.UIState;
/*     */ import journeymap.api.v2.common.waypoint.Waypoint;
/*     */ import journeymap.client.data.DataCache;
/*     */ import journeymap.client.render.draw.DrawImageStep;
/*     */ import journeymap.client.render.draw.DrawMarkerStep;
/*     */ import journeymap.client.render.draw.DrawPolygonStep;
/*     */ import journeymap.client.render.draw.OverlayDrawStep;
/*     */ import journeymap.client.texture.ImageUtil;
/*     */ import journeymap.client.texture.TextureAccess;
/*     */ import journeymap.client.texture.TextureCache;
/*     */ import journeymap.client.waypoint.ClientWaypointImpl;
/*     */ import journeymap.common.CommonConstants;
/*     */ import journeymap.common.Journeymap;
/*     */ import journeymap.common.accessors.TextureManagerAccess;
/*     */ import journeymap.common.helper.DimensionHelper;
/*     */ import journeymap.common.log.LogFormatter;
/*     */ import journeymap.common.waypoint.WaypointIcon;
/*     */ import journeymap.common.waypoint.WaypointImpl;
/*     */ import journeymap.common.waypoint.WaypointStore;
/*     */ import net.minecraft.class_1011;
/*     */ import net.minecraft.class_1043;
/*     */ import net.minecraft.class_1044;
/*     */ import net.minecraft.class_1060;
/*     */ import net.minecraft.class_1937;
/*     */ import net.minecraft.class_2960;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_5321;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @ParametersAreNonnullByDefault
/*     */ class PluginWrapper
/*     */ {
/*     */   private final IClientPlugin plugin;
/*     */   private final String modId;
/*  63 */   private final HashMap<String, HashBasedTable<String, Overlay, OverlayDrawStep>> dimensionOverlays = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PluginWrapper(IClientPlugin plugin) {
/*  74 */     this.modId = plugin.getModId();
/*  75 */     this.plugin = plugin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HashBasedTable<String, Overlay, OverlayDrawStep> getOverlays(class_5321<class_1937> dimension) {
/*  86 */     String dimName = DimensionHelper.getDimKeyName(dimension);
/*  87 */     HashBasedTable<String, Overlay, OverlayDrawStep> table = this.dimensionOverlays.get(dimName);
/*  88 */     if (table == null) {
/*     */       
/*  90 */       table = HashBasedTable.create();
/*  91 */       this.dimensionOverlays.put(dimName, table);
/*     */     } 
/*  93 */     return table;
/*     */   } public void show(Displayable displayable) throws Exception {
/*     */     PolygonOverlay polygon;
/*     */     DrawPolygonStep polygonStep;
/*     */     ShapeProperties prop;
/*     */     MarkerOverlay marker;
/*     */     DrawMarkerStep markerStep;
/*     */     ImageOverlay imageOverlay;
/*     */     DrawImageStep imageStep;
/* 102 */     String displayId = displayable.getId();
/* 103 */     switch (displayable.getDisplayType()) {
/*     */       
/*     */       case Polygon:
/* 106 */         polygon = (PolygonOverlay)displayable;
/* 107 */         polygonStep = DataCache.INSTANCE.getDrawPolygonStep(polygon);
/* 108 */         prop = polygon.getShapeProperties();
/* 109 */         if (prop.getImage() != null || prop.getImageLocation() != null)
/*     */         {
/* 111 */           polygonStep.setTextureResource(getPolygonImageResource(polygon));
/*     */         }
/* 113 */         polygonStep.setEnabled(true);
/* 114 */         getOverlays(polygon.getDimension()).put(displayId, polygon, polygonStep);
/*     */         break;
/*     */       case Marker:
/* 117 */         marker = (MarkerOverlay)displayable;
/* 118 */         markerStep = DataCache.INSTANCE.getDrawMakerStep(marker);
/* 119 */         markerStep.setEnabled(true);
/* 120 */         getOverlays(marker.getDimension()).put(displayId, marker, markerStep);
/*     */         break;
/*     */       case Image:
/* 123 */         imageOverlay = (ImageOverlay)displayable;
/* 124 */         imageStep = DataCache.INSTANCE.getDrawImageStep(imageOverlay);
/* 125 */         imageStep.setEnabled(true);
/* 126 */         getOverlays(imageOverlay.getDimension()).put(displayId, imageOverlay, imageStep);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Waypoint getWaypoint(String guid) {
/* 136 */     ClientWaypointImpl waypoint = WaypointStore.getInstance().get(guid);
/* 137 */     if (waypoint != null && this.modId.equals(waypoint.getModId()))
/*     */     {
/* 139 */       return (Waypoint)waypoint;
/*     */     }
/* 141 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Waypoint> getWaypoints() {
/* 146 */     return (List<Waypoint>)WaypointStore.getInstance().getAll().stream()
/* 147 */       .filter(waypoint -> this.modId.equals(waypoint.getModId())).collect(Collectors.toList());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private class_2960 convertToFakeIcon(Waypoint waypoint) {
/* 153 */     WaypointIcon image = ((WaypointImpl)waypoint).getIcon();
/* 154 */     class_2960 resourceLocation = image.getResourceLocation();
/* 155 */     if (resourceLocation != null) {
/*     */       
/* 157 */       class_1060 manager = class_310.method_1551().method_1531();
/* 158 */       class_2960 fakeLocation = class_2960.method_60655("fake", resourceLocation.method_12832());
/* 159 */       if (((TextureManagerAccess)manager).journeymap$getTex(fakeLocation) == null) {
/*     */         
/* 161 */         class_1011 nativeImage = TextureCache.resolveImage(resourceLocation);
/*     */ 
/*     */         
/* 164 */         try { class_1011 img = ImageUtil.getScaledImage(4.0F, nativeImage, false);
/* 165 */           class_1043 scaledTexture = new class_1043(null, img);
/* 166 */           manager.method_4616(fakeLocation, (class_1044)scaledTexture);
/* 167 */           ((TextureAccess)scaledTexture).journeymap$setDisplayHeight(image.getTextureHeight().intValue());
/* 168 */           ((TextureAccess)scaledTexture).journeymap$setDisplayWidth(image.getTextureWidth().intValue());
/* 169 */           TextureCache.modTextureMap.put(resourceLocation, fakeLocation);
/* 170 */           if (nativeImage != null) nativeImage.close();  } catch (Throwable throwable) { if (nativeImage != null)
/*     */             try { nativeImage.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }
/*     */       
/*     */       } 
/* 174 */     }  return resourceLocation;
/*     */   }
/*     */ 
/*     */   
/*     */   private class_2960 getPolygonImageResource(PolygonOverlay polygon) {
/* 179 */     ShapeProperties prop = polygon.getShapeProperties();
/* 180 */     class_1011 image = prop.getImage();
/* 181 */     class_2960 resourceLocation = prop.getImageLocation();
/* 182 */     if (image != null) {
/*     */       
/* 184 */       resourceLocation = class_2960.method_60655("fake", CommonConstants.getSafeString(polygon.getGuid(), "-").toLowerCase(Locale.ROOT));
/* 185 */       class_1011 clonedImage = new class_1011(image.method_4307(), image.method_4323(), false);
/* 186 */       clonedImage.method_4317(image);
/* 187 */       class_1043 texture = new class_1043(null, clonedImage);
/* 188 */       class_310.method_1551().method_1531().method_4616(resourceLocation, (class_1044)texture);
/*     */     }
/* 190 */     else if (resourceLocation != null) {
/*     */       
/* 192 */       class_1060 manager = class_310.method_1551().method_1531();
/* 193 */       class_2960 oldLocation = resourceLocation;
/* 194 */       resourceLocation = class_2960.method_60655("fake", oldLocation.method_12832());
/* 195 */       if (((TextureManagerAccess)manager).journeymap$getTex(resourceLocation) == null) {
/*     */         
/* 197 */         class_1011 nativeImage = TextureCache.resolveImage(oldLocation);
/*     */         
/* 199 */         try { class_1011 clonedImage = new class_1011(nativeImage.method_4307(), nativeImage.method_4323(), false);
/* 200 */           clonedImage.method_4317(nativeImage);
/* 201 */           class_1043 dynamicTexture = new class_1043(null, clonedImage);
/* 202 */           manager.method_4616(resourceLocation, (class_1044)dynamicTexture);
/* 203 */           if (nativeImage != null) nativeImage.close();  } catch (Throwable throwable) { if (nativeImage != null)
/*     */             try { nativeImage.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }
/*     */       
/*     */       } 
/* 207 */     }  return resourceLocation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove(Displayable displayable) {
/* 215 */     String displayId = displayable.getId();
/*     */     
/*     */     try {
/* 218 */       Overlay overlay = (Overlay)displayable;
/* 219 */       OverlayDrawStep drawStep = (OverlayDrawStep)getOverlays(overlay.getDimension()).remove(displayId, displayable);
/* 220 */       if (drawStep != null)
/*     */       {
/* 222 */         drawStep.setEnabled(false);
/*     */       }
/*     */     }
/* 225 */     catch (Throwable t) {
/*     */       
/* 227 */       Journeymap.getLogger().error("Error removing DrawMarkerStep: {}", LogFormatter.toString(t), t);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove(Waypoint wp) {
/* 238 */     ClientWaypointImpl waypoint = WaypointStore.getInstance().get(wp.getGuid());
/* 239 */     if (waypoint != null && wp.getModId().equals(waypoint.getModId()))
/*     */     {
/* 241 */       WaypointStore.getInstance().remove(waypoint, true);
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
/*     */   public void add(Waypoint waypoint) {
/* 253 */     if (((WaypointImpl)waypoint).getIcon() != null && !"journeymap".equals(((WaypointImpl)waypoint).getIcon().getResourceLocation().method_12836())) {
/*     */ 
/*     */       
/* 256 */       WaypointIcon icon = new WaypointIcon(convertToFakeIcon(waypoint));
/* 257 */       icon.setColor(((WaypointImpl)waypoint).getIcon().getColor());
/* 258 */       ((WaypointImpl)waypoint).setIcon(icon);
/*     */     } 
/* 260 */     WaypointStore.getInstance().save((ClientWaypointImpl)waypoint, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeAll(DisplayType displayType) {
/* 269 */     for (HashBasedTable<String, Overlay, OverlayDrawStep> overlays : this.dimensionOverlays.values()) {
/*     */       
/* 271 */       List<Displayable> list = new ArrayList<>(overlays.columnKeySet());
/* 272 */       for (Displayable displayable : list) {
/*     */         
/* 274 */         if (displayable.getDisplayType() == displayType) {
/*     */           
/* 276 */           remove(displayable);
/* 277 */           if (displayable instanceof PolygonOverlay) { PolygonOverlay polygonOverlay = (PolygonOverlay)displayable;
/*     */             
/* 279 */             DataCache.INSTANCE.invalidatePolygon(polygonOverlay); }
/*     */         
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeAllWaypoints() {
/* 292 */     List<ClientWaypointImpl> waypoints = WaypointStore.getInstance().getAll(this.modId);
/* 293 */     for (Waypoint waypoint : waypoints)
/*     */     {
/* 295 */       remove(waypoint);
/*     */     }
/*     */     
/* 298 */     if (!this.dimensionOverlays.isEmpty())
/*     */     {
/* 300 */       this.dimensionOverlays.clear();
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
/*     */   public boolean exists(Displayable displayable) {
/* 312 */     String displayId = displayable.getId();
/* 313 */     if (displayable instanceof Overlay) {
/*     */       
/* 315 */       class_5321<class_1937> dimension = ((Overlay)displayable).getDimension();
/* 316 */       return getOverlays(dimension).containsRow(displayId);
/*     */     } 
/* 318 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getDrawSteps(List<OverlayDrawStep> list, UIState uiState) {
/* 326 */     HashBasedTable<String, Overlay, OverlayDrawStep> table = getOverlays(uiState.dimension);
/* 327 */     for (Table.Cell<String, Overlay, OverlayDrawStep> cell : (Iterable<Table.Cell<String, Overlay, OverlayDrawStep>>)table.cellSet()) {
/*     */       
/* 329 */       if (((Overlay)cell.getColumnKey()).isActiveIn(uiState))
/*     */       {
/* 331 */         list.add((OverlayDrawStep)cell.getValue());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*     */     PluginWrapper that;
/* 339 */     if (this == o)
/*     */     {
/* 341 */       return true;
/*     */     }
/* 343 */     if (o instanceof PluginWrapper) { that = (PluginWrapper)o; }
/*     */     else
/* 345 */     { return false; }
/*     */     
/* 347 */     return Objects.equal(this.modId, that.modId);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 353 */     return Objects.hashCode(new Object[] { this.modId });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 359 */     return MoreObjects.toStringHelper(this.plugin)
/* 360 */       .add("modId", this.modId)
/* 361 */       .toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\api\client\impl\PluginWrapper.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */