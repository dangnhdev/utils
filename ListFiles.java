import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

class ListFiles {

        /**
         * List all files of directories with extensions specified by ext[]
         *
         * @param dir File of Diretory name
         * @param ext String array of extensions (e.g. ,mp3, .wav, .au, etc.)
         * @param all boolean TRUE if subdirectories are included
         * @return List<File>
         */
        public static List<File> listFiles(File dir, String[] ext, boolean all) {
            GetList gl = new GetList(dir, ext, all);
            (new ForkJoinPool()).execute(gl);
            try {   // wait for timeout in 0.5 microsec. step
                while (!gl.isDone()) {
                    TimeUnit.NANOSECONDS.sleep(500);
                }
            } catch (Exception ex) {
            }
            return gl.join();
        }

        /**
         * List all files of directories with extensions specified by ext[]
         *
         * @param dir String, Diretory name
         * @param ext String array of extensions (e.g. ,mp3, .wav, .au, etc.)
         * @param all boolean TRUE if subdirectories are included
         * @return List<File>
         */
        public List<File> listFiles(String dir, String[] ext, boolean all) {
            try {   // wait for timeout in 5 microsec. step
                return listFiles(new File(dir), ext, all);
            } catch (Exception ex) {
            }
            return new ArrayList<>();
        }

        // Innere Recursive task
        private static class GetList extends RecursiveTask<List<File>> {

            public GetList(File dir, String[] ext, boolean all) {
                this.dir = dir;
                this.ext = ext;
                this.all = all;
            }
            private File dir;
            private boolean all;
            private String ext[];

            protected List<File> compute() {
                File[] allFiles = dir.listFiles();
                List<File> listFile = new ArrayList<>();
                List<GetList> tasks = new ArrayList<GetList>();
                // set a POOL of (number of your proccesor's core) parallel tasks
                ForkJoinPool fjp = new ForkJoinPool();
                A:
                for (File f : allFiles) { // <<< the Label A
                    String name = f.getName();
                    for (String s : ext) {
                        if (name.endsWith(s)) {
                            listFile.add(f);
                            continue A; // <<< to the Label A
                        }
                    }

                    if (all && f.isDirectory()) {
                        GetList gl = new GetList(f, ext, all);
                        gl.fork();
                        tasks.add(gl);
                    }
                }

                for (GetList gl : tasks) {
                    listFile.addAll(gl.join());
                }
                return listFile;
            }
        }
    }
