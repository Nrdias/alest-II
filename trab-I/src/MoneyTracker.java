import java.io.*;
import java.nio.file.*;
import java.util.*;

public class MoneyTracker {

    private static final char LEFT = '/';
    private static final char RIGHT = '\\';
    private static final char VERTICAL = '|';
    private static final char HORIZONTAL = '-';
    private static final char BANDITS_CAPTURED = '#';

    private static class Pointer {
        int x;
        int y;
        char content;

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
        List<Pointer> pointers = new ArrayList<>();
        String path = "src/files/casoG50.txt";
        List<String> navigation = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(path));
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                for (int j = 0; j < line.length(); j++) {
                    char c = line.charAt(j);
                    if (c != ' ') pointers.add(new Pointer(i, j, c));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(pointers);
        Pointer start = findStart(pointers);
        navigation.add(start.toString());
        System.out.println(navigation);

        Pointer current = start;
        Pointer previous = start;
        int directionX = 1; // 1 = right, -1 = left
        int directionY = 0; // -1 = down, 1 = up

        while (current.content != BANDITS_CAPTURED) {
            if (current == start) {
                if (directionX == 1) {
                    previous = current;
                    current = pointers.stream().filter(p -> p.x == start.x && p.y == start.y + 1).findFirst().get();
                }
                if (directionX == -1) {
                    previous = current;
                    current = pointers.stream().filter(p -> p.x == start.x && p.y == start.y - 1).findFirst().get();
                }
                if (directionY == 1) {
                    previous = current;
                    current = pointers.stream().filter(p -> p.x == start.x + 1 && p.y == start.y).findFirst().get();
                }
                if (directionY == -1) {
                    previous = current;
                    current = pointers.stream().filter(p -> p.x == start.x - 1 && p.y == start.y).findFirst().get();
                }
            }

            if (directionX == 1 && current.content == LEFT) {
                directionX = 0;
                directionY = 1;
            }
            if (directionX == 1 && current.content == RIGHT) {
                directionX = 0;
                directionY = -1;
            }
            if (directionX == -1 && current.content == LEFT) {
                directionX = 0;
                directionY = -1;
            }
            if (directionX == -1 && current.content == RIGHT) {
                directionX = 0;
                directionY = 1;
            }
            if (directionY == 1 && current.content == LEFT) {
                directionX = 1;
                directionY = 0;
            }
            if (directionY == 1 && current.content == RIGHT) {
                directionX = -1;
                directionY = 0;
            }
            if (directionY == -1 && current.content == LEFT) {
                directionX = -1;
                directionY = 0;
            }
            if (directionY == -1 && current.content == RIGHT) {
                directionX = 1;
                directionY = 0;
            }

            if (directionX == 1) {
                int x = current.x;
                int y = current.y;
                previous = current;
                current = pointers.stream().filter(p -> p.x == x && p.y == y + 1).findFirst().get();
            }
            if (directionX == -1) {
                int x = current.x;
                int y = current.y;
                previous = current;
                current = pointers.stream().filter(p -> p.x == x && p.y == y - 1).findFirst().get();
            }
            if (directionY == 1) {
                int x = current.x;
                int y = current.y;
                previous = current;
                current = pointers.stream().filter(p -> p.x == x + 1 && p.y == y).findFirst().get();
            }
            if (directionY == -1) {
                int x = current.x;
                int y = current.y;
                previous = current;
                current = pointers.stream().filter(p -> p.x == x - 1 && p.y == y).findFirst().get();
            }

        }

        System.out.println(navigation);
    }


    public static Pointer findStart(List<Pointer> pointers) {
        for (Pointer pointer : pointers) {
            if (pointer.y == 0 && pointer.content == '-') return pointer;
        }
        return null;
    }
}
