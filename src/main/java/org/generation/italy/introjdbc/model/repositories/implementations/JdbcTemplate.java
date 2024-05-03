package org.generation.italy.introjdbc.model.repositories.implementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.generation.italy.introjdbc.model.Customer;
import org.generation.italy.introjdbc.model.exceptions.DataException;
import org.generation.italy.introjdbc.model.repositories.abstractions.PreparedStatementSetter;
import org.generation.italy.introjdbc.model.repositories.abstractions.RowMapper;
import org.generation.italy.introjdbc.utils.ConnectionUtils;

public class JdbcTemplate<T> {

    List<T> query(String sql, PreparedStatementSetter psSetter, RowMapper<T> mapper) throws DataException{
        try(
            Connection c = ConnectionUtils.createConnection();
            PreparedStatement ps = c.prepareStatement(sql);
        ){
                psSetter.setParameters(ps);
                try(ResultSet rs = ps.executeQuery()){
                    List<T> cats = new ArrayList<>();
                    while(rs.next()){
                        cats.add(mapper.mapRow(rs));
                    }
                    return cats;
                }
                
            } catch(SQLException e){
                throw new DataException("Errore nella query", e);
            }
    }

}
