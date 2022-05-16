import jwt from "jsonwebtoken";
import { promisify } from "util";
import { UNAUTHORIZED, INTERNAL_SERVER_EROR } from "../httpStatus";
import AuthException from "./AuthException";
import { API_SECRET } from "../secrets/secrets";

const meptySpace = " ";

export default async (req, res, next) => {
  try {
    let { authorization } = req.headers;
    if (!authorization) {
      throw new AuthException(UNAUTHORIZED, "Access token was not informed");
    }
    let accessToken = authorization;
    if (accessToken.includes(meptySpace)) {
      accessToken = accessToken.split(meptySpace)[1];
    } else {
      accessToken = authorization;
    }
    const decoded = await promisify(jwt.verify)(accessToken, API_SECRET);
    req.authUser = decoded.authUser;
    return next();
  } catch (err) {
    const status = err.status ? err.status : INTERNAL_SERVER_EROR;
    return res.status(err.status).json({
      status,
      message: err.message,
    });
  }
};
