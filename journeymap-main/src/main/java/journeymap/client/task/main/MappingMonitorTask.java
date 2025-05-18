/*     */ package journeymap.client.task.main;
/*     */ 
/*     */ import journeymap.client.JourneymapClient;
/*     */ import journeymap.client.log.ChatLog;
/*     */ import journeymap.common.Journeymap;
/*     */ import journeymap.common.log.LogFormatter;
/*     */ import net.minecraft.class_2960;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_437;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MappingMonitorTask
/*     */   implements IMainThreadTask
/*     */ {
/*  29 */   private static String NAME = "Tick." + MappingMonitorTask.class.getSimpleName();
/*  30 */   Logger logger = Journeymap.getLogger();
/*  31 */   private class_2960 lastDimension = null;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean shouldReset = true;
/*     */ 
/*     */ 
/*     */   
/*     */   public IMainThreadTask perform(class_310 mc, JourneymapClient jm) {
/*     */     try {
/*  41 */       class_437 guiScreen = mc.field_1755;
/*  42 */       boolean inMainMenu = (guiScreen instanceof net.minecraft.class_442 || guiScreen instanceof net.minecraft.class_526 || guiScreen instanceof net.minecraft.class_500);
/*     */ 
/*     */       
/*  45 */       if (inMainMenu)
/*     */       {
/*  47 */         jm.setEnable();
/*     */       }
/*  49 */       if (!jm.isInitialized().booleanValue())
/*     */       {
/*  51 */         return this;
/*     */       }
/*     */       
/*  54 */       boolean isDead = (mc.field_1755 != null && mc.field_1755 instanceof net.minecraft.class_418);
/*     */       
/*  56 */       if (mc.field_1687 == null) {
/*     */         
/*  58 */         if (jm.isMapping().booleanValue())
/*     */         {
/*  60 */           jm.stopMapping();
/*     */         }
/*     */         
/*  63 */         if (inMainMenu)
/*     */         {
/*  65 */           if (jm.getCurrentWorldId() != null) {
/*     */             
/*  67 */             this.logger.info("World ID has been reset.");
/*  68 */             jm.setCurrentWorldId(null);
/*     */           } 
/*     */         }
/*     */         
/*  72 */         return this;
/*     */       } 
/*  74 */       if (this.lastDimension == null || (mc.field_1724 != null && mc.field_1724.method_37908() != null && !mc.field_1724.method_37908().method_27983().method_29177().equals(this.lastDimension))) {
/*     */         
/*  76 */         this.lastDimension = mc.field_1724.method_37908().method_27983().method_29177();
/*  77 */         if (jm.isMapping().booleanValue())
/*     */         {
/*  79 */           jm.stopMapping();
/*     */         
/*     */         }
/*     */       
/*     */       }
/*  84 */       else if (!jm.isMapping().booleanValue() && !isDead) {
/*     */         
/*  86 */         if ((JourneymapClient.getInstance().getCoreProperties()).mappingEnabled.get().booleanValue()) {
/*     */           
/*  88 */           jm.startMapping();
/*  89 */           this.shouldReset = true;
/*     */         }
/*  91 */         else if (this.shouldReset) {
/*     */           
/*  93 */           jm.reset();
/*  94 */           this.shouldReset = false;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/*  99 */       boolean isGamePaused = (mc.field_1755 != null && !(mc.field_1755 instanceof journeymap.client.ui.fullscreen.Fullscreen));
/* 100 */       if (isGamePaused)
/*     */       {
/* 102 */         if (!jm.isMapping().booleanValue())
/*     */         {
/* 104 */           return this;
/*     */         }
/*     */       }
/*     */ 
/*     */       
/* 109 */       if (!isGamePaused)
/*     */       {
/* 111 */         ChatLog.showChatAnnouncements(mc);
/*     */       }
/*     */ 
/*     */       
/* 115 */       if (!jm.isMapping().booleanValue())
/*     */       {
/* 117 */         if ((JourneymapClient.getInstance().getCoreProperties()).mappingEnabled.get().booleanValue())
/*     */         {
/* 119 */           jm.startMapping();
/* 120 */           this.shouldReset = true;
/*     */         }
/* 122 */         else if (this.shouldReset)
/*     */         {
/* 124 */           jm.reset();
/* 125 */           this.shouldReset = false;
/*     */         }
/*     */       
/*     */       }
/* 129 */     } catch (Throwable t) {
/*     */       
/* 131 */       this.logger.error("Error in JourneyMap.performMainThreadTasks(): " + LogFormatter.toString(t));
/*     */     } 
/* 133 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 139 */     return NAME;
/*     */   }
/*     */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\task\main\MappingMonitorTask.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */