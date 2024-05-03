package org.generation.italy.introjdbc.model.repositories.abstractions;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface PreparedStatementSetter {

    void setParamitres(PreparedStatement ps) throws SQLException;
}
