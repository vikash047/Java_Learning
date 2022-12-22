package Interview.IO.SplitWiseApplication.Models;

import java.util.List;

public class CloseGroup {
    private final String name;
    private final String desc;

    private final String imageUrl;

    private final List<String> users;

    public CloseGroup(String name, String desc, String imageUrl, List<String> users) {
        this.name = name;
        this.desc = desc;
        this.imageUrl = imageUrl;
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public List<String> getUsers() {
        return users;
    }
}