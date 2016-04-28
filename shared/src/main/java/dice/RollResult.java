package dice;

import java.util.ArrayList;

public class RollResult {
    private ArrayList<Integer> rolls;
    private String specialRoll;
    private int specialRollCode;

    public RollResult() {
        rolls = new ArrayList<Integer>();
        specialRoll = "";
    }

    public void addRoll(int rollValue) {
        rolls.add(rollValue);
    }

    public int sum() {
        int sum = 0;

        for (int rollIndex = 0; rollIndex < rolls.size(); rollIndex++) {
            sum += rolls.get(rollIndex);
        }

        return sum;
    }

    public int rollCount() {
        return rolls.size();
    }

    public int[] rollsArray() {
        int[] array = new int[rolls.size()];
        int index = 0;
        for (Integer n : rolls) {
            array[index] = n;
            index++;
        }
        return array;
    }
    
    public String getSpecialRollString(){
        updateSpecialRoll();
        return specialRoll;
    }
    
    public int getSpecialRollCode(){
        updateSpecialRoll();
        return specialRollCode;
    }
    
    private void updateSpecialRoll(){
        if(rolls.size() == 3){
            if(rolls.get(0) == rolls.get(1) && rolls.get(1) == rolls.get(2))
            {
                specialRoll = "3 of a kind";
                specialRollCode = 1;
            }
            
            if(isConsecutive(rolls))
            {
                specialRoll = "Consecutive roll";
                specialRollCode = 2;
            }
        }
    }
    
    private boolean isConsecutive(ArrayList<Integer> array){
        int max = getMax(array);
        int min = getMin(array);
        if ((max - min +1) == array.size())
        {
            for (Integer arrayE : array) {
                int index2;
                if (arrayE < 0) {
                    index2 = arrayE - min;
                } else
                    index2 = -arrayE - min;
                
                if (array.get(index2) > 0)
                    array.set(index2,-array.get(index2));
                else 
                    return false;
            }
        }
        return true;
    }
    
    private int getMax(ArrayList<Integer> array)
    {
        int max = array.get(0);
        for (int index = 1; index < array.size(); index++)
        {
            if (max < array.get(index))
                max = array.get(index);
        }
        return max;
    }
    
     private int getMin(ArrayList<Integer> array)
    {
        int min = array.get(0);
        for (int index = 1; index < array.size(); index++)
        {
            if (min > array.get(index))
                min = array.get(index);
        }
        return min;
    }
}
