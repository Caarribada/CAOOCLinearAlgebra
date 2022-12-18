package interfaces;

public interface AdminInterface {
    public void createUser();
    public void updateUser(String user);
    public void deleteUser(String user);
    public void checkUserHistory(String user);
}
