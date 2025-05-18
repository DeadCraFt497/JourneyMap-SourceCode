/*     */ package journeymap.client.waypoint;
/*     */ 
/*     */ import com.google.common.io.Files;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import com.mojang.serialization.JsonOps;
/*     */ import java.io.File;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import journeymap.common.Journeymap;
/*     */ import journeymap.common.codecs.WaypointCodecs;
/*     */ import journeymap.common.log.LogFormatter;
/*     */ import journeymap.common.waypoint.WaypointImpl;
/*     */ import net.minecraft.class_3518;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LegacyWaypointFileMigrator
/*     */ {
/*     */   public static List<ClientWaypointImpl> loadWaypoints(File waypointDir) {
/*  29 */     List<ClientWaypointImpl> waypoints = new ArrayList<>();
/*     */     
/*  31 */     File[] files = waypointDir.listFiles((dir, name) -> name.endsWith(".json"));
/*     */     
/*  33 */     if (files == null)
/*     */     {
/*  35 */       return waypoints;
/*     */     }
/*     */     
/*  38 */     Journeymap.getLogger().info("{} Legacy waypoints found that need to be migrated and backed up.", Integer.valueOf(files.length));
/*  39 */     for (File waypointFile : files) {
/*     */       
/*  41 */       ClientWaypointImpl wp = load(waypointDir, waypointFile);
/*  42 */       if (wp != null)
/*     */       {
/*  44 */         waypoints.add(wp);
/*     */       }
/*     */     } 
/*     */     
/*  48 */     return waypoints;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static ClientWaypointImpl load(File waypointDir, File waypointFile) {
/*  54 */     String waypointString = null;
/*     */     
/*     */     try {
/*  57 */       waypointString = Files.asCharSource(waypointFile, StandardCharsets.UTF_8).read();
/*  58 */       JsonObject waypointJson = class_3518.method_15285(waypointString);
/*     */ 
/*     */       
/*  61 */       WaypointImpl wp = WaypointCodecs.WAYPOINT_CODEC.parse((DynamicOps)JsonOps.INSTANCE, waypointJson).result().get();
/*  62 */       File backupDir = new File(waypointDir, "backup");
/*  63 */       Journeymap.getLogger().info("\"{}\" successfully migrated, backing up to {}\\\\{}", wp.getName(), backupDir.getAbsolutePath(), waypointFile.getName());
/*  64 */       backupWaypoint(backupDir, waypointFile);
/*  65 */       remove(waypointFile);
/*  66 */       return (ClientWaypointImpl)wp;
/*     */     }
/*  68 */     catch (Throwable e) {
/*     */       
/*  70 */       Journeymap.getLogger().error("Can't load waypoint file {} with contents: {} because: ", waypointFile, waypointString, e);
/*     */       
/*  72 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void remove(File waypointFile) {
/*     */     try {
/*  79 */       waypointFile.delete();
/*     */     }
/*  81 */     catch (Exception e) {
/*     */       
/*  83 */       Journeymap.getLogger().warn("Can't delete waypoint file {}: ", waypointFile, e);
/*  84 */       waypointFile.deleteOnExit();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void backupWaypoint(File backupDir, File waypointFile) {
/*     */     try {
/*  94 */       if (!backupDir.exists())
/*     */       {
/*  96 */         backupDir.mkdirs();
/*     */       }
/*     */       
/*  99 */       File file = new File(backupDir, waypointFile.getName());
/* 100 */       Files.copy(waypointFile, file);
/*     */     }
/* 102 */     catch (Exception e) {
/*     */       
/* 104 */       Journeymap.getLogger().error(String.format("Can't backup waypoint file %s: %s", new Object[] { waypointFile, LogFormatter.toString(e) }));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\waypoint\LegacyWaypointFileMigrator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */