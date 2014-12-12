package org.miage.robotsurfeur.extraction;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
import javax.swing.text.EditorKit;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

/**
 * Extraire les liens d'un document HTML
 */
public class LinkExtract {

    public static void main(String[] args) {
        try {
            //Charger la page
            URL url = new URL("http://www.google.fr/");
            URLConnection uconnection = url.openConnection();
            Reader rd = new InputStreamReader(uconnection.getInputStream());
            //lire le document HTML
            EditorKit kit = new HTMLEditorKit();
            HTMLDocument doc = (HTMLDocument) kit.createDefaultDocument();
            doc.putProperty("IgnoreCharsetDirective", new Boolean(true));
            kit.read(rd, doc, 0);
            //Parcourir la balise lien
            HTMLDocument.Iterator it = doc.getIterator(HTML.Tag.A);
            while (it.isValid()) {
                SimpleAttributeSet s = (SimpleAttributeSet) it.getAttributes();
                String link = (String) s.getAttribute(HTML.Attribute.HREF);
                if (link != null) {
                    //Afficher le lien trouvé
                    System.out.println(link);
                }
                it.next();
            }
        } catch (BadLocationException ex) {
            Logger.getLogger(LinkExtract.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LinkExtract.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}