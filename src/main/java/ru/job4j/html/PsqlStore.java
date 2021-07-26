package ru.job4j.html;

import java.io.InputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.quartz.AlertRabbit;

public class PsqlStore implements Store, AutoCloseable {
    private static final Logger LOG = LoggerFactory.getLogger(AlertRabbit.class.getName());
    private Connection cn;

    public PsqlStore(Properties cfg) {
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
            try {
                cn = DriverManager.getConnection(
                        cfg.getProperty("url"),
                        cfg.getProperty("username"),
                        cfg.getProperty("password")
                );
            } catch (Exception ge) {
                LOG.error("Error DriverManager.getConnection", ge);
            }
        } catch (Exception de) {
            LOG.error("Error set Class.forName(\"jdbc.driver\")", de);
        }
    }

    @Override
    public void save(Post post) {
        try (PreparedStatement statement = cn.prepareStatement("insert into post(name, link, text, created) values(?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {
            try {
                statement.setString(1, post.getTitle());
                statement.setString(2, post.getLink());
                statement.setString(3, post.getDescription());
                statement.setTimestamp(4, Timestamp.valueOf(post.getCreated()));
                statement.execute();
            } catch (Exception ce) {
                LOG.error("UnsupportedEncodingException", ce);
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    post.setId(generatedKeys.getInt(1));
                }
            } catch (Exception geK) {
                geK.printStackTrace();
            }
        } catch (Exception pe) {
            LOG.error("Error saving to table post", pe);
        }
    }

    @Override
    public List<Post> getAll() {
        List<Post> posts = new ArrayList<>();
        try (PreparedStatement statement = cn.prepareStatement("select * from post")) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String title = resultSet.getString("name");
                    String link = resultSet.getString("link");
                    String description = resultSet.getString("text");
                    LocalDateTime created = (resultSet.getTimestamp("created").toLocalDateTime());
                    posts.add(new Post(id, title, link, description, created));
                }
            }
        } catch (Exception pe) {
            LOG.error("Error select from table post", pe);
        }
        return posts;
    }

    @Override
    public Post findById(int id) {
        try (PreparedStatement statement = cn.prepareStatement("select * from post where id = ?")) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String title = resultSet.getString("name");
                    String link = resultSet.getString("link");
                    String description = resultSet.getString("text");
                    LocalDateTime created = (resultSet.getTimestamp("created").toLocalDateTime());
                    return new Post(id, title, link, description, created);
                }
            }
        } catch (Exception pe) {
            LOG.error("Error select from table post, with filtering by id", pe);
        }
        return null;
    }

    @Override
    public void close() throws Exception {
        if (cn != null) {
            cn.close();
        }
    }

    public static void main(String[] args) {
        try (InputStream in = PsqlStore.class.getClassLoader().getResourceAsStream("PsqlStore.properties")) {
            Properties cfg = new Properties();
            cfg.load(in);
            PsqlStore psqlStore = new PsqlStore(cfg);
            DateTimeParser dateTimeParser = new SqlRuDateTimeParser();
            SqlRuParse sqlRuParse = new SqlRuParse(dateTimeParser);
            Post savePost = sqlRuParse.detail("https://www.sql.ru/forum/1336341/java-razrabotchik-v-finteh-kompaniu");
            System.out.println(savePost);
            psqlStore.save(savePost);
            Post findPost = psqlStore.findById(516);
            System.out.println(findPost);
        } catch (Exception e) {
            LOG.error("Error getResourceAsStream(\"PsqlStore.properties\")", e);
        }
    }
}