package model.state;
import model.type.Type;
import model.value.Value;
import java.util.HashMap;
import java.util.Map;

public class MyDictionary<K,V> implements MyIDictionary<K, V> {
    private final Map<K,V> dictionary;

    public MyDictionary() {
        this.dictionary = new HashMap<>();
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append("SymTable:");

        for (Map.Entry<K, V> entry : dictionary.entrySet()) {
            builder.append("\n")
                    .append(entry.getKey().toString())
                    .append(" --> ")
                    .append(entry.getValue().toString());
        }
        return builder.toString();
    }

    @Override
    public Map<K, V> getContent() {
        return dictionary;
    }

    @Override
    public void setValue(K variableName, V value)  {
        this.dictionary.put(variableName, value);
    }

    @Override
    public boolean isDefined(K variableName) {
        return dictionary.containsKey(variableName);
    }


    @Override
    public Type getType(K variableName) {
        V value = dictionary.get(variableName);
        if (value instanceof Type) {
            return (Type) value;
        } else if (value instanceof Value) {
            return ((Value) value).getType();
        } else {
            throw new IllegalStateException("Value is neither Type nor Value instance");
        }
    }

    @Override
    public void putType(K variableName, Type type) {
        // Stores the Type object directly, not the default value.
        dictionary.put(variableName, (V) type);
    }
    @Override
    public void declareVariable(K variableName, Type type) {
        dictionary.put(variableName,(V) type.getDefaultValue());
    }

    @Override
    public MyIDictionary<K, V> deepCopy() {
        MyDictionary<K, V> newDict = new MyDictionary<>();
        newDict.dictionary.putAll(this.dictionary);
        return newDict;
    }

    @Override
    public Value getValue(K variableName) {
        return (Value)dictionary.get(variableName);
    }

}
