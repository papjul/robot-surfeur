package org.miage.robotsurfeur.decision;

import org.miage.robotsurfeur.browsing.Main;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.StringTokenizer;

import org.miage.robotsurfeur.extraction.Link;
import org.miage.robotsurfeur.extraction.PageExtract;
import org.miage.robotsurfeur.toolbox.Tools;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.Math;

public class Decision {

    public static WebDriver driver;
    private static String randomURL;
    private static String currentUrl;

    public static void setUp() throws Exception {
        driver = new FirefoxDriver();
        randomURL = Main.getHomeURL();

        if (PageExtract.correctLink(randomURL)) {
            currentUrl = randomURL;
            driver.get(currentUrl);
        } else {
            System.err.println("Bad URL");
            driver.close();
        }
    }

    public static void randomBrowse() throws Exception {
        //Open Home Page
        driver.get(randomURL);

        //Extract links from a given URL
        LinkedList<Link> curPageLinks = PageExtract.getLinks(driver);
        int countURL = curPageLinks.size();

        for (int i = 0; i < countURL; i++) {
            computeLinkScore(curPageLinks.get(i));
        }
        //Count number of links
//        System.err.println("Taille : " + countURL);

        //Generate a random number between 0 and the number of links
        int rand = (int) (Math.random() * countURL);
  //      System.err.println("Rand : " + rand);

        //Get the URL from the chosen one Link
//        Link test = curPageLinks.get(rand);
//        String link = test.getHref();
        //    System.err.println("URL : " + link);
//        System.err.println("Rand : " + rand);
        //       test = curPageLinks.get(rand);
        //      System.err.println("test : " + test);
        //       link = test.getHref();
        //      System.err.println("URL : " + link);
        randomURL = curPageLinks.get(rand).getHref();
        System.err.println("RandomURL : " + randomURL);
    }

    public static void computeLinkScore(Link link) {
        StringTokenizer st = new StringTokenizer(link.getContent(), "/-=;&%.: ");

        // Init of score
        link.resetScore();
        while (st.hasMoreTokens()) {
            String token = st.nextToken().toLowerCase();
            if (Main.keywords.contains(token)) {
                System.out.println("Keyword " + token + " found!");
                link.increaseScore(2);
            }
            if (Main.synonyms.contains(token)) {
                System.out.println("Synonym " + token + " found");
                link.increaseScore(1);
            }
        }

        System.out.println("Score of " + link.getHref() + " = " + link.getScore());
    }

    public void tearDown() throws Exception {
        driver.quit();
    }

    // Filter keyword parameters with empty words
    public static void filterKeywords(LinkedList<String> keywords) {
        String fichier = "src/org/miage/robotsurfeur/decision/EmptyWords.txt";

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
