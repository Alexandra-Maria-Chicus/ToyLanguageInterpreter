package model.type;

import model.value.Value;

public interface Type {
    Value getDefaultValue();
    String getTypeName();
    boolean equals(Object other);
    Type deepCopy();
}
