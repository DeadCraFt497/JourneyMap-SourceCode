/*     */ package journeymap.client.render;
/*     */ 
/*     */ import com.mojang.blaze3d.textures.GpuTexture;
/*     */ import net.minecraft.class_10868;
/*     */ import net.minecraft.class_4668;
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
/*     */ public class IconStateShard
/*     */   extends class_4668.class_5939
/*     */ {
/*     */   private final int textureId;
/*     */   private final boolean blur;
/*     */   private final boolean clamp;
/*     */   
/*     */   public IconStateShard(GpuTexture tex) {
/* 222 */     this(tex, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public IconStateShard(GpuTexture texture, boolean blur) {
/* 227 */     this(texture, blur, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public IconStateShard(GpuTexture texture, boolean blur, boolean clamp) {
/* 232 */     super(() -> {
/*     */           RenderWrapper.bindTexture(((class_10868)texture).method_68427()); RenderWrapper.texParameter(3553, 10241, blur ? 9729 : 9728);
/*     */           RenderWrapper.texParameter(3553, 10240, blur ? 9729 : 9728);
/*     */           RenderWrapper.texParameter(3553, 10242, clamp ? 33071 : 10497);
/*     */           RenderWrapper.texParameter(3553, 10243, clamp ? 33071 : 10497);
/*     */           RenderWrapper.setShaderTexture(0, texture);
/*     */         }() -> {
/*     */         
/*     */         });
/* 241 */     this.blur = blur;
/* 242 */     this.clamp = clamp;
/* 243 */     this.textureId = ((class_10868)texture).method_68427();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 250 */     return this.field_21363 + "[" + this.field_21363 + "] blur:" + this.textureId + " clamp:" + this.blur;
/*     */   }
/*     */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\render\JMRenderTypes$IconStateShard.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */