package com.fast.runner.session;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import com.fast.runner.session.data.DataWorker;
import com.fast.runner.session.data.IData;

/**
 * 
 * @author Syed Imtiaz
 * @contact imtiazshah07@gmail.com
 * @dated 27 September 2016
 * @since 1.0
 * 
 */

public interface SessionFactory {
	@SuppressWarnings("rawtypes")
	public <T> List<? extends IData> getEntityList(String statement, IData<T> dataBean, Object... statementParams)
			throws SQLException;

	@SuppressWarnings("rawtypes")
	public <T extends IData> T getEntity(String statement, IData dataBean, Object... statementParams)
			throws SQLException;

	public BigDecimal getPrimitive(String statement, Object... args) throws SQLException;

	public void executeDataWorkers(DataWorker dataWorker) throws Exception;

	public DataSource getDataSource();
}
