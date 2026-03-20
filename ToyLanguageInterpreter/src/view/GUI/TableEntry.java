package view.GUI;

public class TableEntry<K, V> {
    private final K key;
    private final V value;
    public TableEntry(K key, V value) { this.key = key; this.value = value; }
    public K getKey() { return key; }
    public V getValue() { return value; }
}