package Model.Types;

import Model.Values.Value;

public interface Type {
    Value defValue();
    Type deepCopy();

}
