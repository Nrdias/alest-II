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
        List<Pointer> pointers = new ArrayList<>();
        String path = "src/files/casoG50.txt";
        List<Pointer> navigation = new ArrayList<>();
        Pointer previousPointer = null;
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

        for (int i = 0; i < pointers.size(); i++) {
            Pointer pointer = pointers.get(i);
            if (previousPointer != null) {
                previousPointer.next = pointer;
            }
            previousPointer = pointer;
        }


        System.out.println(pointers);
        Pointer start = findStart(pointers);
        Pointer current = start;
        Pointer next = current.next;
        Pointer previous = start;
        navigation.add(current);
        int directionX = 1;
        int directionY = 0;

        while (current.content != BANDITS_CAPTURED) {
            System.out.println(current);
            if (current.content == HORIZONTAL) {
                if (next.content == VERTICAL) {
                    current = next.next;
                    continue;
                }
                if (previous.content == HORIZONTAL || previous.content == LEFT || previous.content == RIGHT || Character.isDigit(previous.content)) {
                    if (directionX == 1) {
                        for (Pointer pointer : pointers) {
                            if (pointer.x == current.x && pointer.y == current.y + 1) {
                                previous = current;
                                current = pointer;
                                previous.next = current;
                                navigation.add(current);
                                break;
                            }
                        }
                    }
                    if (directionX == -1) {
                        for (Pointer pointer : pointers) {
                            if (pointer.x == current.x && pointer.y == current.y - 1) {
                                previous = current;
                                current = pointer;
                                previous.next = current;
                                navigation.add(current);
                                break;
                            }
                        }
                    }
                }

            }
            if (current.content == VERTICAL) {
                if (next.content == HORIZONTAL) {
                    current = next.next;
                    continue;
                }
                if (previous.content == VERTICAL || previous.content == LEFT || previous.content == RIGHT || Character.isDigit(previous.content)) {
                    if (directionY == 1) {
                        for (Pointer pointer : pointers) {
                            if (pointer.x == current.x + 1 && pointer.y == current.y) {
                                previous = current;
                                current = pointer;
                                previous.next = current;
                                navigation.add(current);
                                break;
                            }
                        }
                    }
                    if (directionY == -1) {
                        for (Pointer pointer : pointers) {
                            if (pointer.x == current.x - 1 && pointer.y == current.y) {
                                previous = current;
                                current = pointer;
                                previous.next = current;
                                navigation.add(current);
                                break;
                            }
                        }
                    }
                }
            }
            if (current.content == LEFT) {
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
                for (Pointer pointer : pointers) {
                    if (pointer.x == current.x + directionX && pointer.y == current.y + directionY) {
                        previous = current;
                        current = pointer;
                        previous.next = current;
                        navigation.add(current);
                        break;
                    }
                }
            }
            if (current.content == RIGHT) {
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
                for (Pointer pointer : pointers) {
                    if (pointer.x == current.x + directionX && pointer.y == current.y + directionY) {
                        previous = current;
                        current = pointer;
                        previous.next = current;
                        navigation.add(current);
                        break;
                    }
                }
            }
            if (Character.isDigit(current.content)) {
                if (directionX == 1) {
                    for (Pointer pointer : pointers) {
                        if (pointer.x == current.x && pointer.y == current.y + 1) {
                            previous = current;
                            current = pointer;
                            previous.next = current;
                            navigation.add(current);
                            break;
                        }
                    }
                }
                if (directionX == -1) {
                    for (Pointer pointer : pointers) {
                        if (pointer.x == current.x && pointer.y == current.y - 1) {
                            previous = current;
                            current = pointer;
                            previous.next = current;
                            navigation.add(current);
                            break;
                        }
                    }
                }
                if (directionY == 1) {
                    for (Pointer pointer : pointers) {
                        if (pointer.x == current.x + 1 && pointer.y == current.y) {
                            previous = current;
                            current = pointer;
                            previous.next = current;
                            navigation.add(current);
                            break;
                        }
                    }
                }
                if (directionY == -1) {
                    for (Pointer pointer : pointers) {
                        if (pointer.x == current.x - 1 && pointer.y == current.y) {
                            previous = current;
                            current = pointer;
                            previous.next = current;
                            navigation.add(current);
                            break;
                        }
                    }
                }
            }
        }
    }

public static Pointer findStart(List<Pointer> pointers) {
    for (Pointer pointer : pointers) {
        if (pointer.y == 0 && pointer.content == '-') return pointer;
    }
    return null;
}
}
