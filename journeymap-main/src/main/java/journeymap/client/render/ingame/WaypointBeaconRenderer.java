/*     */ package journeymap.client.render.ingame;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import journeymap.client.JourneymapClient;
/*     */ import journeymap.client.log.JMLogger;
/*     */ import journeymap.client.render.JMRenderTypes;
/*     */ import journeymap.client.render.draw.DrawStep;
/*     */ import journeymap.client.render.draw.DrawUtil;
/*     */ import journeymap.client.waypoint.ClientWaypointImpl;
/*     */ import journeymap.common.Journeymap;
/*     */ import journeymap.common.waypoint.WaypointStore;
/*     */ import net.minecraft.class_1921;
/*     */ import net.minecraft.class_243;
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_3532;
/*     */ import net.minecraft.class_4587;
/*     */ import net.minecraft.class_4588;
/*     */ import net.minecraft.class_4597;
/*     */ import net.minecraft.class_7833;
/*     */ import org.joml.Quaternionfc;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WaypointBeaconRenderer
/*     */   extends WaypointRenderer
/*     */ {
/*     */   public void render(class_332 graphics) {
/*  29 */     this.waypointProperties = JourneymapClient.getInstance().getWaypointProperties();
/*  30 */     String playerDim = this.minecraft.field_1724.method_5770().method_27983().method_29177().toString();
/*  31 */     class_4597.class_4598 buffers = this.minecraft.method_22940().method_23000();
/*     */     
/*     */     try {
/*  34 */       Collection<ClientWaypointImpl> waypoints = new ArrayList<>(WaypointStore.getInstance().getAll());
/*  35 */       this.deleteQueue = new ArrayList<>();
/*     */       
/*  37 */       for (ClientWaypointImpl waypoint : waypoints) {
/*     */         
/*  39 */         if (canDrawWaypoint(waypoint, playerDim) && (
/*  40 */           !this.waypointProperties.shaderBeacon.get().booleanValue() || this.waypointProperties.showRotatingBeam
/*  41 */           .get().booleanValue() || this.waypointProperties.showStaticBeam
/*  42 */           .get().booleanValue())) {
/*     */           try
/*     */           {
/*     */             
/*  46 */             renderWaypoint(waypoint, graphics.method_51448(), DrawStep.Pass.PreObject, (class_4597)buffers);
/*     */           }
/*  48 */           catch (Exception t)
/*     */           {
/*  50 */             Journeymap.getLogger().error("Waypoint beacon failed to render for " + waypoint.getName() + ": ", t);
/*     */           }
/*     */         
/*     */         }
/*     */       } 
/*  55 */     } catch (Throwable t) {
/*     */       
/*  57 */       JMLogger.throwLogOnce("Error rendering all waypoints. Thread:" + Thread.currentThread().getName(), t);
/*     */     } 
/*     */     
/*  60 */     this.deleteQueue.forEach(wp -> WaypointStore.getInstance().remove(wp, true));
/*  61 */     this.deleteQueue.clear();
/*  62 */     buffers.method_22993();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void render(class_4587 poseStack, DrawStep.Pass pass, class_4597 buffers, ClientWaypointImpl waypoint, float partialTicks, long gameTime, float[] rgba, float fadeAlpha, double shiftX, double shiftY, double shiftZ, class_243 playerVec, class_243 waypointVec, double viewDistance, double actualDistance, double scale) {
/*  69 */     boolean showStaticInnerBeam = this.waypointProperties.showStaticBeam.get().booleanValue();
/*  70 */     boolean showRotatingOuterBeam = this.waypointProperties.showRotatingBeam.get().booleanValue();
/*     */     
/*  72 */     if (!showStaticInnerBeam && !showRotatingOuterBeam) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  78 */     poseStack.method_22903();
/*  79 */     poseStack.method_22904(shiftX, -180.0D, shiftZ);
/*  80 */     renderBeamSegment(poseStack, buffers, partialTicks, gameTime, 1, 360, rgba, 0.2F, 0.25F, showStaticInnerBeam, showRotatingOuterBeam);
/*  81 */     poseStack.method_22909();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderBeamSegment(class_4587 poseStack, class_4597 buffer, float partialTicks, long gameTime, int yOffset, int height, float[] colors, float beamRadius, float glowRadius, boolean showStaticInnerBeam, boolean showRotatingOuterBeam) {
/*  88 */     float texScale = 1.0F;
/*  89 */     int heightOffset = yOffset + height;
/*  90 */     float rotation = (float)Math.floorMod(gameTime, 40L) + partialTicks;
/*  91 */     float texOffset = -((float)-gameTime * 0.2F - class_3532.method_15375((float)-gameTime * 0.1F)) * 0.6F;
/*     */     
/*  93 */     float red = colors[0];
/*  94 */     float blue = colors[1];
/*  95 */     float green = colors[2];
/*  96 */     float alpha = colors[3];
/*     */     
/*  98 */     class_4588 beamBuffer = buffer.getBuffer(class_1921.method_23592(JMRenderTypes.WAYPOINT_DEFAULT_BEAM, true));
/*     */     
/* 100 */     poseStack.method_22903();
/*     */     
/* 102 */     if (!showStaticInnerBeam)
/*     */     {
/* 104 */       poseStack.method_22907((Quaternionfc)class_7833.field_40716.rotationDegrees(rotation * 2.25F - 45.0F));
/*     */     }
/* 106 */     float V2 = -1.0F + texOffset;
/* 107 */     float innerV1 = height * texScale * 0.5F / beamRadius + V2;
/*     */     
/* 109 */     renderPart(poseStack, beamBuffer, red, blue, green, alpha, yOffset, heightOffset, 0.0F, beamRadius, beamRadius, 0.0F, -beamRadius, 0.0F, 0.0F, -beamRadius, 0.0F, 1.0F, innerV1, V2);
/* 110 */     poseStack.method_22909();
/* 111 */     float outerV1 = height * texScale + V2;
/*     */     
/* 113 */     poseStack.method_22903();
/*     */     
/* 115 */     if (showRotatingOuterBeam)
/*     */     {
/* 117 */       poseStack.method_22907((Quaternionfc)class_7833.field_40716.rotationDegrees(rotation * 2.25F - 45.0F));
/*     */     }
/* 119 */     renderPart(poseStack, beamBuffer, red, blue, green, alpha, yOffset, heightOffset, -glowRadius, -glowRadius, glowRadius, -glowRadius, -glowRadius, glowRadius, glowRadius, glowRadius, 0.0F, 1.0F, outerV1, V2);
/* 120 */     poseStack.method_22909();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void renderPart(class_4587 poseStack, class_4588 buffer, float red, float green, float blue, float alpha, int yMin, int yMax, float p1, float p2, float p3, float p4, float p5, float p6, float p7, float p8, float u1, float u2, float v1, float v2) {
/* 127 */     addQuad(poseStack, buffer, red, green, blue, alpha, yMin, yMax, p1, p2, p3, p4, u1, u2, v1, v2);
/* 128 */     addQuad(poseStack, buffer, red, green, blue, alpha, yMin, yMax, p7, p8, p5, p6, u1, u2, v1, v2);
/* 129 */     addQuad(poseStack, buffer, red, green, blue, alpha, yMin, yMax, p3, p4, p7, p8, u1, u2, v1, v2);
/* 130 */     addQuad(poseStack, buffer, red, green, blue, alpha, yMin, yMax, p5, p6, p1, p2, u1, u2, v1, v2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void addQuad(class_4587 poseStack, class_4588 bufferIn, float red, float green, float blue, float alpha, int yMin, int yMax, float x1, float z1, float x2, float z2, float u1, float u2, float v1, float v2) {
/* 136 */     DrawUtil.addVertexUV(poseStack, bufferIn, red, green, blue, alpha, yMax, x1, z1, u2, v1);
/* 137 */     DrawUtil.addVertexUV(poseStack, bufferIn, red, green, blue, alpha, yMin, x1, z1, u2, v2);
/* 138 */     DrawUtil.addVertexUV(poseStack, bufferIn, red, green, blue, alpha, yMin, x2, z2, u1, v2);
/* 139 */     DrawUtil.addVertexUV(poseStack, bufferIn, red, green, blue, alpha, yMax, x2, z2, u1, v1);
/*     */   }
/*     */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\render\ingame\WaypointBeaconRenderer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */