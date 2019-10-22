package nextstep.jdbc.template;

import nextstep.jdbc.exception.JdbcTemplateSqlException;
import nextstep.jdbc.mapper.TableMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class JdbcTemplate {
    private final Connection con;

    public JdbcTemplate(Connection con) {
        this.con = con;
    }

    public void update(String sql, Object... parameters) {
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            setParameters(pstmt, parseAsList(parameters));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new JdbcTemplateSqlException(e);
        }
    }

    public <T> T execute(String sql, TableMapper<T> mapper, Object... parameters) {
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            setParameters(pstmt, parseAsList(parameters));
            ResultSet resultSet = pstmt.executeQuery();
            return mapper.create(resultSet);
        } catch (SQLException e) {
            throw new JdbcTemplateSqlException(e);
        }
    }

    private List<Object> parseAsList(Object... parameters) {
        if (Objects.nonNull(parameters)) {
            return Arrays.asList(parameters);
        }
        return Collections.emptyList();
    }

    private void setParameters(PreparedStatement pstmt, List<Object> parameters) throws SQLException {
        for (int i = 0; i < parameters.size(); i++) {
            pstmt.setObject(i + 1, parameters.get(i));
        }
    }
}
