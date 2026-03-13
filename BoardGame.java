import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class BoardGame implements Serializable {
    private String id;
    private String title;
    private String genre;
    private double price;
    private int quantity;
    private int minPlayers;
    private int maxPlayers;
    private int ageLimit;
    private double rating;
    private String description;
    private String publisher;
    private Date addedDate;
    private boolean available;

    public BoardGame(String title, String genre, double price, int quantity,
                     int minPlayers, int maxPlayers, int ageLimit,
                     String description, String publisher) {
        this.id = "GAME-" + java.util.UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        this.title = title;
        this.genre = genre;
        this.price = price;
        this.quantity = quantity;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.ageLimit = ageLimit;
        this.rating = 0.0;
        this.description = description;
        this.publisher = publisher;
        this.addedDate = new Date();
        this.available = quantity > 0;
    }

    @Override
    public String toString() {
        return title + " | " + genre + " | " + price + " rub | " +
                "В наличии: " + quantity + " | Игроков: " + minPlayers + "-" + maxPlayers;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        BoardGame game = (BoardGame) obj;
        return Objects.equals(id, game.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String toFileString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return id + "|" + title + "|" + genre + "|" + price + "|" + quantity + "|" +
                minPlayers + "|" + maxPlayers + "|" + ageLimit + "|" + rating + "|" +
                description + "|" + publisher + "|" + sdf.format(addedDate) + "|" + available;
    }

    public static BoardGame fromFileString(String line) {
        String[] parts = line.split("\\|");
        if (parts.length < 13) return null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            BoardGame game = new BoardGame(parts[1], parts[2], Double.parseDouble(parts[3]),
                    Integer.parseInt(parts[4]), Integer.parseInt(parts[5]),
                    Integer.parseInt(parts[6]), Integer.parseInt(parts[7]),
                    parts[9], parts[10]);
            game.id = parts[0];
            game.rating = Double.parseDouble(parts[8]);
            game.addedDate = sdf.parse(parts[11]);
            game.available = Boolean.parseBoolean(parts[12]);
            return game;
        } catch (Exception e) {
            return null;
        }
    }

    // Геттеры и сеттеры
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getGenre() { return genre; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }

    public void setTitle(String title) { this.title = title; }
    public void setGenre(String genre) { this.genre = genre; }
    public void setPrice(double price) { this.price = price; }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.available = quantity > 0;
    }
    public void setDescription(String desc) { this.description = desc; }
}