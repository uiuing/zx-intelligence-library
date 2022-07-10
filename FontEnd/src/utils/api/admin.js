import { admin } from "./http.js";

const { get } = admin;

const getNotices = async () => {
  const data = await get("/listNotice2");
  return data;
};

// eslint-disable-next-line import/prefer-default-export
export { getNotices };
