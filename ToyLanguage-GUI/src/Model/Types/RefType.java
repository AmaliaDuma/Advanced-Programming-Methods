package Model.Types;

import Model.Values.RefValue;
import Model.Values.Value;

public class RefType implements Type{
    private Type inner;

    public RefType(Type inner) {this.inner = inner;}

    public Type getInner() {
        return inner;
    }

    public void setInner(Type inner) {
        this.inner = inner;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RefType)
            return this.inner.equals(((RefType) obj).getInner());
        else
            return false;
    }

    @Override
    public Value defValue() {
        return new RefValue(0, inner);
    }

    @Override
    public Type deepCopy() {
        return new RefType(inner.deepCopy());
    }

    @Override
    public String toString() {
        return "Ref(" + inner.toString() + ")";
    }
}
