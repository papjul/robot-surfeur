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

import java.util.LinkedList;
import org.miage.robotsurfeur.extraction.Link;
import org.miage.robotsurfeur.extraction.PageExtract;
import org.miage.robotsurfeur.toolbox.Tools;

/**
 *
 * @author MIAGE
 */
public class Main {

    public static String homeURL;
    public static int time;
    public static String keyWords[];

    /**
     * args[0] = URL, args[1] = Time, args[2+] = KeyWords
     */
    public static void main(String[] args) {
        /**
         * FIRST: Check args
         */
        if(args.length < 3) {
            System.err.println("Usage: <robot-surfeur> <URL> <time> <keyWords>");
            System.err.println("Error: You must provide at least three arguments");
            System.exit(0);
        } else {
            if(args[0].startsWith("http://") || args[0].startsWith("https://")) {
                homeURL = args[0];
            } else {
                System.err.println("Usage: <robot-surfeur> <URL> <time> <keyWords>");
                System.err.println("Error: <URL> = http:// or https://");
            }

            if(Tools.isInteger(args[1])) {
                time = Integer.parseInt(args[1]);
                if(time < 2 || time > 60) {
                    System.err.println("Usage: <robot-surfeur> <URL> <time> <keyWords>");
                    System.err.println("Error: <time> between 2 and 60 seconds");
                }

            } else {
                System.err.println("Usage: <robot-surfeur> <URL> <time> <keyWords>");
                System.err.println("Error: <time> must be an integer");
            }
            String keyWords[] = new String[args.length - 2];

            for(int i = 2; i < args.length; ++i) {
                keyWords[i - 2] = args[i];
            }
        }
        /**
         * SECOND: Get all links
         */
        LinkedList<Link> curPageLinks = PageExtract.getLinks(homeURL);

        // TODO: Remove when done testing
        for(Link l : curPageLinks) {
            System.out.println(l.getHref());
        }
    }

    public String getHomeURL() {
        return homeURL;
    }

    public int getTime() {
        return time;
    }
}
