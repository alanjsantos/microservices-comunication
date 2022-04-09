import { Router } from "express";
import UserController from "../controllers/UserController";

const router = new Router();

router.get("/api/user/email/:email", UserController.getByEmail);
router.post("/api/user/auth", UserController.getToken);

export default router;
