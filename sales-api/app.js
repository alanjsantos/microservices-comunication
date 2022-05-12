import express from "express";
import { connect } from "./src/config/db/mongoDbConfig";
import Order from "./src/modules/sales/model/Order";

const app = express();
const env = process.env;
const PORT = env.PORT || 8082;

connect();

app.get("/api/status", async (req, res) => {
  let test = await Order.find();
  console.log(test);
  return res.status(200).json({
    service: "Sales-API",
    status: "up",
    httpStatus: 200,
  });
});

app.listen(PORT, () => {
  console.log(`Server started sucessfully at port ${PORT}`);
});
