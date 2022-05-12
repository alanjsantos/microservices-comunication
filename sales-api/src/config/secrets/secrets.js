const env = process.env;
export const MONGO_DB_URL = env.MONGO_DB_URL
  ? env.MONGO_DB_URL
  : "mongodb://admin:admin@localhost:27018";
