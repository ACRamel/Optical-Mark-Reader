import FileIO.PDFHelper;
import Filters.OMRFilter;
import Filters.PageResult;
import com.sun.corba.se.impl.orbutil.DenseIntMapImpl;
import core.DImage;
import processing.core.PImage;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

// Author: David Dobervich (this is my edit)
// ANOTHER EDIT.
public class OpticalMarkReaderMain {
    public static void main(String[] args) throws IOException {

        FileWriter answerKeyWriter = new FileWriter("answerKey.csv");

        PImage in = PDFHelper.getPageImage("assets/omrtest.pdf", 1);
        DImage img = new DImage(in);

        OMRFilter myFilter = new OMRFilter();
        String[] answerKey = myFilter.getAnswersFrom(img);

        for (int i = 0; i < answerKey.length; i++) {
            answerKeyWriter.append(answerKey[i] + "," + "\n");
        }
        answerKeyWriter.close();

        BufferedReader reader = new BufferedReader(new FileReader("answerKey.csv"));
        FileWriter numStudentsWhoGotQuestionsWrong = new FileWriter("analysis.csv");
        FileWriter studentScores = new FileWriter("scores.csv");

        PImage student1PImage = PDFHelper.getPageImage("assets/omrtest.pdf", 2); // repeat this sequence of lines for however many students you have
        DImage student1DImage = new DImage(student1PImage);
        String[] student1Answers = myFilter.getAnswersFrom(student1DImage);

        PImage student2PImage = PDFHelper.getPageImage("assets/omrtest.pdf", 3); // repeat this sequence of lines for however many students you have
        DImage student2DImage = new DImage(student1PImage);
        String[] student2Answers = myFilter.getAnswersFrom(student1DImage);

        PImage student3PImage = PDFHelper.getPageImage("assets/omrtest.pdf", 4); // repeat this sequence of lines for however many students you have
        DImage student3DImage = new DImage(student1PImage);
        String[] student3Answers = myFilter.getAnswersFrom(student1DImage);

        int[] wrongAnswers = new int[100];
        String[] studentIDArray = new String[3];
        int[] studentScoresArray = new int[3]; // Change to be the actual number of student scores
        int lineNumber = 0;
        while (reader.readLine() != null) {
            int studentNum = 0; // Repeat these 7 lines for however many students you have to compare
            if (student1Answers[lineNumber] != reader.readLine().substring(0, 1)) {
                wrongAnswers[lineNumber]++;
            } else {
                studentScoresArray[studentNum]++;
            }
            studentNum++;

            if (student2Answers[lineNumber] != reader.readLine().substring(0, 1)) {
                wrongAnswers[lineNumber]++;
            } else {
                studentScoresArray[studentNum]++;
            }
            studentNum++;

            if (student3Answers[lineNumber] != reader.readLine().substring(0, 1)) {
                wrongAnswers[lineNumber]++;
            } else {
                studentScoresArray[studentNum]++;
            }

            lineNumber++;
        }

        for (int i = 0; i < wrongAnswers.length; i++) {
            numStudentsWhoGotQuestionsWrong.append(wrongAnswers[i] + "," + "\n");
        }
        numStudentsWhoGotQuestionsWrong.close();

        for (int i = 0; i < studentIDArray.length; i++) {
            studentScores.append(studentIDArray[i] + " - " + String.valueOf(studentScoresArray[i]));
        }
        studentScores.close();

       /*
       Your code here to...
       (1).  Load the pdf
       (2).  Loop over its pages
       (3).  Create a DImage from each page and process its pixels
       (4).  Output 2 csv files
        */

    }

    private static String fileChooser() {
        String userDirLocation = System.getProperty("user.dir");
        File userDir = new File(userDirLocation);
        JFileChooser fc = new JFileChooser(userDir);
        int returnVal = fc.showOpenDialog(null);
        File file = fc.getSelectedFile();
        return file.getAbsolutePath();
    }
}
