import { favorite } from "./http.js";

const { get, put } = favorite;

const favoriteBook = async (bNo, bookName) => {
  try {
    await put("", {
      bNo,
      bookName,
    });
  } catch (e) {
    return false;
  }
  return true;
};

const unFavoriteBook = async (bNo, bookName) => {
  try {
    await favorite.delete("", {
      data: {
        bNo,
        bookName,
      },
    });
  } catch (e) {
    return false;
  }
  return true;
};

const getFavoriteList = async () => {
  const data = await get("");
  return data;
};

const getBookInfo = async (bNo) => {
  const data = await get("to", {
    params: {
      bNo,
    },
  });
  return data;
};

export { favoriteBook, getBookInfo, getFavoriteList, unFavoriteBook };
