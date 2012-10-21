Rename build.sample.properties to build.properties and configure it for your database. Add application to tomcat, start and got to http://localhost:8080/happyfaces/login.jsf
User/password: aaa/aaa (can change in applicationContext-security.xml

To populate sample data run "mvn properties:read-project-properties dbunit:operation" command

To remove current sample application:
Delete Customer.java, Account.java, Contacts.java, Operation.java, OperationType.java from domain package
Delete CustomerBean.java, AccountBean.java, OperationBean.java from beans package
Delete AccountService.jav, CustomerService.java, OperationService.java, IAccountService.jav, ICustomerService.java, IOperationService.java from services package
Delete accountEdit.xhtml, accounts.xhtml, operationEdit.xhtml, operations.xhtml, customerEdit.xhtml, customers.xhtml from src/main/webapp/pages/
Delete entries from demoDatabase.xml
Change menu.xhtml
Remove messages from messages.properties

TODO: 
1. search lists
2. Multiselects and deletes
3. disable sorting on unsortable fields (like messaged enums)

Ideas:
1. Remove *EntityField.xhtml and simply let fields be accessed through dot (e.g. <formField field="customer.name" />)
2. Code generation lets create entities and windows very fast.