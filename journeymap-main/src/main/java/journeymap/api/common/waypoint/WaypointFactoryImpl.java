/*     */ package journeymap.api.common.waypoint;
/*     */ 
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
/*     */ import java.util.UUID;
/*     */ import journeymap.api.v2.common.waypoint.Waypoint;
/*     */ import journeymap.api.v2.common.waypoint.WaypointFactory;
/*     */ import journeymap.api.v2.common.waypoint.WaypointGroup;
/*     */ import journeymap.client.Constants;
/*     */ import journeymap.client.JourneymapClient;
/*     */ import journeymap.client.cartography.color.RGB;
/*     */ import journeymap.client.properties.WaypointProperties;
/*     */ import journeymap.client.waypoint.ClientWaypointImpl;
/*     */ import journeymap.common.waypoint.WaypointGroupImpl;
/*     */ import journeymap.common.waypoint.WaypointGroupStore;
/*     */ import journeymap.common.waypoint.WaypointIcon;
/*     */ import journeymap.common.waypoint.WaypointImpl;
/*     */ import journeymap.common.waypoint.WaypointPos;
/*     */ import journeymap.common.waypoint.WaypointSettings;
/*     */ import journeymap.common.waypoint.WaypointStore;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_2960;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_3532;
/*     */ import net.minecraft.class_746;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WaypointFactoryImpl
/*     */   implements WaypointFactory.WaypointStore
/*     */ {
/*     */   public static WaypointFactoryImpl instance;
/*     */   public static WaypointFactory factory;
/*     */   
/*     */   public static void init() {
/*  40 */     instance = new WaypointFactoryImpl();
/*  41 */     factory = new WaypointFactory(instance);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Waypoint createWaypoint(String modId, class_2338 blockPos, String name, String dimension, boolean showDeviation) {
/*  46 */     return createWaypoint(modId, blockPos, name, dimension, false, showDeviation, RGB.randomColor(), true);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Waypoint createWaypoint(String modId, class_2338 blockPos, String name, String dimension, int color) {
/*  51 */     return createWaypoint(modId, blockPos, name, dimension, false, false, color, true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Waypoint createClientWaypoint(String modId, class_2338 pos, @Nullable String name, String primaryDimension, boolean persistent) {
/*  57 */     return createWaypoint(modId, pos, name, primaryDimension, false, persistent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WaypointGroup createWaypointGroup(String modId, String name) {
/*  64 */     return (WaypointGroup)new WaypointGroupImpl(modId, name);
/*     */   }
/*     */ 
/*     */   
/*     */   public WaypointGroup createWaypointGroup(String name) {
/*  69 */     return createWaypointGroup("journeymap", name);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Waypoint createWaypoint(String modId, class_2338 pos, @Nullable String name, String primaryDimension, boolean isDeath, boolean persistent) {
/*  74 */     return createWaypoint(modId, pos, name, primaryDimension, isDeath, false, RGB.randomColor(), persistent);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Waypoint createWaypoint(String modId, class_2338 blockPos, @Nullable String name, String primaryDimension, boolean isDeath, boolean showDeviation, int color, boolean persistent) {
/*  79 */     String guid = UUID.randomUUID().toString();
/*  80 */     if (name == null)
/*     */     {
/*  82 */       name = createName(blockPos.method_10263(), blockPos.method_10260());
/*     */     }
/*  84 */     class_2960 icon = isDeath ? WaypointIcon.DEFAULT_ICON_DEATH : WaypointIcon.DEFAULT_ICON_NORMAL;
/*  85 */     String group = isDeath ? WaypointGroupStore.DEATH.getGuid() : WaypointGroupStore.DEFAULT.getGuid();
/*  86 */     return (Waypoint)new ClientWaypointImpl(name, "1", modId, guid, modId, group, new WaypointPos(blockPos, primaryDimension), 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  94 */         Integer.valueOf(color), new WaypointIcon(icon), new WaypointSettings(true, showDeviation, persistent), new TreeSet(
/*     */ 
/*     */           
/*  97 */           Set.of(primaryDimension)), 
/*  98 */         Optional.empty());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Waypoint createCommandWaypoint(String modId, class_2338 blockPos, @Nullable String name, String primaryDimension, boolean isDeath, boolean showDeviation, int color, boolean persistent) {
/* 104 */     String guid = UUID.randomUUID().toString();
/* 105 */     if (name == null)
/*     */     {
/* 107 */       name = createName(blockPos.method_10263(), blockPos.method_10260());
/*     */     }
/* 109 */     class_2960 icon = isDeath ? WaypointIcon.DEFAULT_ICON_DEATH : WaypointIcon.DEFAULT_ICON_NORMAL;
/* 110 */     String group = "journeymap_default";
/* 111 */     return (Waypoint)new WaypointImpl(name, "1", modId, guid, modId, group, new WaypointPos(blockPos, primaryDimension), 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 119 */         Integer.valueOf(color), new WaypointIcon(icon), new WaypointSettings(true, showDeviation, persistent), new TreeSet(
/*     */ 
/*     */           
/* 122 */           Set.of(primaryDimension)), 
/* 123 */         Optional.empty());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void createDeathPoint(class_2338 blockPos, String dimension) {
/* 137 */     Date now = new Date();
/* 138 */     WaypointProperties properties = JourneymapClient.getInstance().getWaypointProperties();
/* 139 */     SimpleDateFormat simpleDateFormat = new SimpleDateFormat(properties.timeFormat.get() + " " + properties.timeFormat.get());
/* 140 */     String timeDate = simpleDateFormat.format(now);
/* 141 */     String name = String.format("%s %s", new Object[] { Constants.getString("jm.waypoint.deathpoint"), timeDate });
/* 142 */     Waypoint waypoint = createWaypoint("journeymap", blockPos, name, dimension, true, true);
/* 143 */     WaypointStore.getInstance().save((ClientWaypointImpl)waypoint, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ClientWaypointImpl at(class_2338 blockPos, boolean isDeath, String dimension) {
/* 148 */     return (ClientWaypointImpl)createWaypoint("journeymap", blockPos, createName(blockPos.method_10263(), blockPos.method_10260()), dimension, isDeath, true);
/*     */   }
/*     */ 
/*     */   
/*     */   private static String createName(int x, int z) {
/* 153 */     return String.format("%s, %s", new Object[] { Integer.valueOf(x), Integer.valueOf(z) });
/*     */   }
/*     */ 
/*     */   
/*     */   public static ClientWaypointImpl of(class_746 player) {
/* 158 */     class_2338 blockPos = new class_2338(class_3532.method_15357(player.method_23317()), class_3532.method_15357(player.method_23318()), class_3532.method_15357(player.method_23321()));
/* 159 */     return at(blockPos, false, (class_310.method_1551()).field_1724.method_5770().method_27983().method_29177().toString());
/*     */   }
/*     */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\api\common\waypoint\WaypointFactoryImpl.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */