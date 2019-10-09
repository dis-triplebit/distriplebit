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
            BufferedReader reader = new BufferedReader(new FileReader(dataFile));
            HashStrategy hashStrategy = new HashStrategy(reader);
            hashStrategy.place();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
