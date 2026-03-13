import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameManager {
    private List<BoardGame> games;
    private static final String DATA_FILE = "boardgames.dat";
    private static final String EXPORT_FILE = "games_export.txt";

    public GameManager() {
        games = new ArrayList<>();
        loadGames();
    }

    public void addGame(BoardGame game) {
        games.add(game);
        saveGames();
        System.out.println("Игра добавлена: " + game.getTitle());
    }

    public void viewAllGames() {
        if (games.isEmpty()) {
            System.out.println("Каталог пуст");
            return;
        }
        System.out.println("\n--- КАТАЛОГ ИГР ---");
        for (int i = 0; i < games.size(); i++) {
            System.out.println((i + 1) + ". " + games.get(i));
        }
    }

    public void editGame(int index, Scanner scanner) {
        if (index < 0 || index >= games.size()) {
            System.out.println("Неверный номер");
            return;
        }
        BoardGame game = games.get(index);
        System.out.println("Редактирование: " + game.getTitle());
        System.out.println("1. Название");
        System.out.println("2. Жанр");
        System.out.println("3. Цена");
        System.out.println("4. Количество");
        System.out.print("Выбор: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                System.out.print("Новое название: ");
                game.setTitle(scanner.nextLine());
                break;
            case 2:
                System.out.print("Новый жанр: ");
                game.setGenre(scanner.nextLine());
                break;
            case 3:
                System.out.print("Новая цена: ");
                game.setPrice(scanner.nextDouble());
                scanner.nextLine();
                break;
            case 4:
                System.out.print("Новое количество: ");
                game.setQuantity(scanner.nextInt());
                scanner.nextLine();
                break;
            default:
                System.out.println("Неверный выбор");
                return;
        }
        saveGames();
        System.out.println("Игра обновлена");
    }

    public void deleteGame(int index) {
        if (index < 0 || index >= games.size()) {
            System.out.println("Неверный номер");
            return;
        }
        BoardGame removed = games.remove(index);
        saveGames();
        System.out.println("Игра удалена: " + removed.getTitle());
    }

    public void searchGames(String query) {
        List<BoardGame> results = new ArrayList<>();
        String lower = query.toLowerCase();
        for (BoardGame g : games) {
            if (g.getTitle().toLowerCase().contains(lower) ||
                    g.getGenre().toLowerCase().contains(lower)) {
                results.add(g);
            }
        }
        if (results.isEmpty()) {
            System.out.println("Ничего не найдено");
        } else {
            for (BoardGame g : results) {
                System.out.println(g);
            }
        }
    }

    public void filterByGenre(String genre) {
        for (BoardGame g : games) {
            if (g.getGenre().equalsIgnoreCase(genre)) {
                System.out.println(g);
            }
        }
    }

    public void exportToTxt() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(EXPORT_FILE))) {
            writer.println("ID|Title|Genre|Price|Quantity|MinPlayers|MaxPlayers|Age|Rating|Description|Publisher|Date|Available");
            for (BoardGame g : games) {
                writer.println(g.toFileString());
            }
            System.out.println("Экспорт завершен: " + EXPORT_FILE);
        } catch (IOException e) {
            System.out.println("Ошибка экспорта");
        }
    }

    public void importFromTxt() {
        try (BufferedReader reader = new BufferedReader(new FileReader(EXPORT_FILE))) {
            reader.readLine(); // пропуск заголовка
            String line;
            int count = 0;
            while ((line = reader.readLine()) != null) {
                BoardGame game = BoardGame.fromFileString(line);
                if (game != null && !games.contains(game)) {
                    games.add(game);
                    count++;
                }
            }
            saveGames();
            System.out.println("Импортировано игр: " + count);
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден");
        } catch (IOException e) {
            System.out.println("Ошибка импорта");
        }
    }

    @SuppressWarnings("unchecked")
    private void loadGames() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            games = (List<BoardGame>) ois.readObject();
        } catch (Exception e) {
            addDemoGames();
        }
    }

    private void addDemoGames() {
        games.add(new BoardGame("Каркассон", "Стратегия", 2499.99, 15, 2, 5, 7, "Классическая стратегия", "Hans im Gluck"));
        games.add(new BoardGame("Монополия", "Экономика", 1999.99, 20, 2, 8, 8, "Игра о недвижимости", "Hasbro"));
        games.add(new BoardGame("Кодовые имена", "Пати", 1499.99, 30, 4, 8, 10, "Командная игра", "Czech Games"));
        games.add(new BoardGame("Пандемия", "Кооператив", 3299.99, 10, 2, 4, 8, "Борьба с эпидемией", "Z-Man"));
        saveGames();
    }

    private void saveGames() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(games);
        } catch (Exception e) {
            System.out.println("Ошибка сохранения");
        }
    }

    public List<BoardGame> getGames() {
        return games;
    }
}