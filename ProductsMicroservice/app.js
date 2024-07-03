const express=require('express')
const bodyParser = require('body-parser');
const app=express()
const cors=require('cors')
app.use(express.json())
app.use(cors())
app.use(bodyParser.json())
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
// get all
app.get('/plates', (req, res) => {
    pool.query('SELECT * FROM PLATE',(err,result)=>{
        if (err) {
            console.error('Error executing query', err);
            res.status(500).json({ error: 'Internal server error' });
          } else {
            res.json(result.rows);
          }
    })
  });
// delete all
app.delete('/plates',(req,res)=>{
  pool.query('DELETE FROM PLATE',(err,result)=>{
    if (err) {
        console.error('Error executing query', err);
        res.status(500).json({ error: 'Internal server error' });
      } else {
        res.json(result.rows);
      }
  })
});
// get max id
app.get('/plates/max', (req, res) => {
    pool.query('SELECT MAX(id) FROM PLATE',(err,result)=>{
        if (err) {
            console.error('Error executing query', err);
            res.status(500).json({ error: 'Internal server error' });
          } else {
            res.json(result.rows);
          }
    })
});
// get by id
app.get('/plates/:id',(req,res)=>{
    const id=parseInt(req.params.id);
    pool.query('SELECT FROM PLATE WHERE ID=$1',[id],(err,result)=>{
        if (err) {
            console.error('Error executing query', err);
            res.status(500).json({ error: 'Internal server error' });
          } else {
            res.json(result.rows);
          }
    })
});

app.post('/plates',(req,res)=>{
    const data = req.body;
    if (!data.id) {
        return res.status(400).send("ID not present!");
    }

    // Check if ID already exists
    pool.query('SELECT id FROM PLATE WHERE id = $1', [data.id], (selectErr, selectResult) => {
        if (selectErr) {
            console.error('Error while executing select query!', selectErr.stack);
            return res.status(500).send("Error while checking the ID!");
        }

        // If ID exists return error
        if (selectResult.rows.length > 0) {
            return res.status(409).send("ID already exists!");
        }

        // If ID does not exist perform insert query
        pool.query('INSERT INTO PLATE (id) VALUES ($1)', [data.id], (insertErr) => {
            if (insertErr) {
                console.error('Error executing insert query!', insertErr.stack);
                return res.status(500).send("Error inserting data!");
            }

            // Successfully inserted data
            res.status(200).send("New plate sucessfully created!");
        });
    });
})

  app.listen(4001, () => {
    console.log(`Server listening at http://localhost:4001`);
  });
