package query;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.sparql.core.TriplePath;
import org.apache.jena.sparql.core.Var;
import org.apache.jena.sparql.syntax.Element;
import org.apache.jena.sparql.syntax.ElementGroup;
import org.apache.jena.sparql.syntax.ElementPathBlock;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class QueryDecomposition {
    Query query;
    List<MyQuery> queries;
    List<TriplePath> tps;
    Map<String, Integer> degree = new HashMap<>();
    List<Map.Entry<String, Integer>> list;

    public QueryDecomposition() {
        query = null;
        queries = null;
    }

    public QueryDecomposition(Query query) {
        this.query = query;
        queries = new ArrayList<>();
    }

    public static void main(String[] args) throws IOException {
        // 不加下面这行运行jar包会报错，尚不知道原因。解决方案来自
        // https://stackoverflow.com/questions/38081684/compiling-a-class-which-depends-on-jena
        org.apache.jena.query.ARQ.init();
        BufferedReader reader = null;
        try {
            System.out.println("current path: " + new File("").getCanonicalPath());
            reader = new BufferedReader(
                    new FileReader(args[0]));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        StringBuffer queryString = new StringBuffer();
        String line;
        while ((line = reader.readLine()) != null) {
            queryString.append(line + "\n");
        }
        System.out.println(queryString);
        Query query = QueryFactory.create(queryString.toString());
        QueryDecomposition queryDecomposition = new QueryDecomposition(query);
        queryDecomposition.init();
        queryDecomposition.decompose();
        queryDecomposition.generateQuery();
    }

    public void init() {
        Element e = query.getQueryPattern();
        if (e instanceof ElementGroup) {
            Element l = ((ElementGroup) e).getElements().get(0);
            tps = ((ElementPathBlock) l).getPattern().getList();
        } else {
            System.out.printf("----1-----");
        }
        for (TriplePath tp : tps) {
            int x = degree.getOrDefault(tp.getSubject().toString(), 0);
            degree.put(tp.getSubject().toString(), x + 1);
            x = degree.getOrDefault(tp.getObject().toString(), 0);
            degree.put(tp.getObject().toString(), x + 1);
        }
        list = degree.entrySet().stream().sorted((o1, o2) -> o2.getValue() - o1.getValue())
                     .collect(Collectors.toList());
    }

    public boolean decompose() {
        for (TriplePath tp : tps) {
            System.out.println(tp.getSubject() + " " + tp.getPredicate() + " " + tp.getObject());
        }
        for (Map.Entry<String, Integer> entry : list) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }

        for (Map.Entry<String, Integer> entry : list) {
            if (tps.isEmpty()) break;
            MyQuery myQuery = new MyQuery();
            List<Pattern> patterns = myQuery.patterns;
            Set<String> vars = myQuery.vars;
            Set<String> projection = myQuery.projection;
            List<Var> projectVars = query.getProjectVars();
            for (Var projectVar : projectVars) {
                projection.add("?" + projectVar.getVarName());
            }
            Iterator<TriplePath> iterator = tps.iterator();
            while (iterator.hasNext()) {
                TriplePath next = iterator.next();
                Pattern pattern = tpToPat(next);
                if (next.getSubject().toString().equals(entry.getKey())) {
                    patterns.add(pattern);
                    updateVars(vars, pattern);
                    iterator.remove();
                } else if (next.getObject().toString().equals(entry.getKey())) {
                    patterns.add(pattern);
                    updateVars(vars, pattern);
                    iterator.remove();
                }
            }
            if (!myQuery.patterns.isEmpty()) {
                queries.add(myQuery);
            }
        }
        return true;
    }

    public void generateQuery() throws IOException {
        for (int i = 0; i < queries.size(); i++) {
            MyQuery query = queries.get(i);
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("subquery-" + i + ".sq"));
            BufferedWriter writer = new BufferedWriter(osw);
            writer.write(constructQuery(query));
            writer.close();
            osw.close();
        }
    }

    public String constructQuery(MyQuery query) {
        String subQuery = "select ";
        for (String var : query.vars) {
            subQuery += var + " ";
        }
        subQuery += "where {\n";
        for (Pattern pattern : query.patterns) {
            subQuery += "\t" + pattern + " .\n";
        }
        subQuery += "}\n";
        return subQuery;
    }

    public Pattern tpToPat(TriplePath tp) {
        Pattern pattern = new Pattern();
        pattern.subject = tp.getSubject().toString();
        pattern.predicate = tp.getPredicate().toString();
        pattern.object = tp.getObject().toString();
        if (pattern.isSubjectVar())
            pattern.isSubVar = true;
        if (pattern.isPredicateVar())
            pattern.isPreVar = true;
        if (pattern.isObjectVar())
            pattern.isObjVar = true;
        return pattern;
    }

    public void updateVars(Set<String> vars, Pattern pattern) {
        if (pattern.isSubVar) {
            vars.add(pattern.subject);
        }
        if (pattern.isPreVar) {
            vars.add(pattern.predicate);
        }
        if (pattern.isObjVar) {
            vars.add(pattern.object);
        }
    }
}