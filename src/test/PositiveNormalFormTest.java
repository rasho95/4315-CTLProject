package test;

import ctl.Formula;
import ctl.Generator;
import ctlform.PositiveNormalForm;
import ctlform.RandomFormula;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import parser.CTLLexer;
import parser.CTLParser;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;


/**
 * 
 * Class for testing the correctness of the functions for the 
 * positive normal form
 * 
 */

class PositiveNormalFormTest {

    public static final String p1 = "java.lang.error";
    public static final String p2 = "java.lang.exception";
    public static final String p3 = "java.lang.math";

    /**
   	 * Function to return a {@code Formula} from the inputed String in.
   	 * @param	in	The depth of the random ENF translatable {@code StateFormula}.
   	 * @return	Returns a {@code Formula}.
   	 */
    private static Formula getFormula(String in) {
        CharStream input = CharStreams.fromString(in);
        CTLLexer lexer = new CTLLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CTLParser parser = new CTLParser(tokens);
        ParseTree tree = parser.root();
        Generator generator = new Generator();
        return generator.visit(tree);
    }
    


    
    /**
     * 
     * Test for asserting that random untranslatable {@code Formula} remains the same
     * after being transformed into its positive normal form
     * 
     */
    @Test
    void testFormulaUnchanged() {

        for (int i = 0; i < 1000; i++) {
            Formula generated = RandomFormula.UntranslatableRandomFormula(10);

            Formula translated = generated.positiveNormalForm();

            Assertions.assertEquals(translated.toString(), generated.toString());
        }

        for (int i = 0; i < 1000; i++) {
            Formula generated = RandomFormula.translatableENFRandomFormula(10);

            Formula translated = generated.positiveNormalForm();

            Assertions.assertEquals(translated.toString(), generated.toString());
        }

    }

    /**
     * 
     * Test for asserting that random translatable PNF {@code Formula} is not the
     * same as its positive normal form
     * 
     */
    @Test
    void testFormulaChanged() {
        for (int i = 0; i < 1000; i++) {
            Formula generated = RandomFormula.translatablePNFRandomFormula(10);
            Formula translated = generated.positiveNormalForm();
            boolean isSame = translated.toString().equals(generated.toString());
            assertFalse(isSame);
        }
    }



    /**
     *
     * Test for asserting the inputed {@code Formula} "!!True" is correctly
     * translated into its positive normal form
     *
     */
    @Test
    void testTranslatedDoubleNegation() {
        String input1 = "!!True";
        Formula formula1 = getFormula(input1);

        Formula tester = PositiveNormalForm.translate(formula1);

        Assertions.assertEquals("true", tester.toString());
    }


    /**
     * 
     * Test for asserting the inputed {@code Formula} "! True" is correctly
     * translated into its positive normal form
     * 
     */
    @Test
    void testTranslatedNegatedTrue() {
        String input1 = "! True";
        Formula formula1 = getFormula(input1);

        Formula tester = PositiveNormalForm.translate(formula1);

        Assertions.assertEquals("false", tester.toString());
    }
    
    /**
     * 
     * Test for asserting the inputed {@code Formula} "! false" is correctly
     * translated into its positive normal form
     * 
     */
    @Test
    void testTranslatedNegatedFalse() {
        String input1 = "! false";
        Formula formula1 = getFormula(input1);

        Formula tester = PositiveNormalForm.translate(formula1);

        Assertions.assertEquals("true", tester.toString());
    }

    
    /**
     * 
     * Test for asserting the inputed {@code Formula} !!!(!(!(!(p1))) && !!!p2)
     * where p1 = java.lang.error and p2 = java.lang.exception, is correctly
     * translated into its positive normal form
     * 
     */
    @Test
    void testTranslatedNegatedAnd() {
        String input1 = "!!!(!(!(!(" + p1 + "))) && !!!" + p2 + ")";
        Formula formula1 = getFormula(input1);

        Formula test = PositiveNormalForm.translate(formula1);

        String expectedOutput = "(" + p1 + ")||(" + p2 + ")";
        Formula expected = getFormula(expectedOutput);
        Assertions.assertEquals(expected, test);
    }
    
    /**
     * 
     * Test for asserting the inputed {@code Formula} !A X True
     * is correctly translated into its positive normal form
     * 
     */
    @Test
    void testTranslatedNegatedForAllNext() {
        String input1 = "!A X True";
        Formula formula1 = getFormula(input1);

        Formula tester = PositiveNormalForm.translate(formula1);

        Assertions.assertEquals("E(X(false))", tester.toString());
    }
    
    /**
     * 
     * Test for asserting the inputed {@code Formula} !E X True
     * is correctly translated into its positive normal form
     * 
     */
    @Test
    void testTranslatedNegatedExistsNext() {
        String input1 = "!E X True";
        Formula formula1 = getFormula(input1);

        Formula tester = PositiveNormalForm.translate(formula1);

        Assertions.assertEquals("A(X(false))", tester.toString());
    }

    /**
     * 
     * Test for asserting the inputed {@code Formula} !E(p1Up1)True 
     * where p1 = java.lang.error is correctly translated into its positive 
     * normal form
     * 
     */
    @Test
    void testTranslatedNegatedExistsUntil() {
        String input1 = "!E(" + p1 + " U " + p1 + ")";
        Formula formula1 = getFormula(input1);

        Formula tester = PositiveNormalForm.translate(formula1);

        Assertions.assertEquals("A(((" + p1 + ")&&(!(" + p1 + ")))W((!(" + p1 + "))&&(!(" + p1 + "))))", tester.toString());
    }

    /**
     * 
     * Test for asserting the inputed {@code Formula} !A(p1Up1)
     * where p1 = java.lang.error is correctly translated into its positive 
     * normal form
     * 
     */
    @Test
    void testTranslatedNegatedForAllUntil() {
        String input1 = "!A(" + p1 + " U " + p1 + ")";
        Formula formula1 = getFormula(input1);
        Formula tester = PositiveNormalForm.translate(formula1);

        Assertions.assertEquals("E(((" + p1 + ")&&(!(" + p1 + ")))W((!(" + p1 + "))&&(!(" + p1 + "))))", tester.toString());
    }
    
    /**
     * 
     * Test for checking a set of samples that contain Negation (!) Quantifiers are 
     * equal to its correct positive normal form translation
     * 
     */
    @Test
    void testMultipleNegationsAtomic() {

        ArrayList<String> inputList = new ArrayList<String>();
        inputList.add("!!!" + p1);
        inputList.add("!!!!" + p1);
        inputList.add("!!!!!" + p2);
        inputList.add("!!!!! true");
        inputList.add("!!!!!false");
        inputList.add("!!!!!!!!" + p1);

        ArrayList<String> outputList = new ArrayList<String>();
        outputList.add("!(" + p1 + ")");
        outputList.add("" + p1 + "");
        outputList.add("!(" + p2 + ")");
        outputList.add("false");
        outputList.add("true");
        outputList.add("" + p1 + "");

        for (int i = 0; i < inputList.size(); i++) {
            assertEquals(outputList.get(i), PositiveNormalForm.translate(getFormula(inputList.get(i))).toString());
        }
    }
    
    /**
     * 
     * Test for checking a set of samples are 
     * equal to its correct positive normal form translation
     * 
     */
    @Test
    void testRandomSamples() {
        ArrayList<String> inputList = new ArrayList<String>();
        inputList.add("!EX(" + p1 + "&&" + p2 + ")");
        inputList.add("(E(" + p3 + " U " + p1 + ") && !true)");
        inputList.add("EX!true");
        inputList.add("!" + p1 + "||!" + p2);
        inputList.add("A(!true U !" + p1+ ")");

        ArrayList<String> outputList = new ArrayList<String>();
        outputList.add("A(X((!(" + p1 + "))||(!(" + p2 + "))))");
        outputList.add("(E((" + p3 + ")U(" + p1 + ")))&&(false)");
        outputList.add("E(X(false))");
        outputList.add("(!(java.lang.error))||(!(java.lang.exception))");
        outputList.add("A((false)U(!(java.lang.error)))");


        for (int i = 0; i < inputList.size(); i++) {
            Formula formula = getFormula(inputList.get(i));
            Formula out = PositiveNormalForm.translate(formula);
            assertEquals(outputList.get(i), out.toString());
        }
    }
}
