import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Customer extends User {
    private String customerId;
    private double balance;
    private String preferredGenre;
    private List<String> purchaseHistory;
    private int loyaltyPoints;

    public Customer(String username, String password, String email, String fullName,
                    String preferredGenre) {
        super(username, password, email, fullName);
        this.customerId = "CUST-" + java.util.UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        this.balance = 0.0;
        this.preferredGenre = preferredGenre;
        this.purchaseHistory = new ArrayList<>();
        this.loyaltyPoints = 0;
    }

    @Override
    public void performRoleSpecificAction() {
        System.out.println("Клиент " + fullName + " ищет игры жанра " + preferredGenre);
        System.out.println("Баллы лояльности: " + loyaltyPoints);
    }

    @Override
    public String getRole() {
        return "CUSTOMER";
    }

    @Override
    public String toString() {
        return "Customer[id=" + id + ", custId=" + customerId + ", genre=" + preferredGenre + "]";
    }

    @Override
    public void showMenu() {
        System.out.println("\n--- МЕНЮ КЛИЕНТА ---");
        System.out.println("1. Каталог игр");
        System.out.println("2. Поиск игры");
        System.out.println("3. Игры по жанру");
        System.out.println("4. Корзина");
        System.out.println("5. История покупок");
        System.out.println("6. Пополнить счет");
        System.out.println("7. Профиль");
        System.out.println("0. Выход");
    }

    public void addPurchase(String gameTitle, double price) {
        purchaseHistory.add(gameTitle + " - " + price + " rub [" + new Date() + "]");
        loyaltyPoints += (int)(price / 100);
    }

    public void addBalance(double amount) {
        balance += amount;
    }

    public double getBalance() { return balance; }
    public int getLoyaltyPoints() { return loyaltyPoints; }
    public String getPreferredGenre() { return preferredGenre; }
}