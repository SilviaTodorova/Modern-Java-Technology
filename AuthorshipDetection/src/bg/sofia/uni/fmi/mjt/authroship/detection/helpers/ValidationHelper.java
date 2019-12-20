package bg.sofia.uni.fmi.mjt.authroship.detection.helpers;

public class ValidationHelper {
    public static void checkNotNull(Object obj) {
        if(obj == null){
            throw new IllegalArgumentException();
        }
    }
}
