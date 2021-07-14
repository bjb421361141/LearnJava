package designPatterns.strategy;

import designPatterns.strategy.inf.IComparable;

public class CatHeightComparator implements IComparable<Cat> {
    @Override
    public int compare(Cat a, Cat b) {
        if(a.height < b.height) {
            return -1;
        } else if(a.height > b.height){
            return 1;
        }
        return 0;
    }
}
