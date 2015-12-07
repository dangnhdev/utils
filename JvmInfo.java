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
                out.write(("Total JVM memory:           " +rt.totalMemory()+" (bytes)\n").getBytes());
                out.write(("Max JVM Memory:      " +rt.maxMemory()+" (bytes)\n").getBytes());
                out.write(("GC collected:    " +total+"\n").getBytes());
                out.write(("GC collecting time (ms): " +gcTime+"\n").getBytes());
        }
}