package ProjectCore;

import ProjectExceptions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

class BruteForce extends Helper {
    static void start(Path path) throws IOException {
        creationMode = path.getParent().endsWith("«Цезарь»") ? "Special mode" : "Normal mode";
        createDirectoryAndFiles(path);
        decrypt();
    }

    private static void decrypt() throws IOException { // Чтение, расшифровка и запись данных!
        String source = Files.readString(sourceFilePath);
        ArrayList<String> resultList = new ArrayList<>();

        for (int key = 0; key < ALPHABET.length; key++) {
            char[] buffer = source.toCharArray();
            for (int i = 0; i < buffer.length; i++) {
                for (int j = 0; j < ALPHABET.length; j++) {
                    if (buffer[i] == ALPHABET[j]) {
                        buffer[i] = ALPHABET[(j + key) % ALPHABET.length];
                        break;
                    }
                }
            }

            String text = String.valueOf(buffer).replace("\r\n", " ");
            int spaceCounter = 0;
            for (int i = 0; i < text.length(); i++) {
                if (Character.isSpaceChar(text.charAt(i))) spaceCounter++;
            }
            long norma = Math.round(text.length() * 0.11); // Частота встречаемости пробела (~11%)

            if (text.contains("\s\s") || spaceCounter < 3) continue;
            if (spaceCounter >= norma && resultList.size() < 5) {
                resultList.add(String.valueOf(buffer));
            }
        }

        analysisOfResults(resultList);
    }

    private static void analysisOfResults(ArrayList<String> resultList) throws IOException {
        try (Scanner in = new Scanner(System.in)) {
            if (resultList.isEmpty()) {
                throw new FailedToDecryptException("""
                    \nОшибка! Не удалось расшифровать содержимое файла! Убедитесь, что содержимое файла:
                        -> Было зашифровано ранее;
                        -> Не состоит из написанного вами случайного набора символов.
                    Перезапустите программу, чтобы попробовать снова!""");
            } else if (resultList.size() == 1) {
                Files.writeString(destinFilePath, resultList.get(0));
                resultMessage();
            } else {
                int number;
                System.out.println("""
                        Внимание! В процессе расшифровки без ключа возникла неопределённость. Требуется ваша помощь!
                        Изучите текстовые фрагменты ниже и выберете из них наиболее осмысленный.
                        На его основе процесс расшифровки будет продолжен!
                            (0) -> [Ничего внятного, выход!]""");
                for (int i = 0; i < resultList.size(); i++) {
                    String[] temp = resultList.get(i).replace("\r\n", " ").split(" ");
                    System.out.print(i != resultList.size() - 1 ?
                            "\t(" + (i + 1) + ") " + "-> " + temp[0] + " " + temp[1] + "..." + "\n" :
                            "\t(" + (i + 1) + ") " + "-> " + temp[0] + " " + temp[1] + "...");
                }
                System.out.print("\nВаш выбор: ");

                if (in.hasNextInt()) {
                    number = in.nextInt();
                    if (number < 0 || number > resultList.size()) {
                        System.out.println();
                        throw new InvalidNumberException("""
                            \nОшибка! Вы ввели неподходящий номер! Необходимо ввести одну из цифр указанных слева!
                            Перезапустите программу, чтобы попробовать снова!""");
                    }
                } else {
                    System.out.println();
                    throw new NotNumberException("""
                        \nОшибка! Вы ввели не число! Необходимо ввести одну из цифр указанных слева!
                        Перезапустите программу, чтобы попробовать снова!""");
                }

                if (number != 0) {
                    loading("Расшифровка");
                    Files.writeString(destinFilePath, resultList.get(number - 1));
                    resultMessage();
                } else {
                    loading("Выход");
                    exitMessage();
                }
            }
        } catch (Exception ex) {
            Files.deleteIfExists(sourceFilePath);
            Files.deleteIfExists(destinFilePath);
            Files.deleteIfExists(destinFilePath.getParent());
            throw ex;
        }
    }
}
