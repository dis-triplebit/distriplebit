package query;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MyQueryTest {

    @Test
    public void queryTest() throws IOException {
        BufferedReader reader = new BufferedReader(
                new FileReader("C:\\Users\\peng\\IdeaProjects\\distriplebit\\Query\\queryLUBM7"));
        StringBuffer queryString = new StringBuffer();
        String line;
        while ((line = reader.readLine()) != null){
            queryString.append(line + "\n");
        }
        Query query = QueryFactory.create(queryString.toString());
        System.out.println(query.toString());
    }
}
