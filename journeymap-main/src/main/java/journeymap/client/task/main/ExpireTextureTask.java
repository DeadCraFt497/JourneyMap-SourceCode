/*     */ package journeymap.client.task.main;
/*     */ 
/*     */ import com.mojang.blaze3d.opengl.GlStateManager;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import journeymap.client.JourneymapClient;
/*     */ import journeymap.client.render.RenderWrapper;
/*     */ import journeymap.client.texture.ImageUtil;
/*     */ import journeymap.common.Journeymap;
/*     */ import net.minecraft.class_1043;
/*     */ import net.minecraft.class_10868;
/*     */ import net.minecraft.class_310;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ExpireTextureTask
/*     */   implements IMainThreadTask
/*     */ {
/*     */   private static final int MAX_FAILS = 5;
/*  30 */   private static String NAME = "Tick." + MappingMonitorTask.class.getSimpleName();
/*  31 */   private static Logger LOGGER = Journeymap.getLogger();
/*     */   
/*     */   private final List<class_1043> textures;
/*     */   private final int textureId;
/*     */   private volatile int fails;
/*     */   
/*     */   private ExpireTextureTask(int textureId) {
/*  38 */     this.textures = null;
/*  39 */     this.textureId = textureId;
/*     */   }
/*     */ 
/*     */   
/*     */   private ExpireTextureTask(class_1043 texture) {
/*  44 */     this.textures = new ArrayList<>();
/*  45 */     this.textures.add(texture);
/*  46 */     this.textureId = -1;
/*     */   }
/*     */ 
/*     */   
/*     */   private ExpireTextureTask(Collection<class_1043> textureCollection) {
/*  51 */     this.textures = new ArrayList<>(textureCollection);
/*  52 */     this.textureId = -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void queue(int textureId) {
/*  57 */     if (textureId != -1)
/*     */     {
/*  59 */       JourneymapClient.getInstance().queueMainThreadTask(new ExpireTextureTask(textureId));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void queue(class_1043 texture) {
/*  65 */     JourneymapClient.getInstance().queueMainThreadTask(new ExpireTextureTask(texture));
/*     */   }
/*     */ 
/*     */   
/*     */   public static void queue(Collection<class_1043> textureCollection) {
/*  70 */     JourneymapClient.getInstance().queueMainThreadTask(new ExpireTextureTask(textureCollection));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IMainThreadTask perform(class_310 mc, JourneymapClient jm) {
/*  76 */     boolean success = deleteTextures();
/*  77 */     if (!success && this.textures != null && !this.textures.isEmpty()) {
/*     */       
/*  79 */       this.fails++;
/*  80 */       LOGGER.warn("ExpireTextureTask.perform() couldn't delete textures: " + String.valueOf(this.textures) + ", fails: " + this.fails);
/*  81 */       if (this.fails <= 5)
/*     */       {
/*  83 */         return this;
/*     */       }
/*     */     } 
/*  86 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean deleteTextures() {
/*  94 */     if (this.textureId != -1)
/*     */     {
/*  96 */       return deleteTexture(this.textureId);
/*     */     }
/*     */ 
/*     */     
/* 100 */     Iterator<class_1043> iter = this.textures.listIterator();
/* 101 */     while (iter.hasNext()) {
/*     */       
/* 103 */       class_1043 texture = iter.next();
/* 104 */       if (texture == null) {
/*     */         
/* 106 */         iter.remove();
/*     */         continue;
/*     */       } 
/* 109 */       if (deleteTexture(texture))
/*     */       {
/* 111 */         iter.remove();
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 118 */     return this.textures.isEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean deleteTexture(class_1043 texture) {
/* 124 */     boolean success = false;
/* 125 */     if (((class_10868)texture.method_68004()).method_68427() != -1) {
/*     */       
/*     */       try
/*     */       {
/* 129 */         ImageUtil.closeSafely(texture);
/* 130 */         success = true;
/*     */       }
/* 132 */       catch (Exception t)
/*     */       {
/* 134 */         LOGGER.warn("Couldn't delete texture " + String.valueOf(texture) + ": " + String.valueOf(t));
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 139 */       ImageUtil.closeSafely(texture);
/* 140 */       success = true;
/*     */     } 
/*     */     
/* 143 */     return success;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean deleteTexture(int textureId) {
/*     */     try {
/* 150 */       if (!RenderSystem.isOnRenderThread()) {
/*     */         
/* 152 */         RenderWrapper.recordRenderQueue(() -> {
/*     */ 
/*     */ 
/*     */               
/*     */               if (this.textureId != -1) {
/*     */                 GlStateManager._deleteTexture(this.textureId);
/*     */               }
/*     */             });
/* 160 */       } else if (this.textureId != -1) {
/*     */         
/* 162 */         GlStateManager._deleteTexture(this.textureId);
/*     */       } 
/* 164 */       return true;
/*     */     }
/* 166 */     catch (Exception t) {
/*     */       
/* 168 */       LOGGER.warn("Couldn't delete textureId " + textureId + ": " + String.valueOf(t));
/*     */       
/* 170 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/* 176 */     return NAME;
/*     */   }
/*     */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\task\main\ExpireTextureTask.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */