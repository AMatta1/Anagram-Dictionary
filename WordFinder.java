import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;


/**
   It is the main method class that is responsible for processing the command-line argument, 
   handling any error processing (in case file not found) and accepting user input in the form of a 
   rack either through the keyboard or through an input file.
   
   It is dependent on other classes to display all the words that can be formed from an input word : 
   Rack (that represents the rack input) and AnagaramDictionary (that represents all anagram sets 
   from a dictionary for a word and its subsets)
*/

public class WordFinder {
   
   public static void main(String[] args) {                                              // Total no. of lines : 12
      
      String filename;                                                                   // Variable to hold the name of the scrabble dictionary 
      AnagramDictionary anagram;
      
      /* Name of the reference scrabble dictionary is assigned as per user input. If user provides 
         the name of the dictionary explicitly as the first argument through the command line, then that dictionary will be 
         used as refernce, else default dictionary 'sowpods.txt' will be used.
         
         So, if command line is java WordFinder testFiles/tinyDictionary.txt --> 'tinyDictionary.txt' will be used else, 
         if command line is java WordFinder -->  'sowpods.txt' will be used. */
      
      if (args.length > 0) {
         filename = args[0];
      }
        
      else {
         filename = "sowpods.txt";
      }
         
      // Exception handling if the user entered dictionary file does not exist
      try {
         
         /* Calling parametrized constructor of AnagramDictionary class to pass the dictionary name to it to
            enable creation of a map consisting of all the words. */
         
         anagram = new AnagramDictionary(filename);
         
      }
      
      // Catching the exception (if occurred). It displays an informative error message  (including the file name) and exits.
      catch(FileNotFoundException exception) {
         
         System.out.println("File : '" + filename + "' not found. Please enter a valid file.");
         return;
         
      }
   
      userInput(anagram);                           
   }
      
   
   
   
   
   /**
      Displays the user prompt to enter the input rack. It can be done in 2 ways: Either thorugh 
      console itself or through an input file. Calls class 'Rack' to get list of all subsets of a word
      followed by class 'AnagramDictionaty' to get all possible anagrams and words that can be formed.
      Continues till runs out of words in file or user enters a '.' through the console.
      
      @Param : anagram - The already created object of class AnagramDictionary to call method on it.
       
   */  
   
   private static void userInput(AnagramDictionary anagram){                             // Total no. of lines : 12

      Scanner in = new Scanner (System.in);                              
      
      // The input word for which we have to find all possible words that can be formed from it
      String userInput; 
      
      // ArrayList to hold all subsets of a particular word returned by class 'Rack' 
      ArrayList<String> subsetsList = new ArrayList<>();
   
      System.out.println("Type . to quit."); 
      System.out.print("Rack? ");
      userInput = in.next(); 
   
      // If user enters a '.', the loop exits and program terminates
      while( !userInput.equals(".") ) {
               
         Rack rack = new Rack();
         
         /* Call to method 'multisetRepresentation' of class 'Rack' which inturn calls 'allSubsets' method to return
            list of all possible subsets of a word/userInput. Variable 'subsetsList' holds the returned arraylist */
               
         subsetsList =  rack.multisetRepresentation(userInput);
         
         /* User input and list of all subsets is passed to method 'fetchAllPossibleWords' of class 'AnagramDictionary'
            to find anagrams of the each subset and display output accordingly. */
         
         anagram.fetchAllPossibleWords(subsetsList, userInput);
         
         System.out.print("Rack? ");
         userInput = in.next(); 
      }
   }
}
 

