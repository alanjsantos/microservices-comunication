<<<<<<< HEAD
import Sequelize from 'sequelize';
import sequelize from './../../../config/db/dbConfig';

const User = sequelize.define(
    "user",
    {
      id: {
        type: Sequelize.INTEGER,
        primaryKey: true,
        autoIncrement: true,
      },
      name: {
        type: Sequelize.STRING,
        allowNull: false,
      },
      email: {
        type: Sequelize.STRING,
        allowNull: false,
      },
      password: {
        type: Sequelize.STRING,
        allowNull: false,
      },
    },
    {}
  );

  export default User;
=======
import Sequelize from "sequelize";
import sequelize from "../../../config/db/dbConfig";

const User = sequelize.define(
    'user', 
    {
    id: {
      type: Sequelize.INTEGER,
      primaryKey: true,
      autoIncrement: true,
    },
    name: {
        type: Sequelize.STRING,
        allowNull: false,
    }, 
    email: {
        type: Sequelize.STRING,
        allowNull: false,
    },
    password: {
        type: Sequelize.STRING,
        allowNull: false,
    },
    },
    {}   
)

export default User
>>>>>>> 6f3a565cf2fcab7739468ebdf1d19d01b6a08276
