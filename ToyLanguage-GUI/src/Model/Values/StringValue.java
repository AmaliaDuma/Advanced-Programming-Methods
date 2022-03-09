package Model.Values;
import Model.Types.StringType;
import Model.Types.Type;

import java.util.Objects;

public class StringValue implements Value{

    String value;

    public StringValue(String v) {value=v;}
    public StringValue() {value="";}

    public String getVal() { return value;}
    public void setVal(String value) { this.value = value;}

    @Override
    public Type getType() { return new StringType();}

    @Override
    public Value deepCopy() {
        return new StringValue(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof StringValue))
            return false;
        StringValue v = (StringValue) obj;
        return value.equals(v.value);
    }

    @Override
    public String toString() { return value.toString();}

}
