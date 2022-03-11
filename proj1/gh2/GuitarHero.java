package gh2;

import edu.princeton.cs.algs4.StdAudio;
import edu.princeton.cs.algs4.StdDraw;

public class GuitarHero {
    public static void main(String[] args) {
        /* create two guitar strings, for concert A and C */
        String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
        GuitarString[] keys = new GuitarString[keyboard.length()];
        for (int i = 0; i < keyboard.length(); i++) {
            keys[i] = new GuitarString(440 * Math.pow(2, ((double) i - 24) / 12));
        }
        while (true) {

            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                int index = keyboard.indexOf(key);
                if (index >= 0) {
                    keys[index].pluck();
                }

            }


            /* compute the superposition of samples */
            double sample = 0;
            for (int i = 0; i < keys.length; i++) {
                sample += keys[i].sample();
            }
            /* play the sample on standard audio */
            StdAudio.play(sample);

            /* advance the simulation of each guitar string by one step */
            for (int i = 0; i < keys.length; i++) {
                keys[i].tic();
            }
        }
    }

}
