package org.openjfx;

import java.util.Random;

public class MistakesGenerator {
        //0 - brak bledu, 1 - usuniecie literki, 2 - zmiana literki, 3 - dodanie literki
//        private int mistakeNumber;
        private Random random;


        public MistakesGenerator() {
                random = new Random();
        }

        public String changeWord(String word) {
                int mistakenumber = random.nextInt(4);

                if(mistakenumber == 1) {
                        return deleteRandomChar(word);
                }else if(mistakenumber == 2) {
                        return changeRandomChar(word);
                }else if(mistakenumber == 3) {
                        return addRandomChar(word);
                }

                return word;
        }

        private String deleteRandomChar(String word) {
                int characterposition = random.nextInt(word.length()-1);

                return word.substring(0, characterposition) + word.substring(characterposition + 1);
        }

        private String changeRandomChar(String word) {
                int characterposition = random.nextInt(word.length()-1);
                char c = (char)(random.nextInt(26)+'a');

                StringBuilder sb = new StringBuilder(word);
                sb.setCharAt(characterposition, c);
                return sb.toString();
        }

        private String addRandomChar(String word) {
                int characterposition = random.nextInt(word.length()-1);
                char c = (char)(random.nextInt(26)+'a');

                StringBuilder sb = new StringBuilder(word);
                sb.insert(characterposition, c);

                return sb.toString();
        }
}
