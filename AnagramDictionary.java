import java.io.FileNotFoundException;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Map;
import java.util.TreeMap;
import java.util.HashMap;
import java.util.Set;
import java.util.Comparator;
import java.util.Collections;


/**
   A dictionary of all anagram sets. 
   Note: the processing is case-sensitive; so if the dictionary has all lower
   case words, you will likely want any string you test to have all lower case
   letters too, and likewise if the dictionary words are all upper case.
*/

public class AnagramDictionary {
   
   // Variable to hold the processed dictionary created from words of the provided dictionary
   Map<String, String> processedDictionary = new HashMap<>();                     
   
   /**
      Create an anagram dictionary from the list of words given in the file
      indicated by fileName.  
      PRE: The strings in the file are unique.
      @param fileName  the name of the file to read from
      @throws FileNotFoundException  if the file is not found
   */
   
   public AnagramDictionary(String fileName) throws FileNotFoundException {                 // Total no. of words : 13

      File file = new File(fileName);                                                       // Creates file instance for the given file
      Scanner in = new Scanner(file);                                                       // Scans the given file instance
     
      /* PREPROCESSING TECHNIQUE USED: 
      
         Reads each word of the Scrabble dictionary, sorts it , creates entries in map 'processedDictionary' where keys
         are the sorted version of the dictionary word, and corresponding values are all the dictionary words that have their
         sorted version same as that key. These words having the same sorted version are separated by a comma (',') and 
         concatinated into a larger string as the list grows. So basically all words that are part of the value of a 
         particular key are anagrams of each other with their sorted version as that key.
         
         Eg : For anagrams: ["leer", "lere", "reel"], when the first word 'leer' is processed, it sorts it to create 'eelr'
         as the key. Then its corresponding value will be string : "leer,lere,reel" which consists of all dictionary 
         words with sorted version as 'eelr' (or the key) */
      
      while (in.hasNext()) {
           
         String word = in.next();
          
         // Creates a corresponding array of char for a dictionary word
         char[] interimCharArray = word.toCharArray();
         
         // Sorts the array of char
         Arrays.sort(interimCharArray);
         
         // Creates a new string 'sortedDictionaryWord' from the sorted char array
         String sortedDictionaryWord = new String(interimCharArray);   
           
         /* If processed dictionary does not contain a sorted word as a key, it means it is being encountered for 
            the first time and hence, no corresponding anagrams discovered so far. So it creates a map entry with key as 
            the sorted word and value as the original word itself. */
         
         if(!processedDictionary.containsKey( sortedDictionaryWord )) {
              
            processedDictionary.put( sortedDictionaryWord,word );
               
         }
           
         /* If processed dictionary already creates a sorted word as key, it means a word with that sorted version is
            already encountered, so the new word is the anagram of that word .Hence, this new word is concatinated to
            the value using a comma (','). At the end all words in a particular value are anagrams of one another */
         
         else {
              
            String anagramSet = processedDictionary.get( sortedDictionaryWord );
            anagramSet = anagramSet + "," + word;
            processedDictionary.put( sortedDictionaryWord,anagramSet );
              
         }
         
      }
      
   }
      

        
           
     
   
       
   /**
      Get all anagrams of the given string. This method is case-sensitive.
      E.g. "CARE" and "race" would not be recognized as anagrams.
      @param s string to process
      @return a list of the anagrams of s
   */
   
   public ArrayList<String> getAnagramsOf(String s) {                                       // Total no. of words : 12
      
      ArrayList<String> anagramList = new ArrayList<>();                                    // Arraylist to hold anagrams of a word
      
      // Creates a corresponding array of char for the given string
      char[] interimCharArray = s.toCharArray();
      
      // Sorts the array of char
      Arrays.sort(interimCharArray);
      
      // Creates a new string 'sorted' from the sorted char array
      String sorted = new String(interimCharArray);                            
      
       // Holds value for an entry of map 'processedDictionary'
      String mapValue;                                                        
      
      /* If sorted version of word is present as a key in map, it creates an array of strings called 'anagrams'
         that has all anagrams for that word after splitting up the concatinated string.
         So string : "leer, lere, reel" is split up into words 'leer', 'lere' and 'reel' and stored in array to 
         give back the anagrams 
         
         Since this array 'anagrams' contains only anagrams of a given word and it is traversed only once to pick each element 
         and append to 'anagramList', hence, all the anagrams of one String are found in time linear in the size of the output set */
      
      if (processedDictionary.containsKey(sorted)) {                                        // Takes O(1) for hashMap
         
         mapValue = processedDictionary.get(sorted);
         
         //Takes 0(N) as it does linear passes through the string mapValue, where N is no. of anagrams separated by ','
         String[] anagrams = mapValue.split(",");                      
        
         if (anagrams.length >= 1) {                                                        // Checks atleast one anagram exists for the given word
            
            for (int i = 0; i < anagrams.length; i ++) {                                    // Takes linear time O(N) in the size of the output set
         
               anagramList.add(anagrams[i]);                                                // Adds anagrams from array to arraylist
               
            }
            
         }
         
      }
     
      return anagramList;                                              
   }
   
   
  
   
   
   /**
      For a given list of subsets for a word, calls 'getAnagramsOf' method for every subset in the 
      list and adds those anagrams to arraylist 'wordsList' that will be displayed.
      WordFinder provides it with the list of subsets returned by class 'Rack'. Hence, fetches all possible 
      words for a given input.
      
      @Param : userInput - word for which we have to find all possible words that can be made fom it
               subsetsList - arraylist of all possible subsets for word 'userInput'
      
   */
   
   public void fetchAllPossibleWords(ArrayList<String> subsetsList, String userInput) {     // Total no. of words : 5
   
      ArrayList<String> wordsList = new ArrayList<>();                                      // Holds all anagrams for all subsets of a word
       
      // For each subset of a word, adds its anagrams to the bigger wordsList that will be displayed finally
      for (String subset : subsetsList) {
        
         if (subset.length() != 0) {                                                        // Filters out empty subsets with no characters like ""
             
            wordsList.addAll(getAnagramsOf(subset));                                        // Appends smaller anagram lists to  'wordsList' 
        
         }
         
      }
      
      // Calls method to finally display all possible words and their scores as the output
      sortAndDisplayAllWords (wordsList, userInput);
      
   }
   
   
   
   
   
   /**
      Displays the appropriate message and the final output on the console/ output file wherein all 
      possible words are listed in decreasing order of their scores and in ascending lexicographic order 
      of the words in case of a tie.
      
      @Param: wordsList - Arraylist of all anagrams for all possible subsets of an input word
              userInput - Word input by user
   */
   
   private void sortAndDisplayAllWords (ArrayList<String> wordsList, String userInput) {    // Total no. of words : 18
      
      int wordScore = 0;                                                                    // Holds score for each word
     
      // Holds no. of words that can be created from a particular word
      int numOfWords = 0;                                                          
      
      // Map that holds words to be displayed as keys and their scores as values 
      Map<String,Integer> scoresAndWords = new TreeMap<>();               
      
      ScoreTable score = new ScoreTable();
      
      /* For each word in received arraylist 'wordsList', score is calculated by adding points for each letter in that word.
         Method 'letterScore' of class 'ScoreTable' is called for this purpose. */
      
      for (String word : wordsList) {
         
         wordScore = 0;
         numOfWords ++;                                                                     // Increment no. of words formed
         
         for (int i = 0; i < word.length(); i++) {
            
            wordScore += score.letterScore(word.charAt(i));
            
         }
           
         scoresAndWords.put(word,wordScore);                                                // Word inserted as key and its score as the value
         
      }
       
      // Display no. of words that can be formed
      System.out.println("We can make " + numOfWords + " words from \""+ userInput + "\"");
      
      // Included message displayed only if no. of words formed is > 0
      if (numOfWords !=0) {                                                        
         
         System.out.println("All of the words with their scores (sorted by score):");
         
      }
      
      //Set that contains entrySet of map scoresAndWords
      Set<Map.Entry<String, Integer>> entry = scoresAndWords.entrySet();
      
      // Arraylist initialized by calling set 'entry' that contains entrySet of the map
      ArrayList<Map.Entry<String, Integer>> listOfEntrySets = new ArrayList<>(entry);
   
      /* Sorting the entries in arraylist using object of class 'mapValueComparator', such that words are displayed
         in decreasing order of their scores and in acending lexicographic order of the words in case of a tie. */
      
      Collections.sort(listOfEntrySets, new mapValueComparator());
       
      // Display words and scores as final output
      for(Map.Entry<String, Integer> entrySet : listOfEntrySets) {
         
         System.out.println(entrySet.getValue()+": " + entrySet.getKey());
         
      }
      
   }
   
}
       
     
   
  

/**
   Class 'mapValueComparator' is used by class 'AnagramDictionary'. It implements 'Comparator' interface that takes in
   elements of type Map.Entry<String,Integer> and compares two map entries based on their values in descending order. 
   
   It defines 'compare' method such that it returns a negative number if value of entry2 is less than that of entry1,
   0 if both values are same and a positive number otherwise.
    
*/
   
class mapValueComparator implements Comparator<Map.Entry<String,Integer>> {
      
   public int compare(Map.Entry<String,Integer> entry1 , Map.Entry<String,Integer> entry2) {     // Total no. of words : 1
         
      return Integer.compare(entry2.getValue(), entry1.getValue());
      
   }
   
}
         
    

   
 