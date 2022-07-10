const initContentHeight = () => {
  document.body.style.setProperty(
    "--auto-content-height",
    `${window.innerHeight}px`
  );
  document.body.style.setProperty(
    "--auto-tabbar-height",
    `${
      document.querySelector(
        "#app > div.van-tabbar.van-tabbar--fixed.van-hairline--top-bottom.van-safe-area-bottom"
      ).clientHeight + 20
    }px`
  );
  document.body.style.setProperty(
    "--auto-content-mini-height",
    `${
      window.innerHeight -
      document.querySelector(
        "#app > div.van-tabbar.van-tabbar--fixed.van-hairline--top-bottom.van-safe-area-bottom"
      ).clientHeight -
      100
    }px`
  );
};

// eslint-disable-next-line import/prefer-default-export
export { initContentHeight };
