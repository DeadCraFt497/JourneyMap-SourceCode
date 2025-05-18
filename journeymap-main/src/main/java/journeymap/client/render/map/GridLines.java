/*    */ package journeymap.client.render.map;
/*    */ 
/*    */ import java.awt.geom.Point2D;
/*    */ import java.awt.geom.Rectangle2D;
/*    */ import journeymap.client.JourneymapClient;
/*    */ import journeymap.client.cartography.color.RGB;
/*    */ import journeymap.client.model.grid.GridSpec;
/*    */ import journeymap.client.model.region.RegionCoord;
/*    */ import journeymap.client.render.draw.DrawUtil;
/*    */ import net.minecraft.class_1921;
/*    */ import net.minecraft.class_332;
/*    */ import net.minecraft.class_4587;
/*    */ import net.minecraft.class_4588;
/*    */ import net.minecraft.class_4597;
/*    */ import org.joml.Matrix4f;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GridLines
/*    */ {
/*    */   private final MapRenderer mapRenderer;
/*    */   
/*    */   public GridLines(MapRenderer mapRenderer) {
/* 26 */     this.mapRenderer = mapRenderer;
/*    */   }
/*    */ 
/*    */   
/*    */   public void draw(class_332 graphics, class_4597 buffers, RegionCoord centerRegion, Point2D.Double centerPixel, Rectangle2D.Double regionBounds, double pixelOffsetX, double pixelOffsetZ, double offsetX, double offsetZ, int zoom, float alpha, boolean showGrid) {
/* 31 */     if (showGrid) {
/*    */       
/* 33 */       GridSpec gridSpec = (JourneymapClient.getInstance().getCoreProperties()).gridSpecs.getSpec(this.mapRenderer.mapType);
/*    */       
/* 35 */       Point2D.Double distToCornerRegion = new Point2D.Double(centerRegion.regionX - regionBounds.x, centerRegion.regionZ - regionBounds.y);
/* 36 */       Point2D.Double upperLeftRegion = new Point2D.Double(centerPixel.x - ((int)distToCornerRegion.x * zoom) + pixelOffsetX + offsetX, centerPixel.y - ((int)distToCornerRegion.y * zoom) + pixelOffsetZ + offsetZ);
/* 37 */       int regionCols = (int)regionBounds.width;
/* 38 */       int regionRows = (int)regionBounds.height;
/*    */       
/* 40 */       if (zoom >= 512) {
/*    */         
/* 42 */         class_1921 renderType = gridSpec.getRenderType(zoom);
/* 43 */         class_4588 gridLinesBuffer = buffers.getBuffer(renderType);
/* 44 */         DrawUtil.drawQuad(graphics.method_51448(), gridLinesBuffer, gridSpec.getColor().intValue(), alpha * gridSpec.alpha, upperLeftRegion.x, upperLeftRegion.y, (regionCols * zoom), (regionRows * zoom), 0.0D, 0.0D, regionCols, regionRows, 0.0D, false);
/*    */       }
/*    */       else {
/*    */         
/* 48 */         boolean drawChunkLines = (zoom >= 128 && gridSpec.style.hasChunkLines());
/* 49 */         boolean drawRegionLines = (zoom >= 8 && gridSpec.style.hasRegionLines());
/*    */         
/* 51 */         int chunkCols = regionCols * 32;
/* 52 */         int chunkRows = regionRows * 32;
/*    */         
/* 54 */         class_1921 renderType = gridSpec.getRenderType(zoom);
/* 55 */         class_4588 gridLinesBuffer = buffers.getBuffer(renderType);
/* 56 */         class_4587.class_4665 entry = graphics.method_51448().method_23760();
/* 57 */         Matrix4f matrix4f = entry.method_23761();
/*    */         
/* 59 */         float gridAlpha = gridSpec.alpha * alpha;
/*    */         
/* 61 */         if (drawChunkLines) {
/*    */           
/* 63 */           int chunkColor = RGB.toArgb(gridSpec.getColor().intValue(), (zoom == 256) ? (gridAlpha * 0.6666F) : (gridAlpha * 0.3333F));
/* 64 */           for (int c = 0; c < chunkCols; c++) {
/*    */             
/* 66 */             boolean regionLine = (c % 32 == 0);
/* 67 */             if (!drawRegionLines || !regionLine) {
/*    */               
/* 69 */               gridLinesBuffer.method_22918(matrix4f, (float)(upperLeftRegion.x + (c * (zoom >> 5))), (float)upperLeftRegion.y, 0.0F).method_39415(chunkColor);
/* 70 */               gridLinesBuffer.method_22918(matrix4f, (float)(upperLeftRegion.x + (c * (zoom >> 5))), (float)upperLeftRegion.y + (regionRows * zoom), 0.0F).method_39415(chunkColor);
/*    */             } 
/*    */           } 
/*    */           
/* 74 */           for (int r = 0; r < chunkRows; r++) {
/*    */             
/* 76 */             boolean regionLine = (r % 32 == 0);
/* 77 */             if (!drawRegionLines || !regionLine) {
/*    */               
/* 79 */               gridLinesBuffer.method_22918(matrix4f, (float)upperLeftRegion.x, (float)(upperLeftRegion.y + (r * (zoom >> 5))), 0.0F).method_39415(chunkColor);
/* 80 */               gridLinesBuffer.method_22918(matrix4f, (float)upperLeftRegion.x + (regionCols * zoom), (float)(upperLeftRegion.y + (r * (zoom >> 5))), 0.0F).method_39415(chunkColor);
/*    */             } 
/*    */           } 
/*    */         } 
/*    */         
/* 85 */         if (drawRegionLines) {
/*    */           
/* 87 */           int baseColor = gridSpec.style.hasChunkLines() ? RGB.tint(gridSpec.getColor().intValue(), 16711680) : gridSpec.getColor().intValue();
/* 88 */           int regionColor = RGB.toArgb(baseColor, (zoom == 16) ? (gridAlpha * 0.6666F) : ((zoom == 8) ? (gridAlpha * 0.3333F) : gridAlpha));
/* 89 */           for (int c = 0; c < regionCols; c++) {
/*    */             
/* 91 */             gridLinesBuffer.method_22918(matrix4f, (float)(upperLeftRegion.x + (c * zoom)), (float)upperLeftRegion.y, 0.0F).method_39415(regionColor);
/* 92 */             gridLinesBuffer.method_22918(matrix4f, (float)(upperLeftRegion.x + (c * zoom)), (float)upperLeftRegion.y + (regionRows * zoom), 0.0F).method_39415(regionColor);
/*    */           } 
/*    */           
/* 95 */           for (int r = 0; r < regionRows; r++) {
/*    */             
/* 97 */             gridLinesBuffer.method_22918(matrix4f, (float)upperLeftRegion.x, (float)(upperLeftRegion.y + (r * zoom)), 0.0F).method_39415(regionColor);
/* 98 */             gridLinesBuffer.method_22918(matrix4f, (float)upperLeftRegion.x + (regionCols * zoom), (float)(upperLeftRegion.y + (r * zoom)), 0.0F).method_39415(regionColor);
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\render\map\GridLines.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */