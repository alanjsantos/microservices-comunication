import jwt from "jsonwebtoken";
import { promisify } from "util";
import * as httpStatus from "../constants/HttpStatus";
import AuthException from "./AuthException";
import * as secrets from "../constants/secrets";

const meptySpace = " ";

export default async (req, res, next) => {
  try {
    const { authorization } = req.headers;
    if (!authorization) {
      throw new AuthException(
        httpStatus.UNAUTHORIZED,
        "Access token was not informed"
      );
    }
    let accessToken = authorization;
    if (accessToken.includes(meptySpace)) {
      accessToken = accessToken.split(meptySpace)[1];
    } else {
      accessToken = authorization;
    }
    const decoded = await promisify(jwt.verify)(
      accessToken,
      secrets.API_SECRET
    );
    req.authUser = decoded.authUser;
    return next();
  } catch (err) {
    const status = err.status ? err.status : httpStatus.INTERNAL_SERVER_EROR;
    return res.status(err.status).json({
      status,
      message: err.message,
    });
  }
};
