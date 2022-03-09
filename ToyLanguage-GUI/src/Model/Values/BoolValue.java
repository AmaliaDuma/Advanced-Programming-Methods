package Model.Values;

import Model.Types.BoolType;
import Model.Types.Type;

public class BoolValue implements Value{

    private boolean val;

    public BoolValue(boolean v) {val=v;}
    public BoolValue() {val=false;}

    public boolean getVal() {return val;}
    public void setVal(boolean v) {val=v;}

    @Override
    public Type getType() {
        return new BoolType();
    }

    @Override
    public Value deepCopy() {
        return new BoolValue(val);
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (!(obj instanceof BoolValue))
            return false;
        BoolValue v = (BoolValue) obj;
        return v.val == val;
    }

    @Override
    public String toString() {
        return Boolean.toString(val);
    }
}
