package query;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class MyQuery {
    List<Pattern> patterns;
    Set<String> vars;
    List<String> projection;

    public MyQuery() {
        patterns = new ArrayList<>();
        vars = new HashSet<>();
        projection = new ArrayList<>();
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
