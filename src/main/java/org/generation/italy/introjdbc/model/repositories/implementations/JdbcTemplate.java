package org.generation.italy.introjdbc.model.repositories.implementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.generation.italy.introjdbc.model.Customer;
import org.generation.italy.introjdbc.model.exceptions.DataException;
import org.generation.italy.introjdbc.model.repositories.abstractions.PreparedStatementSetter;
import org.generation.italy.introjdbc.model.repositories.abstractions.RowMapper;
import org.generation.italy.introjdbc.utils.ConnectionUtils;

public class JdbcTemplate<T> {

    public List<T> query(String sql, PreparedStatementSetter psSetter, RowMapper<T> mapper) throws DataException{
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

    
    public List<T> query(String sql, RowMapper<T> mapper, Object... params) throws DataException{
        try(
            Connection c = ConnectionUtils.createConnection();
            PreparedStatement ps = c.prepareStatement(sql);
        ){
                for(int i = 0; i < params.length; i++){
                    ps.setObject(i+1, params[i]);
                }
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

    public Optional<T> queryForObject(String sql, RowMapper<T> mapper, Object... params) throws DataException{
        try(
            Connection c = ConnectionUtils.createConnection();
            PreparedStatement ps = c.prepareStatement(sql);
        ){
                for(int i = 0; i < params.length; i++){
                    ps.setObject(i+1, params[i]);
                }
                try(ResultSet rs = ps.executeQuery()){
                    if(rs.next()){
                        return Optional.of(mapper.mapRow(rs));
                    }
                    return Optional.empty();
                }
                
            } catch(SQLException e){
                throw new DataException("Errore nella query", e);
            }
    }

    public int update(String sql, Object... params) throws DataException{
        try(
            Connection c = ConnectionUtils.createConnection();
            PreparedStatement ps = c.prepareStatement(sql);
        ){
                for(int i = 0; i < params.length; i++){
                    ps.setObject(i+1, params[i]);
                }
                    return ps.executeUpdate();
                
            } catch(SQLException e){
                throw new DataException("Errore nella query", e);
            }
    }

    public void insert(String sql, KeyHolder key, Object... params) throws DataException{
        try(
            Connection c = ConnectionUtils.createConnection();
            PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ){
                for(int i = 0; i < params.length; i++){
                    ps.setObject(i+1, params[i]);
                }
                ps.executeUpdate();
                try(ResultSet rs = ps.getGeneratedKeys()){
                    if(rs.next()){
                        Number n = (Number) rs.getObject(1);
                        key.setKey(n);
                    }
                }
                
            } catch(SQLException e){
                throw new DataException("Errore nella query", e);
            }
    }
}
