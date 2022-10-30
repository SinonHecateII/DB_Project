const express = require("express");
const app = express();
const multer = require("multer");
const fs = require("fs");

app.listen(8080, () => {
	const dir = "./uploads";
    if(!fs.existsSync(dir)) {
    	fs.mkdirSync(dir);
    }
    console.log("서버 실행");
});

const storage = multer.diskStorage({
  destination: function (req, file, cb) {
    cb(null, 'uploads')
  },
  filename: function (req, file, cb) {
    cb(null, file.originalname)
  }
});

const upload = multer({ storage: storage })

app.post('/multipart', upload.array('img'), (req, res, next) => {

    // console check
    req.files.map((data) => {
        console.log(data);
    });
    
    res.status(200).send({
        message: "Ok",
        fileInfo: req.files
    })
});

