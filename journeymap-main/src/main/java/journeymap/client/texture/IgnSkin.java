/*     */ package journeymap.client.texture;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.UUID;
/*     */ import journeymap.common.accessors.NativeImageAccess;
/*     */ import net.minecraft.class_1011;
/*     */ import net.minecraft.class_1043;
/*     */ import net.minecraft.class_1044;
/*     */ import net.minecraft.class_1060;
/*     */ import net.minecraft.class_2631;
/*     */ import net.minecraft.class_2960;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_640;
/*     */ import net.minecraft.class_8685;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IgnSkin
/*     */ {
/*  28 */   public static final Map<UUID, class_1043> faceImageCache = Maps.newHashMap();
/*  29 */   private static final Map<UUID, class_640> playerInfoMap = Maps.newHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  35 */   private static final class_1043 DEFAULT_SKIN = getDefaultSkin();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class_1043 getFace(GameProfile profile) {
/*  41 */     class_1043 face = faceImageCache.get(profile.getId());
/*  42 */     if (face == null) {
/*     */       
/*  44 */       class_2631.method_59539(profile.getId()).thenAccept(optional -> {
/*     */             GameProfile gameProfile = optional.orElse(profile);
/*     */             faceImageCache.put(profile.getId(), getSkin(gameProfile));
/*     */           });
/*  48 */       return DEFAULT_SKIN;
/*     */     } 
/*  50 */     return face;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class_1043 getSkin(GameProfile profile) {
/*     */     try {
/*  57 */       class_640 playerInfo = playerInfoMap.computeIfAbsent(profile.getId(), k -> new class_640(profile, false));
/*  58 */       class_8685 skin = playerInfo.method_52810();
/*  59 */       if (skin != null) {
/*     */         
/*  61 */         class_1060 textureManager = class_310.method_1551().method_1531();
/*  62 */         class_1044 texture = textureManager.method_4619(skin.comp_1626());
/*     */         
/*  64 */         if (texture != null && texture instanceof class_1043) { class_1043 tex = (class_1043)texture; if (tex.method_4525() != null)
/*     */           {
/*  66 */             return cropToFace(tex.method_4525());
/*     */           } }
/*     */       
/*     */       } 
/*  70 */     } catch (Throwable throwable) {}
/*     */ 
/*     */ 
/*     */     
/*  74 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class_1043 getDefaultSkin() {
/*     */     try {
/*  81 */       class_1043 class_10431 = TextureCache.getTexture(class_2960.method_60656("textures/entity/player/wide/steve.png"));
/*  82 */       if (class_10431 != null && class_10431 instanceof class_1043) { class_1043 tex = class_10431;
/*     */         
/*  84 */         return cropToFace(tex.method_4525()); }
/*     */ 
/*     */     
/*  87 */     } catch (Throwable throwable) {}
/*     */ 
/*     */ 
/*     */     
/*  91 */     return new class_1043(null, ImageUtil.getNewBlankImage(24, 24));
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
/*     */   public static class_1043 cropToFace(class_1011 playerSkin) {
/* 103 */     class_1011 skin = new class_1011(playerSkin.method_4307(), playerSkin.method_4323(), false);
/* 104 */     skin.method_4317(playerSkin);
/* 105 */     if (skin != null && skin.field_4988 != 0L) {
/*     */ 
/*     */       
/* 108 */       if (skin.method_4318().method_4329()) {
/*     */         
/* 110 */         class_1011 hat = ImageUtil.getSubImage(40, 8, 8, 8, skin, false);
/*     */         
/* 112 */         for (int x = 0; x < 8; x++) {
/*     */           
/* 114 */           for (int y = 0; y < 8; y++) {
/*     */             
/* 116 */             int hatPixel = hat.method_61940(x, y);
/* 117 */             ((NativeImageAccess)skin).blendPixelRGBA(x + 8, y + 8, hatPixel);
/*     */           } 
/*     */         } 
/* 120 */         hat.close();
/*     */       } 
/* 122 */       class_1011 sub = ImageUtil.getSubImage(8, 8, 8, 8, skin, false);
/*     */ 
/*     */       
/* 125 */       return new class_1043(null, ImageUtil.getSizedImage(24, 24, sub, false));
/*     */     } 
/*     */     
/* 128 */     return new class_1043(null, ImageUtil.getNewBlankImage(24, 24));
/*     */   }
/*     */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\texture\IgnSkin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */