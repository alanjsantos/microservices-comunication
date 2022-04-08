import UserService from "../service/UserService";

class UserController {
  async getByEmail(req, res) {
    let user = await UserService.getByEmail(req);
    return res.status(user.status).json(user);
  }
}

export default new UserController();
