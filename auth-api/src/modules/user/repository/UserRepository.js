import User from "../model/User";

class UserRepository {
  async getById(id) {
    try {
      return await User.findOne({ where: { id } });
    } catch (err) {
      console.log(err.message);
    }
  }

  async getByEmail(email) {
    try {
      return await User.findOne({ where: { email } });
    } catch (err) {
      console.log(err.message);
    }
  }
}

export default new UserRepository();
