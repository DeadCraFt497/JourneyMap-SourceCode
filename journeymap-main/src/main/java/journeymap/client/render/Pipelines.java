/*     */ package journeymap.client.render;
/*     */ 
/*     */ import com.mojang.blaze3d.pipeline.BlendFunction;
/*     */ import com.mojang.blaze3d.pipeline.RenderPipeline;
/*     */ import com.mojang.blaze3d.platform.DepthTestFunction;
/*     */ import com.mojang.blaze3d.platform.DestFactor;
/*     */ import com.mojang.blaze3d.platform.SourceFactor;
/*     */ import com.mojang.blaze3d.vertex.VertexFormat;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*     */ import net.minecraft.class_10789;
/*     */ import net.minecraft.class_156;
/*     */ import net.minecraft.class_290;
/*     */ import net.minecraft.class_2960;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface Pipelines
/*     */ {
/*  21 */   public static final BlendFunction BLEND_SRC_MINUS_SRS_ONE_ZERO = new BlendFunction(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
/*  22 */   public static final RenderPipeline.Snippet COLOR_WRITE = RenderPipeline.builder(new RenderPipeline.Snippet[0])
/*  23 */     .withColorWrite(true)
/*  24 */     .withDepthWrite(false)
/*  25 */     .buildSnippet();
/*  26 */   public static final RenderPipeline.Snippet COLOR_WRITE_NO_ALPHA = RenderPipeline.builder(new RenderPipeline.Snippet[0])
/*  27 */     .withColorWrite(true, false)
/*  28 */     .withDepthWrite(false)
/*  29 */     .buildSnippet();
/*  30 */   public static final RenderPipeline.Snippet DEPTH_WRITE = RenderPipeline.builder(new RenderPipeline.Snippet[0])
/*  31 */     .withColorWrite(false)
/*  32 */     .withDepthWrite(true)
/*  33 */     .buildSnippet();
/*  34 */   public static final RenderPipeline.Snippet COLOR_DEPTH_WRITE = RenderPipeline.builder(new RenderPipeline.Snippet[0])
/*  35 */     .withColorWrite(true)
/*  36 */     .withDepthWrite(true)
/*  37 */     .buildSnippet();
/*     */   
/*  39 */   public static final RenderPipeline.Snippet MATRICES_SNIPPET = RenderPipeline.builder(new RenderPipeline.Snippet[0])
/*  40 */     .withUniform("ModelViewMat", class_10789.field_56747)
/*  41 */     .withUniform("ProjMat", class_10789.field_56747)
/*  42 */     .buildSnippet();
/*     */   
/*  44 */   public static final RenderPipeline.Snippet MATRICES_COLOR_SNIPPET = RenderPipeline.builder(new RenderPipeline.Snippet[] { MATRICES_SNIPPET
/*  45 */       }).withUniform("ColorModulator", class_10789.field_56746)
/*  46 */     .buildSnippet();
/*     */   
/*  48 */   public static final RenderPipeline.Snippet POSITION_TEX_COLOR_SHADER = RenderPipeline.builder(new RenderPipeline.Snippet[] { MATRICES_COLOR_SNIPPET
/*  49 */       }).withLocation("jm/pipeline/position_tex_color")
/*  50 */     .withVertexShader("core/position_tex_color")
/*  51 */     .withFragmentShader("core/position_tex_color")
/*  52 */     .withSampler("Sampler0")
/*  53 */     .buildSnippet();
/*     */   
/*  55 */   public static final RenderPipeline.Snippet POSITION_TEX_SHADER = RenderPipeline.builder(new RenderPipeline.Snippet[] { MATRICES_COLOR_SNIPPET
/*  56 */       }).withLocation("jm/pipeline/position_tex")
/*  57 */     .withVertexShader("core/position_tex")
/*  58 */     .withFragmentShader("core/position_tex")
/*  59 */     .withSampler("Sampler0")
/*  60 */     .buildSnippet();
/*  61 */   public static final RenderPipeline.Snippet POSITION_COLOR_SHADER = RenderPipeline.builder(new RenderPipeline.Snippet[] { MATRICES_COLOR_SNIPPET
/*  62 */       }).withLocation("jm/pipeline/position_color")
/*  63 */     .withVertexShader("core/position_color")
/*  64 */     .withFragmentShader("core/position_color")
/*  65 */     .buildSnippet();
/*  66 */   public static final RenderPipeline.Snippet POSITION_COLOR_TEX_LIGHTMAP_SHADER = RenderPipeline.builder(new RenderPipeline.Snippet[] { MATRICES_COLOR_SNIPPET
/*  67 */       }).withLocation("jm/pipeline/position_color_tex_lightmap")
/*  68 */     .withVertexShader("core/position_color_tex_lightmap")
/*  69 */     .withFragmentShader("core/position_color_tex_lightmap")
/*  70 */     .withSampler("Sampler0")
/*  71 */     .buildSnippet();
/*  72 */   public static final RenderPipeline GRID_LINES_RENDER_TYPE_PIPELINE = RenderPipeline.builder(new RenderPipeline.Snippet[] { POSITION_TEX_COLOR_SHADER, COLOR_WRITE
/*  73 */       }).withLocation("jm/pipeline/grid_lines_type")
/*  74 */     .withVertexFormat(class_290.field_1575, VertexFormat.class_5596.field_27382)
/*  75 */     .withDepthTestFunction(DepthTestFunction.LEQUAL_DEPTH_TEST)
/*  76 */     .withBlend(BlendFunction.TRANSLUCENT)
/*  77 */     .withCull(false)
/*  78 */     .build();
/*     */   
/*  80 */   public static final RenderPipeline GRID_LINES_RENDER_PIPELINE = RenderPipeline.builder(new RenderPipeline.Snippet[] { POSITION_COLOR_SHADER, COLOR_WRITE
/*  81 */       }).withLocation("jm/pipeline/grid_lines")
/*  82 */     .withVertexFormat(class_290.field_1576, VertexFormat.class_5596.field_29344)
/*  83 */     .withDepthTestFunction(DepthTestFunction.LEQUAL_DEPTH_TEST)
/*  84 */     .withCull(false)
/*  85 */     .withBlend(BlendFunction.TRANSLUCENT)
/*  86 */     .build();
/*     */   
/*  88 */   public static final RenderPipeline REGION_DEFAULT_RENDER_PIPELINE = RenderPipeline.builder(new RenderPipeline.Snippet[] { POSITION_TEX_COLOR_SHADER, COLOR_WRITE
/*  89 */       }).withLocation("jm/pipeline/pos_tex_color")
/*  90 */     .withVertexFormat(class_290.field_1575, VertexFormat.class_5596.field_27382)
/*  91 */     .withDepthTestFunction(DepthTestFunction.LEQUAL_DEPTH_TEST)
/*  92 */     .withBlend(BlendFunction.TRANSLUCENT)
/*  93 */     .withCull(false)
/*  94 */     .build();
/*     */   
/*  96 */   public static final RenderPipeline MINIMAP_CIRCLE_MASK_PIPELINE = RenderPipeline.builder(new RenderPipeline.Snippet[] { POSITION_TEX_SHADER, DEPTH_WRITE
/*  97 */       }).withLocation("jm/pipeline/minimap_circle_mask")
/*  98 */     .withVertexFormat(class_290.field_1585, VertexFormat.class_5596.field_27382)
/*  99 */     .withDepthTestFunction(DepthTestFunction.GREATER_DEPTH_TEST)
/* 100 */     .withCull(false)
/* 101 */     .build();
/*     */   
/* 103 */   public static final RenderPipeline MINIMAP_RECTANGLE_MASK_RENDER_PIPELINE = RenderPipeline.builder(new RenderPipeline.Snippet[] { POSITION_COLOR_SHADER, DEPTH_WRITE
/* 104 */       }).withLocation("jm/pipeline/minimap_rectangle_mask")
/* 105 */     .withVertexFormat(class_290.field_1576, VertexFormat.class_5596.field_27382)
/* 106 */     .withDepthTestFunction(DepthTestFunction.GREATER_DEPTH_TEST)
/* 107 */     .withCull(false)
/* 108 */     .build();
/*     */   
/* 110 */   public static final RenderPipeline RECTANGLE_RENDER_PIPELINE = RenderPipeline.builder(new RenderPipeline.Snippet[] { POSITION_COLOR_SHADER, COLOR_DEPTH_WRITE
/* 111 */       }).withLocation("jm/pipeline/rectangle")
/* 112 */     .withVertexFormat(class_290.field_1576, VertexFormat.class_5596.field_27382)
/* 113 */     .withDepthTestFunction(DepthTestFunction.LEQUAL_DEPTH_TEST)
/* 114 */     .withCull(false)
/* 115 */     .withBlend(BlendFunction.TRANSLUCENT)
/* 116 */     .build();
/*     */   
/* 118 */   public static final RenderPipeline POSITION_TEX_PIPELINE = RenderPipeline.builder(new RenderPipeline.Snippet[] { POSITION_TEX_SHADER, COLOR_WRITE
/* 119 */       }).withLocation("jm/pipeline/position_tex")
/* 120 */     .withBlend(BLEND_SRC_MINUS_SRS_ONE_ZERO)
/* 121 */     .withVertexFormat(class_290.field_1585, VertexFormat.class_5596.field_27382)
/* 122 */     .build();
/*     */   
/* 124 */   public static final RenderPipeline ICON_RENDER_PIPELINE = RenderPipeline.builder(new RenderPipeline.Snippet[] { POSITION_TEX_COLOR_SHADER, COLOR_DEPTH_WRITE
/* 125 */       }).withLocation("jm/pipeline/icon")
/* 126 */     .withVertexFormat(class_290.field_1575, VertexFormat.class_5596.field_27382)
/* 127 */     .withDepthTestFunction(DepthTestFunction.LEQUAL_DEPTH_TEST)
/* 128 */     .withBlend(BLEND_SRC_MINUS_SRS_ONE_ZERO)
/* 129 */     .withCull(false)
/* 130 */     .build();
/*     */   
/* 132 */   public static final RenderPipeline ICON_UNMASKED_RENDER_PIPELINE = RenderPipeline.builder(new RenderPipeline.Snippet[] { POSITION_TEX_COLOR_SHADER, COLOR_WRITE
/* 133 */       }).withLocation("jm/pipeline/icon_unmasked")
/* 134 */     .withVertexFormat(class_290.field_1575, VertexFormat.class_5596.field_27382)
/* 135 */     .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
/* 136 */     .withBlend(BLEND_SRC_MINUS_SRS_ONE_ZERO)
/* 137 */     .withCull(false)
/* 138 */     .build();
/*     */   
/* 140 */   public static final RenderPipeline POLYGON_WITH_TEXTURE_RENDER_PIPELINE = RenderPipeline.builder(new RenderPipeline.Snippet[] { POSITION_TEX_COLOR_SHADER, COLOR_WRITE
/* 141 */       }).withLocation("jm/pipeline/polygon_tex")
/* 142 */     .withVertexFormat(class_290.field_1575, VertexFormat.class_5596.field_27379)
/* 143 */     .withDepthTestFunction(DepthTestFunction.LEQUAL_DEPTH_TEST)
/* 144 */     .withBlend(BLEND_SRC_MINUS_SRS_ONE_ZERO)
/* 145 */     .withCull(false)
/* 146 */     .build();
/*     */   
/* 148 */   public static final RenderPipeline POLYGON_POS_PIPELINE = RenderPipeline.builder(new RenderPipeline.Snippet[] { POSITION_COLOR_SHADER, COLOR_WRITE
/* 149 */       }).withLocation("jm/pipeline/polygon")
/* 150 */     .withVertexFormat(class_290.field_1576, VertexFormat.class_5596.field_27379)
/* 151 */     .withDepthTestFunction(DepthTestFunction.LEQUAL_DEPTH_TEST)
/* 152 */     .withCull(false)
/* 153 */     .withBlend(BLEND_SRC_MINUS_SRS_ONE_ZERO)
/* 154 */     .build();
/*     */   
/* 156 */   public static final RenderPipeline POLYGON_STROKE_PIPELINE = RenderPipeline.builder(new RenderPipeline.Snippet[] { POSITION_COLOR_SHADER, COLOR_WRITE
/* 157 */       }).withLocation("jm/pipeline/polygon_stroke")
/* 158 */     .withVertexFormat(class_290.field_1576, VertexFormat.class_5596.field_27380)
/* 159 */     .withDepthTestFunction(DepthTestFunction.LEQUAL_DEPTH_TEST)
/* 160 */     .withCull(false)
/* 161 */     .withBlend(BLEND_SRC_MINUS_SRS_ONE_ZERO)
/* 162 */     .build();
/*     */   
/* 164 */   public static final RenderPipeline WAYPOINT_BEAM_RENDER_PIPELINE = RenderPipeline.builder(new RenderPipeline.Snippet[] { POSITION_COLOR_TEX_LIGHTMAP_SHADER
/* 165 */       }).withLocation("jm/pipeline/waypoint_beam")
/* 166 */     .withVertexFormat(class_290.field_20888, VertexFormat.class_5596.field_27382)
/* 167 */     .withDepthTestFunction(DepthTestFunction.LEQUAL_DEPTH_TEST)
/* 168 */     .withDepthWrite(false)
/* 169 */     .withCull(true)
/* 170 */     .withBlend(BlendFunction.TRANSLUCENT)
/* 171 */     .build();
/*     */ 
/*     */   
/* 174 */   public static final RenderPipeline GRAYSCALE_RENDER_PIPELINE = RenderPipeline.builder(new RenderPipeline.Snippet[] { MATRICES_SNIPPET, MATRICES_COLOR_SNIPPET
/* 175 */       }).withLocation("jm/pipeline/gray_scale")
/* 176 */     .withVertexShader("core/position_tex_color")
/* 177 */     .withFragmentShader(class_2960.method_60654("journeymap:core/grayscale"))
/* 178 */     .withSampler("Sampler0")
/* 179 */     .withVertexFormat(class_290.field_1575, VertexFormat.class_5596.field_27382)
/* 180 */     .withDepthTestFunction(DepthTestFunction.LEQUAL_DEPTH_TEST)
/* 181 */     .withBlend(BlendFunction.TRANSLUCENT)
/* 182 */     .withCull(false)
/* 183 */     .build();
/*     */   
/* 185 */   public static final RenderPipeline SEPIA_1_RENDER_PIPELINE = RenderPipeline.builder(new RenderPipeline.Snippet[] { MATRICES_SNIPPET, MATRICES_COLOR_SNIPPET
/* 186 */       }).withLocation("jm/pipeline/sepia_1")
/* 187 */     .withVertexShader("core/position_tex_color")
/* 188 */     .withFragmentShader(class_2960.method_60654("journeymap:core/sepia_1"))
/* 189 */     .withSampler("Sampler0")
/* 190 */     .withVertexFormat(class_290.field_1575, VertexFormat.class_5596.field_27382)
/* 191 */     .withDepthTestFunction(DepthTestFunction.LEQUAL_DEPTH_TEST)
/* 192 */     .withBlend(BlendFunction.TRANSLUCENT)
/* 193 */     .withCull(false)
/* 194 */     .build();
/*     */   
/* 196 */   public static final RenderPipeline SEPIA_2_RENDER_PIPELINE = RenderPipeline.builder(new RenderPipeline.Snippet[] { MATRICES_SNIPPET, MATRICES_COLOR_SNIPPET
/* 197 */       }).withLocation("jm/pipeline/sepia_2")
/* 198 */     .withVertexShader("core/position_tex_color")
/* 199 */     .withFragmentShader(class_2960.method_60654("journeymap:core/sepia_2"))
/* 200 */     .withSampler("Sampler0")
/* 201 */     .withVertexFormat(class_290.field_1575, VertexFormat.class_5596.field_27382)
/* 202 */     .withDepthTestFunction(DepthTestFunction.LEQUAL_DEPTH_TEST)
/* 203 */     .withBlend(BlendFunction.TRANSLUCENT)
/* 204 */     .withCull(false)
/* 205 */     .build();
/*     */   
/* 207 */   public static final RenderPipeline SEPIA_3_RENDER_PIPELINE = RenderPipeline.builder(new RenderPipeline.Snippet[] { MATRICES_SNIPPET, MATRICES_COLOR_SNIPPET
/* 208 */       }).withLocation("jm/pipeline/sepia_3")
/* 209 */     .withVertexShader("core/position_tex_color")
/* 210 */     .withFragmentShader(class_2960.method_60654("journeymap:core/sepia_3"))
/* 211 */     .withSampler("Sampler0")
/* 212 */     .withVertexFormat(class_290.field_1575, VertexFormat.class_5596.field_27382)
/* 213 */     .withDepthTestFunction(DepthTestFunction.LEQUAL_DEPTH_TEST)
/* 214 */     .withBlend(BlendFunction.TRANSLUCENT)
/* 215 */     .withCull(false)
/* 216 */     .build();
/*     */   static {
/* 218 */     REGION_SHADERS_MAP = (Object2ObjectOpenHashMap<String, RenderPipeline>)class_156.method_656(() -> {
/*     */           Object2ObjectOpenHashMap<String, RenderPipeline> map = new Object2ObjectOpenHashMap();
/*     */           map.put("default", REGION_DEFAULT_RENDER_PIPELINE);
/*     */           map.put("grayscale", GRAYSCALE_RENDER_PIPELINE);
/*     */           map.put("sepia_1", SEPIA_1_RENDER_PIPELINE);
/*     */           map.put("sepia_2", SEPIA_2_RENDER_PIPELINE);
/*     */           map.put("sepia_3", SEPIA_3_RENDER_PIPELINE);
/*     */           return map;
/*     */         });
/*     */   }
/*     */   
/*     */   public static final Object2ObjectOpenHashMap<String, RenderPipeline> REGION_SHADERS_MAP;
/*     */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\render\Pipelines.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */