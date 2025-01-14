import java.io.*;
import java.util.*;

public class Assignment {
    // List of words to exclude
    private static final ArrayList<String> excludedWords = new ArrayList<>(Arrays.asList(
            "a", "an", "the", "is", "was", "were", "has", "have", "had", "be", "been", "being", "am", "are", "will", 
            "shall", "can", "could", "should", "would", "may", "might", "must"));

    public static void main(String[] args) throws IOException {
        // Take the Input as URL where File is present
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the file path  ");
        String filePath = sc.nextLine();
        
        // Creating a File object using the provided file path
        File file = new File(filePath);
        
        // creating outFile to Write Analysis data into OutPutFile
        File resultantFile = new File("Result.txt");
        StringBuilder result = new StringBuilder();

        // Check if the file exists and is a file (not a directory)
        if (file.exists() && file.isFile()) {

            // Check if the file has a '.txt' extension
            if (filePath.toLowerCase().endsWith(".txt")) {
                try {
                    // Analyze the file
                    analyzeFile(file, result);

                    // Write the anlayze  result to the file
                    writeToFile(resultantFile, result);
                    System.out.println("Analysis data has been written to the output file. You can check the file using the URL below.");
                    System.out.println(resultantFile.getAbsolutePath());

                } catch (IOException exception) {
                    System.out.println(exception.getMessage());
                }
            } else {
                System.out.println("The file is not a text file. Please provide a '.txt' file.");
            }
        } else {
            System.out.println("The file does not exist or is not a valid file. Please check the file path.");
        }

        sc.close();
    }

    // Method to analyze the file content
    private static void analyzeFile(File file, StringBuilder result) throws IOException {
        // Create a FileReader and BufferedReader to read the file
        FileReader reader = new FileReader(file);
        BufferedReader buffReader = new BufferedReader(reader);

        int totalWords = 0;
        int totalCharacters = 0;
        int totalAuxiliaryVerbs = 0;
        Map<String, Integer> wordCountMap = new HashMap<>();
        int totalLines = 0;

        // Read through the file line by line
        String line;
        while ((line = buffReader.readLine()) != null) {
            totalLines++;
            String[] words = line.split("\\s+");

            for (String word : words) {
                // Clean the word by removing punctuation and converting to lowercase
                word = word.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();

                // Count auxiliary verbs
                if (excludedWords.contains(word)) {
                    totalAuxiliaryVerbs++;
                }

                // Ignore empty words and words to exclude
                if (!word.isEmpty() && !excludedWords.contains(word)) {
                    totalWords++;
                    totalCharacters += word.length();

                    // Count the frequency of each word
                    wordCountMap.put(word, wordCountMap.getOrDefault(word, 0) + 1);
                }
            }
        }

        // Append the analysis data  to the StringBuilder
        result.append("----- Analysis Report -----\n\n");
        result.append("1. Total Words (excluding articles and auxiliary verbs): ").append(totalWords).append("\n");
        result.append("2. Total articles and auxiliary verbs: ").append(totalAuxiliaryVerbs).append("\n");
        result.append("3. Total Characters (excluding spaces): ").append(totalCharacters).append("\n");
        result.append("4. Total Number of Lines: ").append(totalLines).append("\n");

        result.append("5. Word Frequencies:\n");

        // Sort the word counts based on frequency (in descending order)
        wordCountMap.entrySet()
                .stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .forEach(entry -> result.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n"));

        // Close the readers
        buffReader.close();
    }

    // Method to write the result to the file
    private static void writeToFile(File file, StringBuilder result) {
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(result.toString());
        } catch (IOException e) {
            System.out.println("Error writing to the file: " + e.getMessage());
        }
    }
}