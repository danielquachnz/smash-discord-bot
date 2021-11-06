package au.com.ausmash.model;

public enum NzRegion {
    AUCK("Auckland"),
    CHCH("Christchurch"),
    DUN("Dunedin"),
    HAM("Hamilton"),
    NZ("New Zealand"),
    WEL("Wellington");

    private String description;

    NzRegion(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return String.format("%s: %s", this.description, this.name());
    }
}