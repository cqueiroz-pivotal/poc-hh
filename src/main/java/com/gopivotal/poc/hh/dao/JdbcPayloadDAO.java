package com.gopivotal.poc.hh.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by cax on 2/21/14.
 * @author cax
 */
public class JdbcPayloadDAO implements PayloadDAO {

    private final Logger LOG = LoggerFactory.getLogger(JdbcPayloadDAO.class);

    private Connection conn = null;

    /**
     *
     * @param conn
     */
    public JdbcPayloadDAO(Connection conn){
        this.conn = conn;
    }

    @Override
    public void closeConnection(){
        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                LOG.error(">>>>>>Error closing connection<<<<<", e);
            }
        }
    }


// --Commented out by Inspection START (2/25/14, 7:25 AM):
//    @Override
//    public void insert(Payload payload) {
//
//        PreparedStatement pstm = null;
//
//        try{
//            conn.setAutoCommit(true);
//            pstm = conn.prepareStatement(SQL);
//            for (int j = 0; j < payload.data().length; j++) {
//                pstm.setObject(j + 1, payload.data()[j]);
//            }
//            pstm.executeUpdate();
//
//        }catch (SQLException e){
//            try {
//                if(conn!=null)
//                    conn.rollback();
//                LOG.error(">>>>>>Error inserting single payload<<<<<", e);
//                System.exit(-1);
//            } catch (SQLException e1) {
//                //NOTHING TO DO.
//            }
//
//        }
//        finally{
//            if(pstm!=null){
//                try {
//                    pstm.close();
//                } catch (SQLException e) {
//                    LOG.error("Error closing prepareStatement", e);
//                }
//            }
//
//        }
//
//        LOG.debug("single payload inserted.",this);
//    }
// --Commented out by Inspection STOP (2/25/14, 7:25 AM)

    @Override
    public void insertBatch(Payload[] payloads) {

        PreparedStatement pstm = null;

        try{

            conn.setAutoCommit(false);
            String SQL = "INSERT INTO app.erd_data values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pstm = conn.prepareStatement(SQL);
            for (Payload payload : payloads) {
                for (int j = 0; j < payload.data().length; j++) {
                    pstm.setObject(j + 1, payload.data()[j]);
                }
                pstm.addBatch();
            }
            pstm.executeBatch();
            conn.commit();

        }catch (SQLException e){
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
                    LOG.error("Error closing prepareStatement", e);
                }
            }

        }

        LOG.debug("batch inserted.",this);

    }


}
