package JDKRuntime;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class JDKRuntime {
    public static void main(String[] args) {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        long freeMemory = Runtime.getRuntime().freeMemory();
        long totalMemory = Runtime.getRuntime().totalMemory();
        long maxMemory = Runtime.getRuntime().maxMemory();
        System.out.println(availableProcessors + "," + freeMemory + "," + totalMemory + "," + maxMemory);

        Process process;
        BufferedReader br = null;
        String line;
        try {
            process = Runtime.getRuntime().exec("ipconfig");
            br = new BufferedReader(new InputStreamReader(process.getInputStream(), Charset.forName("UTF8")));
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }

            try {
                process.waitFor();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
