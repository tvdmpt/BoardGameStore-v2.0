import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private List<User> users;
    private static final String FILE_NAME = "boardgame_users.dat";

    public UserManager() {
        users = new ArrayList<>();
        loadUsers();
    }

    public boolean registerEmployee(String username, String password, String email,
                                    String fullName, String department,
                                    double salary, boolean isAdmin) {
        if (findUser(username) != null) {
            System.out.println("Пользователь уже существует");
            return false;
        }
        users.add(new Employee(username, password, email, fullName, department, salary, isAdmin));
        saveUsers();
        return true;
    }

    public boolean registerCustomer(String username, String password, String email,
                                    String fullName, String preferredGenre) {
        if (findUser(username) != null) {
            System.out.println("Пользователь уже существует");
            return false;
        }
        users.add(new Customer(username, password, email, fullName, preferredGenre));
        saveUsers();
        return true;
    }

    public User login(String username, String password) {
        User user = findUser(username);
        if (user != null && user.getPassword().equals(password)) {
            System.out.println("Добро пожаловать, " + user.getFullName());
            user.performRoleSpecificAction();
            return user;
        }
        System.out.println("Неверный логин или пароль");
        return null;
    }

    private User findUser(String username) {
        for (User u : users) {
            if (u.getUsername().equals(username)) return u;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private void loadUsers() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            users = (List<User>) ois.readObject();
        } catch (FileNotFoundException e) {
            createDefaults();
        } catch (Exception e) {
            createDefaults();
        }
    }

    private void createDefaults() {
        users.add(new Employee("admin", "admin123", "admin@shop.ru", "Админ", "Управление", 80000, true));
        users.add(new Employee("manager", "mgr123", "mgr@shop.ru", "Менеджер", "Продажи", 50000, false));
        users.add(new Customer("player", "play123", "player@mail.ru", "Игрок Иванов", "Стратегии"));
        saveUsers();
    }

    private void saveUsers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(users);
        } catch (Exception e) {
            System.out.println("Ошибка сохранения");
        }
    }

    public List<Employee> getEmployees() {
        List<Employee> list = new ArrayList<>();
        for (User u : users) {
            if (u instanceof Employee) list.add((Employee)u);
        }
        return list;
    }
}