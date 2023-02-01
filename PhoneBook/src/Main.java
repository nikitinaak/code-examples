import java.util.Scanner;

public class Main {
    public static PhoneBook phoneBook = new PhoneBook();

    public static void main(String[] args) {
        while (true) {
            System.out.println("Введите номер, имя или команду:");
            String input = new Scanner(System.in).nextLine();
            phoneBook.checkInput(input);
            System.out.println();
        }
    }
}
