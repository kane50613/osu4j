package tw.kane.osu4j;

public enum ScoreType {
    BEST("best"),
    FIRSTS("firsts"),
    RECENT("recent");

    private String name;

    ScoreType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
