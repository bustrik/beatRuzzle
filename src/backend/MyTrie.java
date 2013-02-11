package backend;

import java.io.*;


class MyNode{
    public static char[] alphabet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 
        'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 
        's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    
    private MyNode[] map;
	public boolean isaWord;
	
	public MyNode()
	{
		this.map = new MyNode[alphabet.length];
		this.isaWord = false;
		for (int i = 0; i < alphabet.length ; i++)
		{
			this.map[i] = null;
		}
	}

	public MyNode get(char letter)
	{
		return this.map[letter - 'a'];
	}
	
	public MyNode put(char letter)
	{
		MyNode children = new MyNode();
		this.map[letter - 'a'] = children;		
		return children;
	}
}


public class MyTrie {
    
    
    private MyNode root;
    private MyNode pointer;
       
    
    public boolean isaPrefix (String wordToCheck)
    {
    	pointer = root;
    	MyNode children;
    	int l = wordToCheck.length();
    	for (int i = 0; i < l; i++)
    	{
    		char nl = wordToCheck.charAt(i);
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
    		char nl = wordToCheck.charAt(i);
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
    		char nl = newWord.charAt(i);
    		//controllo alfabeto
    		if (nl < 'a' && nl > 'z')
    		{
    			System.out.println("Attenzione: parola " + newWord + " non indicizzabile");
    			break;
    		}
    
    		children = pointer.get(nl);
    		if (children == null)
    		{
    			children = pointer.put(nl);
    		}
    		pointer = children;
    	}
    	pointer.isaWord = true;
    }
    
    // Trie Constructor
	public MyTrie(String dictionaryFile) 
	{
//		System.out.println("Creating root node");
		pointer = root = new MyNode();
//		System.out.println("Root node created");

//		System.out.println("Loading dictionary");
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