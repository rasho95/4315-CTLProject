package test;

import ctl.Formula;
import ctl.Generator;
import ctlform.ExistentialNormalForm;
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

/**
 * 
 * Class for testing the correctness of the functions for the 
 * existential normal form
 * 
 */

class ExistentialNormalFormTest {

    public static final String p1 = "java.lang.error";
    public static final String p2 = "java.lang.exception";
    public static final String p3 = "java.lang.object";

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
     * Test for asserting a random {@code Formula} remains unchanged after 
     * being translated into its existential normal form
     * 
     */
    @Test
    void testFormulaUnchanged() {

        for (int i = 0; i < 10000; i++) {
            Formula generated = RandomFormula.UntranslatableRandomFormula(10);
            Formula translated = generated.existentialNormalForm();
            Assertions.assertEquals(translated.toString(), generated.toString());
        }

    }
    
    /**
     * 
     * Test for asserting a random {@code Formula} is not equal after 
     * being translated into its positive normal form
     * 
     */
    @Test
    void testFormulaChanged() {
        for (int i = 0; i < 10000; i++) {
            Formula generated = RandomFormula.translatablePNFRandomFormula(10);
            Formula translated = generated.positiveNormalForm();
            boolean isSame = translated.toString().equals(generated.toString());
            Assertions.assertFalse(isSame);
        }
    }

    
    /**
     * 
     * Test for checking a ForAllNext {@code Formula} is transformed 
     * into its existential normal form
     * 
     */
    @Test
    void testTranslatedForAllNext() {
        String in = "AX(" + p1 + ")";
        Formula formula = getFormula(in);
        Formula g = ExistentialNormalForm.translate(formula);
    }
    
    /**
     * 
     * Test for checking a ForAllUntil {@code Formula} is transformed 
     * into its existential normal form
     * 
     */
    @Test
    void testTranslatedForAllUntil() {
        String in = "AX(" + p1 + ")";
        String in1 = "A (" + in + "U " + in + ")";
        Formula formula = getFormula(in1);
        Formula g = ExistentialNormalForm.translate(formula);

        Formula gg = g.existentialNormalForm();
    }


    /**
     * 
     * Test for checking a set of samples that contain ForAll Quantifiers are 
     * equal too its correct existential normal form translation
     * 
     */
    @Test
    void testRandomSamples() {
        ArrayList<String> in = new ArrayList<String>();
        in.add("AX(" + p1 + ")&&EX(" + p2 + ")");
        in.add("EX(" + p1 + ")");
        in.add("A(A(" + p1 + " U true) U A(true U " + p2 + "))");
        in.add("(true && AX " + p1 + ")");
        in.add("(true && AX(true && true))");
        in.add("EX A(" + p2 + " U " + p3 + ")");
        in.add("AX EX " + p3);
        in.add("!AX " + p2);
        in.add("EX A(" + p3 + " U " + p3 + ")");
        in.add("!AX true");
        in.add("A(A(" + p1 + " U " + p2 + ") U E(" + p2 + " U true))");

        ArrayList<String> out = new ArrayList<String>();
        out.add("(!(E(X(!(" + p1 + ")))))&&(E(X(" + p2 + ")))");
        out.add("E(X(" + p1 + "))");
        out.add("(!(E((!((!(E((!(" + p2 + "))U((!(true))&&(!(" + p2 + "))))))&&(!(E(G(!(" + p2 + ")))))))U((!((!(E((!(true))U((!(" + p1 + "))&&(!(true))))))&&(!(E(G(!(true)))))))&&(!((!(E((!(" + p2 + "))U((!(true))&&(!(" + p2 + "))))))&&(!(E(G(!(" + p2 + ")))))))))))&&(!(E(G(!((!(E((!(" + p2 + "))U((!(true))&&(!(" + p2 + "))))))&&(!(E(G(!(" + p2 + "))))))))))");
        out.add("(true)&&(!(E(X(!(" + p1 + ")))))");
        out.add("(true)&&(!(E(X(!((true)&&(true))))))");
        out.add("E(X((!(E((!(" + p3 + "))U((!(" + p2 + "))&&(!(" + p3 + "))))))&&(!(E(G(!(" + p3 + ")))))))");
        out.add("!(E(X(!(E(X(" + p3 + "))))))");
        out.add("!(!(E(X(!(" + p2 + ")))))");
        out.add("E(X((!(E((!(" + p3 + "))U((!(" + p3 + "))&&(!(" + p3 + "))))))&&(!(E(G(!(" + p3 + ")))))))");
        out.add("!(!(E(X(!(true)))))");
        out.add("(!(E((!(E((" + p2 + ")U(true))))U((!((!(E((!(" + p2 + "))U((!(" + p1 + "))&&(!(" + p2 + "))))))&&(!(E(G(!(" + p2 + ")))))))&&(!(E((" + p2 + ")U(true))))))))&&(!(E(G(!(E((" + p2 + ")U(true)))))))");


        for (int i = 0; i < in.size(); i++) {
            String form = getFormula(in.get(i)).existentialNormalForm().toString();
            Assertions.assertEquals(form, out.get(i));
        }

    }

}
