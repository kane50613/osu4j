package tw.kane.osu4j;

public enum RankedStatus {
    GRAVEYARD("graveyard"),
    WIP("wip"),
    PENDING("pending"),
    RANKED("ranked"),
    APPROVED("approved"),
    QUALIFIED("qualified"),
    LOVED("loved");

    private final String name;

    RankedStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
