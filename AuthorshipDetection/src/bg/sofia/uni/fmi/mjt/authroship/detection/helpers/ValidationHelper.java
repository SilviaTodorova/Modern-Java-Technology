package bg.sofia.uni.fmi.mjt.authroship.detection.helpers;

public class ValidationHelper {
    private static final String ERROR_NULL_VALUE = "Null value";
    private static final String ERROR_NEGATIVE_VALUE = "Negative value";
    private static final String ERROR_COUNT_VALUES_IN_ARRAY = "Array must contains %d elements not %d";

    public static <T> void checkNotNull(T elem) {
        if(elem == null){
            throw new IllegalArgumentException(ERROR_NULL_VALUE);
        }
    }

    public static void checkNotNegative(double elem) {
        if(elem <= 0){
            throw new IllegalArgumentException(ERROR_NEGATIVE_VALUE);
        }
    }

    public static <T> void checkLengthDoubleArray(double[] arr, long length) {
        int arrLength = arr.length;
        if(length != arrLength) {
            String message = String.format(ERROR_COUNT_VALUES_IN_ARRAY, length, arrLength);
            throw new IllegalArgumentException(message);
        }
    }
}
