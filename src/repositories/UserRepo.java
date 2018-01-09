package repositories;

public class UserRepo {
    

    public boolean register(String username, String password) {
        if (!checkIfNameExists(username))
        {
            try {
                String query = "INSERT into user(username, password) VALUES(?, ?);";
                IConnection connection = new ConnectionManager();
                Connection conn = connection.getConnection();
                PreparedStatement preparedStmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                preparedStmt.setString(1, username.toLowerCase());
                preparedStmt.setString(2,password);
                preparedStmt.execute();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
        else {
            return false;
        }
    }
}
