package hawhamburg.api.endpoints;

public enum BlackboardServiceEndPoints implements BasicEndPoint {

    TAVERNA("/taverna/adventurers"),
    GROUP("/taverna/groups"),
    USERS("/users"),
    QUESTS("/blackboard/quests"),
    LOGIN("/login"),
    WHOAMI("/whoami"),
    VISITS("/visits");

    private String resource;

    BlackboardServiceEndPoints(final String resource)
    {
        this.resource = resource;
    }

    @Override
    public String getEndpoint() {
        return resource;
    }
}
