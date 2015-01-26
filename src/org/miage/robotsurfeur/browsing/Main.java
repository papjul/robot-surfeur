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
package org.miage.robotsurfeur.browsing;

import org.miage.robotsurfeur.decision.Decision;
import org.miage.robotsurfeur.decision.Synonym;

import java.util.Iterator;
import java.util.LinkedList;

import org.miage.robotsurfeur.extraction.Link;
import org.miage.robotsurfeur.extraction.PageExtract;
import org.miage.robotsurfeur.toolbox.Configuration;
import org.miage.robotsurfeur.toolbox.Tools;

/**
 * Main class. Launch our program.
 *
 * @author MIAGE
 */
public class Main {

    public static String homeURL;
    public static int time;
    public static LinkedList<String> keywords;
    public static LinkedList<String> synonyms;

    /**
     * Main function. Check args first and then launch the robot.
     *
     * @param args [0] = URL, [1] = time, [2+] = keywords
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        Configuration.setUp();
        /**
         * FIRST: Check args
         */
        if(args.length < 3) {
            throw new IllegalArgumentException("Usage: <robot-surfeur> <URL> <time> <keywords>\nError: You must provide at least three arguments");
        } else {
            if(args[0].startsWith("http://") || args[0].startsWith("https://")) {
                homeURL = args[0];
            } else {
                throw new IllegalArgumentException("Usage: <robot-surfeur> <URL> <time> <keywords>\nError: <URL> = http:// or https://");
            }

            if(Tools.isInteger(args[1])) {
                time = Integer.parseInt(args[1]);
                if(time < 2 || time > 60) {
                    throw new IllegalArgumentException("Usage: <robot-surfeur> <URL> <time> <keywords>\nError: <time> between 2 and 60 seconds");
                }

            } else {
                throw new IllegalArgumentException("Usage: <robot-surfeur> <URL> <time> <keywords>\nError: <time> must be an integer");
            }

            keywords = new LinkedList<String>();
            synonyms = new LinkedList<String>();

            for(int i = 2; i < args.length; ++i) 
            {
                keywords.add(args[i]);
            }	 
            // TODO: Must be done before getting similars!
            Decision.filterKeywords(keywords);
            
            Iterator<String> itr = keywords.iterator();
            
            while(itr.hasNext())
            {
                synonyms.add(Synonym.getMostSimilarFor(itr.next()));
            }

           
            Decision.setUp();
            for(int i = 0; i < 3; ++i) {
                Decision.randomBrowse();
            }

        }
    }

    /**
     * Getter for home URL.
     *
     * @return <tt>String</tt> URL
     */
    public static String getHomeURL() {
        return homeURL;
    }

    /**
     * Getter for time between pages.
     *
     * @return <tt>int</tt> time
     */
    public int getTime() {
        return time;
    }
}
