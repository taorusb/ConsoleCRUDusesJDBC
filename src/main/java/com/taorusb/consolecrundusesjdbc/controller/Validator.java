package com.taorusb.consolecrundusesjdbc.controller;

public class Validator {

    public static String idError = "Incorrect argument. The id value must not contain any letters or special characters.";
    public static String nameError = "The meaning of the first or last name must not contain numbers or special characters.";
    public static String elementNotFoundError = "A entity with such id does'n exist.";
    public static String successful = "successful.";
    public static String allRight = "allRight";

    public static boolean checkString(String s) {
        return s.matches("[\\p{L}|]+");
    }

    public static boolean checkId(String s) {
        return s.matches("\\d+");
    }
}