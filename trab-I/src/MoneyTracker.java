import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.*;

public class MoneyTracker {

    private static final char LEFT = '/';
    private static final char RIGHT = '\\';
    private static final char VERTICAL = '|';
    private static final char HORIZONTAL = '-';
    private static final char BANDITS_CAPTURED = '#';

    private static int operations = 0;
    private static int money = 0;

    private static class Pointer {
        int x;
        int y;
        char content;
        Pointer next;

        public Pointer(int x, int y, char content) {
            this.x = x;
            this.y = y;
            this.content = content;
        }

        @Override
        public String toString() {
            return "Pointer{" +
                    "x=" + x +
                    ", y=" + y +
                    ", content='" + content + '\'' +
                    '}';
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("""
                digite o numero de entradas:
                50
                100
                200
                500
                750
                1000
                1500
                2000
                """);
        int entrada = scanner.nextInt();
        List<Pointer> pointers = new ArrayList<>();
        Pointer previous = null;
        Path path = Paths.get("src/files/casoG" + entrada +".txt");
        List<Pointer> navigation = new ArrayList<>();
        try {
            BufferedReader reader = Files.newBufferedReader(path, Charset.defaultCharset());
            String line;
            int y = 0;
            while ((line = reader.readLine()) != null) {
                for (int x = 0; x < line.length(); x++) {
                    char content = line.charAt(x);
                    if (content != ' ') {
                        Pointer pointer = new Pointer(y, x, content);
                        pointers.add(pointer);
                        previous = pointer;
                        operations++;
                    }
                }
                y++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Pointer start = findStart(pointers);
        navigation.add(start);
        Pointer current = start;
        previous = start;
        int directionX = 1; // 1 = right, -1 = left
        int directionY = 0; // -1 = down, 1 = up
        String moneyString = "";
        while (current.content != BANDITS_CAPTURED) {
            if (current.content == LEFT) {
                if (directionX == 1) {
                    directionX = 0;
                    directionY = -1;
                } else if (directionX == -1) {
                    directionX = 0;
                    directionY = 1;
                } else if (directionY == 1) {
                    directionX = -1;
                    directionY = 0;
                } else if (directionY == -1) {
                    directionX = 1;
                    directionY = 0;

                }
            } else if (current.content == RIGHT) {
                if (directionX == 1) {
                    directionX = 0;
                    directionY = 1;
                } else if (directionX == -1) {
                    directionX = 0;
                    directionY = -1;
                } else if (directionY == 1) {
                    directionX = 1;
                    directionY = 0;
                } else if (directionY == -1) {
                    directionX = -1;
                    directionY = 0;
                }
            }
            for (Pointer pointer : pointers) {
                if (pointer.x == current.x + directionY && pointer.y == current.y + directionX) {
                    navigation.add(pointer);
                    previous = current;
                    current = pointer;
                    if (Character.isDigit(current.content)) {
                        moneyString += current.content;
                    }else{
                        if (!moneyString.isEmpty()) {
                            money += Integer.parseInt(moneyString);
                            moneyString = "";
                        }
                    }
                    operations++;
                    break;
                }

            }
        }
        System.out.println("Entradas:" + entrada + ", Operações: " + operations);
        System.out.println("Dinheiro: " + money);
    }


    public static Pointer findStart(List<Pointer> pointers) {
        for (Pointer pointer : pointers) {
            if (pointer.y == 0 && pointer.content == '-') return pointer;
        }
        return null;
    }
}
