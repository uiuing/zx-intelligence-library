import { book } from "./http.js";

const { get } = book;

const getBook = async (page) => {
  const data = await get("/list", {
    params: {
      page,
      pageSize: 15,
    },
  });
  return data;
};

const getSearchBook = async (keyword, page) => {
  const data = await get("/page", {
    params: {
      name: keyword,
      page,
      pageSize: 10,
    },
  });
  return data;
};

const getCategoryBook = async (category, page) => {
  const data = await get("/catePage", {
    params: {
      cate: category,
      page,
      pageSize: 14,
    },
  });
  return data;
};

export { getBook, getCategoryBook, getSearchBook };
