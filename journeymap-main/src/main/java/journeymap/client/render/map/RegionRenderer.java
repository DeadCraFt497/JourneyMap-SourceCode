/*     */ package journeymap.client.render.map;
/*     */ 
/*     */ import java.awt.geom.Point2D;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Stack;
/*     */ import journeymap.api.client.impl.ClientAPI;
/*     */ import journeymap.api.v2.client.display.Context;
/*     */ import journeymap.api.v2.client.display.DisplayType;
/*     */ import journeymap.api.v2.client.display.Displayable;
/*     */ import journeymap.api.v2.client.display.IOverlayListener;
/*     */ import journeymap.api.v2.client.display.PolygonOverlay;
/*     */ import journeymap.api.v2.client.fullscreen.ModPopupMenu;
/*     */ import journeymap.api.v2.client.model.MapPolygon;
/*     */ import journeymap.api.v2.client.model.ShapeProperties;
/*     */ import journeymap.api.v2.client.model.TextProperties;
/*     */ import journeymap.api.v2.client.util.PolygonHelper;
/*     */ import journeymap.api.v2.client.util.UIState;
/*     */ import journeymap.client.io.nbt.RegionLoader;
/*     */ import journeymap.client.model.region.RegionCoord;
/*     */ import journeymap.client.texture.TextureCache;
/*     */ import journeymap.client.ui.fullscreen.Fullscreen;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_310;
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
/*     */ public class RegionRenderer
/*     */ {
/*     */   private static RegionRenderer instance;
/*     */   public static boolean TOGGLED = false;
/*     */   
/*     */   public static void render(boolean toggled) {
/*  45 */     if (instance == null)
/*     */     {
/*  47 */       instance = new RegionRenderer();
/*     */     }
/*  49 */     TOGGLED = toggled;
/*  50 */     if (toggled) {
/*     */       
/*  52 */       ClientAPI.INSTANCE.flagOverlaysForRerender();
/*     */ 
/*     */       
/*  55 */       PolygonOverlay overlay = instance.createTexturedOverlay(instance.getRegions().get(0));
/*  56 */       ClientAPI.INSTANCE.show((Displayable)overlay);
/*     */     
/*     */     }
/*     */     else {
/*     */       
/*  61 */       ClientAPI.INSTANCE.removeAll("journeymap", DisplayType.Polygon);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Stack<RegionCoord> getRegions() {
/*     */     try {
/*  69 */       class_310 minecraft = class_310.method_1551();
/*  70 */       RegionLoader regionLoader = new RegionLoader(minecraft, Fullscreen.state().getMapType(), true);
/*  71 */       return regionLoader.getRegions();
/*     */     }
/*  73 */     catch (Exception e) {
/*     */       
/*  75 */       throw new RuntimeException("Unable to load regions", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected PolygonOverlay createOverlay(RegionCoord rCoord) {
/*  81 */     String groupName = "Region";
/*  82 */     String label = "x:" + rCoord.regionX + ", z:" + rCoord.regionZ;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  89 */     ShapeProperties shapeProps = (new ShapeProperties()).setStrokeWidth(2.0F).setStrokeColor(16711680).setStrokeOpacity(0.7F).setFillOpacity(0.2F);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  97 */     TextProperties textProps = (new TextProperties()).setBackgroundColor(34).setBackgroundOpacity(0.5F).setColor(65280).setOpacity(1.0F).setFontShadow(true);
/*     */ 
/*     */     
/* 100 */     int x = rCoord.getMinChunkX() << 4;
/* 101 */     int y = 70;
/* 102 */     int z = rCoord.getMinChunkZ() << 4;
/* 103 */     int maxX = (rCoord.getMaxChunkX() << 4) + 15;
/* 104 */     int maxZ = (rCoord.getMaxChunkZ() << 4) + 15;
/* 105 */     class_2338 sw = new class_2338(x + 1, y, maxZ + 1);
/* 106 */     class_2338 se = new class_2338(maxX + 1, y, maxZ + 1);
/* 107 */     class_2338 ne = new class_2338(maxX + 1, y, z + 1);
/* 108 */     class_2338 nw = new class_2338(x + 1, y, z + 1);
/* 109 */     MapPolygon regionPolygon = new MapPolygon(new class_2338[] { sw, se, ne, nw });
/*     */ 
/*     */     
/* 112 */     PolygonOverlay overlay = new PolygonOverlay("journeymap", rCoord.dimension, shapeProps, regionPolygon);
/*     */ 
/*     */     
/* 115 */     overlay.setOverlayGroupName(groupName)
/* 116 */       .setOverlayListener(new IOverlayListener(this)
/*     */         {
/*     */           public void onActivate(UIState mapState) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void onDeactivate(UIState mapState) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void onMouseMove(UIState mapState, Point2D.Double mousePosition, class_2338 blockPosition) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void onMouseOut(UIState mapState, Point2D.Double mousePosition, class_2338 blockPosition) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public boolean onMouseClick(UIState mapState, Point2D.Double mousePosition, class_2338 blockPosition, int button, boolean doubleClick) {
/* 145 */             return false;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void onOverlayMenuPopup(UIState mapState, Point2D.Double mousePosition, class_2338 blockPosition, ModPopupMenu modPopupMenu) {}
/* 154 */         }).setTitle("Title")
/* 155 */       .setLabel(label)
/* 156 */       .setTextProperties(textProps)
/* 157 */       .setActiveUIs(new Context.UI[] { Context.UI.Fullscreen, Context.UI.Webmap
/* 158 */         }).setActiveMapTypes(Context.MapType.all());
/*     */     
/* 160 */     return overlay;
/*     */   }
/*     */ 
/*     */   
/*     */   protected PolygonOverlay createTexturedOverlay(RegionCoord rCoord) {
/* 165 */     String displayId = "Region Display" + String.valueOf(rCoord);
/* 166 */     String groupName = "Region";
/* 167 */     String label = "x:" + rCoord.regionX + ", z:" + rCoord.regionZ;
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
/* 180 */     ShapeProperties shapeProps = (new ShapeProperties()).setStrokeWidth(5.0F).setStrokeColor(16711680).setStrokeOpacity(0.7F).setFillOpacity(0.2F).setImageLocation(TextureCache.Logo).setTexturePositionX(-20.0D).setTexturePositionY(8.0D).setTextureScaleX(0.8D).setTextureScaleY(0.4D).setFillColor(65280);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 188 */     TextProperties textProps = (new TextProperties()).setBackgroundColor(34).setBackgroundOpacity(0.5F).setColor(65280).setOpacity(1.0F).setFontShadow(true);
/*     */ 
/*     */     
/* 191 */     int x = rCoord.getMinChunkX() << 4;
/* 192 */     int y = 70;
/* 193 */     int z = rCoord.getMinChunkZ() << 4;
/* 194 */     int maxX = (rCoord.getMaxChunkX() << 4) + 15;
/* 195 */     int maxZ = (rCoord.getMaxChunkZ() << 4) + 15;
/* 196 */     List<class_2338> blockPosList = new ArrayList<>();
/* 197 */     blockPosList.add(new class_2338(384, 256, 128));
/*     */     
/* 199 */     blockPosList.add(new class_2338(144, 256, 304));
/* 200 */     blockPosList.add(new class_2338(144, 256, 224));
/* 201 */     blockPosList.add(new class_2338(160, 256, 224));
/* 202 */     blockPosList.add(new class_2338(160, 256, 128));
/* 203 */     blockPosList.add(new class_2338(272, 256, 128));
/* 204 */     blockPosList.add(new class_2338(272, 256, 144));
/* 205 */     blockPosList.add(new class_2338(288, 256, 144));
/* 206 */     blockPosList.add(new class_2338(288, 256, 128));
/* 207 */     blockPosList.add(new class_2338(368, 256, 128));
/*     */     
/* 209 */     MapPolygon hole = PolygonHelper.createChunkPolygonForWorldCoords(284, 70, 197);
/*     */ 
/*     */     
/* 212 */     PolygonOverlay overlay = new PolygonOverlay("journeymap", rCoord.dimension, shapeProps, new MapPolygon(blockPosList), Collections.singletonList(hole));
/*     */ 
/*     */     
/* 215 */     overlay.setOverlayGroupName(groupName)
/* 216 */       .setOverlayListener(new IOverlayListener(this)
/*     */         {
/*     */           public void onActivate(UIState mapState) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void onDeactivate(UIState mapState) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void onMouseMove(UIState mapState, Point2D.Double mousePosition, class_2338 blockPosition) {
/* 233 */             System.out.println(blockPosition);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public void onMouseOut(UIState mapState, Point2D.Double mousePosition, class_2338 blockPosition) {
/* 239 */             System.out.println(mousePosition);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public boolean onMouseClick(UIState mapState, Point2D.Double mousePosition, class_2338 blockPosition, int button, boolean doubleClick) {
/* 245 */             System.out.println("click");
/* 246 */             return false;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void onOverlayMenuPopup(UIState mapState, Point2D.Double mousePosition, class_2338 blockPosition, ModPopupMenu modPopupMenu) {}
/* 255 */         }).setTitle("Test Title")
/* 256 */       .setLabel(label)
/* 257 */       .setTextProperties(textProps)
/* 258 */       .setActiveUIs(new Context.UI[] { Context.UI.Fullscreen, Context.UI.Minimap, Context.UI.Webmap
/* 259 */         }).setActiveMapTypes(Context.MapType.all());
/*     */     
/* 261 */     return overlay;
/*     */   }
/*     */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\render\map\RegionRenderer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */