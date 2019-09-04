package ru.nbki.ali.testprojects.businesslogic;

/**
 * A category belonging resolver that is based on World Human Organization's categories.
 *
 * @author Arseniy Lee
 * @version 1.0
 */
public enum WHO_BMICategory {
    VERY_SEVERELY_UNDERWEIGHT   (0f, 15f, "Very severely underweight"),
    SEVERELY_UNDERWEIGHT        (15f, 16f, "Severely underweight"),
    UNDERWEIGHT                 (16f, 18.5f, "Underweight"),
    NORMAL                      (18.5f, 25f, "Normal"),
    OVERWEIGHT                  (25f, 30f, "Overweight"),
    MODERATELY_OBESE            (30f, 35f, "Moderately obese"),
    SEVERELY_OBESE              (35f, 40f, "Severely obese"),
    VERY_SEVERELY_OBESE         (40f, 45f, "Very severely obese"),
    MORBIDLY_OBESE              (45f, 50f, "Morbidly obese"),
    SUPER_OBESE                 (50f, 60f, "Super obese"),
    HYPER_OBESE                 (60f, Float.POSITIVE_INFINITY, "Hyper obese");
    /**
     * Thresholds that specify an interval which the category lies in.
     */
    private final float floorThreshold, ceilThreshold;
    /**
     * Name of the category.
     */
    private final String name;

    /**
     * A simple constructor.
     * @param floorThreshold Floor threshold
     * @param ceilThreshold Ceiling threshold
     * @param name Name of the category
     */
    WHO_BMICategory(float floorThreshold, float ceilThreshold, String name) {
        this.floorThreshold = floorThreshold;
        this.ceilThreshold = ceilThreshold;
        this.name = name;
    }

    /**
     * Selects a category depending on bmi parameter.
     * @param bmi BMI value.
     * @return Category name.
     */
    public static String getCategory(float bmi) {
        for (WHO_BMICategory i: WHO_BMICategory.values()) {
            if (i.floorThreshold <= bmi && bmi < i.ceilThreshold) {
                return i.name;
            }
        }
        return null;
    }
}
