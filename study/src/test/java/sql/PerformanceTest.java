package sql;

import nextstep.jdbc.mapper.RowMapper;
import nextstep.jdbc.template.JdbcTemplate;
import org.junit.jupiter.api.Test;
import slipp.support.db.ConnectionManager;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTimeout;

public class PerformanceTest {

    @Test
    public void test() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(ConnectionManager.getDataSource());
        final String sql = "SELECT hobby, (COUNT(hobby) / (SELECT COUNT(*)\n" +
                "FROM survey_results_public)) as ratio\n" +
                "FROM survey_results_public\n" +
                "GROUP BY hobby";

        RowMapper<Hobby> rowMapper = resultSet -> {
//            Hobby hobby = new Hobby();
//            hobby.setYes(resultSet.getDouble(2));
//            if (resultSet.next()) {
//                hobby.setNo(resultSet.getDouble(2));
//            }
//            return hobby;
            return null;
        };

        Hobby hobby = jdbcTemplate.executeForObject(sql, rowMapper);

        assertTimeout(Duration.ofMillis(150L), () -> jdbcTemplate.executeForObject(sql, rowMapper));
    }
}
