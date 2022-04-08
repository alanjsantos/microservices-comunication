import { Router } from "express";
import UserController from "../controllers/UserController";

const router = new Router();

router.get("/api/user/email/:email", UserController.getByEmail);

export default router;
