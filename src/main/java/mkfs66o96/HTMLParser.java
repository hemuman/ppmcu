/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mkfs66o96;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author PDI
 */
public class HTMLParser {

    /*public static void main(String[] args) throws Exception {
        HTMLEditorKit.ParserCallback callback = new CallBack();
        Reader reader = new FileReader("D:/PROJECTS/Tests/DynamicFlowChart/2.htm");
        ParserDelegator delegator = new ParserDelegator();
        delegator.parse(reader, callback, false);
    }*/

public static void main(String [] args) throws Exception {
      // Reader reader = new StringReader("<table><tr><td>Hello</td><td>World!</td></tr></table>");
       Reader reader = new FileReader("D:/PROJECTS/Tests/DynamicFlowChart/2.htm");
       
       HTMLEditorKit.Parser parser = new ParserDelegator();
       parser.parse(reader, new HTMLTagParser(), true);
       
       reader.close();
       
       
       
   }

 public static DocumentFragment parseXml(Document doc, String fragment)
 {
    // Wrap the fragment in an arbitrary element.
    fragment = "<fragment>"+fragment+"</fragment>";
    try
    {
        // Create a DOM builder and parse the fragment.
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Document d = factory.newDocumentBuilder().parse(
                new InputSource(new StringReader(fragment)));

        // Import the nodes of the new document into doc so that they
        // will be compatible with doc.
        Node node = doc.importNode(d.getDocumentElement(), true);

        // Create the document fragment node to hold the new nodes.
        DocumentFragment docfrag = doc.createDocumentFragment();

        // Move the nodes into the fragment.
        while (node.hasChildNodes())
        {
            docfrag.appendChild(node.removeChild(node.getFirstChild()));
        }
        // Return the fragment.
        return docfrag;
    }
    catch (SAXException e)
    {
        // A parsing error occurred; the XML input is not valid.
    }
    catch (ParserConfigurationException e)
    {
    }
    catch (IOException e)
    {
    }
    return null;
}

}

class HTMLTagParser extends HTMLEditorKit.ParserCallback {

    private boolean encounteredATableRow = false;

    public void handleText(char[] data, int pos) {
        if(encounteredATableRow) System.out.println(new String(data));
    }

    public void handleStartTag(HTML.Tag t, MutableAttributeSet a, int pos) {
        if(t == HTML.Tag.TEXTAREA) encounteredATableRow = true;
    }

    public void handleEndTag(HTML.Tag t, int pos) {
        if(t == HTML.Tag.TEXTAREA) encounteredATableRow = false;
    }

}