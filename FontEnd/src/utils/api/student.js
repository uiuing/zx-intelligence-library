import * as localforage from "localforage";

import { student } from "./http.js";

const { get, post, put } = student;

const getUserInfo = async () => {
  const res = await get("");
  const { data } = res;
  if ("code" in data && data.code === 0) {
    window.location.hash = "sign-in";
  }

  return data.data;
};

const signIn = async (card, password) => {
  const res = await post("/login", {
    idCard: card,
    passWd: password,
  });
  const { code } = res.data;
  if (code === 0) {
    return false;
  }
  const { data } = res.data;
  await localforage.setItem("idCard", data.idCard);
  await localforage.setItem("studentId", data.studentId);
  await localforage.setItem("studentName", data.studentName);
  return true;
};

const signOut = async () => {
  await post("/logout");
  await localforage.clear();
  window.location.reload();
};

const changePassword = async (newPassword) => {
  let idCard = "";
  try {
    idCard = await localforage.getItem("idCard");
    // eslint-disable-next-line no-empty
  } catch (e) {}
  const data = await put("/changePassWd", {
    idCard,
    passWd: newPassword,
  });
  return data;
};

export { changePassword, getUserInfo, signIn, signOut };
