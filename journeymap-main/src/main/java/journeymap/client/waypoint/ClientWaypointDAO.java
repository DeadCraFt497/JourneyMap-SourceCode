/*     */ package journeymap.client.waypoint;
/*     */ 
/*     */ import com.google.common.io.Files;
/*     */ import com.mojang.serialization.DataResult;
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.Collection;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ import journeymap.client.io.FileHandler;
/*     */ import journeymap.common.Journeymap;
/*     */ import journeymap.common.codecs.WaypointCodecs;
/*     */ import journeymap.common.codecs.WaypointGroupCodecs;
/*     */ import journeymap.common.nbt.waypoint.WaypointDAO;
/*     */ import journeymap.common.waypoint.WaypointGroupImpl;
/*     */ import journeymap.common.waypoint.WaypointImpl;
/*     */ import net.minecraft.class_156;
/*     */ import net.minecraft.class_2487;
/*     */ import net.minecraft.class_2507;
/*     */ import net.minecraft.class_2509;
/*     */ import net.minecraft.class_2520;
/*     */ 
/*     */ public class ClientWaypointDAO extends WaypointDAO {
/*     */   protected static final String DAT_FILE = "WaypointData.dat";
/*  31 */   private final ReentrantLock writeLock = new ReentrantLock();
/*     */ 
/*     */   
/*     */   public ClientWaypointDAO() {
/*  35 */     this.data = load(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<String, WaypointGroupImpl> decodeGroups(class_2487 data) {
/*  40 */     LinkedHashMap<String, WaypointGroupImpl> groups = new LinkedHashMap<>();
/*  41 */     if (data.method_10545("groups")) {
/*     */       
/*  43 */       class_2487 groupsTags = data.method_10562("groups").get();
/*  44 */       for (String key : groupsTags.method_10541()) {
/*     */         
/*  46 */         class_2520 tag = groupsTags.method_10580(key);
/*  47 */         DataResult<WaypointGroupImpl> result = WaypointGroupCodecs.V1_WAYPOINT_GROUP_CODEC.parse((DynamicOps)class_2509.field_11560, tag);
/*  48 */         if (result.result().isPresent()) {
/*     */           
/*  50 */           WaypointGroupImpl group = result.result().get();
/*  51 */           groups.put(group.getGuid(), group);
/*     */         } 
/*     */       } 
/*     */     } 
/*  55 */     return groups;
/*     */   }
/*     */ 
/*     */   
/*     */   public class_2487 encodeGroups(Collection<WaypointGroupImpl> groups) {
/*  60 */     class_2487 tag = new class_2487();
/*  61 */     tag.method_10566("groups", (class_2520)new class_2487());
/*  62 */     class_2487 groupsTag = tag.method_10562("groups").get();
/*  63 */     for (WaypointGroupImpl group : groups) {
/*     */       
/*  65 */       DataResult<class_2520> result = WaypointGroupCodecs.V1_WAYPOINT_GROUP_CODEC.encodeStart((DynamicOps)class_2509.field_11560, group);
/*  66 */       if (result.result().isPresent())
/*     */       {
/*  68 */         groupsTag.method_10566(group.getGuid(), result.result().get());
/*     */       }
/*     */     } 
/*  71 */     return tag;
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<String, WaypointImpl> decodeWaypoints(class_2487 data) {
/*  76 */     LinkedHashMap<String, WaypointImpl> waypoints = new LinkedHashMap<>();
/*  77 */     if (data.method_10545("waypoints")) {
/*     */       
/*  79 */       class_2487 groupsTags = data.method_10562("waypoints").get();
/*  80 */       for (String key : groupsTags.method_10541()) {
/*     */         
/*  82 */         class_2520 tag = groupsTags.method_10580(key);
/*  83 */         WaypointImpl waypoint = decodeWaypoint(tag);
/*  84 */         if (waypoint != null)
/*     */         {
/*  86 */           waypoints.put(waypoint.getGuid(), waypoint);
/*     */         }
/*     */       } 
/*     */     } 
/*  90 */     return waypoints;
/*     */   }
/*     */ 
/*     */   
/*     */   public class_2487 encodeWaypoints(Collection<WaypointImpl> waypoints) {
/*  95 */     class_2487 tag = new class_2487();
/*  96 */     tag.method_10566("waypoints", (class_2520)new class_2487());
/*  97 */     class_2487 wpTag = tag.method_10562("waypoints").get();
/*  98 */     for (WaypointImpl wp : waypoints) {
/*     */       
/* 100 */       class_2520 result = encodeWaypoint(wp);
/* 101 */       if (result != null)
/*     */       {
/* 103 */         wpTag.method_10566(wp.getGuid(), result);
/*     */       }
/*     */     } 
/* 106 */     return tag;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public WaypointImpl decodeWaypoint(class_2520 data) {
/* 112 */     DataResult<WaypointImpl> result = WaypointCodecs.V1_WAYPOINT_CODEC.parse((DynamicOps)class_2509.field_11560, data);
/* 113 */     if (result.result().isPresent())
/*     */     {
/* 115 */       return result.result().get();
/*     */     }
/* 117 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public class_2520 encodeWaypoint(WaypointImpl waypoint) {
/* 123 */     DataResult<class_2520> result = WaypointCodecs.V1_WAYPOINT_CODEC.encodeStart((DynamicOps)class_2509.field_11560, waypoint);
/* 124 */     if (result.result().isPresent())
/*     */     {
/* 126 */       return result.result().get();
/*     */     }
/* 128 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public WaypointImpl copyWaypoint(WaypointImpl waypoint) {
/* 134 */     class_2520 rawCopy = encodeWaypoint(waypoint);
/* 135 */     if (rawCopy != null)
/*     */     {
/* 137 */       return decodeWaypoint(rawCopy);
/*     */     }
/* 139 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private File getFile() {
/* 145 */     return new File(FileHandler.getWaypointDir(), "WaypointData.dat");
/*     */   }
/*     */ 
/*     */   
/*     */   private void write(File path) {
/* 150 */     this.writeLock.lock(); try {
/* 151 */       FileOutputStream fos = new FileOutputStream(path);
/*     */       
/* 153 */       try { DataOutputStream outputStream = new DataOutputStream(fos);
/*     */         
/* 155 */         try { class_2507.method_10628(this.data, outputStream);
/* 156 */           outputStream.close(); } catch (Throwable throwable) { try { outputStream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }
/* 157 */          fos.close(); } catch (Throwable throwable) { try { fos.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; } 
/* 158 */     } catch (IOException e) {
/*     */       
/* 160 */       throw new RuntimeException(e);
/*     */     }
/*     */     finally {
/*     */       
/* 164 */       this.writeLock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void save(boolean async) {
/* 171 */     if (isDirty()) {
/*     */       
/* 173 */       Journeymap.getLogger().info("Saving WaypointData.dat async:{}", Boolean.valueOf(async));
/* 174 */       if (async) {
/*     */         
/* 176 */         CompletableFuture.runAsync(() -> write(getFile()), (Executor)class_156.method_27958());
/*     */       }
/*     */       else {
/*     */         
/* 180 */         write(getFile());
/*     */       } 
/* 182 */       setDirty(false);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, WaypointGroupImpl> getGroups() {
/* 189 */     return decodeGroups(this.data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, WaypointImpl> getWaypoints() {
/* 195 */     return decodeWaypoints(this.data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class_2487 load(boolean backup) {
/*     */     try {
/* 203 */       if (getFile().exists()) {
/*     */         
/* 205 */         class_2487 data = class_2507.method_10633(getFile().toPath());
/* 206 */         if (backup)
/*     */         {
/* 208 */           backup(new File(FileHandler.getWaypointDir(), "backup"), getFile());
/*     */         }
/* 210 */         return data;
/*     */       } 
/*     */ 
/*     */       
/* 214 */       return new class_2487();
/*     */     
/*     */     }
/* 217 */     catch (Exception e) {
/*     */       
/* 219 */       Journeymap.getLogger().error("WaypointData.dat file is corrupted. Deleting as it is unusable.", e);
/* 220 */       if (getFile().exists()) {
/*     */         
/* 222 */         File corrupted = new File(FileHandler.getWaypointDir(), "corrupted");
/* 223 */         backup(corrupted, getFile());
/* 224 */         getFile().delete();
/*     */       } 
/* 226 */       if (restore(new File(FileHandler.getWaypointDir(), "backup"), FileHandler.getWaypointDir(), "WaypointData.dat"))
/*     */       {
/*     */         
/* 229 */         return load(false);
/*     */       }
/*     */ 
/*     */       
/* 233 */       return new class_2487();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static boolean restore(File backupDir, File dir, String fileName) {
/* 238 */     File backupFile = new File(backupDir, fileName);
/* 239 */     Journeymap.getLogger().error("Corrupted waypoint dat file detected, restoring backup from {}", backupFile);
/*     */ 
/*     */     
/*     */     try {
/* 243 */       if (!backupFile.exists())
/*     */       {
/* 245 */         return false;
/*     */       }
/*     */       
/* 248 */       File file = new File(dir, backupFile.getName());
/* 249 */       Files.copy(backupFile, file);
/* 250 */       return true;
/*     */     }
/* 252 */     catch (Exception e) {
/*     */       
/* 254 */       Journeymap.getLogger().error(String.format("Can't restore file %s: %s", new Object[] { backupFile, e }));
/*     */       
/* 256 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void backup(File backupDir, File oldFile) {
/*     */     try {
/* 265 */       if (!backupDir.exists())
/*     */       {
/* 267 */         backupDir.mkdirs();
/*     */       }
/*     */       
/* 270 */       File file = new File(backupDir, oldFile.getName());
/* 271 */       Files.copy(oldFile, file);
/*     */     }
/* 273 */     catch (Exception e) {
/*     */       
/* 275 */       Journeymap.getLogger().error(String.format("Can't backup file %s: %s", new Object[] { oldFile, e }));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\waypoint\ClientWaypointDAO.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */