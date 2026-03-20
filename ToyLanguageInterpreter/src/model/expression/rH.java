package model.expression;

import exception.InvalidTypeException;
import model.state.IHeap;
import model.state.MyIDictionary;
import model.type.RefType;
import model.type.Type;
import model.value.RefValue;
import model.value.Value;

public record rH(Expression expression) implements Expression {
    @Override
    public Value evaluate(MyIDictionary<String, Value> symbolTable, IHeap<Value> heap) {
        Value val = expression.evaluate(symbolTable, heap);
        if(!(val instanceof RefValue))
            throw new IllegalArgumentException();
        return heap.getContent().get(((RefValue)val).getAddress());
    }

    @Override
    public Expression deepCopy() {
        return new rH(expression);
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) {
        Type typ=expression.typecheck(typeEnv);
        if (typ instanceof RefType) {
            RefType reft =(RefType) typ;
            return reft.getInner();
        } else
            throw new InvalidTypeException("the rH argument is not a Ref Type");
    }


    @Override
    public String toString() {
        return "readHeap(" + expression.toString() + ")";
    }
}
