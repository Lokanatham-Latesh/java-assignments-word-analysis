import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Assignment {
    public static void main(String[] args) throws IOException {
        // Take the Input as URL where File is present
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the file path  ");
        String filePath = sc.nextLine();
        // Defining List Of words to exclude in from that file
        ArrayList<String> excludedWords = new ArrayList<>(
                Arrays.asList("a", "an", "the", "is", "was", "were", "has", "have", "had", "be", "been", "being", "am",
                        "are", "will", "shall", "can", "could", "should", "would", "may", "might", "must"));

        // Creating a File object using the provided file path
        File file = new File(filePath);

        // creating outFile to Write Analysis data into OutPutFile
        File resultantFile = new File("Result.txt");
        FileWriter fileWriter = new FileWriter(resultantFile);

        // Check if the file exists and is a file (not a directory)
        if (file.exists() && file.isFile()) {

            // Check if the file has a '.txt' extension
            if (filePath.toLowerCase().endsWith(".txt")) {
                try {
                    // Create a FileReader and BufferedReader to read the file
                    // File Reader help us to read char by char
                    // Buffer Reader help us to read Line By Line which help us to count number
                    // lines present in text file
                    FileReader reader = new FileReader(file);
                    BufferedReader buffReader = new BufferedReader(reader);

                    int totalWords = 0;
                    int totalCharacters = 0;
                    int totalAuxiliaryVerbs= 0;
                    Map<String, Integer> wordCountMap = new HashMap<>();

                    // Read through the file line by line
                    String line;
                    int count = 0;
                    while ((line = buffReader.readLine()) != null) {
                        // Split the line into words separated with spaces
                        count++;
                        String[] words = line.split("\\s+");

                        for (String word : words) {
                            // Clean the word by removing punctuation and converting to lowercase
                            word = word.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();

                            // count the totalAuxiliaryVerbs
                             if(excludedWords.contains(word)){
                                totalAuxiliaryVerbs++;
                             }

                            // Ignore empty words and words to exclude
                            if (!word.isEmpty() && !excludedWords.contains(word)) {
                                // Count total words and total characters (excluding spaces)
                                totalWords++;
                                totalCharacters += word.length();

                                // Count the frequency of each word
                                if (wordCountMap.containsKey(word)) {
                                    wordCountMap.put(word, wordCountMap.get(word) + 1);
                                } else {
                                    wordCountMap.put(word, 1);
                                }

                            }
                        }
                    }

                    // Write the main heading for the analysis for understanding purpose that this is a Analysis File
                    fileWriter.write("----- Analysis Report -----\n\n");

                    // Write total word count
                    fileWriter.write("1. Total Words (excluding articles and auxiliary verbs): " + totalWords + "\n");

                    // write total articles and auxiliary verbs count
                    fileWriter.write("2. Total articles and auxiliary verbs: "+totalAuxiliaryVerbs+"\n");

                    // Write total character count
                    fileWriter.write("3. Total Characters (excluding spaces): " + totalCharacters + "\n");

                    // Write total number of lines
                    fileWriter.write("4. Total Number of Lines: " + count + "\n");

                    // Write word frequencies with sorting
                    fileWriter.write("5. Word Frequencies:\n");

                    // Sort the word counts based on frequency (in descending order)
                    wordCountMap.entrySet()
                            .stream()
                            .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                            .forEach(entry -> {
                                try {
                                    fileWriter.write(entry.getKey() + ": " + entry.getValue() + "\n");
                                } catch (IOException e) {
                                    System.out.println(e.getMessage());
                                }
                            });

                    // Close the fileWriter
                    fileWriter.close();

                } catch (IOException exception) {
                    System.out.println(exception.getMessage());

                }
                System.out.println(
                        "Analysis data has been written to the output file. You can check the file using the URL below. \n To open the file, hold the 'Ctrl' key and click the URL.");
                System.out.println(resultantFile.getAbsolutePath());

            }
            // If the user provides a file other than a .txt file, it falls into this block.
            else {
                System.out.println("The file is not a text file. Please provide a '.txt' file.");
            }

        }
        // If the user provides an incorrect path or if the file does not exist, it
        // falls into this block
        else {
            System.out.println("The file does not exist or is not a valid file. Please check the file path.");
        }
        sc.close();

    }

}