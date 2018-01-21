package simplenlg.morphophonology.catalan;

import simplenlg.framework.ElementCategory;
import simplenlg.framework.LexicalCategory;
import simplenlg.framework.NLGElement;
import simplenlg.framework.StringElement;

public class MorphophonologyRules extends simplenlg.morphophonology.MorphophonologyRules {

	public static final String vowels_regex =
			"a|A|ä|Ä|à|À|â|Â|e|E|ë|Ë|é|É|è|È|ê|Ê|i|I|ï|Ï|î|Î|o|O|ô|Ô|u|U|û|Û|ü|Ü|ù|Ù";
	
	@Override
	public void doMorphophonology(StringElement leftWord, StringElement rightWord) {
		
		ElementCategory leftCategory = leftWord.getCategory();
		ElementCategory rightCategory = rightWord.getCategory();
		NLGElement leftParent = leftWord.getParent();
		String leftRealisation = leftWord.getRealisation();
		String rightRealisation = rightWord.getRealisation();
		
		if (leftRealisation != null && rightRealisation != null) {
			
			if(LexicalCategory.DETERMINER.equalTo(leftCategory) && 
					LexicalCategory.NOUN.equalTo(rightCategory) && beginsWithVowel(rightWord)){
				if (leftRealisation.matches("de")) {
					leftWord.setRealisation("d'");
				} else if (leftRealisation.matches("el")) {
					leftWord.setRealisation("l'");
				}
			} else if(LexicalCategory.PRONOUN.equalTo(leftCategory) && 
					LexicalCategory.VERB.equalTo(rightCategory) && beginsWithVowel(rightWord)){
				if (leftRealisation.matches("em")) {
					leftWord.setRealisation("m'");
				} else if (leftRealisation.matches("et")) {
					leftWord.setRealisation("t'");
				} else if (leftRealisation.matches("el")) {
					leftWord.setRealisation("l'");
				}
			}
			
		}
	}
	
	/**
	 * Tells if a word begins with a vowel  or an h
	 * 
	 * @param word
	 * @return true if the words begins with a vowel or an h
	 */
	public boolean beginsWithVowel(StringElement word)
	{
		String realisation = word.getRealisation();
		return ( realisation.matches("\\A(" + vowels_regex + "|h|H).*"));
}

}
