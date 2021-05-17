package org.openjfx;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.language.DoubleMetaphone;
import org.apache.commons.codec.language.Metaphone;
import org.apache.commons.codec.language.Nysiis;
import org.apache.commons.codec.language.Soundex;
import org.apache.commons.codec.language.bm.BeiderMorseEncoder;

public class PhoneticAlgorithms {

        private Soundex soundex;
        private BeiderMorseEncoder morse;
        private Metaphone metaphone;
        private DoubleMetaphone doubleMetaphone;
        private Nysiis nysiis;

        public PhoneticAlgorithms() {
                soundex = new Soundex();
                morse = new BeiderMorseEncoder();
                metaphone = new Metaphone();
                doubleMetaphone = new DoubleMetaphone();
                nysiis = new Nysiis();
        }

        public String encodeSoundex(String word) {
                return soundex.encode(word);
        }

        public String encodeMorse(String word) {
                try {
                        return morse.encode(word);
                }catch (EncoderException e) {
                        e.getCause();
                }

                return null;
        }

        public String encodeMetaphone(String word) {
                return metaphone.encode(word);
        }

        public String encodeDoubleMetaphone(String word) {
                return doubleMetaphone.encode(word);
        }

        public String encodeNysiis(String word) {
                return nysiis.encode(word);
        }
}
