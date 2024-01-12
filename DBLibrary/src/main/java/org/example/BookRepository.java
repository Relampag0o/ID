package org.example;

import java.io.Closeable;
import java.sql.*;
import java.util.LinkedList;

public class BookRepository {

    private Connection c;


    public BookRepository() {
        openConnection();

    }

    public static void showSQLError(SQLException e) {
        System.err.println("SQL error message: " + e.getMessage());
        System.err.println("SQL state: " + e.getSQLState());
        System.err.println("SQL error code: " + e.getErrorCode());
    }


    public static void close(Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void closeStatement(Statement s) {
        if (s != null) {
            try {
                s.close();
            } catch (SQLException sqlException) {
                showSQLError(sqlException);
            }
        }
    }

    public void openConnection() {
        String db = "biblioteca";
        String host = "localhost";
        String port = "3306";
        String urlConnection = "jdbc:mariadb://" + host + ":" + port + "/" + db;
        String user = "root";
        String password = "5856101097";
        try {
            this.c = DriverManager.getConnection(urlConnection, user, password);
            System.out.println("Connected");

        } catch (SQLException e) {
            showSQLError(e);

        }
    }

        public LinkedList<Book> getBooks() {
            LinkedList<Book> books = new LinkedList<>();
            PreparedStatement pst = null;
            try {
                pst = c.prepareStatement("SELECT * FROM libros");
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    books.add(new Book(rs.getInt("id"), rs.getString("titulo"), rs.getString("autor"), rs.getString("genero"), rs.getBoolean("prestado")));
                }
                System.out.println(books.size());
            } catch (SQLException e) {
                showSQLError(e);
            } finally {
                closeStatement(pst);

            }

            return books;
        }

    public void createBook(String title, String author, String genre, Boolean borrowed) {
        PreparedStatement pst = null;
        try {
            pst = c.prepareStatement("INSERT INTO libros (titulo, autor, genero, prestado) VALUES (?, ?, ?, ?)");
            pst.setString(1, title);
            pst.setString(2, author);
            pst.setString(3, genre);
            pst.setBoolean(4, borrowed);
            pst.executeUpdate();
        } catch (SQLException e) {
            showSQLError(e);
        } finally {
            closeStatement(pst);
        }
    }

    public void deleteBook(int id) {
        PreparedStatement pst = null;
        try {
            pst = c.prepareStatement("DELETE FROM libros WHERE id = ?");
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            showSQLError(e);
        } finally {
            closeStatement(pst);
        }


    }

    public void updateBook(int id, String title, String author, String genre) {
        PreparedStatement pst = null;
        try {
            pst = c.prepareStatement("UPDATE libros SET titulo = ?, autor = ?, genero = ?, prestado = ? WHERE id = ?");
            pst.setString(1, title);
            pst.setString(2, author);
            pst.setString(3, genre);
            pst.setBoolean(4, false);
            pst.setInt(5, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            showSQLError(e);
        } finally {
            closeStatement(pst);
        }
    }

}
