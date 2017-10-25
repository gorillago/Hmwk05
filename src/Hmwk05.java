import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Hmwk05 {
    public static void main(String[] args) {
        final int MAX = 500;
        printResults("input.txt", MAX);
    }

    public static void printResults(String fileName, int max) {
        String[] passwords = new String[max];
        int count = readFromFile(fileName, passwords);
        int failCount = 0;
        int weakCount = 0;
        int goodCount = 0;
        int strongCount = 0;
        int veryStrongCount = 0;

        System.out.printf("     Strength    Password            Common    Length    Range    Entropy\n");

        for (int i = 0; i < count; i++) {
            String password = passwords[i];
            int range = getRange(password);
            int length = password.length();
            double entropy = calculateMaximumEntropy(range, length);

            final int MAX = 100;
            String[] commonPasswords = new String[MAX];
            int n = readFromFile("common.txt", commonPasswords);
            boolean isCommon = commonPassword(commonPasswords, n, password);
            String strength = passwordStrength(entropy, isCommon);

            System.out.printf("%4d %-11s %-19s %-9s %-9d %-8d %-7.1f\n", i+1, strength, displayPassword(password, 19), isCommon, length, range, entropy);

            switch (strength) {
                case "Fail":
                    failCount++;
                    break;
                case "Weak":
                    weakCount++;
                    break;
                case "Good":
                    goodCount++;
                    break;
                case "Strong":
                    strongCount++;
                    break;
                case "Very Strong":
                    veryStrongCount++;
                    break;
            }
        }

        System.out.printf("\n%4d (%3.1f%%) Fail\n",  failCount, 100*((double)failCount/(double)count));
        System.out.printf("%4d (%3.1f%%) Weak\n", weakCount, 100*((double)weakCount/(double)count));
        System.out.printf("%4d (%3.1f%%) Good\n", goodCount, 100*((double)goodCount/(double)count));
        System.out.printf("%4d (%3.1f%%) Strong\n", strongCount, 100*((double)strongCount/(double)count));
        System.out.printf("%4d (%3.1f%%) Very Strong\n", veryStrongCount, 100*((double)veryStrongCount/(double)count));
        System.out.printf("%4d Passwords were processed.", count);
    }

    public static String displayPassword(String password, int length) {
        String result = password;
        if (password.length() > length) {
            result = password.substring(0, length);
        }
        return result;
    }

    public static boolean commonPassword(String[] commonPasswords, int count, String password) {
        boolean found = false;

        int current = 0;
        while (!found && (current < count)) {
            found = (password.equalsIgnoreCase(commonPasswords[current]));
            current++;
        }
        return found;
    }

    public static int readFromFile(String fileName, String[] passwords) {
        int count = 0;
        int max = passwords.length;
        File inputFile = new File(fileName);

        try {
            Scanner input = new Scanner(inputFile);
            if (input.hasNextLine()) {
                while ((count < max) && input.hasNextLine()) {
                    passwords[count] = input.nextLine().trim();
                    count++;
                }
            } else {
                System.out.printf("File \"%s\" is empty.\n", inputFile);
                System.exit(1);
            }
            input.close();
        } catch (FileNotFoundException e) {
            System.out.printf("File \"%s\" not found.\n", inputFile);
            System.exit(1);
        }

        return count;
    }

    public static String passwordStrength(double entropy, boolean commonPassword) {

        String result = "";

        if (commonPassword || (entropy < 28)) {
            result = "Fail";
        } else if (entropy < 36) {
            result = "Weak";
        } else if (entropy < 60) {
            result = "Good";
        } else if (entropy < 128) {
            result = "Strong";
        } else if (entropy >= 128) {
            result = "Very Strong";
        }
        return result;
    }

    public static double log2(double number) {
        return Math.log(number)/Math.log(2);
    }

    public static double calculateMaximumEntropy(int range, int length) {
        return log2(Math.pow(range, length-1));
    }

    public static int getRange(String password) {
        int range = 0;

        boolean digit = containsDigits(password);
        boolean lowerCase = containsLowercaseCharacters(password);
        boolean upperCase = containsUppercaseCharacters(password);
        int specialChars = specialCharacters(password);

        if(digit) {
            range += 10;
        }
        if (lowerCase) {
            range += 26;
        }
        if (upperCase) {
            range += 26;
        }
        range += specialChars;

        return range;
    }

    public static boolean containsDigits(String password) {
        boolean found = false;
        char ch = '0';

        while (ch <= '9' && !found) {
            found = (password.indexOf(ch) > -1);
            ch++;
        }
        return found;
    }

    public static boolean containsLowercaseCharacters(String password) {
        boolean found = false;
        int count = 0;

        while (!found && (count < password.length())) {
            char ch = password.charAt(count);
            found = ((ch >= 'a') && (ch <= 'z'));
            count++;
        }
        return found;
    }

    public static boolean containsUppercaseCharacters(String password) {
        boolean found = false;
        int count = 0;

        while (!found && (count < password.length())) {
            char ch = password.charAt(count);
            found = ((ch >= 'A') && (ch <= 'Z'));
            count++;
        }
        return found;
    }

    public static int specialCharacters(String password) {
        String characters = " !\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";
        int count = 0;

        for (int i = 0; i < characters.length(); i++) {
            char ch = characters.charAt(i);
            if (password.indexOf(ch) > -1) {
                count++;
            }
        }
        return count;
    }
}