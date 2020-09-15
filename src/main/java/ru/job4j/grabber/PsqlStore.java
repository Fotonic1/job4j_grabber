package ru.job4j.grabber;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PsqlStore implements Store, AutoCloseable {

    private Connection cnn;

    public PsqlStore(Properties cfg) {
        try {
            Class.forName(cfg.getProperty("driver_class_name"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        try {
            cnn = DriverManager.getConnection(cfg.getProperty("url"),
                    cfg.getProperty("user"),
                    cfg.getProperty("password"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(Post post) {
        try (PreparedStatement st = cnn
                .prepareStatement("insert into post(name, description, link, created) values (?, ?, ?, ?);")) {
            st.setString(1, post.getName());
            st.setString(2, post.getDescription());
            st.setString(3, post.getLink());
            st.setTimestamp(4, Timestamp.valueOf(post.getCreated()));
            st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Post> getAll() {
        List<Post> list = new ArrayList<>();
        try (PreparedStatement st = cnn
                .prepareStatement("select * from post;")) {
            ResultSet rsl = st.executeQuery();
            while (rsl.next()) {
                list.add(queryToPost(rsl));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Post findById(int id) {
        try (PreparedStatement st = cnn
                .prepareStatement("select * from post where id = ?;")) {
            st.setInt(1, id);
            ResultSet rsl = st.executeQuery();
            rsl.next();
            return queryToPost(rsl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Post queryToPost(ResultSet rsl) throws SQLException {
            Post post = new Post();
            post.setId(rsl.getInt("id"));
            post.setName(rsl.getString("name"));
            post.setDescription(rsl.getString("description"));
            post.setLink(rsl.getString("link"));
            post.setCreated(rsl.getTimestamp("created").toLocalDateTime());
            return post;
    }

    @Override
    public void close() throws Exception {
        if (cnn != null) {
            cnn.close();
        }
    }
}
