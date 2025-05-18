package journeymap.client.task;

import java.util.concurrent.Callable;
import journeymap.common.version.Version;

public interface MigrationTask extends Callable<Boolean> {
  boolean isActive(Version paramVersion);
}


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\task\MigrationTask.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */