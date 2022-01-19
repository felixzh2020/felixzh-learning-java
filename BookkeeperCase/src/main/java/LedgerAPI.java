import org.apache.bookkeeper.client.BookKeeper;
import org.apache.bookkeeper.client.LedgerEntry;
import org.apache.bookkeeper.client.LedgerHandle;

import java.nio.ByteBuffer;
import java.util.Enumeration;

public class LedgerAPI {
    public static void main(String[] args) throws Exception {
        // Create a client object for the local ensemble. This
// operation throws multiple exceptions, so make sure to
// use a try/catch block when instantiating client objects.
        BookKeeper bkc = new BookKeeper("felixzh:2181");

// A password for the new ledger
        byte[] ledgerPassword = "felixzh".getBytes();

// Create a new ledger and fetch its identifier
        LedgerHandle lh = bkc.createLedger(1, 1, BookKeeper.DigestType.MAC, ledgerPassword);
        long ledgerId = lh.getId();

// Create a buffer for four-byte entries
        ByteBuffer entry = ByteBuffer.allocate(4);

        int numberOfEntries = 3;

// Add entries to the ledger, then close it
        for (int i = 0; i < numberOfEntries; i++) {
            entry.putInt(i);
            entry.position(0);
            lh.addEntry(entry.array());
        }
        lh.close();

// Open the ledger for reading
        lh = bkc.openLedger(ledgerId, BookKeeper.DigestType.MAC, ledgerPassword);

// Read all available entries
        Enumeration<LedgerEntry> entries = lh.readEntries(0, numberOfEntries - 1);

        while (entries.hasMoreElements()) {
            ByteBuffer result = ByteBuffer.wrap(entries.nextElement().getEntry());
            Integer retrEntry = result.getInt();

            // Print the integer stored in each entry
            System.out.println(String.format("Result: %s", retrEntry));
        }

// Close the ledger and the client
        lh.close();
        bkc.close();
    }
}
