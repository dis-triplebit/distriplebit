import config.Config;
import org.junit.Test;

import java.io.*;

public class CountTriples {
    String dataFile = Config.INPUTFILE;
    int slaveNum = Config.SLAVENUM;

    @Test
    public void countTriplesOfSimpleFile() throws IOException{
        int dot = dataFile.indexOf('.');
        int[] counts = new int[slaveNum + 1];
        String[] files = new String[slaveNum + 1];
        files[0] = dataFile;
        if (dot == -1){
            for (int i = 0; i < slaveNum; i++) {
                files[i + 1] = dataFile + "-" + i;
            }
        } else {
            String prefix = dataFile.substring(0, dot);
            for (int i = 0; i < slaveNum; i++) {
                files[i + 1] = prefix + "-" + i + ".n3";
            }
        }
        for (int i = 0; i <= slaveNum; i++) {
            int count = 0;
            BufferedReader reader = new BufferedReader(new FileReader(files[i]));
            while (reader.readLine() != null){
                count += 1;
            }
            reader.close();
            counts[i] = count;
        }
        for (int i = 0; i <= slaveNum; i++) {
            System.out.println(files[i] + ":\t" + counts[i]);
        }
    }
}
