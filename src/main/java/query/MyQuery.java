package query;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class MyQuery {
    List<Pattern> patterns;
    Set<String> vars;
    Set<String> projection;

    public MyQuery() {
        patterns = new ArrayList<>();
        vars = new HashSet<>();
        projection = new HashSet<>();
    }
    /*
    public static MyQuery loadQuery(String queryPath) {
        MyQuery query = new MyQuery();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(queryPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        Map<String, String> prefixMapping = new HashMap<>();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("PREFIX") || line.startsWith("prefix")) {
                    line = line.substring("prefix".length()).trim();

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return query;
    }
*/
}
