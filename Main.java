import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static UserManager userManager = new UserManager();
    private static GameManager gameManager = new GameManager();
    private static User currentUser = null;

    public static void main(String[] args) {
        System.out.println("МАГАЗИН НАСТОЛЬНЫХ ИГР");

        while (true) {
            if (currentUser == null) {
                showAuthMenu();
            } else if (currentUser instanceof Employee) {
                showEmployeeMenu((Employee) currentUser);
            } else {
                showCustomerMenu((Customer) currentUser);
            }
        }
    }

    private static void showAuthMenu() {
        System.out.println("\n--- МЕНЮ ---");
        System.out.println("1. Вход");
        System.out.println("2. Регистрация клиента");
        System.out.println("3. Регистрация сотрудника");
        System.out.println("0. Выход");
        System.out.print("Выбор: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1: login(); break;
            case 2: registerCustomer(); break;
            case 3: registerEmployee(); break;
            case 0: System.exit(0);
            default: System.out.println("Неверный выбор");
        }
    }

    private static void login() {
        System.out.print("Логин: ");
        String username = scanner.nextLine();
        System.out.print("Пароль: ");
        String password = scanner.nextLine();
        currentUser = userManager.login(username, password);
    }

    private static void registerCustomer() {
        System.out.print("Логин: ");
        String username = scanner.nextLine();
        System.out.print("Пароль: ");
        String password = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("ФИО: ");
        String fullName = scanner.nextLine();
        System.out.print("Любимый жанр: ");
        String genre = scanner.nextLine();

        if (userManager.registerCustomer(username, password, email, fullName, genre)) {
            System.out.println("Регистрация успешна");
        }
    }

    private static void registerEmployee() {
        System.out.print("Код доступа: ");
        if (!scanner.nextLine().equals("GAME2024")) {
            System.out.println("Неверный код");
            return;
        }

        System.out.print("Логин: ");
        String username = scanner.nextLine();
        System.out.print("Пароль: ");
        String password = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("ФИО: ");
        String fullName = scanner.nextLine();
        System.out.print("Отдел: ");
        String dept = scanner.nextLine();
        System.out.print("Зарплата: ");
        double salary = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Админ? (yes/no): ");
        boolean isAdmin = scanner.nextLine().equalsIgnoreCase("yes");

        if (userManager.registerEmployee(username, password, email, fullName, dept, salary, isAdmin)) {
            System.out.println("Регистрация успешна");
        }
    }

    private static void showEmployeeMenu(Employee emp) {
        emp.showMenu();
        System.out.print("Выбор: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1: addGame(); break;
            case 2: gameManager.viewAllGames(); break;
            case 3: editGame(); break;
            case 4: deleteGame(); break;
            case 5: searchGames(); break;
            case 6: gameManager.exportToTxt(); break;
            case 7: gameManager.importFromTxt(); break;
            case 8:
                if (emp.isAdmin()) listEmployees();
                break;
            case 9:
                if (emp.isAdmin()) showStats();
                break;
            case 0: currentUser = null; break;
            default: System.out.println("Неверный выбор");
        }
    }

    private static void showCustomerMenu(Customer cust) {
        cust.showMenu();
        System.out.print("Выбор: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1: gameManager.viewAllGames(); break;
            case 2: searchGames(); break;
            case 3: gameManager.filterByGenre(cust.getPreferredGenre()); break;
            case 4: System.out.println("Корзина"); break;
            case 5: System.out.println("История покупок"); break;
            case 6:
                System.out.print("Сумма: ");
                cust.addBalance(scanner.nextDouble());
                scanner.nextLine();
                break;
            case 7:
                System.out.println("Профиль: " + cust.getFullName());
                System.out.println("Баланс: " + cust.getBalance());
                break;
            case 0: currentUser = null; break;
            default: System.out.println("Неверный выбор");
        }
    }

    private static void addGame() {
        System.out.print("Название: ");
        String title = scanner.nextLine();
        System.out.print("Жанр: ");
        String genre = scanner.nextLine();
        System.out.print("Цена: ");
        double price = scanner.nextDouble();
        System.out.print("Количество: ");
        int quantity = scanner.nextInt();
        System.out.print("Мин игроков: ");
        int min = scanner.nextInt();
        System.out.print("Макс игроков: ");
        int max = scanner.nextInt();
        System.out.print("Возраст: ");
        int age = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Описание: ");
        String desc = scanner.nextLine();
        System.out.print("Издатель: ");
        String pub = scanner.nextLine();

        BoardGame game = new BoardGame(title, genre, price, quantity, min, max, age, desc, pub);
        gameManager.addGame(game);
    }

    private static void editGame() {
        gameManager.viewAllGames();
        System.out.print("Номер игры: ");
        int num = scanner.nextInt();
        scanner.nextLine();
        gameManager.editGame(num - 1, scanner);
    }

    private static void deleteGame() {
        gameManager.viewAllGames();
        System.out.print("Номер игры: ");
        int num = scanner.nextInt();
        scanner.nextLine();
        gameManager.deleteGame(num - 1);
    }

    private static void searchGames() {
        System.out.print("Поиск: ");
        gameManager.searchGames(scanner.nextLine());
    }

    private static void listEmployees() {
        for (Employee e : userManager.getEmployees()) {
            System.out.println(e);
        }
    }

    private static void showStats() {
        System.out.println("Всего игр: " + gameManager.getGames().size());
    }
}