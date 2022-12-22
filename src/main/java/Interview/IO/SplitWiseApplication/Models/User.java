package Interview.IO.SplitWiseApplication.Models;

public class User {
    private final String name;
    private final String id;
    private final String bio;
    private final String imageUrl;

    public User(String name, String id, String bio, String imageUrl) {
        this.name = name;
        this.id = id;
        this.bio = bio;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getBio() {
        return bio;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
