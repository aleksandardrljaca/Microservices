# Microservices

The task is to create a simple simulation of a company that produces metal plates.<br> A machine creates plates and reports this to the products microservice via a REST endpoint.<br> Additionally, the machine must send telemetry data, including machine start time, plate creation start time, plate creation end time, and machine shutdown time.<br> Communication is conducted using the MQTT protocol.<br>

## Running Microservices

### Install Dependencies:<br>
Before running the microservices, make sure to install all dependencies. Navigate to the directory of each microservice in your terminal and run the following command:
    
    npm install

This command will install all required packages and dependencies listed in the package.json file.

### Start the Microservice:
After installing dependencies, start the microservice by running:
    
    node app.js


![image](https://github.com/aleksandardrljaca/Microservices/assets/74873784/f8202212-bb49-4b32-beb7-17e66c75d6fe)
