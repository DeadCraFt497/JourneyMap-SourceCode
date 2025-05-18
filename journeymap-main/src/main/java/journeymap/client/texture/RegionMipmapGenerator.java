/*     */ package journeymap.client.texture;
/*     */ 
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import com.mojang.blaze3d.textures.GpuTexture;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import net.minecraft.class_1011;
/*     */ import net.minecraft.class_1923;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RegionMipmapGenerator
/*     */ {
/*  14 */   private static final float[] GAMMA_POW_2_2 = new float[256];
/*     */ 
/*     */   
/*     */   static {
/*  18 */     for (int i = 0; i < 256; i++)
/*     */     {
/*  20 */       GAMMA_POW_2_2[i] = (float)Math.pow((i / 255.0F), 2.2D);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class_1011[] generateMipmaps(class_1011 baseImage, int mipmapLevel) {
/*  26 */     class_1011[] mipmaps = new class_1011[mipmapLevel + 1];
/*  27 */     mipmaps[0] = baseImage;
/*     */     
/*  29 */     for (int i = 1; i <= mipmapLevel; i++) {
/*     */       
/*  31 */       class_1011 image = mipmaps[i - 1];
/*  32 */       class_1011 nextImage = new class_1011(image.method_4307() >> 1, image.method_4323() >> 1, false);
/*  33 */       int width = nextImage.method_4307();
/*  34 */       int height = nextImage.method_4323();
/*     */       
/*  36 */       for (int x = 0; x < width; x++) {
/*     */         
/*  38 */         for (int y = 0; y < height; y++)
/*     */         {
/*  40 */           nextImage.method_61941(x, y, alphaBlend(image
/*  41 */                 .method_61940(x * 2 + 0, y * 2 + 0), image
/*  42 */                 .method_61940(x * 2 + 1, y * 2 + 0), image
/*  43 */                 .method_61940(x * 2 + 0, y * 2 + 1), image
/*  44 */                 .method_61940(x * 2 + 1, y * 2 + 1)));
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/*  49 */       mipmaps[i] = nextImage;
/*     */     } 
/*     */     
/*  52 */     return mipmaps;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void updateMipmapsAndUpload(class_1011[] mipmaps, Set<class_1923> dirtyChunks, GpuTexture texture) {
/*  57 */     Set<class_1923> dirtyChunksPrev = new HashSet<>(dirtyChunks);
/*  58 */     int chunkSize = 16;
/*     */     
/*  60 */     for (int i = 1; i < mipmaps.length; i++) {
/*     */       
/*  62 */       HashSet<class_1923> dirtyChunksScaled = new HashSet<>();
/*  63 */       for (class_1923 pos : dirtyChunksPrev)
/*     */       {
/*  65 */         dirtyChunksScaled.add(new class_1923(pos.field_9181 >> 1, pos.field_9180 >> 1));
/*     */       }
/*  67 */       dirtyChunksPrev = dirtyChunksScaled;
/*     */       
/*  69 */       class_1011 image = mipmaps[i - 1];
/*  70 */       class_1011 nextImage = mipmaps[i];
/*  71 */       chunkSize = Math.max(chunkSize >> 1, 1);
/*     */       
/*  73 */       for (class_1923 pos : dirtyChunksScaled) {
/*     */         
/*  75 */         for (int x = pos.field_9181; x < pos.field_9181 + chunkSize; x++) {
/*     */           
/*  77 */           for (int y = pos.field_9180; y < pos.field_9180 + chunkSize; y++)
/*     */           {
/*  79 */             nextImage.method_61941(x, y, alphaBlend(image
/*  80 */                   .method_61940(x * 2 + 0, y * 2 + 0), image
/*  81 */                   .method_61940(x * 2 + 1, y * 2 + 0), image
/*  82 */                   .method_61940(x * 2 + 0, y * 2 + 1), image
/*  83 */                   .method_61940(x * 2 + 1, y * 2 + 1)));
/*     */           }
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/*  89 */         RenderSystem.getDevice().createCommandEncoder().writeToTexture(texture, nextImage, i, pos.field_9181, pos.field_9180, chunkSize, chunkSize, pos.field_9181, pos.field_9180);
/*     */       } 
/*     */ 
/*     */       
/*  93 */       mipmaps[i] = nextImage;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static int alphaBlend(int color1, int color2, int color3, int color4) {
/*  99 */     float r = 0.0F;
/* 100 */     float g = 0.0F;
/* 101 */     float b = 0.0F;
/* 102 */     int count = 0;
/* 103 */     if (color1 >> 24 != 0) {
/*     */       
/* 105 */       r += getPow22(color1 >> 0);
/* 106 */       g += getPow22(color1 >> 8);
/* 107 */       b += getPow22(color1 >> 16);
/* 108 */       count++;
/*     */     } 
/*     */     
/* 111 */     if (color2 >> 24 != 0) {
/*     */       
/* 113 */       r += getPow22(color2 >> 0);
/* 114 */       g += getPow22(color2 >> 8);
/* 115 */       b += getPow22(color2 >> 16);
/* 116 */       count++;
/*     */     } 
/*     */     
/* 119 */     if (color3 >> 24 != 0) {
/*     */       
/* 121 */       r += getPow22(color3 >> 0);
/* 122 */       g += getPow22(color3 >> 8);
/* 123 */       b += getPow22(color3 >> 16);
/* 124 */       count++;
/*     */     } 
/*     */     
/* 127 */     if (color4 >> 24 != 0) {
/*     */       
/* 129 */       r += getPow22(color4 >> 0);
/* 130 */       g += getPow22(color4 >> 8);
/* 131 */       b += getPow22(color4 >> 16);
/* 132 */       count++;
/*     */     } 
/*     */     
/* 135 */     if (count == 0)
/*     */     {
/* 137 */       return 0;
/*     */     }
/*     */     
/* 140 */     r /= count;
/* 141 */     g /= count;
/* 142 */     b /= count;
/* 143 */     int r2 = (int)(Math.pow(r, 0.45454545454545453D) * 255.0D);
/* 144 */     int g2 = (int)(Math.pow(g, 0.45454545454545453D) * 255.0D);
/* 145 */     int b2 = (int)(Math.pow(b, 0.45454545454545453D) * 255.0D);
/*     */     
/* 147 */     return 0xFF000000 | b2 << 16 | g2 << 8 | r2;
/*     */   }
/*     */ 
/*     */   
/*     */   private static float getPow22(int value) {
/* 152 */     return GAMMA_POW_2_2[value & 0xFF];
/*     */   }
/*     */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\texture\RegionMipmapGenerator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */