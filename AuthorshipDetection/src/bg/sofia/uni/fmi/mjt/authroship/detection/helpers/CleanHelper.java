package bg.sofia.uni.fmi.mjt.authroship.detection.helpers;

public class CleanHelper {
    public static String cleanUp(String word) {
        return word.toLowerCase()
                .replaceAll( "^[!,:;\\-?<>#\\*\'\"\\[\\(\\]\\n\\t\\\\]+|[!,:;\\-?<>#\\*\'\"\\[\\(\\]\\n\\t\\\\]+$", "");
    }
}
