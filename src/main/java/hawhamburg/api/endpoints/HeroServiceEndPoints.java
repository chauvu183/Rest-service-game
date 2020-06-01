package hawhamburg.api.endpoints;

public enum HeroServiceEndPoints implements BasicEndPoint {
    HIRINGS("/hirings"),
    GROUP("/group"),
    MESSAGES("/messages"),
    ASSIGNMENTS("/assignments");

    private String path;
    HeroServiceEndPoints(String path){this.path = path;}

    @Override
    public String getEndpoint() {
        return path;
    }
}
