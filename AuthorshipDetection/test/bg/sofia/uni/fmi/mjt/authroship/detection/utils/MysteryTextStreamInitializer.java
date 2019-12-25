package bg.sofia.uni.fmi.mjt.authroship.detection.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.stream.Collectors;

public class MysteryTextStreamInitializer {

    public static InputStream initMysteryTextStream(String mysteryText) {
        return new ByteArrayInputStream(
                Arrays.stream(mysteryText.split(""))
                        .collect(Collectors.joining(System.lineSeparator()))
                        .getBytes());

    }
}
