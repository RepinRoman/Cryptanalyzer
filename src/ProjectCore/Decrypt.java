package ProjectCore;

import java.io.IOException;
import java.nio.file.Path;

class Decrypt {
    static void start(Path path, int key) throws IOException {
        Encrypt.start(path, -key);
    } // Расшифровка - это шифрование с (ключом * -1)!

    static void start(Path path) throws IOException {
        BruteForce.start(path);
    }
}
