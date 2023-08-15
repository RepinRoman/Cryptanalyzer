package ProjectCore;

import ProjectExceptions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Scanner;

public class Menu {
    private static final Scanner in = new Scanner(System.in);
    private static Path path;
    private static int key;

    public static void start() throws IOException {
        int modeNumber;
        System.out.print("""
                    \nВас приветствует команда разработки программы шифрования «Цезарь»!
                    (1) Начать!
                    (2) Выход.
                    Ваш выбор:\s""");

        if (in.hasNextInt()) {
            modeNumber = in.nextInt();
            if (modeNumber < 1 || modeNumber > 2) {
                throw new InvalidNumberException("""
                            \nОшибка! Вы ввели неподходящий номер! Необходимо ввести цифру 1 или 2!
                            Перезапустите программу, чтобы попробовать снова!""");
            }
        } else {
            throw new NotNumberException("""
                        \nОшибка! Вы ввели не число! Необходимо ввести цифру 1 или 2!
                        Перезапустите программу, чтобы попробовать снова!""");
        }

        if (modeNumber == 1) {
            Helper.loading("Загрузка");
            stepOneWorkingMode();
        } else {
            Helper.loading("Выход");
            Helper.exitMessage();
        }
    }

    private static void stepOneWorkingMode() throws IOException {
        int modeNumber;
        System.out.print("""
                    Шаг №1. Выберите режим работы программы:
                    (1) Шифрование;
                    (2) Расшифровка;
                    (3) Выход.
                    Ваш выбор:\s""");

        if (in.hasNextInt()) {
            modeNumber = in.nextInt();
            in.nextLine(); // Переход с чисел на строки создаёт лишнюю "", тут она устраняется!
            if (modeNumber < 1 || modeNumber > 3) {
                throw new InvalidNumberException("""
                            \nОшибка! Вы ввели неподходящий номер! Необходимо ввести цифру 1, 2 или 3!
                            Перезапустите программу, чтобы попробовать снова!""");
            }
        } else {
            throw new NotNumberException("""
                        \nОшибка! Вы ввели не число! Необходимо ввести цифру 1, 2 или 3!
                        Перезапустите программу, чтобы попробовать снова!""");
        }

        if (modeNumber == 1) {
            Helper.loading("Загрузка");
            encryptionStepTwo();
        } else if (modeNumber == 2) {
            Helper.loading("Загрузка");
            decryptionStepTwo();
        } else {
            Helper.loading("Выход");
            Helper.exitMessage();
        }
    }

    private static void encryptionStepTwo() throws IOException {
        System.out.print("""
                    Шаг №2. Укажите путь к текстовому файлу, содержимое которого вы хотите зашифровать.
                        -> Указываемый путь должен быть абсолютным!
                        -> Файл должен иметь расширение.txt!
                    Абсолютный путь:\s""");
        path = Helper.txtPathFix(in.nextLine());

        if (!path.isAbsolute()) {
            throw new NotAbsolutePathException("""
                        \nОшибка! Вы ввели не абсолютный путь! Примеры абсолютного пути:
                            -> Windows. C:\\Users\\username\\Desktop\\text.txt
                            -> MacOS. /Users/username/Desktop/text.txt
                            -> Linux. /home/username/Desktop/text.txt
                        Перезапустите программу, чтобы попробовать снова!""");
        } else if (Files.notExists(path)) {
            throw new NoSuchFileException("\nОшибка! Файла с именем \"" + path.getFileName()
                    + "\" в заданной директории \"" + path.getParent() + "\" не существует!"
                    + "\nПерезапустите программу, чтобы попробовать снова!");
        }

        Helper.loading("Загрузка");
        encryptionStepThree();
    }

    private static void encryptionStepThree() throws IOException {
        System.out.print("""
                    Шаг №3. Укажите любое целое число. Оно будет использовано в качестве ключа шифрования.
                    Ключ шифрования:\s""");

        if (in.hasNextInt()) {
            key = in.nextInt();
            in.close();
        } else {
            throw new NotNumberException("""
                        \nОшибка! Вы ввели не целое число! Либо введённое вами число вне диапазона допустимых значений!
                            -> от -2147483648 до 2147483647!
                        Перезапустите программу, чтобы попробовать снова!""");
        }

        Helper.loading("Шифрование");
        Encrypt.start(path, key);
    }

    private static void decryptionStepTwo() throws IOException {
        int modeNumber;
        System.out.print("""
                Шаг №2. Выберите режим расшифровки:
                (1) Расшифровка с использованием ключа:
                    -> Необходим абсолютный путь к файлу, содержимое которого вы хотите расшифровать;
                    -> Необходим ключ (целое число), при помощи которого будет произведена расшифровка.
                (2) Расшифровка без использования ключа:
                    -> Необходим только абсолютный путь к файлу, содержимое которого вы хотите расшифровать;
                    -> Содержимое выбранного вами файла не должно быть короче 50-ти символов!
                Ваш выбор:\s""");

        if (in.hasNextInt()) {
            modeNumber = in.nextInt();
            in.nextLine(); // Переход с чисел на строки создаёт лишнюю "", тут она устраняется!
            if (modeNumber < 1 || modeNumber > 2) {
                throw new InvalidNumberException("""
                            \nОшибка! Вы ввели неподходящий номер! Необходимо ввести цифру 1 или 2!
                            Перезапустите программу, чтобы попробовать снова!""");
            }
        } else {
            throw new NotNumberException("""
                        \nОшибка! Вы ввели не число! Необходимо ввести цифру 1 или 2!
                        Перезапустите программу, чтобы попробовать снова!""");
        }

        Helper.loading("Загрузка");
        if (modeNumber == 1) {
            decryptionWithKeyStepThree();
        } else {
            decryptionKeylessStepThree();
        }
    }

    private static void decryptionWithKeyStepThree() throws IOException {
        System.out.print("""
                Шаг №3. Укажите путь к текстовому файлу, содержимое которого вы хотите расшифровать.
                    -> Указываемый путь должен быть абсолютным!
                    -> Файл должен иметь расширение.txt!
                Абсолютный путь:\s""");
        path = Helper.txtPathFix(in.nextLine());

        if (!path.isAbsolute()) {
            throw new NotAbsolutePathException("""
                        \nОшибка! Вы ввели не абсолютный путь! Примеры абсолютного пути:
                            -> Windows. C:\\Users\\username\\Desktop\\text.txt
                            -> MacOS. /Users/username/Desktop/text.txt
                            -> Linux. /home/username/Desktop/text.txt
                        Перезапустите программу, чтобы попробовать снова!""");
        } else if (Files.notExists(path)) {
            throw new NoSuchFileException("\nОшибка! Файла с именем \"" + path.getFileName()
                    + "\" в заданной директории \"" + path.getParent() + "\" не существует!"
                    + "\nПерезапустите программу, чтобы попробовать снова!");
        }

        Helper.loading("Загрузка");
        decryptionWithKeyStepFour();
    }

    private static void decryptionWithKeyStepFour() throws IOException {
        System.out.print("""
                    Шаг №4. Укажите целое число, что было использовано для шифрования содержимого вашего файла.
                    Оно будет использовано в качестве ключа расшифровки.
                    Ключ расшифровки:\s""");

        if (in.hasNextInt()) {
            key = in.nextInt();
            in.close();
        } else {
            throw new NotNumberException("""
                        \nОшибка! Вы ввели не целое число! Либо введённое вами число вне диапазона допустимых значений!
                            -> от -2147483648 до 2147483647!
                        Перезапустите программу, чтобы попробовать снова!""");
        }

        Helper.loading("Расшифровка");
        Decrypt.start(path, key);
    }

    private static void decryptionKeylessStepThree() throws IOException {
        System.out.print("""
                    Шаг №3. Укажите путь к текстовому файлу, содержимое которого вы хотите расшифровать.
                        -> Указываемый путь должен быть абсолютным!
                        -> Файл должен иметь расширение.txt!
                    Абсолютный путь:\s""");
        path = Helper.txtPathFix(in.nextLine());

        if (!path.isAbsolute()) {
            throw new NotAbsolutePathException("""
                        \nОшибка! Вы ввели не абсолютный путь! Примеры абсолютного пути:
                            -> Windows. C:\\Users\\username\\Desktop\\text.txt
                            -> MacOS. /Users/username/Desktop/text.txt
                            -> Linux. /home/username/Desktop/text.txt
                        Перезапустите программу, чтобы попробовать снова!""");
        } else if (Files.notExists(path)) {
            throw new NoSuchFileException("\nОшибка! Файла с именем \"" + path.getFileName()
                    + "\" в заданной директории \"" + path.getParent() + "\" не существует!"
                    + "\nПерезапустите программу, чтобы попробовать снова!");
        } else if (Files.readString(path).replace("\r\n", " ").length() < 50) {
            throw new TooShortTextException("""
                        \nОшибка! Содержимое выбранного вами файла короче 50-ти символов!
                        Для текстов короче 50-ти символов результат расшифровки без ключа не гарантирован!
                        Перезапустите программу, чтобы попробовать снова!""");
        }

        Helper.loading("Расшифровка");
        Decrypt.start(path);
    }
}
