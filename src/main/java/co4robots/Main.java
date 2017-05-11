package co4robots;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.BitSet;

import javax.xml.transform.ErrorListener;
import javax.xml.transform.TransformerException;

import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.DiagnosticErrorListener;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimaps;

//import co4robots.engparser.engLexer;
//import co4robots.engparser.engParser;

public class Main {

	public static void main(String[] args) {
		String prop = "Globally,";

		ANTLRInputStream input;
		ANTLRErrorListener listener = new ANTLRErrorListener() {

			@Override
			public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line,
					int charPositionInLine, String msg, RecognitionException e) {
				System.out.println("A-CIAO");
				System.out.println(e.toString());
				System.out.println(msg);
				System.out.println(
				e.getExpectedTokens());
				;
				System.out.println("Clause: "+e.getCause());
				System.out.println(recognizer.getRuleIndexMap());
				System.out.println(e.getOffendingState());
				System.out.println("CONTEXT"+e.getCtx());
				ListMultimap<Integer, String> inverse=Multimaps.invertFrom(Multimaps.forMap(recognizer.getTokenTypeMap()),  ArrayListMultimap.create());
				e.getExpectedTokens().toList().forEach(i -> System.out.println(i+"\t"+inverse.get(i)));
				
			}

			@Override
			public void reportAmbiguity(Parser recognizer, DFA dfa, int startIndex, int stopIndex, boolean exact,
					BitSet ambigAlts, ATNConfigSet configs) {
				System.out.println("B-CIAO");
				
			}

			@Override
			public void reportAttemptingFullContext(Parser recognizer, DFA dfa, int startIndex, int stopIndex,
					BitSet conflictingAlts, ATNConfigSet configs) {
				System.out.println("C-CIAO");
				
			}

			@Override
			public void reportContextSensitivity(Parser recognizer, DFA dfa, int startIndex, int stopIndex,
					int prediction, ATNConfigSet configs) {
				System.out.println("D-CIAO");
				
			}
	
		};

		/*engParser parser = null;
		try {
			input = new ANTLRInputStream(new ByteArrayInputStream(prop.getBytes(StandardCharsets.UTF_8)));

			engLexer lexer = new engLexer(input);
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			parser = new engParser(tokens);
			parser.setProfile(true);
			parser.setBuildParseTree(true);
		

			parser.addErrorListener(listener);

			parser.eng();
			

			
	//		System.out.println(listener..getExpectedTokens());

		} catch (FileNotFoundException e) {
			System.out.println("AAAA");
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("AAAA");
			if (parser != null) {
				System.out.println("AAAA");
				System.out.println(parser.getExpectedTokens());
			}
		}*/
	}

}
