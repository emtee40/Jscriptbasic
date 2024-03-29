package com.scriptbasic.utility.functions;

import com.scriptbasic.api.BasicFunction;

/**
 * This class implements string functions for the BASIC interpreter. Most of the
 * methods implement a wrapper for the method of the same name in the class
 * {@code java.lang.String}. These methods in the class {@code java.lang.String}
 * are not static and therefore need a String object to work on. The wrapper
 * methods are static and take the first argument as the String object to work
 * on. In other words if in Java you would write
 * <p>
 * <pre>
 * s.xyz(parameterlist)
 * </pre>
 * <p>
 * to call the method {@code xyz()} then in BASIC you will be able to call
 * <p>
 * <pre>
 * xyz(s, parameterlist)
 * </pre>
 * <p>
 * The documentation of the methods implemented in {@link java.lang.String} are
 * not repeated here. Other methods, that implement BASIC like string functions
 * not implemented in the class {@code java.lang.String} are documented in this
 * class.
 *
 * @author Peter Verhas
 */
public class StringFunctions {

    /**
     * Chop off the new line character(s) from the end of the string. If there
     * are more than one new line character at the end of the string then all of
     * them are cut off.
     * <p>
     * If there are no new line characters at the end of the string then the
     * unaltered string is returned.
     *
     * @param s the string to chomp
     * @return the string without the trailing new lines
     */
    @BasicFunction(classification = {com.scriptbasic.classification.String.class,
            com.scriptbasic.classification.Utility.class})
    static public String chomp(String s) {
        return s.replaceAll("\\n*$", "");
    }

    /**
     * Trim the white spaces from the start of the string.
     *
     * @param s the string to trim.
     * @return the trimmed string
     */
    @BasicFunction(classification = {com.scriptbasic.classification.String.class,
            com.scriptbasic.classification.Utility.class})
    static public String ltrim(String s) {
        return s.replaceAll("^\\s*", "");
    }

    /**
     * Trim the white spaces from the end of the string.
     *
     * @param s the string to trim
     * @return the trimmed string
     */
    @BasicFunction(classification = {com.scriptbasic.classification.String.class,
            com.scriptbasic.classification.Utility.class})
    static public String rtrim(String s) {
        return s.replaceAll("\\s*$", "");
    }

    /**
     * Return a substring from the string that starts at the position
     * {@code start} and has a length of {@code len}.
     *
     * @param s
     * @param start
     * @param len
     * @return
     */
    @BasicFunction(classification = {com.scriptbasic.classification.String.class,
            com.scriptbasic.classification.Utility.class})
    static public String mid(String s, int start, int len) {
        return s.substring(start, start + len);
    }

    /**
     * Return {@code len} number of characters from the right (the end) of the
     * string.
     *
     * @param s
     * @param len
     * @return
     */
    @BasicFunction(classification = {com.scriptbasic.classification.String.class,
            com.scriptbasic.classification.Utility.class})
    static public String right(String s, int len) {
        return s.length() > len ? s.substring(s.length() - len) : s;
    }

    /**
     * Return a string that is {@code len} number of space characters.
     *
     * @param len
     * @return
     */
    @BasicFunction(classification = {com.scriptbasic.classification.String.class,
            com.scriptbasic.classification.Utility.class})
    static public String space(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(" ");
        }
        return sb.toString();
    }

    /**
     * Return a string that is {@code len} times the character in {@code s}. If
     * the string {@code s} contains more than one characters then only the
     * first character is repeated.
     *
     * @param len
     * @param s
     * @return
     */
    @BasicFunction(classification = {com.scriptbasic.classification.String.class})
    static public String string(int len, String s) {
        s = s.substring(0, 1);
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(s);
        }
        return sb.toString();
    }

    /**
     * Return a string with the characters reversed.
     *
     * @param s
     * @return
     */
    @BasicFunction(classification = {com.scriptbasic.classification.String.class})
    static public String strreverse(String s) {
        StringBuilder sb = new StringBuilder(s.length());
        for (int i = s.length() - 1; i >= 0; i--) {
            sb.append(s.substring(i, i + 1));
        }
        return sb.toString();
    }

    /**
     * Return a string upper cased.
     *
     * @param s
     * @return
     */
    @BasicFunction(classification = {com.scriptbasic.classification.String.class})
    static public String ucase(String s) {
        return s.toUpperCase();
    }

    /**
     * Return a string lower cased.
     *
     * @param s
     * @return
     */
    @BasicFunction(classification = {com.scriptbasic.classification.String.class})
    static public String lcase(String s) {
        return s.toLowerCase();
    }

    @BasicFunction(classification = {com.scriptbasic.classification.String.class})
    static public String trim(String s) {
        return s.trim();
    }

    /**
     * Implements the functionality of the method {@code s1.indexOf(s2)}
     *
     * @param s1
     * @param s2
     * @return
     */
    @BasicFunction(classification = {com.scriptbasic.classification.String.class})
    static public Long index(String s1, String s2) {
        return (long) s1.indexOf(s2);
    }

    /**
     * Implements the functionality of the method {@code s1.lastIndexOf(s2)}
     *
     * @param s1
     * @param s2
     * @return
     */
    @BasicFunction(classification = {com.scriptbasic.classification.String.class})
    static public Long lastIndex(String s1, String s2) {
        return (long) s1.lastIndexOf(s2);
    }

    /**
     * Implements the functionality of the method {@code s1.indexOf(s2,i)}
     *
     * @param s1
     * @param s2
     * @param i
     * @return
     */
    @BasicFunction(classification = {com.scriptbasic.classification.String.class})
    static public Long indexAfter(String s1, String s2, int i) {
        return (long) s1.indexOf(s2, i);
    }

    /**
     * Implements the functionality of the method {@code s1.lastIndexOf(s2,i)}
     *
     * @param s1
     * @param s2
     * @param i
     * @return
     */
    @BasicFunction(classification = {com.scriptbasic.classification.String.class})
    static public Long lastIndexAfter(String s1, String s2, int i) {
        return (long) s1.lastIndexOf(s2, i);
    }

    /**
     * Returns a one character string that contains the character that is at the
     * position {@code i} in the string {@code s1}.
     *
     * @param s1
     * @param i
     * @return
     */
    @BasicFunction(classification = {com.scriptbasic.classification.String.class})
    static public String charAt(String s1, int i) {
        char[] characterArray = new char[1];
        characterArray[0] = s1.charAt(i);
        return new String(characterArray);
    }

    @BasicFunction(classification = {com.scriptbasic.classification.String.class})
    static public String replaceAll(String s1, String regex, String s2) {
        return s1.replaceAll(regex, s2);
    }

    @BasicFunction(classification = {com.scriptbasic.classification.String.class})
    static public String replaceFirst(String s1, String regex, String s2) {
        return s1.replaceFirst(regex, s2);
    }

    /**
     * @param s1
     * @return the length of the string
     */
    @BasicFunction(classification = {com.scriptbasic.classification.String.class})
    static public int strlen(String s1) {
        return s1.length();
    }

    @BasicFunction(classification = {com.scriptbasic.classification.String.class})
    static public int codePointAt(String s1, int i) {
        return s1.codePointAt(i);
    }

    @BasicFunction(classification = {com.scriptbasic.classification.String.class})
    static public int codePointBefore(String s1, int i) {
        return s1.codePointBefore(i);
    }

    @BasicFunction(classification = {com.scriptbasic.classification.String.class})
    static public int codePointCount(String s1, int i, int j) {
        return s1.codePointCount(j, j);
    }

    @BasicFunction(classification = {com.scriptbasic.classification.String.class})
    static public int compareTo(String s1, String s2) {
        return s1.compareTo(s2);
    }

    @BasicFunction(classification = {com.scriptbasic.classification.String.class})
    static public int compareToIgnoreCase(String s1, String s2) {
        return s1.compareToIgnoreCase(s2);
    }

    @BasicFunction(classification = {com.scriptbasic.classification.String.class})
    static public boolean contains(String s1, String s2) {
        return s1.contains(s2);
    }

    @BasicFunction(classification = {com.scriptbasic.classification.String.class})
    static public boolean endsWith(String s1, String s2) {
        return s1.endsWith(s2);
    }

    @BasicFunction(classification = {com.scriptbasic.classification.String.class})
    static public boolean startsWith(String s1, String s2) {
        return s1.startsWith(s2);
    }

    @BasicFunction(classification = {com.scriptbasic.classification.String.class})
    static public boolean isEmpty(String s1) {
        return s1.isEmpty();
    }
}
