package www.ariandasilvaperez.spend_sense.invoice.services;

import java.io.IOException;
import java.nio.file.Path;

public interface ITempFilePersistence{
    Path createTempFile(byte[] imagenFactura) throws IOException;
    void deleteTempFile(Path tempFile) throws IOException;
}
