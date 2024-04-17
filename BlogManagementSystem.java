// import java.sql.*;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.Scanner;


// public class BlogManagementSystem {
//     private static final String DB_URL = "jdbc:mysql://localhost:3306/blog";
//     private static final String DB_USER = "root";
//     private static final String DB_PASSWORD = "Pravin9521@#/";
//     public static void main(String[] args) {
//         try {
//             Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
//             initializeDatabase(connection);

//             UserDao userDao = new UserDao(connection);
//             PostDao postDao = new PostDao(connection);

//             BlogManagementSystem blogSystem = new BlogManagementSystem(userDao, postDao);
//             blogSystem.run();
//         } catch (SQLException e) {
//             e.printStackTrace();
//         }
//     }

//     private static void initializeDatabase(Connection connection) throws SQLException {
//         try (Statement statement = connection.createStatement()) {
//             // Create users table
//             statement.executeUpdate("CREATE TABLE IF NOT EXISTS users (" +
//                     "id INT AUTO_INCREMENT PRIMARY KEY," +
//                     "username VARCHAR(50) NOT NULL," +
//                     "password VARCHAR(255) NOT NULL)");
                    
    
//             // Create posts table
//             statement.executeUpdate("CREATE TABLE IF NOT EXISTS posts (" +
//                     "id INT AUTO_INCREMENT PRIMARY KEY," +
//                     "title VARCHAR(100) NOT NULL," +
//                     "content TEXT NOT NULL," +
//                     "author_id INT," +
//                     "timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP," + // Added timestamp column
//                     "FOREIGN KEY (author_id) REFERENCES users(id))");
//         }
//     }
    

//     private UserDao userDao;
//     private PostDao postDao;

//     public BlogManagementSystem(UserDao userDao, PostDao postDao) {
//         this.userDao = userDao;
//         this.postDao = postDao;
//     }

//     public void run() {
//         Scanner scanner = new Scanner(System.in);

//         while (true) {
//             System.out.println("1. Create User");
//             System.out.println("2. Create Post");
//             System.out.println("3. View Posts by User");
//             System.out.println("4. Exit");
//             System.out.print("Select an option: ");

//             int choice = scanner.nextInt();
//             scanner.nextLine(); // Consume the newline character

//             switch (choice) {
//                 case 1:
//                     createUser(scanner);
//                     break;
//                 case 2:
//                     createPost(scanner);
//                     break;
//                 case 3:
//                     viewPostsByUser(scanner);
//                     break;
//                 case 4:
//                     System.out.println("Exiting the program. Goodbye!");
//                     System.exit(0);
//                     break;
//                 default:
//                     System.out.println("Invalid option. Please try again.");
//             }
//         }
//     }

//     private void createUser(Scanner scanner) {
//         System.out.print("Enter username: ");
//         String username = scanner.nextLine();

//         System.out.print("Enter password: ");
//         String password = scanner.nextLine();

//         User user = new User(username, password);
//         userDao.createUser(user);
//         System.out.println("User created successfully!");
//     }

//     private void createPost(Scanner scanner) {
//         System.out.print("Enter title: ");
//         String title = scanner.nextLine();

//         System.out.print("Enter content: ");
//         String content = scanner.nextLine();

//         System.out.print("Enter author's username: ");
//         String username = scanner.nextLine();

//         User author = userDao.getUserByUsername(username);

//         if (author != null) {
//             Post post = new Post(title, content, author.getId());
//             postDao.createPost(post);
//             System.out.println("Post created successfully!");
//         } else {
//             System.out.println("User not found. Please create the user first.");
//         }
//     }

//     private void viewPostsByUser(Scanner scanner) {
//         System.out.print("Enter username: ");
//         String username = scanner.nextLine();

//         User author = userDao.getUserByUsername(username);

//         if (author != null) {
//             List<Post> posts = postDao.getPostsByUser(author.getId());

//             if (!posts.isEmpty()) {
//                 System.out.println("Posts by " + username + ":");
//                 for (Post post : posts) {
//                     System.out.println(post.getTitle() + " - " + post.getContent());
//                 }
//             } else {
//                 System.out.println(username + " has no posts yet.");
//             }
//         } else {
//             System.out.println("User not found.");
//         }
//     }
// }

// class User {
//     private int id;
//     private String username;
//     private String password;

//     // Constructor without 'id' parameter
//     public User(String username, String password) {
//         this.username = username;
//         this.password = password;
//     }

//     // Constructor with 'id' parameter
//     public User(int id, String username, String password) {
//         this.id = id;
//         this.username = username;
//         this.password = password;
//     }

//     public int getId() {
//         return id;
//     }

//     public void setId(int id) {
//         this.id = id;
//     }

//     public String getUsername() {
//         return username;
//     }

//     public String getPassword() {
//         return password;
//     }
// }




// class Post {
//     private int id;
//     private String title;
//     private String content;
//     private int authorId;
//     private Timestamp timestamp;

//     public Post(String title, String content, int authorId) {
//         this.title = title;
//         this.content = content;
//         this.authorId = authorId;
//     }

//      public int getId() {
//         return id;
//     }

//     public void setId(int id) {
//         this.id = id;
//     }

//     public String getTitle() {
//         return title;
//     }

//     public String getContent() {
//         return content;
//     }

//     public int getAuthorId() {
//         return authorId;
//     }

//     public Timestamp getTimestamp() {
//         return timestamp;
//     }

//     public void setTimestamp(Timestamp timestamp) {
//         this.timestamp = timestamp;
//     }
// }


// class UserDao {
//     private Connection connection;

//     public UserDao(Connection connection) {
//         this.connection = connection;
//     }

//     public void createUser(User user) {
//         try {
//             String query = "INSERT INTO users (username, password) VALUES (?, ?)";
//             try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
//                 preparedStatement.setString(1, user.getUsername());
//                 preparedStatement.setString(2, user.getPassword());
//                 preparedStatement.executeUpdate();

//                 try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
//                     if (generatedKeys.next()) {
//                         user.setId(generatedKeys.getInt(1));
//                     }
//                 }
//             }
//         } catch (SQLException e) {
//             e.printStackTrace();
//         }
//     }

//     public User getUserByUsername(String username) {
//         try {
//             String query = "SELECT * FROM users WHERE username = ?";
//             try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//                 preparedStatement.setString(1, username);

//                 try (ResultSet resultSet = preparedStatement.executeQuery()) {
//                     if (resultSet.next()) {
//                         return new User(
//                                 resultSet.getInt("id"),
//                                 resultSet.getString("username"),
//                                 resultSet.getString("password")
//                         );
//                     }
//                 }
//             }
//         } catch (SQLException e) {
//             e.printStackTrace();
//         }

//         return null;
//     }
// }

// class PostDao {
//     private Connection connection;

//     public PostDao(Connection connection) {
//         this.connection = connection;
//     }

//     public void createPost(Post post) {
//         try {
//             String query = "INSERT INTO posts (title, content, author_id) VALUES (?, ?, ?)";
//             try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
//                 preparedStatement.setString(1, post.getTitle());
//                 preparedStatement.setString(2, post.getContent());
//                 preparedStatement.setInt(3, post.getAuthorId());
//                 preparedStatement.executeUpdate();

//                 try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
//                     if (generatedKeys.next()) {
//                         post.setId(generatedKeys.getInt(1));
//                     }
//                 }
//             }
//         } catch (SQLException e) {
//             e.printStackTrace();
//         }
//     }

//     public List<Post> getPostsByUser(int authorId) {
//         List<Post> posts = new ArrayList<>();

//         try {
//             String query = "SELECT * FROM posts WHERE author_id = ?";
//             try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//                 preparedStatement.setInt(1, authorId);

//                 try (ResultSet resultSet = preparedStatement.executeQuery()) {
//                     while (resultSet.next()) {
//                         Post post = new Post(
//                                 resultSet.getString("title"),
//                                 resultSet.getString("content"),
//                                 resultSet.getInt("author_id")
//                         );
//                         post.setId(resultSet.getInt("id"));
//                         post.setTimestamp(resultSet.getTimestamp("timestamp"));
//                         posts.add(post);
//                     }
//                 }
//             }
//         } catch (SQLException e) {
//             e.printStackTrace();
//         }

//         return posts;
//     }
// }
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BlogManagementSystem {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/blog";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Pravin9521@#/";

    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            initializeDatabase(connection);

            UserDao userDao = new UserDao(connection);
            PostDao postDao = new PostDao(connection);

            BlogManagementSystem blogSystem = new BlogManagementSystem(userDao, postDao);
            blogSystem.run();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void initializeDatabase(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            // Create users table
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "username VARCHAR(50) NOT NULL," +
                    "password VARCHAR(255) NOT NULL)");

            // Create posts table
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS posts (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "title VARCHAR(100) NOT NULL," +
                    "content TEXT NOT NULL," +
                    "author_id INT," +
                    "timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP," + // Added timestamp column
                    "FOREIGN KEY (author_id) REFERENCES users(id))");
        }
    }

    private UserDao userDao;
    private PostDao postDao;

    public BlogManagementSystem(UserDao userDao, PostDao postDao) {
        this.userDao = userDao;
        this.postDao = postDao;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Create User");
            System.out.println("2. Create Post");
            System.out.println("3. View Posts by User");
            System.out.println("4. Update User");
            System.out.println("5. Delete User");
            System.out.println("6. Update Post");
            System.out.println("7. Delete Post");
            System.out.println("8. Exit");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    createUser(scanner);
                    break;
                case 2:
                    createPost(scanner);
                    break;
                case 3:
                    viewPostsByUser(scanner);
                    break;
                case 4:
                    updateUser(scanner);
                    break;
                case 5:
                    deleteUser(scanner);
                    break;
                case 6:
                    updatePost(scanner);
                    break;
                case 7:
                    deletePost(scanner);
                    break;
                case 8:
                    System.out.println("Exiting the program. Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void createUser(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = new User(username, password);
        userDao.createUser(user);
        System.out.println("User created successfully!");
    }

    private void createPost(Scanner scanner) {
        System.out.print("Enter title: ");
        String title = scanner.nextLine();

        System.out.print("Enter content: ");
        String content = scanner.nextLine();

        System.out.print("Enter author's username: ");
        String username = scanner.nextLine();

        User author = userDao.getUserByUsername(username);

        if (author != null) {
            Post post = new Post(title, content, author.getId());
            postDao.createPost(post);
            System.out.println("Post created successfully!");
        } else {
            System.out.println("User not found. Please create the user first.");
        }
    }

    private void viewPostsByUser(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        User author = userDao.getUserByUsername(username);

        if (author != null) {
            List<Post> posts = postDao.getPostsByUser(author.getId());

            if (!posts.isEmpty()) {
                System.out.println("Posts by " + username + ":");
                for (Post post : posts) {
                    System.out.println(post.getTitle() + " - " + post.getContent());
                }
            } else {
                System.out.println(username + " has no posts yet.");
            }
        } else {
            System.out.println("User not found.");
        }
    }

    private void updateUser(Scanner scanner) {
        System.out.print("Enter the ID of the user to update: ");
        int userId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        User existingUser = userDao.getUserById(userId);

        if (existingUser != null) {
            System.out.print("Enter new username: ");
            String newUsername = scanner.nextLine();

            System.out.print("Enter new password: ");
            String newPassword = scanner.nextLine();

            User updatedUser = new User(userId, newUsername, newPassword);
            userDao.updateUser(updatedUser);
            System.out.println("User updated successfully!");
        } else {
            System.out.println("User not found.");
        }
    }

    private void deleteUser(Scanner scanner) {
        System.out.print("Enter the ID of the user to delete: ");
        int userId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        User existingUser = userDao.getUserById(userId);

        if (existingUser != null) {
            userDao.deleteUser(userId);
            System.out.println("User deleted successfully!");
        } else {
            System.out.println("User not found.");
        }
    }

    private void updatePost(Scanner scanner) {
        System.out.print("Enter the ID of the post to update: ");
        int postId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        Post existingPost = postDao.getPostById(postId);

        if (existingPost != null) {
            System.out.print("Enter new title: ");
            String newTitle = scanner.nextLine();

            System.out.print("Enter new content: ");
            String newContent = scanner.nextLine();

            Post updatedPost = new Post(newTitle, newContent, existingPost.getAuthorId());
            updatedPost.setId(postId);
            postDao.updatePost(updatedPost);
            System.out.println("Post updated successfully!");
        } else {
            System.out.println("Post not found.");
        }
    }

    private void deletePost(Scanner scanner) {
        System.out.print("Enter the ID of the post to delete: ");
        int postId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        Post existingPost = postDao.getPostById(postId);

        if (existingPost != null) {
            postDao.deletePost(postId);
            System.out.println("Post deleted successfully!");
        } else {
            System.out.println("Post not found.");
        }
    }
}

class User {
    private int id;
    private String username;
    private String password;

    // Constructor without 'id' parameter
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Constructor with 'id' parameter
    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

class Post {
    private int id;
    private String title;
    private String content;
    private int authorId;
    private Timestamp timestamp;

    public Post(String title, String content, int authorId) {
        this.title = title;
        this.content = content;
        this.authorId = authorId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public int getAuthorId() {
        return authorId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}

class UserDao {
    private Connection connection;

    public UserDao(Connection connection) {
        this.connection = connection;
    }

    public void createUser(User user) {
        try {
            String query = "INSERT INTO users (username, password) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, user.getUsername());
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.executeUpdate();

                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setId(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUser(User user) {
        try {
            String query = "UPDATE users SET username = ?, password = ? WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, user.getUsername());
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.setInt(3, user.getId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(int userId) {
        try {
            String query = "DELETE FROM users WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, userId);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getUserById(int userId) {
        try {
            String query = "SELECT * FROM users WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, userId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return new User(
                                resultSet.getInt("id"),
                                resultSet.getString("username"),
                                resultSet.getString("password")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public User getUserByUsername(String username) {
        try {
            String query = "SELECT * FROM users WHERE username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return new User(
                                resultSet.getInt("id"),
                                resultSet.getString("username"),
                                resultSet.getString("password")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}

class PostDao {
    private Connection connection;

    public PostDao(Connection connection) {
        this.connection = connection;
    }

    public void createPost(Post post) {
        try {
            String query = "INSERT INTO posts (title, content, author_id) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, post.getTitle());
                preparedStatement.setString(2, post.getContent());
                preparedStatement.setInt(3, post.getAuthorId());
                preparedStatement.executeUpdate();

                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        post.setId(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePost(Post post) {
        try {
            String query = "UPDATE posts SET title = ?, content = ? WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, post.getTitle());
                preparedStatement.setString(2, post.getContent());
                preparedStatement.setInt(3, post.getId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePost(int postId) {
        try {
            String query = "DELETE FROM posts WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, postId);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Post> getPostsByUser(int authorId) {
        List<Post> posts = new ArrayList<>();

        try {
            String query = "SELECT * FROM posts WHERE author_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, authorId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Post post = new Post(
                                resultSet.getString("title"),
                                resultSet.getString("content"),
                                resultSet.getInt("author_id")
                        );
                        post.setId(resultSet.getInt("id"));
                        post.setTimestamp(resultSet.getTimestamp("timestamp"));
                        posts.add(post);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return posts;
    }

    public Post getPostById(int postId) {
        try {
            String query = "SELECT * FROM posts WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, postId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        Post post = new Post(
                                resultSet.getString("title"),
                                resultSet.getString("content"),
                                resultSet.getInt("author_id")
                        );
                        post.setId(resultSet.getInt("id"));
                        post.setTimestamp(resultSet.getTimestamp("timestamp"));
                        return post;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
