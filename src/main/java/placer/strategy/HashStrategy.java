package placer.strategy;

import config.Config;

import java.io.*;

public class HashStrategy {
    private BufferedReader reader;
    private BufferedWriter[] writers;
    private String dataFile = Config.getProp("INPUTFILE");
    private int slaveNum = Integer.valueOf(Config.getProp("SLAVENUM"));

    public HashStrategy() {
    }

    public HashStrategy(BufferedReader reader) throws FileNotFoundException {
        this.reader = reader;
        writers = new BufferedWriter[slaveNum];
        int dot = dataFile.indexOf('.');
        if (dot == -1) {
            for (int i = 0; i < writers.length; i++) {
                OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(dataFile + "-" + i));
                writers[i] = new BufferedWriter(osw);
            }
        } else {
            String prefix = Config.getProp("INPUTFILE").substring(0, dot);
            for (int i = 0; i < writers.length; i++) {
                OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(prefix + "-" + i + ".n3"));
                writers[i] = new BufferedWriter(osw);
            }
        }
    }

    public boolean place() throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] spo = line.split(" ");
            int sHash = Math.abs(spo[0].hashCode());
            int pHash = Math.abs(spo[1].hashCode());
            int oHash = Math.abs(spo[2].hashCode());
            int slaveIndex = sHash % slaveNum;
            writers[slaveIndex].write(line + "\n");
            slaveIndex = oHash % slaveNum;
            writers[slaveIndex].write(line + "\n");
        }
        for (int i = 0; i < slaveNum; i++) {
            writers[i].close();
        }
        return true;
    }
}
