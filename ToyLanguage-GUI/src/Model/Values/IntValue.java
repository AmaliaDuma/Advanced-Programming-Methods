package Model.Values;

import Model.Types.IntType;
import Model.Types.Type;

import java.util.Objects;

public class IntValue implements Value{

    private int val;

    public IntValue(int v) {val=v;}
    public IntValue() {val=0;}

    public int getVal() {return val;}
    public void setVal(int v) {val=v;}

    @Override
    public Type getType() {
        return new IntType();
    }

    @Override
    public Value deepCopy() {
        return new IntValue(val);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof IntValue))
            return false;
        IntValue v = (IntValue) obj;
        return v.val == val;

    }

    @Override
    public String toString() {
        return Integer.toString(val);
    }
}
