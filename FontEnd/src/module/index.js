import { createStore } from "vuex";

const store = createStore({
  state() {
    return {
      activeTabBarIndex: 0,
      isSearch: false,
      searchBook: "",
      isBookContent: false,
      bookContent: {},
    };
  },
  mutations: {
    setActiveTabBarIndex(state, index) {
      // eslint-disable-next-line no-param-reassign
      state.activeTabBarIndex = index;
    },
    setIsSearch(state, isSearch) {
      // eslint-disable-next-line no-param-reassign
      state.isSearch = isSearch;
    },
    setSearchBook(state, searchBook) {
      // eslint-disable-next-line no-param-reassign
      state.searchBook = searchBook;
    },
    setIsBookContent(state, isBookContent) {
      // eslint-disable-next-line no-param-reassign
      state.isBookContent = isBookContent;
    },
    setBookContent(state, bookContent) {
      // eslint-disable-next-line no-param-reassign
      state.bookContent = bookContent;
    },
  },
});

export default store;
