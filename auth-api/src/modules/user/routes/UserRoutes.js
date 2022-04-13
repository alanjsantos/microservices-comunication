import { Router } from "express";
import checkToken from "../../../config/auth/checkToken";
import UserController from "../controllers/UserController";

const router = new Router();

router.post("/api/user/auth", UserController.getToken);

router.use(checkToken);

router.get("/api/user/email/:email", UserController.getByEmail);

export default router;
