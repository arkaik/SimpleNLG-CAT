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
 * Contributor(s): Ehud Reiter, Albert Gatt, Dave Wewstwater, Roman Kutlak, Margaret Mitchell.
 */

package simplenlg.syntax.catalan;

import junit.framework.Assert;
import org.junit.After;
import org.junit.Test;
import simplenlg.features.*;
import simplenlg.framework.CoordinatedPhraseElement;
import simplenlg.framework.LexicalCategory;
import simplenlg.framework.NLGElement;
import simplenlg.framework.PhraseElement;
import simplenlg.phrasespec.NPPhraseSpec;
import simplenlg.phrasespec.PPPhraseSpec;
import simplenlg.phrasespec.SPhraseSpec;

/**
 * Tests for the NPPhraseSpec and CoordinateNPPhraseSpec classes.
 *
 * @author agatt
 */
public class NounPhraseTest extends SimpleNLG4Test {

    /**
     * Instantiates a new nP test.
     *
     * @param name the name
     */
    public NounPhraseTest(String name) {
        super(name);
    }

    @Override
    @After
    public void tearDown() {
        super.tearDown();
    }


    /**
     * Test the setPlural() method in noun phrases.
     */
    @Test
    public void testPlural() {
        this.np4.setFeature(Feature.NUMBER, NumberAgreement.PLURAL);
        Assert.assertEquals(
                "les roques", this.realiser.realise(this.np4).getRealisation()); //$NON-NLS-1$

        this.np5.setFeature(Feature.NUMBER, NumberAgreement.PLURAL);
        Assert
                .assertEquals(
                        "les cortines", this.realiser.realise(this.np5).getRealisation()); //$NON-NLS-1$

        this.np5.setFeature(Feature.NUMBER, NumberAgreement.SINGULAR);
        Assert.assertEquals(NumberAgreement.SINGULAR, this.np5
                .getFeature(Feature.NUMBER));
        Assert
                .assertEquals(
                        "la cortina", this.realiser.realise(this.np5).getRealisation()); //$NON-NLS-1$

        this.np5.setFeature(Feature.NUMBER, NumberAgreement.PLURAL);
        Assert
                .assertEquals(
                        "les cortines", this.realiser.realise(this.np5).getRealisation()); //$NON-NLS-1$
    }

    /**
     * Test the pronominalisation method for full NPs.
     */
    @Test
    public void testPronominalisation() {
        // sing
        this.proTest1.setFeature(LexicalFeature.GENDER, Gender.FEMININE);
        this.proTest1.setFeature(Feature.PRONOMINAL, true);
        Assert.assertEquals(
                "ella", this.realiser.realise(this.proTest1).getRealisation()); //$NON-NLS-1$

        // sing, possessive
        this.proTest1.setFeature(Feature.POSSESSIVE, true);
        Assert.assertEquals(
                "seu", this.realiser.realise(this.proTest1).getRealisation()); //$NON-NLS-1$

        // plural pronoun
        this.proTest2.setFeature(Feature.NUMBER, NumberAgreement.PLURAL);
        this.proTest2.setFeature(Feature.PRONOMINAL, true);
        Assert.assertEquals(
                "elles", this.realiser.realise(this.proTest2).getRealisation()); //$NON-NLS-1$

        // accusative: "them"
        this.proTest2.setFeature(InternalFeature.DISCOURSE_FUNCTION,
                DiscourseFunction.OBJECT);
        Assert.assertEquals(
                "les", this.realiser.realise(this.proTest2).getRealisation()); //$NON-NLS-1$
    }

    /**
     * Test the pronominalisation method for full NPs (more thorough than above)
     */
    @Test
    public void testPronominalisation2() {
        // Ehud - added extra pronominalisation tests
        NPPhraseSpec pro = phraseFactory.createNounPhrase("Mary");
        pro.setFeature(Feature.PRONOMINAL, true);
        pro.setFeature(Feature.PERSON, Person.FIRST);
        SPhraseSpec sent = phraseFactory.createClause(pro, "estimar", phraseFactory.createPrepositionPhrase("al", "John"));
        Assert
                .assertEquals("Jo estimo al John.", this.realiser
                        .realiseSentence(sent));

        pro = phraseFactory.createNounPhrase("Mary");
        pro.setFeature(Feature.PRONOMINAL, true);
        pro.setFeature(Feature.PERSON, Person.SECOND);
        sent = phraseFactory.createClause(pro, "estimar", phraseFactory.createPrepositionPhrase("al", "John"));
        Assert.assertEquals("Tu estimes al John.", this.realiser
                .realiseSentence(sent));

        pro = phraseFactory.createNounPhrase("Mary");
        pro.setFeature(Feature.PRONOMINAL, true);
        pro.setFeature(Feature.PERSON, Person.THIRD);
        pro.setFeature(LexicalFeature.GENDER, Gender.FEMININE);
        sent = phraseFactory.createClause(pro, "estimar", phraseFactory.createPrepositionPhrase("al", "John"));
        Assert.assertEquals("Ella estima al John.", this.realiser
                .realiseSentence(sent));

        pro = phraseFactory.createNounPhrase("Mary");
        pro.setFeature(Feature.PRONOMINAL, true);
        pro.setFeature(Feature.PERSON, Person.FIRST);
        pro.setPlural(true);
        sent = phraseFactory.createClause(pro, "estimar", phraseFactory.createPrepositionPhrase("al", "John"));
        Assert.assertEquals("Nosaltres estimem al John.", this.realiser
                .realiseSentence(sent));

        pro = phraseFactory.createNounPhrase("Mary");
        pro.setFeature(Feature.PRONOMINAL, true);
        pro.setFeature(Feature.PERSON, Person.SECOND);
        pro.setPlural(true);
        sent = phraseFactory.createClause(pro, "estimar", phraseFactory.createPrepositionPhrase("al", "John"));
        Assert.assertEquals("Vosaltres estimeu al John.", this.realiser
                .realiseSentence(sent));

        pro = phraseFactory.createNounPhrase("Mary");
        pro.setFeature(Feature.PRONOMINAL, true);
        pro.setFeature(Feature.PERSON, Person.THIRD);
        pro.setPlural(true);
        pro.setFeature(LexicalFeature.GENDER, Gender.FEMININE);
        sent = phraseFactory.createClause(pro, "estimar", phraseFactory.createPrepositionPhrase("al", "John"));
        Assert.assertEquals("Elles estimen al John.", this.realiser
                .realiseSentence(sent));

        pro = phraseFactory.createNounPhrase("John");
        pro.setFeature(Feature.PRONOMINAL, true);
        pro.setFeature(Feature.PERSON, Person.FIRST);
        sent = phraseFactory.createClause("La Mary", "estimar", pro);
        Assert.assertEquals("La Mary m'estima.", this.realiser
                .realiseSentence(sent));

        pro = phraseFactory.createNounPhrase("John");
        pro.setFeature(Feature.PRONOMINAL, true);
        pro.setFeature(Feature.PERSON, Person.SECOND);
        sent = phraseFactory.createClause("La Mary", "estimar", pro);
        Assert.assertEquals("La Mary t'estima.", this.realiser
                .realiseSentence(sent));

        pro = phraseFactory.createNounPhrase("John");
        pro.setFeature(Feature.PRONOMINAL, true);
        pro.setFeature(Feature.PERSON, Person.THIRD);
        pro.setFeature(LexicalFeature.GENDER, Gender.MASCULINE);
        sent = phraseFactory.createClause("Mary", "estimar", pro);
        Assert.assertEquals("Mary l'estima.", this.realiser
                .realiseSentence(sent));

        pro = phraseFactory.createNounPhrase("John");
        pro.setFeature(Feature.PRONOMINAL, true);
        pro.setFeature(Feature.PERSON, Person.FIRST);
        pro.setPlural(true);
        sent = phraseFactory.createClause("Mary", "estimar", pro);
        Assert.assertEquals("Mary ens estima.", this.realiser
                .realiseSentence(sent));

        pro = phraseFactory.createNounPhrase("John");
        pro.setFeature(Feature.PRONOMINAL, true);
        pro.setFeature(Feature.PERSON, Person.SECOND);
        pro.setPlural(true);
        sent = phraseFactory.createClause("Mary", "estimar", pro);
        Assert.assertEquals("Mary us estima.", this.realiser
                .realiseSentence(sent));

        pro = phraseFactory.createNounPhrase("John");
        pro.setFeature(Feature.PRONOMINAL, true);
        pro.setFeature(Feature.PERSON, Person.THIRD);
        pro.setFeature(LexicalFeature.GENDER, Gender.MASCULINE);
        pro.setPlural(true);
        sent = phraseFactory.createClause("Mary", "estimar", pro);
        Assert.assertEquals("Mary els estima.", this.realiser
                .realiseSentence(sent));
    }

    /**
     * Test premodification in NPS.
     */
    @Test
    public void testPremodification() {
        this.man.addPreModifier(this.salacious);
        Assert.assertEquals("el salaç home", this.realiser //$NON-NLS-1$
                .realise(this.man).getRealisation());

        this.woman.addPreModifier(this.beautiful);
        Assert.assertEquals("la bonica dona", this.realiser.realise( //$NON-NLS-1$
                this.woman).getRealisation());

        this.dog.addPreModifier(this.stunning);
        Assert.assertEquals("el fantàstic gos", this.realiser.realise(this.dog) //$NON-NLS-1$
                .getRealisation());

        // premodification with a WordElement
        this.man.setPreModifier(this.phraseFactory.createWord("idiota",
                LexicalCategory.ADJECTIVE));
        Assert.assertEquals("l'idiota home", this.realiser //$NON-NLS-1$
                .realise(this.man).getRealisation());

    }

    /**
     * Test prepositional postmodification.
     */
    @Test
    public void testPostmodification() {
        this.man.addPostModifier(this.onTheRock);
        Assert.assertEquals("el hombre en la roca", this.realiser.realise( //$NON-NLS-1$
                this.man).getRealisation());

        this.woman.addPostModifier(this.behindTheCurtain);
        Assert.assertEquals("la mujer tras la cortina", this.realiser //$NON-NLS-1$
                .realise(this.woman).getRealisation());

        // postmodification with a WordElement
        this.man.setPostModifier(this.phraseFactory.createWord("jack",
                LexicalCategory.NOUN));
        Assert.assertEquals("el hombre jack", this.realiser.realise( //$NON-NLS-1$
                this.man).getRealisation());
    }

    /**
     * Test nominal complementation
     */
    @Test
    public void testComplementation() {
        // complementation with a WordElement
        this.man.setComplement(this.phraseFactory.createWord("jack",
                LexicalCategory.NOUN));
        Assert.assertEquals("el hombre jack", this.realiser.realise( //$NON-NLS-1$
                this.man).getRealisation());

        this.woman.addComplement(this.behindTheCurtain);
        Assert.assertEquals("la mujer tras la cortina", this.realiser //$NON-NLS-1$
                .realise(this.woman).getRealisation());
    }

    /**
     * Test possessive constructions.
     */
//    @Test
//    public void testPossessive() {
//
//        // simple possessive 's: 'a man's'
//        PhraseElement possNP = this.phraseFactory.createNounPhrase("a", "man"); //$NON-NLS-1$ //$NON-NLS-2$
//        possNP.setFeature(Feature.POSSESSIVE, true);
//        Assert.assertEquals("a man's", this.realiser.realise(possNP) //$NON-NLS-1$
//                .getRealisation());
//
//        // now set this possessive as specifier of the NP 'the dog'
//        this.dog.setFeature(InternalFeature.SPECIFIER, possNP);
//        Assert.assertEquals("a man's dog", this.realiser.realise(this.dog) //$NON-NLS-1$
//                .getRealisation());
//
//        // convert possNP to pronoun and turn "a dog" into "his dog"
//        // need to specify gender, as default is NEUTER
//        possNP.setFeature(LexicalFeature.GENDER, Gender.MASCULINE);
//        possNP.setFeature(Feature.PRONOMINAL, true);
//        Assert.assertEquals("his dog", this.realiser.realise(this.dog) //$NON-NLS-1$
//                .getRealisation());
//
//        // make it slightly more complicated: "his dog's rock"
//        this.dog.setFeature(Feature.POSSESSIVE, true); // his dog's
//
//        // his dog's rock (substituting "the"
//        // for the
//        // entire phrase)
//        this.np4.setFeature(InternalFeature.SPECIFIER, this.dog);
//        Assert.assertEquals("his dog's rock", this.realiser.realise(this.np4) //$NON-NLS-1$
//                .getRealisation());
//    }

    /**
     * Test NP coordination.
     */
    @Test
    public void testCoordination() {

        CoordinatedPhraseElement cnp1 = phraseFactory.createCoordinatedPhrase(this.dog,
                this.woman);
        // simple coordination
        Assert.assertEquals("el perro y la mujer", this.realiser //$NON-NLS-1$
                .realise(cnp1).getRealisation());

        // simple coordination with complementation of entire coordinate NP
        cnp1.addComplement(this.behindTheCurtain);
        Assert.assertEquals("el perro y la mujer tras la cortina", //$NON-NLS-1$
                this.realiser.realise(cnp1).getRealisation());

        // raise the specifier in this cnp
        // Assert.assertEquals(true, cnp1.raiseSpecifier()); // should return
        // true as all
        // sub-nps have same spec
        // assertEquals("the dog and woman behind the curtain",
        // realiser.realise(cnp1));
    }

    /**
     * Another battery of tests for NP coordination.
     */
    @Test
    public void testCoordination2() {

        // simple coordination of complementised nps
        this.dog.clearComplements();
        this.woman.clearComplements();

        CoordinatedPhraseElement cnp1 = phraseFactory.createCoordinatedPhrase(this.dog,
                this.woman);
        cnp1.setFeature(Feature.RAISE_SPECIFIER, true);
        NLGElement realised = this.realiser.realise(cnp1);
        Assert.assertEquals("el perro y mujer", realised.getRealisation());

        this.dog.addComplement(this.onTheRock);
        this.woman.addComplement(this.behindTheCurtain);

        CoordinatedPhraseElement cnp2 = phraseFactory.createCoordinatedPhrase(this.dog,
                this.woman);

        this.woman.setFeature(InternalFeature.RAISED, false);
        Assert.assertEquals(
                "el perro en la roca y la mujer tras la cortina", //$NON-NLS-1$
                this.realiser.realise(cnp2).getRealisation());

        // complementised coordinates + outer pp modifier
        cnp2.addPostModifier(this.inTheRoom);
        Assert
                .assertEquals(
                        "el perro en la roca y la mujer tras la cortina en la habitación", //$NON-NLS-1$
                        this.realiser.realise(cnp2).getRealisation());

        // set the specifier for this cnp; should unset specifiers for all inner
        // coordinates
        NLGElement every = this.phraseFactory.createWord(
                "todo", LexicalCategory.DETERMINER); //$NON-NLS-1$

        cnp2.setFeature(InternalFeature.SPECIFIER, every);

        Assert
                .assertEquals(
                        "todo perro en la roca y toda mujer tras la cortina en la habitación", //$NON-NLS-1$
                        this.realiser.realise(cnp2).getRealisation());

        // pronominalise one of the constituents
        this.dog.setFeature(Feature.PRONOMINAL, true); // ="it"
        this.dog.setFeature(InternalFeature.SPECIFIER, this.phraseFactory
                .createWord("el", LexicalCategory.DETERMINER));
        // raising spec still returns true as spec has been set
        cnp2.setFeature(Feature.RAISE_SPECIFIER, true);

        // CNP should be realised with pronominal internal const
        Assert.assertEquals(
                "él y toda mujer tras la cortina en la habitación", //$NON-NLS-1$
                this.realiser.realise(cnp2).getRealisation());
    }

    /**
     * Test possessives in coordinate NPs.
     */
    @Test
    public void testPossessiveCoordinate() {
        // simple coordination
        CoordinatedPhraseElement cnp2 = phraseFactory.createCoordinatedPhrase(this.dog,
                this.woman);
        Assert.assertEquals("el gos i la dona", this.realiser //$NON-NLS-1$
                .realise(cnp2).getRealisation());

//        // set possessive -- wide-scope by default
//        cnp2.setFeature(Feature.POSSESSIVE, true);
//        Assert.assertEquals("el perro y la mujer", this.realiser.realise( //$NON-NLS-1$
//                cnp2).getRealisation());
//
//        // set possessive with pronoun
//        this.dog.setFeature(Feature.PRONOMINAL, true);
//        this.dog.setFeature(Feature.POSSESSIVE, true);
//        cnp2.setFeature(Feature.POSSESSIVE, true);
//        Assert.assertEquals("its and the woman's", this.realiser.realise(cnp2) //$NON-NLS-1$
//                .getRealisation());

    }

    /**
     * Test A vs An.
     */
    @Test
    public void testAAn() {
        PhraseElement _dog = this.phraseFactory.createNounPhrase("un", "gos"); //$NON-NLS-1$ //$NON-NLS-2$
        Assert.assertEquals("un gos", this.realiser.realise(_dog) //$NON-NLS-1$
                .getRealisation());

        _dog.addPreModifier(phraseFactory.createWord("enorme", LexicalCategory.ADJECTIVE)); //$NON-NLS-1$

        Assert.assertEquals("un enorme gos", this.realiser.realise(_dog) //$NON-NLS-1$
                .getRealisation());

        PhraseElement elephant = this.phraseFactory.createNounPhrase(
                "un", "elefante"); //$NON-NLS-1$ //$NON-NLS-2$
        Assert.assertEquals("un elefant", this.realiser.realise(elephant) //$NON-NLS-1$
                .getRealisation());

        elephant.addPreModifier("gran"); //$NON-NLS-1$
        Assert.assertEquals("un gran elefant", this.realiser.realise(elephant) //$NON-NLS-1$
                .getRealisation());

        // test treating of plural specifiers
        _dog.setFeature(Feature.NUMBER, NumberAgreement.PLURAL);

        Assert.assertEquals("uns enormes gossos", this.realiser.realise(_dog) //$NON-NLS-1$
                .getRealisation());
    }

    /**
     * Further tests for a/an agreement with coordinated premodifiers
     */
    public void testAAnCoord() {
        NPPhraseSpec _dog = this.phraseFactory.createNounPhrase("un", "gos");
        _dog.addPreModifier(this.phraseFactory.createCoordinatedPhrase(
                "enorme", "negro"));
        String realisation = this.realiser.realise(_dog).getRealisation();
        Assert.assertEquals("un enorme i negre perro", realisation);
    }

    /**
     * Test for a/an agreement with numbers
     */
    public void testAAnWithNumbers() {
        NPPhraseSpec num = this.phraseFactory.createNounPhrase("un", "canvi");
        String realisation;

        // no an with "one"
        num.setPostModifier("d'un u per cent");
        realisation = this.realiser.realise(num).getRealisation();
        Assert.assertEquals("un canvi d'un uno per cent", realisation);

        // an with "eighty"
        num.setPostModifier("de un ochenta por ciento");
        realisation = this.realiser.realise(num).getRealisation();
        Assert.assertEquals("un cambio de un ochenta por ciento", realisation);

        // an with 80
        num.setPostModifier("de un 80%");
        realisation = this.realiser.realise(num).getRealisation();
        Assert.assertEquals("un cambio de un 80%", realisation);

        // an with 80000
        num.setPostModifier("de 80000");
        realisation = this.realiser.realise(num).getRealisation();
        Assert.assertEquals("un cambio de 80000", realisation);

        // an with 11,000
        num.setPostModifier("de 11,000");
        realisation = this.realiser.realise(num).getRealisation();
        Assert.assertEquals("un cambio de 11,000", realisation);

        // an with 18
        num.setPostModifier("de un 18%");
        realisation = this.realiser.realise(num).getRealisation();
        Assert.assertEquals("un cambio de un 18%", realisation);

        // a with 180
        num.setPostModifier("de 180");
        realisation = this.realiser.realise(num).getRealisation();
        Assert.assertEquals("un cambio de 180", realisation);

        // a with 1100
        num.setPostModifier("de 1100");
        realisation = this.realiser.realise(num).getRealisation();
        Assert.assertEquals("un cambio de 1100", realisation);

        // a with 180,000
        num.setPostModifier("de 180,000");
        realisation = this.realiser.realise(num).getRealisation();
        Assert.assertEquals("un cambio de 180,000", realisation);

        // an with 11000
        num.setPostModifier("de 11000");
        realisation = this.realiser.realise(num).getRealisation();
        Assert.assertEquals("un cambio de 11000", realisation);

        // an with 18000
        num.setPostModifier("de 18000");
        realisation = this.realiser.realise(num).getRealisation();
        Assert.assertEquals("un cambio de 18000", realisation);

        // an with 18.1
        num.setPostModifier("de un 18.1%");
        realisation = this.realiser.realise(num).getRealisation();
        Assert.assertEquals("un cambio de un 18.1%", realisation);

        // an with 11.1
        num.setPostModifier("de un 11.1%");
        realisation = this.realiser.realise(num).getRealisation();
        Assert.assertEquals("un cambio de un 11.1%", realisation);

    }

    /**
     * Test Modifier "guess" placement.
     */
    @Test
    public void testModifier() {
        PhraseElement _dog = this.phraseFactory.createNounPhrase("un", "perro"); //$NON-NLS-1$ //$NON-NLS-2$
        _dog.addPostModifier("enfadado"); //$NON-NLS-1$

        Assert.assertEquals("un perro enfadado", this.realiser.realise(_dog) //$NON-NLS-1$
                .getRealisation());

        _dog.addPostModifier("en el parque"); //$NON-NLS-1$
        Assert.assertEquals("un perro enfadado en el parque", this.realiser.realise( //$NON-NLS-1$
                _dog).getRealisation());

        PhraseElement cat = this.phraseFactory.createNounPhrase("un", "gat"); //$NON-NLS-1$ //$NON-NLS-2$
        cat.addPostModifier(this.phraseFactory.createAdjectivePhrase("enfadat")); //$NON-NLS-1$
        Assert.assertEquals("un gato enfadado", this.realiser.realise(cat) //$NON-NLS-1$
                .getRealisation());

        cat.addPostModifier(this.phraseFactory.createPrepositionPhrase(
                "en", "el parque")); //$NON-NLS-1$ //$NON-NLS-2$
        Assert.assertEquals("un gato enfadado en el parque", this.realiser.realise( //$NON-NLS-1$
                cat).getRealisation());

    }

    @Test
    public void testPluralNounsBelongingToASingular() {

        SPhraseSpec sent = this.phraseFactory.createClause("jo", "comptar");
        sent.setFeature(Feature.TENSE, Tense.PAST);
        NPPhraseSpec obj = this.phraseFactory.createNounPhrase("el", "dígit");
        obj.setPlural(true);
        PPPhraseSpec possessor = this.phraseFactory.createPrepositionPhrase("de", this.phraseFactory.createNounPhrase("la", "caixa"));
        possessor.setPlural(false);
//        possessor.setFeature(Feature.POSSESSIVE, true);
        obj.setPostModifier(possessor);
        sent.setObject(obj);

        Assert.assertEquals("yo conté los dígitos de la caja", this.realiser.realise(sent) //$NON-NLS-1$
                .getRealisation());
    }


    @Test
    public void testSingularNounsBelongingToAPlural() {

        SPhraseSpec sent = this.phraseFactory.createClause("yo", "netejar");
        sent.setFeature(Feature.TENSE, Tense.PAST);
        NPPhraseSpec obj = this.phraseFactory.createNounPhrase("el", "cotxe");
        obj.setPlural(false);
        PPPhraseSpec possessor = this.phraseFactory.createPrepositionPhrase("de", this.phraseFactory.createNounPhrase("el", "padre"));
        possessor.getObject().setPlural(true);
//        possessor.setFeature(Feature.POSSESSIVE, true);
        obj.setPostModifier(possessor);
        sent.setObject(obj);

        Assert.assertEquals("yo limpié el coche dels pares", this.realiser.realise(sent) //$NON-NLS-1$
                .getRealisation());
    }

    /**
     * Test for appositive postmodifiers
     */
    @Test
    public void testAppositivePostmodifier() {
        PhraseElement _dog = this.phraseFactory.createNounPhrase("el", "perro");
        PhraseElement _rott = this.phraseFactory.createNounPhrase("un", "rottweiler");
        _rott.setFeature(Feature.APPOSITIVE, true);
        _dog.addPostModifier(_rott);
        SPhraseSpec _sent = this.phraseFactory.createClause(_dog, "correr");
        Assert.assertEquals("El perro, un rottweiler corre.", this.realiser.realiseSentence(_sent));
    }
}
