package www.ariandasilvaperez.spend_sense.invoice.services.impl;

import org.springframework.stereotype.Service;
import www.ariandasilvaperez.spend_sense.invoice.services.ITempFilePersistence;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@Service
public class TempFilePersistenceImpl implements ITempFilePersistence {
    @Override
    public Path createTempFile(byte[] imagenFactura) throws IOException {
        Path tempFile = Files.createTempFile("Invoice-", ".tmp");
        Files.write(tempFile, imagenFactura, StandardOpenOption.CREATE);
        return tempFile;
    }

    @Override
    public void deleteTempFile(Path tempFile) throws IOException {
        Files.delete(tempFile);
    }
}
