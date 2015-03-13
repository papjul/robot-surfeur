package org.miage.robotsurfeur.decision;

import org.miage.robotsurfeur.browsing.Main;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.StringTokenizer;

import org.miage.robotsurfeur.extraction.Link;
import org.miage.robotsurfeur.extraction.PageExtract;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import org.miage.robotsurfeur.toolbox.Configuration;

public class Decision {

    public static WebDriver driver;
    private static Link currentURL;
    private static LinkedList<String> linkHistory;

    public static void setUp() throws Exception {
        driver = new FirefoxDriver();

        if (PageExtract.correctLink(Main.getHomeURL())) {
            linkHistory = new LinkedList<>();
            linkHistory.add(Main.getHomeURL());
            driver.get(Main.getHomeURL());
        } else {
            System.err.println("Bad home URL argument");
            driver.close();
            System.exit(0);
        }
    }

    public static String getPreviousLink() {
        return linkHistory.get(linkHistory.size() - 1);
    }

    public static void browse() throws Exception {
        // Extract links from a given URL
        LinkedList<Link> curPageLinks = PageExtract.getLinks(driver);

        // If no links found, we go backwards
        if (curPageLinks.size() == 0) {
            driver.get(getPreviousLink());
        } else {
            for (Link curPageLink : curPageLinks) {
                computeLinkScore(curPageLink);
            }

            int nb = 0;
            switch (Configuration.getString("DECISION_MODE")) {
                case "random":
                    // Generate a random number between 0 and the number of links
                    nb = (int) (Math.random() * curPageLinks.size());

                    // We enter this loop only if we already went to the page randomly selected
                    while (linkHistory.contains(curPageLinks.get(nb).getHref())) {
                        nb = (int) (Math.random() * curPageLinks.size());
                    }
                    break;
                case "intelligent":
                    Collections.sort(curPageLinks, Collections.reverseOrder());
                    // Highest score in the list
                    nb = 0;
                    // We enter this loop only if we already went to the page randomly selected
                    if (curPageLinks.size() > 1) { // Check if we have more than one link
                        while (linkHistory.contains(curPageLinks.get(nb).getHref())) {
                            ++nb; // We select the second highest, third, etc
                            // If we find no suitable links at the end of the list, let’s go with the first one
                            if (nb == curPageLinks.size()) {
                                nb = 0;
                                break;
                            }
                        }
                    }
                    break;
                default:
                    System.err.println("Bad value for DECISION_MODE config in config.properties");
                    System.exit(0);
            }
            currentURL = curPageLinks.get(nb);
            System.out.println(currentURL + " " + currentURL.getScore());

            driver.get(currentURL.getHref());
        }
    }

    public static void computeLinkScore(Link link) {
        StringTokenizer st = new StringTokenizer(link.getContent() + " " + link.getHref(), "/-=;?&%.+: ");

        // Init of score
        link.resetScore();
        while (st.hasMoreTokens()) {
            String token = st.nextToken().toLowerCase();
            if (Main.keywords.contains(token)) {
                if (Configuration.getBoolean("DEBUG")) {
                    System.out.println("Keyword " + token + " found in " + link.getHref() + "!");
                }
                link.increaseScore(Configuration.getInt("SCORE_KEYWORD"));
            }
            if (Main.synonyms.contains(token)) {
                if (Configuration.getBoolean("DEBUG")) {
                    System.out.println("Synonym " + token + " found in " + link.getHref() + "!");
                }
                link.increaseScore(Configuration.getInt("SCORE_SYNONYM"));
            }
        }

        if (Configuration.getBoolean("DEBUG")) {
            System.out.println("Score of " + link.getHref() + " = " + link.getScore());
        }
    }

    public void tearDown() throws Exception {
        driver.quit();
    }

    // Filter keyword parameters with empty words
    public static void filterKeywords(LinkedList<String> keywords) {
        String fichier = "EmptyWords.txt";

        //Reading the text file
        try {
            InputStream ips = new FileInputStream(fichier);
            InputStreamReader ipsr = new InputStreamReader(ips);
            BufferedReader br = new BufferedReader(ipsr);
            String ligne;
            //Test "stopword" exist an array keyword(parameter)
            while ((ligne = br.readLine()) != null) {
                Iterator<String> itr = keywords.iterator();
                while (itr.hasNext()) {
                    if (itr.next().equals(ligne)) {
                        itr.remove();
                    }
                }
            }
            Iterator<String> itr1 = keywords.iterator();
            while (itr1.hasNext()) {
                System.out.println(itr1.next());
            }
            br.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
