Rename build.sample.properties to build.properties and configure it for your database. Add application to tomcat (might need to right click -> maven -> update project configuration for exlipse maven plugin), 
start and go to http://localhost:8080/happyfaces/login.jsf
User/password: admin/admin (can change in applicationContext-security.xml

If you prefer you can populate sample data run "mvn properties:read-project-properties dbunit:operation" command (run command in same dir as project's pom.xml).
Import.sql on startup is used with create-drop by default for data population.

To create project site with javadocs, source code, findbugs, checkstyle etc run mvn site:run (url: http://localhost:port/)

To remove current sample application:
Delete Customer.java, CustomerPerk.java, Account.java, Contacts.java, Operation.java, OperationType.java, City.java from domain package
Delete CustomerBean.java, AccountBean.java, OperationBean.java, CityBean.java from beans package
Delete AccountService.java, CustomerService.java, OperationService.java, CityService.java, 
       IAccountService.java, ICustomerService.java, IOperationService.java, ICityService.java from services package
Delete AccountRepository.java, CustomerRepository.java, OperationRepository.java, CityRepository.java from repositories package
Delete accountEdit.xhtml, accounts.xhtml, operationEdit.xhtml, operations.xhtml, customerEdit.xhtml, customers.xhtml from src/main/webapp/pages/
Delete entries from demoDatabase.xml(if u use it) and import.sql
Change menu.xhtml
Remove messages from messages.properties
Remove faces-config.xml navigation rules.

After those steps you are good to go further with your own application without any sight of demo files.