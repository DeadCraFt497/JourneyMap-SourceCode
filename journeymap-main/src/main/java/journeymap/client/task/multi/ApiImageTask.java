/*    */ package journeymap.client.task.multi;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.util.function.Consumer;
/*    */ import journeymap.api.v2.client.display.Context;
/*    */ import journeymap.client.io.FileHandler;
/*    */ import journeymap.client.model.map.MapType;
/*    */ import journeymap.common.Journeymap;
/*    */ import journeymap.common.log.LogFormatter;
/*    */ import net.minecraft.class_1011;
/*    */ import net.minecraft.class_1923;
/*    */ import net.minecraft.class_1937;
/*    */ import net.minecraft.class_310;
/*    */ import net.minecraft.class_5321;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ApiImageTask
/*    */   implements Runnable
/*    */ {
/*    */   final String modId;
/*    */   final class_5321<class_1937> dimension;
/*    */   final MapType mapType;
/*    */   final class_1923 startChunk;
/*    */   final class_1923 endChunk;
/*    */   final Integer vSlice;
/*    */   final int zoom;
/*    */   final boolean showGrid;
/*    */   final File jmWorldDir;
/*    */   final Consumer<class_1011> callback;
/*    */   
/*    */   public ApiImageTask(String modId, class_5321<class_1937> dimension, Context.MapType apiMapType, class_1923 startChunk, class_1923 endChunk, Integer vSlice, int zoom, boolean showGrid, Consumer<class_1011> callback) {
/* 42 */     this.modId = modId;
/* 43 */     this.dimension = dimension;
/* 44 */     this.startChunk = startChunk;
/* 45 */     this.endChunk = endChunk;
/* 46 */     this.zoom = zoom;
/* 47 */     this.showGrid = showGrid;
/* 48 */     this.callback = callback;
/* 49 */     this.vSlice = vSlice;
/* 50 */     this.mapType = MapType.fromApiContextMapType(apiMapType, vSlice, dimension);
/* 51 */     this.jmWorldDir = FileHandler.getJMWorldDir(class_310.method_1551());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void run() {
/* 57 */     class_1011 image = null;
/*    */ 
/*    */     
/*    */     try {
/* 61 */       int i = (int)Math.pow(2.0D, this.zoom);
/*    */     
/*    */     }
/* 64 */     catch (Throwable t) {
/*    */       
/* 66 */       Journeymap.getLogger().error("Error in ApiImageTask: {}" + String.valueOf(t), LogFormatter.toString(t));
/*    */     } 
/*    */ 
/*    */     
/* 70 */     class_1011 finalImage = image;
/* 71 */     class_310.method_1551().method_20493(() -> this.callback.accept(finalImage));
/*    */   }
/*    */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\task\multi\ApiImageTask.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */