package bg.sofia.uni.fmi.mjt.authroship.detection.enums;

public enum FeatureType {
    // средният брой символи в дума, след strip-ване на пунктуацията
    AVERAGE_WORD_LENGTH,

    // броят на всички различни думи, използвани в текста, разделен
    // на броя на всички думи. Измерва колко повтаряща се е лексиката
    TYPE_TOKEN_RATIO,

    // броят на думите, срещащи се само по веднъж в даден текст,
    // разделен на броя на всички думи.
    HAPAX_LEGOMENA_RATIO,

    // броят на всички думи, използвани в текста, разделен на
    // броя на изреченията.
    AVERAGE_SENTENCE_LENGTH,

    // средният брой фрази в изречение
    AVERAGE_SENTENCE_COMPLEXITY
}