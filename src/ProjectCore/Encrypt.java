package ProjectCore;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class Encrypt extends Helper {
    static void start(Path path, int key) throws IOException {
        creationMode = path.getParent().endsWith("«Цезарь»") ? "Special mode" : "Normal mode";
        createDirectoryAndFiles(path);
        encrypt(key);
        resultMessage();
    }

    private static void encrypt(int key) throws IOException { // Чтение, шифрование и запись данных!
        char[] buffer = Files.readString(sourceFilePath).toCharArray();
        key = (key % ALPHABET.length) + ALPHABET.length; // Позволяет задавать корректный ключ ЛЮБЫМ целым числом!

        for (int i = 0; i < buffer.length; i++) {
            for (int j = 0; j < ALPHABET.length; j++) {
                if (buffer[i] == ALPHABET[j]) {
                    buffer[i] = ALPHABET[(j + key) % ALPHABET.length];
                    break;
                }
            }
        }

        Files.writeString(destinFilePath, String.valueOf(buffer));
    }
}
