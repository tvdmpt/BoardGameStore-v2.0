import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Employee extends User {
    private String employeeId;
    private String department;
    private double salary;
    private boolean isAdmin;
    private List<String> permissions;

    public Employee(String username, String password, String email, String fullName,
                    String department, double salary, boolean isAdmin) {
        super(username, password, email, fullName);
        this.employeeId = "EMP-" + java.util.UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        this.department = department;
        this.salary = salary;
        this.isAdmin = isAdmin;
        this.permissions = new ArrayList<>();
        setupPermissions();
    }

    private void setupPermissions() {
        permissions.add("VIEW_GAMES");
        permissions.add("EDIT_GAMES");
        permissions.add("DELETE_GAMES");
        if (isAdmin) {
            permissions.add("MANAGE_EMPLOYEES");
            permissions.add("STATISTICS");
        }
    }

    @Override
    public void performRoleSpecificAction() {
        System.out.println("Сотрудник " + fullName + " работает в отделе " + department);
    }

    @Override
    public String getRole() {
        return isAdmin ? "ADMIN" : "EMPLOYEE";
    }

    @Override
    public String toString() {
        return "Employee[id=" + id + ", empId=" + employeeId + ", role=" + getRole() + "]";
    }

    @Override
    public void showMenu() {
        System.out.println("\n--- МЕНЮ СОТРУДНИКА ---");
        System.out.println("1. Добавить игру");
        System.out.println("2. Просмотреть игры");
        System.out.println("3. Редактировать игру");
        System.out.println("4. Удалить игру");
        System.out.println("5. Поиск");
        System.out.println("6. Экспорт в файл");
        System.out.println("7. Импорт из файла");
        if (isAdmin) {
            System.out.println("8. Управление персоналом");
            System.out.println("9. Статистика");
        }
        System.out.println("0. Выход");
    }

    public boolean isAdmin() { return isAdmin; }
    public String getEmployeeId() { return employeeId; }
}