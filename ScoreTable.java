/** 
   This class has information about Scrabble scores for different scrabble letters and words.
   Letters that occur more frequently in English language are worth more points than those that occur
   less often. Letter values assigned are :
   
   (1 point)-A, E, I, O, U, L, N, S, T, R
   (2 points)-D, G
   (3 points)-B, C, M, P
   (4 points)-F, H, V, W, Y
   (5 points)-K
   (8 points)- J, X
   (10 points)-Q, Z
   
   Works for both upper and lower case versions of the letters, e.g., 'a' and 'A' will have the same score.
   
*/

public class ScoreTable {
   
   /* An array of integers holding hard-coded scores for different letters. An index 'i' stores score for letter 
      whose internal numeric code differs from that of letter 'a' by value = i.
      So score [2] is the score of letter for which i = [letter - 'a'] = 2. So this letter is 'c' or 'C'. */
      
   private static int[] score = {1,3,3,2,1,4,2,4,1,8,5,1,3,1,1,3,10,1,1,1,1,4,4,8,4,10};
   
   /** 
      Returns score for a letter. Both upper and lower case versions of the letters, e.g., 'a' 
      and 'A' will have the same score. 
      @Param : c - Received letter whose score is to be returned
      Returns score for the letter. 
   
   */

   public static int letterScore(char c) {                                                // Total no. of words : 2
      
      // Received character converted to lower case, so that same score is assigned irrespective of case.
      char letter= Character.toLowerCase(c);
      
      // Returns score of a letter with respect to letter 'a'. 
      return score[letter - 'a'];
      
   }
   
}