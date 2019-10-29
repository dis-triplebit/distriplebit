package placer.strategy;

import config.Config;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class HashStrategyTest {

    @Test
    public void test() {
        String dataFile = Config.getProp("INPUTFILE");
        try {
            HashStrategy hashStrategy = new HashStrategy(dataFile);
            hashStrategy.place();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
