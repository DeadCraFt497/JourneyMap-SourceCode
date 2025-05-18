/*    */ package journeymap.client.thread;
/*    */ 
/*    */ import java.io.File;
/*    */ import journeymap.client.io.FileHandler;
/*    */ import journeymap.common.log.LogFormatter;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class Inner
/*    */   implements Runnable
/*    */ {
/*    */   public final void run() {
/*    */     try {
/* 64 */       if (!RunnableTask.jm.isMapping().booleanValue()) {
/*    */         
/* 66 */         RunnableTask.logger.debug("JM not mapping, aborting");
/*    */         
/*    */         return;
/*    */       } 
/* 70 */       File jmWorldDir = FileHandler.getJMWorldDir(RunnableTask.mc);
/* 71 */       if (jmWorldDir == null) {
/*    */         
/* 73 */         RunnableTask.logger.debug("JM world dir not found, aborting");
/*    */         
/*    */         return;
/*    */       } 
/* 77 */       RunnableTask.this.task.performTask(RunnableTask.mc, RunnableTask.jm, jmWorldDir, RunnableTask.threadLogging);
/*    */     
/*    */     }
/* 80 */     catch (InterruptedException e) {
/*    */       
/* 82 */       RunnableTask.logger.debug("Task interrupted: " + LogFormatter.toPartialString(e));
/*    */     }
/* 84 */     catch (Throwable t) {
/*    */       
/* 86 */       String error = "Unexpected error during RunnableTask: " + LogFormatter.toString(t);
/* 87 */       RunnableTask.logger.error(error);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\thread\RunnableTask$Inner.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */