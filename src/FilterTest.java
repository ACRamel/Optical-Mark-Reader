import FileIO.PDFHelper;
import Filters.DisplayInfoFilter;
import Interfaces.PixelFilter;
import core.DImage;
import core.DisplayWindow;
import processing.core.PImage;

import java.util.ArrayList;

public class FilterTest {
    public static String currentFolder = System.getProperty("user.dir") + "/";

    public static void main(String[] args) {
        SaveAndDisplayExample();

        RunTheFilter();

        // ArrayList<PImage> pages = PDFHelper.getPImagesFromPdf("assets/omrtest.pdf");
        // System.out.println(pages.size() + " pages total");
    }

    private static void RunTheFilter() {
        System.out.println("Loading pdf....");
        PImage in = PDFHelper.getPageImage("assets/omrtest.pdf",1);
        DImage img = new DImage(in);       // you can make a DImage from a PImage

        System.out.println("Running filter on page 1....");
        DisplayInfoFilter filter = new DisplayInfoFilter();
        int bCount = filter.getBlackCount(img);
        int wCount = filter.getWhiteCount(img);// if you want, you can make a different method
        // that does the image processing an returns a DTO with
        // the information you want
        System.out.println("White Count: " + wCount);
        System.out.println("Black Count: " + bCount);
    }

    private static void SaveAndDisplayExample() {
        PImage img = PDFHelper.getPageImage("assets/omrtest.pdf",1);
        img.save(currentFolder + "assets/page1.png");

        DisplayWindow.showFor("assets/page1.png");
    }
}
