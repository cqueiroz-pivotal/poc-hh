package com.gopivotal.poc.hh.dao;

import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by queirc on 2/21/14.
 */
@Component
public class JdbcPayloadDAO implements PayloadDAO {

    private final Logger LOG = LoggerFactory.getLogger(JdbcPayloadDAO.class);

    private final String SQL = "INSERT INTO app.erd_data values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    @Autowired
    private HikariDataSource ds;


    @Override
    public void insert(Payload payload) {
        Connection conn = null;
        PreparedStatement pstm = null;
        try{
            conn = ds.getConnection();
            conn.setAutoCommit(true);
            pstm = conn.prepareStatement(SQL);
            for (int j = 0; j < payload.data().length; j++) {
                pstm.setObject(j + 1, payload.data()[j]);
            }
            pstm.executeUpdate();

        }catch (Exception e){
            try {
                if(conn!=null)
                    conn.rollback();
                LOG.error(">>>>>>Error inserting single payload<<<<<", e);
                System.exit(-1);
            } catch (SQLException e1) {
                //NOTHING TO DO.
            }

        }
        finally{
            if(pstm!=null){
                try {
                    pstm.close();
                } catch (SQLException e) {
                    //NOTHING TO DO
                }
            }
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOG.error(">>>>>>Error closing connection<<<<<", e);
                }
            }
        }

        LOG.debug("single payload inserted.",this);
    }

    @Override
    public void insertBatch(Payload[] payloads) {

        Connection conn = null;
        PreparedStatement pstm = null;

        try{
            conn = ds.getConnection();
            conn.setAutoCommit(false);
            pstm = conn.prepareStatement(SQL);
            for(int i = 0; i < payloads.length;i++){
                Payload payload = payloads[i];

                for (int j = 0; j < payload.data().length; j++) {
                    pstm.setObject(j + 1, payload.data()[j]);
                }
                pstm.addBatch();
            }
            pstm.executeBatch();
            conn.commit();

        }catch (Exception e){
            try {
                if(conn!=null)
                    conn.rollback();
                LOG.error(">>>>>>Error inserting batch<<<<<", e);
                System.exit(-1);
            } catch (SQLException e1) {
                //NOTHING TO DO.
            }

        }
        finally{
            if(pstm!=null){
                try {
                    pstm.close();
                } catch (SQLException e) {
                    //NOTHING TO DO
                }
            }
//            if(conn!=null){
//                try {
//                    conn.close();
//                } catch (SQLException e) {
//                    LOG.error(">>>>>>Error closing connection<<<<<", e);
//                }
//            }

        }

        LOG.debug("batch inserted.",this);

    }


}
