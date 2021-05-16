package tw.kane.osu4j;

public enum Mode {
    OSU("osu"),
    MANIA("mania"),
    CATCH("fruits"),
    TAIKO("taiko");

    private String name;

    Mode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
