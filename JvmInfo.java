import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.io.OutputStream;

public class JvmInfo {
        public static void getJvmInfo() {
                    long total = 0;
                    long gcTime = 0;
                    
                    Runtime rt = Runtime.getRuntime();
                    
                    for(GarbageCollectorMXBean gc : ManagementFactory.getGarbageCollectorMXBeans()) {
                        total += gc.getCollectionCount();
                        gcTime += gc.getCollectionTime();
                    }
                    
                    System.out.println("Used Memory:      " + (rt.totalMemory() - rt.freeMemory())+" (bytes)\r\n");
                    System.out.println("Total collected Garbage:    " +total+"\r\n");
                    System.out.println("Total collecting Time (ms): " +gcTime+"\r\n");
        }
}