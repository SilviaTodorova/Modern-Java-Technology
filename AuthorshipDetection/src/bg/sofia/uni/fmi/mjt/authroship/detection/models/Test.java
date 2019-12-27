package bg.sofia.uni.fmi.mjt.authroship.detection.models;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static bg.sofia.uni.fmi.mjt.authroship.detection.models.common.GlobalConstants.EMPTY_STRING;
import static bg.sofia.uni.fmi.mjt.authroship.detection.models.common.GlobalFunctions.lastIndexOfRegex;

public class Test {


    public static void main(String[] args) throws IOException {
        String filename = "D:\\\\JavaProjects\\\\ModernJavaTechnology\\\\hws\\\\AuthorshipDetection\\\\resources\\\\mysteryFiles\\\\mystery1.txt";
        String delimiters = "[\\.!\\?]";
        Pattern pattern = Pattern.compile(delimiters);

        Set<String> set = new LinkedHashSet<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String buff;
            StringBuilder remainder = new StringBuilder();

            while ((buff = reader.readLine()) != null) {

                StringBuilder sentence = remainder.append(" ").append(buff);
                Matcher matcher = pattern.matcher(sentence);

                if (matcher.find()) {
                    int lastIndex = lastIndexOfRegex(sentence.toString(), delimiters);
                    String start = sentence.substring(0, lastIndex);
                    String[] allSentences = start.trim().split(delimiters);

                    Arrays.stream(allSentences).filter(x->!x.isEmpty() && !x.isBlank()).forEach(x -> set.add(x));

                    remainder.replace(0, remainder.length(), sentence.substring(lastIndex + 1));

                } else {
                    remainder.append(buff).trimToSize();
                }
            }

            String remainderAsString = remainder.toString().trim();
            if (!remainderAsString.isEmpty() && !remainderAsString.isBlank()) {
                set.add(remainderAsString);
            }

            var x =0;
        } catch (Exception ex) {

        }
    }
}