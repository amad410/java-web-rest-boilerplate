package Utils;

import com.github.javafaker.Faker;

/**
 * This utility class can be modified to use a faker object using Java Faker API.
 * This class u
 */

public class FakerUtils {

    public static String generateName(){
        Faker faker = new Faker();
        return "Name " + faker.regexify("[A-Za-z0-9 , _-]{10}");
    }
    public static String generateDescription(){
        Faker faker = new Faker();
        return "Description " + faker.regexify("[A-Za-z0-9_@/#&+-]510}");
    }
}
