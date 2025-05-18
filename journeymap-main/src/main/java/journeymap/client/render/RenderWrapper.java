/*     */ package journeymap.client.render;
/*     */ 
/*     */ import com.google.common.collect.Queues;
/*     */ import com.mojang.blaze3d.opengl.GlConst;
/*     */ import com.mojang.blaze3d.opengl.GlStateManager;
/*     */ import com.mojang.blaze3d.platform.DestFactor;
/*     */ import com.mojang.blaze3d.platform.SourceFactor;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import com.mojang.blaze3d.textures.GpuTexture;
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.concurrent.ConcurrentLinkedQueue;
/*     */ import journeymap.client.JourneymapClient;
/*     */ import journeymap.client.log.JMLogger;
/*     */ import net.minecraft.class_10366;
/*     */ import org.joml.Matrix4f;
/*     */ import org.joml.Matrix4fStack;
/*     */ import org.lwjgl.opengl.GL11C;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RenderWrapper
/*     */ {
/*  25 */   private static final ConcurrentLinkedQueue<RenderQueue> renderQueue = Queues.newConcurrentLinkedQueue();
/*     */   
/*     */   public static final int GL_TEXTURE_2D = 3553;
/*     */   
/*     */   public static final int GL_NEAREST = 9728;
/*     */   
/*     */   public static final int GL_LINEAR = 9729;
/*     */   
/*     */   public static final int GL_TEXTURE_MAG_FILTER = 10240;
/*     */   
/*     */   public static final int GL_TEXTURE_MIN_FILTER = 10241;
/*     */   
/*     */   public static final int GL_NEAREST_MIPMAP_NEAREST = 9984;
/*     */   
/*     */   public static final int GL_NEAREST_MIPMAP_LINEAR = 9986;
/*     */   
/*     */   public static final int GL_LINEAR_MIPMAP_LINEAR = 9987;
/*     */   
/*     */   public static final int GL_TEXTURE_WRAP_S = 10242;
/*     */   public static final int GL_TEXTURE_WRAP_T = 10243;
/*     */   public static final int GL_REPEAT = 10497;
/*     */   public static final int GL_SRC_ALPHA = 770;
/*     */   public static final int GL_ONE_MINUS_SRC_ALPHA = 771;
/*     */   public static final int GL_ZERO = 0;
/*     */   public static final int GL_DEPTH_BUFFER_BIT = 256;
/*     */   public static final int GL_LEQUAL = 515;
/*     */   public static final int GL_GREATER = 516;
/*     */   public static final int GL_GEQUAL = 518;
/*     */   public static final int GL_NO_ERROR = 0;
/*     */   public static final int GL_VIEWPORT = 2978;
/*     */   public static final int UNSIGNED_INT_8_8_8_8_REV = 33639;
/*     */   public static final int GL_UNSIGNED_BYTE = 5121;
/*     */   public static final int GL_BGRA = 32993;
/*     */   public static final int GL_RGBA = 6408;
/*     */   public static final int GL_CLAMP_TO_EDGE = 33071;
/*     */   public static final int GL_TEXTURE_MAX_LEVEL = 33085;
/*     */   public static final int GL_TEXTURE_MAX_LOD = 33083;
/*     */   public static final int GL_TEXTURE_MIN_LOD = 33082;
/*     */   public static final int GL_MIRRORED_REPEAT = 33648;
/*     */   public static final int GL_TEXTURE_LOD_BIAS = 34049;
/*  65 */   public static final boolean errorCheck = (JourneymapClient.getInstance().getCoreProperties()).glErrorChecking.get().booleanValue();
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setColor4f(float red, float green, float blue, float alpha) {
/*  70 */     RenderSystem.setShaderColor(red, green, blue, alpha);
/*  71 */     checkGLError("setColor4f");
/*     */   }
/*     */ 
/*     */   
/*     */   public static void enableBlend() {
/*  76 */     GlStateManager._enableBlend();
/*  77 */     checkGLError("enableBlend");
/*     */   }
/*     */ 
/*     */   
/*     */   public static void disableBlend() {
/*  82 */     GlStateManager._disableBlend();
/*  83 */     checkGLError("disableBlend");
/*     */   }
/*     */ 
/*     */   
/*     */   public static void defaultBlendFunc() {
/*  88 */     blendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
/*  89 */     checkGLError("defaultBlendFunc");
/*     */   }
/*     */ 
/*     */   
/*     */   public static void blendFuncSeparate(SourceFactor sourceFactor, DestFactor destFactor, SourceFactor sourceFactorAlpha, DestFactor destFactorAlpha) {
/*  94 */     GlStateManager._blendFuncSeparate(
/*  95 */         GlConst.toGl(sourceFactor), 
/*  96 */         GlConst.toGl(destFactor), 
/*  97 */         GlConst.toGl(sourceFactorAlpha), 
/*  98 */         GlConst.toGl(destFactorAlpha));
/*     */   }
/*     */ 
/*     */   
/*     */   public static void enableDepthTest() {
/* 103 */     GlStateManager._enableDepthTest();
/* 104 */     checkGLError("enableDepthTest");
/*     */   }
/*     */ 
/*     */   
/*     */   public static void disableDepthTest() {
/* 109 */     GlStateManager._disableDepthTest();
/* 110 */     checkGLError("disableDepthTest");
/*     */   }
/*     */ 
/*     */   
/*     */   public static Matrix4f getProjectionMatrix() {
/* 115 */     Matrix4f projectionMatrix = RenderSystem.getProjectionMatrix();
/* 116 */     checkGLError("getProjectionMatrix");
/* 117 */     return projectionMatrix;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getError() {
/* 123 */     return GlStateManager._getError();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void texParameter(int target, int pname, int param) {
/* 129 */     GlStateManager._texParameter(target, pname, param);
/* 130 */     checkGLError("texParameter");
/*     */   }
/*     */ 
/*     */   
/*     */   public static void blendFuncSeparate(int sfactorRGB, int dfactorRGB, int sfactorAlpha, int dfactorAlpha) {
/* 135 */     GlStateManager._blendFuncSeparate(sfactorRGB, dfactorRGB, sfactorAlpha, dfactorAlpha);
/* 136 */     checkGLError("blendFuncSeparate");
/*     */   }
/*     */ 
/*     */   
/*     */   public static void blendFunc(int sfactor, int dfactor) {
/* 141 */     blendFuncSeparate(sfactor, dfactor, 1, 0);
/* 142 */     checkGLError("blendFunc");
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setShaderTexture(int index, GpuTexture tex) {
/* 147 */     RenderSystem.setShaderTexture(index, tex);
/* 148 */     checkGLError("setShaderTexture");
/*     */   }
/*     */ 
/*     */   
/*     */   public static void activeTexture(int texture) {
/* 153 */     GlStateManager._activeTexture(texture);
/* 154 */     checkGLError("activeTexture");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void bindTexture(int id) {
/* 160 */     GlStateManager._bindTexture(id);
/* 161 */     checkGLError("bindTexture");
/*     */   }
/*     */ 
/*     */   
/*     */   public static void clear(int mask) {
/* 166 */     GlStateManager._clear(mask);
/* 167 */     checkGLError("clear");
/*     */   }
/*     */ 
/*     */   
/*     */   public static void depthFunc(int func) {
/* 172 */     GlStateManager._depthFunc(func);
/* 173 */     checkGLError("depthFunc");
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setProjectionMatrix(Matrix4f matrix4f, class_10366 sorting) {
/* 178 */     RenderSystem.setProjectionMatrix(matrix4f, sorting);
/* 179 */     checkGLError("setProjectionMatrix");
/*     */   }
/*     */ 
/*     */   
/*     */   public static Matrix4fStack getModelViewStack() {
/* 184 */     Matrix4fStack stack = RenderSystem.getModelViewStack();
/* 185 */     checkGLError("getModelViewStack");
/* 186 */     return stack;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void texImage2D(int target, int level, int internalFormat, int width, int height, int border, int format, int type, IntBuffer pixels) {
/* 192 */     GlStateManager._texImage2D(target, level, internalFormat, width, height, border, format, type, pixels);
/* 193 */     checkGLError("texImage2D");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void texSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, long pixels) {
/* 199 */     GlStateManager._texSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels);
/* 200 */     checkGLError("texSubImage2D");
/*     */   }
/*     */ 
/*     */   
/*     */   public static void clearDepth(int i) {
/* 205 */     GL11C.glClearDepth(i);
/* 206 */     checkGLError("clearDepth");
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean checkGLError(String method) {
/* 211 */     if (errorCheck) {
/*     */       
/* 213 */       boolean hasError = false;
/*     */       int err;
/* 215 */       while ((err = getError()) != 0) {
/*     */         
/* 217 */         hasError = true;
/* 218 */         JMLogger.logOnce("GL Error in method: " + method + " ERROR: " + err);
/*     */       } 
/* 220 */       return hasError;
/*     */     } 
/* 222 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void recordRenderQueue(RenderQueue call) {
/* 227 */     renderQueue.add(call);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void runRenderQueue() {
/* 232 */     while (!renderQueue.isEmpty()) {
/*     */       
/* 234 */       RenderQueue queue = renderQueue.poll();
/* 235 */       queue.execute();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\render\RenderWrapper.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */