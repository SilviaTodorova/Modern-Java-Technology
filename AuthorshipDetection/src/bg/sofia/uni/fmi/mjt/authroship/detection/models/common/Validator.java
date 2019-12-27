package bg.sofia.uni.fmi.mjt.authroship.detection.models.common;

import static bg.sofia.uni.fmi.mjt.authroship.detection.models.common.GlobalConstants.*;

public class Validator {
    public static <T> void checkNotNull(T elem) {
        if(elem == null){
            throw new IllegalArgumentException(ERROR_NULL_VALUE);
        }
    }

    public static void checkNotNegativeDoubleValuesInArray(double[] arr) {
        for (double elem: arr) {
            if(elem <= 0){
                throw new IllegalArgumentException(ERROR_NEGATIVE_VALUE);
            }
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
