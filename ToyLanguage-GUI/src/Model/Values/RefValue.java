package Model.Values;

import Model.Types.RefType;
import Model.Types.Type;

public class RefValue implements Value{
    private int addr;
    private Type type;

    public  RefValue(int addr, Type type) {
        this.addr = addr;
        this.type = type;
    }

    public int getAddr() {
        return addr;
    }

    public void setAddr(int addr) {
        this.addr = addr;
    }

    @Override
    public Type getType() {
        return new RefType(type);
    }

    @Override
    public Value deepCopy() {
        return new RefValue(addr, type.deepCopy());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof RefValue))
            return false;
        RefValue v = (RefValue) obj;
        return (v.addr == this.addr) && v.type.equals(this.type);
    }

    @Override
    public String toString() {
        return "(" + Integer.toString(addr) + ", " + type.toString() + ")";
    }
}
