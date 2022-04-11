import UserRepository from "../repository/UserRepository";
import * as httpStatus from "../../../config/constants/HttpStatus";
import UserException from "../exception/UserException";
import bcrypt from "bcrypt";
import jwt from "jsonwebtoken";
import * as secrets from "../../../config/constants/secrets";

class UserService {
  async getByEmail(req) {
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

  async getToken(req) {
    try {
      const { email, password } = req.body;
      this.validateToken(email, password);
      let user = await UserRepository.getByEmail(email);
      this.valdidateUserNotFound(user);
      await this.validatePassword(password, user.password);
      const authUser = { id: user.id, name: user.name, email: user.email };
      const accessToken = jwt.sign({ authUser }, secrets.API_SECRET, {
        expiresIn: "1d",
      });
      return {
        status: httpStatus.SUCCESS,
        accessToken,
      };
    } catch (err) {
      return {
        status: err.status ? err.status : httpStatus.INTERNAL_SERVER_EROR,
        message: err.message,
      };
    }
  }

  validateToken(email, password) {
    if (!email || !password) {
      throw new UserException(
        httpStatus.UNAUTHORIZED,
        "Email and password must be informed."
      );
    }
  }

  async validatePassword(password, hashPassword) {
    if (!(await bcrypt.compare(password, hashPassword))) {
      throw new UserException(
        httpStatus.UNAUTHORIZED,
        "Password doesn't match."
      );
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
