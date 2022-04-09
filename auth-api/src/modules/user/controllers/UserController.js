import User from "../model/User";
import UserService from "../service/UserService";

class UserController {
  async getToken(req, res) {
    let accessToken = await UserService.getToken(req);
    return res.status(accessToken.status).json(accessToken);
  }

  async getByEmail(req, res) {
    let user = await UserService.getByEmail(req);
    return res.status(user.status).json(user);
  }
}

export default new UserController();
