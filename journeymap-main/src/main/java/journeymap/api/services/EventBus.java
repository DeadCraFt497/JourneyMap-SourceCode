/*    */ package journeymap.api.services;
/*    */ 
/*    */ import java.util.List;
/*    */ import journeymap.api.v2.common.event.impl.EventFactory;
/*    */ import journeymap.api.v2.common.event.impl.EventImpl;
/*    */ import journeymap.api.v2.common.event.impl.JourneyMapEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EventBus
/*    */ {
/*    */   public static <T extends JourneyMapEvent> T post(T event) {
/* 14 */     EventImpl<JourneyMapEvent> impl = (EventImpl<JourneyMapEvent>)EventFactory.getInstance().getEvents().get(event.getClass());
/* 15 */     if (impl != null && impl.getListeners() != null)
/*    */     {
/* 17 */       impl.getListeners().forEach(listener -> listener.listener().accept(event));
/*    */     }
/* 19 */     return event;
/*    */   }
/*    */ 
/*    */   
/*    */   public static <T extends JourneyMapEvent> boolean hasListeners(T event) {
/* 24 */     List<EventImpl.Listener<JourneyMapEvent>> listeners = getEventListeners(event);
/* 25 */     return (listeners != null && !listeners.isEmpty());
/*    */   }
/*    */ 
/*    */   
/*    */   public static <T extends JourneyMapEvent> List<EventImpl.Listener<JourneyMapEvent>> getEventListeners(T event) {
/* 30 */     return ((EventImpl)EventFactory.getInstance().getEvents().get(event.getClass())).getListeners();
/*    */   }
/*    */ 
/*    */   
/*    */   public static <T extends JourneyMapEvent> boolean pluginHasListeners(String modId, T event) {
/* 35 */     List<EventImpl.Listener<JourneyMapEvent>> listeners = getEventListeners(event);
/* 36 */     return (listeners != null && listeners.size() > 0 && listeners
/* 37 */       .stream()
/* 38 */       .anyMatch(listener -> listener.modId().equals(modId)));
/*    */   }
/*    */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\api\services\EventBus.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */