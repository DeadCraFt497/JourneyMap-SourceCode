/*    */ package journeymap.client.task.multi;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.util.List;
/*    */ import journeymap.client.JourneymapClient;
/*    */ import journeymap.client.model.chunk.ChunkMD;
/*    */ import journeymap.common.Journeymap;
/*    */ import journeymap.common.log.LogFormatter;
/*    */ import net.minecraft.class_310;
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
/*    */ public class TaskBatch
/*    */   implements ITask
/*    */ {
/*    */   final List<ITask> taskList;
/*    */   final int timeout;
/*    */   protected long startNs;
/*    */   protected long elapsedNs;
/*    */   
/*    */   public TaskBatch(List<ITask> tasks) {
/* 29 */     this.taskList = tasks;
/* 30 */     int timeout = 0;
/* 31 */     for (ITask task : tasks)
/*    */     {
/* 33 */       timeout += task.getMaxRuntime();
/*    */     }
/* 35 */     this.timeout = timeout;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMaxRuntime() {
/* 41 */     return this.timeout;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void performTask(class_310 mc, JourneymapClient jm, File jmWorldDir, boolean threadLogging) throws InterruptedException {
/* 47 */     if (this.startNs == 0L)
/*    */     {
/* 49 */       this.startNs = System.nanoTime();
/*    */     }
/*    */     
/* 52 */     if (threadLogging)
/*    */     {
/* 54 */       Journeymap.getLogger().debug("START batching tasks");
/*    */     }
/*    */     
/* 57 */     while (!this.taskList.isEmpty()) {
/*    */       
/* 59 */       if (Thread.interrupted()) {
/*    */         
/* 61 */         Journeymap.getLogger().warn("TaskBatch thread interrupted: " + String.valueOf(this));
/* 62 */         throw new InterruptedException();
/*    */       } 
/*    */       
/* 65 */       ITask task = this.taskList.remove(0);
/*    */       
/*    */       try {
/* 68 */         if (threadLogging)
/*    */         {
/* 70 */           Journeymap.getLogger().debug("Batching task: " + String.valueOf(task));
/*    */         }
/* 72 */         task.performTask(mc, jm, jmWorldDir, threadLogging);
/*    */       }
/* 74 */       catch (journeymap.client.model.chunk.ChunkMD.ChunkMissingException e) {
/*    */         
/* 76 */         Journeymap.getLogger().warn("Chunk Missing Exception: " + e.getMessage());
/*    */       }
/* 78 */       catch (Throwable t) {
/*    */         
/* 80 */         Journeymap.getLogger().error(String.format("Unexpected error during task batch: %s", new Object[] { LogFormatter.toString(t) }));
/*    */       } 
/*    */     } 
/*    */     
/* 84 */     if (threadLogging)
/*    */     {
/* 86 */       Journeymap.getLogger().debug("DONE batching tasks");
/*    */     }
/*    */     
/* 89 */     this.elapsedNs = System.nanoTime() - this.startNs;
/*    */   }
/*    */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\task\multi\TaskBatch.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */