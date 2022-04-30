package byow.lab12;

import org.junit.Test;

import static org.junit.Assert.*;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;
    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        // initialize tiles
        TETile[][] world = new TETile[WIDTH][HEIGHT];

        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        int size = 3;
        addHexagon(world, WIDTH / 2 - size - 1, 0, size);
        addHexagon(world, WIDTH / 2 - size - 1, size * 2, size);
        addHexagon(world, WIDTH / 2 - size - 1, size * 4, size);
        addHexagon(world, WIDTH / 2 - size - 1, size * 6, size);
        addHexagon(world, WIDTH / 2 - size - 1, size * 8, size);
        addHexagon(world, WIDTH / 2 - size - 1 - 5, size, size);
        addHexagon(world, WIDTH / 2 - size - 1 - 5, size * 3, size);
        addHexagon(world, WIDTH / 2 - size - 1 - 5, size * 5, size);
        addHexagon(world, WIDTH / 2 - size - 1 - 5, size * 7, size);
        addHexagon(world, WIDTH / 2 - size - 1 - 10, size * 2, size);
        addHexagon(world, WIDTH / 2 - size - 1 - 10, size * 4, size);
        addHexagon(world, WIDTH / 2 - size - 1 - 10, size * 6, size);
        addHexagon(world, WIDTH / 2 - size - 1 + 5, size, size);
        addHexagon(world, WIDTH / 2 - size - 1 + 5, size * 3, size);
        addHexagon(world, WIDTH / 2 - size - 1 + 5, size * 5, size);
        addHexagon(world, WIDTH / 2 - size - 1 + 5, size * 7, size);
        addHexagon(world, WIDTH / 2 - size - 1 + 10, size * 2, size);
        addHexagon(world, WIDTH / 2 - size - 1 + 10, size * 4, size);
        addHexagon(world, WIDTH / 2 - size - 1 + 10, size * 6, size);
        // draws the world to the screen
        ter.renderFrame(world);
    }

    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(10);
        switch (tileNum) {
            case 0:
                return Tileset.WALL;
            case 1:
                return Tileset.FLOWER;
            case 2:
                return Tileset.AVATAR;
            case 3:
                return Tileset.FLOOR;
            case 4:
                return Tileset.TREE;
            case 5:
                return Tileset.SAND;
            case 6:
                return Tileset.GRASS;
            case 7:
                return Tileset.UNLOCKED_DOOR;
            case 8:
                return Tileset.WATER;
            default:
                return Tileset.MOUNTAIN;
        }
    }

    private static void offsetSetTETile(TETile[][] world, int defaultX, int defaultY, int offsetX, int offsetY, TETile color) {
        world[defaultX + offsetX][defaultY + offsetY] = color;
    }

    public static void addHexagon(TETile[][] world, int defaultX, int defaultY, int size) {
        TETile color = randomTile();
        color = TETile.colorVariant(color, Integer.MAX_VALUE / 100, Integer.MAX_VALUE / 100, Integer.MAX_VALUE / 100, new Random());
        int x = 0, y = size, x1 = 0, y1 = size - 1;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < i; j++) {
                ++x;
                ++x1;
            }
            for (int j = 0; j < size * 3 - 2 - i * 2; j++) {
                offsetSetTETile(world, defaultX, defaultY, x, y, color);
                ++x;

                offsetSetTETile(world, defaultX, defaultY, x1, y1, color);
                ++x1;
            }
            for (int j = 0; j < i; j++) {
                ++x;
                ++x1;
            }
            ++y;
            x = 0;

            --y1;
            x1 = 0;


        }


    }
}
