# Microservices

Task is to create a simple simulation of company that produces metal plates.<br>
Machine creates plates and reports that to products microservice over the REST endpoint.<br>
Meanwhile, machine must send telemetry data (machine start time, plate creation start time, plate creation end time, machine shutdown time). Communications is done through MQTT protocol. <br>

Running Node.js Microservices

    Install Dependencies:
    Before running the microservices, make sure to install all dependencies. Navigate to the directory of each microservice in your terminal and run the following command:

npm install

This command will install all required packages and dependencies listed in the package.json file.

Start the Microservice:
After installing dependencies, start the microservice by running:

node app.js

![image](https://github.com/aleksandardrljaca/Microservices/assets/74873784/f8202212-bb49-4b32-beb7-17e66c75d6fe)
