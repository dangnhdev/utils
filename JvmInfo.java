import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.io.OutputStream;

public class JvmInfo {
        public static void getJvmInfo(OutputStream out) throws Exception {
                    long total = 0;
                    long gcTime = 0;
                    
                    Runtime rt = Runtime.getRuntime();
                    
                    for(GarbageCollectorMXBean gc : ManagementFactory.getGarbageCollectorMXBeans()) {
                        total += gc.getCollectionCount();
                        gcTime += gc.getCollectionTime();
                    }
                    
                    out.write(("Used Memory:      " + (rt.totalMemory() - rt.freeMemory())+" (bytes)\r\n").getBytes());
                    out.write(("Total collected Garbage:    " +total+"\r\n").getBytes());
                    out.write(("Total collecting Time (ms): " +gcTime+"\r\n").getBytes());
        }
}