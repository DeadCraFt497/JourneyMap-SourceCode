package journeymap.client.task.multi;

import net.minecraft.class_310;

public interface ITaskManager {
  Class<? extends ITask> getTaskClass();
  
  boolean enableTask(class_310 paramclass_310, Object paramObject);
  
  boolean isEnabled(class_310 paramclass_310);
  
  ITask getTask(class_310 paramclass_310);
  
  void taskAccepted(ITask paramITask, boolean paramBoolean);
  
  void disableTask(class_310 paramclass_310);
}


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\task\multi\ITaskManager.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */