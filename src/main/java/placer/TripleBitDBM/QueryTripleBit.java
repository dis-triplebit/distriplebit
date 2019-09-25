package TripleBitDBM;

public class QueryTripleBit {

    static {
        System.load("/home/ganpeng/spark/libtriplebit.so");
    }

    public static void main(String[] args) {
        query("/home/ganpeng/TripleBit/database/", "/home/ganpeng/TripleBit/Query/");
    }

    private static void query(String dbDir, String queryDir) {

        TripleBitDBM tripleBitDBM = new TripleBitDBM();
        if (tripleBitDBM.loadTripleBit(dbDir, queryDir)) {

            String query = "queryLUBM1";
            if (query.equals("exit")) {
                tripleBitDBM.deleteTripleBit();
            } else {
                tripleBitDBM.queryTripleBit(query);
            }
        }
    }

    public void queryData(String dbDir, String queryDir) {
        query(dbDir, queryDir);
    }

}
