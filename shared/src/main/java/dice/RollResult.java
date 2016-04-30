package dice;

import java.util.ArrayList;
import java.util.Collections;

public class RollResult 
{
    private ArrayList<Integer> rolls;
    private int specialRollCode;

    public RollResult() 
    {
        rolls = new ArrayList<Integer>();
        specialRollCode=0;
    }

    public void addRoll(int rollValue) 
    {
        rolls.add(rollValue);
        updateSpecialRoll();
    }

    public int sum() 
    {
        int sum = 0;

        for (int rollIndex = 0; rollIndex < rolls.size(); rollIndex++) 
        {
            sum += rolls.get(rollIndex);
        }

        return sum;
    }

    public int rollCount() 
    {
        return rolls.size();
    }

    public int[] rollsArray() {
        int[] array = new int[rolls.size()];
        int index = 0;
        for (Integer n : rolls) 
        {
            array[index] = n;
            index++;
        }
        return array;
    }
    
    public int getSpecialRollCode()
    {
        return specialRollCode;
    }
    
    private void updateSpecialRoll(){
        ArrayList<Integer> temp = new ArrayList(rolls);
        if(temp.size() == 3){
            if(temp.get(0) == rolls.get(1) && rolls.get(1) == rolls.get(2))
            {
                specialRollCode = 1;
            }
            
            if(isConsecutive(temp))
            {
                specialRollCode = 2;
            }
        }
    }
    
    private boolean isConsecutive(ArrayList<Integer> array){
        int max = Collections.max(array);
        int min = Collections.min(array);
        if ((max - min +1) == array.size())
        {
            for (Integer arrayE : array) {
                int index2;
                if (arrayE > 0) 
                {
                    index2 = arrayE - min;
                } else
                    index2 = -arrayE - min;
                
                if (array.get(index2) > 0)
                    array.set(index2,-array.get(index2));
                else 
                    return false;
            }
            return true;
        } else
            return false;
        
    }
}
