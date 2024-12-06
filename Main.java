import java.util.Scanner;

public class Main {

    private static boolean isRoman(String str) {
        return str.matches("^(I|V|X)+$");
    }


    private static boolean isArabic(String str) {
        return str.matches("^[0-9]+$") && Integer.parseInt(str) >= 0 && Integer.parseInt(str) <= 10; // Условие: числа от 0 до 10
    }


    private static int romanToArabic(String roman) {
        int total = 0;
        int prevValue = 0;
        for (char ch : roman.toCharArray()) {
            int currentValue = switch (ch) {
                case 'I' -> 1;
                case 'V' -> 5;
                case 'X' -> 10;
                default -> throw new IllegalArgumentException("Неверное римское число");
            };

            if (currentValue > prevValue) {
                total += currentValue - 2 * prevValue;
            } else {
                total += currentValue;
            }
            prevValue = currentValue;
        }
        return total;
    }


    private static String arabicToRoman(int number) {
        if (number <= 0) throw new IllegalArgumentException("Результат должен быть положительным");

        StringBuilder roman = new StringBuilder();
        String[] thousands = {"", "M", "MM", "MMM"};
        String[] hundreds = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
        String[] tens = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        String[] units = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};

        roman.append(thousands[number / 1000]);
        roman.append(hundreds[(number % 1000) / 100]);
        roman.append(tens[(number % 100) / 10]);
        roman.append(units[number % 10]);
        return roman.toString();
    }

    private static int calculate(int a, int b, String operation) {
        return switch (operation) {
            case "+" -> a + b;
            case "-" -> a - b;
            case "*" -> a * b;
            case "/" -> {
                if (b == 0) throw new ArithmeticException("Деление на ноль");
                yield a / b;
            }
            default -> throw new IllegalArgumentException("Неверная операция");
        };
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите арифметическое выражение: ");
        String input = scanner.nextLine().trim();

        String[] parts = input.split(" ");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Неверный формат ввода");
        }

        String firstOperand = parts[0];
        String operation = parts[1];
        String secondOperand = parts[2];
        
        boolean firstIsRoman = isRoman(firstOperand);
        boolean secondIsRoman = isRoman(secondOperand);
        boolean firstIsArabic = isArabic(firstOperand);
        boolean secondIsArabic = isArabic(secondOperand);

        if ((firstIsRoman && secondIsArabic) || (firstIsArabic && secondIsRoman)) {
            throw new IllegalArgumentException("Не могу смешивать римские и арабские числа");
        }

        int a;
        int b;

        if (firstIsRoman && secondIsRoman) {
            a = romanToArabic(firstOperand);
            b = romanToArabic(secondOperand);
            int result = calculate(a, b, operation);
            String romanResult = arabicToRoman(result);
            System.out.println("Результат: " + romanResult);
        } else if (firstIsArabic && secondIsArabic) {
            a = Integer.parseInt(firstOperand);
            b = Integer.parseInt(secondOperand);
            int result = calculate(a, b, operation);
            System.out.println("Результат: " + result);
        } else {
            throw new IllegalArgumentException("Неверный ввод чисел");
        }

        scanner.close();
    }
}