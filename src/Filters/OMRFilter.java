package Filters;

import Interfaces.PixelFilter;
import core.DImage;

public class OMRFilter implements PixelFilter {
    private int firstColInitialY, firstColInitialX;
    private int secondColInitialY, secondColInitialX;
    private int thirdColInitialY, thirdColInitialX;
    private int fourthColInitialY, fourthColInitialX;

    private int firstColEndY, firstColEndX;
    private int secondColEndY, secondColEndX;
    private int thirdColEndY, thirdColEndX;
    private int fourthColEndY, fourthColEndX;

    private int distanceBetweenBubbles, distanceBetweenRows;
    private int bubbleWidth;

    private int studentIDStartY, studentIDStartX;
    private int studentIDEndY, studentIDEndX;
    private int distanceBetweenStudentIDRows, distanceBetweenStudentIDBubbles;
    private int studentIDBubbleWidth;

    private int minBlackToBeConsideredBubbled;


    @Override
    public DImage processImage(DImage img) {
        // we dont change the input image at all!
        return img;

    }

    public String[] getAnswersFrom(DImage img) {
        Monochrome monochrome = new Monochrome();
        DImage newImg = monochrome.processImage(img);
        short[][] pixelArray = newImg.getBWPixelGrid();

        String[] column1Answers = getAnswerChoicesPerColumn(firstColInitialY, firstColInitialX, firstColEndY, firstColEndX, distanceBetweenRows, distanceBetweenBubbles, bubbleWidth, minBlackToBeConsideredBubbled, pixelArray);
        String[] column2Answers = getAnswerChoicesPerColumn(secondColInitialY, secondColInitialX, secondColEndY, secondColEndX, distanceBetweenRows, distanceBetweenBubbles, bubbleWidth, minBlackToBeConsideredBubbled, pixelArray);
        String[] column3Answers = getAnswerChoicesPerColumn(thirdColInitialY, thirdColInitialX, thirdColEndY, thirdColEndX, distanceBetweenRows, distanceBetweenBubbles, bubbleWidth, minBlackToBeConsideredBubbled, pixelArray);
        String[] column4Answers = getAnswerChoicesPerColumn(fourthColInitialY, fourthColInitialX, fourthColEndY, fourthColEndX, distanceBetweenRows, distanceBetweenBubbles, bubbleWidth, minBlackToBeConsideredBubbled, pixelArray);

        String[] selectedAnswers = new String[100];
        int answerIndex = 0;
        for (int i = 0; i < 25; i++) {
            selectedAnswers[answerIndex] = column1Answers[i];
            answerIndex++;
        }

        for (int i = 0; i < 25; i++) {
            selectedAnswers[answerIndex] = column2Answers[i];
            answerIndex++;
        }

        for (int i = 0; i < 25; i++) {
            selectedAnswers[answerIndex] = column3Answers[i];
            answerIndex++;
        }

        for (int i = 0; i < 25; i++) {
            selectedAnswers[answerIndex] = column4Answers[i];
            answerIndex++;
        }

        return selectedAnswers;
    }

    public static int getBlackPixelCountOfBubble(int startingRow, int startingCol, short[][] pixelArray, double bubbleWidth) {
        int blackPixelCount = 0;
        for (int i = startingRow; i < startingRow + bubbleWidth; i++) {
            for (int j = startingCol; j < startingCol + bubbleWidth; j++) {
                if (pixelArray[i][j] < 250) {
                    blackPixelCount++;
                }
            }
        }

        return blackPixelCount;
    }

    public static String[] getAnswerChoicesPerColumn(int startingRow, int startingCol, int endingRow, int endingCol, int distanceBetweenRows, int distanceBetweenBubbles, int bubbleWidth, int minBlackToBeConsideredBubbled, short[][] pixels) {
        String[] indexToAnswerChoice = {"A", "B", "C", "D", "E"};
        String[] selectedAnswers = new String[25];
        int counter = 0;
        for (int r = startingRow; r < endingRow; r += distanceBetweenRows) {
            int max = 0;
            int maxIndex = 0;
            int numSelected = 0;
            String answerChoice = "";
            for (int c = startingCol; c < endingCol; c += distanceBetweenBubbles) {
                if (getBlackPixelCountOfBubble(r, c, pixels, bubbleWidth) > max) {
                    max = getBlackPixelCountOfBubble(r, c, pixels, bubbleWidth);
                    answerChoice = indexToAnswerChoice[maxIndex];
                }
                if (getBlackPixelCountOfBubble(r, c, pixels, bubbleWidth) > minBlackToBeConsideredBubbled) {
                    numSelected++;
                }
                maxIndex++;
            }

            if (max < minBlackToBeConsideredBubbled) {
                answerChoice = "Not Answered";
            }

            if (numSelected > 1) {
                answerChoice = "Not Answered";
            }

            selectedAnswers[counter] = answerChoice;
            counter++;
        }

        return selectedAnswers;
    }

    public String getStudentID(DImage img) {
        Monochrome monochrome = new Monochrome();
        DImage newImg = monochrome.processImage(img);
        short[][] pixelArray = newImg.getBWPixelGrid();
        
        String studentID = new String(" "); 
        for (int i = studentIDStartY; i < studentIDEndY; i += distanceBetweenStudentIDRows) {
            for (int j = studentIDStartX; j < studentIDEndX; j += distanceBetweenStudentIDBubbles) {
                int maxBlack = 0; 
                int maxIndex = 0;
                for (int k = 0; k < 10; k++) {
                    if (getBlackPixelCountOfBubble(i, j, pixelArray, studentIDBubbleWidth) > maxBlack) {
                        maxBlack = getBlackPixelCountOfBubble(i, j, pixelArray, studentIDBubbleWidth);
                        maxIndex = k;
                    }
                }
                studentID += String.valueOf(maxIndex);
            }
        }

        return studentID;
    }
}
