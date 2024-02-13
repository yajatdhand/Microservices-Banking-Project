# Banking Application
Project Overview
The Banking Application project is a Microservices-based banking application designed to streamline and modernize various banking processes. It aims to provide a scalable, modular, and efficient solution for managing customer information and associated accounts.

# PORTS :
EUREKA-SERVER: http://localhost:8761
 
API-GATEWAY: http://localhost:8082
 
CUSTOMER-SERVICE: http://localhost:8080
 
ACCOUNT-SERVICE: http://localhost:8081
 
# CUSTOMER-SERVICE :
 
 
# 1.)  Add Customer
 
 http://localhost:8082/customer/addCustomer
 
    JSON Request Body:
   
{
    "custName": "Ayush",
    "custAddress": "Jaipur",
    "custPhone": "8876149087",
    "custDob": "2001-01-01"
}
 
 # 2.) Update Customer
 
       http://localhost:8082/customer/updateCustomer
 
{
    "custName": "Ayush Sharma",
    "custAddress": "Mumbai",
    "custPhone": "8876149087",
    "custDob": "2002-01-01"
}
       
# 3.) Get All Customers
 
      http://localhost:8082/customer/getAllCustomers
 
# 4.) Get Customer By ID
 
      http://localhost:8082/customer/getCustomer/{id}
 
# 5.) Delete Customer
 
      http://localhost:8082/customer/deleteCustomer/{id}
 
 
 
 # ACCOUNT-SERVICE :
 
  # 1.) Get Account Details
 
    http://localhost:8082/account/getAccount?custId=10&accountNumber=14000000990670
 
  # 2.) Deposit/Withdraw Money
 
    http://localhost:8082/account/transaction 
{
    "custId" : 11,
    "accountNumber" : "14000000029130",
    "transactionType": "W",
    "amount" : 100
}
  
  # 3.) Delete Account
 
        http://localhost:8082/account/deleteAccount?custId=10&accountNumber=14000000990670
 
 
 
# Confi-Server Properties : https://github.com/yajatdhand/microservices-config
