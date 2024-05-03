package org.generation.italy.introjdbc.model.repositories.abstractions;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface RowMapper<T> {
<<<<<<< HEAD

    T mapRow(ResultSet rs) throws SQLException;

=======
    T mapRow(ResultSet rs) throws SQLException;
>>>>>>> main
}
