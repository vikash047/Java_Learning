package Interview.IO.RateLimiter.Models;

import java.util.Objects;

public class ClientId {
    private final String Id;

    public ClientId(String id) {
        Id = id;
    }

    public String getId() {
        return Id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientId clientId = (ClientId) o;
        return Id == clientId.Id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id);
    }
}
