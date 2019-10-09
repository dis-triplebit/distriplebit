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
}
