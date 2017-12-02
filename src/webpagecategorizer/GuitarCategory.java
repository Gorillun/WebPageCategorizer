/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package webpagecategorizer;

/**
 *
 * @author keith
 */
public class GuitarCategory extends Category
{
    private final String category;
    public GuitarCategory(String url)
    {
        super(url);
        category="Guitars";
        
    }
    @Override
    public void setInventory(String in)
    {
        ++numOfInventory;
        String test=in;
        int i=testInputString(test);
        if(i!=0)
        {
            addInventory(test);
                    
        }
        
    }
    private int testInputString(String in)
    {
        String test=in;
        if((test.length()<4))
        {
            return 0;
        }
        else
        return 1;
    } 
    private void addInventory(String add)
    {
        String input =add;
        
        if(!siteInfo.containsKey(input))
        {
            siteInfo.put(input,1);
        }
        else
        {
            Integer value =(Integer) siteInfo.get(input);
            siteInfo.put(input,value+1);    
            
        }
    }
    @Override
    public String getCategoryType()
    {
        return category;
    }
}
