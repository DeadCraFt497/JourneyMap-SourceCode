/*     */ package journeymap.client.texture;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ArrayBlockingQueue;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.ThreadPoolExecutor;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import journeymap.client.io.FileHandler;
/*     */ import journeymap.client.io.IconSetFileHandler;
/*     */ import journeymap.client.io.ThemeLoader;
/*     */ import journeymap.client.log.JMLogger;
/*     */ import journeymap.client.model.region.RegionImageCache;
/*     */ import journeymap.client.render.RenderWrapper;
/*     */ import journeymap.client.render.draw.MobIconCache;
/*     */ import journeymap.client.ui.theme.Theme;
/*     */ import journeymap.client.waypoint.ClientWaypointImpl;
/*     */ import journeymap.common.Journeymap;
/*     */ import journeymap.common.accessors.TextureManagerAccess;
/*     */ import journeymap.common.thread.JMThreadFactory;
/*     */ import journeymap.common.waypoint.WaypointStore;
/*     */ import net.minecraft.class_1011;
/*     */ import net.minecraft.class_1043;
/*     */ import net.minecraft.class_1044;
/*     */ import net.minecraft.class_1060;
/*     */ import net.minecraft.class_2960;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_3298;
/*     */ import net.minecraft.class_3300;
/*     */ import net.minecraft.class_8666;
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
/*     */ public class TextureCache
/*     */ {
/*  58 */   public static final class_8666 TELEPORT_SPRITE = new class_8666(uiImage("teleport.png"), uiImage("teleport.png"));
/*  59 */   public static final class_8666 SHARE_SPRITE = new class_8666(uiImage("share.png"), uiImage("share.png"));
/*  60 */   public static final class_8666 POWER_SPRITE = new class_8666(uiImage("power.png"), uiImage("power.png"));
/*  61 */   public static final class_8666 PIN_SPRITE = new class_8666(uiImage("pin.png"), uiImage("pin.png"));
/*  62 */   public static final class_8666 OPTIONS_SPRITE = new class_8666(uiImage("option.png"), uiImage("option.png"));
/*  63 */   public static final class_8666 X_OUTLINE_SPRITE = new class_8666(uiImage("x_outline_icon.png"), uiImage("x_outline_icon.png"));
/*  64 */   public static final class_8666 X_SPRITE = new class_8666(uiImage("x_icon.png"), uiImage("x_icon.png"));
/*  65 */   public static final class_2960 TOGGLE_ON = uiImage("toggle-button-on.png");
/*  66 */   public static final class_2960 TOGGLE_OFF = uiImage("toggle-button-off.png");
/*  67 */   public static final class_2960 ARROW_GLYPH = uiImage("arrow_glyph.png");
/*     */ 
/*     */ 
/*     */   
/*  71 */   public static final class_2960 Question = uiImage("question.png");
/*  72 */   public static final class_2960 GridSquares = uiImage("grid.png");
/*     */ 
/*     */ 
/*     */   
/*  76 */   public static final class_2960 GridRegionSquares = uiImage("grid-region.png");
/*     */ 
/*     */ 
/*     */   
/*  80 */   public static final class_2960 GridRegion = uiImage("region.png");
/*     */   
/*  82 */   public static final class_2960 SearchIcon = uiImage("search.png");
/*     */ 
/*     */ 
/*     */   
/*  86 */   public static final class_2960 ColorPicker = uiImage("colorpick.png");
/*     */ 
/*     */ 
/*     */   
/*  90 */   public static final class_2960 ColorPicker2 = uiImage("colorpick2.png");
/*     */ 
/*     */ 
/*     */   
/*  94 */   public static final class_2960 TileSampleDay = uiImage("tile-sample-day.png");
/*     */ 
/*     */ 
/*     */   
/*  98 */   public static final class_2960 TileSampleNight = uiImage("tile-sample-night.png");
/*     */ 
/*     */ 
/*     */   
/* 102 */   public static final class_2960 TileSampleUnderground = uiImage("tile-sample-underground.png");
/*     */ 
/*     */ 
/*     */   
/* 106 */   public static final class_2960 UnknownEntity = uiImage("unknown.png");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 111 */   public static final class_2960 Deathpoint = waypointTexture("waypoint-death-icon.png");
/*     */ 
/*     */ 
/*     */   
/* 115 */   public static final class_2960 Waypoint = waypointTexture("waypoint-icon.png");
/*     */ 
/*     */ 
/*     */   
/* 119 */   public static final class_2960 MobDot = uiImage("marker-dot-160.png");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 124 */   public static final class_2960 MobDotArrow = uiImage("marker-dot-arrow-160.png");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 129 */   public static final class_2960 MobDotChevron = uiImage("marker-chevron-160.png");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 134 */   public static final class_2960 MobIconArrow = uiImage("marker-icon-arrow-160.png");
/*     */   
/* 136 */   public static final class_2960 MobIconArrowBG = uiImage("marker-icon-arrow-bg-160.png");
/*     */   
/* 138 */   public static final class_2960 MobIcon = uiImage("marker-icon-160.png");
/*     */   
/* 140 */   public static final class_2960 MobIconBG = uiImage("marker-icon-bg-160.png");
/*     */   
/* 142 */   public static final class_2960 MobIconMask = uiImage("marker-icon-mask.png");
/*     */ 
/*     */ 
/*     */   
/* 146 */   public static final class_2960 PlayerArrow = uiImage("marker-player-160.png");
/*     */ 
/*     */ 
/*     */   
/* 150 */   public static final class_2960 PlayerArrowBG = uiImage("marker-player-bg-160.png");
/*     */   
/* 152 */   public static final class_2960 PlayerOutline = uiImage("marker-player-outline.png");
/*     */ 
/*     */ 
/*     */   
/* 156 */   public static final class_2960 Logo = uiImage("ico/journeymap.png");
/*     */ 
/*     */ 
/*     */   
/* 160 */   public static final class_2960 MinimapSquare128 = uiImage("minimap/minimap-square-128.png");
/*     */ 
/*     */ 
/*     */   
/* 164 */   public static final class_2960 MinimapSquare256 = uiImage("minimap/minimap-square-256.png");
/*     */ 
/*     */ 
/*     */   
/* 168 */   public static final class_2960 MinimapSquare512 = uiImage("minimap/minimap-square-512.png");
/*     */ 
/*     */ 
/*     */   
/* 172 */   public static final class_2960 Discord = uiImage("discord.png");
/*     */   
/* 174 */   public static final class_2960 CurseForge = uiImage("curseforge.png");
/*     */   
/* 176 */   public static final class_2960 Modrinth = uiImage("modrinth.png");
/*     */   
/* 178 */   public static final class_2960 ColorWheel = uiImage("colorwheel.png");
/*     */   
/* 180 */   public static final class_2960 ColorWheelHandler = uiImage("colorwheel-handler.png");
/*     */   
/* 182 */   public static final class_2960 ColorBox = uiImage("color-box.png");
/*     */   
/* 184 */   public static final class_2960 ColorVSlider = uiImage("color-v-slider.png");
/*     */   
/* 186 */   public static final class_2960 ColorVSliderHandler = uiImage("color-v-slider-handler.png");
/*     */   
/* 188 */   public static final class_2960 ColorHistoryButton = uiImage("color-history-button.png");
/*     */   
/* 190 */   public static final class_2960 Flag = uiImage("flag.png");
/*     */ 
/*     */ 
/*     */   
/* 194 */   public static final class_2960 WaypointEdit = uiImage("waypoint-edit.png");
/*     */ 
/*     */ 
/*     */   
/* 198 */   public static final class_2960 WaypointOffscreen = uiImage("waypoint-offscreen.png");
/*     */   
/* 200 */   private static final Map<String, class_2960> dynamicTextureMap = Collections.synchronizedMap(new HashMap<>());
/* 201 */   public static final Map<String, class_1043> colorizedWPIconMap = Collections.synchronizedMap(new HashMap<>());
/* 202 */   public static final Map<class_2960, class_1043> waypointIconMap = Collections.synchronizedMap(new HashMap<>());
/*     */ 
/*     */ 
/*     */   
/* 206 */   public static final Map<class_2960, class_2960> modTextureMap = Collections.synchronizedMap(new HashMap<>());
/*     */ 
/*     */   
/*     */   public static class_2960 getTexture(String texturePath) {
/* 210 */     class_2960 tex = dynamicTextureMap.get(texturePath);
/* 211 */     if (tex == null) {
/*     */       
/* 213 */       tex = uiImage(texturePath);
/* 214 */       dynamicTextureMap.put(texturePath, tex);
/*     */     } 
/* 216 */     return tex;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class_2960 spriteImage(String fileName) {
/* 221 */     class_2960 location = class_2960.method_60655("journeymap", "ui/img/" + fileName);
/* 222 */     return location;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class_2960 waypointTexture(String fileName) {
/* 227 */     return class_2960.method_60655("journeymap", "textures/waypoint/icon/" + fileName);
/*     */   }
/*     */ 
/*     */   
/*     */   public static class_2960 uiImage(String fileName) {
/* 232 */     return class_2960.method_60655("journeymap", "ui/img/" + fileName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 239 */   public static final Map<String, class_1043> playerSkins = Collections.synchronizedMap(new HashMap<>());
/*     */ 
/*     */ 
/*     */   
/* 243 */   public static final Map<String, class_1043> themeImages = Collections.synchronizedMap(new HashMap<>());
/*     */   
/* 245 */   private static ThreadPoolExecutor texExec = new ThreadPoolExecutor(2, 4, 15L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(8), (ThreadFactory)new JMThreadFactory("texture"), new ThreadPoolExecutor.CallerRunsPolicy());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void flush() {
/* 252 */     LogUtils.getLogger().info("[JourneyMap]: Purging Theme Images");
/* 253 */     purgeThemeImages(themeImages);
/* 254 */     LogUtils.getLogger().info("[JourneyMap]: Purging Region Images");
/* 255 */     RegionImageCache.INSTANCE.flushToDisk(true);
/* 256 */     RegionImageCache.INSTANCE.clear();
/* 257 */     LogUtils.getLogger().info("[JourneyMap]: Purging Mob Icon Images");
/* 258 */     MobIconCache.clearCache();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class_1043 getTexture(class_2960 location) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: ifnonnull -> 6
/*     */     //   4: aconst_null
/*     */     //   5: areturn
/*     */     //   6: invokestatic method_1551 : ()Lnet/minecraft/class_310;
/*     */     //   9: invokevirtual method_1531 : ()Lnet/minecraft/class_1060;
/*     */     //   12: astore_1
/*     */     //   13: aload_1
/*     */     //   14: checkcast journeymap/common/accessors/TextureManagerAccess
/*     */     //   17: aload_0
/*     */     //   18: invokeinterface journeymap$getTex : (Lnet/minecraft/class_2960;)Lnet/minecraft/class_1044;
/*     */     //   23: astore_2
/*     */     //   24: aload_2
/*     */     //   25: ifnull -> 40
/*     */     //   28: aload_2
/*     */     //   29: instanceof net/minecraft/class_1049
/*     */     //   32: ifeq -> 68
/*     */     //   35: aload_2
/*     */     //   36: checkcast net/minecraft/class_1049
/*     */     //   39: astore_3
/*     */     //   40: aload_0
/*     */     //   41: invokestatic resolveImage : (Lnet/minecraft/class_2960;)Lnet/minecraft/class_1011;
/*     */     //   44: astore #4
/*     */     //   46: aload #4
/*     */     //   48: ifnull -> 68
/*     */     //   51: new net/minecraft/class_1043
/*     */     //   54: dup
/*     */     //   55: aconst_null
/*     */     //   56: aload #4
/*     */     //   58: invokespecial <init> : (Ljava/util/function/Supplier;Lnet/minecraft/class_1011;)V
/*     */     //   61: astore_2
/*     */     //   62: aload_1
/*     */     //   63: aload_0
/*     */     //   64: aload_2
/*     */     //   65: invokevirtual method_4616 : (Lnet/minecraft/class_2960;Lnet/minecraft/class_1044;)V
/*     */     //   68: aload_2
/*     */     //   69: checkcast net/minecraft/class_1043
/*     */     //   72: areturn
/*     */     //   73: astore_3
/*     */     //   74: invokestatic getLogger : ()Lorg/apache/logging/log4j/Logger;
/*     */     //   77: ldc 'Not a proper texture for {}'
/*     */     //   79: aload_0
/*     */     //   80: invokeinterface error : (Ljava/lang/String;Ljava/lang/Object;)V
/*     */     //   85: aconst_null
/*     */     //   86: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #263	-> 0
/*     */     //   #265	-> 4
/*     */     //   #268	-> 6
/*     */     //   #269	-> 13
/*     */     //   #286	-> 24
/*     */     //   #288	-> 40
/*     */     //   #289	-> 46
/*     */     //   #291	-> 51
/*     */     //   #292	-> 62
/*     */     //   #297	-> 68
/*     */     //   #299	-> 73
/*     */     //   #301	-> 74
/*     */     //   #303	-> 85
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   46	22	4	image	Lnet/minecraft/class_1011;
/*     */     //   74	11	3	e	Ljava/lang/Exception;
/*     */     //   0	87	0	location	Lnet/minecraft/class_2960;
/*     */     //   13	74	1	textureManager	Lnet/minecraft/class_1060;
/*     */     //   24	63	2	textureObject	Lnet/minecraft/class_1044;
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   68	72	73	java/lang/Exception
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class_1043 getWaypointIcon(class_2960 location) {
/* 314 */     if (!"journeymap".equals(location.method_12836())) {
/*     */       
/* 316 */       class_1060 manager = class_310.method_1551().method_1531();
/* 317 */       class_2960 fakeResource = modTextureMap.get(location);
/* 318 */       if (fakeResource == null || ((TextureManagerAccess)manager).journeymap$getTex(fakeResource) == null) {
/*     */ 
/*     */         
/* 321 */         fakeResource = class_2960.method_60655("fake", location.method_12832());
/* 322 */         modTextureMap.put(location, fakeResource);
/* 323 */         class_1011 nativeImage = resolveImage(location);
/*     */ 
/*     */         
/* 326 */         try { class_1011 img = ImageUtil.getScaledImage(4.0F, nativeImage, false);
/* 327 */           class_1043 scaledTexture = new class_1043(null, img);
/* 328 */           manager.method_4616(fakeResource, (class_1044)scaledTexture);
/* 329 */           ((TextureAccess)scaledTexture).journeymap$setDisplayHeight(nativeImage.method_4323());
/* 330 */           ((TextureAccess)scaledTexture).journeymap$setDisplayWidth(nativeImage.method_4323());
/* 331 */           if (nativeImage != null) nativeImage.close();  } catch (Throwable throwable) { if (nativeImage != null)
/*     */             try { nativeImage.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; } 
/* 333 */       }  return (class_1043)((TextureManagerAccess)manager).journeymap$getTex(fakeResource);
/*     */     } 
/* 335 */     return getTexture(location);
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
/*     */   public static <T extends class_1044> Future<T> scheduleTextureTask(Callable<T> textureTask) {
/* 348 */     return texExec.submit(textureTask);
/*     */   }
/*     */ 
/*     */   
/* 352 */   public static final Map<String, class_2960> waypointIconCache = Collections.synchronizedMap(new HashMap<String, class_2960>()
/*     */       {
/*     */       
/*     */       });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void reset() {
/* 364 */     playerSkins.clear();
/* 365 */     colorizedWPIconMap.clear();
/* 366 */     dynamicTextureMap.clear();
/* 367 */     modTextureMap.clear();
/* 368 */     waypointIconMap.clear();
/* 369 */     getTextures("waypoint/icon").forEach(loc -> waypointIconMap.put(loc, getTexture(loc)));
/* 370 */     Arrays.<class_8666>asList(new class_8666[] { OPTIONS_SPRITE, X_SPRITE, X_OUTLINE_SPRITE, TELEPORT_SPRITE, SHARE_SPRITE, POWER_SPRITE, PIN_SPRITE
/* 371 */         }).forEach(TextureCache::resetSprite);
/*     */     
/* 373 */     Arrays.<class_2960>asList(new class_2960[] { ARROW_GLYPH, Question, ColorPicker, ColorPicker2, Deathpoint, GridSquares, GridRegionSquares, GridRegion, Logo, MinimapSquare128, MinimapSquare256, MinimapSquare512, MobDot, MobDotArrow, MobDotChevron, PlayerArrow, PlayerArrowBG, PlayerArrowBG, TileSampleDay, TileSampleNight, TileSampleUnderground, UnknownEntity, SearchIcon, Waypoint, WaypointEdit, WaypointOffscreen, ColorPicker, ColorPicker2, GridSquares, GridRegion, GridRegionSquares, TileSampleDay, TileSampleNight, TileSampleUnderground, UnknownEntity
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 379 */         }).stream().map(TextureCache::getTexture);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Collection<class_2960> getTextures(String path) {
/* 385 */     Map<class_2960, class_3298> textures = class_310.method_1551().method_1478().method_14488("textures/" + path, location -> location.method_12832().endsWith(".png"));
/* 386 */     return textures.keySet();
/*     */   }
/*     */ 
/*     */   
/*     */   static void resetSprite(class_8666 sprites) {
/* 391 */     getTexture(sprites.comp_1605());
/* 392 */     getTexture(sprites.comp_1607());
/* 393 */     getTexture(sprites.comp_1604());
/* 394 */     getTexture(sprites.comp_1606());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void purgeThemeImages(Map<String, class_1043> themeImages) {
/* 404 */     synchronized (themeImages) {
/*     */       
/* 406 */       themeImages.values().forEach(class_1043::close);
/* 407 */       themeImages.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static class_1011 resolveImage(class_2960 location) {
/* 413 */     if (location.method_12836().equals("fake"))
/*     */     {
/* 415 */       return null;
/*     */     }
/*     */     
/* 418 */     class_3300 resourceManager = class_310.method_1551().method_1478();
/*     */     
/*     */     try {
/* 421 */       class_3298 resource = resourceManager.method_14486(location).orElse(null);
/* 422 */       if (resource != null) {
/*     */         
/* 424 */         InputStream is = resource.method_14482();
/* 425 */         return class_1011.method_4309(is);
/*     */       } 
/*     */ 
/*     */       
/* 429 */       JMLogger.logOnce("Image is null: " + String.valueOf(location));
/*     */     
/*     */     }
/* 432 */     catch (FileNotFoundException|NullPointerException e) {
/*     */ 
/*     */       
/*     */       try {
/* 436 */         if ("journeymap".equals(location.method_12836()))
/*     */         {
/*     */ 
/*     */           
/* 440 */           class_3298 imgFile = class_310.method_1551().method_1478().method_14486(class_2960.method_60654("../src/main/resources/assets/journeymap/" + location.method_12832())).orElse(null);
/* 441 */           if (imgFile != null)
/*     */           {
/* 443 */             return class_1011.method_4309(imgFile.method_14482());
/*     */           }
/*     */         }
/*     */       
/* 447 */       } catch (IOException ioe) {
/*     */         
/* 449 */         Journeymap.getLogger().warn("Image not found: " + ioe.getMessage());
/*     */       } 
/* 451 */       return null;
/*     */     }
/* 453 */     catch (Exception e) {
/*     */ 
/*     */ 
/*     */       
/* 457 */       JMLogger.throwLogOnce("Resource not readable: " + String.valueOf(location), e);
/* 458 */       return null;
/*     */     } 
/* 460 */     return null;
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
/*     */   public static class_1043 getThemeTexture(Theme theme, String iconPath) {
/* 472 */     return getSizedThemeTexture(theme, iconPath, 0, 0, false, 1.0F);
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
/*     */   public static class_1043 getThemeTextureFromResource(class_2960 icon) {
/* 484 */     synchronized (themeImages) {
/*     */       
/* 486 */       class_1043 tex = themeImages.get(icon.toString());
/* 487 */       if (tex == null || !((TextureAccess)tex).journeymap$hasImage()) {
/*     */         
/* 489 */         if (tex != null)
/*     */         {
/*     */           
/* 492 */           ImageUtil.closeSafely(tex);
/*     */         }
/*     */         
/* 495 */         class_1011 nativeImage = resolveImage(icon);
/* 496 */         if (nativeImage != null && nativeImage.field_4988 > 0L) {
/*     */           
/* 498 */           tex = new class_1043(null, nativeImage);
/* 499 */           themeImages.put(icon.toString(), tex);
/*     */         }
/*     */         else {
/*     */           
/* 503 */           Journeymap.getLogger().error("Unknown theme image: {}", icon);
/* 504 */           IconSetFileHandler.ensureEntityIconSet("Default");
/* 505 */           return getTexture(UnknownEntity);
/*     */         } 
/*     */       } 
/* 508 */       return tex;
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
/*     */   public static class_1043 getSizedThemeTexture(Theme theme, String iconPath, int width, int height, boolean resize, float alpha) {
/* 525 */     String texName = String.format("%s/%s", new Object[] { theme.directory, iconPath });
/* 526 */     synchronized (themeImages) {
/*     */       
/* 528 */       class_1043 tex = themeImages.get(texName);
/* 529 */       if (tex == null || !((TextureAccess)tex).journeymap$hasImage() || (resize && (width != ((TextureAccess)tex).journeymap$getWidth() || height != ((TextureAccess)tex).journeymap$getHeight()))) {
/*     */         
/* 531 */         if (tex != null)
/*     */         {
/*     */           
/* 534 */           ImageUtil.closeSafely(tex);
/*     */         }
/* 536 */         File parentDir = ThemeLoader.getThemeIconDir();
/* 537 */         class_1011 nativeImage = FileHandler.getIconFromFile(parentDir, theme.directory, iconPath);
/* 538 */         if (nativeImage == null) {
/*     */           
/* 540 */           String resourcePath = String.format("theme/%s/%s", new Object[] { theme.directory, iconPath });
/* 541 */           nativeImage = resolveImage(class_2960.method_60655("journeymap", resourcePath));
/*     */         } 
/*     */         
/* 544 */         if (nativeImage != null && nativeImage.field_4988 > 0L) {
/*     */           
/* 546 */           if (resize || alpha < 1.0F)
/*     */           {
/* 548 */             if (alpha < 1.0F || nativeImage.method_4307() != width || nativeImage.method_4323() != height) {
/*     */               
/* 550 */               class_1011 tmp = ImageUtil.getSizedImage(width, height, nativeImage, false);
/* 551 */               nativeImage.close();
/* 552 */               nativeImage = tmp;
/*     */             } 
/*     */           }
/* 555 */           tex = new class_1043(null, nativeImage);
/* 556 */           themeImages.put(texName, tex);
/*     */         }
/*     */         else {
/*     */           
/* 560 */           Journeymap.getLogger().error("Unknown theme image: " + texName);
/* 561 */           IconSetFileHandler.ensureEntityIconSet("Default");
/* 562 */           return getTexture(UnknownEntity);
/*     */         } 
/*     */       } 
/* 565 */       return tex;
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
/*     */   public static class_1043 getScaledCopy(String texName, class_1043 original, int width, int height, float alpha) {
/* 581 */     synchronized (themeImages) {
/*     */ 
/*     */ 
/*     */       
/* 585 */       class_1043 tex = themeImages.get(texName);
/* 586 */       if (tex == null || !((TextureAccess)tex).journeymap$hasImage() || width != ((TextureAccess)tex).journeymap$getWidth() || height != ((TextureAccess)tex).journeymap$getHeight())
/*     */       {
/* 588 */         if (original != null) {
/*     */           
/* 590 */           if (alpha < 1.0F || ((TextureAccess)original).journeymap$getWidth() != width || ((TextureAccess)original).journeymap$getHeight() != height)
/*     */           {
/* 592 */             tex = new class_1043(null, ImageUtil.getSizedImage(width, height, original.method_4525(), true));
/* 593 */             themeImages.put(texName, tex);
/*     */           }
/*     */           else
/*     */           {
/* 597 */             return original;
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 602 */           Journeymap.getLogger().error("Unable to get scaled image: " + texName);
/* 603 */           return getTexture(UnknownEntity);
/*     */         } 
/*     */       }
/* 606 */       return tex;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static class_1043 getColorizedWaypointIcon(String id) {
/* 626 */     ClientWaypointImpl holder = WaypointStore.getInstance().get(id);
/* 627 */     if (holder != null) {
/*     */       
/* 629 */       class_2960 location = holder.getTextureResource();
/* 630 */       int color = holder.getIconColor().intValue();
/* 631 */       class_1060 textureManager = class_310.method_1551().method_1531();
/*     */       
/* 633 */       if (holder.hasCustomIconColor() || "journeymap".equals(location.method_12836())) {
/*     */         
/* 635 */         class_1011 image = resolveImage(location);
/* 636 */         class_1011 nativeImage = (image != null) ? image : resolveImage(Waypoint);
/* 637 */         if (colorizedWPIconMap.get(id) == null && nativeImage != null && nativeImage.field_4988 != 0L) {
/*     */           
/* 639 */           RenderWrapper.recordRenderQueue(() -> {
/*     */                 class_1011 coloredImage = ImageUtil.recolorImage(nativeImage, color);
/*     */                 class_1043 texture = new class_1043(null, coloredImage);
/*     */                 colorizedWPIconMap.put(id, texture);
/*     */                 nativeImage.close();
/*     */               });
/* 645 */           return null;
/*     */         } 
/*     */ 
/*     */         
/* 649 */         return colorizedWPIconMap.get(id);
/*     */       } 
/*     */       
/* 652 */       return (class_1043)((TextureManagerAccess)textureManager).journeymap$getTex(location);
/*     */     } 
/* 654 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class_1043 getPlayerSkin(GameProfile profile) {
/* 665 */     return IgnSkin.getFace(profile);
/*     */   }
/*     */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\texture\TextureCache.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */