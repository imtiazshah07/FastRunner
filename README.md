
## FAST RUNNER

Fast Runner is based Query Runner along with spring libraries.

##Initial Setup and configuration

### Spring xml configuration:
     
     Scanning fast runner component:
     <context:component-scan base-package="com.fast.runner" />

     Initiate jndi factory bean:
     <beans:bean id="dataSource" class="org.springframework.jndi.JndiObjectFactoryBean">
   	    <beans:property name="jndiName" value="<<jndi>>"/>
      </beans:bean>
      
      Initiate transaction manager:
      <beans:bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
              <beans:property name="dataSource" ref="dataSource" />
      </beans:bean>

###  Spring Annotation configuration
      
      @ComponentScan(basePackages = "com.fast.runner")

      @Bean
      public JndiObjectFactoryBean jndiObjectFactoryBean() {
          JndiObjectFactoryBean jndi = new JndiObjectFactoryBean();
          jndi.setJndiName("<<JNDI>>");		
          return jndi;
      }

      @Bean
      public DataSourceTransactionManager dSourceManager() {
          DataSourceTransactionManager dSourceManager = new DataSourceTransactionManager();
          dSourceManager.setDataSource(jndiObjectFactoryBean());
          return dSourceManager;
      }

## Usage

### Entity POJO Implementation

      Creating entity beans:
      @SuppressWarnings("rawtypes")
      public class <<Entity>>  implements java.io.Serializable,IData{
        // Entity colums getter and setters.
      }

### DAO Implementation
      
      Creating DAO interface:
      public interface <<DAO Inteface>>DAO {
        // List of abstract methods
        public <<Entity>> findById(Integer Id);
      }

      Implementation of DAO interface:
      @Repository(value = "<<DAO Class>>DAOImpl")
      public class <<DAO Class>>DAOImpl implements <<DAO Interface>>DAO{
        @Value("${query.findById}") private String findById;
        @Value("${query.save}") private String save;

        @Autowired 
        SessionFactory session;
        public SessionFactory getSession(){
          return session;
        }

        @Override
        public <<Entity>> findById(Integer Id) {
          try {
            <<Entity>> entity = getSession().getEntity(findById,new <<Entity>>(),Id);
            return entity;
          } catch (SQLException e) {
            e.printStackTrace();
          }
          return null;
        }


        @Override
        public void save(<<Entity>> entity) throws Exception {
           Object []statementParams = new Object[]{entity.getId(),entity.getXXProp()};
           DataWorker dataWorker =  new DataWorker(save, null,statementParams);
           try {
            session.executeDataWorkers(dataWorker);
          } catch (SQLException e) {
            throw e;
          }
        }
      }

### Add JAR to maven repository
      mvn install:install-file -Dfile=FastRunner-1.0.jar -DgroupId=com.fast -DartifactId=Fast-Runner -Dversion=1.0 -Dpackaging=jar
      
### Include the depency in pom
      	<dependency>
			<groupId>com.fast</groupId>
			<artifactId>Fast-Runner</artifactId>
			<version>1.1</version>
		</dependency>
      
      

Enjoy!


