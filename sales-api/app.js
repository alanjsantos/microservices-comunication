import express from "express";
import { connect } from "./src/config/db/mongoDbConfig";
import { createInitialData } from "./src/config/db/intialData";
// import checkToken from "./src/config/auth/checkToken";

const app = express();
const env = process.env;
const PORT = env.PORT || 8082;

connect();
createInitialData();
// app.use(checkToken);

app.get("/api/status", async (req, res) => {
  return res.status(200).json({
    service: "Sales-API",
    status: "up",
    httpStatus: 200,
  });
});

app.listen(PORT, () => {
  console.log(`Server started sucessfully at port ${PORT}`);
});
