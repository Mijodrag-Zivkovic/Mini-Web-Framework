package framework.bussineslogic.model;

public class User {

    private int id;
    private String name;
    private boolean premiumUser;

    public User(int id, String name, boolean premiumUser) {
        this.id = id;
        this.name = name;
        this.premiumUser = premiumUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPremiumUser() {
        return premiumUser;
    }

    public void setPremiumUser(boolean premiumUser) {
        this.premiumUser = premiumUser;
    }
}
