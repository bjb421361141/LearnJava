package designPatterns.strategy;

import designPatterns.strategy.inf.IComparable;

public class CatWeightComparator implements IComparable<Cat> {
    @Override
    public int compare(Cat a, Cat b) {
        if(a.weight < b.weight) {
            return -1;
        } else if(a.weight > b.weight){
            return 1;
        }
        return 0;
    }
}
