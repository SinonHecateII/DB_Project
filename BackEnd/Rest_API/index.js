const express = require('express');
const app = express();
const PORT = 8080;

app.use(express.json())

app.listen(
    PORT,
    () => console.log(`Server is running on port ${PORT}`)
)

app.get('/tshirt', (req, res) => {
    res.status(200).send({
        "id": 1,
        "name": "T-Shirt",
        "price": 10.99,
    })
});

app.post('/tshirt/id', (req, res) => {
    const { id } = req.params;
    const { logo } = req.body;

    if(!logo){
        res.status(418).send({message:'We need a logo!'})
    }

    res.send({
        tshirt: 'T-Shirt with your ${logo} and ID ${id}',
    });
});