/*     */ package journeymap.client.waypoint;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.Files;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import journeymap.api.common.waypoint.WaypointFactoryImpl;
/*     */ import journeymap.api.v2.common.waypoint.Waypoint;
/*     */ import journeymap.client.io.FileHandler;
/*     */ import journeymap.common.Journeymap;
/*     */ import journeymap.common.waypoint.WaypointStore;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_310;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ExternalWaypointFinder
/*     */ {
/*  21 */   private final List<ClientWaypointImpl> xaeroWaypoints = new ArrayList<>();
/*     */ 
/*     */   
/*     */   private boolean didSearch = false;
/*     */ 
/*     */   
/*     */   public boolean hasExternalWaypoints() {
/*  28 */     if (!this.didSearch) {
/*     */       
/*     */       try {
/*     */         
/*  32 */         findXaeroWaypointsForWorld();
/*     */       }
/*  34 */       catch (Exception e) {
/*     */         
/*  36 */         Journeymap.getLogger().error("Error finding external waypoints.", e);
/*     */       } 
/*     */     }
/*  39 */     return !getXaeroWaypoints().isEmpty();
/*     */   }
/*     */ 
/*     */   
/*     */   public List<ClientWaypointImpl> getXaeroWaypoints() {
/*  44 */     return this.xaeroWaypoints.stream().filter(waypoint -> !waypointExist((Waypoint)waypoint)).toList();
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean waypointExist(Waypoint waypoint) {
/*  49 */     for (ClientWaypointImpl wp : WaypointStore.getInstance().getAll()) {
/*     */       
/*  51 */       if (wp.getX() == waypoint.getX() && wp
/*  52 */         .getZ() == waypoint.getZ() && wp
/*  53 */         .getY() == waypoint.getY() && wp
/*  54 */         .getGroupId().equalsIgnoreCase(waypoint.getGroupId()) && wp
/*  55 */         .getPrimaryDimension().equalsIgnoreCase(waypoint.getPrimaryDimension()))
/*     */       {
/*  57 */         return true;
/*     */       }
/*     */     } 
/*  60 */     return false;
/*     */   }
/*     */   
/*     */   private void findXaeroWaypointsForWorld() {
/*     */     String worldDirectory;
/*  65 */     this.didSearch = true;
/*  66 */     class_310 mc = class_310.method_1551();
/*     */     
/*  68 */     if (mc.method_47392() || class_310.method_1551().method_1558() == null || class_310.method_1551().method_1558().method_2994()) {
/*     */       
/*  70 */       worldDirectory = (mc.method_1576()).field_23784.method_27005();
/*     */     }
/*     */     else {
/*     */       
/*  74 */       worldDirectory = "Multiplayer_" + (class_310.method_1551().method_1558()).field_3761;
/*     */     } 
/*     */     
/*  77 */     File xaeroMiniMapDir = new File(FileHandler.getMinecraftDirectory(), "xaero/minimap/" + worldDirectory);
/*  78 */     if (xaeroMiniMapDir.exists()) {
/*     */       
/*  80 */       File[] directories = xaeroMiniMapDir.listFiles(File::isDirectory);
/*  81 */       if (directories != null)
/*     */       {
/*  83 */         for (File wpDir : directories) {
/*     */           
/*  85 */           if (wpDir.isDirectory()) {
/*     */ 
/*     */             
/*  88 */             File[] files = wpDir.listFiles(file -> file.getName().endsWith(".txt"));
/*  89 */             String dim = getDimensionFromDirectoryName(wpDir.getName());
/*  90 */             if (files != null)
/*     */             {
/*  92 */               for (File waypointFile : files) {
/*     */                 
/*  94 */                 List<ClientWaypointImpl> waypoints = buildWaypoints(dim, waypointFile);
/*  95 */                 if (waypoints != null)
/*     */                 {
/*  97 */                   this.xaeroWaypoints.addAll(waypoints);
/*     */                 }
/*     */               } 
/*     */             }
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private List<ClientWaypointImpl> buildWaypoints(String dimension, File waypoints) {
/* 110 */     List<ClientWaypointImpl> xaeroWaypoints = new ArrayList<>();
/*     */     
/*     */     try {
/* 113 */       return Files.readAllLines(waypoints.toPath())
/* 114 */         .stream()
/* 115 */         .skip(3L)
/* 116 */         .map(wp -> parsed(wp, dimension))
/* 117 */         .filter(waypoint -> !waypointExist((Waypoint)waypoint))
/* 118 */         .toList();
/*     */     }
/* 120 */     catch (IOException e) {
/*     */       
/* 122 */       Journeymap.getLogger().error("Error building importable waypoints.", e);
/*     */       
/* 124 */       return xaeroWaypoints;
/*     */     } 
/*     */   }
/*     */   
/*     */   private ClientWaypointImpl parsed(String wp, String dimension) {
/* 129 */     String[] parsed = wp.split(":");
/* 130 */     String name = parsed[1].replace("gui.xaero_", "");
/* 131 */     int x = Integer.parseInt(parsed[3]);
/* 132 */     int y = Integer.parseInt(parsed[4].replace("~", "63"));
/* 133 */     int z = Integer.parseInt(parsed[5]);
/* 134 */     class_2338 pos = new class_2338(x, y, z);
/* 135 */     if (name.equalsIgnoreCase("deathpoint"))
/*     */     {
/* 137 */       return (ClientWaypointImpl)WaypointFactoryImpl.createWaypoint("journeymap", pos, name, dimension, true, true);
/*     */     }
/* 139 */     return (ClientWaypointImpl)WaypointFactoryImpl.createWaypoint("journeymap", pos, name, dimension, false, true);
/*     */   }
/*     */ 
/*     */   
/*     */   private String getDimensionFromDirectoryName(String name) {
/* 144 */     String value = name.split("%")[1];
/* 145 */     switch (value) { case "-1": case "1": case "0":  }  return 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 150 */       value.replace("$", ":");
/*     */   }
/*     */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\waypoint\ExternalWaypointFinder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */