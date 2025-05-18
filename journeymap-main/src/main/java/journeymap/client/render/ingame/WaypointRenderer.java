/*     */ package journeymap.client.render.ingame;
/*     */ 
/*     */ import java.util.List;
/*     */ import journeymap.client.cartography.color.RGB;
/*     */ import journeymap.client.properties.WaypointProperties;
/*     */ import journeymap.client.render.draw.DrawStep;
/*     */ import journeymap.client.waypoint.ClientWaypointImpl;
/*     */ import journeymap.common.Journeymap;
/*     */ import journeymap.common.waypoint.WaypointGroupStore;
/*     */ import net.minecraft.class_243;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_4587;
/*     */ import net.minecraft.class_4597;
/*     */ import net.minecraft.class_898;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class WaypointRenderer
/*     */ {
/*     */   protected WaypointProperties waypointProperties;
/*  30 */   protected final class_310 minecraft = class_310.method_1551();
/*  31 */   protected class_898 renderManager = this.minecraft.method_1561();
/*     */ 
/*     */ 
/*     */   
/*     */   protected List<ClientWaypointImpl> deleteQueue;
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void render(class_332 paramclass_332);
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canDrawWaypoint(ClientWaypointImpl waypoint, String playerDim) {
/*     */     // Byte code:
/*     */     //   0: invokestatic getInstance : ()Ljourneymap/common/waypoint/WaypointGroupStore;
/*     */     //   3: aload_1
/*     */     //   4: invokevirtual getGroupId : ()Ljava/lang/String;
/*     */     //   7: invokevirtual get : (Ljava/lang/String;)Ljourneymap/common/waypoint/WaypointGroupImpl;
/*     */     //   10: astore_3
/*     */     //   11: getstatic journeymap/common/waypoint/WaypointOrigin.EXTERNAL_FORCE : Ljourneymap/common/waypoint/WaypointOrigin;
/*     */     //   14: invokevirtual getValue : ()Ljava/lang/String;
/*     */     //   17: aload_1
/*     */     //   18: invokevirtual getOrigin : ()Ljava/lang/String;
/*     */     //   21: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   24: ifne -> 99
/*     */     //   27: aload_1
/*     */     //   28: invokevirtual isEnabled : ()Z
/*     */     //   31: ifeq -> 114
/*     */     //   34: aload_3
/*     */     //   35: ifnull -> 45
/*     */     //   38: aload_3
/*     */     //   39: invokevirtual isEnabled : ()Z
/*     */     //   42: ifne -> 57
/*     */     //   45: ldc 'player_icon_display'
/*     */     //   47: aload_1
/*     */     //   48: invokevirtual getGroupId : ()Ljava/lang/String;
/*     */     //   51: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   54: ifeq -> 114
/*     */     //   57: invokestatic getInstance : ()Ljourneymap/client/JourneymapClient;
/*     */     //   60: invokevirtual getWaypointProperties : ()Ljourneymap/client/properties/WaypointProperties;
/*     */     //   63: getfield beaconEnabled : Ljourneymap/common/properties/config/BooleanField;
/*     */     //   66: invokevirtual get : ()Ljava/lang/Boolean;
/*     */     //   69: invokevirtual booleanValue : ()Z
/*     */     //   72: ifeq -> 114
/*     */     //   75: invokestatic getInstance : ()Ljourneymap/client/JourneymapClient;
/*     */     //   78: invokevirtual getStateHandler : ()Ljourneymap/client/InternalStateHandler;
/*     */     //   81: invokevirtual canShowInGameBeacons : ()Z
/*     */     //   84: ifeq -> 114
/*     */     //   87: invokestatic getInstance : ()Ljourneymap/client/JourneymapClient;
/*     */     //   90: invokevirtual getStateHandler : ()Ljourneymap/client/InternalStateHandler;
/*     */     //   93: invokevirtual isWaypointsAllowed : ()Z
/*     */     //   96: ifeq -> 114
/*     */     //   99: aload_1
/*     */     //   100: invokevirtual getDimensions : ()Ljava/util/TreeSet;
/*     */     //   103: aload_2
/*     */     //   104: invokevirtual contains : (Ljava/lang/Object;)Z
/*     */     //   107: ifeq -> 114
/*     */     //   110: iconst_1
/*     */     //   111: goto -> 115
/*     */     //   114: iconst_0
/*     */     //   115: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #38	-> 0
/*     */     //   #39	-> 11
/*     */     //   #40	-> 28
/*     */     //   #41	-> 39
/*     */     //   #42	-> 57
/*     */     //   #43	-> 75
/*     */     //   #44	-> 87
/*     */     //   #45	-> 100
/*     */     //   #39	-> 115
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	116	0	this	Ljourneymap/client/render/ingame/WaypointRenderer;
/*     */     //   0	116	1	waypoint	Ljourneymap/client/waypoint/ClientWaypointImpl;
/*     */     //   0	116	2	playerDim	Ljava/lang/String;
/*     */     //   11	105	3	group	Ljourneymap/common/waypoint/WaypointGroupImpl;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void renderWaypoint(ClientWaypointImpl waypoint, class_4587 poseStack, DrawStep.Pass pass, class_4597 buffers) {
/*  50 */     if (pass == DrawStep.Pass.Tooltip) {
/*     */       return;
/*     */     }
/*     */     
/*  54 */     if (this.renderManager == null)
/*     */     {
/*  56 */       this.renderManager = this.minecraft.method_1561();
/*     */     }
/*  58 */     float partialTicks = this.minecraft.method_61966().method_60637(true);
/*  59 */     long gameTime = this.minecraft.field_1687.method_8510();
/*  60 */     float fadeAlpha = 1.0F;
/*     */     
/*  62 */     class_243 waypointVec = waypoint.getPosition().method_1031(0.0D, 0.118D, 0.0D);
/*     */ 
/*     */     
/*  65 */     class_243 playerVec = this.minecraft.field_1724.method_19538();
/*     */ 
/*     */     
/*  68 */     double actualDistance = playerVec.method_1022(waypointVec);
/*  69 */     int maxDistance = this.waypointProperties.maxDistance.get().intValue();
/*  70 */     int minDistance = this.waypointProperties.minDistance.get().intValue();
/*  71 */     float[] rgba = RGB.floats(waypoint.getRenderColor(), fadeAlpha * 0.4F);
/*     */ 
/*     */     
/*  74 */     double viewX = this.renderManager.field_4686.method_19326().method_10216();
/*  75 */     double viewY = this.renderManager.field_4686.method_19326().method_10214();
/*  76 */     double viewZ = this.renderManager.field_4686.method_19326().method_10215();
/*     */ 
/*     */ 
/*     */     
/*  80 */     double viewDistance = actualDistance;
/*  81 */     double maxRenderDistance = (((Integer)this.minecraft.field_1690.method_42503().method_41753()).intValue() * 16);
/*     */ 
/*     */     
/*  84 */     if (maxDistance > 0 && actualDistance > maxDistance) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  90 */     if (waypoint.isDeathPoint() && this.waypointProperties.autoRemoveDeathpoints
/*  91 */       .get().booleanValue() && actualDistance < this.waypointProperties.autoRemoveDeathpointDistance
/*  92 */       .get().intValue() && actualDistance > 1.5D) {
/*     */ 
/*     */       
/*  95 */       Journeymap.getLogger().debug("Auto removing deathpoint " + String.valueOf(waypoint));
/*  96 */       this.deleteQueue.add(waypoint);
/*     */       
/*     */       return;
/*     */     } 
/* 100 */     if (WaypointGroupStore.TEMP.getGuid().equals(waypoint.getGroupId()) && (actualDistance <= this.waypointProperties.autoRemoveTempWaypoints
/* 101 */       .get().intValue() || actualDistance <= (minDistance + 4)) && actualDistance > 1.5D) {
/*     */ 
/*     */ 
/*     */       
/* 105 */       Journeymap.getLogger().debug("Auto removing temp waypoint " + String.valueOf(waypoint));
/* 106 */       this.deleteQueue.add(waypoint);
/*     */       
/*     */       return;
/*     */     } 
/* 110 */     if (minDistance > 0) {
/*     */ 
/*     */       
/* 113 */       if ((int)actualDistance <= minDistance) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 118 */       if ((int)actualDistance <= minDistance + 4)
/*     */       {
/* 120 */         fadeAlpha = Math.min((float)(actualDistance - minDistance) / 3.0F, 1.0F);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 125 */     if (viewDistance > maxRenderDistance) {
/*     */       
/* 127 */       class_243 delta = waypointVec.method_1020(playerVec).method_1029();
/* 128 */       waypointVec = playerVec.method_1031(delta.field_1352 * maxRenderDistance, delta.field_1351 * maxRenderDistance, delta.field_1350 * maxRenderDistance);
/* 129 */       viewDistance = maxRenderDistance;
/*     */     } 
/* 131 */     double scale = 0.00390625D * (viewDistance + 4.0D) / 3.0D;
/* 132 */     double shiftX = waypointVec.field_1352 - viewX;
/* 133 */     double shiftY = waypointVec.field_1351 - viewY;
/* 134 */     double shiftZ = waypointVec.field_1350 - viewZ;
/*     */     
/* 136 */     render(poseStack, pass, buffers, waypoint, partialTicks, gameTime, rgba, fadeAlpha, shiftX, shiftY, shiftZ, playerVec, waypointVec, viewDistance, actualDistance, scale);
/*     */   }
/*     */   
/*     */   protected abstract void render(class_4587 paramclass_4587, DrawStep.Pass paramPass, class_4597 paramclass_4597, ClientWaypointImpl paramClientWaypointImpl, float paramFloat1, long paramLong, float[] paramArrayOffloat, float paramFloat2, double paramDouble1, double paramDouble2, double paramDouble3, class_243 paramclass_2431, class_243 paramclass_2432, double paramDouble4, double paramDouble5, double paramDouble6);
/*     */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\render\ingame\WaypointRenderer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */