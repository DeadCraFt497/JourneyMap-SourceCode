package journeymap.client.task.multi;

import java.io.File;
import journeymap.client.JourneymapClient;
import net.minecraft.class_310;

public interface ITask {
  int getMaxRuntime();
  
  void performTask(class_310 paramclass_310, JourneymapClient paramJourneymapClient, File paramFile, boolean paramBoolean) throws InterruptedException;
}


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\task\multi\ITask.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */