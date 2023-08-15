package ProjectCore;

import ProjectConstants.Alphabet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class Helper {
    protected static final char[] ALPHABET = Alphabet.getAlphabet();
    protected static String creationMode;
    protected static Path sourceFilePath;
    protected static Path destinFilePath;

    protected static void createDirectoryAndFiles(Path path) throws IOException { // Создание директории и файлов!
        if (creationMode.equals("Normal mode")) { // Исходный файл находится НЕ В папке «Цезарь»!
            Path caesarPath = Path.of(path.getParent() + File.separator + "«Цезарь»");
            Path sourceFile = Path.of(caesarPath + File.separator + "1_Исходный файл.txt");
            Path destinFile = Path.of(caesarPath + File.separator + "2_Итоговый файл.txt");

            if (Files.notExists(caesarPath)) { // Папка «Цезарь» не существует - создать её!
                Files.createDirectory(caesarPath);
            } else { // Папка «Цезарь» существует - очистить её!
                Files.deleteIfExists(sourceFile);
                Files.deleteIfExists(destinFile);
            }

            sourceFilePath = Files.move(path, sourceFile);
            destinFilePath = Files.createFile(destinFile);

        } else if (creationMode.equals("Special mode")) { // Исходный файл находится В папке «Цезарь»!
            Path sourceFile = Path.of(path.getParent() + File.separator + "1_Исходный файл.txt");
            Path finalFile  = Path.of(path.getParent() + File.separator + "2_Итоговый файл.txt");

            if (!path.equals(sourceFile)) Files.deleteIfExists(sourceFile);

            sourceFilePath = Files.move(path, sourceFile);
            destinFilePath = Files.exists(finalFile) ? finalFile : Files.createFile(finalFile);
        }
    }

    protected static void resultMessage() {
        System.out.println("Успех! Результат работы программы можно найти в папке «Цезарь»!");
        System.out.println("\t-> " + destinFilePath.getParent());
        System.out.println("Спасибо, что выбрали нашу программу! Возвращайтесь к нам ещё!");
    }

    static void exitMessage() {
        System.out.println("""
                Очень жаль, что мы не смогли вам ничем помочь. Мы будем по вам скучать!
                Если захотите попробовать снова - просто перезапустите программу!""");
    }

    static void loading(String message) {
        try {
            Thread.sleep(1000);
            System.out.print("\n" + message);
            Thread.sleep(500);
            System.out.print(".");
            Thread.sleep(500);
            System.out.print(".");
            Thread.sleep(500);
            System.out.print(".");
            Thread.sleep(500);
            System.out.println("\n");
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            System.err.print(ex.getMessage());
        }
    }

    static Path txtPathFix(String path) { // Пользователь мог забыть про .txt, данный метод это исправляет!
        return path.endsWith(".txt") ? Path.of(path) : Path.of(path + ".txt");
    }
}
