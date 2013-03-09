Rename build.sample.properties to build.properties and configure it for your database. Add application to tomcat (might need to right click -> maven -> update project configuration for exlipse maven plugin), 
start and go to http://localhost:8080/IgnasTraderGui/login.jsf
User/password: aaa/aaa (can change in applicationContext-security.xml

To populate sample data run "mvn properties:read-project-properties dbunit:operation" command (run command in same dir as project's pom.xml).

To create project site with javadocs, source code, findbugs, checkstyle etc run mvn site:run (url: http://localhost:8080/)

To remove current sample application:
Delete Customer.java, CustomerPerk.java, Account.java, Contacts.java, Operation.java, OperationType.java, City.java from domain package
Delete CustomerBean.java, AccountBean.java, OperationBean.java, CityBean.java from beans package
Delete AccountService.jav, CustomerService.java, OperationService.java, IAccountService.jav, ICustomerService.java, IOperationService.java from services package
Delete same services from applicationContext.xml
Delete accountEdit.xhtml, accounts.xhtml, operationEdit.xhtml, operations.xhtml, customerEdit.xhtml, customers.xhtml from src/main/webapp/pages/
Delete entries from demoDatabase.xml
Change menu.xhtml
Remove messages from messages.properties
Remove faces-config.xml navigation rules.

After those steps you are good to go further with your own application without any sight of demo files. 

TODO: 
1. 

Something more to add:
1. More list types: Maybe list of checkboxes etc.
2. Search in entity fields that are lists also.
3. Add list elements in simpleList type.

Ideas:
1. Code generation lets create entities and windows very fast.