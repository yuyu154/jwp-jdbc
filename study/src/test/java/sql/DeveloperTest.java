package sql;

import nextstep.jdbc.mapper.ObjectMapper;
import nextstep.jdbc.mapper.RowMapper;
import nextstep.jdbc.mapper.TableMapper;
import nextstep.jdbc.template.JdbcTemplate;
import org.junit.jupiter.api.Test;
import slipp.support.db.ConnectionManager;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTimeout;

public class DeveloperTest {

    @Test
    public void test() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(ConnectionManager.getConnection());
        final String sql = "SELECT COUNT(*)\n" +
                "FROM survey_results_public\n";

        RowMapper<Hobby> rowMapper = resultSet -> {
            Hobby hobby = new Hobby();
            if (resultSet.next()) {
                hobby.setYes(resultSet.getDouble(1));
                hobby.setNo(resultSet.getDouble(1));
            }
            return hobby;
        };

        TableMapper objectMapper = new ObjectMapper(rowMapper);
        Hobby hobby = jdbcTemplate.execute(sql, new ObjectMapper<>(rowMapper), null);
//        assertThat(hobby.getYes()).isEqualTo(79897);
//        assertThat(hobby.getNo()).isEqualTo(18958);

        assertTimeout(Duration.ofMillis(100L), () ->
                jdbcTemplate.execute(
                        sql, objectMapper
                ));
    }
}
