/*     */ package journeymap.client.waypoint;
/*     */ 
/*     */ import com.google.common.base.Joiner;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import java.util.TreeSet;
/*     */ import java.util.UUID;
/*     */ import journeymap.api.v2.common.waypoint.Waypoint;
/*     */ import journeymap.client.texture.TextureAccess;
/*     */ import journeymap.client.texture.TextureCache;
/*     */ import journeymap.common.Journeymap;
/*     */ import journeymap.common.waypoint.WaypointGroupStore;
/*     */ import journeymap.common.waypoint.WaypointIcon;
/*     */ import journeymap.common.waypoint.WaypointImpl;
/*     */ import journeymap.common.waypoint.WaypointOrigin;
/*     */ import journeymap.common.waypoint.WaypointPos;
/*     */ import journeymap.common.waypoint.WaypointSettings;
/*     */ import net.minecraft.class_1043;
/*     */ import net.minecraft.class_124;
/*     */ import net.minecraft.class_1937;
/*     */ import net.minecraft.class_243;
/*     */ import net.minecraft.class_2960;
/*     */ import net.minecraft.class_310;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ClientWaypointImpl
/*     */   extends WaypointImpl
/*     */ {
/*     */   public ClientWaypointImpl(Waypoint waypoint) {
/*  37 */     super((WaypointImpl)waypoint);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ClientWaypointImpl(String name, String version, String modId, String guid, String origin, String groupId, WaypointPos pos, Integer color, WaypointIcon icon, WaypointSettings settings, TreeSet<String> dimensions, Optional<String> customData) {
/*  56 */     super(name, guid);
/*  57 */     this.origin = origin;
/*  58 */     this.pos = pos;
/*  59 */     this.color = color.intValue();
/*  60 */     this.icon = icon;
/*  61 */     this.dimensions = dimensions;
/*  62 */     this.version = version;
/*  63 */     this.modId = modId;
/*  64 */     this.settings = settings;
/*  65 */     this.groupId = groupId;
/*  66 */     customData.ifPresent(this::setCustomData);
/*     */     
/*  68 */     if (dimensions.size() == 1)
/*     */     {
/*  70 */       pos.setPrimaryDimension(dimensions.first());
/*     */     }
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
/*     */ 
/*     */   
/*     */   @Internal
/*     */   public ClientWaypointImpl(String name, WaypointPos pos, Integer color, WaypointSettings settings, String origin, TreeSet<String> dimensions, WaypointIcon icon, String groupId) {
/*  88 */     this(name, "1", 
/*     */ 
/*     */         
/*  91 */         (WaypointOrigin.fromValue(origin) == null) ? origin : "journeymap", 
/*  92 */         UUID.randomUUID().toString(), 
/*  93 */         (WaypointOrigin.fromValue(origin) != null) ? origin : "journeymap", groupId, pos, color, icon, settings, dimensions, 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 100 */         Optional.empty());
/*     */ 
/*     */     
/* 103 */     if (!dimensions.isEmpty()) {
/*     */       
/* 105 */       pos.setPrimaryDimension(dimensions.first());
/* 106 */       if ("minecraft:the_nether".equals(pos.getPrimaryDimension())) {
/*     */         
/* 108 */         pos.setX(pos.getX() >> 3);
/* 109 */         pos.setZ(pos.getZ() >> 3);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDisplayName() {
/* 116 */     String tag = getGroup().getTag();
/* 117 */     if (tag != null && !tag.isEmpty())
/*     */     {
/* 119 */       return "[" + tag + "] " + getName();
/*     */     }
/* 121 */     return getName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getX() {
/* 131 */     class_310 mc = class_310.method_1551();
/* 132 */     if (mc != null && mc.field_1724 != null && neitherShiftRight((class_1937)mc.field_1687))
/*     */     {
/* 134 */       return super.getX() >> 3;
/*     */     }
/* 136 */     if (mc != null && mc.field_1724 != null && netherShiftLeft((class_1937)mc.field_1687))
/*     */     {
/* 138 */       return super.getX() << 3;
/*     */     }
/* 140 */     return super.getX();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getZ() {
/* 150 */     class_310 mc = class_310.method_1551();
/* 151 */     if (mc != null && mc.field_1724 != null && neitherShiftRight((class_1937)mc.field_1687))
/*     */     {
/* 153 */       return super.getZ() >> 3;
/*     */     }
/* 155 */     if (mc != null && mc.field_1724 != null && netherShiftLeft((class_1937)mc.field_1687))
/*     */     {
/* 157 */       return super.getZ() << 3;
/*     */     }
/* 159 */     return super.getZ();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRawX() {
/* 164 */     return super.getX();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRawZ() {
/* 169 */     return super.getZ();
/*     */   }
/*     */ 
/*     */   
/*     */   public double getRawCenterX() {
/* 174 */     return super.getX() + 0.5D;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getRawCenterZ() {
/* 179 */     return super.getZ() + 0.5D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getBlockCenteredX() {
/* 189 */     return getX() + 0.5D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getBlockCenteredY() {
/* 199 */     return getY() + 0.5D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getBlockCenteredZ() {
/* 209 */     return getZ() + 0.5D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public class_243 getPosition() {
/* 219 */     return new class_243(getBlockCenteredX(), getBlockCenteredY(), getBlockCenteredZ());
/*     */   }
/*     */ 
/*     */   
/*     */   public Integer getIconColor() {
/* 224 */     if (getIcon() != null && !getIcon().useBeaconColor())
/*     */     {
/* 226 */       return getIcon().getColor();
/*     */     }
/*     */ 
/*     */     
/* 230 */     return Integer.valueOf(getRenderColor());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRenderColor() {
/* 236 */     if (getGroup().colorOverride() && getGroup().getColor() != null)
/*     */     {
/* 238 */       return getGroup().getColor().intValue();
/*     */     }
/* 240 */     return getColor();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDeathPoint() {
/* 250 */     return WaypointGroupStore.DEATH.getGuid().equals(this.groupId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getSafeColor() {
/* 260 */     if (getRed() + getGreen() + getBlue() >= 100)
/*     */     {
/* 262 */       return Integer.valueOf(getRenderColor());
/*     */     }
/* 264 */     return Integer.valueOf(4210752);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public class_1043 getTexture() {
/* 275 */     class_1043 tex = TextureCache.getTexture(getTextureResource());
/* 276 */     if (tex == null)
/*     */     {
/* 278 */       tex = TextureCache.getTexture(TextureCache.Waypoint);
/*     */     }
/* 280 */     ((TextureAccess)tex).journeymap$setDisplayHeight(16);
/* 281 */     ((TextureAccess)tex).journeymap$setDisplayWidth(16);
/* 282 */     return tex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTeleportReady() {
/* 292 */     return (getY() >= (class_310.method_1551()).field_1687.method_31607() && isInPlayerDimension());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInPlayerDimension() {
/* 303 */     return getDimensions().contains((class_310.method_1551()).field_1724.method_5770().method_27983().method_29177().toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toChatString() {
/* 314 */     boolean useName = !getName().equals(String.format("%s, %s", new Object[] { Integer.valueOf(getX()), Integer.valueOf(getZ()) }));
/* 315 */     return toChatString(useName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toChatString(boolean useName) {
/* 326 */     return toChatString((Waypoint)this, useName);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String toChatString(Waypoint waypoint) {
/* 331 */     boolean useName = !waypoint.getName().equals(String.format("%s, %s", new Object[] { Integer.valueOf(waypoint.getX()), Integer.valueOf(waypoint.getZ()) }));
/* 332 */     return toChatString(waypoint, useName);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String toChatString(Waypoint waypoint, boolean useName) {
/* 337 */     String dim = waypoint.getPrimaryDimension();
/* 338 */     if (!waypoint.getDimensions().isEmpty())
/*     */     {
/* 340 */       dim = waypoint.getDimensions().first();
/*     */     }
/* 342 */     boolean useDim = !"overworld".equalsIgnoreCase(dim);
/*     */     
/* 344 */     List<String> parts = new ArrayList<>();
/* 345 */     List<Object> args = new ArrayList();
/* 346 */     if (useName) {
/*     */       
/* 348 */       parts.add("name:\"%s\"");
/* 349 */       args.add(waypoint.getName().replaceAll("\"", " "));
/*     */     } 
/*     */     
/* 352 */     parts.add("x:%s, y:%s, z:%s");
/* 353 */     args.add(Integer.valueOf(waypoint.getX()));
/* 354 */     args.add(Integer.valueOf(waypoint.getY()));
/* 355 */     args.add(Integer.valueOf(waypoint.getZ()));
/*     */     
/* 357 */     if (useDim) {
/*     */       
/* 359 */       parts.add("dim:%s");
/* 360 */       args.add(dim);
/*     */     } 
/*     */     
/* 363 */     String format = "[" + Joiner.on(", ").join(parts) + "]";
/* 364 */     String result = String.format(format, args.toArray());
/* 365 */     if (WaypointParser.parse(result) == null) {
/*     */       
/* 367 */       Journeymap.getLogger().warn("Couldn't produce parsable chat string from Waypoint: " + String.valueOf(waypoint));
/* 368 */       if (useName)
/*     */       {
/* 370 */         return toChatString(waypoint, false);
/*     */       }
/*     */     } 
/* 373 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPrettyName() {
/* 384 */     class_124 textFormatting = class_124.field_1068;
/* 385 */     for (class_124 tf : class_124.values()) {
/*     */       
/* 387 */       if (tf.method_532() != null && tf.method_532().equals(Integer.valueOf((getRed() & 0xFF) << 16 | (getGreen() & 0xFF) << 8 | getBlue() & 0xFF)))
/*     */       {
/* 389 */         textFormatting = tf;
/*     */       }
/*     */     } 
/* 392 */     return String.valueOf(textFormatting) + String.valueOf(textFormatting) + getName();
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
/*     */   public static ClientWaypointImpl fromString(String json) {
/* 404 */     WaypointImpl waypoint = (WaypointImpl)GSON.fromJson(json, WaypointImpl.class);
/* 405 */     return new ClientWaypointImpl((Waypoint)waypoint);
/*     */   }
/*     */ 
/*     */   
/*     */   public class_2960 getTextureResource() {
/* 410 */     if (getGroup().getIconResourceLocation() != null && getGroup().iconOverride())
/*     */     {
/* 412 */       return getGroup().getIconResourceLocation();
/*     */     }
/* 414 */     if (getIcon() != null && getIcon().getResourceLocation() != null)
/*     */     {
/* 416 */       return getIcon().getResourceLocation();
/*     */     }
/* 418 */     if (WaypointGroupStore.DEATH.getGuid().equals(this.groupId))
/*     */     {
/* 420 */       return WaypointIcon.DEFAULT_ICON_DEATH;
/*     */     }
/* 422 */     return WaypointIcon.DEFAULT_ICON_NORMAL;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasCustomTexture() {
/* 427 */     if (getIcon() != null && getIcon().getResourceLocation() != null)
/*     */     {
/* 429 */       return (getIcon().getResourceLocation() != WaypointIcon.DEFAULT_ICON_DEATH && getIcon().getResourceLocation() != WaypointIcon.DEFAULT_ICON_NORMAL);
/*     */     }
/* 431 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasCustomIconColor() {
/* 436 */     return (hasCustomTexture() && getIcon().getColor() != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getUuid() {
/* 441 */     return getGuid();
/*     */   }
/*     */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\waypoint\ClientWaypointImpl.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */