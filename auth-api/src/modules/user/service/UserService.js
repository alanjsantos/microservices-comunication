import UserRepository from "../repoitory/UserRepository";
import * as httpStatus from "../../../config/constants/HttpStatus";
import UserException from "../exception/UserException";

class UserService {
  async getByEmail(req, res) {
    try {
      const { email } = req.params;
      this.validateRequestData(email);
      let user = await UserRepository.getByEmail(email);
      this.valdidateUserNotFound(user);

      return {
        status: httpStatus.SUCCESS,
        user: {
          id: user.id,
          name: user.name,
          email: user.email,
        },
      };
    } catch (err) {
      return {
        status: err.status ? err.status : httpStatus.INTERNAL_SERVER_EROR,
        message: err.message,
      };
    }
  }

  validateRequestData(email) {
    if (!email) {
      throw new UserException(
        httpStatus.BAD_REQUEST,
        "User email was not informed."
      );
    }
  }

  valdidateUserNotFound(user) {
    if (!user) {
      throw new UserException(httpStatus.NOT_FOUND, "User was not found");
    }
  }
}

export default new UserService();
