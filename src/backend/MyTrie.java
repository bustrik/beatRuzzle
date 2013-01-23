package backend;

import java.util.HashMap;
import java.io.*;


class MyNode extends HashMap<String, MyNode>{

	public String letter; //letter inside this node
	public boolean isaWord;
	
	public MyNode(String[] alphabet)
	{
		for (String l : alphabet)
		{
			this.put(l, null);
			this.letter = null;
			this.isaWord = false;
		}
	}
	
	public MyNode(String[] alphabet, String letter)
	{
		for (String l : alphabet)
		{
			this.put(l, null);
			this.letter = letter;
		}
	}
}


public class MyTrie {
    private static String[] alphabet = {"a", "b", "c", "d", "e", "f", "g", "h", "i", 
        "j", "k", "l", "m", "n", "o", "p", "q", "r", 
        "s", "t", "u", "v", "w", "x", "y", "z"};
    
    private MyNode root;
    private MyNode pointer;
    
    
    
    
    public boolean isaPrefix (String wordToCheck)
    {
    	pointer = root;
    	MyNode children;
    	int l = wordToCheck.length();
    	for (int i = 0; i < l; i++)
    	{
    		String nl = ""+wordToCheck.charAt(i);
    		children = pointer.get(nl);
    		if (children == null)
    			return false;
    		pointer = children;
    	}	
    	return true;
    }
    
    public boolean isaWord (String wordToCheck)
    {
    	pointer = root;
    	MyNode children;
    	int l = wordToCheck.length();
    	for (int i = 0; i < l; i++)
    	{
    		String nl = ""+wordToCheck.charAt(i);
    		children = pointer.get(nl);
    		if (children == null)
    			return false;
    		pointer = children;
    	}	
    	return pointer.isaWord;
    }
    
    private void addWord(String newWord)
    {
    	pointer = root;
    	MyNode children;
    	int l = newWord.length();
    	for (int i = 0; i < l; i++)
    	{
    		String nl = ""+newWord.charAt(i);
    		//controllo presenza nodo:
    		children = pointer.get(nl);
    		if (children == null)
    		{
    			pointer.put(nl, children = new MyNode(alphabet,nl));
    		}
    		pointer = children;
    	}
    	pointer.isaWord = true;
    }
    
    // Trie Constructor
	public MyTrie(String dictionaryFile) 
	{
		System.out.println("Creating root node");
		pointer = root = new MyNode(alphabet);
		System.out.println("Root node created");
		
		
		System.out.println("Loading dictionary");
		try
		{
			//Open file
			
			  FileInputStream fstream = new FileInputStream(dictionaryFile);
			  DataInputStream in = new DataInputStream(fstream);
			  BufferedReader br = new BufferedReader(new InputStreamReader(in));
			  String strLine;
			//Read File Line By Line
			  while ((strLine = br.readLine()) != null)   {
			  // Print the content on the console
				  this.addWord(strLine);
			  }
			  //Close the input stream
			  in.close();
			    }catch (IOException e){//Catch exception if any
			  System.err.println("Error: " + e.getMessage());
		}
		
	}
	
}