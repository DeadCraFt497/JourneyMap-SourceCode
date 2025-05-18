/*     */ package journeymap.api.client.impl;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import javax.annotation.ParametersAreNonnullByDefault;
/*     */ import journeymap.api.services.EventBus;
/*     */ import journeymap.api.v2.client.event.DisplayUpdateEvent;
/*     */ import journeymap.api.v2.client.event.MappingEvent;
/*     */ import journeymap.api.v2.common.event.impl.JourneyMapEvent;
/*     */ import journeymap.client.JourneymapClient;
/*     */ import net.minecraft.class_1937;
/*     */ import net.minecraft.class_5321;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @ParametersAreNonnullByDefault
/*     */ public class ClientEventManager
/*     */ {
/*  25 */   private final DisplayUpdateEventThrottle displayUpdateEventThrottle = new DisplayUpdateEventThrottle();
/*     */   
/*     */   private final Collection<PluginWrapper> plugins;
/*     */   
/*     */   public ClientEventManager(Collection<PluginWrapper> plugins) {
/*  30 */     this.plugins = plugins;
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
/*     */   public void fireMappingEvent(boolean started, class_5321<class_1937> dimension) {
/*  42 */     MappingEvent.Stage type = started ? MappingEvent.Stage.MAPPING_STARTED : MappingEvent.Stage.MAPPING_STOPPED;
/*  43 */     if (started)
/*     */     {
/*     */       
/*  46 */       ClientAPI.INSTANCE.refreshDataPathCache(false);
/*     */     }
/*     */ 
/*     */     
/*  50 */     MappingEvent mappingEvent = new MappingEvent(type, dimension, JourneymapClient.getInstance().getCurrentWorldId());
/*  51 */     EventBus.post((JourneyMapEvent)mappingEvent);
/*  52 */     if (!started) {
/*     */ 
/*     */       
/*  55 */       ClientAPI.INSTANCE.refreshDataPathCache(true);
/*     */     }
/*     */     else {
/*     */       
/*  59 */       JourneymapClient.getInstance().setCurrentWorldId(mappingEvent.getWorldId());
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
/*     */   public void queueDisplayUpdateEvent(DisplayUpdateEvent clientEvent) {
/*     */     try {
/*  73 */       this.displayUpdateEventThrottle.add(clientEvent);
/*     */     }
/*  75 */     catch (Throwable t) {
/*     */       
/*  77 */       ClientAPI.INSTANCE.logError("Error in fireDisplayUpdateEvent(): " + String.valueOf(clientEvent), t);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fireNextClientEvents() {
/*  86 */     if (!this.plugins.isEmpty() && this.displayUpdateEventThrottle.isReady()) {
/*     */       
/*  88 */       Iterator<DisplayUpdateEvent> iterator = this.displayUpdateEventThrottle.iterator();
/*  89 */       while (iterator.hasNext()) {
/*     */         
/*  91 */         DisplayUpdateEvent event = iterator.next();
/*  92 */         iterator.remove();
/*     */ 
/*     */         
/*     */         try {
/*  96 */           EventBus.post((JourneyMapEvent)event);
/*     */         }
/*  98 */         catch (Throwable t) {
/*     */           
/* 100 */           ClientAPI.INSTANCE.logError("Error in fireDeathpointEvent(): " + String.valueOf(event), t);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void purge() {
/* 112 */     this.plugins.clear();
/*     */   }
/*     */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\api\client\impl\ClientEventManager.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */