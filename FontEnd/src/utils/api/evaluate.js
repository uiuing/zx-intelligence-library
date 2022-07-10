import localforage from "localforage";

import { evaluate } from "./http.js";

const { get, put } = evaluate;

const addEvaluate = async (bno, content) => {
  let studentId = "";
  let studentName = "";
  try {
    studentId = await localforage.getItem("studentId");
    studentName = await localforage.getItem("studentName");
    // eslint-disable-next-line no-empty
  } catch (e) {}
  try {
    await put("", {
      bno,
      studentName,
      studentId,
      content,
      parentId: -1,
    });
  } catch (e) {
    return false;
  }
  return true;
};

const getBookEvaluateList = async (bno, page) => {
  const data = await get("/page", {
    params: {
      bno,
      page,
      pageSize: 5,
    },
  });
  return data;
};

const deleteEvaluate = async (id) => {
  try {
    await evaluate.delete("", {
      data: {
        id,
      },
    });
  } catch (e) {
    return false;
  }
  return true;
};

export { addEvaluate, deleteEvaluate, getBookEvaluateList };
