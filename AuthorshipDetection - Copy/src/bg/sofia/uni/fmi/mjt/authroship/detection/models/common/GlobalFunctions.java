package bg.sofia.uni.fmi.mjt.authroship.detection.models.common;

public final class GlobalFunctions {

    private GlobalFunctions() {
    }

    public static String cleanUp(String word) {
        return word.toLowerCase()
                .replaceAll("^[!,:;\\-?<>#*\'\"\\[\\(]\\n\\t\\\\]+|[!,:;\\-?<>#\\*\"\\[\\(]\\n\\t\\\\]+$", "");
    }
}
