package tw.kane.osu4j;

public enum Mode {
    OSU("standard"),
    MANIA("mania"),
    CATCH("fruits"),
    TAIKO("taiko");

    private final String name;

    Mode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
