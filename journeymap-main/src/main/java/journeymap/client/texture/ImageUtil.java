/*     */ package journeymap.client.texture;
/*     */ 
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import java.util.Objects;
/*     */ import journeymap.client.cartography.color.RGB;
/*     */ import journeymap.client.render.RenderWrapper;
/*     */ import journeymap.common.Journeymap;
/*     */ import journeymap.common.accessors.NativeImageAccess;
/*     */ import net.minecraft.class_1011;
/*     */ import net.minecraft.class_1043;
/*     */ import org.lwjgl.stb.STBImageResize;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ImageUtil
/*     */ {
/*     */   public static class_1011 getSizedImage(int width, int height, class_1011 from, boolean autoClose) {
/*  21 */     class_1011 scaledImage = new class_1011(from.method_4318(), width, height, false);
/*  22 */     STBImageResize.nstbir_resize_uint8_generic(from.field_4988, from
/*     */         
/*  24 */         .method_4307(), from
/*  25 */         .method_4323(), 0, scaledImage.field_4988, width, height, 0, from
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  31 */         .method_4318().method_4335(), -1, 0, 1, 1, 0, 0L);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  38 */     if (autoClose)
/*     */     {
/*  40 */       from.close();
/*     */     }
/*  42 */     return scaledImage;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class_1011 getScaledImage(float scale, class_1011 from, boolean autoClose) {
/*  47 */     int scaledWidth = (int)(from.method_4307() * scale);
/*  48 */     int scaledHeight = (int)(from.method_4323() * scale);
/*  49 */     class_1011 scaledImage = new class_1011(from.method_4318(), scaledWidth, scaledHeight, false);
/*  50 */     STBImageResize.nstbir_resize_uint8_generic(from.field_4988, from
/*     */         
/*  52 */         .method_4307(), from
/*  53 */         .method_4323(), 0, scaledImage.field_4988, scaledWidth, scaledHeight, 0, from
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  59 */         .method_4318().method_4335(), -1, 0, 1, 1, 0, 0L);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  66 */     if (autoClose)
/*     */     {
/*  68 */       from.close();
/*     */     }
/*  70 */     return scaledImage;
/*     */   }
/*     */ 
/*     */   
/*     */   public static ComparableNativeImage getComparableSubImage(int x, int y, int width, int height, class_1011 from, boolean autoClose) {
/*  75 */     class_1011 to = new ComparableNativeImage(from.method_4318(), width, height);
/*  76 */     return (ComparableNativeImage)getSubImage(x, y, width, height, from, to, autoClose);
/*     */   }
/*     */ 
/*     */   
/*     */   public static class_1011 getSubImage(int x, int y, int width, int height, class_1011 from, boolean autoClose) {
/*  81 */     class_1011 to = new class_1011(width, height, false);
/*  82 */     return getSubImage(x, y, width, height, from, to, autoClose);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class_1011 getSubImage(int x, int y, int width, int height, class_1011 from, class_1011 to, boolean autoClose) {
/*  88 */     if (from.field_4988 == 0L)
/*     */     {
/*  90 */       throw new IllegalStateException("Image is not allocated. " + String.valueOf(from));
/*     */     }
/*  92 */     if (to.method_4318() != from.method_4318())
/*     */     {
/*  94 */       throw new UnsupportedOperationException("getSubImage only works for images of the same format.");
/*     */     }
/*     */ 
/*     */     
/*  98 */     int i = from.method_4318().method_4335();
/*  99 */     STBImageResize.nstbir_resize_uint8_generic(from.field_4988 + (x + y * from
/* 100 */         .method_4307()) * i, width, height, from
/*     */ 
/*     */         
/* 103 */         .method_4307() * i, to.field_4988, to
/*     */         
/* 105 */         .method_4307(), to
/* 106 */         .method_4323(), 0, from
/*     */         
/* 108 */         .method_4318().method_4335(), -1, 0, 1, 1, 0, 0L);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 116 */     if (autoClose)
/*     */     {
/* 118 */       from.close();
/*     */     }
/* 120 */     return to;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class_1011 recolorImage(class_1011 image, int color) {
/* 125 */     class_1011 tintedImage = new class_1011(image.method_4307(), image.method_4323(), false);
/* 126 */     tintedImage.method_4317(image);
/* 127 */     for (int x = 0; x < image.method_4307(); x++) {
/*     */       
/* 129 */       for (int y = 0; y < image.method_4307(); y++) {
/*     */         
/* 131 */         int imgColor = image.method_61940(x, y);
/* 132 */         if (getAlpha(imgColor) > 1)
/*     */         {
/* 134 */           ((NativeImageAccess)tintedImage).blendPixel(x, y, RGB.toArgb(color, 0.75F));
/*     */         }
/*     */       } 
/*     */     } 
/* 138 */     return tintedImage;
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
/*     */   public static class_1011 getNewBlankImage(int width, int height) {
/* 151 */     class_1011 image = new class_1011(width, height, false);
/* 152 */     for (int x = 0; x < image.method_4307(); x++) {
/*     */       
/* 154 */       for (int y = 0; y < image.method_4323(); y++)
/*     */       {
/* 156 */         image.method_61941(x, y, RGB.toRgba(0, 0.0F));
/*     */       }
/*     */     } 
/* 159 */     return image;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void closeSafely(class_1011 image) {
/* 164 */     if (image != null) {
/*     */       
/*     */       try {
/*     */         
/* 168 */         image.close();
/*     */       }
/* 170 */       catch (Throwable t) {
/*     */         
/* 172 */         Journeymap.getLogger().warn("Failed to clear and close image: ", t);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void closeSafely(class_1043 texture) {
/* 179 */     if (texture != null && texture.method_4525() != null && (texture.method_4525()).field_4988 != 0L) {
/*     */       
/*     */       try {
/*     */         
/* 183 */         if (!RenderSystem.isOnRenderThread())
/*     */         {
/* 185 */           Objects.requireNonNull(texture); RenderWrapper.recordRenderQueue(texture::close);
/*     */         }
/*     */         else
/*     */         {
/* 189 */           texture.close();
/*     */         }
/*     */       
/*     */       }
/* 193 */       catch (Throwable t) {
/*     */         
/* 195 */         Journeymap.getLogger().warn("Failed to clear and close image: ", t);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getAlpha(int color) {
/* 202 */     return color >> 24 & 0xFF;
/*     */   }
/*     */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\texture\ImageUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */