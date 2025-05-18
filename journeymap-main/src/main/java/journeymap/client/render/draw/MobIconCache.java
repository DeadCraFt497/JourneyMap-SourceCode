/*      */ package journeymap.client.render.draw;
/*      */ 
/*      */ import com.google.common.collect.ImmutableList;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Objects;
/*      */ import java.util.Optional;
/*      */ import java.util.Set;
/*      */ import journeymap.api.services.Services;
/*      */ import journeymap.api.v2.client.util.tuple.Tuple2;
/*      */ import journeymap.client.JourneymapClient;
/*      */ import journeymap.client.cartography.color.RGB;
/*      */ import journeymap.client.io.FileHandler;
/*      */ import journeymap.client.model.entity.EntityDTO;
/*      */ import journeymap.client.model.entity.GeckoLibHelper;
/*      */ import journeymap.client.texture.IgnSkin;
/*      */ import journeymap.client.texture.ImageUtil;
/*      */ import journeymap.client.texture.TextureAccess;
/*      */ import journeymap.client.texture.TextureCache;
/*      */ import journeymap.common.Journeymap;
/*      */ import journeymap.common.accessors.NativeImageAccess;
/*      */ import journeymap.common.log.LogFormatter;
/*      */ import journeymap.common.mixin.client.AgeableMobRendererAccessor;
/*      */ import journeymap.common.mixin.client.EnderDragonModelMixin;
/*      */ import journeymap.common.mixin.client.EnderDragonRendererMixin;
/*      */ import journeymap.common.mixin.client.ModelPartMixin;
/*      */ import journeymap.common.mixin.client.PufferfishRendererMixin;
/*      */ import journeymap.common.mixin.client.RabbitModelMixin;
/*      */ import journeymap.common.mixin.client.SalmonRendererInvoker;
/*      */ import journeymap.common.mixin.client.TropicalFishRendererMixin;
/*      */ import journeymap.common.util.ReflectionHelper;
/*      */ import net.minecraft.class_10042;
/*      */ import net.minecraft.class_10076;
/*      */ import net.minecraft.class_1011;
/*      */ import net.minecraft.class_1043;
/*      */ import net.minecraft.class_1297;
/*      */ import net.minecraft.class_1309;
/*      */ import net.minecraft.class_1474;
/*      */ import net.minecraft.class_2960;
/*      */ import net.minecraft.class_310;
/*      */ import net.minecraft.class_3298;
/*      */ import net.minecraft.class_3300;
/*      */ import net.minecraft.class_3850;
/*      */ import net.minecraft.class_3851;
/*      */ import net.minecraft.class_3852;
/*      */ import net.minecraft.class_3882;
/*      */ import net.minecraft.class_4587;
/*      */ import net.minecraft.class_5603;
/*      */ import net.minecraft.class_583;
/*      */ import net.minecraft.class_596;
/*      */ import net.minecraft.class_599;
/*      */ import net.minecraft.class_625;
/*      */ import net.minecraft.class_630;
/*      */ import net.minecraft.class_7923;
/*      */ import net.minecraft.class_895;
/*      */ import net.minecraft.class_897;
/*      */ import net.minecraft.class_898;
/*      */ import net.minecraft.class_922;
/*      */ import net.minecraft.class_936;
/*      */ import net.minecraft.class_938;
/*      */ import net.minecraft.class_959;
/*      */ import net.minecraft.class_9990;
/*      */ import org.joml.Vector2f;
/*      */ import org.joml.Vector3f;
/*      */ import org.joml.Vector3fc;
/*      */ import org.joml.Vector4f;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class MobIconCache
/*      */ {
/*      */   private static final String MOB_ICON_FILE_SUFFIX = ".png";
/*      */   private static final String OUTLINED_SUFFIX = "_outlined";
/*   90 */   private static HashMap<class_2960, IconTexture> mobsIcons = null;
/*   91 */   private static final Map<class_2960, class_1043> webMapIconCache = Collections.synchronizedMap(new HashMap<>());
/*   92 */   private static class_1011 markerMask = null;
/*      */ 
/*      */ 
/*      */   
/*      */   public static class_1043 getWebMapIcon(class_2960 loc) {
/*   97 */     return webMapIconCache.get(loc);
/*      */   }
/*      */ 
/*      */   
/*      */   public static Tuple2<class_2960, class_1043> getMobIcon(EntityDTO dto, boolean outlined) {
/*  102 */     ensureMobIconsLoaded();
/*      */     
/*  104 */     class_2960 mobLocation = dto.entityTypeLocation;
/*  105 */     addIconIfMissing(dto, mobLocation);
/*  106 */     IconTexture icon = mobsIcons.get(mobLocation);
/*      */     
/*  108 */     if (((JourneymapClient.getInstance().getCoreProperties()).legacyIcons.get().booleanValue() || dto.hasCustomIcon || icon == null) && dto.entityIconLocation != null) {
/*      */       
/*      */       try {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  115 */         class_1043 tex = TextureCache.getTexture(dto.entityIconLocation);
/*  116 */         if (tex != null && ((TextureAccess)tex).journeymap$hasImage())
/*      */         {
/*  118 */           mobsIcons.put(dto.entityIconLocation, new IconTexture(tex, null));
/*  119 */           webMapIconCache.put(dto.entityIconLocation, tex);
/*  120 */           return new Tuple2(dto.entityIconLocation, tex);
/*      */         }
/*      */       
/*  123 */       } catch (Exception e) {
/*      */         
/*  125 */         Journeymap.getLogger().error("Error getting texture location for texture {}", dto.entityTextureLocation, e);
/*      */       } 
/*      */     }
/*  128 */     if (icon == null)
/*      */     {
/*  130 */       return null;
/*      */     }
/*  132 */     if (outlined) {
/*      */       
/*  134 */       webMapIconCache.put(mobLocation, icon.outlined);
/*  135 */       return new Tuple2(mobLocation, icon.outlined);
/*      */     } 
/*      */ 
/*      */     
/*  139 */     webMapIconCache.put(mobLocation, icon.solid);
/*  140 */     return new Tuple2(mobLocation, icon.solid);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void ensureMobIconsLoaded() {
/*  146 */     if (mobsIcons == null) {
/*      */       
/*  148 */       mobsIcons = new HashMap<>();
/*  149 */       markerMask = TextureCache.getTexture(TextureCache.MobIconMask).method_4525();
/*      */       
/*  151 */       class_3300 resourceManager = class_310.method_1551().method_1478();
/*      */ 
/*      */       
/*  154 */       Set<Map.Entry<class_2960, List<class_3298>>> resourceStacks = resourceManager.method_41265("icon/entity", loc -> (loc.method_12832().endsWith(".png") && loc.method_12836().equals("journeymap"))).entrySet();
/*  155 */       for (Map.Entry<class_2960, List<class_3298>> resources : resourceStacks) {
/*      */         
/*  157 */         Map<class_2960, IconTexture> map = new HashMap<>();
/*  158 */         for (class_3298 resource : resources.getValue()) {
/*      */ 
/*      */           
/*      */           try {
/*  162 */             class_1011 icon = FileHandler.getImageFromStream(resource.method_14482());
/*  163 */             String[] path = ((class_2960)resources.getKey()).method_12832().split("/");
/*  164 */             if (path.length == 4)
/*      */             {
/*  166 */               String mobName = path[3];
/*  167 */               boolean outlined = mobName.endsWith("_outlined.png");
/*  168 */               if (outlined) {
/*      */                 
/*  170 */                 mobName = mobName.replaceAll("_outlined.png", "");
/*      */               }
/*      */               else {
/*      */                 
/*  174 */                 mobName = mobName.replaceAll(".png", "");
/*      */               } 
/*  176 */               class_2960 mobLoc = class_2960.method_60655(path[2], mobName);
/*  177 */               if (!mobsIcons.containsKey(mobLoc))
/*      */               {
/*  179 */                 addIcon(map, mobLoc, icon, outlined);
/*      */               }
/*      */             }
/*      */           
/*  183 */           } catch (Throwable t) {
/*      */             
/*  185 */             Journeymap.getLogger().error("Could not load Mob icon: {}", LogFormatter.toString(t));
/*      */           } 
/*      */         } 
/*  188 */         addMissingSolidOrOutlined(map);
/*  189 */         mobsIcons.putAll(map);
/*      */       } 
/*      */       
/*  192 */       File[] domainsDirs = FileHandler.getMobIconsDomainsDirectories();
/*  193 */       Map<class_2960, IconTexture> icons = new HashMap<>();
/*  194 */       for (File domainDir : domainsDirs) {
/*      */         
/*  196 */         if (class_2960.method_20209(domainDir.getName())) {
/*      */ 
/*      */ 
/*      */           
/*  200 */           File[] mobsFiles = domainDir.listFiles((dir, name) -> name.endsWith(".png"));
/*      */           
/*  202 */           if (mobsFiles != null)
/*      */           {
/*  204 */             for (File mobFile : mobsFiles) {
/*      */ 
/*      */               
/*      */               try {
/*  208 */                 String mobName = mobFile.getName();
/*  209 */                 boolean outlined = mobName.endsWith("_outlined.png");
/*  210 */                 if (outlined) {
/*      */                   
/*  212 */                   mobName = mobName.replaceAll("_outlined.png", "");
/*      */                 }
/*      */                 else {
/*      */                   
/*  216 */                   mobName = mobName.replaceAll(".png", "");
/*      */                 } 
/*  218 */                 class_1011 icon = FileHandler.getImageFromFile(mobFile);
/*  219 */                 class_2960 mobLoc = class_2960.method_60655(domainDir.getName(), mobName);
/*  220 */                 if (!mobsIcons.containsKey(mobLoc))
/*      */                 {
/*  222 */                   addIcon(icons, mobLoc, icon, outlined);
/*      */                 }
/*      */               }
/*  225 */               catch (Throwable t) {
/*      */                 
/*  227 */                 Journeymap.getLogger().error("Could not load Mob icon: {}", LogFormatter.toString(t));
/*      */               } 
/*      */             }  } 
/*      */         } 
/*      */       } 
/*  232 */       addMissingSolidOrOutlined(icons);
/*  233 */       mobsIcons.putAll(icons);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void clearCache() {
/*  239 */     webMapIconCache.clear();
/*  240 */     if (mobsIcons != null && !mobsIcons.isEmpty())
/*      */     {
/*  242 */       mobsIcons.forEach((resource, tex) -> {
/*      */             if (tex != null) {
/*      */               class_310.method_1551().method_1531().method_4615(resource);
/*      */               
/*      */               tex.remove();
/*      */             } 
/*      */           });
/*      */     }
/*  250 */     mobsIcons = null;
/*      */   }
/*      */ 
/*      */   
/*      */   private static void addIcon(Map<class_2960, IconTexture> icons, class_2960 mob, class_1011 icon, boolean outlined) {
/*  255 */     IconTexture iconTexture = icons.computeIfAbsent(mob, m -> new IconTexture());
/*  256 */     class_1043 texture = new class_1043(null, icon);
/*  257 */     if (outlined) {
/*      */       
/*  259 */       iconTexture.outlined = texture;
/*      */     }
/*      */     else {
/*      */       
/*  263 */       iconTexture.solid = texture;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void addMissingSolidOrOutlined(Map<class_2960, IconTexture> icons) {
/*  269 */     for (IconTexture icon : icons.values()) {
/*      */       
/*  271 */       if (icon.solid == null) {
/*      */         
/*  273 */         icon.solid = icon.outlined; continue;
/*      */       } 
/*  275 */       if (icon.outlined == null)
/*      */       {
/*  277 */         icon.outlined = icon.solid;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void addIconIfMissing(EntityDTO dto, class_2960 mobLocation) {
/*  284 */     if (!mobsIcons.containsKey(mobLocation)) {
/*      */       
/*  286 */       class_1011 entityIcon = null;
/*  287 */       boolean saveToDisk = true;
/*  288 */       class_1297 entity = dto.getEntityRef().get();
/*  289 */       if (entity != null) {
/*      */         
/*  291 */         class_898 renderManager = class_310.method_1551().method_1561();
/*  292 */         class_897 renderer = renderManager.method_3953(entity);
/*      */ 
/*      */         
/*  295 */         class_2960 textureLocation = getTextureLocation(renderManager.method_3953(entity), entity, dto);
/*  296 */         class_1043 tex = TextureCache.getTexture(textureLocation);
/*  297 */         if (tex == null || !((TextureAccess)tex).journeymap$hasImage()) {
/*      */           
/*  299 */           mobsIcons.put(mobLocation, null);
/*      */           return;
/*      */         } 
/*  302 */         class_1011 entityImage = tex.method_4525();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  308 */         if (ReflectionHelper.isInstanceOf(renderer, new String[] { "software.bernie.geckolib.renderer.GeoEntityRenderer" }))
/*      */         
/*  310 */         { boolean isFish = entity instanceof net.minecraft.class_1422;
/*  311 */           List<class_630> head = GeckoLibHelper.getModelParts(entity, mobLocation, !isFish);
/*  312 */           entityIcon = getIconFromHead(head, entityImage, mobLocation, isFish);
/*  313 */           if (entityIcon == null)
/*      */           {
/*  315 */             entityIcon = getIconFromHead(head, entityImage, mobLocation, !isFish);
/*      */           } }
/*      */         
/*  318 */         else if (renderer instanceof class_922) { class_583 class_583; class_922<?, ?, ?> mobRenderer = (class_922<?, ?, ?>)renderer;
/*      */ 
/*      */ 
/*      */           
/*  322 */           if (renderer instanceof class_9990) { class_9990<?, ?, ?> ageableMobRenderer = (class_9990<?, ?, ?>)renderer;
/*      */             
/*  324 */             AgeableMobRendererAccessor r = (AgeableMobRendererAccessor)ageableMobRenderer;
/*  325 */             class_583 = r.getAdultModel(); }
/*      */           
/*  327 */           else if (renderer instanceof class_936) { class_936 pufferfishRenderer = (class_936)renderer;
/*      */             
/*  329 */             PufferfishRendererMixin r = (PufferfishRendererMixin)pufferfishRenderer;
/*  330 */             class_583 = r.getBigModel(); }
/*      */           
/*  332 */           else if (renderer instanceof class_938) { class_938 salmonRenderer = (class_938)renderer;
/*      */             
/*  334 */             SalmonRendererInvoker r = (SalmonRendererInvoker)salmonRenderer;
/*  335 */             class_599 class_599 = r.getMediumSalmonModel(); }
/*      */           
/*      */           else
/*      */           
/*  339 */           { class_583 = mobRenderer.method_4038(); }
/*      */ 
/*      */ 
/*      */           
/*  343 */           ImmutableList immutableList = ImmutableList.of();
/*  344 */           if (class_583 instanceof net.minecraft.class_609 || class_583 instanceof net.minecraft.class_576 || class_583 instanceof net.minecraft.class_604 || class_583 instanceof net.minecraft.class_565)
/*      */           
/*  346 */           { List list = class_583.method_63513(); }
/*      */           
/*  348 */           else if (class_583 instanceof class_596) { class_596 model = (class_596)class_583;
/*      */             
/*  350 */             RabbitModelMixin m = (RabbitModelMixin)model;
/*      */ 
/*      */ 
/*      */             
/*  354 */             immutableList = ImmutableList.of(m.getHead()); }
/*      */           
/*  356 */           else if (class_583 instanceof net.minecraft.class_3884 && class_583 instanceof class_3882) { class_3882 headedModel = (class_3882)class_583;
/*      */             
/*  358 */             if (entity instanceof class_3851) { String type; class_3851 villager = (class_3851)entity;
/*      */ 
/*      */               
/*  361 */               if (entity instanceof net.minecraft.class_1646) {
/*      */                 
/*  363 */                 type = "villager";
/*      */               }
/*  365 */               else if (entity instanceof net.minecraft.class_1641) {
/*      */                 
/*  367 */                 type = "zombie_villager";
/*      */               }
/*      */               else {
/*      */                 
/*  371 */                 type = null;
/*      */               } 
/*  373 */               if (type != null) {
/*      */                 
/*  375 */                 class_3850 data = villager.method_7231();
/*  376 */                 class_3852 profession = (class_3852)data.comp_3521().comp_349();
/*  377 */                 class_2960 professionKey = class_7923.field_41195.method_10221(profession);
/*  378 */                 if (professionKey != class_3852.field_17051.method_29177())
/*      */                 {
/*      */                   
/*  381 */                   class_2960 professionRes = professionKey.method_45134($$1x -> "textures/entity/" + type + "/profession/" + $$1x + ".png");
/*  382 */                   class_1011 altEntityImage = mergeImages(entityImage, professionRes);
/*  383 */                   entityIcon = getIconFromHead(headedModel.method_2838(), altEntityImage, mobLocation, false);
/*  384 */                   if (altEntityImage != entityImage)
/*      */                   {
/*  386 */                     altEntityImage.close();
/*      */                   }
/*      */                 }
/*      */               
/*      */               }  }
/*      */              }
/*  392 */           else if (renderer instanceof class_959) { class_959 tropicalFishRenderer = (class_959)renderer;
/*      */             
/*  394 */             if (entity instanceof class_1474) { class_583<class_10076> tropicalFishModel; class_1011 altEntityImage; class_1474 tropicalFish = (class_1474)entity;
/*      */ 
/*      */ 
/*      */               
/*  398 */               if (tropicalFish.method_66681().method_47867() == class_1474.class_7991.field_41574) {
/*      */                 
/*  400 */                 tropicalFishModel = ((TropicalFishRendererMixin)tropicalFishRenderer).getModelA();
/*  401 */                 altEntityImage = mergeImages(entityImage, class_2960.method_60654("textures/entity/fish/tropical_a_pattern_1.png"), 16711680, 16777215);
/*      */               }
/*      */               else {
/*      */                 
/*  405 */                 tropicalFishModel = ((TropicalFishRendererMixin)tropicalFishRenderer).getModelB();
/*  406 */                 altEntityImage = mergeImages(entityImage, class_2960.method_60654("textures/entity/fish/tropical_b_pattern_4.png"), 8991416, 16701501);
/*      */               } 
/*      */               
/*  409 */               class_630 body = tropicalFishModel.method_62104("root").orElse(null);
/*  410 */               entityIcon = getIconFromHead(body, altEntityImage, mobLocation, true);
/*      */               
/*  412 */               if (altEntityImage != entityImage)
/*      */               {
/*  414 */                 altEntityImage.close();
/*      */               } }
/*      */             
/*      */              }
/*  418 */           else if (entity instanceof net.minecraft.class_1422 || class_583 instanceof net.minecraft.class_889)
/*      */           
/*  420 */           { immutableList = ImmutableList.of(class_583.method_63512()); }
/*      */ 
/*      */ 
/*      */           
/*  424 */           if (immutableList.isEmpty()) {
/*      */             
/*  426 */             Optional<class_630> headPart = class_583.method_62104("head");
/*  427 */             if (headPart.isEmpty())
/*      */             {
/*  429 */               headPart = class_583.method_62104("body");
/*      */             }
/*      */             
/*  432 */             Objects.requireNonNull(class_583); immutableList = ImmutableList.of(headPart.orElseGet(class_583::method_63512));
/*      */           } 
/*      */ 
/*      */           
/*  436 */           if (entityIcon == null && !immutableList.isEmpty()) {
/*      */             
/*  438 */             boolean rotated = (class_583 instanceof net.minecraft.class_9947 || class_583 instanceof net.minecraft.class_578 || class_583 instanceof net.minecraft.class_7751 || class_583 instanceof net.minecraft.class_9945 || class_583 instanceof net.minecraft.class_584 || class_583 instanceof net.minecraft.class_889 || entity instanceof net.minecraft.class_1422);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  446 */             entityIcon = getIconFromHead((Iterable<class_630>)immutableList, entityImage, mobLocation, rotated);
/*  447 */             if (entityIcon == null)
/*      */             {
/*  449 */               entityIcon = getIconFromHead((Iterable<class_630>)immutableList, entityImage, mobLocation, !rotated);
/*      */             }
/*      */           } else {
/*  452 */             class_583<?> class_5831 = mobRenderer.method_4038(), model = class_5831; if (class_5831 instanceof class_583 && 
/*  453 */               ReflectionHelper.isInstanceOf(mobRenderer.method_4038(), new String[] { "com.cobblemon.mod.common.client.render.models.blockbench.npc.PosableNPCModel" })) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  460 */               saveToDisk = false;
/*  461 */               class_1043 texture = TextureCache.getTexture(textureLocation);
/*  462 */               entityIcon = IgnSkin.cropToFace(texture.method_4525()).method_4525();
/*  463 */               entityIcon = cropImage(entityIcon);
/*  464 */               entityIcon = resizeImage(entityIcon, 4, false);
/*  465 */               maskImage(entityIcon);
/*      */             } 
/*      */           }  }
/*  468 */         else if (renderer instanceof class_895) { class_895 dragonRenderer = (class_895)renderer;
/*      */           
/*  470 */           class_625 model = ((EnderDragonRendererMixin)dragonRenderer).getModel();
/*  471 */           entityIcon = getIconFromHead(((EnderDragonModelMixin)model).getHead(), entityImage, mobLocation, false); }
/*      */       
/*      */       } 
/*      */       
/*  475 */       if (entityIcon != null && mobLocation != null) {
/*      */         
/*  477 */         class_1011 outlined = generateOutlined(entityIcon);
/*  478 */         class_2960 outlinedLocation = mobLocation.method_45136(mobLocation.method_12832() + "_outlined");
/*  479 */         mobsIcons.put(mobLocation, new IconTexture(new class_1043(null, entityIcon), new class_1043(null, outlined)));
/*  480 */         if (saveToDisk) {
/*      */           
/*  482 */           File domainDir = FileHandler.getMobIconsDomainsDirectory(mobLocation);
/*  483 */           File iconFile = new File(domainDir, mobLocation.method_12832() + ".png");
/*  484 */           File outlinedFile = new File(domainDir, outlinedLocation.method_12832() + ".png");
/*      */           
/*      */           try {
/*  487 */             if (!iconFile.exists())
/*      */             {
/*  489 */               entityIcon.method_4325(iconFile);
/*      */             }
/*      */           }
/*  492 */           catch (IOException e) {
/*      */             
/*  494 */             String error = "Could not save icon to file: " + String.valueOf(iconFile) + ": " + e.getMessage();
/*  495 */             Journeymap.getLogger().error(error);
/*      */           } 
/*      */ 
/*      */           
/*      */           try {
/*  500 */             if (!outlinedFile.exists())
/*      */             {
/*  502 */               outlined.method_4325(outlinedFile);
/*      */             }
/*      */           }
/*  505 */           catch (IOException e) {
/*      */             
/*  507 */             String error = "Could not save outlined icon to file: " + String.valueOf(outlinedFile) + ": " + e.getMessage();
/*  508 */             Journeymap.getLogger().error(error);
/*      */           }
/*      */         
/*      */         } 
/*      */       } else {
/*      */         
/*  514 */         mobsIcons.put(mobLocation, null);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static class_1011 mergeImages(class_1011 bottomImage, class_2960 top) {
/*  521 */     return mergeImages(bottomImage, top, 16777215, 16777215);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class_1011 mergeImages(class_1011 bottomImage, class_2960 top, int bottomTint, int topTint) {
/*  527 */     class_1011 newImage = null;
/*      */     
/*      */     try {
/*  530 */       class_1043 topTex = TextureCache.getTexture(top);
/*  531 */       if (topTex == null || !((TextureAccess)topTex).journeymap$hasImage())
/*      */       {
/*  533 */         return bottomImage;
/*      */       }
/*  535 */       class_1011 topImage = topTex.method_4525();
/*      */       
/*  537 */       if (bottomImage.method_4307() != ((TextureAccess)topTex).journeymap$getWidth() || bottomImage.method_4323() != ((TextureAccess)topTex).journeymap$getHeight())
/*      */       {
/*  539 */         return bottomImage;
/*      */       }
/*      */       
/*  542 */       newImage = ImageUtil.getNewBlankImage(bottomImage.method_4307(), bottomImage.method_4323());
/*  543 */       for (int y = 0; y < bottomImage.method_4323(); y++) {
/*      */         
/*  545 */         for (int x = 0; x < bottomImage.method_4307(); x++) {
/*      */           
/*  547 */           int color = topImage.method_61940(x, y);
/*  548 */           if ((color & 0xFF000000) == 0) {
/*      */             
/*  550 */             color = RGB.tintRgba(bottomImage.method_61940(x, y), bottomTint);
/*      */           }
/*      */           else {
/*      */             
/*  554 */             color = RGB.tintRgba(color, topTint);
/*      */           } 
/*  556 */           newImage.method_61941(x, y, color);
/*      */         } 
/*      */       } 
/*  559 */       return newImage;
/*      */     }
/*  561 */     catch (Exception e) {
/*      */       
/*  563 */       if (newImage != null)
/*      */       {
/*  565 */         newImage.close();
/*      */       }
/*      */ 
/*      */       
/*  569 */       return bottomImage;
/*      */     } 
/*      */   }
/*      */   
/*      */   private static <T extends class_1297> class_2960 getTextureLocation(class_897<? super T, ?> renderer, T entity, EntityDTO dto) {
/*  574 */     if (Services.COMMON_SERVICE.isModLoaded("entity_texture_features"))
/*      */     {
/*  576 */       if (renderer instanceof class_922) { class_922<?, ?, ?> mobRenderer = (class_922)renderer;
/*      */         
/*  578 */         if (entity instanceof class_1309) {
/*      */           
/*  580 */           class_922<?, ?, ?> class_922 = mobRenderer;
/*  581 */           class_10042 renderState = (class_10042)class_922.method_55269();
/*  582 */           class_922.method_62355((class_1309)entity, renderState, 1.0F);
/*  583 */           return ((LivingEntityRendererETFTextureGetter)mobRenderer).getETFTextureLocation(renderState);
/*      */         }  }
/*      */     
/*      */     }
/*  587 */     return dto.entityTextureLocation;
/*      */   }
/*      */ 
/*      */   
/*      */   private static class_1011 getIconFromHead(class_630 head, class_1011 entityImage, class_2960 mobLocation, boolean rotated) {
/*  592 */     if (head == null)
/*      */     {
/*  594 */       return null;
/*      */     }
/*      */     
/*  597 */     return getIconFromHead((Iterable<class_630>)ImmutableList.of(head), entityImage, mobLocation, rotated);
/*      */   }
/*      */ 
/*      */   
/*      */   private static class_1011 getIconFromHead(Iterable<class_630> heads, class_1011 entityImage, class_2960 mobLocation, boolean rotated) {
/*  602 */     if (heads == null)
/*      */     {
/*  604 */       return null;
/*      */     }
/*      */     
/*  607 */     List<class_630> CEMFixedHeads = new ArrayList<>();
/*  608 */     heads.forEach(head -> ((ModelPartMixin)head).getChildren().forEach(()));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  615 */     if (!CEMFixedHeads.isEmpty())
/*      */     {
/*  617 */       heads = CEMFixedHeads;
/*      */     }
/*      */     
/*  620 */     Map<class_630, class_5603> headPoses = new HashMap<>();
/*  621 */     heads.forEach(head -> headPoses.put(head, head.method_32084()));
/*  622 */     heads.forEach(class_630::method_41923);
/*      */     
/*  624 */     int scale = 4;
/*  625 */     List<HeadPolygon> facePolygons = new ArrayList<>();
/*  626 */     heads.forEach(head -> head.method_35745(new class_4587(), ()));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  638 */     heads.forEach(head -> head.method_32085((class_5603)headPoses.get(head)));
/*      */     
/*  640 */     if (facePolygons.isEmpty())
/*      */     {
/*  642 */       return null;
/*      */     }
/*      */     
/*  645 */     facePolygons.sort((p1, p2) -> {
/*      */           float p1AverageZ = ((p1.vertices[0].comp_3186()).z + (p1.vertices[1].comp_3186()).z + (p1.vertices[2].comp_3186()).z + (p1.vertices[3].comp_3186()).z) / 4.0F;
/*      */           
/*      */           float p2AverageZ = ((p2.vertices[0].comp_3186()).z + (p2.vertices[1].comp_3186()).z + (p2.vertices[2].comp_3186()).z + (p2.vertices[3].comp_3186()).z) / 4.0F;
/*      */           
/*      */           float comp = Math.signum(p2AverageZ - p1AverageZ);
/*      */           if (comp == 0.0F) {
/*      */             comp = Math.signum(p2.normal.z - p1.normal.z);
/*      */           }
/*      */           return (int)comp;
/*      */         });
/*  656 */     int minX = Integer.MAX_VALUE;
/*  657 */     int maxX = Integer.MIN_VALUE;
/*  658 */     int minY = Integer.MAX_VALUE;
/*  659 */     int maxY = Integer.MIN_VALUE;
/*  660 */     for (HeadPolygon p : facePolygons) {
/*      */       
/*  662 */       for (class_630.class_618 v : p.vertices) {
/*      */         
/*  664 */         minX = Math.min(minX, Math.round((v.comp_3186()).x));
/*  665 */         maxX = Math.max(maxX, Math.round((v.comp_3186()).x));
/*  666 */         minY = Math.min(minY, Math.round((v.comp_3186()).y));
/*  667 */         maxY = Math.max(maxY, Math.round((v.comp_3186()).y));
/*      */       } 
/*      */     } 
/*      */     
/*  671 */     int headWidth = maxX - minX;
/*  672 */     int headHeight = maxY - minY;
/*      */     
/*  674 */     class_1011 headImage = ImageUtil.getNewBlankImage((headWidth + 1) * 4, (headHeight + 1) * 4);
/*      */ 
/*      */     
/*      */     try {
/*  678 */       for (HeadPolygon p : facePolygons)
/*      */       {
/*  680 */         drawPolygonOnImage(p, minX, minY, entityImage, headImage);
/*      */       }
/*  682 */       headImage = cropImage(headImage);
/*  683 */       if (headImage == null)
/*      */       {
/*  685 */         return null;
/*      */       }
/*  687 */       headImage = resizeImage(headImage, 4, rotated);
/*  688 */       maskImage(headImage);
/*      */     }
/*  690 */     catch (Exception e) {
/*      */       
/*  692 */       Journeymap.getLogger().error("Error creating icon for '{}': {}", mobLocation, e);
/*  693 */       return null;
/*      */     } 
/*      */ 
/*      */     
/*  697 */     return headImage;
/*      */   }
/*      */ 
/*      */   
/*      */   private static void drawPolygonOnImage(HeadPolygon polygon, int originX, int originY, class_1011 from, class_1011 to) {
/*  702 */     if (polygon.vertices.length != 4) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  707 */     int texWidth = from.method_4307();
/*  708 */     int texHeight = from.method_4323();
/*  709 */     float minU = texWidth;
/*  710 */     float maxU = 0.0F;
/*  711 */     float minV = texHeight;
/*  712 */     float maxV = 0.0F;
/*  713 */     float minX = Float.MAX_VALUE;
/*  714 */     float maxX = Float.MIN_VALUE;
/*  715 */     float minY = Float.MAX_VALUE;
/*  716 */     float maxY = Float.MIN_VALUE;
/*  717 */     for (class_630.class_618 vertex : polygon.vertices) {
/*      */       
/*  719 */       minU = Math.min(minU, vertex.comp_3187() * texWidth);
/*  720 */       maxU = Math.max(maxU, vertex.comp_3187() * texWidth);
/*  721 */       minV = Math.min(minV, vertex.comp_3188() * texHeight);
/*  722 */       maxV = Math.max(maxV, vertex.comp_3188() * texHeight);
/*  723 */       minX = Math.min(minX, (vertex.comp_3186()).x);
/*  724 */       maxX = Math.max(maxX, (vertex.comp_3186()).x);
/*  725 */       minY = Math.min(minY, (vertex.comp_3186()).y);
/*  726 */       maxY = Math.max(maxY, (vertex.comp_3186()).y);
/*      */     } 
/*      */     float y;
/*  729 */     for (y = minY; y < maxY; y++) {
/*      */       float x;
/*  731 */       for (x = minX; x < maxX; x++) {
/*      */         
/*  733 */         Vector2f uv = getUVCoordinates(polygon, x, y);
/*  734 */         uv.x = (float)Math.floor((uv.x * texWidth));
/*  735 */         uv.y = (float)Math.floor((uv.y * texHeight));
/*  736 */         if (uv.x >= minU && uv.x < maxU && uv.y >= minV && uv.y < maxV) {
/*      */           
/*  738 */           int c = from.method_61940(
/*  739 */               Math.min(Math.max(0, (int)uv.x), texWidth - 1), 
/*  740 */               Math.min(Math.max(0, (int)uv.y), texHeight - 1));
/*      */           
/*  742 */           if ((c >> 24 & 0xFF) == 255) {
/*      */             
/*  744 */             to.method_61941(Math.round(x) - originX, Math.round(y) - originY, c);
/*      */           }
/*  746 */           else if ((c >> 24 & 0xFF) > 0) {
/*      */             
/*  748 */             ((NativeImageAccess)to).blendPixel(Math.round(x) - originX, Math.round(y) - originY, c);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static Vector2f getUVCoordinates(HeadPolygon polygon, float x, float y) {
/*  757 */     Vector3f p0 = polygon.vertices[0].comp_3186();
/*  758 */     Vector3f p1 = polygon.vertices[1].comp_3186();
/*  759 */     Vector3f p2 = polygon.vertices[2].comp_3186();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  765 */     Vector2f v0 = new Vector2f(p0.x - p2.x, p0.y - p2.y);
/*  766 */     Vector2f v1 = new Vector2f(p1.x - p2.x, p1.y - p2.y);
/*  767 */     Vector2f v2 = new Vector2f(x + 0.5F - p2.x, y + 0.5F - p2.y);
/*  768 */     float den = v0.x * v1.y - v1.x * v0.y;
/*  769 */     float a = (v2.x * v1.y - v1.x * v2.y) / den;
/*  770 */     float b = (v0.x * v2.y - v2.x * v0.y) / den;
/*  771 */     float c = 1.0F - b - a;
/*      */     
/*  773 */     return new Vector2f(a * polygon.vertices[0]
/*  774 */         .comp_3187() + b * polygon.vertices[1].comp_3187() + c * polygon.vertices[2].comp_3187(), a * polygon.vertices[0]
/*  775 */         .comp_3188() + b * polygon.vertices[1].comp_3188() + c * polygon.vertices[2].comp_3188());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class_1011 cropImage(class_1011 image) {
/*  781 */     int width = image.method_4307();
/*  782 */     int height = image.method_4323();
/*  783 */     int left = width;
/*  784 */     int top = height;
/*  785 */     int right = width;
/*  786 */     int bottom = height;
/*      */     
/*  788 */     for (int y = 0; y < height; y++) {
/*      */       
/*  790 */       for (int x = 0; x < width; x++) {
/*      */         
/*  792 */         int c = image.method_61940(x, y);
/*  793 */         if ((c >> 24 & 0xFF) > 0) {
/*      */           
/*  795 */           left = Math.min(left, x);
/*  796 */           top = Math.min(top, y);
/*  797 */           right = Math.min(right, width - x - 1);
/*  798 */           bottom = Math.min(bottom, height - y - 1);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  803 */     if (left == width || top == height) {
/*      */       
/*  805 */       image.close();
/*  806 */       return null;
/*      */     } 
/*      */     
/*  809 */     int newWidth = width - left - right;
/*  810 */     int newHeight = height - top - bottom;
/*  811 */     boolean extraLeft = (newWidth % 2 == 1);
/*  812 */     boolean extraBottom = (newHeight % 2 == 1);
/*  813 */     class_1011 result = new class_1011(newWidth + (extraLeft ? 1 : 0), newHeight + (extraBottom ? 1 : 0), false);
/*  814 */     image.method_47594(result, left, top, 0, 0, newWidth, newHeight, false, false);
/*  815 */     image.close();
/*      */     
/*  817 */     if (extraLeft)
/*      */     {
/*  819 */       result.method_47594(result, newWidth - 1, 0, newWidth, 0, 1, newHeight, false, false);
/*      */     }
/*  821 */     if (extraBottom)
/*      */     {
/*  823 */       result.method_47594(result, 0, newHeight - 1, 0, newHeight, newWidth + (extraLeft ? 1 : 0), 1, false, false);
/*      */     }
/*  825 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   private static class_1011 resizeImage(class_1011 image, int scale, boolean rotated) {
/*  830 */     int width = image.method_4307();
/*  831 */     int height = image.method_4323();
/*  832 */     SizeInfo sizeInfo = getClosestValidSize(width, height, scale);
/*  833 */     CropInfo crop = new CropInfo(0, 0, 0, 0);
/*      */     
/*  835 */     if (!sizeInfo.exact) {
/*      */       
/*  837 */       List<CropInfo> cropsToTry = getSizesToTry(image, scale, rotated);
/*  838 */       for (CropInfo c : cropsToTry) {
/*      */         
/*  840 */         SizeInfo size = getClosestValidSize(width - c.left - c.right, height - c.top - c.bottom, scale);
/*  841 */         if (size.exact) {
/*      */           
/*  843 */           sizeInfo = size;
/*  844 */           crop = c;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } 
/*  850 */     class_1011 result = ImageUtil.getNewBlankImage(sizeInfo.width, sizeInfo.height);
/*  851 */     float finalScale = Math.min(sizeInfo.width / (width - crop.left - crop.right), sizeInfo.height / (height - crop.top - crop.bottom));
/*  852 */     for (int y = 0; y < result.method_4323(); y++) {
/*      */       
/*  854 */       for (int x = 0; x < result.method_4307(); x++) {
/*      */         
/*  856 */         int fromX = Math.round(x / finalScale) + crop.left;
/*  857 */         int fromY = Math.round(y / finalScale) + crop.top;
/*  858 */         if (fromX >= 0 && fromX < width && fromY >= 0 && fromY < height)
/*      */         {
/*      */ 
/*      */           
/*  862 */           result.method_61941(x, y, image.method_61940(fromX, fromY)); } 
/*      */       } 
/*      */     } 
/*  865 */     image.close();
/*  866 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   private static SizeInfo getClosestValidSize(int width, int height, int scale) {
/*  871 */     if (width == 16 * scale && height == 16 * scale)
/*      */     {
/*  873 */       return new SizeInfo(16, 16, true);
/*      */     }
/*      */     
/*  876 */     boolean portrait = (height > width);
/*  877 */     int longest = portrait ? height : width;
/*  878 */     int shortest = portrait ? width : height;
/*  879 */     float ratio = longest / shortest;
/*      */     
/*  881 */     int closestL = 16;
/*  882 */     int closestS = 16;
/*  883 */     float closestDist = Math.abs(ratio - 1.0F);
/*  884 */     boolean exact = false;
/*  885 */     for (int l = 14; l <= 22; l++) {
/*      */       
/*  887 */       int s = Math.round(l / ratio);
/*  888 */       int diag = l * l + s * s;
/*  889 */       if (diag >= 390 && diag <= 650) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  894 */         boolean e = (l % longest / scale == 0 && s % shortest / scale == 0);
/*  895 */         float dist = Math.abs(ratio - l / s);
/*      */         
/*  897 */         if (e || !exact)
/*      */         {
/*  899 */           if (e || dist < closestDist) {
/*      */             
/*  901 */             closestL = l;
/*  902 */             closestS = s;
/*  903 */             exact = e;
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*  908 */     if (portrait)
/*      */     {
/*  910 */       return new SizeInfo(closestS, closestL, exact);
/*      */     }
/*      */ 
/*      */     
/*  914 */     return new SizeInfo(closestL, closestS, exact);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static List<CropInfo> getSizesToTry(class_1011 image, int scale, boolean rotated) {
/*  920 */     int width = image.method_4307();
/*  921 */     int height = image.method_4323();
/*  922 */     boolean portrait = (height > width);
/*  923 */     List<CropInfo> crops = new ArrayList<>();
/*      */     
/*  925 */     for (int y = -1; y < 4; y++) {
/*      */       
/*  927 */       for (int x = -1; x < 4; x++) {
/*      */         
/*  929 */         if (x != 0 || y != 0)
/*      */         {
/*      */ 
/*      */ 
/*      */           
/*  934 */           if (rotated) {
/*      */             
/*  936 */             crops.add(new CropInfo(
/*  937 */                   (x < 0) ? (scale * x) : 0, 
/*  938 */                   (y < 0) ? (scale * y) : 0, 
/*  939 */                   (x > 0) ? (scale * x) : 0, 
/*  940 */                   (y > 0) ? (scale * y) : 0));
/*      */           }
/*      */           else {
/*      */             
/*  944 */             crops.add(new CropInfo(scale * x / 2, scale * y / 2, scale * x / 2, scale * y / 2));
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*  949 */     crops.sort((c1, c2) -> {
/*      */           int c1H = c1.left + c1.right;
/*      */           
/*      */           int c1V = c1.top + c1.bottom;
/*      */           
/*      */           int c2H = c2.left + c2.right;
/*      */           
/*      */           int c2V = c2.top + c2.bottom;
/*      */           
/*      */           int d = c1H + c1V - c2H - c2V;
/*      */           if (d == 0) {
/*      */             if (portrait && c1V > c1H) {
/*      */               return -1;
/*      */             }
/*      */             if (!portrait && c1V < c1H) {
/*      */               return 1;
/*      */             }
/*      */           } 
/*      */           return d;
/*      */         });
/*  969 */     return crops;
/*      */   }
/*      */ 
/*      */   
/*      */   private static void maskImage(class_1011 image) {
/*  974 */     if (markerMask == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  979 */     int cornerX = markerMask.method_4307() / 2 - image.method_4307() / 2;
/*  980 */     int cornerY = markerMask.method_4323() / 2 - image.method_4323() / 2;
/*  981 */     for (int y = 0; y < image.method_4323(); y++) {
/*      */       
/*  983 */       for (int x = 0; x < image.method_4307(); x++) {
/*      */         
/*  985 */         if ((markerMask.method_61940(cornerX + x, cornerY + y) & 0xFF000000) == 0)
/*      */         {
/*  987 */           image.method_61941(x, y, 0);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class_1011 generateOutlined(class_1011 image) {
/*  996 */     class_1011 outline = ImageUtil.getNewBlankImage(image.method_4307() + 2, image.method_4323() + 2);
/*  997 */     for (int y = -1; y < image.method_4323() + 1; y++) {
/*      */       
/*  999 */       for (int x = -1; x < image.method_4307() + 1; x++) {
/*      */         
/* 1001 */         if (!isOpaque(image, x, y)) {
/*      */           
/* 1003 */           boolean opaque = false;
/* 1004 */           opaque = (opaque || isOpaque(image, x - 1, y - 1));
/* 1005 */           opaque = (opaque || isOpaque(image, x + 0, y - 1));
/* 1006 */           opaque = (opaque || isOpaque(image, x + 1, y - 1));
/* 1007 */           opaque = (opaque || isOpaque(image, x - 1, y + 0));
/* 1008 */           opaque = (opaque || isOpaque(image, x + 1, y + 0));
/* 1009 */           opaque = (opaque || isOpaque(image, x - 1, y + 1));
/* 1010 */           opaque = (opaque || isOpaque(image, x + 0, y + 1));
/* 1011 */           opaque = (opaque || isOpaque(image, x + 1, y + 1));
/* 1012 */           if (opaque) {
/*      */             
/* 1014 */             outline.method_61941(x + 1, y + 1, -16777216);
/*      */             
/*      */             continue;
/*      */           } 
/*      */         } 
/* 1019 */         if (x >= 0 && x < image.method_4307() && y >= 0 && y < image.method_4323())
/*      */         {
/*      */ 
/*      */ 
/*      */           
/* 1024 */           outline.method_61941(x + 1, y + 1, image.method_61940(x, y)); } 
/*      */         continue;
/*      */       } 
/*      */     } 
/* 1028 */     return outline;
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean isOpaque(class_1011 image, int x, int y) {
/* 1033 */     if (x < 0 || x >= image.method_4307() || y < 0 || y >= image.method_4323())
/*      */     {
/* 1035 */       return false;
/*      */     }
/* 1037 */     return ((image.method_61940(x, y) & 0xFF000000) != 0);
/*      */   }
/*      */ 
/*      */   
/*      */   private static class IconTexture
/*      */   {
/*      */     public class_1043 solid;
/*      */     
/*      */     public class_1043 outlined;
/*      */     
/*      */     public IconTexture() {
/* 1048 */       this.solid = null;
/* 1049 */       this.outlined = null;
/*      */     }
/*      */ 
/*      */     
/*      */     public IconTexture(class_1043 solid, class_1043 outlined) {
/* 1054 */       this.solid = solid;
/* 1055 */       this.outlined = outlined;
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/* 1060 */       if (this.solid != null && ((TextureAccess)this.solid).journeymap$hasImage())
/*      */       {
/* 1062 */         ImageUtil.closeSafely(this.solid);
/*      */       }
/* 1064 */       if (this.outlined != null && ((TextureAccess)this.outlined).journeymap$hasImage())
/*      */       {
/* 1066 */         ImageUtil.closeSafely(this.outlined);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class SizeInfo
/*      */   {
/*      */     public int width;
/*      */     public int height;
/*      */     public boolean exact;
/*      */     
/*      */     public SizeInfo(int width, int height, boolean exact) {
/* 1079 */       this.width = width;
/* 1080 */       this.height = height;
/* 1081 */       this.exact = exact;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class CropInfo
/*      */   {
/*      */     public int left;
/*      */     public int top;
/*      */     public int right;
/*      */     public int bottom;
/*      */     
/*      */     public CropInfo(int left, int top, int right, int bottom) {
/* 1094 */       this.left = left;
/* 1095 */       this.top = top;
/* 1096 */       this.right = right;
/* 1097 */       this.bottom = bottom;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class HeadPolygon
/*      */   {
/*      */     private static final float PIXEL_TO_BLOCK = 0.0625F;
/*      */     
/*      */     private static final float BLOCK_TO_PIXEL = 16.0F;
/*      */     
/*      */     public final class_630.class_618[] vertices;
/*      */     
/*      */     public final Vector3f normal;
/*      */     
/*      */     public HeadPolygon(class_4587.class_4665 pose, class_630.class_593 polygon, boolean rotated, int scale) {
/* 1113 */       int s = scale / 2;
/* 1114 */       this.vertices = (class_630.class_618[])polygon.comp_3184().clone();
/* 1115 */       for (int i = 0; i < this.vertices.length; i++) {
/*      */         
/* 1117 */         class_630.class_618 v = this.vertices[i];
/* 1118 */         Vector4f pos = pose.method_23761().transform(new Vector4f((v.comp_3186()).x * 0.0625F, (v.comp_3186()).y * 0.0625F, (v.comp_3186()).z * 0.0625F, 1.0F));
/* 1119 */         if (rotated) {
/*      */           
/* 1121 */           this.vertices[i] = new class_630.class_618((Math.round(pos.z * 16.0F * s) * s), (Math.round(pos.y * 16.0F * s) * s), pos.x * 16.0F * scale, v.comp_3187(), v.comp_3188());
/*      */         }
/*      */         else {
/*      */           
/* 1125 */           this.vertices[i] = new class_630.class_618((Math.round(pos.x * 16.0F * s) * s), (Math.round(pos.y * 16.0F * s) * s), pos.z * 16.0F * scale, v.comp_3187(), v.comp_3188());
/*      */         } 
/*      */       } 
/*      */       
/* 1129 */       Vector3f r1 = new Vector3f();
/* 1130 */       Vector3f r2 = new Vector3f();
/* 1131 */       this.vertices[0].comp_3186().sub((Vector3fc)this.vertices[1].comp_3186(), r1);
/* 1132 */       this.vertices[0].comp_3186().sub((Vector3fc)this.vertices[2].comp_3186(), r2);
/* 1133 */       this.normal = r1.cross((Vector3fc)r2).normalize();
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\render\draw\MobIconCache.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */