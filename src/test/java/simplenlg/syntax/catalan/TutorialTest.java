/*
 * The contents of this file are subject to the Mozilla Public License
 * Version 1.1 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * The Original Code is "Simplenlg".
 *
 * The Initial Developer of the Original Code is Ehud Reiter, Albert Gatt and Dave Westwater.
 * Portions created by Ehud Reiter, Albert Gatt and Dave Westwater are Copyright (C) 2010-11 The University of Aberdeen. All Rights Reserved.
 *
 * Contributor(s): Ehud Reiter, Albert Gatt, Dave Westwater, Roman Kutlak, Margaret Mitchell, Saad Mahamood.
 */

package simplenlg.syntax.catalan;

import org.junit.Test;
import simplenlg.features.*;
import simplenlg.framework.CoordinatedPhraseElement;
import simplenlg.framework.DocumentElement;
import simplenlg.framework.NLGElement;
import simplenlg.framework.NLGFactory;
import simplenlg.lexicon.Lexicon;
import simplenlg.lexicon.catalan.XMLLexicon;
import simplenlg.phrasespec.NPPhraseSpec;
import simplenlg.phrasespec.PPPhraseSpec;
import simplenlg.phrasespec.SPhraseSpec;
import simplenlg.phrasespec.VPPhraseSpec;
import simplenlg.realiser.catalan.Realiser;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Tests from SimpleNLG tutorial
 * <hr>
 * <p>
 * <p>
 * Copyright (C) 2011, University of Aberdeen
 * </p>
 *
 * @author Ehud Reiter
 */
public class TutorialTest {


    // no code in sections 1 and 2

    /**
     * test section 3 code
     */
    @Test
    public void section3_Test() {
        Lexicon lexicon = new XMLLexicon();                         // default simplenlg lexicon
        NLGFactory nlgFactory = new NLGFactory(lexicon);             // factory based on lexicon

        NLGElement s1 = nlgFactory.createSentence("el meu gos és feliç");

        Realiser r = new Realiser(lexicon);

        String output = r.realiseSentence(s1);

        assertEquals("El meu gos és feliç.", output);
    }

    /**
     * test section 5 code
     */
    @Test
    public void section5_Test() {
        Lexicon lexicon = new XMLLexicon();                         // default simplenlg lexicon
        NLGFactory nlgFactory = new NLGFactory(lexicon);             // factory based on lexicon
        Realiser realiser = new Realiser(lexicon);

        SPhraseSpec p = nlgFactory.createClause();
        p.setSubject("el meu gos");
        p.setVerb("perseguir");
        p.setObject("George");

        String output = realiser.realiseSentence(p);
        assertEquals("El meu gos persegueix George.", output);
    }

    /**
     * test section 6 code
     */
    @Test
    public void section6_Test() {
        Lexicon lexicon = new XMLLexicon();                         // default simplenlg lexicon
        NLGFactory nlgFactory = new NLGFactory(lexicon);             // factory based on lexicon
        Realiser realiser = new Realiser(lexicon);

        SPhraseSpec p = nlgFactory.createClause();
        p.setSubject("Mary");
        p.setVerb("perseguir");
        p.setObject(nlgFactory.createPrepositionPhrase("a", "George"));

        p.setFeature(Feature.TENSE, Tense.PAST);
        String output = realiser.realiseSentence(p);
        assertEquals("Mary perseguí a George.", output);

        p.setFeature(Feature.TENSE, Tense.FUTURE);
        output = realiser.realiseSentence(p);
        assertEquals("Mary perseguirà a George.", output);

        p.setFeature(Feature.NEGATED, true);
        output = realiser.realiseSentence(p);
        assertEquals("Mary no perseguirà a George.", output);

        p = nlgFactory.createClause();
        p.setSubject("Mary");
        p.setVerb("perseguir");
        p.setObject(nlgFactory.createPrepositionPhrase("a", "George"));

        p.setFeature(Feature.INTERROGATIVE_TYPE,
                InterrogativeType.YES_NO);
        output = realiser.realiseSentence(p);
        assertEquals("¿Persegueix Mary a George?", output);

        p.setSubject("Mary");
        p.setVerb("perseguir");
        p.setFeature(Feature.INTERROGATIVE_TYPE, InterrogativeType.WHO_OBJECT);
        output = realiser.realiseSentence(p);
        assertEquals("¿A qui persegueix Mary?", output);

        p = nlgFactory.createClause();
        p.setSubject("el gos");
        p.setVerb("desperta");
        output = realiser.realiseSentence(p);
        assertEquals("El gos desperta.", output);

    }

    /**
     * test ability to use variant words
     */
    @Test
    public void variantsTest() {
        Lexicon lexicon = new XMLLexicon();                         // default simplenlg lexicon
        NLGFactory nlgFactory = new NLGFactory(lexicon);             // factory based on lexicon
        Realiser realiser = new Realiser(lexicon);

        SPhraseSpec p = nlgFactory.createClause();
        p.setSubject("el meu gos");
        p.setVerb("és");  // variant of be
        p.setObject("George");

        String output = realiser.realiseSentence(p);
        assertEquals("El meu gos és George.", output);

        p = nlgFactory.createClause();
        p.setSubject("El meu gos");
        p.setVerb("perseguir");  // variant of chase
        p.setObject(nlgFactory.createPrepositionPhrase("a", "George"));

        output = realiser.realiseSentence(p);
        assertEquals("El meu gos persegueix a George.", output);


        p = nlgFactory.createClause();
        p.setSubject(nlgFactory.createNounPhrase("els", "gossos"));   // variant of "dog"
        p.setVerb("és");  // variant of be
        p.setObject("feliços");  // variant of happy
        output = realiser.realiseSentence(p);
        assertEquals("El gos és feliç.", output);

        p = nlgFactory.createClause();
        p.setSubject(nlgFactory.createNounPhrase("els", "nens"));   // variant of "child"
        p.setVerb("son");  // variant of be
        p.setObject("feliços");  // variant of happy
        output = realiser.realiseSentence(p);
        assertEquals("El nen és feliç.", output);

        // following functionality is enabled
        p = nlgFactory.createClause();
        p.setSubject(nlgFactory.createNounPhrase("los", "perros"));   // variant of "dog"
        p.setVerb("son");  // variant of be
        p.setObject("felices");  // variant of happy
        output = realiser.realiseSentence(p);
        assertEquals("El perro es feliz.", output); //corrected automatically
    }

	/* Following code tests the section 5 to 15
     * sections 5 & 6 are repeated here in order to match the simplenlg tutorial version 4
	 * James Christie
	 * June 2011
	 */

    /**
     * test section 5 to match simplenlg tutorial version 4's code
     */
    @Test
    public void section5A_Test() {
        Lexicon lexicon = new XMLLexicon();      // default simplenlg lexicon
        NLGFactory nlgFactory = new NLGFactory(lexicon);  // factory based on lexicon
        Realiser realiser = new Realiser(lexicon);

        SPhraseSpec p = nlgFactory.createClause();
        p.setSubject("Mary");
        p.setVerb("perseguir");
        p.setObject("el mico");

        String output = realiser.realiseSentence(p);
        assertEquals("Mary persegueix el mico.", output);
    } // testSection5A

    /**
     * test section 6 to match simplenlg tutorial version 4' code
     */
    @Test
    public void section6A_Test() {
        Lexicon lexicon = new XMLLexicon();    // default simplenlg lexicon
        NLGFactory nlgFactory = new NLGFactory(lexicon);  // factory based on lexicon
        Realiser realiser = new Realiser(lexicon);

        SPhraseSpec p = nlgFactory.createClause();
        p.setSubject("Mary");
        p.setVerb("perseguir");
        p.setObject(nlgFactory.createPrepositionPhrase(nlgFactory.createNounPhrase("el", "mico")));

        p.setFeature(Feature.TENSE, Tense.PAST);
        String output = realiser.realiseSentence(p);
        assertEquals("Mary perseguí el mico.", output);

        p.setFeature(Feature.TENSE, Tense.FUTURE);
        output = realiser.realiseSentence(p);
        assertEquals("Mary perseguirà el mico.", output);

        p.setFeature(Feature.NEGATED, true);
        output = realiser.realiseSentence(p);
        assertEquals("Mary no perseguirà el mico.", output);

        p = nlgFactory.createClause();
        p.setSubject("Mary");
        p.setVerb("perseguir");
        p.setObject(nlgFactory.createPrepositionPhrase(nlgFactory.createNounPhrase("el", "mico")));

        p.setFeature(Feature.INTERROGATIVE_TYPE, InterrogativeType.YES_NO);
        output = realiser.realiseSentence(p);
        assertEquals("¿Persegueix Mary el mico?", output);

        p.setSubject("Mary");
        p.setVerb("perseguir");
        p.setFeature(Feature.INTERROGATIVE_TYPE, InterrogativeType.WHO_OBJECT);
        output = realiser.realiseSentence(p);
        assertEquals("¿A qui persegueix Mary?", output);
    }

    /**
     * test section 7 code
     */
    @Test
    public void section7_Test() {
        Lexicon lexicon = new XMLLexicon();      // default simplenlg lexicon
        NLGFactory nlgFactory = new NLGFactory(lexicon);  // factory based on lexicon
        Realiser realiser = new Realiser(lexicon);

        SPhraseSpec p = nlgFactory.createClause();
        p.setSubject("Mary");
        p.setVerb("perseguir");
        p.setObject("el mico");
        p.addComplement("molt ràpid");
        p.addComplement("malgrat el seu agotament");

        String output = realiser.realiseSentence(p);
        assertEquals("Mary persegueix el mico molt ràpid malgrat el seu agotament.", output);
    }

    /**
     * test section 8 code
     */
    @Test
    public void section8_Test() {
        Lexicon lexicon = new XMLLexicon();      // default simplenlg lexicon
        NLGFactory nlgFactory = new NLGFactory(lexicon);  // factory based on lexicon
        Realiser realiser = new Realiser(lexicon);

        NPPhraseSpec subject = nlgFactory.createNounPhrase("Mary");
        NPPhraseSpec object = nlgFactory.createNounPhrase("el mico");
        VPPhraseSpec verb = nlgFactory.createVerbPhrase("perseguir");
        subject.addModifier("ràpid");
        subject.setFeature(LexicalFeature.GENDER, Gender.FEMININE);

        SPhraseSpec p = nlgFactory.createClause();
        p.setSubject(subject);
        p.setVerb(verb);
        p.setObject(object);

        String outputA = realiser.realiseSentence(p);
        assertEquals("Ràpida Mary persegueix el mico.", outputA);

        verb.addModifier("ràpidament");

        String outputB = realiser.realiseSentence(p);
        assertEquals("Ràpida Mary persegueix ràpidament el mico.", outputB);
    }

    // there is no code specified in section 9

    /**
     * test section 10 code
     */
    @Test
    public void section10_Test() {
        Lexicon lexicon = new XMLLexicon();      // default simplenlg lexicon
        NLGFactory nlgFactory = new NLGFactory(lexicon);  // factory based on lexicon
        Realiser realiser = new Realiser(lexicon);

        NPPhraseSpec subject1 = nlgFactory.createNounPhrase("Mary");
        NPPhraseSpec subject2 = nlgFactory.createNounPhrase("la teva", "girafa");

        // next line is not correct ~ should be nlgFactory.createCoordinatedPhrase ~ may be corrected in the API
        CoordinatedPhraseElement subj = nlgFactory.createCoordinatedPhrase(subject1, subject2);

        VPPhraseSpec verb = nlgFactory.createVerbPhrase("perseguir");

        SPhraseSpec p = nlgFactory.createClause();
        p.setSubject(subj);
        p.setVerb(verb);
        p.setObject("el mico");

        String outputA = realiser.realiseSentence(p);
        assertEquals("Mary i la teva girafa persegueixen el mico.", outputA);

        NPPhraseSpec object1 = nlgFactory.createNounPhrase("el mico");
        NPPhraseSpec object2 = nlgFactory.createNounPhrase("George");

        // next line is not correct ~ should be nlgFactory.createCoordinatedPhrase ~ may be corrected in the API
        CoordinatedPhraseElement obj = nlgFactory.createCoordinatedPhrase(object1, object2);
        obj.addCoordinate("Martha");
        p.setObject(obj);

        String outputB = realiser.realiseSentence(p);
        assertEquals("Mary i la teva girafa persegueixen el mico, George i Martha.", outputB);

        obj.setFeature(Feature.CONJUNCTION, "o");

        String outputC = realiser.realiseSentence(p);
        assertEquals("Mary i la teva girafa persegueixen el mico, George o Martha.", outputC);
    }

    /**
     * test section 11 code
     */
    @Test
    public void section11_Test() {
        Lexicon lexicon = new XMLLexicon();     // default simplenlg lexicon
        NLGFactory nlgFactory = new NLGFactory(lexicon);  // factory based on lexicon

        Realiser realiser = new Realiser(lexicon);

        SPhraseSpec pA = nlgFactory.createClause("Mary", "perseguir", "el mico");
        pA.addComplement("al parc");

        String outputA = realiser.realiseSentence(pA);
        assertEquals("Mary persegueix el mico al parc.", outputA);

        // alternative build paradigm
        NPPhraseSpec place = nlgFactory.createNounPhrase("parc");
        SPhraseSpec pB = nlgFactory.createClause("Mary", "perseguir", "el mico");

        // next line is depreciated ~ may be corrected in the API
        place.setDeterminer("el");
        PPPhraseSpec pp = nlgFactory.createPrepositionPhrase();
        pp.addComplement(place);
        pp.setPreposition("en");

        pB.addComplement(pp);

        String outputB = realiser.realiseSentence(pB);
        assertEquals("Mary persigue el mono en el parque.", outputB);

        place.addPreModifier("frondoso");

        String outputC = realiser.realiseSentence(pB);
        assertEquals("Mary persigue el mono en el frondoso parque.", outputC);
    } // testSection11

    // section12 only has a code table as illustration

    /**
     * test section 13 code
     */
    @Test
    public void section13_Test() {
        Lexicon lexicon = new XMLLexicon();     // default simplenlg lexicon
        NLGFactory nlgFactory = new NLGFactory(lexicon);  // factory based on lexicon

        Realiser realiser = new Realiser(lexicon);

        SPhraseSpec s1 = nlgFactory.createClause("el meu gat", "voler", "peix");
        SPhraseSpec s2 = nlgFactory.createClause("el meu gos", "voler", "óssos grans");
        SPhraseSpec s3 = nlgFactory.createClause("el meu cavall", "voler", "herba");

        CoordinatedPhraseElement c = nlgFactory.createCoordinatedPhrase();
        c.addCoordinate(s1);
        c.addCoordinate(s2);
        c.addCoordinate(s3);

        String outputA = realiser.realiseSentence(c);
        assertEquals("El meu gat vol peix, el meu gos vol óssos grans i el meu cavall vol herba.", outputA);

        SPhraseSpec p = nlgFactory.createClause("Jo", "ser", "feliç");
        SPhraseSpec q = nlgFactory.createClause("Jo", "menjar", "peix");
        q.setFeature(Feature.COMPLEMENTISER, "perque");
        q.setFeature(Feature.TENSE, Tense.PAST);
        q.getSubject().setFeature(Feature.ELIDED, true);
        p.addComplement(q);

        String outputB = realiser.realiseSentence(p);
        assertEquals("Jo soc feliç perque vaig menjar peix.", outputB);
    }

    /**
     * test section 14 code
     */
    @Test
    public void section14_Test() {
        Lexicon lexicon = new XMLLexicon();     // default simplenlg lexicon
        NLGFactory nlgFactory = new NLGFactory(lexicon);  // factory based on lexicon

        Realiser realiser = new Realiser(lexicon);

        SPhraseSpec p1 = nlgFactory.createClause("Mary", "perseguir", "el mico");
        SPhraseSpec p2 = nlgFactory.createClause("El mico", "colpeja");
        SPhraseSpec p3 = nlgFactory.createClause("Mary", "estar", "nerviosa");

        DocumentElement s1 = nlgFactory.createSentence(p1);
        DocumentElement s2 = nlgFactory.createSentence(p2);
        DocumentElement s3 = nlgFactory.createSentence(p3);

        DocumentElement par1 = nlgFactory.createParagraph(Arrays.asList(s1, s2, s3));

        String output14a = realiser.realise(par1).getRealisation();
        assertEquals("Mary persegueix el mico. El mico colpeja. Mary està nerviosa.\n\n", output14a);

        DocumentElement section = nlgFactory.createSection("Les proves i tribulacions de Maria i el mico");
        section.addComponent(par1);
        String output14b = realiser.realise(section).getRealisation();
        assertEquals("Les proves i tribulacions de Maria i el mico\nMary persegueix el mico. El mico colpeja. Mary està nerviosa.\n\n", output14b);
    }

} 
