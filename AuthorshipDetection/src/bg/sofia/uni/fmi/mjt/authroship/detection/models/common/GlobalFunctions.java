package bg.sofia.uni.fmi.mjt.authroship.detection.models.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class GlobalFunctions {

    private GlobalFunctions() {
    }

    public static String cleanUp(String word) {
        return word.toLowerCase()
                .replaceAll("^[!,:;\\-?<>#*\'\"\\[(]\\n\\t\\\\]+|[!,:;\\-?<>#*\\[(\n\\t\\\\]+$", "");
    }

    public static int lastIndexOfRegex(String str, String toFind) {
        Pattern pattern = Pattern.compile(toFind);
        Matcher matcher = pattern.matcher(str);

        int lastIndex = -1;
        while (matcher.find()) {
            lastIndex = matcher.start();
        }

        return lastIndex;
    }
}
