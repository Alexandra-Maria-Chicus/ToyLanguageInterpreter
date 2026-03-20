package model.type;

import model.value.RefValue;
import model.value.Value;
public class RefType implements Type{
    Type inner;
    public RefType(Type inner) {
        this.inner=inner;
    }
    public Type getInner() {return inner;}

    @Override
    public Value getDefaultValue() {
        return new RefValue(0,inner);
    }

    @Override
    public String getTypeName() {
        return "";
    }

    @Override
    public boolean equals(Object another){
        if (another instanceof RefType)
            return inner.equals(((RefType) another).getInner());
        else
            return false;
    }

    @Override
    public Type deepCopy() {
        return new RefType(inner.deepCopy());
    }

    @Override
    public String toString() { return "Ref " +inner.toString()+"";}
}
