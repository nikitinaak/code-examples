import java.util.*;

public class PhoneBook {
    private Map<String, Set<String>> phoneBook = new TreeMap<>();
    public static final String REGEX_NAME = "[А-ЯЁ][а-яё]+";
    public static final String REGEX_PHONE = "7[0-9]{10}";
    public static final String REGEX_LIST = "LIST";
    public static final String WRONG_FORMAT = "Неверный формат ввода";

    public void checkInput(String input) {
        if (input.matches(REGEX_NAME)) {
            inputIsName(input);
        }
        if (input.matches(REGEX_PHONE)) {
            inputIsPhone(input);
        }
        if (input.matches(REGEX_LIST)) {
            for (String output : getAllContacts()) {
                System.out.println(output);
            }
        }
        if (!input.matches(REGEX_NAME) && !input.matches(REGEX_PHONE) && !input.matches(REGEX_LIST)) {
            System.out.println(WRONG_FORMAT);
        }
    }

    public void inputIsName(String input) {
        if (phoneBook.containsKey(input)) {
            for (String output : getContactByName(input)) {
                System.out.println(output);
            }
        } else {
            System.out.println("Такого имени в телефонной книге нет." +
                    "\nВведите номер телефона для абонента " + '"' +
                    input + '"' + ':');
            String phone = new Scanner(System.in).nextLine();
            if (phone.matches(REGEX_PHONE)) {
                addContact(input, phone);
                System.out.println("Контакт сохранён!");
            } else {
                System.out.println(WRONG_FORMAT);
            }
        }
    }

    public void inputIsPhone(String input) {
        if (getContactByPhone(input).isEmpty()) {
            System.out.println("Такого номера нет в телефонной книге." +
                    "\nВведите имя абонента для номера " + '"' +
                    input + '"' + ':');
            String name = new Scanner(System.in).nextLine();
            if (name.matches(REGEX_NAME)) {
                addContact(name, input);
                System.out.println("Контакт сохранён!");
            } else {
                System.out.println(WRONG_FORMAT);
            }
        }
    }

    public void addContact(String phone, String name) {
        if (name.matches(REGEX_NAME) && phone.matches(REGEX_PHONE)) {
            if (phoneBook.containsKey(name)) {
                for (Map.Entry<String, Set<String>> entry : phoneBook.entrySet()) {
                    Set<String> phoneList = entry.getValue();
                    for (String phoneNumber : phoneList) {
                        if (phoneNumber.equals(phone)) {
                            phoneList.remove(phone);
                        }
                    }
                }
                Set<String> newPhoneList = new TreeSet<>(phoneBook.get(name));
                newPhoneList.add(phone);
                phoneBook.put(name, newPhoneList);
            } else {
                for (Map.Entry<String, Set<String>> entry : phoneBook.entrySet()) {
                    String key = entry.getKey();
                    Set<String> phoneList = entry.getValue();
                    for (String phoneNumber : phoneList) {
                        if (phoneNumber.equals(phone)) {
                            phoneList.remove(phone);
                        }
                    }
                    if (phoneBook.get(key).isEmpty()) {
                        phoneBook.remove(key);
                    }
                }
                Set<String> phoneList = new TreeSet<>();
                phoneList.add(phone);
                phoneBook.put(name, phoneList);
            }
        }
    }

    public String getContactByPhone(String phone) {
        String contactByPhone = "";
        for (Map.Entry<String, Set<String>> entry : phoneBook.entrySet()) {
            String name = entry.getKey();
            Set<String> phoneList = entry.getValue();
            if (phoneList.contains(phone)) {
                contactByPhone += name + " - ";
                for (String phoneSet : phoneList) {
                    contactByPhone += phoneSet + ", ";
                }
                contactByPhone = contactByPhone.substring(0, contactByPhone.length() - 2);
            }
        }
        return contactByPhone;
    }

    public Set<String> getContactByName(String name) {
        if (phoneBook.containsKey(name)) {
            Set<String> contactByName = new TreeSet<>();
            for (Map.Entry<String, Set<String>> entry : phoneBook.entrySet()) {
                Set<String> phoneList = entry.getValue();
                String key = entry.getKey();
                if (key.equals(name)) {
                    String s = name + " - ";
                    for (String phone : phoneList) {
                        s += phone + ", ";
                    }
                    contactByName.add(s.substring(0, s.length() - 2));
                }
            }
            return contactByName;
        }
        return new TreeSet<>();
    }

    public Set<String> getAllContacts() {
        if (!phoneBook.isEmpty()) {
            Set<String> allContacts = new TreeSet<>();
            for (Map.Entry<String, Set<String>> entry : phoneBook.entrySet()) {
                String name = entry.getKey();
                Set<String> phoneList = entry.getValue();
                String s = name + " - ";
                for (String phone : phoneList) {
                    s += phone + ", ";
                }
                allContacts.add(s.substring(0, s.length() - 2));
            }
            return allContacts;
        }
        return new TreeSet<>();
    }
}
