package com.fast.runner.session.data;

/**
 * 
 * @author Syed Imtiaz
 * @contact imtiazshah07@gmail.com
 * @dated 27 September 2016
 * @since 1.0
 * 
 */
public class DataWorker {

	private String statement;
	private IData iData;
	private Object[] statementParams;

	public DataWorker(String statement, IData iData, Object... statementParams) {
		super();
		this.statement = statement;
		this.iData = iData;
		this.statementParams = statementParams;
	}

	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}

	public IData getiData() {
		return iData;
	}

	public void setiData(IData iData) {
		this.iData = iData;
	}

	public Object[] getStatementParams() {
		return statementParams;
	}

	public void setStatementParams(Object... statementParams) {
		this.statementParams = statementParams;
	}

}
