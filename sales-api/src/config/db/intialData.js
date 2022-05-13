import Order from "../../modules/sales/model/Order";

export async function createInitialData() {
  await Order.collection.drop();
  await Order.create({
    products: [
      {
        productId: 1,
        quantity: 2,
      },
      {
        productId: 2,
        quantity: 1,
      },
      {
        productId: 3,
        quantity: 1,
      },
    ],
    user: {
      id: "123",
      name: "UserTest2",
      email: "usertest2@gmail.com",
    },
    status: "REJECTED",
    createdAt: new Date(),
    updatedAt: new Date(),
  });

  await Order.create({
    products: [
      {
        productId: 1,
        quantity: 4,
      },
      {
        productId: 3,
        quantity: 2,
      },
    ],
    user: {
      id: "123456",
      name: "UserTest",
      email: "userteste@gmail.com",
    },
    status: "APPROVED",
    createdAt: new Date(),
    updatedAt: new Date(),
  });

  let intialData = await Order.find();
  console.log(intialData);
}
