package com.fast.runner.session;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fast.runner.session.data.DataWorker;
import com.fast.runner.session.data.IData;
import com.fast.runner.session.data.UsefulRowProcessor;

/**
 * 
 * @author Syed Imtiaz
 * @contact imtiazshah07@gmail.com
 * @dated 27 September 2016
 * @since 1.0
 * 
 */

@Repository
public class SessionFactoryImpl implements SessionFactory {
	private static final Logger logger = LoggerFactory.getLogger(SessionFactoryImpl.class);

	//@Autowired
	private DataSource dataSource;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> List<? extends IData> getEntityList(String statement, IData<T> dataBean, Object... statementParams)
			throws SQLException {
		logger.info("In SessionFactoryImpl.getEntityList, Executing SQL : " + statement);
		try {
			QueryRunner runner = new QueryRunner(getDataSource());

			ResultSetHandler<List<? extends IData>> handle = new BeanListHandler(dataBean.getClass(),
					new BasicRowProcessor(new UsefulRowProcessor()));
			return runner.query(statement, handle, statementParams);
		} catch (Exception n) {
			logger.error("SessionFactoryImpl.getEntityList, Executing SQL : " + statement + ", Exception :"
					+ n.getMessage());
			throw new SQLException(n.getMessage());
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T extends IData> T getEntity(String statement, IData dataBean, Object... statementParams)
			throws SQLException {
		logger.info("In SessionFactoryImpl.getEntity, Executing SQL : " + statement);
		try {
			QueryRunner runner = new QueryRunner(getDataSource());
			ResultSetHandler<T> handle = new BeanHandler(dataBean.getClass(),
					new BasicRowProcessor(new UsefulRowProcessor()));
			return runner.query(statement, handle, statementParams);
		} catch (Exception n) {
			logger.error(
					"SessionFactoryImpl.getEntity, Executing SQL : " + statement + ", Exception :" + n.getMessage());
			throw new SQLException(n.getMessage());
		}
	}

	public BigDecimal getPrimitive(String statement) throws SQLException {
		return getPrimitive(statement, null);
	}

	public BigDecimal getPrimitive(String statement, Object... args) throws SQLException {
		logger.info("In SessionFactoryImpl.getPrimitive, Executing SQL : " + statement);
		try {
			QueryRunner runner = new QueryRunner(getDataSource());
			ResultSetHandler<BigDecimal> handle = new ScalarHandler<BigDecimal>();
			return runner.query(statement, handle, args);
		} catch (Exception n) {
			logger.error(
					"SessionFactoryImpl.getPrimitive, Executing SQL : " + statement + ", Exception :" + n.getMessage());
			throw new SQLException(n.getMessage());
		}

	}

	public void executeDataWorkers(DataWorker dataWorker) throws Exception {
		logger.info("In SessionFactoryImpl.executeDataWorkers, Executing SQL : " + dataWorker.getStatement());
		Connection connection = dataSource.getConnection();
		connection.setAutoCommit(false);
		try {
			QueryRunner runner = new QueryRunner(getDataSource());
			runner.update(dataWorker.getStatement(), dataWorker.getStatementParams());
			connection.commit();
		} catch (Exception n) {
			logger.error("SessionFactoryImpl.getPrimitive, Executing SQL : " + dataWorker.getStatement()
					+ ", Exception :" + n.getMessage());

			try {
				connection.rollback();
			} finally {
				connection.close();
			}
			throw new SQLException(n.getMessage());
		} finally {
			connection.close();
		}
	}

}
