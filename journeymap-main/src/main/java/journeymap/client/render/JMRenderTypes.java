/*     */ package journeymap.client.render;
/*     */ 
/*     */ import com.mojang.blaze3d.pipeline.RenderPipeline;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import com.mojang.blaze3d.textures.GpuTexture;
/*     */ import com.mojang.blaze3d.vertex.VertexFormat;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*     */ import java.util.OptionalDouble;
/*     */ import journeymap.api.client.impl.ClientAPI;
/*     */ import journeymap.api.v2.client.display.Context;
/*     */ import journeymap.client.JourneymapClient;
/*     */ import journeymap.client.texture.RegionTexture;
/*     */ import net.minecraft.class_10868;
/*     */ import net.minecraft.class_1921;
/*     */ import net.minecraft.class_276;
/*     */ import net.minecraft.class_2960;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_4668;
/*     */ import net.minecraft.class_9801;
/*     */ import net.minecraft.class_9851;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JMRenderTypes
/*     */   extends class_1921
/*     */ {
/*  41 */   public static final class_2960 WAYPOINT_DEFAULT_BEAM = class_2960.method_60654("textures/entity/beacon_beam.png");
/*     */   
/*  43 */   static final Object2ObjectOpenHashMap<class_2960, class_1921> GRID_LINES_RENDER_TYPE_MAP = new Object2ObjectOpenHashMap();
/*  44 */   static final Int2ObjectOpenHashMap<Int2ObjectOpenHashMap<class_1921>> REGION_TILE_RENDER_TYPE_MAP = new Int2ObjectOpenHashMap();
/*  45 */   static final Int2ObjectOpenHashMap<class_1921> ICON_RENDER_TYPE_MAP = new Int2ObjectOpenHashMap();
/*  46 */   static final Int2ObjectOpenHashMap<class_1921> ICON_CLAMP_RENDER_TYPE_MAP = new Int2ObjectOpenHashMap();
/*  47 */   static final Int2ObjectOpenHashMap<class_1921> ICON_NOBLUR_RENDER_TYPE_MAP = new Int2ObjectOpenHashMap();
/*  48 */   static final Int2ObjectOpenHashMap<class_1921> ICON_UNMASKED_RENDER_TYPE_MAP = new Int2ObjectOpenHashMap();
/*  49 */   static final Int2ObjectOpenHashMap<class_1921> POLYGON_WITH_TEXTURE_RENDER_TYPE_MAP = new Int2ObjectOpenHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void registerMapShader(String key, RenderPipeline shader) {
/*  56 */     REGION_TILE_RENDER_TYPE_MAP.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void registerPosTexColorShader() {
/*  61 */     POLYGON_WITH_TEXTURE_RENDER_TYPE_MAP.clear();
/*  62 */     ICON_UNMASKED_RENDER_TYPE_MAP.clear();
/*  63 */     ICON_NOBLUR_RENDER_TYPE_MAP.clear();
/*  64 */     ICON_RENDER_TYPE_MAP.clear();
/*  65 */     ICON_CLAMP_RENDER_TYPE_MAP.clear();
/*  66 */     REGION_TILE_RENDER_TYPE_MAP.clear();
/*  67 */     GRID_LINES_RENDER_TYPE_MAP.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public JMRenderTypes(String name, int bufferSize, boolean useDelegate, boolean needsSorting, Runnable pre, Runnable post) {
/*  73 */     super(name, bufferSize, useDelegate, needsSorting, pre, post);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void method_60895(class_9801 meshData) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public class_276 method_68494() {
/*  85 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public RenderPipeline method_68495() {
/*  91 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VertexFormat method_23031() {
/*  98 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public VertexFormat.class_5596 method_23033() {
/* 104 */     return null;
/*     */   }
/*     */   
/* 107 */   protected static final class_4668.class_4684 GRID_LINES_TRANSPARENCY = new class_4668.class_4684("grid_lines_transparency", () -> {
/*     */         RenderWrapper.enableBlend();
/*     */         RenderWrapper.blendFuncSeparate(770, 771, 1, 771);
/*     */         RenderWrapper.texParameter(3553, 10241, 9728);
/*     */         RenderWrapper.texParameter(3553, 10240, 9728);
/*     */         RenderWrapper.texParameter(3553, 10242, 10497);
/*     */         RenderWrapper.texParameter(3553, 10243, 10497);
/*     */       }() -> {
/*     */         RenderWrapper.disableBlend();
/*     */         RenderWrapper.defaultBlendFunc();
/*     */       });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 123 */   protected static final class_4668.class_4684 MINIMAP_MASK_TRANSPARENCY = new class_4668.class_4684("minimap_mask_texturing", () -> RenderSystem.getDevice().createCommandEncoder().clearDepthTexture(class_310.method_1551().method_1522().method_30278(), 0.0D), () -> RenderWrapper.depthFunc(515));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 131 */   protected static final class_4668.class_4675 ICON_TRANSPARENCY = new class_4668.class_4675("icon_transparency", () -> {
/*     */         RenderWrapper.enableBlend();
/*     */         RenderWrapper.blendFuncSeparate(770, 771, 1, 0);
/*     */       }() -> {
/*     */         RenderWrapper.disableBlend();
/*     */         RenderWrapper.defaultBlendFunc();
/*     */       });
/*     */ 
/*     */ 
/*     */   
/* 141 */   protected static final class_4668.class_4675 POLYGON_TRANSPARENCY = new class_4668.class_4675("polygon_transparency", () -> {
/*     */         RenderWrapper.enableBlend();
/*     */         RenderWrapper.blendFuncSeparate(770, 771, 1, 0);
/*     */         RenderWrapper.texParameter(3553, 10241, 9728);
/*     */         RenderWrapper.texParameter(3553, 10240, 9728);
/*     */         RenderWrapper.texParameter(3553, 10242, 10497);
/*     */         RenderWrapper.texParameter(3553, 10243, 10497);
/*     */       }() -> {
/*     */         RenderWrapper.disableBlend();
/*     */         RenderWrapper.defaultBlendFunc();
/*     */       });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void clearRegionRenderTypes(int id) {
/* 158 */     if (id != -1) {
/*     */       
/* 160 */       Int2ObjectOpenHashMap<class_1921> map = (Int2ObjectOpenHashMap<class_1921>)REGION_TILE_RENDER_TYPE_MAP.get(id);
/* 161 */       if (map != null)
/*     */       {
/* 163 */         map.clear();
/*     */       }
/* 165 */       REGION_TILE_RENDER_TYPE_MAP.remove(id);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected static class RegionTileStateShard
/*     */     extends class_4668.class_5939
/*     */   {
/*     */     private final int textureId;
/*     */     
/*     */     public RegionTileStateShard(GpuTexture texture) {
/* 175 */       super(() -> {
/*     */             RenderWrapper.bindTexture(((class_10868)texture).method_68427());
/*     */ 
/*     */             
/*     */             boolean blur = false;
/*     */             
/*     */             if ((ClientAPI.INSTANCE.getLastUIState()).ui == Context.UI.Fullscreen) {
/*     */               blur = ((JourneymapClient.getInstance().getFullMapProperties()).zoomLevel.get().intValue() < 512);
/*     */             } else if ((ClientAPI.INSTANCE.getLastUIState()).ui == Context.UI.Minimap) {
/*     */               blur = ((JourneymapClient.getInstance().getActiveMiniMapProperties()).zoomLevel.get().intValue() < 512);
/*     */             } 
/*     */             
/*     */             int mipmapLevels = (JourneymapClient.getInstance().getCoreProperties()).mipmapLevels.get().intValue();
/*     */             
/* 189 */             int mag = (mipmapLevels == 0 && blur) ? 9729 : 9984;
/* 190 */             int min = (mipmapLevels == 0 && blur) ? 9729 : 9728;
/*     */             
/*     */             RenderWrapper.bindTexture(((class_10868)texture).method_68427());
/*     */             RenderWrapper.texParameter(3553, 10241, mag);
/*     */             RenderWrapper.texParameter(3553, 10240, min);
/*     */             RenderWrapper.texParameter(3553, 10242, 33071);
/*     */             RenderWrapper.texParameter(3553, 10243, 33071);
/*     */             RenderWrapper.texParameter(3553, 33085, mipmapLevels);
/*     */             RenderWrapper.texParameter(3553, 33082, 0);
/*     */             RenderWrapper.texParameter(3553, 33083, mipmapLevels);
/*     */             RenderWrapper.texParameter(3553, 34049, 0);
/*     */             RenderWrapper.setShaderTexture(0, texture);
/*     */           }() -> {
/*     */           
/*     */           });
/* 205 */       this.textureId = ((class_10868)texture).method_68427();
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 210 */       return this.field_21363 + "[" + this.field_21363 + ")]";
/*     */     }
/*     */   }
/*     */   
/*     */   protected static class IconStateShard
/*     */     extends class_4668.class_5939
/*     */   {
/*     */     private final int textureId;
/*     */     private final boolean blur;
/*     */     private final boolean clamp;
/*     */     
/*     */     public IconStateShard(GpuTexture tex) {
/* 222 */       this(tex, true);
/*     */     }
/*     */ 
/*     */     
/*     */     public IconStateShard(GpuTexture texture, boolean blur) {
/* 227 */       this(texture, blur, false);
/*     */     }
/*     */ 
/*     */     
/*     */     public IconStateShard(GpuTexture texture, boolean blur, boolean clamp) {
/* 232 */       super(() -> {
/*     */             RenderWrapper.bindTexture(((class_10868)texture).method_68427()); RenderWrapper.texParameter(3553, 10241, blur ? 9729 : 9728);
/*     */             RenderWrapper.texParameter(3553, 10240, blur ? 9729 : 9728);
/*     */             RenderWrapper.texParameter(3553, 10242, clamp ? 33071 : 10497);
/*     */             RenderWrapper.texParameter(3553, 10243, clamp ? 33071 : 10497);
/*     */             RenderWrapper.setShaderTexture(0, texture);
/*     */           }() -> {
/*     */           
/*     */           });
/* 241 */       this.blur = blur;
/* 242 */       this.clamp = clamp;
/* 243 */       this.textureId = ((class_10868)texture).method_68427();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 250 */       return this.field_21363 + "[" + this.field_21363 + "] blur:" + this.textureId + " clamp:" + this.blur;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class_1921 getGridLines(class_2960 resourceLocation) {
/*     */     class_1921.class_4687 class_4687;
/* 256 */     class_1921 type = (class_1921)GRID_LINES_RENDER_TYPE_MAP.get(resourceLocation);
/* 257 */     if (type == null) {
/*     */       
/* 259 */       class_4687 = method_24049("grid_lines" + resourceLocation.method_36181(), 256, false, true, Pipelines.GRID_LINES_RENDER_TYPE_PIPELINE, 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 264 */           class_1921.class_4688.method_23598()
/* 265 */           .method_34577((class_4668.class_5939)new class_4668.class_4683(resourceLocation, class_9851.field_52395, false))
/* 266 */           .method_23609(new class_4668.class_4677(OptionalDouble.empty()))
/* 267 */           .method_23607(field_21352)
/*     */           
/* 269 */           .method_23608(field_21384)
/* 270 */           .method_23617(false));
/* 271 */       GRID_LINES_RENDER_TYPE_MAP.put(resourceLocation, class_4687);
/*     */     } 
/* 273 */     return (class_1921)class_4687;
/*     */   }
/*     */   
/*     */   public static class_1921 getRegionTile(RegionTexture texture, int shaderIndex) {
/*     */     class_1921.class_4687 class_4687;
/* 278 */     int id = ((class_10868)texture.method_68004()).method_68427();
/* 279 */     Int2ObjectOpenHashMap<class_1921> map = (Int2ObjectOpenHashMap<class_1921>)REGION_TILE_RENDER_TYPE_MAP.get(id);
/* 280 */     if (map == null) {
/*     */       
/* 282 */       map = new Int2ObjectOpenHashMap();
/* 283 */       REGION_TILE_RENDER_TYPE_MAP.put(id, map);
/*     */     } 
/* 285 */     class_1921 type = (class_1921)map.get(shaderIndex);
/* 286 */     if (type == null) {
/*     */       
/* 288 */       String shader = RegionTileShaders.from(shaderIndex);
/* 289 */       RenderPipeline shaderState = (Pipelines.REGION_SHADERS_MAP.get(shader) == null) ? Pipelines.REGION_DEFAULT_RENDER_PIPELINE : (RenderPipeline)Pipelines.REGION_SHADERS_MAP.get(shader);
/* 290 */       class_4687 = method_24049("region_tile" + id, 256, false, false, shaderState, 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 296 */           class_1921.class_4688.method_23598()
/* 297 */           .method_34577(new RegionTileStateShard(texture.method_68004()))
/* 298 */           .method_23609(new class_4668.class_4677(OptionalDouble.empty()))
/* 299 */           .method_23607(field_21352)
/* 300 */           .method_23608(field_21384)
/* 301 */           .method_23617(false));
/* 302 */       map.put(id, class_4687);
/*     */     } 
/* 304 */     return (class_1921)class_4687;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class_1921 getMinimapCircleMask(GpuTexture texture) {
/* 309 */     return (class_1921)method_24049("minimap_circle_mask", 256, false, false, Pipelines.MINIMAP_CIRCLE_MASK_PIPELINE, 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 315 */         class_1921.class_4688.method_23598()
/* 316 */         .method_34577(new class_4668.class_5939(() -> {
/*     */               RenderWrapper.bindTexture(((class_10868)texture).method_68427());
/*     */ 
/*     */               
/*     */               RenderWrapper.setShaderTexture(0, texture);
/*     */             }() -> {
/*     */             
/* 323 */             })).method_23609(new class_4668.class_4677(OptionalDouble.empty()))
/* 324 */         .method_23607(field_21352)
/* 325 */         .method_23608(field_21384)
/* 326 */         .method_23614(MINIMAP_MASK_TRANSPARENCY)
/* 327 */         .method_23617(false));
/*     */   }
/*     */ 
/*     */   
/* 331 */   public static final class_1921 MINIMAP_RECTANGLE_MASK_RENDER_TYPE = (class_1921)method_24049("minimap_rectangle_mask", 256, false, false, Pipelines.MINIMAP_RECTANGLE_MASK_RENDER_PIPELINE, 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 337 */       class_1921.class_4688.method_23598()
/* 338 */       .method_34577(field_21378)
/* 339 */       .method_23609(new class_4668.class_4677(OptionalDouble.empty()))
/* 340 */       .method_23607(field_21352)
/* 341 */       .method_23608(field_21384)
/* 342 */       .method_23614(MINIMAP_MASK_TRANSPARENCY)
/* 343 */       .method_23617(false));
/*     */ 
/*     */   
/*     */   public static class_1921 getIcon(GpuTexture texture) {
/*     */     class_1921.class_4687 class_4687;
/* 348 */     int id = ((class_10868)texture).method_68427();
/* 349 */     class_1921 type = (class_1921)ICON_RENDER_TYPE_MAP.get(id);
/* 350 */     if (type == null) {
/*     */       
/* 352 */       class_4687 = method_24049("icon" + id, 256, false, false, Pipelines.ICON_RENDER_PIPELINE, 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 358 */           class_1921.class_4688.method_23598()
/* 359 */           .method_34577(new IconStateShard(texture))
/* 360 */           .method_23609(new class_4668.class_4677(OptionalDouble.empty()))
/* 361 */           .method_23607(field_21352)
/* 362 */           .method_23608(field_21384)
/* 363 */           .method_23617(false));
/* 364 */       ICON_RENDER_TYPE_MAP.put(id, class_4687);
/*     */     } 
/* 366 */     return (class_1921)class_4687;
/*     */   }
/*     */   
/*     */   public static class_1921 getIconClamp(GpuTexture texture) {
/*     */     class_1921.class_4687 class_4687;
/* 371 */     int id = ((class_10868)texture).method_68427();
/* 372 */     class_1921 type = (class_1921)ICON_CLAMP_RENDER_TYPE_MAP.get(id);
/* 373 */     if (type == null) {
/*     */       
/* 375 */       class_4687 = method_24049("icon" + id, 256, false, false, Pipelines.ICON_RENDER_PIPELINE, 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 381 */           class_1921.class_4688.method_23598()
/* 382 */           .method_34577(new IconStateShard(texture, true, false))
/* 383 */           .method_23609(new class_4668.class_4677(OptionalDouble.empty()))
/* 384 */           .method_23607(field_21352)
/* 385 */           .method_23608(field_21384)
/* 386 */           .method_23617(false));
/* 387 */       ICON_CLAMP_RENDER_TYPE_MAP.put(id, class_4687);
/*     */     } 
/* 389 */     return (class_1921)class_4687;
/*     */   }
/*     */   
/*     */   public static class_1921 getIconNoBlur(GpuTexture texture) {
/*     */     class_1921.class_4687 class_4687;
/* 394 */     int id = ((class_10868)texture).method_68427();
/* 395 */     class_1921 type = (class_1921)ICON_NOBLUR_RENDER_TYPE_MAP.get(id);
/* 396 */     if (type == null) {
/*     */       
/* 398 */       class_4687 = method_24049("icon" + id, 256, false, false, Pipelines.ICON_RENDER_PIPELINE, 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 404 */           class_1921.class_4688.method_23598()
/* 405 */           .method_34577(new IconStateShard(texture, false))
/* 406 */           .method_23609(new class_4668.class_4677(OptionalDouble.empty()))
/* 407 */           .method_23607(field_21352)
/* 408 */           .method_23608(field_21384)
/* 409 */           .method_23617(false));
/* 410 */       ICON_NOBLUR_RENDER_TYPE_MAP.put(id, class_4687);
/*     */     } 
/* 412 */     return (class_1921)class_4687;
/*     */   }
/*     */   
/*     */   public static class_1921 getIconUnmasked(GpuTexture texture) {
/*     */     class_1921.class_4687 class_4687;
/* 417 */     int id = ((class_10868)texture).method_68427();
/* 418 */     class_1921 type = (class_1921)ICON_UNMASKED_RENDER_TYPE_MAP.get(id);
/* 419 */     if (type == null) {
/*     */       
/* 421 */       class_4687 = method_24049("icon_unmasked" + id, 256, false, false, Pipelines.ICON_UNMASKED_RENDER_PIPELINE, 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 427 */           class_1921.class_4688.method_23598()
/* 428 */           .method_34577(new IconStateShard(texture))
/* 429 */           .method_23609(new class_4668.class_4677(OptionalDouble.empty()))
/* 430 */           .method_23607(field_21352)
/* 431 */           .method_23608(field_21384)
/* 432 */           .method_23617(false));
/* 433 */       ICON_UNMASKED_RENDER_TYPE_MAP.put(id, class_4687);
/*     */     } 
/* 435 */     return (class_1921)class_4687;
/*     */   }
/*     */   
/*     */   public static class_1921 getPolygonWithTexture(GpuTexture texture) {
/*     */     class_1921.class_4687 class_4687;
/* 440 */     int id = ((class_10868)texture).method_68427();
/* 441 */     class_1921 type = (class_1921)POLYGON_WITH_TEXTURE_RENDER_TYPE_MAP.get(id);
/* 442 */     if (type == null) {
/*     */       
/* 444 */       class_4687 = method_24049("polygon" + id, 256, false, false, Pipelines.POLYGON_WITH_TEXTURE_RENDER_PIPELINE, 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 450 */           class_1921.class_4688.method_23598()
/* 451 */           .method_34577(new class_4668.class_5939(() -> {
/*     */                 RenderWrapper.bindTexture(id);
/*     */ 
/*     */                 
/*     */                 RenderWrapper.setShaderTexture(0, texture);
/*     */               }() -> {
/*     */               
/* 458 */               })).method_23609(new class_4668.class_4677(OptionalDouble.empty()))
/* 459 */           .method_23607(field_21352)
/*     */           
/* 461 */           .method_23608(field_21384)
/* 462 */           .method_23617(false));
/* 463 */       POLYGON_WITH_TEXTURE_RENDER_TYPE_MAP.put(id, class_4687);
/*     */     } 
/* 465 */     return (class_1921)class_4687;
/*     */   }
/*     */   
/* 468 */   public static final class_1921 BEAM_RENDER_TYPE = (class_1921)method_24049("waypoint_beam", 256, false, false, Pipelines.WAYPOINT_BEAM_RENDER_PIPELINE, 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 474 */       class_1921.class_4688.method_23598()
/* 475 */       .method_34577((class_4668.class_5939)new class_4668.class_4683(WAYPOINT_DEFAULT_BEAM, class_9851.field_52394, false))
/* 476 */       .method_23609(new class_4668.class_4677(OptionalDouble.empty()))
/* 477 */       .method_23607(field_21352)
/* 478 */       .method_23608(field_21383)
/* 479 */       .method_23617(false));
/*     */ 
/*     */   
/* 482 */   public static final class_1921 RECTANGLE_RENDER_TYPE = (class_1921)method_24049("rectangle", 256, false, false, Pipelines.RECTANGLE_RENDER_PIPELINE, 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 488 */       class_1921.class_4688.method_23598()
/* 489 */       .method_34577(field_21378)
/* 490 */       .method_23609(new class_4668.class_4677(OptionalDouble.empty()))
/* 491 */       .method_23607(field_21352)
/* 492 */       .method_23608(field_21384)
/* 493 */       .method_23617(false));
/*     */ 
/*     */   
/* 496 */   public static final class_1921 GRID_LINES_RENDER_TYPE = (class_1921)method_24049("grid_lines", 256, false, true, Pipelines.GRID_LINES_RENDER_PIPELINE, 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 502 */       class_1921.class_4688.method_23598()
/* 503 */       .method_34577(field_21378)
/* 504 */       .method_23609(new class_4668.class_4677(OptionalDouble.of(1.0D)))
/* 505 */       .method_23607(field_21352)
/* 506 */       .method_23608(field_21384)
/* 507 */       .method_23617(false));
/*     */ 
/*     */   
/* 510 */   public static final class_1921 POLYGON_WITHOUT_TEXTURE_RENDER_TYPE = (class_1921)method_24049("polygon", 256, false, false, Pipelines.POLYGON_POS_PIPELINE, 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 516 */       class_1921.class_4688.method_23598()
/* 517 */       .method_34577(field_21378)
/* 518 */       .method_23609(new class_4668.class_4677(OptionalDouble.empty()))
/* 519 */       .method_23607(field_21352)
/*     */       
/* 521 */       .method_23608(field_21384)
/* 522 */       .method_23617(false));
/*     */ 
/*     */ 
/*     */   
/* 526 */   public static final class_1921 POLYGON_STROKE_RENDER_TYPE = (class_1921)method_24049("polygon_stroke", 256, false, false, Pipelines.POLYGON_STROKE_PIPELINE, 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 532 */       class_1921.class_4688.method_23598()
/* 533 */       .method_34577(field_21378)
/* 534 */       .method_23609(new class_4668.class_4677(OptionalDouble.empty()))
/* 535 */       .method_23607(field_21352)
/*     */       
/* 537 */       .method_23608(field_21384)
/* 538 */       .method_23617(false));
/*     */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\render\JMRenderTypes.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */