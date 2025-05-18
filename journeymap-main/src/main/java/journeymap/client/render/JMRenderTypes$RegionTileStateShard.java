/*     */ package journeymap.client.render;
/*     */ 
/*     */ import com.mojang.blaze3d.textures.GpuTexture;
/*     */ import journeymap.api.client.impl.ClientAPI;
/*     */ import journeymap.api.v2.client.display.Context;
/*     */ import journeymap.client.JourneymapClient;
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
/*     */ public class RegionTileStateShard
/*     */   extends class_4668.class_5939
/*     */ {
/*     */   private final int textureId;
/*     */   
/*     */   public RegionTileStateShard(GpuTexture texture) {
/* 175 */     super(() -> {
/*     */           RenderWrapper.bindTexture(((class_10868)texture).method_68427());
/*     */ 
/*     */           
/*     */           boolean blur = false;
/*     */           
/*     */           if ((ClientAPI.INSTANCE.getLastUIState()).ui == Context.UI.Fullscreen) {
/*     */             blur = ((JourneymapClient.getInstance().getFullMapProperties()).zoomLevel.get().intValue() < 512);
/*     */           } else if ((ClientAPI.INSTANCE.getLastUIState()).ui == Context.UI.Minimap) {
/*     */             blur = ((JourneymapClient.getInstance().getActiveMiniMapProperties()).zoomLevel.get().intValue() < 512);
/*     */           } 
/*     */           
/*     */           int mipmapLevels = (JourneymapClient.getInstance().getCoreProperties()).mipmapLevels.get().intValue();
/*     */           
/* 189 */           int mag = (mipmapLevels == 0 && blur) ? 9729 : 9984;
/* 190 */           int min = (mipmapLevels == 0 && blur) ? 9729 : 9728;
/*     */           
/*     */           RenderWrapper.bindTexture(((class_10868)texture).method_68427());
/*     */           RenderWrapper.texParameter(3553, 10241, mag);
/*     */           RenderWrapper.texParameter(3553, 10240, min);
/*     */           RenderWrapper.texParameter(3553, 10242, 33071);
/*     */           RenderWrapper.texParameter(3553, 10243, 33071);
/*     */           RenderWrapper.texParameter(3553, 33085, mipmapLevels);
/*     */           RenderWrapper.texParameter(3553, 33082, 0);
/*     */           RenderWrapper.texParameter(3553, 33083, mipmapLevels);
/*     */           RenderWrapper.texParameter(3553, 34049, 0);
/*     */           RenderWrapper.setShaderTexture(0, texture);
/*     */         }() -> {
/*     */         
/*     */         });
/* 205 */     this.textureId = ((class_10868)texture).method_68427();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 210 */     return this.field_21363 + "[" + this.field_21363 + ")]";
/*     */   }
/*     */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\render\JMRenderTypes$RegionTileStateShard.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */