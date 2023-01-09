import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

//PART 2
public class Spellchecker {
    private Node root;

    public static void main(String[] args) {
        Spellchecker sc = new Spellchecker();
        sc.addFile("dictionary.txt"); //Trie
        System.out.println("\nAmount of misspelled words: " + sc.checkFile("sample.txt"));
    }

    public Spellchecker() {
        root = new Node(false); //setting up an empty trie
    }

    private class Node {
        private final Map<Character, Node> children;
        private boolean contains;

        public Node(boolean contains) {
            children = new HashMap<>(); //Empty Trie
            this.contains = contains;
        }
    }

    public void addWord(String word) { //adds dictionary words into the Trie
        String toLowerString = word.toLowerCase();
        Node searchWords = root; // setting up a current node from the parent/root
        for (char chars: toLowerString.toCharArray()) {
            searchWords = searchWords.children.computeIfAbsent(chars, c -> new Node(false));
        }
        /*
        * To explain the line above:
        * it checks if the node already has a reference to the character received,
        * then it sets the node to that character, if there is no reference to that character
        * it creates a new node and sets that character. Also initializing the the node to the new node.
        * it repeats until the the key is TRAVERSED.
        * */
        searchWords.contains = true;
    }

    public boolean containsWord(String word) {
        String toLowerString = word.toLowerCase();
        Node searchWords = root; // setting up a current node from the parent/root
        for (int i = 0; i < toLowerString.length(); i++) { //iterates through every character of the String word
            char chars = toLowerString.charAt(i);
            Node node = searchWords.children.get(chars); // checking if the character is a part of sub-nodes or sub-trie characters.
            if (node == null) { //If it is not then false is returned
                return false;
            }
            searchWords = node;
        }
        return searchWords.contains; //prints true if the end of the node is reached
    }

    public int countWords() {
        return root.children.size();
    }

    public void addFile(String filename) {
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            int counting = 0;
            while (scanner.hasNext()) { //simply adds all the dictionary words to the Trie by looping each and every one of the them
                String input = scanner.nextLine();
                addWord(input);
                counting++;
            }
            System.out.println("Dictionary contains " + counting + " words\n");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public int checkFile(String filename) {
        ArrayList<String> wordsToCheck = new ArrayList<>();
        int counting = 0;
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNext()) {
                String input = scanner.next().replaceAll("[\\W]", " "); //takes in the Word and removes any kind of punctuation
                String[] inputArray = input.split("\\s"); //splits by whitespace into array of strings

                wordsToCheck.addAll(Arrays.asList(inputArray)); // checks all the elements of the inputArray and adds them to the ArrayList
            }

            for (String str : wordsToCheck) { //loops through arraylist and compares with the dictionary
                if (!containsWord(str) && !str.isEmpty()) {
                    counting++;
                    System.out.println("Misspelled word: " + str); //prints misspelled words
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return counting;
    }
}
