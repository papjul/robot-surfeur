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

    private static WebDriver driver;
    private static String randomURL;
    private static String keywords[];
    private static String currentUrl;
	static int [] count = new int[2];


    

    public static void setUp() throws Exception {
    	
    	driver = new FirefoxDriver();
        randomURL = Main.getHomeURL();
        
        if(PageExtract.correctLink(randomURL)){
			currentUrl = randomURL;
			driver.get(currentUrl);
        }
        else{
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
        
        for (int i = 0 ; i < countURL ; i++){
        	wordInTheLink(curPageLinks.get(i).getHref(), countURL, curPageLinks.get(i));
        }
        //Count number of links
        System.err.println("Taille : " + countURL);

        //Generate a random number between 0 and the number of links
        int rand = (int) (Math.random() * countURL);
        System.err.println("Rand : " + rand);

        //Get the URL from the chosen one Link
        Link test = curPageLinks.get(rand);
        String link = test.getHref();
        System.err.println("URL : " + link);

        rand = (int) (Math.random() * countURL);
        System.err.println("Rand : " + rand);
        
        
        test = curPageLinks.get(rand);
        System.err.println("test : " + test);
        link = test.getHref();
        System.err.println("URL : " + link);
        
        

        randomURL = link;
        System.err.println("RandomURL : " + randomURL);

    }
    
    public static void wordInTheLink(String string, int counter, Link choice){
    	
    	StringTokenizer st = new StringTokenizer(string,"/-=;&%.:");
    	String str = st.nextToken();
    	String[] tab = new String[st.countTokens()];
    	int i = 0;
    	while (st.hasMoreTokens()) {
	         tab[i]=st.nextToken();
	         i++;
	     }
    	for (int j=0; j<tab.length; j++){
    		System.out.println(tab[j]+" ");
    	}
    	
    	for(int j = 0 ; j < tab.length ; j++){
    		for(int k = 0 ; k < Main.keywords.size() ; k++){
    			if(tab[j].contains(Main.keywords.get(k))){
    				 
    			}
    		}
    		
    	}
    	for (int l = 0 ; l < counter ; l++){
    		System.out.println("link:"+l+ "keywords = "+count[0]+" Synonyms = "+count[1]);
    	}
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
            while((ligne = br.readLine()) != null) {
                Iterator<String> itr = keywords.iterator();
                while(itr.hasNext()) {
                    if(itr.next().equals(ligne)) {
                        itr.remove();
                    }
                }
            }
            Iterator<String> itr1 = keywords.iterator();
            while(itr1.hasNext()) {
                System.out.println(itr1.next());
            }
            br.close();
        } catch(Exception e) {
            System.out.println(e.toString());
        }
    }
}