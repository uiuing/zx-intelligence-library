import axios from "axios";

const ROOT_URL = "/api";

const book = axios.create({
  baseURL: `${ROOT_URL}/book`,
});

const student = axios.create({
  baseURL: `${ROOT_URL}/user`,
});

const favorite = axios.create({
  baseURL: `${ROOT_URL}/favorite`,
});

const borrowed = axios.create({
  baseURL: `${ROOT_URL}/borrowed`,
});

const evaluate = axios.create({
  baseURL: `${ROOT_URL}/evaluate`,
});

const comment = axios.create({
  baseURL: `${ROOT_URL}/comment`,
});

const admin = axios.create({
  baseURL: `${ROOT_URL}/admin`,
});

const cnt = axios.create({
  baseURL: `${ROOT_URL}/cnt`,
});

const apiList = [book, favorite, borrowed, evaluate, comment, admin, cnt];

// eslint-disable-next-line no-restricted-syntax
for (const obj of apiList) {
  obj.interceptors.response.use((response) => {
    if (response.status === 200) {
      const { data } = response;
      if ("code" in data && data.code === 0) {
        window.location.hash = "sign-in";
        return Promise.reject(data);
      }
      if ("data" in data) {
        return Promise.resolve(data.data);
      }
      return Promise.resolve(data);
    }
    return Promise.reject(response);
  });
}

export { admin, book, borrowed, cnt, comment, evaluate, favorite, student };
