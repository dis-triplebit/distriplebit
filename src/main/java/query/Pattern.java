package query;

public class Pattern {
    String subject;
    String predicate;
    String object;
    boolean isSubVar;
    boolean isPreVar;
    boolean isObjVar;

    public boolean isSubjectVar() {
        return subject.startsWith("?");
    }

    public boolean isPredicateVar() {
        return predicate.startsWith("?");
    }

    public boolean isObjectVar() {
        return object.startsWith("?");
    }

    @Override
    public String toString() {
        String rst = "";
        if (subject.startsWith("http"))
            rst += "<" + subject + ">";
        else rst += subject;
        rst += " ";
        if (predicate.startsWith("http"))
            rst += "<" + predicate + ">";
        else rst += predicate;
        rst += " ";
        if (object.startsWith("http"))
            rst += "<" + object + ">";
        else rst += object;
        return rst;
    }
}
