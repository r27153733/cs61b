package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;

    public static final int FRAME = 3;

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     * <p>
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     * <p>
     * In other words, both of these calls:
     * - interactWithInputString("n123sss:q")
     * - interactWithInputString("lww")
     * <p>
     * should yield the exact same world state as:
     * - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        // TODO: Fill out this method so that it run the engine using the input
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.

        TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
        initWorld(finalWorldFrame);
        //String seed = input.substring(1, input.length() - 1);
        String[] strings = new String[0];
        try {
            strings = handleString(input);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        String seed = strings[0];
        String offsetString = strings[1];

        Random seedToRandom = new Random(Long.parseLong(seed));
        createASkeleton(finalWorldFrame, seedToRandom, offsetString);
        //ter.initialize(WIDTH, HEIGHT);
        //ter.renderFrame(finalWorldFrame);
        return finalWorldFrame;
    }


    private static String[] handleString(String input) throws IOException, ClassNotFoundException {
        if (input.startsWith("l", 0) || input.startsWith("L", 0)) {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("date.save"));
            String save = (String) objectInputStream.readObject();
            objectInputStream.close();
            input = save + input.substring(1, input.length());
        }
        if (input.startsWith(":q", input.length() - 2) || input.startsWith(":Q", input.length() - 2)) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("dataSave.txt"));
            objectOutputStream.writeObject(input);
            objectOutputStream.close();
        }
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == 'N' || input.charAt(i) == 'n') {
                ++i;
                int j = i;
                int k = i;
                while (input.charAt(i) != 'S' && input.charAt(i) != 's') {
                    j = i;
                    ++i;
                }
                ++i;
                String seed = input.substring(k, j + 1);
                String offsetString = input.substring(i, input.length());
                return new String[]{seed, offsetString};
            }
        }
        throw new RuntimeException("输入异常");
    }

    private static void initWorld(TETile[][] tiles) {

        int height = tiles[0].length;
        int width = tiles.length;
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }

    }

    private static class Point {
        private int x;
        private int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Point() {
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        @Override
        public String toString() {
            return "Point{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        public static void randomMoveXR(Point point, Random seedToRandom) {
            int sw = seedToRandom.nextInt(4);

            switch (sw) {
                case 0:
                    point.setY(point.getY() + 1);
                    break;
                case 1:
                    point.setY(point.getY() - 1);
                    break;
                default:
                    point.setX(point.getX() + 1);
                    break;
            }
        }

        public static boolean endMoveXR(Point point) {
            if (point.getY() >= HEIGHT - FRAME) {
                point.setY(point.getY() - 2);
            } else if (point.getY() < FRAME) {
                point.setY(point.getY() + 2);
            }

            return point.getX() >= WIDTH - FRAME;
        }

        public static void randomMoveXL(Point point, Random seedToRandom) {
            int sw = seedToRandom.nextInt(4);

            switch (sw) {
                case 0:
                    point.setY(point.getY() + 1);
                    break;
                case 1:
                    point.setY(point.getY() - 1);
                    break;
                default:
                    point.setX(point.getX() - 1);
                    break;
            }
        }

        public static void randomMoveYU(Point point, Random seedToRandom) {
            int sw = seedToRandom.nextInt(4);

            switch (sw) {
                case 0:
                    point.setX(point.getX() + 1);
                    break;
                case 1:
                    point.setX(point.getX() - 1);
                    break;
                default:
                    point.setY(point.getY() + 1);
                    break;
            }
        }

        public static boolean endMoveYU(Point point) {
            if (point.getX() >= WIDTH - FRAME) {
                point.setX(point.getX() - 2);
            } else if (point.getX() < FRAME) {
                point.setX(point.getX() + 2);
            }

            return point.getY() >= HEIGHT - FRAME;
        }

        public static void randomMoveYD(Point point, Random seedToRandom) {
            int sw = seedToRandom.nextInt(4);

            switch (sw) {
                case 0:
                    point.setX(point.getX() + 1);
                    break;
                case 1:
                    point.setX(point.getX() - 1);
                    break;
                default:
                    point.setY(point.getY() - 1);
                    break;
            }
        }

        public static boolean endMoveYD(Point point) {

            if (point.getX() >= WIDTH - FRAME) {
                point.setX(point.getX() - 2);
            } else if (point.getX() < FRAME) {
                point.setX(point.getX() + 2);
            }

            return point.getY() <= FRAME;
        }

    }

    private interface RandomPoint {
        void randomMove(Point point, Random seedToRandom);

        boolean endMove(Point point);
    }

    private static void addSkeletonPoint(ArrayList<Point> points, int x, int y) {
        points.add(new Point(x, y));
    }


    private static void moveToTheRight(TETile[][] finalWorldFrame, Random seedToRandom, Point point, ArrayList<Point> points, int size, RandomPoint randomPoint) {

        if (size == 0) {
            return;
        }
        --size;
        randomPoint.randomMove(point, seedToRandom);


        if (randomPoint.endMove(point)) {
            return;
        }

        finalWorldFrame[point.getX()][point.getY()] = Tileset.FLOOR;

        addSkeletonPoint(points, point.getX(), point.getY());
        moveToTheRight(finalWorldFrame, seedToRandom, point, points, size, randomPoint);
    }

    private static void moveToTheRight(TETile[][] finalWorldFrame, Random seedToRandom, Point point, ArrayList<Point> points, RandomPoint randomPoint) {

        moveToTheRight(finalWorldFrame, seedToRandom, point, points, Integer.MAX_VALUE, randomPoint);
    }


    private static ArrayList<Point> generateCorridor(TETile[][] finalWorldFrame, Point point) {

        ArrayList<Point> wall = new ArrayList<>();
        if (finalWorldFrame[point.getX() - 1][point.getY()].equals(Tileset.NOTHING)) {
            wall.add(new Point(point.getX() - 1, point.getY()));
        }
        if (finalWorldFrame[point.getX() + 1][point.getY()].equals(Tileset.NOTHING)) {
            wall.add(new Point(point.getX() + 1, point.getY()));
        }
        if (finalWorldFrame[point.getX()][point.getY() - 1].equals(Tileset.NOTHING)) {
            wall.add(new Point(point.getX(), point.getY() - 1));
        }
        if (finalWorldFrame[point.getX()][point.getY() + 1].equals(Tileset.NOTHING)) {
            wall.add(new Point(point.getX(), point.getY() + 1));
        }

        if (finalWorldFrame[point.getX() + 1][point.getY() + 1].equals(Tileset.NOTHING)) {
            wall.add(new Point(point.getX() + 1, point.getY() + 1));
        }
        if (finalWorldFrame[point.getX() - 1][point.getY() + 1].equals(Tileset.NOTHING)) {
            wall.add(new Point(point.getX() - 1, point.getY() + 1));
        }
        if (finalWorldFrame[point.getX() + 1][point.getY() - 1].equals(Tileset.NOTHING)) {
            wall.add(new Point(point.getX() + 1, point.getY() - 1));
        }
        if (finalWorldFrame[point.getX() - 1][point.getY() - 1].equals(Tileset.NOTHING)) {
            wall.add(new Point(point.getX() - 1, point.getY() - 1));
        }
        if (wall.size() >= 1) {
            for (Point p : wall) {
                finalWorldFrame[p.getX()][p.getY()] = Tileset.WALL;
            }
            return wall;
        }


        return null;
    }

    private static HashSet<Point> generateCorridor(TETile[][] finalWorldFrame, ArrayList<Point> points) {
        HashSet<Point> gap = new HashSet<>();
        ArrayList<Point> temp;
        for (Point p : points) {
            temp = generateCorridor(finalWorldFrame, p);
            if (temp != null) {
                gap.addAll(temp);
            }
        }
        return gap;
    }

    private static void spanningTree(TETile[][] finalWorldFrame, Random seedToRandom, Point point, ArrayList<Point> points, RandomPoint randomPoint) {
        moveToTheRight(finalWorldFrame, seedToRandom, point, points, (WIDTH + HEIGHT) / 2, randomPoint);
    }

    private static void randomlyGenerateBranches(TETile[][] finalWorldFrame, Random seedToRandom, ArrayList<Point> points) {
        Point root;

        int numberOfForks = seedToRandom.nextInt((HEIGHT + WIDTH) / 8) + ((HEIGHT + WIDTH) / 4);
        for (int i = 0; i < numberOfForks; i++) {
            root = points.get(seedToRandom.nextInt(points.size()));

            root = new Point(root.getX(), root.getY());
            spanningTree(finalWorldFrame, seedToRandom, root, points, new RandomPoint() {
                int randomNum = seedToRandom.nextInt(2);

                @Override
                public void randomMove(Point point, Random seedToRandom) {

                    if (randomNum == 0) {
                        Point.randomMoveYD(point, seedToRandom);
                    } else {
                        Point.randomMoveYU(point, seedToRandom);
                    }


                }

                @Override
                public boolean endMove(Point point) {

                    if (randomNum == 0) {
                        return Point.endMoveYD(point);
                    }
                    return Point.endMoveYU(point);
                }
            });
        }
    }

    private static void createASkeleton(TETile[][] finalWorldFrame, Random seedToRandom, String offset) {
        int x = FRAME;
        int y = seedToRandom.nextInt(HEIGHT - 2 * FRAME) + FRAME;
        finalWorldFrame[x][y] = Tileset.FLOOR;
        Point point = new Point(x, y);
        ArrayList<Point> points = new ArrayList<>();
        points.add(new Point(x, y));
        moveToTheRight(finalWorldFrame, seedToRandom, point, points, new RandomPoint() {
            @Override
            public void randomMove(Point point, Random seedToRandom) {
                Point.randomMoveXR(point, seedToRandom);
            }

            @Override
            public boolean endMove(Point point) {
                return Point.endMoveXR(point);
            }
        });
        randomlyGenerateBranches(finalWorldFrame, seedToRandom, points);
        for (int i = 0; i < 10; i++) {
            generateRoom(finalWorldFrame, seedToRandom, points);
        }

        HashSet<Point> gap = generateCorridor(finalWorldFrame, points);
        //finalWorldFrame[48][9] = Tileset.FLOWER;
        door(finalWorldFrame, gap, offset);
    }

    private static void door(TETile[][] finalWorldFrame, HashSet<Point> gap, String offset) {
        AtomicBoolean a = new AtomicBoolean(false);
        for (Point p : gap) {
            if (finalWorldFrame[p.getX()][p.getY()].equals(Tileset.WALL)) {
                int cont = 0;
                int hiTo = 0;

                if (!isItOutOfBounds(p.getX(), p.getY() - 1, 0) && !isItOutOfBounds(p.getX(), p.getY() + 1, 0)) {
                    if (finalWorldFrame[p.getX()][p.getY() - 1].equals(Tileset.NOTHING)) {
                        cont -= 2;
                    } else if (finalWorldFrame[p.getX()][p.getY() - 1].equals(Tileset.FLOOR)) {
                        cont += 1;
                        hiTo = -1;
                    }
                    if (finalWorldFrame[p.getX()][p.getY() + 1].equals(Tileset.NOTHING)) {
                        cont -= 2;
                    } else if (finalWorldFrame[p.getX()][p.getY() + 1].equals(Tileset.FLOOR)) {
                        cont += 1;
                        hiTo = 1;
                    }
                    if (cont == -1) {
                        finalWorldFrame[p.getX()][p.getY()] = Tileset.LOCKED_DOOR;
                        hiToMove(p.getX(), p.getY(), offset, finalWorldFrame, hiTo);
                        return;
                    }
                }
            }
        }
    }

    private static void hiToMove(int x, int y, String offset, TETile[][] finalWorldFrame, int a) {
        if (finalWorldFrame[x][y + 1].equals(Tileset.FLOOR)) {
            ++y;
        } else {
            --y;
        }
        for (int i = 0; i < offset.length(); i++) {
            if (offset.charAt(i) == 'S' || offset.charAt(i) == 's') {

                if (!isItOutOfBounds(x, y - 1, 0) && !finalWorldFrame[x][y - 1].equals(Tileset.WALL)) {
                    --y;
                }
            } else if (offset.charAt(i) == 'W' || offset.charAt(i) == 'w') {
                if (!isItOutOfBounds(x, y + 1, 0) && !finalWorldFrame[x][y + 1].equals(Tileset.WALL)) {
                    ++y;
                }
            } else if (offset.charAt(i) == 'A' || offset.charAt(i) == 'a') {
                if (!isItOutOfBounds(x - 1, y, 0) && !finalWorldFrame[x - 1][y].equals(Tileset.WALL)) {
                    --x;
                }
            } else if (!isItOutOfBounds(x + 1, y, 0) && offset.charAt(i) == 'D' || offset.charAt(i) == 'd') {
                if (!finalWorldFrame[x + 1][y].equals(Tileset.WALL)) {
                    ++x;
                }
            }
        }
        finalWorldFrame[x][y] = Tileset.AVATAR;
    }

    private static void generateRoom(TETile[][] finalWorldFrame, Random seedToRandom, ArrayList<Point> points) {
        Point centerPoint = points.get(seedToRandom.nextInt(points.size()));
        int x = centerPoint.getX();
        int y = centerPoint.getY();
        int width = seedToRandom.nextInt(5) + 1;
        int height = seedToRandom.nextInt(5) + 1;
        if (!isItOutOfBounds(x - width, y) && !isItOutOfBounds(x + width, y) && !isItOutOfBounds(x, y - height) && !isItOutOfBounds(x, y + height)) {
            for (int j = y - height; j <= y + height; j++) {
                finalWorldFrame[x][j] = Tileset.FLOOR;
                points.add(new Point(x, j));
                for (int i = 1; i <= width; i++) {
                    finalWorldFrame[x - i][j] = Tileset.FLOOR;
                    finalWorldFrame[x + i][j] = Tileset.FLOOR;
                    points.add(new Point(x - i, j));
                    points.add(new Point(x + i, j));
                }

            }
        }
    }

    private static boolean isItOutOfBounds(int x, int y) {
        return x < FRAME || x >= WIDTH - FRAME || y < FRAME || y >= HEIGHT - FRAME;
    }

    private static boolean isItOutOfBounds(int x, int y, int a) {
        return x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT;
    }

}
