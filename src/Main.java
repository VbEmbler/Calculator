import java.util.Scanner;
import java.lang.String;

public class Main {

    public static void main(String[] args) {
        String inputString;
        String result;
        Scanner in = new Scanner(System.in);

        System.out.println("""
                Введите математическую операцию в виде два операнда и один оператор.
                Операция может содержать только целые арабские и римские цифры от 1 до 10 включительно.
                Пример: 4 + 8 или III - I:""");
        inputString = in.nextLine();

        result = calc(inputString);
        System.out.println("Результат = " + result);
    }

    public static String calc(String input) {
        String firstNumberString;
        String secondNumberString;
        String symbolRegex = "[1-9]|10|I|II|III|IV|V|VI|VII|VIII|IX|X";
        String result = "";
        int firstNumber;
        int secondNumber;
        int indexOfMathSymbol;
        char operator;
        boolean isFirstNumberInt = true;
        boolean isSecondNumberInt = true;

        indexOfMathSymbol = getIndexOfMathSymbol(input);
        firstNumberString = getFirstNumber(input, indexOfMathSymbol);
        secondNumberString = getSecondNumber(input, indexOfMathSymbol);

        if(!firstNumberString.matches(symbolRegex) || !secondNumberString.matches(symbolRegex)) {
            System.out.println("Разрешенн ввод только целых арабских или римских цифр от 1 до 10 включительно.");
            System.exit(1);
        }

        try {
            firstNumber = Integer.parseInt(firstNumberString);
        } catch (Exception e) {
            firstNumber = RomanNumeral.valueOf(firstNumberString).getArabicNumber();
            isFirstNumberInt = false;
        }

        try {
            secondNumber = Integer.parseInt(secondNumberString);
        } catch (Exception e) {
            secondNumber = RomanNumeral.valueOf(secondNumberString).getArabicNumber();
            isSecondNumberInt = false;
        }

        if(isFirstNumberInt != isSecondNumberInt) {
            try {
                throw new Exception();
            } catch (Exception e) {
                System.out.println("Используются одновременно разные системы счисления.");
                System.exit(1);
            }
        }
        operator = input.charAt(indexOfMathSymbol);

        switch (operator) {
            case '+' -> result = Integer.toString(firstNumber + secondNumber);
            case '-' -> result = Integer.toString(firstNumber - secondNumber);
            case '*' -> result = Integer.toString(firstNumber * secondNumber);
            case '/' -> result = Integer.toString(firstNumber / secondNumber);
        }

        if(!isFirstNumberInt) {
            if(Integer.parseInt(result) < 1) {
                try {
                    throw new Exception();
                } catch (Exception e) {
                    System.out.println("В римской системе нет 0 и отрицательных чисел.");
                    System.exit(1);
                }
            }
            result = IntegerConverter.intToRoman(Integer.parseInt(result));
        }
        return result;
    }

    private static int getIndexOfMathSymbol(String input) {
        int indexOfMathSymbol = 0;
        int symbolCount = 0;

        for(int i = 0; i < input.length(); i++) {
            char symbol = input.charAt(i);
            if (symbol == '+' ||  symbol == '-' ||  symbol == '*' || symbol == '/') {
                symbolCount++;
                if(symbolCount == 2) {
                    try {
                        throw new Exception();
                    } catch (Exception e) {
                        System.out.println("Формат математической операции не удовлетворяет заданию - " +
                                "два операнда и один оператор (+, -, /, *).");
                        System.exit(1);
                    }
                }
                indexOfMathSymbol = i;
            }
        }

        if(indexOfMathSymbol == 0) {
            try {
                throw new Exception();
            } catch (Exception e) {
                System.out.println("Строка не является математической операцией.");
                System.exit(1);
            }
        }
        return indexOfMathSymbol;
    }

    private static String getFirstNumber(String inputString, int operatorIndex) {
        String firstNumber;

        firstNumber = inputString.substring(0, operatorIndex).trim();
        return firstNumber;
    }

    private static String getSecondNumber(String inputString, int operatorIndex) {
        String secondNumber;

        secondNumber = inputString.substring(operatorIndex + 1).trim();
        return secondNumber;
    }
}