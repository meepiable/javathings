package com.CASGame.InsomniacCheese;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

public class BufferedImageLoader {

    public static BufferedImage resize(BufferedImage inputImage, int scaledWidth, int scaledHeight) throws IOException {
        BufferedImage image = new BufferedImage(scaledWidth, scaledHeight, inputImage.getType());
        Graphics2D g2d = image.createGraphics();
        g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
        g2d.dispose();
        return image;
    }
    public static BufferedImage resize2(BufferedImage inputImage, String outputImagePath, double percent) throws IOException {
        BufferedImage input = inputImage;
        int scaledWidth = (int) (input.getWidth() * percent);
        int scaledHeight = (int) (input.getHeight() * percent);
        return resize(inputImage, scaledWidth, scaledHeight);
    }

    public static BufferedImage resize(String inputImagePath, String outputImagePath, int scaledWidth, int scaledHeight) throws IOException {
        // reads input image
        File inputFile = new File(inputImagePath);
        BufferedImage inputImage = ImageIO.read(inputFile);

        // creates output image
        BufferedImage outputImage = new BufferedImage(scaledWidth,
                scaledHeight, inputImage.getType());

        // scales the input image to the output image
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
        g2d.dispose();

        // extracts extension of output file
        String formatName = outputImagePath.substring(outputImagePath
                .lastIndexOf(".") + 1);

        // or i could just uh
        //ImageIO.write(outputImage, formatName, new File(outputImagePath));
        return outputImage;
    }

    public static BufferedImage resize(String inputImagePath, String outputImagePath, double percent) throws IOException {
        File inputFile = new File(inputImagePath);
        BufferedImage inputImage = ImageIO.read(inputFile);
        int scaledWidth = (int) (inputImage.getWidth() * percent);
        int scaledHeight = (int) (inputImage.getHeight() * percent);
        return resize(inputImagePath, outputImagePath, scaledWidth, scaledHeight);
    }


    
    public BufferedImage loadImage(String path) throws IOException {

        //final InputStream in = Files.newInputStream(Paths.get("src/com/CASGame/InsomniacCheese/resources/" + path));

        String in = "resources/" + path;
        //System.out.println(in);

        //return ImageIO.read(in);
        return ImageIO.read(BufferedImageLoader.class.getResourceAsStream(in));
    }
}
