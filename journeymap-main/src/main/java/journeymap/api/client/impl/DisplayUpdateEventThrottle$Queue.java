/*     */ package journeymap.api.client.impl;
/*     */ 
/*     */ import journeymap.api.v2.client.event.DisplayUpdateEvent;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class Queue
/*     */ {
/*     */   private final long delay;
/*     */   private DisplayUpdateEvent lastEvent;
/*     */   private boolean throttleNext;
/*     */   private long releaseTime;
/*     */   
/*     */   Queue(DisplayUpdateEventThrottle this$0, long delay) {
/* 117 */     this.delay = delay;
/*     */   }
/*     */ 
/*     */   
/*     */   void offer(DisplayUpdateEvent event) {
/* 122 */     if (this.releaseTime == 0L && this.lastEvent != null)
/*     */     {
/* 124 */       this.releaseTime = System.currentTimeMillis() + this.delay;
/*     */     }
/* 126 */     this.lastEvent = event;
/*     */   }
/*     */ 
/*     */   
/*     */   DisplayUpdateEvent remove() {
/* 131 */     DisplayUpdateEvent event = this.lastEvent;
/* 132 */     this.lastEvent = null;
/* 133 */     this.releaseTime = 0L;
/* 134 */     return event;
/*     */   }
/*     */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\api\client\impl\DisplayUpdateEventThrottle$Queue.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */