/*    */ package journeymap.client.render.draw;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import java.util.List;
/*    */ import journeymap.client.JourneymapClient;
/*    */ import journeymap.client.data.DataCache;
/*    */ import journeymap.client.render.map.Renderer;
/*    */ import journeymap.client.waypoint.ClientWaypointImpl;
/*    */ import journeymap.common.Journeymap;
/*    */ import journeymap.common.log.LogFormatter;
/*    */ import journeymap.common.waypoint.WaypointGroupStore;
/*    */ import net.minecraft.class_243;
/*    */ import net.minecraft.class_310;
/*    */ import net.minecraft.class_746;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WaypointDrawStepFactory
/*    */ {
/* 30 */   final List<DrawWayPointStep> drawStepList = new ArrayList<>();
/*    */ 
/*    */   
/*    */   public List<DrawWayPointStep> prepareSteps(Collection<ClientWaypointImpl> waypoints, Renderer renderer, boolean checkDistance, boolean showLabel) {
/* 34 */     class_310 mc = class_310.method_1551();
/* 35 */     class_746 class_746 = mc.field_1724;
/* 36 */     int maxDistance = (JourneymapClient.getInstance().getWaypointProperties()).maxDistance.get().intValue();
/* 37 */     float waypointLabelScale = (JourneymapClient.getInstance().getFullMapProperties()).waypointLabelScale.get().floatValue();
/* 38 */     float waypointIconScale = (JourneymapClient.getInstance().getFullMapProperties()).waypointIconScale.get().floatValue();
/* 39 */     checkDistance = (checkDistance && maxDistance > 0);
/* 40 */     class_243 playerVec = checkDistance ? class_746.method_19538() : null;
/* 41 */     this.drawStepList.clear();
/*    */ 
/*    */     
/*    */     try {
/* 45 */       for (ClientWaypointImpl waypoint : waypoints) {
/*    */         
/* 47 */         if (waypoint.isEnabled() && waypoint.isInPlayerDimension() && WaypointGroupStore.getInstance().get(waypoint.getGroupId()).isEnabled()) {
/*    */           
/* 49 */           if (checkDistance) {
/*    */ 
/*    */             
/* 52 */             double actualDistance = playerVec.method_1022(waypoint.getPosition());
/* 53 */             if (actualDistance > maxDistance) {
/*    */               continue;
/*    */             }
/*    */           } 
/*    */ 
/*    */           
/* 59 */           DrawWayPointStep wayPointStep = DataCache.INSTANCE.getDrawWayPointStep(waypoint);
/* 60 */           if (wayPointStep != null)
/*    */           {
/* 62 */             this.drawStepList.add(wayPointStep);
/* 63 */             wayPointStep.setShowLabel(showLabel);
/* 64 */             wayPointStep.setLabelScale(waypointLabelScale);
/* 65 */             wayPointStep.setIconScale(waypointIconScale);
/*    */           }
/*    */         
/*    */         } 
/*    */       } 
/* 70 */     } catch (Throwable t) {
/*    */       
/* 72 */       Journeymap.getLogger().error("Error during prepareSteps: " + LogFormatter.toString(t));
/*    */     } 
/*    */     
/* 75 */     return this.drawStepList;
/*    */   }
/*    */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\render\draw\WaypointDrawStepFactory.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */