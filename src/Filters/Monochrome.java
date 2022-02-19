package Filters;

import Interfaces.PixelFilter;
import core.DImage;

public class Monochrome implements PixelFilter {

    @Override
    public DImage processImage(DImage img) {

        short[][] pixels = img.getBWPixelGrid();
        short[][] outputPixels = img.getBWPixelGrid();  // <-- overwrite these values
        for (int r = 0; r < outputPixels.length; r++) {
            for (int c = 0; c < outputPixels[0].length; c++) {
                if(outputPixels[r][c] < 250) {
                    outputPixels[r][c] = 0;
                } else {
                    outputPixels[r][c] = 255;
                }
            }
        }
        img.setPixels(outputPixels);
        return img;
    }
}