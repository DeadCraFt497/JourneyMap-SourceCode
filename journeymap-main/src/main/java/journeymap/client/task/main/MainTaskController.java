/*     */ package journeymap.client.task.main;
/*     */ 
/*     */ import com.google.common.collect.Queues;
/*     */ import java.util.concurrent.ConcurrentLinkedQueue;
/*     */ import journeymap.client.JourneymapClient;
/*     */ import journeymap.client.log.StatTimer;
/*     */ import journeymap.common.Journeymap;
/*     */ import journeymap.common.log.LogFormatter;
/*     */ import net.minecraft.class_310;
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
/*     */ public class MainTaskController
/*     */ {
/*  23 */   private final ConcurrentLinkedQueue<IMainThreadTask> currentQueue = Queues.newConcurrentLinkedQueue();
/*  24 */   private final ConcurrentLinkedQueue<IMainThreadTask> deferredQueue = Queues.newConcurrentLinkedQueue();
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
/*     */   public void addTask(IMainThreadTask task) {
/*  40 */     synchronized (this.currentQueue) {
/*     */       
/*  42 */       this.currentQueue.add(task);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isActive() {
/*  48 */     if (this.currentQueue.isEmpty())
/*     */     {
/*  50 */       return false;
/*     */     }
/*  52 */     if (this.currentQueue.size() == 1 && this.currentQueue.peek() instanceof MappingMonitorTask)
/*     */     {
/*  54 */       return false;
/*     */     }
/*  56 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void performTasks() {
/*     */     try {
/*  66 */       synchronized (this.currentQueue)
/*     */       {
/*  68 */         if (this.currentQueue.isEmpty())
/*     */         {
/*  70 */           this.currentQueue.add(new MappingMonitorTask());
/*     */         }
/*     */         
/*  73 */         class_310 minecraft = class_310.method_1551();
/*  74 */         JourneymapClient journeymapClient = JourneymapClient.getInstance();
/*     */         
/*  76 */         while (!this.currentQueue.isEmpty()) {
/*     */           
/*  78 */           IMainThreadTask task = this.currentQueue.poll();
/*  79 */           if (task != null) {
/*     */             
/*  81 */             StatTimer timer = StatTimer.get(task.getName());
/*  82 */             timer.start();
/*  83 */             IMainThreadTask deferred = task.perform(minecraft, journeymapClient);
/*  84 */             timer.stop();
/*  85 */             if (deferred != null)
/*     */             {
/*  87 */               this.deferredQueue.add(deferred);
/*     */             }
/*     */           } 
/*     */         } 
/*     */         
/*  92 */         this.currentQueue.addAll(this.deferredQueue);
/*  93 */         this.deferredQueue.clear();
/*     */       }
/*     */     
/*  96 */     } catch (Throwable t) {
/*     */       
/*  98 */       String error = "Error in TickTaskController.performMainThreadTasks(): " + t.getMessage();
/*  99 */       Journeymap.getLogger().error(error);
/* 100 */       Journeymap.getLogger().error(LogFormatter.toString(t));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\task\main\MainTaskController.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */