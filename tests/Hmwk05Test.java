import org.junit.Test;

import static org.junit.Assert.*;

public class Hmwk05Test {
    final double DELTA = 0.0001;
    @Test
    public void displayPassword() throws Exception {
        assertEquals("abc", Hmwk05.displayPassword("abcdef", 3));
        assertEquals("abcdef", Hmwk05.displayPassword("abcdef", 10));
    }
    @Test
    public void commonPassword() {
        final String[] commonPasswords = {"Testing123", "testing", "!@#$asdf"};
        assertTrue(Hmwk05.commonPassword(commonPasswords, 3, "Testing123"));
        assertTrue(Hmwk05.commonPassword(commonPasswords, 3, "testing"));
        assertTrue(Hmwk05.commonPassword(commonPasswords, 3, "!@#$asdf"));
        assertTrue(Hmwk05.commonPassword(commonPasswords, 3, "testing123"));
        assertTrue(Hmwk05.commonPassword(commonPasswords, 3, "TeStInG"));
        assertTrue(Hmwk05.commonPassword(commonPasswords, 3, "!@#$ASDF"));
        assertFalse(Hmwk05.commonPassword(commonPasswords, 3, "ting124"));
        assertFalse(Hmwk05.commonPassword(commonPasswords, 3, "asdf1234"));
    }
    @Test
    public void readFromFile() throws Exception {
        final int MAX = 100;
        String[] fileContents = new String[MAX];
        Hmwk05.readFromFile("tests/testing.txt", fileContents);
        assertEquals("testing", fileContents[0]);
        assertEquals("file", fileContents[1]);
        assertEquals("passwords", fileContents[2]);
        assertEquals("are", fileContents[3]);
        assertEquals("here", fileContents[4]);
    }
    @Test
    public void passwordStrength() throws Exception {
        assertEquals("Fail", Hmwk05.passwordStrength(10, true));
        assertEquals("Fail", Hmwk05.passwordStrength(30, true));
        assertEquals("Fail", Hmwk05.passwordStrength(38, true));
        assertEquals("Fail", Hmwk05.passwordStrength(62, true));
        assertEquals("Fail", Hmwk05.passwordStrength(129, true));
        assertEquals("Fail", Hmwk05.passwordStrength(10, false));
        assertEquals("Weak", Hmwk05.passwordStrength(30, false));
        assertEquals("Good", Hmwk05.passwordStrength(38, false));
        assertEquals("Strong", Hmwk05.passwordStrength(62, false));
        assertEquals("Very Strong", Hmwk05.passwordStrength(129, false));
    }

    @Test
    public void log2() throws Exception {
        assertEquals(0, Hmwk05.log2(1.), DELTA);
        assertEquals(1, Hmwk05.log2(2.), DELTA);
        assertEquals(2, Hmwk05.log2(4.), DELTA);
        assertEquals(6, Hmwk05.log2(64.), DELTA);
        assertEquals(3.3219, Hmwk05.log2(10.), DELTA);
        assertEquals(13.54375, Hmwk05.log2(11942.), DELTA);
        assertEquals(-1, Hmwk05.log2(0.5), DELTA);
        assertEquals(2.459431, Hmwk05.log2(5.5), DELTA);
        assertEquals(Double.NaN, Hmwk05.log2(-1.), DELTA);
        assertEquals(Double.NEGATIVE_INFINITY, Hmwk05.log2(0.), DELTA);
    }

    @Test
    public void calculateMaximumEntropy() throws Exception {
        assertEquals(29.89735285, Hmwk05.calculateMaximumEntropy(10, 10), DELTA);
        assertEquals(42.30395746, Hmwk05.calculateMaximumEntropy(26, 10), DELTA);
        assertEquals(46.52932501, Hmwk05.calculateMaximumEntropy(36, 10), DELTA);
        assertEquals(51.30395746, Hmwk05.calculateMaximumEntropy(52, 10), DELTA);
        assertEquals(53.58776679, Hmwk05.calculateMaximumEntropy(62, 10), DELTA);
        assertEquals(56.89735285, Hmwk05.calculateMaximumEntropy(80, 10), DELTA);
        assertEquals(13.28771237, Hmwk05.calculateMaximumEntropy(10, 5), DELTA);
        assertEquals(18.80175887, Hmwk05.calculateMaximumEntropy(26, 5), DELTA);
        assertEquals(20.67970000, Hmwk05.calculateMaximumEntropy(36, 5), DELTA);
        assertEquals(22.80175887, Hmwk05.calculateMaximumEntropy(52, 5), DELTA);
        assertEquals(23.81678524, Hmwk05.calculateMaximumEntropy(62, 5), DELTA);
        assertEquals(25.28771237, Hmwk05.calculateMaximumEntropy(80, 5), DELTA);
    }

    @Test
    public void getRange() throws Exception {
        assertEquals(10, Hmwk05.getRange("0"));
        assertEquals(26, Hmwk05.getRange("a"));
        assertEquals(26, Hmwk05.getRange("A"));
        assertEquals(1, Hmwk05.getRange("!"));
        assertEquals(4, Hmwk05.getRange("!!@#$$"));
        assertEquals(36, Hmwk05.getRange("0a"));
        assertEquals(36, Hmwk05.getRange("0A"));
        assertEquals(52, Hmwk05.getRange("aA"));
        assertEquals(62, Hmwk05.getRange("aA0"));
        assertEquals(67, Hmwk05.getRange("aA0!@#$%"));
    }

    @Test
    public void containsDigits() throws Exception {
        assertTrue(Hmwk05.containsDigits("102355"));
        assertTrue(Hmwk05.containsDigits("asdf12345"));
        assertFalse(Hmwk05.containsDigits("asdfasdf"));
        assertFalse(Hmwk05.containsDigits("asdf!@#$"));
    }

    @Test
    public void containsLowercaseCharacters() throws Exception {
        assertTrue(Hmwk05.containsLowercaseCharacters("asDF!123"));
        assertTrue(Hmwk05.containsLowercaseCharacters("10235d5"));
        assertFalse(Hmwk05.containsLowercaseCharacters("HELLO1234"));
        assertFalse(Hmwk05.containsLowercaseCharacters("H!@#%$6"));
    }

    @Test
    public void containsUppercaseCharacters() throws Exception {
        assertTrue(Hmwk05.containsUppercaseCharacters("ASDf123fd"));
        assertTrue(Hmwk05.containsUppercaseCharacters("1234!@#!Asdf"));
        assertFalse(Hmwk05.containsUppercaseCharacters("f123fd"));
        assertFalse(Hmwk05.containsUppercaseCharacters("1234!@#!sdf"));
    }

    @Test
    public void specialCharacters() throws Exception {
        assertEquals(1, Hmwk05.specialCharacters("asdf!!Asdf!!"));
        assertEquals(2, Hmwk05.specialCharacters("!!@@"));
        assertEquals(3, Hmwk05.specialCharacters("aSDF!@@$"));
        assertEquals(0, Hmwk05.specialCharacters("asdfASDF"));
    }
}