package PutclubHelper.crawler;

public enum Material_Type {
    Special(26), Standard(27);
    private final int type;
    private Material_Type(int type) {
        this.type = type;
    }
    public int Type() {
        return this.type;
    }
    public String toString() {
        return String.valueOf(type);
    }
}
