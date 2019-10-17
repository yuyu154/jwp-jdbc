package nextstep.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class ListMapper<T> implements JdbcMapper {

    @Override
    public List<T> mapped(ResultSet resultSet) throws SQLException {
        List<T> rows = new ArrayList<>();
        while (resultSet.next()) {
            T user = createRow();
            rows.add(user);
        }

        return rows;
    }

    protected abstract T createRow();
}