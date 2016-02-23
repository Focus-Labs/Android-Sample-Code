package com.focuslabs.mekuanent.backgroundjobs.parser;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by mekuanent on 2/19/16.
 */
public class RSSParser {

    public static String parse(InputStream stream){
        try{

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse(stream);

            NodeList list = document.getElementsByTagName("description");

            StringBuilder stringBuilder = new StringBuilder();

            for (int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);
//                System.out.println(node.getTextContent());
                stringBuilder.append(node.getTextContent() + "\n");
            }
            return stringBuilder.toString();
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

}
