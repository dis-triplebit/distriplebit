package placer.strategy;

import config.Config;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class HashStrategy {
    private BufferedReader reader;
    private BufferedWriter[] writers;
    private BufferedWriter indexWriter;
    private Map<String, Integer> index;
    private int slaveNum = Integer.valueOf(Config.getProp("SLAVENUM"));

    public HashStrategy() {
    }

    public HashStrategy(String dataFile) throws FileNotFoundException {
        this.reader = new BufferedReader(new FileReader(dataFile));
        writers = new BufferedWriter[slaveNum];
        int dot = dataFile.indexOf(".");
        if (dot == -1) {
            for (int i = 0; i < writers.length; i++) {
                OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(dataFile + "-" + i));
                writers[i] = new BufferedWriter(osw);
            }
        } else {
            String prefix = dataFile.substring(0, dot);
            String surfix = dataFile.substring(dot);
            for (int i = 0; i < writers.length; i++) {
                OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(prefix + "-" + i + surfix));
                writers[i] = new BufferedWriter(osw);
            }
        }
        indexWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("INDEX")));
    }

    public static void main(String[] args) {
        try {
            HashStrategy hashStrategy = new HashStrategy(args[0]);
            hashStrategy.place();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean place() throws IOException {
        String line;
        index = new HashMap<>();
        int count = 0;
        while ((line = reader.readLine()) != null) {
            String[] spo = line.split("\\t");
            if (spo.length != 3) {
                spo = line.split(" ");
            }
            index.merge(spo[0], 1, (integer, integer2) -> integer + integer2);
            index.merge(spo[2], 1, (integer, integer2) -> integer + integer2);
            int sHash = Math.abs(spo[0].hashCode());
            int pHash = Math.abs(spo[1].hashCode());
            int oHash = Math.abs(spo[2].hashCode());
            int slaveIndex = sHash % slaveNum;
            writers[slaveIndex].write(line + "\n");
            slaveIndex = oHash % slaveNum;
            writers[slaveIndex].write(line + "\n");
        }
        System.out.println(count);
        for (int i = 0; i < slaveNum; i++) {
            writers[i].close();
        }
        for (Map.Entry<String, Integer> entry : index.entrySet()) {
            indexWriter.write(entry.getKey() + "\t" + entry.getValue() + "\n");
        }
        return true;
    }
}
