import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksIterator;

public class RocksDBDemo {
    static {
        RocksDB.loadLibrary();
    }

    private static RocksDB rocksDB;

    private static String path = "/opt/myRocksDB";

    public static void main(String[] args) throws Exception {
        Options options = new Options();
        options.setCreateIfMissing(true);
        rocksDB = RocksDB.open(options, path);
        System.out.println("put: key is felixzh_key, value is felixzh_value");
        rocksDB.put("felixzh_key".getBytes(), "felixzh_value".getBytes());
        System.out.println("===================================");
        byte[] bytes = rocksDB.get("felixzh_key".getBytes());
        System.out.println("get: key is felixzh_key, value is " + new String(bytes));
        RocksIterator iter = rocksDB.newIterator();
        System.out.println("===================================");
        System.out.println("all key and value:");
        for (iter.seekToFirst(); iter.isValid(); iter.next()) {
            System.out.println("iter key: " + new String(iter.key()) + ",iter value: " +
                    new String(iter.value()));
        }
    }
}
