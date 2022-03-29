import express from "express";

const app = express();
const env = process.env;
const PORT  = env.PORT || 8083;

app.listen(PORT, () => {
    console.log(`Server started sucessfully at port ${PORT}`);
})