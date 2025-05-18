/*    */ package journeymap.client.texture;
/*    */ 
/*    */ import java.util.stream.IntStream;
/*    */ import net.minecraft.class_1011;
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
/*    */ public class ComparableNativeImage
/*    */   extends class_1011
/*    */ {
/*    */   private boolean changed = false;
/*    */   
/*    */   public ComparableNativeImage(class_1011 other) {
/* 21 */     super(other.method_4318(), other.method_4307(), other.method_4323(), false);
/* 22 */     method_4317(other);
/*    */   }
/*    */ 
/*    */   
/*    */   public ComparableNativeImage(class_1011.class_1012 format, int width, int height) {
/* 27 */     super(format, width, height, false);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public synchronized void method_61941(int x, int y, int argb) {
/* 33 */     if (!this.changed)
/*    */     {
/* 35 */       if (method_61940(x, y) != argb)
/*    */       {
/* 37 */         this.changed = true;
/*    */       }
/*    */     }
/* 40 */     super.method_61941(x, y, argb);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isChanged() {
/* 45 */     return this.changed;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setChanged(boolean val) {
/* 50 */     this.changed = val;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean identicalTo(class_1011 other) {
/* 55 */     return areIdentical(getPixelData(), getPixelData(other));
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean areIdentical(int[] pixels, int[] otherPixels) {
/* 60 */     return IntStream.range(0, pixels.length)
/* 61 */       .map(i -> pixels[i] ^ 0xFFFFFFFF | otherPixels[i])
/* 62 */       .allMatch(n -> (n == -1));
/*    */   }
/*    */ 
/*    */   
/*    */   public int[] getPixelData() {
/* 67 */     return getPixelData(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public static int[] getPixelData(class_1011 image) {
/* 72 */     return image.method_4322();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void close() {
/* 78 */     super.close();
/*    */   }
/*    */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\texture\ComparableNativeImage.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */