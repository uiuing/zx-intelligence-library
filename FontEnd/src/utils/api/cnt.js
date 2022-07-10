import { cnt } from "./http.js";

const { get } = cnt;

const getBorrowed = async (page) => {
  const data = await get("/borrowed", {
    params: {
      page,
      pageSize: 10,
    },
  });
  return data;
};

const getFavorite = async (page) => {
  const data = await get("/favorite", {
    params: {
      page,
      pageSize: 10,
    },
  });
  return data;
};

export { getBorrowed, getFavorite };
