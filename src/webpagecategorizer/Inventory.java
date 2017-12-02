/*Keith Fosmire
  CSC365
   Due 4 March 2014
*/

package webpagecategorizer;
/*
* This is the "storage room" for the program. The Inventory class stores all Categories and 
* associated strings. Nodes are instantiated here using the keywords found in the added Category
* Every node is then added to the Tree. This Inventory Class has a function called findCategory().
* The findCategory() may end up as its own class due to the shear madness of it. Regardless
* of the mess I have made...the program will find what category that a given URL belongs to..
*you can either trust me for now and wait until I make it more efficient, or soon discover that
*I have the thought process of a raging lunatic...your choice.
*/

import java.util.*;

public class Inventory 
{
   // What a nice view...simple...I am finally allowed to use some Java tools other than an array
   //Once again private variables for an ArrayList and Nodes
   private final ArrayList<Category> categories= new ArrayList();
   private InventoryNode root;
   //seems like a good idea...not sure why I need it
   private int nodes=0;

    //all categories must enter here, did not want to add through a constructor...obviously
   //Ok, if I used a constuctor...it would be a lot of different inventories and defeat the purpose
    public void insertURL(Category inv )
    {
       
       categories.add(inv);
       Set keys =inv.getInventory().keySet();
       Iterator it = keys.iterator();
      while(it.hasNext())//checking if it exists
      {
          String key = (String) it.next();
          InventoryNode match =searchTree(key,root);//searching for a node
          if(match!=null)
          {
              match.addCategory(inv);//if it  does then I only add the category, Node does the rest
              ++nodes;// I must be related to Count Dracula from sesame street
          } 
          else
          {
            InventoryNode temp = new InventoryNode(key,inv);//didn't exist..so I made a new Node
            root=addNode(temp,root);
          }
      }
    }
    //I know....I will clean this up very soon
    //Receives an arraylist and iterates through to finde the closest category
    public String findCategory(ArrayList<String> in)
    {
        ArrayList<String> check = in;
        String theCategory ="miscellaneous";//default category
        Map<String,Integer> matches = new  HashMap<>();//this is fast..thats why
        matches.put("http://www.guitarcenter.com", 0);
        matches.put("http://www.musiciansfriend.com", 0);
        matches.put("http://www.fender.com/", 0);
         matches.put("http://www.schecterguitars.com/", 0);
         matches.put("https://www.denniskirk.com/", 0);
         matches.put("http://www.jpcycles.com/", 0);
         matches.put("http://www.bikebandit.com/", 0);
         matches.put("http://www.motorcycle-superstore.com/", 0);
         matches.put("http://www.foxnews.com/", 0);
         matches.put("http://www.cnn.com/", 0);
         matches.put("https://news.google.com/", 0);
        Iterator it = check.iterator();///These rock!! 
        InventoryNode results;
        while(it.hasNext())///Ok here we go, lets find the Category
        {
            String str =it.next().toString();//the word
            results=searchTree(str,root);//the search result of the word
            if (results!=null)//Found a matching node...or not
            {
                Collection<Category> cats = results.getCategories();//Lets pull the Category(ies) off the shelf
                Iterator<Category> catIt = cats.iterator();//man these Iterators are fun
                while(catIt.hasNext())
                {
                    Category cat =catIt.next();///good idea to store them...found out the hard way
                    int hits = 1+matches.get(cat.getName());//adding hits up
                    matches.put(cat.getName().toString(), hits);//put it i the out box, matches
                }    
                } 
         }
        Iterator<Category> itC = categories.iterator();//now that I have the hits, I need the percentage
        while(itC.hasNext())
        {
           Category cat =itC.next();
           int invCount = cat.getNumOfInventory();//actual count of words for each Category
           int invHits = matches.get(cat.getName());//number of its
          float percent = ((float)invHits/(float)invCount)*100;//percentage calculated...it is accurate
          Integer p = (int)percent;//convert to integer
           matches.put(cat.getName(), p);//back in the outbox
        }
        Set cats =matches.keySet();//used to iterate through the MAP
        Iterator catIt=cats.iterator();
        int x=0;
        String targetCat=null;
        while(catIt.hasNext())
       {
           String s = catIt.next().toString();//this is the key
           int v= matches.get(s);//this is the value
           if(v>x)// who has the highest hit??
           {
                x=v;
                targetCat=s;///this guy does
           }
       }
        Iterator<Category> rightOne = categories.iterator();//I might need counseling
        while(rightOne.hasNext())
        {
            Category temp=rightOne.next();
            if(temp.getName().equalsIgnoreCase(targetCat))//this needs to be clean, searching again!!
                {
                    theCategory=temp.getCategoryType();//just to get the category type, i need to fire that node
                }
        }
        return theCategory;
    }
    //Recursive search..its just the right thing to do
    public InventoryNode searchTree(String name,InventoryNode current)
    {
        if(current==null)//nothing there
        {
            return null;
        }
        else if(current.getName().equalsIgnoreCase(name))//found it
            return current;
        else if(current.getName().compareToIgnoreCase(name)<0)//too small
        {
            return searchTree(name,current.getRight());//trying a bigger size
            
        }
        else if(current.getName().compareToIgnoreCase(name)>0)//too big
        {
             return searchTree(name,current.getLeft());//trying a smaller size
             
        }
        return current;
    }
    //Adding the node alphabetically
    private InventoryNode addNode(InventoryNode in,InventoryNode current)
    {
        if(current==null)//tree is empty
        {
            return in;
        }
        else if(current.getName().compareToIgnoreCase(in.getName())<0)//too small
        {
            current.setRight(addNode(in,current.getRight())); //try a bigger one
            current.getRight().setParent(current);//got it...set it and get it later
            
        }
        else if(current.getName().compareToIgnoreCase(in.getName())>0)//too big
        {
             current.setLeft(addNode(in,current.getLeft()));//get a smaller one
             current.getLeft().setParent(current);//thats a good spot
             
        }
        return current;
    }
    // Might want a category
   public String getCategory(String in)
   {
       String rightOne = null;
       String mate = in;
       Iterator<Category> getIt=categories.iterator();//Once again
       while(getIt.hasNext())
       {
           Category cat = getIt.next();
           if(mate.equalsIgnoreCase(cat.getName()))//are you my dad?
                    rightOne=cat.getName();//guess so
       }
       return rightOne;
   }
   
    
}
