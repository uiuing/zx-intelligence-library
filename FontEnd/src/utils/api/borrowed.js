import localforage from "localforage";

import { borrowed } from "./http.js";

const { get, post } = borrowed;

const borrowBook = async (bno, bookName, startDate, endDate) => {
  let idCard = "";
  try {
    idCard = await localforage.getItem("idCard");
    // eslint-disable-next-line no-empty
  } catch (e) {}
  try {
    await post("add", {
      idCard,
      bno,
      bookName,
      startDate,
      endDate,
    });
  } catch (e) {
    return false;
  }
  return true;
};

const getBorrowList = async () => {
  const data = await get("");
  return data;
};

const getBorrowTimeOutList = async () => {
  const data = await get("ifTimeout");
  return data;
};

export { borrowBook, getBorrowList, getBorrowTimeOutList };
