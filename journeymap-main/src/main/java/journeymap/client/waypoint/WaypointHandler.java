/*    */ package journeymap.client.waypoint;
/*    */ 
/*    */ import java.util.List;
/*    */ import journeymap.client.JourneymapClient;
/*    */ import journeymap.client.io.FileHandler;
/*    */ import journeymap.common.Journeymap;
/*    */ import journeymap.common.nbt.waypoint.WaypointDAO;
/*    */ import journeymap.common.waypoint.WaypointGroupStore;
/*    */ import journeymap.common.waypoint.WaypointStore;
/*    */ 
/*    */ public class WaypointHandler
/*    */ {
/*    */   private static WaypointHandler instance;
/*    */   private WaypointDAO dao;
/*    */   
/*    */   public static WaypointHandler getInstance() {
/* 17 */     if (instance == null)
/*    */     {
/* 19 */       instance = new WaypointHandler();
/*    */     }
/* 21 */     return instance;
/*    */   }
/*    */ 
/*    */   
/*    */   public WaypointDAO getDao() {
/* 26 */     if (this.dao == null)
/*    */     {
/* 28 */       if (JourneymapClient.getInstance().getStateHandler().isJourneyMapServerConnection()) {
/*    */         
/* 30 */         this.dao = new ClientWaypointDAO();
/*    */       }
/*    */       else {
/*    */         
/* 34 */         this.dao = new ClientWaypointDAO();
/*    */       } 
/*    */     }
/* 37 */     return this.dao;
/*    */   }
/*    */ 
/*    */   
/*    */   public void reset() {
/* 42 */     this.dao = null;
/* 43 */     Journeymap.getLogger().info("Resetting Waypoints and Groups.");
/* 44 */     WaypointStore.getInstance().reset();
/* 45 */     WaypointGroupStore.getInstance().reset();
/* 46 */     List<ClientWaypointImpl> migratedWaypoints = LegacyWaypointFileMigrator.loadWaypoints(FileHandler.getWaypointDir());
/* 47 */     if (!migratedWaypoints.isEmpty())
/*    */     {
/* 49 */       migratedWaypoints.forEach(wp -> WaypointStore.getInstance().save(wp, false));
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\waypoint\WaypointHandler.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */