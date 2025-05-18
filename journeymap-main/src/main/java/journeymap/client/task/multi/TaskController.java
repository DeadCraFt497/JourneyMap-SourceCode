/*     */ package journeymap.client.task.multi;
/*     */ 
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.ArrayBlockingQueue;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.ScheduledExecutorService;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ import journeymap.client.log.StatTimer;
/*     */ import journeymap.client.thread.RunnableTask;
/*     */ import journeymap.common.Journeymap;
/*     */ import journeymap.common.log.LogFormatter;
/*     */ import journeymap.common.thread.JMThreadFactory;
/*     */ import net.minecraft.class_10209;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_3695;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TaskController
/*     */ {
/*  29 */   static final Logger logger = Journeymap.getLogger();
/*  30 */   final ArrayBlockingQueue<Future> queue = new ArrayBlockingQueue<>(1);
/*  31 */   final List<ITaskManager> managers = new LinkedList<>();
/*  32 */   final class_310 minecraft = class_310.method_1551();
/*  33 */   final ReentrantLock lock = new ReentrantLock();
/*     */ 
/*     */   
/*     */   private volatile ScheduledExecutorService taskExecutor;
/*     */ 
/*     */   
/*     */   public TaskController() {
/*  40 */     this.managers.add(new MapRegionTask.Manager());
/*  41 */     this.managers.add(new SaveMapTask.Manager());
/*  42 */     this.managers.add(new MapPlayerTask.Manager());
/*     */   }
/*     */ 
/*     */   
/*     */   private void ensureExecutor() {
/*  47 */     if (this.taskExecutor == null || this.taskExecutor.isShutdown()) {
/*     */       
/*  49 */       this.taskExecutor = Executors.newScheduledThreadPool(1, (ThreadFactory)new JMThreadFactory("task"));
/*  50 */       this.queue.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Boolean isActive() {
/*  56 */     return Boolean.valueOf((this.taskExecutor != null && !this.taskExecutor.isShutdown()));
/*     */   }
/*     */ 
/*     */   
/*     */   public void enableTasks() {
/*  61 */     this.queue.clear();
/*  62 */     ensureExecutor();
/*     */     
/*  64 */     List<ITaskManager> list = new LinkedList<>(this.managers);
/*  65 */     for (ITaskManager manager : this.managers) {
/*     */       
/*  67 */       boolean enabled = manager.enableTask(this.minecraft, (Object)null);
/*  68 */       if (!enabled) {
/*     */         
/*  70 */         logger.debug("Task not initially enabled: " + manager.getTaskClass().getSimpleName());
/*     */         
/*     */         continue;
/*     */       } 
/*  74 */       logger.debug("Task ready: " + manager.getTaskClass().getSimpleName());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/*  82 */     this.managers.clear();
/*  83 */     this.queue.clear();
/*     */     
/*  85 */     if (this.taskExecutor != null && !this.taskExecutor.isShutdown()) {
/*     */       
/*  87 */       this.taskExecutor.shutdownNow();
/*     */       
/*     */       try {
/*  90 */         this.taskExecutor.awaitTermination(5L, TimeUnit.SECONDS);
/*     */       }
/*  92 */       catch (InterruptedException e) {
/*     */         
/*  94 */         e.printStackTrace();
/*     */       } 
/*  96 */       this.taskExecutor = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ITaskManager getManager(Class<? extends ITaskManager> managerClass) {
/* 102 */     ITaskManager taskManager = null;
/* 103 */     for (ITaskManager manager : this.managers) {
/*     */       
/* 105 */       if (manager.getClass() == managerClass) {
/*     */         
/* 107 */         taskManager = manager;
/*     */         break;
/*     */       } 
/*     */     } 
/* 111 */     return taskManager;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isTaskManagerEnabled(Class<? extends ITaskManager> managerClass) {
/* 116 */     ITaskManager taskManager = getManager(managerClass);
/* 117 */     if (taskManager != null)
/*     */     {
/* 119 */       return taskManager.isEnabled(class_310.method_1551());
/*     */     }
/*     */ 
/*     */     
/* 123 */     logger.warn("1 Couldn't toggle task; manager not in controller: " + managerClass.getClass().getName());
/* 124 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void toggleTask(Class<? extends ITaskManager> managerClass, boolean enable, Object params) {
/* 131 */     ITaskManager taskManager = null;
/* 132 */     for (ITaskManager manager : this.managers) {
/*     */       
/* 134 */       if (manager.getClass() == managerClass) {
/*     */         
/* 136 */         taskManager = manager;
/*     */         break;
/*     */       } 
/*     */     } 
/* 140 */     if (taskManager != null) {
/*     */       
/* 142 */       toggleTask(taskManager, enable, params);
/*     */     }
/*     */     else {
/*     */       
/* 146 */       logger.warn("1 Couldn't toggle task; manager not in controller: " + managerClass.getClass().getName());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void toggleTask(ITaskManager manager, boolean enable, Object params) {
/* 152 */     class_310 minecraft = class_310.method_1551();
/* 153 */     if (manager.isEnabled(minecraft)) {
/*     */       
/* 155 */       if (!enable)
/*     */       {
/* 157 */         logger.debug("Disabling task: " + manager.getTaskClass().getSimpleName());
/* 158 */         manager.disableTask(minecraft);
/*     */       }
/*     */       else
/*     */       {
/* 162 */         logger.debug("Task already enabled: " + manager.getTaskClass().getSimpleName());
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 167 */     else if (enable) {
/*     */       
/* 169 */       logger.debug("Enabling task: " + manager.getTaskClass().getSimpleName());
/* 170 */       manager.enableTask(minecraft, params);
/*     */     }
/*     */     else {
/*     */       
/* 174 */       logger.debug("Task already disabled: " + manager.getTaskClass().getSimpleName());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void disableTasks() {
/* 181 */     for (ITaskManager manager : this.managers) {
/*     */       
/* 183 */       if (manager.isEnabled(this.minecraft)) {
/*     */         
/* 185 */         manager.disableTask(this.minecraft);
/* 186 */         logger.debug("Task disabled: " + manager.getTaskClass().getSimpleName());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasRunningTask() {
/* 193 */     return !this.queue.isEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void queueOneOff(Runnable runnable) throws Exception {
/*     */     try {
/* 200 */       ensureExecutor();
/* 201 */       if (this.taskExecutor != null && !this.taskExecutor.isShutdown())
/*     */       {
/* 203 */         this.taskExecutor.submit(runnable);
/*     */       }
/*     */       else
/*     */       {
/* 207 */         throw new IllegalStateException("TaskExecutor isn't running");
/*     */       }
/*     */     
/* 210 */     } catch (Exception e) {
/*     */       
/* 212 */       logger.error("TaskController couldn't queueOneOff(): " + LogFormatter.toString(e));
/* 213 */       throw e;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void performTasks() {
/* 219 */     class_3695 profiler = class_10209.method_64146();
/* 220 */     profiler.method_15396("journeymapTask");
/* 221 */     StatTimer totalTimer = StatTimer.get("TaskController.performMultithreadTasks", 1, 500).start();
/*     */ 
/*     */     
/*     */     try {
/* 225 */       if (this.lock.tryLock())
/*     */       {
/* 227 */         if (!this.queue.isEmpty())
/*     */         {
/* 229 */           if (((Future)this.queue.peek()).isDone()) {
/*     */             
/*     */             try {
/*     */               
/* 233 */               this.queue.take();
/*     */             }
/* 235 */             catch (InterruptedException e) {
/*     */               
/* 237 */               logger.warn("Interrupted" + e.getMessage());
/*     */             } 
/*     */           }
/*     */         }
/*     */         
/* 242 */         if (this.queue.isEmpty()) {
/*     */           
/* 244 */           ITask task = null;
/* 245 */           ITaskManager manager = getNextManager(this.minecraft);
/* 246 */           if (manager == null) {
/*     */             
/* 248 */             logger.warn("No task managers enabled!");
/*     */             return;
/*     */           } 
/* 251 */           boolean accepted = false;
/*     */           
/* 253 */           StatTimer timer = StatTimer.get(manager.getTaskClass().getSimpleName() + ".Manager.getTask").start();
/* 254 */           task = manager.getTask(this.minecraft);
/*     */           
/* 256 */           if (task == null) {
/*     */             
/* 258 */             timer.cancel();
/*     */           }
/*     */           else {
/*     */             
/* 262 */             timer.stop();
/*     */             
/* 264 */             ensureExecutor();
/*     */             
/* 266 */             if (this.taskExecutor != null && !this.taskExecutor.isShutdown()) {
/*     */ 
/*     */               
/* 269 */               RunnableTask runnableTask = new RunnableTask(this.taskExecutor, task);
/* 270 */               this.queue.add(this.taskExecutor.submit((Runnable)runnableTask));
/* 271 */               accepted = true;
/*     */               
/* 273 */               if (logger.isTraceEnabled())
/*     */               {
/* 275 */                 logger.debug("Scheduled " + manager.getTaskClass().getSimpleName());
/*     */               }
/*     */             }
/*     */             else {
/*     */               
/* 280 */               logger.warn("TaskExecutor isn't running");
/*     */             } 
/*     */             
/* 283 */             manager.taskAccepted(task, accepted);
/*     */           } 
/*     */         } 
/* 286 */         this.lock.unlock();
/*     */       }
/*     */       else
/*     */       {
/* 290 */         logger.warn("TaskController appears to have multiple threads trying to use it");
/*     */       }
/*     */     
/*     */     } finally {
/*     */       
/* 295 */       totalTimer.stop();
/* 296 */       profiler.method_15407();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private ITaskManager getNextManager(class_310 minecraft) {
/* 302 */     for (ITaskManager manager : this.managers) {
/*     */       
/* 304 */       if (manager.isEnabled(minecraft))
/*     */       {
/* 306 */         return manager;
/*     */       }
/*     */     } 
/* 309 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\task\multi\TaskController.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */