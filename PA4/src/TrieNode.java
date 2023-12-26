import java.util.ArrayList;

public class TrieNode {

    private String wordHere;

    private TrieNode[] links;

    public TrieNode() {
        wordHere = null;
        links = new TrieNode[26];
    }

    //Convert a letter to a number
    private int let(char c) {
        return c - 'A';
    }

    private char firstChar(String key) {
        return key.charAt(0);
    }

    private String rest(String key) {
        return key.substring(1, key.length());
    }

    private TrieNode linkWordStart(String start) {
        return links[let(firstChar(start))];
    }

    public void insert(String key, String toHere) {
        // make String upper case
        key = key.toUpperCase();

        if (key.isEmpty()) {
            // If the key is empty, set the wordHere field
            wordHere = toHere;
        } else {
            char firstChar = firstChar(key);
            String restKey = rest(key);

            // If the link for the first character is null, create a new TrieNode
            if (linkWordStart(key) == null) {
                links[let(firstChar)] = new TrieNode();
            }

            // Recursively insert the rest of the key, updating toHere parameter
            linkWordStart(key).insert(restKey, toHere + firstChar);
        }
    }


    public TrieNode find(String key, String soFar) {

        /*
         * The second parameter is mostly here to show you something that is useful in
         * other methods
         */

        if (key.length() == 0) {
            if (wordHere == null)
                return null;
            else {
                if (!soFar.equals(wordHere)) {
                    System.out.println(
                            "Sanity check failure in find - this should never happen!");
                    System.exit(99);
                }
                return this;
            }
        } else {
            if (linkWordStart(key) == null)
                return null;
            else return linkWordStart(key).find(rest(key), soFar + key.charAt(0));
        }
    }


    public void print(String string) {
        if (wordHere != null)
            System.out.println(string + " " + wordHere);
        else System.out.println(string + " empty");
        for (int i = 0; i < 26; i++) {
            if (links[i] != null) {
                links[i].print(string + "-");
            }
        }
    }

    // kid checker
    public boolean hasAnyKids() {
        //recurse through links
        for (TrieNode child : links) {
            //if there's a kid or more return true otherwise return false
            if (child != null) {
                return true;
            }
        }
        return false;
    }

    //made public from private

    public boolean delete(String key) {
        //if empty return false
        if (key.isEmpty()) {
            return false;
        }
        //else
        if (key.length() == 1) {
            linkWordStart(key).wordHere = null;
            //check for kids
            if (linkWordStart(key).hasAnyKids()) {
                return false;
            } else {
                this.links[let(firstChar(key))] = null;
                return wordHere == null;
            }
        }
        //recurse
        return linkWordStart(key).delete(rest(key));
    }


    public String longestPrefix(String s, String soFar) {
        //base case
        if (s.isEmpty()) {
            return soFar;
            //check that start isn't null
        } else {
            if (linkWordStart(s) != null) {
                //recurse
                return linkWordStart(s).longestPrefix(rest(s), soFar + s.charAt(0));
            }
        }
        //return longest
        return soFar;
    }


    public String longestPrefixWord(String s, String soFar) {
        if (wordHere != null) {
            // If the current node is a valid word, update the result
            soFar = wordHere;
        }

        if (s.isEmpty()) {
            // If the input string is empty, return the accumulated prefix
            return soFar;
        }

        char nextChar = s.charAt(0);
        TrieNode nextNode = links[let(nextChar)];

        if (nextNode == null) {
            // If the next character is not in the Trie, return the accumulated prefix
            return soFar;
        }

        // Recursively explore the next character
        return nextNode.longestPrefixWord(s.substring(1), soFar);
    }


    public ArrayList<String> allKeysWithPrefix(String pre) {
        //create lists for the results and for the method itself to use
        ArrayList<String> result = new ArrayList<String>();
        ArrayList<String> tempList = new ArrayList<>();
        //run allkeys on tempList
        allKeys(tempList);
        //set length equal to the length of the pre
        int length = pre.length();
        //loop through strings in temp list checking that chars match
        for (String s : tempList) {
            for (int j = 0; j < pre.length(); j++) {
                if (s.charAt(j) != pre.charAt(j)) {
                    break;
                }
                if (j == length - 1) {
                    result.add(s);
                }
            }
        }
        //return array
        return result;
    }


    public void allKeys(ArrayList<String> v) {
        if (wordHere != null) {
            v.add(wordHere);
        }
        if (hasAnyKids()) {
            for (TrieNode link : links) {
                if (link != null) {
                    link.allKeys(v);
                }
            }
        }
    }


    public ArrayList<String> allPrefixMatch(String s, ArrayList<String> v) {
        //creat bool for pre checking
        boolean contPre = true;
        //if there's a word
        if (wordHere != null) {
            String word = wordHere;
            //loop through and check for pre
            if (word.length() <= s.length()) {
                for (int i = 0; i < word.length(); i++) {
                    if (word.charAt(i) != s.charAt(i)) {
                        contPre = false;
                        break;
                    }
                }
                if (contPre) {
                    v.add(wordHere);
                }
            }
        }
        //check children as well
        if (contPre) {
            if (hasAnyKids()) {
                for (TrieNode link : links) {
                    if (link != null) {
                        link.allPrefixMatch(s, v);
                    }
                }
            }
        }
        return v;
    }


    public ArrayList<String> allLargestPrefix(String pre) {
        //creat lists for the list in the method and results
        ArrayList<String> result = new ArrayList<String>();
        ArrayList<String> tempList = new ArrayList<>();
        //get longest prefix
        String longestPrefix = longestPrefix(pre, "");
        //run all keys
        allKeys(tempList);
        int length = longestPrefix.length();
        //double loop to find the largest prefixes
        for (String s : tempList) {
            for (int j = 0; j < longestPrefix.length(); j++) {
                if (s.charAt(j) != longestPrefix.charAt(j)) {
                    break;
                }
                if (j == length - 1) {
                    result.add(s);
                }
            }
        }

        return result;
    }


    public void allFind(ArrayList<String> result, String target, Trie searchTrie) {
        int n = target.length();
        for (int i = 0; i < n; i++) {
            TrieNode currentNode = this;
            for (int j = i; j < n; j++) {
                char nextChar = target.charAt(j);
                TrieNode nextNode = currentNode.links[let(nextChar)];

                if (nextNode == null) {
                    break;
                }
                currentNode = nextNode;

                // Check if the current node is the end of a word in the searchTrie
                if (currentNode.wordHere != null && searchTrie.find(currentNode.wordHere)) {
                    result.add(currentNode.wordHere);
                }
            }
        }
    }


    //A LEVEL
    public void spellCheck(ArrayList<String> ws, String key, int errs) {
        //if there's a word
        if (wordHere != null) {
            if (wordHere.length() == key.length()) {
                //create counter for errors
                int counter = 0;
                for (int i = 0; i < key.length(); i++) {
                    //increment for errors
                    if (wordHere.charAt(i) != key.charAt(i)) {
                        counter++;
                    }
                }
                //if word is messed up add to list passed in
                if (errs >= counter) {
                    ws.add(wordHere);
                }
            }
        }
        //if it has kids
        if (this.hasAnyKids()) {
            //loop through and spell check recursively
            for (TrieNode link : this.links) {
                //if there are more links
                if (link != null) {
                    //recurse through them
                    link.spellCheck(ws, key, errs);
                }
            }
        }
    }


    public void wildCardMatch(ArrayList<String> v, String wild) {
        //if the wild string is empty
        if (wild.isEmpty()) {
            if (this.wordHere != null && !v.contains(wordHere)) {
                v.add(wordHere);
            }
            //if the wild string is just '*'
        } else if (wild.length() == 1) {
            if (wild.charAt(0) == '*') {
                //check for kids
                if (this.hasAnyKids()) {
                    //check all links
                    for (TrieNode link : links) {
                        if (link != null) {
                            //run wild card if the link is not null
                            link.wildCardMatch(v, wild);
                        }
                    }
                }
                //if word is not null and is not in the passed in string
                if (this.wordHere != null && !v.contains(wordHere)) {
                    //add to wordhere
                    v.add(wordHere);
                }
            } else {
                //if it's not null then recurse
                if (linkWordStart(wild) != null) {
                    linkWordStart(wild).wildCardMatch(v, rest(wild));
                }
            }
            //check for cases with multiple stars (similar process)
        } else if (this.hasAnyKids()) {
            if (wild.charAt(1) == '*') {
                if (wild.charAt(0) == '*') {
                    this.wildCardMatch(v, rest(wild));
                } else {
                    if (linkWordStart(wild) != null) {
                        linkWordStart(wild).wildCardMatch(v, rest(wild));
                    }
                }
            } else if (wild.charAt(0) == '*') {
                for (TrieNode link : links) {
                    if (link != null) {
                        link.wildCardMatch(v, wild);
                    }
                }
                if (linkWordStart(rest(wild)) != null) {
                    linkWordStart(rest(wild)).wildCardMatch(v, rest(rest(wild)));
                }
            } else {
                if (linkWordStart(wild) != null) {
                    linkWordStart(wild).wildCardMatch(v, rest(wild));
                }
            }
        }
    }
}