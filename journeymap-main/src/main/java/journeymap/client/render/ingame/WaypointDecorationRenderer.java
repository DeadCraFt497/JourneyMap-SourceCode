/*     */ package journeymap.client.render.ingame;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import journeymap.client.Constants;
/*     */ import journeymap.client.JourneymapClient;
/*     */ import journeymap.client.render.JMRenderTypes;
/*     */ import journeymap.client.render.draw.DrawStep;
/*     */ import journeymap.client.render.draw.DrawUtil;
/*     */ import journeymap.client.texture.TextureAccess;
/*     */ import journeymap.client.texture.TextureCache;
/*     */ import journeymap.client.waypoint.ClientWaypointImpl;
/*     */ import journeymap.client.waypoint.PlayerPoint;
/*     */ import journeymap.common.Journeymap;
/*     */ import journeymap.common.mixin.client.GameRendererInvoker;
/*     */ import journeymap.common.mixin.client.GuiGraphicsAccessor;
/*     */ import journeymap.common.waypoint.WaypointStore;
/*     */ import net.minecraft.class_1041;
/*     */ import net.minecraft.class_1043;
/*     */ import net.minecraft.class_124;
/*     */ import net.minecraft.class_1921;
/*     */ import net.minecraft.class_243;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_2960;
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_4587;
/*     */ import net.minecraft.class_4588;
/*     */ import net.minecraft.class_4597;
/*     */ import net.minecraft.class_757;
/*     */ import org.joml.Matrix4f;
/*     */ import org.joml.Matrix4fc;
/*     */ import org.joml.Quaternionf;
/*     */ import org.joml.Quaternionfc;
/*     */ import org.joml.Vector4f;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WaypointDecorationRenderer
/*     */   extends WaypointRenderer
/*     */ {
/*     */   public void render(class_332 graphics) {
/*  43 */     this.waypointProperties = JourneymapClient.getInstance().getWaypointProperties();
/*  44 */     class_4597.class_4598 buffers = ((GuiGraphicsAccessor)graphics).getBufferSource();
/*  45 */     Collection<ClientWaypointImpl> waypoints = WaypointStore.getInstance().getAll();
/*  46 */     List<ClientWaypointImpl> waypointsOrdered = waypointsToDraw(waypoints);
/*  47 */     waypointsOrdered.addAll(WaypointStore.getInstance().getPlayerPoints());
/*  48 */     graphics.method_51452();
/*  49 */     if (waypointsOrdered.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/*  53 */     class_4587 poseStack = graphics.method_51448();
/*  54 */     class_1041 mainWindow = this.minecraft.method_22683();
/*  55 */     DrawUtil.sizeDisplay(mainWindow.method_4489(), mainWindow.method_4506());
/*     */     
/*  57 */     for (DrawStep.Pass pass : DrawStep.Pass.values()) {
/*     */       
/*  59 */       if (pass != DrawStep.Pass.PreObject && pass != DrawStep.Pass.PostObject && pass != DrawStep.Pass.Tooltip) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  64 */         int zLevel = 0;
/*  65 */         for (ClientWaypointImpl waypoint : waypointsOrdered) {
/*     */ 
/*     */           
/*     */           try {
/*  69 */             zLevel++;
/*  70 */             poseStack.method_22903();
/*  71 */             poseStack.method_46416(0.0F, 0.0F, zLevel);
/*  72 */             renderWaypoint(waypoint, poseStack, pass, (class_4597)buffers);
/*  73 */             poseStack.method_22909();
/*     */           }
/*  75 */           catch (Exception t) {
/*     */             
/*  77 */             Journeymap.getLogger().error("Waypoint decoration failed to render for " + waypoint.getName() + ": ", t);
/*     */           } 
/*     */         } 
/*  80 */         buffers.method_22993();
/*     */       } 
/*     */     } 
/*  83 */     DrawUtil.sizeDisplay(mainWindow.method_4486(), mainWindow.method_4502());
/*     */   }
/*     */ 
/*     */   
/*     */   private List<ClientWaypointImpl> waypointsToDraw(Collection<ClientWaypointImpl> waypoints) {
/*  88 */     String playerDim = this.minecraft.field_1724.method_5770().method_27983().method_29177().toString();
/*  89 */     List<ClientWaypointImpl> toDraw = new ArrayList<>();
/*     */ 
/*     */ 
/*     */     
/*  93 */     double closestAngle = 0.0D;
/*  94 */     ClientWaypointImpl closestHolder = null;
/*  95 */     for (ClientWaypointImpl waypoint : waypoints) {
/*     */       
/*  97 */       if (canDrawWaypoint(waypoint, playerDim) && (
/*  98 */         !this.waypointProperties.shaderBeacon.get().booleanValue() || this.waypointProperties.showRotatingBeam
/*  99 */         .get().booleanValue() || this.waypointProperties.showStaticBeam
/* 100 */         .get().booleanValue())) {
/*     */         
/* 102 */         double angle = angleToBeacon(waypoint.getPosition());
/* 103 */         if (angle < 180.0D) {
/*     */           
/* 105 */           if (closestHolder == null || angle < closestAngle) {
/*     */             
/* 107 */             if (closestHolder != null)
/*     */             {
/* 109 */               toDraw.add(closestHolder);
/*     */             }
/* 111 */             closestHolder = waypoint;
/* 112 */             closestAngle = angle;
/*     */             
/*     */             continue;
/*     */           } 
/* 116 */           toDraw.add(waypoint);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 122 */     if (closestHolder != null)
/*     */     {
/* 124 */       toDraw.add(closestHolder);
/*     */     }
/* 126 */     return toDraw;
/*     */   }
/*     */ 
/*     */   
/*     */   private double angleToBeacon(class_243 waypointVec) {
/* 131 */     double yaw = Math.atan2(this.renderManager.field_4686.method_19326().method_10215() - waypointVec.field_1350, this.renderManager.field_4686.method_19326().method_10216() - waypointVec.field_1352);
/* 132 */     double degrees = Math.toDegrees(yaw) + 90.0D;
/* 133 */     if (degrees < 0.0D)
/*     */     {
/* 135 */       degrees += 360.0D;
/*     */     }
/* 137 */     double playerYaw = (this.minecraft.field_1719.method_5791() % 360.0F);
/* 138 */     if (playerYaw < 0.0D)
/*     */     {
/* 140 */       playerYaw += 360.0D;
/*     */     }
/* 142 */     playerYaw = Math.toRadians(playerYaw);
/* 143 */     double playerDegrees = Math.toDegrees(playerYaw);
/*     */     
/* 145 */     double angle = Math.abs(degrees - playerDegrees);
/* 146 */     if (angle > 180.0D)
/*     */     {
/* 148 */       angle -= 180.0D;
/*     */     }
/*     */     
/* 151 */     return angle;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void render(class_4587 poseStack, DrawStep.Pass pass, class_4597 buffers, ClientWaypointImpl waypoint, float partialTicks, long gameTime, float[] rgba, float fadeAlpha, double shiftX, double shiftY, double shiftZ, class_243 playerVec, class_243 waypointVec, double viewDistance, double actualDistance, double scale) {
/* 156 */     class_757 gameRenderer = this.minecraft.field_1773;
/* 157 */     Vector4f position = new Vector4f((float)shiftX, (float)shiftY, (float)shiftZ, 1.0F);
/* 158 */     double angle = angleToBeacon(waypoint.getPosition());
/* 159 */     Quaternionf rotation = new Quaternionf();
/* 160 */     this.renderManager.field_4686.method_23767().conjugate(rotation);
/* 161 */     position.rotate((Quaternionfc)rotation);
/* 162 */     position.rotateY(3.1415927F);
/* 163 */     if (position.z <= 0.0F) {
/*     */       return;
/*     */     }
/*     */     
/* 167 */     Matrix4f projection = gameRenderer.method_22973(((GameRendererInvoker)gameRenderer).invokeGetFov(this.renderManager.field_4686, partialTicks, true));
/* 168 */     position.mulProject((Matrix4fc)projection);
/*     */     
/* 170 */     class_1041 mainWindow = this.minecraft.method_22683();
/* 171 */     int halfWidth = mainWindow.method_4489() / 2;
/* 172 */     int halfHeight = mainWindow.method_4506() / 2;
/* 173 */     double x = (position.x * halfWidth + halfWidth);
/* 174 */     double y = (position.y * halfHeight + halfHeight);
/*     */     
/* 176 */     if (pass == DrawStep.Pass.TextBG || pass == DrawStep.Pass.Text) {
/*     */ 
/*     */       
/* 179 */       if (actualDistance > 0.5D && (angle < this.waypointProperties.autoHideLabelAngle
/* 180 */         .get().intValue() || !this.waypointProperties.autoHideLabel.get().booleanValue()))
/*     */       {
/* 182 */         renderNameTag(waypoint, poseStack, pass, buffers, x, y, fadeAlpha, actualDistance);
/*     */       }
/*     */ 
/*     */       
/* 186 */       if (actualDistance > 0.5D && ((waypoint
/* 187 */         .showDeviation() && angle < this.waypointProperties.autoHideLabelAngle.get().intValue()) || (
/* 188 */         !this.waypointProperties.autoHideLabel.get().booleanValue() && waypoint.showDeviation())))
/*     */       {
/* 190 */         renderDeviation(waypoint, poseStack, pass, buffers, x, y, fadeAlpha, playerVec, waypointVec);
/*     */       }
/*     */     } 
/*     */     
/* 194 */     if (pass == DrawStep.Pass.Object && actualDistance > 0.1D && (!this.waypointProperties.autoHideIcon.get().booleanValue() || angle < this.waypointProperties.autoHideIconAngle.get().intValue()))
/*     */     {
/*     */       
/* 197 */       renderIcon(waypoint, poseStack, buffers, x, y, fadeAlpha);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void renderIcon(ClientWaypointImpl waypoint, class_4587 poseStack, class_4597 buffers, double iconX, double iconY, float alpha) {
/*     */     class_1043 texture;
/*     */     double width, height;
/* 204 */     int scale = this.waypointProperties.textureSmall.get().booleanValue() ? 2 : 4;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 209 */     if (waypoint instanceof PlayerPoint) { PlayerPoint playerPoint = (PlayerPoint)waypoint;
/*     */       
/* 211 */       texture = playerPoint.getTexture();
/* 212 */       width = (8 * scale);
/* 213 */       height = (8 * scale); }
/*     */     
/*     */     else
/*     */     
/* 217 */     { class_2960 location = waypoint.getTextureResource();
/* 218 */       texture = TextureCache.getWaypointIcon(location);
/* 219 */       if (texture == null)
/*     */       {
/* 221 */         texture = TextureCache.getWaypointIcon(TextureCache.Waypoint);
/*     */       }
/* 223 */       width = (((TextureAccess)texture).journeymap$getWidth() * scale);
/* 224 */       height = (((TextureAccess)texture).journeymap$getHeight() * scale); }
/*     */ 
/*     */     
/* 227 */     double drawX = iconX - (int)(width / 2.0D);
/* 228 */     double drawY = iconY - (int)(height / 2.0D);
/* 229 */     class_1921 renderType = JMRenderTypes.getIconNoBlur(texture.method_68004());
/* 230 */     class_4588 vertexBuilder = buffers.getBuffer(renderType);
/* 231 */     DrawUtil.drawQuad(poseStack, vertexBuilder, waypoint.getIconColor().intValue(), alpha, drawX, drawY, width, height, 0.0D, false);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderWaypointLabel(String label, ClientWaypointImpl waypoint, class_4587 poseStack, DrawStep.Pass pass, class_4597 buffers, double labelX, double labelY, float alpha) {
/* 236 */     float fontScale = this.waypointProperties.fontScale.get().floatValue();
/* 237 */     DrawUtil.drawBatchLabel(poseStack, (class_2561)class_2561.method_43470(label), pass, buffers, labelX, labelY, DrawUtil.HAlign.Center, DrawUtil.VAlign.Above, -16777216, 0.75F * alpha, waypoint.getSafeColor().intValue(), alpha, fontScale, false, 0.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderNameTag(ClientWaypointImpl waypoint, class_4587 poseStack, DrawStep.Pass pass, class_4597 buffers, double labelX, double labelY, float alpha, double actualDistance) {
/* 242 */     String distanceLabel = Constants.getString("jm.waypoint.distance_meters", new Object[] { "%1.0f" });
/* 243 */     String label = waypoint.getDisplayName();
/* 244 */     boolean showName = (this.waypointProperties.showName.get().booleanValue() && label != null && label.length() > 0);
/* 245 */     boolean showDistance = this.waypointProperties.showDistance.get().booleanValue();
/* 246 */     if (showName || showDistance) {
/*     */ 
/*     */ 
/*     */       
/* 250 */       StringBuilder sb = new StringBuilder();
/*     */       
/* 252 */       if (this.waypointProperties.boldLabel.get().booleanValue())
/*     */       {
/* 254 */         sb.append(class_124.field_1067);
/*     */       }
/* 256 */       if (showName)
/*     */       {
/* 258 */         sb.append(label);
/*     */       }
/* 260 */       if (showName && showDistance)
/*     */       {
/* 262 */         sb.append(" ");
/*     */       }
/* 264 */       if (showDistance)
/*     */       {
/* 266 */         sb.append(String.format(distanceLabel, new Object[] { Double.valueOf(actualDistance) }));
/*     */       }
/*     */       
/* 269 */       if (sb.length() > 0) {
/*     */         
/* 271 */         label = sb.toString();
/*     */         
/* 273 */         labelY = labelY - (((TextureAccess)waypoint.getTexture()).journeymap$getHeight() >> 1) - 8.0D;
/* 274 */         renderWaypointLabel(label, waypoint, poseStack, pass, buffers, labelX, labelY, alpha);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void renderDeviation(ClientWaypointImpl waypoint, class_4587 poseStack, DrawStep.Pass pass, class_4597 buffers, double labelX, double labelY, float alpha, class_243 playerVec, class_243 waypointVec) {
/* 282 */     StringBuilder sb = new StringBuilder();
/* 283 */     class_243 vecTo = playerVec.method_1035(waypointVec);
/* 284 */     if (this.waypointProperties.boldLabel.get().booleanValue())
/*     */     {
/* 286 */       sb.append(class_124.field_1067);
/*     */     }
/* 288 */     sb.append(String.format("x:%d, y:%d, z:%d", new Object[] { Integer.valueOf((int)vecTo.field_1352), Integer.valueOf((int)vecTo.field_1351), Integer.valueOf((int)vecTo.field_1350) }));
/*     */     
/* 290 */     labelY = labelY + (((TextureAccess)waypoint.getTexture()).journeymap$getHeight() >> 1) + 35.0D;
/* 291 */     renderWaypointLabel(sb.toString(), waypoint, poseStack, pass, buffers, labelX, labelY, alpha);
/*     */   }
/*     */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\render\ingame\WaypointDecorationRenderer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */