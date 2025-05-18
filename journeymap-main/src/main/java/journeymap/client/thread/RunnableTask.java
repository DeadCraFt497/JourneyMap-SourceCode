/*    */ package journeymap.client.thread;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.util.concurrent.ExecutorService;
/*    */ import journeymap.client.JourneymapClient;
/*    */ import journeymap.client.io.FileHandler;
/*    */ import journeymap.client.task.multi.ITask;
/*    */ import journeymap.common.Journeymap;
/*    */ import journeymap.common.log.LogFormatter;
/*    */ import net.minecraft.class_310;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RunnableTask
/*    */   implements Runnable
/*    */ {
/* 21 */   static final JourneymapClient jm = JourneymapClient.getInstance();
/* 22 */   static final Logger logger = Journeymap.getLogger();
/* 23 */   static final class_310 mc = class_310.method_1551();
/* 24 */   static final boolean threadLogging = jm.isThreadLogging().booleanValue();
/*    */   
/*    */   private final ExecutorService taskExecutor;
/*    */   
/*    */   private final Runnable innerRunnable;
/*    */   
/*    */   private final ITask task;
/*    */   private final int timeout;
/*    */   
/*    */   public RunnableTask(ExecutorService taskExecutor, ITask task) {
/* 34 */     this.taskExecutor = taskExecutor;
/* 35 */     this.task = task;
/* 36 */     this.timeout = task.getMaxRuntime();
/* 37 */     this.innerRunnable = new Inner();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void run() {
/*    */     try {
/* 46 */       this.taskExecutor.submit(this.innerRunnable);
/*    */     }
/* 48 */     catch (Throwable t) {
/*    */       
/* 50 */       Journeymap.getLogger().warn("Interrupted task that ran too long:" + String.valueOf(this.task));
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   class Inner
/*    */     implements Runnable
/*    */   {
/*    */     public final void run() {
/*    */       try {
/* 64 */         if (!RunnableTask.jm.isMapping().booleanValue()) {
/*    */           
/* 66 */           RunnableTask.logger.debug("JM not mapping, aborting");
/*    */           
/*    */           return;
/*    */         } 
/* 70 */         File jmWorldDir = FileHandler.getJMWorldDir(RunnableTask.mc);
/* 71 */         if (jmWorldDir == null) {
/*    */           
/* 73 */           RunnableTask.logger.debug("JM world dir not found, aborting");
/*    */           
/*    */           return;
/*    */         } 
/* 77 */         RunnableTask.this.task.performTask(RunnableTask.mc, RunnableTask.jm, jmWorldDir, RunnableTask.threadLogging);
/*    */       
/*    */       }
/* 80 */       catch (InterruptedException e) {
/*    */         
/* 82 */         RunnableTask.logger.debug("Task interrupted: " + LogFormatter.toPartialString(e));
/*    */       }
/* 84 */       catch (Throwable t) {
/*    */         
/* 86 */         String error = "Unexpected error during RunnableTask: " + LogFormatter.toString(t);
/* 87 */         RunnableTask.logger.error(error);
/*    */       } 
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\thread\RunnableTask.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */