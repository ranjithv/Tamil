package test;


import my.interest.lang.tamil.EzhuththuUtils;
import my.interest.lang.tamil.StringUtils;
import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.impl.rx.cir.Asai;
import my.interest.lang.tamil.impl.rx.cir.Eerasaichcheer;
import my.interest.lang.tamil.impl.rx.cir.Moovasaichcheer;
import my.interest.lang.tamil.impl.rx.cir.Ntaalasaichcheer;
import org.junit.Assert;
import org.junit.Test;
import tamil.lang.*;
import tamil.lang.api.ezhuththu.TamilCharacterSetCalculator;
import tamil.lang.api.regex.RXIncludeCanonicalEquivalenceFeature;
import tamil.lang.api.regex.RXKuttuFeature;
import tamil.lang.api.regex.RXOverrideSysDefnFeature;
import tamil.util.IPropertyFinder;
import tamil.util.regex.TamilPattern;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class Ezhuththu_0Test {

    static {
        TamilFactory.init();
    }


    static void cirSelfTestUtility(List<String> collectionRx) {
        for (String cir : collectionRx) {
            TamilPattern pattern = TamilPattern.compile("${" + cir + "}");

            for (String cir1 : collectionRx) {
                System.out.println("comparing:" + cir + " with " + cir1);
                Matcher matcher = pattern.matcher(cir1);
                if (cir.equals(cir1)) {
                    Assert.assertTrue(matcher.matches());
                } else {
                    Assert.assertFalse(matcher.matches());
                }
            }

        }
    }

    @Test
    public void kouvaiTest() {
        TamilWord w = TamilWord.from("கெளவை");
        Assert.assertEquals(3, w.size());
    }

    @Test
    public void cirSelfTest() {
        List<String> cirs = new ArrayList<String>();
        cirs.addAll(new Asai().getAllCirs());
        cirs.addAll(new Eerasaichcheer().getAllCirs());
        cirs.addAll(new Moovasaichcheer().getAllCirs());
        cirs.addAll(new Ntaalasaichcheer().getAllCirs());

        cirSelfTestUtility(cirs);
    }


    @Test
    public void testUnicodeBlocSize() {
       // Assert.assertEquals(209, TamilFactory.getRegEXCompiler().getUnicodeBMPBlocksRegEXDescriptions().size());

        //BMP blocks
        Assert.assertTrue( TamilFactory.getRegEXCompiler().getUnicodeBMPBlocksRegEXDescriptions().size()>150);

    }

    @Test
    public void testConstants() {
        TamilPattern pat = TamilFactory.getRegEXCompiler().compile("${[[அம்மா]]}");
        Matcher m = pat.matcher("அம்மா");
        Assert.assertEquals(true, m.matches());

         pat = TamilFactory.getRegEXCompiler().compile("${[அம்மா]}");
         m = pat.matcher("அ");
        Assert.assertEquals(true, m.matches());

        pat = TamilFactory.getRegEXCompiler().compile("${[அம்மா]}");
        m = pat.matcher("ம்");
        Assert.assertEquals(true, m.matches());

        pat = TamilFactory.getRegEXCompiler().compile("${[அம்மா]}");
        m = pat.matcher("மா");
        Assert.assertEquals(true, m.matches());

        pat = TamilFactory.getRegEXCompiler().compile("${[அம்மா]}");
        m = pat.matcher("ம");
        Assert.assertEquals(false, m.matches());
    }

    @Test
    public void testUnicodeMatches() {
        TamilPattern pat = TamilFactory.getRegEXCompiler().compile("${!TAMIL}");
        Matcher m = pat.matcher("2");
        Assert.assertEquals(true, m.matches());
        pat = TamilPattern.compile("${TAMIL}");
        String s = "That is a simple test";
        m = pat.matcher(s);
        Assert.assertEquals(false, m.find());


        pat = TamilPattern.compile("${ஒருங்குறித்தொகுதிக்கு உள்ளே[BASIC_LATIN,TAMIL]}+");
        s = "That is  a simple test 23423 ஒருங்குறித்தொகுதிக்கு";
        m = pat.matcher(s);
        Assert.assertEquals(true, m.matches());

        pat = TamilPattern.compile("${ஒருங்குறித்தொகுதிக்கு உள்ளே[TAMIL,BASIC_LATIN]}+");
        s = "There is an arabic character in it ف  ";
        m = pat.matcher(s);
        Assert.assertEquals(false, m.matches());

        pat = TamilPattern.compile("${TAMIL}");
        s = "அ";
        m = pat.matcher(s);
        Assert.assertEquals(true, m.matches());

        pat = TamilPattern.compile("${!TAMIL}");
        s = "a";
        m = pat.matcher(s);
        Assert.assertEquals(true, m.matches());

        pat = TamilPattern.compile("${ஒருங்குறித்தொகுதிக்கு வெளியே[TAMIL,BASIC_LATIN]}");
        s = "ف";
        m = pat.matcher(s);
        Assert.assertEquals(true, m.matches());
//       while(m.find()) {
//           System.out.println(m.start() + ":" + m.end());
//       }

        pat = TamilPattern.compile("${ஒருங்குறித்தொகுதிக்கு வெளியே[TAMIL,BASIC_LATIN,GENERAL_PUNCTUATION,   ARROWS]}");
        s = "→";
        m = pat.matcher(s);
        Assert.assertEquals(false, m.matches());


    }


    @Test
    public void testSize() {
        Assert.assertEquals(5 * 18 + 5, EzhuththuUtils.filterKuRil().size());
        Assert.assertEquals(7 * 18 + 7, EzhuththuUtils.filterNedil().size());
        Assert.assertEquals(12, EzhuththuUtils.filterUyir().size());
    }


    @Test
    public void testNoCalculations() {
        TamilCharacterSetCalculator calc = TamilFactory.getTamilCharacterSetCalculator();

        System.out.println("Size:" + calc.getEzhuththuSetDescriptions().size());
        Assert.assertTrue(105 == calc.getEzhuththuSetDescriptions().size());
        Set<TamilCharacter> set = calc.find("யகரவரிசை");
        Assert.assertEquals(set.size(), 13);

        set = calc.find("யகரவரிசை");
        Assert.assertEquals(set.size(), 13);

        set = calc.find("உயிர்மெய்");
        Assert.assertEquals(set.size(), 216);

        set = calc.find("!வடமொழியெழுத்து");
        Assert.assertEquals(set.size(), 247);

        set = calc.find("!இளகரவரிசை");
        Assert.assertEquals(set.size(), 234);

        set = calc.find("!நெடில்");
        Assert.assertEquals(set.size(), 114);


        set = calc.find("இதழ்குவிவரிசை");
        System.out.println(set);
        Assert.assertEquals(set.size(), 95);

        set = calc.find("இதழ்மூடுவரிசை");
        Assert.assertEquals(set.size(), 26);


        set = calc.find("இதழ்குவிவரிசை அல்லது வகரவரிசை அல்லது இதழ்மூடுவரிசை");
        Assert.assertEquals(set.size(), 119);
    }


    @Test
    public void testCodePoints() {

        String rep = toString(TamilCompoundCharacter.IK_OU.getCodePoints(new FeatureSet(RXIncludeCanonicalEquivalenceFeature.FEATURE)));
        System.out.print(rep);
        Assert.assertEquals("\\u0b95\\u0bcc,\\u0b95\\u0bc6\\u0bd7,", rep);

        rep = toString(TamilSimpleCharacter.a.getCodePoints(new FeatureSet(RXIncludeCanonicalEquivalenceFeature.FEATURE)));
        System.out.print(rep);
        Assert.assertEquals("\\u0b85,", rep);

        rep = toString(TamilSimpleCharacter.OU.getCodePoints(new FeatureSet(RXIncludeCanonicalEquivalenceFeature.FEATURE)));
        System.out.print(rep);
        Assert.assertEquals("\\u0b94,\\u0b92\\u0bd7,", rep);

        rep = toString(TamilSuperCompoundCharacter.IKSH_OU.getCodePoints(new FeatureSet(RXIncludeCanonicalEquivalenceFeature.FEATURE)));
        System.out.print(rep);
        Assert.assertEquals("\\u0b95\\u0bcd\\u0bb7\\u0bcc,\\u0b95\\u0bcd\\u0bb7\\u0bc6\\u0bd7,", rep);

        rep = toString(TamilSuperCompoundCharacter.SHREE_.getCodePoints(new FeatureSet(RXIncludeCanonicalEquivalenceFeature.FEATURE)));
        System.out.print(rep);
        Assert.assertEquals("\\u0bb8\\u0bcd\\u0bb0\\u0bc0,", rep);


        rep = toString(TamilCompoundCharacter.IR_EE.getCodePoints(new FeatureSet(RXIncludeCanonicalEquivalenceFeature.FEATURE)));
        System.out.print(rep);
        Assert.assertEquals("\\u0bb0\\u0bc0,", rep);


        rep = toString(TamilCompoundCharacter.IK_OO.getCodePoints(new FeatureSet(RXIncludeCanonicalEquivalenceFeature.FEATURE)));
        System.out.print(rep);
        Assert.assertEquals("\\u0b95\\u0bcb,\\u0b95\\u0bc7\\u0bbe,", rep);

        rep = toString(TamilCompoundCharacter.IK_UU.getCodePoints(new FeatureSet(RXIncludeCanonicalEquivalenceFeature.FEATURE)));
        System.out.print(rep);
        Assert.assertEquals("\\u0b95\\u0bc2,", rep);


    }

    private static String toString(List<int[]> fulllist) {
        StringBuffer buffer = new StringBuffer();
        for (int[] list : fulllist) {
            for (int i : list) {

                String val = Integer.toHexString(i);
                while (val.length() < 4) {
                    val = "0" + val;
                }
                buffer.append("\\u" + val);

            }
            buffer.append(",");
        }
        return buffer.toString();
    }


    @Test
    public void testPatterns1() {
        TamilPattern pattern = TamilPattern.compile("${வகை[ka]}");
        Matcher matcher = pattern.matcher(TamilFactory.getTransliterator(null).transliterate("sai").toString());
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${[கெ]}");
        String ko = "\u0B95\u0BC6\u0BBE";

        matcher = pattern.matcher(ko);
        Assert.assertFalse(matcher.find());


        pattern = TamilPattern.compile("${(theamaa)}");
        matcher = pattern.matcher("தேமா  தேமா");
        Assert.assertTrue(matcher.find());
        System.out.println(matcher.start() + "--------:" + matcher.end());
        Assert.assertEquals(0, matcher.start());
        Assert.assertEquals(4, matcher.end());


        pattern = TamilPattern.compile("${thodarthodakkachchoathanai[(theamaa) thodanguvathilirunthu  ezhuththu]}");
        matcher = pattern.matcher("தேமா  தேமா");
        Assert.assertTrue(matcher.find());
        System.out.println(matcher.start() + "--------:" + matcher.end());
        Assert.assertEquals(0, matcher.start());
        Assert.assertEquals(2, matcher.end());


        pattern = TamilPattern.compile("${thodarthodakkachchoathanai[(theamaa) thodanguvathilirunthu (ntedil thodanguvathilirunthu   (ezhuththu]}");
        matcher = pattern.matcher("தேமா  தேமா");
        Assert.assertTrue(matcher.find());
        System.out.println(matcher.start() + "--------:" + matcher.end());
        Assert.assertEquals(0, matcher.start());
        Assert.assertEquals(2, matcher.end());


        pattern = TamilPattern.compile("${thodarthodakkachchoathanai[(theamaa) thodangaathathilirunthu (ntedil thodanguvathilirunthu   (ezhuththu]}");
        matcher = pattern.matcher("தேமா  தேமா");
        Assert.assertFalse(matcher.find());


        pattern = TamilPattern.compile("${thodarthodakkachchoathanai[(pulhimaa) thodangaathathilirunthu (ntedil thodanguvathilirunthu   (ezhuththu]}");
        matcher = pattern.matcher("தேமா  தேமா");
        Assert.assertTrue(matcher.find());
        Assert.assertEquals(0, matcher.start());
        Assert.assertEquals(2, matcher.end());


        pattern = TamilPattern.compile("${thodarthodakkachchoathanai[(pulhimaa) thodangaathathilirunthu (kurril thodangaathathilirunthu   (ezhuththu]}");
        matcher = pattern.matcher("தேமா  தேமா");
        Assert.assertTrue(matcher.find());
        Assert.assertEquals(0, matcher.start());
        Assert.assertEquals(2, matcher.end());


        pattern = TamilPattern.compile("${(குறள்)}", null, RXIncludeCanonicalEquivalenceFeature.FEATURE);
        String kurralh = "அணியன்றோ நாணுடைமை சான்றோர்க்கஃ தின்றேல்\n" +
                "பிணிஅன்றோ பீடு நடை";
        matcher = pattern.matcher(kurralh);
        Assert.assertTrue(matcher.matches());


        pattern = TamilPattern.compile("${thodarthodakkachchoathanai[(ezhuththu thodanguvathilirunthu theamaa thodangaathathilirunthu ntedil]}", null, RXIncludeCanonicalEquivalenceFeature.FEATURE);
        matcher = pattern.matcher(kurralh);
        Assert.assertTrue(matcher.find());
        System.out.println(matcher.start() + "-----------:" + matcher.end() + ": " + kurralh.substring(matcher.start(), matcher.end()));

        Assert.assertEquals(9, matcher.start());
        Assert.assertEquals(11, matcher.end());


        pattern = TamilPattern.compile("${thodarthodakkachchoathanai[(moovasaichcheer) thodanguvathilirunthu pulhimaa  thodangaathathilirunthu ntedil]}", null, RXIncludeCanonicalEquivalenceFeature.FEATURE);
        matcher = pattern.matcher(kurralh);
        Assert.assertTrue(matcher.find());
        System.out.println(matcher.start() + "-----------:" + matcher.end() + ": " + kurralh.substring(matcher.start(), matcher.end()));

        Assert.assertEquals(9, matcher.start());
        Assert.assertEquals(11, matcher.end());


        pattern = TamilPattern.compile("${thodarthodakkachchoathanai[(kurralh) thodanguvathilirunthu (pulhimaangaay)]}", null, RXIncludeCanonicalEquivalenceFeature.FEATURE);
        matcher = pattern.matcher(kurralh);
        Assert.assertTrue(matcher.find());
        Assert.assertEquals(0, matcher.start());
        Assert.assertEquals(8, matcher.end());


        pattern = TamilPattern.compile("${thodarthodakkachchoathanai[(theamaa) thodanguvathilirunthu ntedil]}", null, RXIncludeCanonicalEquivalenceFeature.FEATURE);
        matcher = pattern.matcher(kurralh);
        Assert.assertTrue(matcher.find());
        Assert.assertEquals(50, matcher.start());
        Assert.assertEquals(52, matcher.end());


        pattern = TamilPattern.compile("${thodarthodakkachchoathanai[(kaaychcheer) thodanguvathilirunthu (kurralh)]}", null, RXIncludeCanonicalEquivalenceFeature.FEATURE);
        matcher = pattern.matcher(kurralh);
        Assert.assertTrue(matcher.find());


        Assert.assertEquals(0, matcher.start());
        Assert.assertEquals(58, matcher.end());


        pattern = TamilPattern.compile("${தளை[irumaaththirai பின்  நேர்]}");
        matcher = pattern.matcher("தேமா  தேமா");
        Assert.assertTrue(matcher.find());
        System.out.println(matcher.start() + "--------:" + matcher.end());
        Assert.assertEquals(6, matcher.start());
        Assert.assertEquals(8, matcher.end());


//        pattern = TamilPattern.compile("${தளை[நேர் பின்  நேர்]}");
//        matcher = pattern.matcher("தேமா  தேமா");
//        Assert.assertTrue(matcher.find());
//        System.out.println(matcher.start() + "--------:" + matcher.end());
//        Assert.assertEquals(6, matcher.start());
//        Assert.assertEquals(8, matcher.end());


        pattern = TamilPattern.compile("${தளை[[க] பின்  நேர்]}");
        matcher = pattern.matcher("தேமாக தேமா");
        Assert.assertTrue(matcher.find());
        System.out.println(matcher.start() + "--------:" + matcher.end());
        Assert.assertEquals(6, matcher.start());
        Assert.assertEquals(8, matcher.end());


        pattern = TamilPattern.compile("${அசை[தொற்]}");
        matcher = pattern.matcher("நேர்");
        Assert.assertTrue(matcher.matches());


        pattern = TamilPattern.compile("${அசை[செந்தமிழ்]}");
        matcher = pattern.matcher("கூவிளம்");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${(வகை[kadal])}");
        matcher = pattern.matcher(TamilFactory.getTransliterator(null).transliterate("thamizh").toString());
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${(குறள்)}");
        matcher = pattern.matcher("\n" +
                "           இருள்சேர் இருவினையும் சேரா\n" +
                " இறைவன்\n" +
                "பொருள்சேர் \n" +
                "புகழ்புரிந்தார் மாட்டு ");
        Assert.assertTrue(matcher.find());


        pattern = TamilPattern.compile("${தளை[(மாச்சீர்) முன் நேர்]}");
        matcher = pattern.matcher("தேமா      தேமா");
        Assert.assertTrue(matcher.find());
        System.out.println(matcher.start() + "--------:" + matcher.end());
        Assert.assertEquals(0, matcher.start());
        Assert.assertEquals(10, matcher.end());

        pattern = TamilPattern.compile("${தளை[மாச்சீர் முன் நேர்]}");
        matcher = pattern.matcher("தேமா விளம்");
        Assert.assertFalse(matcher.find());


        pattern = TamilPattern.compile("${(வகை[f f])}");
        matcher = pattern.matcher(TamilFactory.getTransliterator(null).transliterate("f f").toString());
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${(வகை[qwrwrwrwtetertetet y ryrtytryrtytrfhfghgfh])}");
        matcher = pattern.matcher(TamilFactory.getTransliterator(null).transliterate("qwrwrwrwtetertetet y ryrtytryrtytrfhfghgfh").toString());
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${(வகை[f f])}");
        matcher = pattern.matcher(TamilFactory.getTransliterator(null).transliterate("f f").toString());
        Assert.assertTrue(matcher.matches());


        pattern = TamilPattern.compile("${மாத்திரை[தென்னை]}");
        matcher = pattern.matcher("கண்ணா");
        Assert.assertTrue(matcher.matches());


        pattern = TamilPattern.compile("${வகை[தமிழ்]}");
        matcher = pattern.matcher("பாகம்");
        Assert.assertTrue(matcher.matches());


        pattern = TamilPattern.compile("${மெய்வகை[அங்கு]}");
        matcher = pattern.matcher("அந்தி");
        Assert.assertTrue(matcher.matches());
    }


    @Test
    public void testPatterns() {

        TamilPattern pattern = TamilPattern.compile("${koovilham}", null, RXKuttuFeature.FEATURE);
        Matcher matcher = pattern.matcher("துயாழ்இனி");
        Assert.assertTrue(matcher.matches());


        pattern = TamilPattern.compile("${ezhuththuvadivam}*");
        matcher = pattern.matcher("தமிழ்தமிழ்");

        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${நாலசைச்சீர்}");
        matcher = pattern.matcher("தேமாந்தண்பூ");
        Assert.assertTrue(matcher.matches());


        pattern = TamilPattern.compile("${அசையெண்ணிக்கை[8]}");
        matcher = pattern.matcher("தேமாந்தண்பூதேமாந்தண்பூ");
        Assert.assertTrue(matcher.matches());


        pattern = TamilPattern.compile("${தேமாநறும்பூ}");
        matcher = pattern.matcher("தேமாநறும்பூ");
        Assert.assertTrue(matcher.matches());


        pattern = TamilPattern.compile("${தேமாந்தண்ணிழல்}");
        matcher = pattern.matcher("தேமாந்தண்ணிழல்");
        Assert.assertTrue(matcher.matches());


        pattern = TamilPattern.compile("${தேமாந்தண்பூ}");
        matcher = pattern.matcher("தேமாந்தண்பூ");
        Assert.assertTrue(matcher.matches());

        matcher = pattern.matcher("ததேமாந்தண்பூ");
        Assert.assertFalse(matcher.matches());


        matcher = pattern.matcher("தேதமாந்தண்பூ");
        Assert.assertFalse(matcher.matches());

        matcher = pattern.matcher("தேமாந்ததண்பூ");
        Assert.assertFalse(matcher.matches());


        pattern = TamilPattern.compile("${theamaangaay}");
        matcher = pattern.matcher("தம்பியும்");
        Assert.assertFalse(matcher.matches());


//        Pattern patt = Pattern.compile("(?<!ab)a");
//        matcher = patt.matcher("ada");
//        Assert.assertTrue(matcher.matches());


        pattern = TamilPattern.compile("${ezhuththuvadivam}*");
        matcher = pattern.matcher("aதமிழ்தமிழ்");
        Assert.assertFalse(matcher.matches());

        Assert.assertFalse(TamilCompoundCharacter.ILLL.isKurilezhuththu());

        pattern = TamilPattern.compile("${kurril}*");
        matcher = pattern.matcher("தமிழ்தமிழ்");
        Assert.assertFalse(matcher.matches());


        pattern = TamilPattern.compile("${kurril}*");
        matcher = pattern.matcher("தமிதமி");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${ntedil}*");
        matcher = pattern.matcher("வேலா");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${ntedil}*");
        matcher = pattern.matcher("ல");
        Assert.assertFalse(TamilSimpleCharacter.LA.isNtedilezhuththu());
        Assert.assertFalse(matcher.matches());

        pattern = TamilPattern.compile("${ntedil}");
        matcher = pattern.matcher("ஆ");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${ntedil}${kurril}+");
        matcher = pattern.matcher("ஆடுடு");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${uyir}${kurril}+");
        matcher = pattern.matcher("ஆடுடு");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${ntedil}");
        matcher = pattern.matcher("க்");
        Assert.assertFalse(matcher.matches());

        pattern = TamilPattern.compile("${kurril}");
        matcher = pattern.matcher("க்");
        Assert.assertFalse(matcher.matches());

//        Pattern j = Pattern.compile("a(?!d)");
//        System.out.println( j.matcher("ac").find());

        pattern = TamilPattern.compile("${!kurril}*");
        matcher = pattern.matcher("காக்கா");
        Assert.assertTrue(matcher.matches());
//        Assert.assertTrue(matcher.find());
//        System.out.println(matcher.start() +":" + matcher.end());
        pattern = TamilPattern.compile("${!kurril}*");
        matcher = pattern.matcher("காகம்");
        Assert.assertFalse(matcher.matches());


        pattern = TamilPattern.compile("${!ezhuththu}*");
        matcher = pattern.matcher("abcd");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${!ezhuththu}*");
        matcher = pattern.matcher("காகம்");
        Assert.assertFalse(matcher.matches());


        pattern = TamilPattern.compile("${ezhuththu}*");
        matcher = pattern.matcher("காகம்a");
        Assert.assertFalse(matcher.matches());

        pattern = TamilPattern.compile("${mozhimuthal}${mozhiyidai}*${mozhikkadai}+");
        matcher = pattern.matcher("காகம்");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${ஓரெழுத்துமொழி}+");
        matcher = pattern.matcher("கா");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${mozhi}");
        matcher = pattern.matcher("பி");
        Assert.assertFalse(matcher.matches());

        pattern = TamilPattern.compile("${mozhi}");
        matcher = pattern.matcher("அம்மா");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${(oarezhuththumozhi)}");
        matcher = pattern.matcher("மா");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${(mozhi)}");
        matcher = pattern.matcher("மா");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${(ntear)}");
        matcher = pattern.matcher("மா");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${ntear}");
        matcher = pattern.matcher("அம்");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${ntirai}");
        matcher = pattern.matcher("நடா");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${ntirai}");
        matcher = pattern.matcher("னடா");
        Assert.assertTrue(matcher.matches());


        pattern = TamilPattern.compile("${ntirai}");
        matcher = pattern.matcher("காசு");
        Assert.assertFalse(matcher.matches());


        pattern = TamilPattern.compile("${ntirai}");
        matcher = pattern.matcher("பசு");
        Assert.assertTrue(matcher.matches());


        pattern = TamilPattern.compile("${theamaa}");
        matcher = pattern.matcher("பசு");
        Assert.assertFalse(matcher.matches());

        pattern = TamilPattern.compile("${theamaa}");
        matcher = pattern.matcher("பசுக்கள்");
        Assert.assertFalse(matcher.matches());


        pattern = TamilPattern.compile("${theamaa}");
        matcher = pattern.matcher("சுகா");
        Assert.assertFalse(matcher.matches());

        pattern = TamilPattern.compile("${theamaa}");
        matcher = pattern.matcher("காசு");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${ntirai}");
        matcher = pattern.matcher("காசும்");
        Assert.assertFalse(matcher.matches());


        pattern = TamilPattern.compile("${theamaa}");
        matcher = pattern.matcher("காசும்");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${theamaa}");
        matcher = pattern.matcher("காசுமட்");
        Assert.assertFalse(matcher.matches());

        pattern = TamilPattern.compile("${theamaa}");
        matcher = pattern.matcher("சுகன்");
        Assert.assertFalse(matcher.matches());

        pattern = TamilPattern.compile("${ntirai}");
        matcher = pattern.matcher("சுக்");
        Assert.assertFalse(matcher.matches());

        pattern = TamilPattern.compile("${ntirai}");
        matcher = pattern.matcher("சுக்கன்");
        Assert.assertFalse(matcher.matches());

        pattern = TamilPattern.compile("${ntear}");
        matcher = pattern.matcher("சுக்");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${ntear}${ntear}");
        matcher = pattern.matcher("சுக்கன்");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${ntear}${ntear}");
        matcher = pattern.matcher("சுக்க");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${theamaa}");
        matcher = pattern.matcher("மாமா");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${theamaa}");
        matcher = pattern.matcher("காக்கா");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${ntear}${ntear}");
        matcher = pattern.matcher("பக்கா");
        Assert.assertTrue(matcher.matches());


        pattern = TamilPattern.compile("${pulhimaa}");
        matcher = pattern.matcher("சுக்கன்");
        Assert.assertFalse(matcher.matches());

        pattern = TamilPattern.compile("${pulhimaa}");
        matcher = pattern.matcher("புளிமா");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${pulhimaa}");
        matcher = pattern.matcher("மாமா");
        Assert.assertFalse(matcher.matches());


        pattern = TamilPattern.compile("${koovilham}");
        matcher = pattern.matcher("சுக்கன்");
        Assert.assertFalse(matcher.matches());


        pattern = TamilPattern.compile("${koovilham}");
        matcher = pattern.matcher("புளிமா");
        Assert.assertFalse(matcher.matches());

        pattern = TamilPattern.compile("${koovilham}");
        matcher = pattern.matcher("மாமா");
        Assert.assertFalse(matcher.matches());

        pattern = TamilPattern.compile("${koovilham}");
        matcher = pattern.matcher("கூவிளம்");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${koovilham}");
        matcher = pattern.matcher("அம்மடு");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${koovilham}");
        matcher = pattern.matcher("கருவி");
        Assert.assertFalse(matcher.matches());

        pattern = TamilPattern.compile("${koovilham}");
        matcher = pattern.matcher("அலகிடம்");
        Assert.assertFalse(matcher.matches());

        pattern = TamilPattern.compile("${koovilham}");
        matcher = pattern.matcher("அரங்குடன்");
        Assert.assertFalse(matcher.matches());


        pattern = TamilPattern.compile("${koovilham}");
        matcher = pattern.matcher("காலடம்");
        Assert.assertTrue(matcher.matches());


        pattern = TamilPattern.compile("${karuvilham}");
        matcher = pattern.matcher("சுக்கன்");
        Assert.assertFalse(matcher.matches());


        pattern = TamilPattern.compile("${karuvilham}");
        matcher = pattern.matcher("புளிமா");
        Assert.assertFalse(matcher.matches());

        pattern = TamilPattern.compile("${karuvilham}");
        matcher = pattern.matcher("மாமா");
        Assert.assertFalse(matcher.matches());

        pattern = TamilPattern.compile("${karuvilham}");
        matcher = pattern.matcher("கருவிளம்");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${karuvilham}");
        matcher = pattern.matcher("கருவி");
        Assert.assertFalse(matcher.matches());

        pattern = TamilPattern.compile("${karuvilham}");
        matcher = pattern.matcher("அலாக்கிடாம்");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${karuvilham}");
        matcher = pattern.matcher("அரங்குடன்");
        Assert.assertTrue(matcher.matches());


        pattern = TamilPattern.compile("${karuvilham}");
        matcher = pattern.matcher("காலடம்");
        Assert.assertFalse(matcher.matches());


        pattern = TamilPattern.compile("${theamaangaay}");
        matcher = pattern.matcher("காலடம்");
        Assert.assertFalse(matcher.matches());


        pattern = TamilPattern.compile("${theamaangaay}");
        matcher = pattern.matcher("தேமாங்காய்");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${theamaangaay}");
        matcher = pattern.matcher("கூவிளம்");
        Assert.assertFalse(matcher.matches());

        pattern = TamilPattern.compile("${theamaangaay}");
        matcher = pattern.matcher("கூவிள");
        Assert.assertFalse(matcher.matches());


        pattern = TamilPattern.compile("${theamaangaay}");
        matcher = pattern.matcher("மாம்மாம்ம");
        Assert.assertTrue(matcher.matches());


        pattern = TamilPattern.compile("${theamaa}");
        matcher = pattern.matcher("பக்கா");
        Assert.assertTrue(matcher.matches());
        pattern = TamilPattern.compile("${theamaa}");
        matcher = pattern.matcher("பக்காடு");
        Assert.assertFalse(matcher.matches());

        matcher = pattern.matcher("பக்காட்");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${theamaa}");
        matcher = pattern.matcher("சுக்க");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${theamaa}");
        matcher = pattern.matcher("சுக்கன்");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${theamaangani}");
        matcher = pattern.matcher(TamilFactory.getTransliterator(null).transliterate("theamaangani").toString());
        Assert.assertTrue(matcher.matches());


        pattern = TamilPattern.compile("${theamaangani}");
        matcher = pattern.matcher(TamilFactory.getTransliterator(null).transliterate("தெம்மாங்கனி").toString());
        Assert.assertTrue(matcher.matches());


        pattern = TamilPattern.compile("${theamaangaay}");
        matcher = pattern.matcher(TamilFactory.getTransliterator(null).transliterate("தெம்மாங்க").toString());
        Assert.assertTrue(matcher.matches());


        pattern = TamilPattern.compile("${pulhimaangaay}");
        matcher = pattern.matcher(TamilFactory.getTransliterator(null).transliterate("புளிமங்காய்").toString());
        Assert.assertTrue(matcher.matches());


        pattern = TamilPattern.compile("${pulhimaangaay}");
        matcher = pattern.matcher(TamilFactory.getTransliterator(null).transliterate("pulhimaangaay").toString());
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${pulhimaangani}");
        matcher = pattern.matcher(TamilFactory.getTransliterator(null).transliterate("pulhimaangani").toString());
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${koovilhangaay}");
        matcher = pattern.matcher(TamilFactory.getTransliterator(null).transliterate("koovilhangaay").toString());
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${koovilhangani}");
        matcher = pattern.matcher(TamilFactory.getTransliterator(null).transliterate("koovilhangani").toString());
        Assert.assertTrue(matcher.matches());


        pattern = TamilPattern.compile("${koovilhangani}");
        matcher = pattern.matcher(TamilFactory.getTransliterator(null).transliterate("அக்கடல்டான").toString());
        Assert.assertFalse(matcher.matches());
        pattern = TamilPattern.compile("${koovilhangani}");
        matcher = pattern.matcher(TamilFactory.getTransliterator(null).transliterate("அக்கடல்டா").toString());
        Assert.assertFalse(matcher.matches());

        pattern = TamilPattern.compile("${koovilhangani}");
        matcher = pattern.matcher(TamilFactory.getTransliterator(null).transliterate("அக்கடல்னடா").toString());
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${koovilhangaay}");
        matcher = pattern.matcher(TamilFactory.getTransliterator(null).transliterate("அக்கடல்ன").toString());
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${koovilhangaay}");
        matcher = pattern.matcher(TamilFactory.getTransliterator(null).transliterate("அக்கடல்டா").toString());
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${koovilhangaay}");
        matcher = pattern.matcher(TamilFactory.getTransliterator(null).transliterate("அக்கடல்டால்").toString());
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${karuvilhangaay}");
        matcher = pattern.matcher(TamilFactory.getTransliterator(null).transliterate("karuvilhangaay").toString());
        Assert.assertTrue(matcher.matches());
        pattern = TamilPattern.compile("${karuvilhangani}");
        matcher = pattern.matcher(TamilFactory.getTransliterator(null).transliterate("karuvilhangani").toString());
        Assert.assertTrue(matcher.matches());


        pattern = TamilPattern.compile("${theamaangaay}");
        matcher = pattern.matcher(TamilFactory.getTransliterator(null).transliterate("pulhimaangaay").toString());
        Assert.assertFalse(matcher.matches());


        pattern = TamilPattern.compile("${(mozhi)}${(idaivelhi)}${தளை[(பிறப்பு) முன்  நேர்]}${(mozhi)}");
        matcher = pattern.matcher("தமிழ் எனது தாய்மொழி");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${mozhi}${இடைவெளி}${தளை[பிறப்பு முன்  நேர்]}${mozhi}", new IPropertyFinder() {
            public String findProperty(String p1) {
                if ("இடைவெளி".equals(p1)) {
                    return "_";
                } else {
                    return null;
                }
            }
        }, RXOverrideSysDefnFeature.FEATURE);
        matcher = pattern.matcher("தமிழ்_எனது_தாய்மொழி");

        Assert.assertTrue(matcher.matches());


    }


    @Test
    public void testUniCodeChar() {
        TamilWord tamil = TamilWord.from("தமிழ்");
        String unicode = tamil.toUnicodeStringRepresentation(FeatureSet.EMPTY);
        unicode = unicode.replace("\"", "\\\"");
        System.out.println(unicode);
        Pattern p = Pattern.compile(unicode, Pattern.CANON_EQ);
        Matcher matcher = p.matcher("abதமிழ்a தமிழ்     ");
        while (matcher.find()) {
            System.out.println(matcher.start() + ":" + matcher.end());
        }


    }


    @Test
    public void tesIndexReplacement() {

        testIndexReplacement("one", "1=one,two=2,200=two hundred", 2, new StringUtils.IndexContext.Range(2));
        testIndexReplacement("${1} ${two}", "1=one,two=2,200=two hundred", 4, new StringUtils.IndexContext.Range(5, 11));
        testIndexReplacement("${1} ${2}", "1=one,2=2,200=two hundred", 0, new StringUtils.IndexContext.Range(0, 4));

        testIndexReplacement("${1} ${2} ${one}", "one=1,1=${one},2=2,200=two hundred", 4, new StringUtils.IndexContext.Range(10, 16));


        testIndexReplacement("${1} ${000} ${2} ${one}", "one=1,1=${one},000=${ZERO}, ZERO=${0z}, 0z=100000000,  one=1,1=${one},2=2,200=two hundred", 10, new StringUtils.IndexContext.Range(5, 11));


    }

    private StringUtils.IndexContext testIndexReplacement(String pattern, final String dataStr, int indexToTest, StringUtils.IndexContext.Range range) {
        Map<String, String> data = new HashMap<String, String>();

        if (dataStr != null) {
            String[] list = dataStr.split(",");
            for (String item : list) {
                String[] pair = item.split("=");
                if (pair.length > 1) {
                    data.put(pair[0].trim(), pair[1]);
                }
            }
        }

        StringUtils.IndexContext context = testIndexReplacement(pattern, data, indexToTest, range);
        return context;

    }


    private StringUtils.IndexContext testIndexReplacement(String pattern, final Map<String, String> data, int indexToTest, StringUtils.IndexContext.Range range) {

        IPropertyFinder keys = new IPropertyFinder() {
            @Override
            public String findProperty(String p1) {
                if (data == null) {
                    return null;
                }
                return data.get(p1);
            }
        };
        StringUtils.IndexContext context = StringUtils.replaceWithContext("${", "}", pattern, keys, true, true, true);
        return testContext(context, indexToTest, range);

    }

    private StringUtils.IndexContext testContext(StringUtils.IndexContext context, int indexToTest, StringUtils.IndexContext.Range range) {
        System.out.println(context.finalString);
        System.out.println(context.ranges);

        StringUtils.IndexContext.Range range1 = context.getSourceIndexRecursively(indexToTest);
        System.out.println("Found Index range:" + range1 + " for given index:" + indexToTest);
        Assert.assertTrue(range1.from == ((range == null) ? indexToTest : range.from));

        Assert.assertTrue(range1.to == ((range == null) ? indexToTest : range.to));

        return context;

    }


    @Test
    public void testStartAndEnding() {

        Assert.assertTrue(wordOK("தமிழ்"));
        Assert.assertTrue(wordOK("இடப்பா"));
        Assert.assertFalse(wordOK("டப்பா"));
        Assert.assertFalse(wordOK("வுடி"));
        Assert.assertFalse(wordOK("றிடிசும்"));
        Assert.assertFalse(wordOK("கப்"));
        Assert.assertTrue(wordOK("கப்பு"));
        Assert.assertTrue(wordOK("புஷ்பம்"));
    }


    private boolean wordOK(String word) {
        TamilWord w = TamilWord.from(word);

        if (w.getFirst().isTamilLetter() && w.getLast().isTamilLetter()) {
            return w.getFirst().asTamilCharacter().isWordToStartWith() && w.getLast().asTamilCharacter().isWordToEndWith();
        } else {
            return false;
        }
    }

}
