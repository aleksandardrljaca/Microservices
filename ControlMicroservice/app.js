const express=require('express')

const app=express()
const mqtt = require("mqtt");
const cors=require('cors')
app.use(express.json())
app.use(cors())

//initializing mqtt client
const client = mqtt.connect("tcp://broker.mqttdashboard.com:1883");

//setting up communication with postgres
const { Pool } = require('pg');
const pool = new Pool({
    user: 'postgres',
    host: 'localhost',
    database: 'postgres',
    schema:'public',
    password: 'YOUR_PASSWORD',
    port: 9000,
  });

pool.connect((err)=>{
  if(err)
    console.log('connection error',err.stack)
  else console.log('connected')
})

//subscribing to topic to receive telemetry data messages
client.on('connect', function () {
  console.log('Connected to MQTT broker');
    client.subscribe('machine/telemetrydata', function (err) {
    if (!err) {
      console.log('Subscribed to machine/telemetrydata');
    }
  });
});
// when message is received on machine/telemetrydata topic, store telemetry data into db
client.on('message', function (topic, message) {
  // Parsing telemetry data from received message
  if(message.length!=0){
    const jsonObject = JSON.parse(message.toString('utf-8'));
    const machineId = jsonObject.machineId;
    const plateId = jsonObject.plateId;
    const time = jsonObject.time;
    const type = jsonObject.type;
    try {
          console.log('received telemetry data from machine'+machineId)
            // Perform query
          pool.query(`INSERT INTO telemetry_data ("machineId", "plateId", "time", "type") VALUES ($1, $2, $3, $4)`, [machineId, plateId, time, type], (err, res) => {
              if (err) {
                  console.error('Error executing query', err);
              } 
          });
      } catch (error) {
            console.error('Error parsing JSON message', error);
        }
  }
 
});
// get all
app.get('/telemetrydata',(req,res)=>{
    pool.query('SELECT * FROM TELEMETRY_DATA', (err, result) => {
        if (err) {
          console.error('Error executing query', err);
          res.status(500).json({ error: 'Internal server error' });
        } else {
          res.json(result.rows);
        }
    })
});
app.delete('/telemetrydata',(req,res)=>{
  pool.query('DELETE FROM TELEMETRY_DATA', (err, result) => {
    if (err) {
      console.error('Error executing query', err);
      res.status(500).json({ error: 'Internal server error' });
    } else {
      res.json(result.rows);
    }
})
});



app.listen(3001,()=>{
    console.log('Service running...')
})
