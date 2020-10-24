import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;


/**
   A Rack of Scrabble tiles that refers to the current rack.
   This class represents the idea of a rack which is a multiset of letters i.e., set because the ordering 
   of the letters is immaterial and multiset because letters can appear more than once.
   It manages the representation of the rack and creates its subsets to enable anagram and final wordlist
   generation.
   
*/

public class Rack {
   
   /**
      Represents the input rack(multiset of letters) in the form of two parallel arrays as below :
      1) A string of unique letters
      2) An 'int' array storing multiplicity of a letter at the same array index.
      For example : " bbccdde" will be represented as : String unique = "bcde" and int[] mult = {2, 2, 2, 1}
      Calls 'allSubsets' method to return list of all possible subsets of a word / userInput
      
      @Param : word - user input for which we have to find all possible words that can be made from it
      Returns an arraylist of all the possible subsets
   
   */
   
   public static ArrayList<String> multisetRepresentation(String word) {       // Total no. of words : 21
      
      // Arraylist that stores all the subsets of the input word
      ArrayList<String> subsetsList  = new ArrayList<>();
      
      
      /* Map that will have keys as the unique constituent letters of the word and corresponding values as their
         multiplicity. */
      
      Map<Character, Integer> multiset   = new TreeMap<>();
      
      int length = word.length();
      
      // String consisting of unique letters in a word
      String unique ;
      
      /* We fill up the entries in map 'multiset' by reading each letter in word one-by-one and getting its value 
         from map. If the value is not null i.e. , letter has already been encountered in the word, then we increment
         its value or frequency by 1, else set the value to 1 */
      
      for (int i = 0; i < length; i++) {
         
         char letter = word.charAt(i);                                         // Stores the letter at index 'i' in the word / multiset
         Integer oldVal = multiset.get(letter);                                // Stores the value/multiplicity associated with the key/letter
         
         if (oldVal != null) {
            
            multiset.put(letter, oldVal + 1);                                  // Increments value if key encountered before
            
         }
         
         else {
                          
            multiset.put(letter, 1);                                           // Sets value = 1 if key encountered fot the first time
         }
         
      }
                
       
      int i = 0;
      
      char[] letters = new char [length];                                      // Array of char to store keys of the map's entries
      int[] mult = new int [length];                                           // Array of int to store values of the map's entries
      
      for (Map.Entry<Character, Integer> mapEntry : multiset.entrySet()) {
       
         letters[i] = mapEntry.getKey();                                       // Filling up letters from map
         mult[i] = mapEntry.getValue();                                        // Filling up multiplicity of letters from map
         i++;
         
      }
      
      // Creating a string 'unique' from array of char just filled up
      unique = new String(letters);
      
      /* Calls allSubsets method by passing parameters 'unique', 'mult' and '0' (i.e, the starting 
         postion from which to find the subsets for the word / multiset). Stores the returned arraylist of
         subsets in variable  'subsetsList'. */
            
      subsetsList = allSubsets(unique, mult, 0);                          
      
      return subsetsList;
      
   }
   
   

   
   
   /**
      Finds all subsets of the multiset starting at position k in unique and mult.
      unique and mult describe a multiset such that mult[i] is the multiplicity of the char
           unique.charAt(i).
      PRE: mult.length must be at least as big as unique.length()
           0 <= k <= unique.length()
      @param unique a string of unique letters
      @param mult the multiplicity of each letter from unique.  
      @param k the smallest index of unique and mult to consider.
      @return all subsets of the indicated multiset.  Unlike the multiset in the parameters,
      each subset is represented as a String that can have repeated characters in it.
      @author Claire Bono
   */
   
   private static ArrayList<String> allSubsets(String unique, int[] mult, int k) {
      ArrayList<String> allCombos = new ArrayList<>();
      
      if (k == unique.length()) {  // multiset is empty
         allCombos.add("");
         return allCombos;
      }
      
      // get all subsets of the multiset without the first unique char
      ArrayList<String> restCombos = allSubsets(unique, mult, k+1);
      
      // prepend all possible numbers of the first char (i.e., the one at position k) 
      // to the front of each string in restCombos.  Suppose that char is 'a'...
      
      String firstPart = "";          // in outer loop firstPart takes on the values: "", "a", "aa", ...
      for (int n = 0; n <= mult[k]; n++) {   
         for (int i = 0; i < restCombos.size(); i++) {  // for each of the subsets 
                                                        // we found in the recursive call
            // create and add a new string with n 'a's in front of that subset
            allCombos.add(firstPart + restCombos.get(i));  
         }
         firstPart += unique.charAt(k);  // append another instance of 'a' to the first part
      }
      
      return allCombos;
   }

   
}
