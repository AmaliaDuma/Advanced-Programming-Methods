package Model.Types;

import Model.Values.IntValue;
import Model.Values.Value;

public class IntType implements Type{

    @Override
    public boolean equals(Object obj) {
        return obj instanceof IntType;
    }

    @Override
    public Value defValue() {
        return new IntValue();
    }

    @Override
    public Type deepCopy() {
        return new IntType();
    }

    @Override
    public String toString() {
        return "int";
    }


}
