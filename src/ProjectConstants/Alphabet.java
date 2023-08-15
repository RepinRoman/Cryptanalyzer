package ProjectConstants;

public class Alphabet {
    private static final char[] ALPHABET = {
            'А', 'а', 'Б', 'б', 'В', 'в', 'Г', 'г', 'Д', 'д', 'Е',
            'е', 'Ё', 'ё', 'Ж', 'ж', 'З', 'з', 'И', 'и', 'Й', 'й',
            'К', 'к', 'Л', 'л', 'М', 'м', 'Н', 'н', 'О', 'о', 'П',
            'п', 'Р', 'р', 'С', 'с', 'Т', 'т', 'У', 'у', 'Ф', 'ф',
            'Х', 'х', 'Ц', 'ц', 'Ч', 'ч', 'Ш', 'ш', 'Щ', 'щ', 'Ъ',
            'ъ', 'Ы', 'ы', 'Ь', 'ь', 'Э', 'э', 'Ю', 'ю', 'Я', 'я',

            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '.', ',', '!', '?', ':', '-', '"', ' '};

    public static char[] getAlphabet() {
        return ALPHABET;
    }
}
