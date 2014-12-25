/*
 * Copyright (C) 2014 MIAGE
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package org.miage.robotsurfeur.decision;

import de.linguatools.disco.DISCO;
import de.linguatools.disco.ReturnDataBN;
import de.linguatools.disco.ReturnDataCol;
import java.io.IOException;

import static org.miage.robotsurfeur.toolbox.Constants.DEBUG;
import static org.miage.robotsurfeur.toolbox.Constants.DISCO_PATH;

/**
 *
 * @author MIAGE
 */
public class Synonym {

    public static String getMostSimilarFor(String word) throws IOException {
        DISCO disco = new DISCO(DISCO_PATH, false);

        // retrieve the frequency of the input word
        int freq = disco.frequency(word);
        if(DEBUG) {
            // and print it to stdout
            System.out.println("Frequency of " + word + " is " + freq);
        }

        // end if the word wasn't found in the index
        if(freq == 0) {
            return null;
        }

        // retrieve the most similar words for the input word
        ReturnDataBN simResult = disco.similarWords(word);
        if(DEBUG) {
            // and print the first one of them to stdout
            System.out.println("Most similar word: " + simResult.words[1]
                    + " (freq: " + simResult.values[1] + ")");
        }

        return simResult.words[1];
    }
}
