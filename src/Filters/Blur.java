package Filters;

import Interfaces.PixelFilter;
import core.DImage;

public class Blur implements PixelFilter {
    private double[][] blurKernel =
            {       {1.0/9, 1.0/9, 1.0/9},
                    {1.0/9, 1.0/9, 1.0/9},
                    {1.0/9, 1.0/9, 1.0/9}   };

    private void applyKernel(int n, short[][] pixels, short[][] outputPixels, double[][] kernel) {
        for (int r = 0; r < pixels.length - (n-1); r++) {
            for (int c = 0; c < pixels[0].length - (n-1); c++) {
                outputPixels[r+1][c+1] = average(pixels, kernel, r, c, n);
            }
        }
    }


    private short average(short[][] img, double[][] kernel, int r, int c, int n){
        double kernelSum = 0;
        double total = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                kernelSum += kernel[i][j] * img[i+r][j+c];
                total += kernel[i][j];
            }
        }

        if (kernelSum < 0) kernelSum = 0;

        if (kernelSum > 255) kernelSum = 255;

        if (total != 0) kernelSum = kernelSum/total;

        return (short)(kernelSum);
    }

    @Override
    public DImage processImage(DImage img) {

        short[][] pixels = img.getBWPixelGrid();
        short[][] outputPixels = img.getBWPixelGrid();  // <-- overwrite these values
        applyKernel(blurKernel.length, pixels, outputPixels, blurKernel);
        for (int r = 0; r < outputPixels.length; r++) {
            for (int c = 0; c < outputPixels[0].length; c++) {
                if(outputPixels[r][c] < 240) {
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
